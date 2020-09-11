package com.hamzasabir.schoolmanagementsystem.Fragmets;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hamzasabir.schoolmanagementsystem.MainActivity;
import com.hamzasabir.schoolmanagementsystem.Models.SchoolEventModel;
import com.hamzasabir.schoolmanagementsystem.Models.SchoolModel;
import com.hamzasabir.schoolmanagementsystem.R;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class ManageSchoolFragment extends Fragment {

    EditText eventNameET, eventDateET, eventDescriptionET, schoolMotoET ;
    Button  submitSchool;
    ProgressBar addSchoolProgressBar;

    ImageView img1, img2, img3;
    private static final int Gallery_Pick = 1;

    Uri img1Uri, img2Uri, img3Uri;
    int school_position = 0;
    String school_type = null;

    DatabaseReference SchoolRef;
    private StorageReference PostsImagesReferences;
    private String img1Uri_String, img2Uri_String, img3Uri_String;

    ArrayList<Uri> images = new ArrayList<>(3);
    ArrayList<String> images_links = new ArrayList<>(3);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_manage_school, container, false);

        PostsImagesReferences = FirebaseStorage.getInstance().getReference();
        eventNameET = view.findViewById(R.id.eventNameET);
        eventDateET = view.findViewById(R.id.eventDateET);
        eventDescriptionET = view.findViewById(R.id.eventDescriptionET);


        submitSchool = view.findViewById(R.id.submitSchool);

        addSchoolProgressBar = view.findViewById(R.id.addSchoolProgressBar);


        img1 = view.findViewById(R.id.img1);
        img2 = view.findViewById(R.id.img2);
        img3 = view.findViewById(R.id.img3);

        SchoolRef = FirebaseDatabase.getInstance().getReference();
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
                school_position = 1;

            }
        });

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
                school_position = 2;
            }
        });

        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
                school_position = 3;
            }
        });

        submitSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSchoolProgressBar.setVisibility(View.VISIBLE);

                //Strings
                final String eventName_String = eventNameET.getText().toString();
                final String eventDate_String = eventDateET.getText().toString();
                final String eventDescription_String = eventDescriptionET.getText().toString();

                if (img1Uri == null) {
                    Toast.makeText(getActivity(), "Image 1 in not Selected", Toast.LENGTH_SHORT).show();
                    addSchoolProgressBar.setVisibility(View.GONE);
                } else if (img2Uri == null) {
                    Toast.makeText(getActivity(), "Image 2 in not Selected", Toast.LENGTH_SHORT).show();
                    addSchoolProgressBar.setVisibility(View.GONE);
                } else if (img3Uri == null) {
                    Toast.makeText(getActivity(), "Image 3 in not Selected", Toast.LENGTH_SHORT).show();
                    addSchoolProgressBar.setVisibility(View.GONE);
                }  else if (TextUtils.isEmpty(eventName_String) && TextUtils.isEmpty(eventDate_String) && TextUtils.isEmpty(eventDescription_String)) {
                    Toast.makeText(getActivity(), "All Fields are requires", Toast.LENGTH_SHORT).show();
                    addSchoolProgressBar.setVisibility(View.GONE);
                } else {

                    StorageReference ImageFolder = FirebaseStorage.getInstance().getReference().child("Event Images").child(FirebaseAuth.getInstance().getUid());

                    int upload_count;
                    for (upload_count = 0; upload_count < images.size(); upload_count++) {

                        Uri IndividualImage = images.get(upload_count);
                        final StorageReference ImageName = ImageFolder.child("Images" + IndividualImage.getLastPathSegment());

                        ImageName.putFile(IndividualImage).addOnSuccessListener(
                                new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        ImageName.getDownloadUrl().addOnSuccessListener(
                                                new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        images_links.add(String.valueOf(uri));

                                                        if (images_links.size() == images.size()) {

                                                            SchoolEventModel model = new SchoolEventModel( images_links.get(0), images_links.get(1), images_links.get(2),
                                                                    eventName_String,eventDate_String,eventDescription_String);
                                                            SchoolRef.child("Schools").child(FirebaseAuth.getInstance().getUid()).child("Events").push().setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {
                                                                        addSchoolProgressBar.setVisibility(View.GONE);
                                                                        Toast.makeText(getActivity(), "Submitted", Toast.LENGTH_SHORT).show();
                                                                        startActivity(new Intent(getActivity(), MainActivity.class));
                                                                        getActivity().finish();
                                                                    } else {
                                                                        addSchoolProgressBar.setVisibility(View.GONE);
                                                                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();

                                                                    }
                                                                }
                                                            });
                                                        }

                                                    }
                                                }
                                        );
                                    }
                                }
                        ).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                addSchoolProgressBar.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "Error\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });


                    }


                }

            }
        });


        return view;
    }


    private void openGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, Gallery_Pick);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Gallery_Pick && resultCode == RESULT_OK && data != null) {

            if (school_position == 1) {
                img1Uri = data.getData();
                img1.setImageURI(img1Uri);
                images.add(0, img1Uri);
            } else if (school_position == 2) {
                img2Uri = data.getData();
                img2.setImageURI(img2Uri);
                images.add(1, img2Uri);
            } else {
                img3Uri = data.getData();
                img3.setImageURI(img3Uri);
                images.add(2, img3Uri);
            }
        }
    }
}