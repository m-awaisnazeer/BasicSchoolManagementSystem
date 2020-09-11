package com.hamzasabir.schoolmanagementsystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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
import com.hamzasabir.schoolmanagementsystem.Models.StaffModel;

public class AddStaffActivity extends AppCompatActivity {

    ImageView staff_img_logo;
    Button submit_staff;
    ProgressBar PB;
    EditText name, degree, experience, describe;
    private static final int Gallery_Pick = 1;
    Uri imageUri;

    DatabaseReference StaffRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staff);

        staff_img_logo = findViewById(R.id.staff_img_logo);
        submit_staff = findViewById(R.id.submit_staff);
        PB = findViewById(R.id.PB);
        describe = findViewById(R.id.describe);
        name = findViewById(R.id.name);
        degree = findViewById(R.id.degree);
        experience = findViewById(R.id.experience);


        final StorageReference ImageFolder = FirebaseStorage.getInstance().getReference().child("Staffs").child(FirebaseAuth.getInstance().getUid());
        StaffRef = FirebaseDatabase.getInstance().getReference();

        staff_img_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        submit_staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PB.setVisibility(View.VISIBLE);
                final String describe_str = describe.getText().toString();
                final String degree_str = degree.getText().toString();
                final String name_str = name.getText().toString();
                final String experience_str = experience.getText().toString();

                if (imageUri == null) {
                    PB.setVisibility(View.GONE);
                    Toast.makeText(AddStaffActivity.this, "Select Image..!!", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(describe_str) && TextUtils.isEmpty(degree_str) &&
                        TextUtils.isEmpty(name_str) && TextUtils.isEmpty(experience_str)) {
                    PB.setVisibility(View.GONE);
                    Toast.makeText(AddStaffActivity.this, "All Fields are Required", Toast.LENGTH_SHORT).show();
                } else {
                    final StorageReference ImageName = ImageFolder.child("Images" + imageUri.getLastPathSegment());

                    ImageName.putFile(imageUri).addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    ImageName.getDownloadUrl().addOnSuccessListener(
                                            new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    String imgUrl = String.valueOf(uri);

                                                    StaffModel staffModel = new StaffModel(imgUrl, name_str, degree_str
                                                            , experience_str, describe_str, FirebaseAuth.getInstance().getUid());

                                                    StaffRef.child("Staffs").child(FirebaseAuth.getInstance().getUid()).push()
                                                            .setValue(staffModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                PB.setVisibility(View.GONE);
                                                                Toast.makeText(AddStaffActivity.this, "Submitted", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            PB.setVisibility(View.GONE);
                                                            Toast.makeText(AddStaffActivity.this, "Error\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    });

                                                }
                                            }
                                    );
                                }
                            }
                    ).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            PB.setVisibility(View.GONE);
                            Toast.makeText(AddStaffActivity.this, "Error\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
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
            imageUri = data.getData();
            staff_img_logo.setImageURI(imageUri);
        }
    }
}