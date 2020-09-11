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
import com.hamzasabir.schoolmanagementsystem.Models.StudentModel;

public class ApplyForAdmissionsActivity extends AppCompatActivity {

    ImageView student_img_logo;
    Button submit_admission;
    ProgressBar PB;
    EditText name, age, classTaken, describe;
    private static final int Gallery_Pick = 1;
    Uri imageUri;
    String uid;

    DatabaseReference StaffRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_for_admissions);

        student_img_logo = findViewById(R.id.student_img_logo);
        submit_admission = findViewById(R.id.submit_admission);
        PB = findViewById(R.id.PB);
        describe = findViewById(R.id.describe);
        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        classTaken = findViewById(R.id.classET);

        uid = getIntent().getStringExtra("uid");


        final StorageReference ImageFolder = FirebaseStorage.getInstance().getReference().child(uid).child("AdmissionsRequests");
        StaffRef = FirebaseDatabase.getInstance().getReference();

        student_img_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        submit_admission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PB.setVisibility(View.VISIBLE);
                final String describe_str = describe.getText().toString();
                final String age_String = age.getText().toString();
                final String name_str = name.getText().toString();
                final String class_taken_String = classTaken.getText().toString();

                if (imageUri == null) {
                    PB.setVisibility(View.GONE);
                    Toast.makeText(ApplyForAdmissionsActivity.this, "Select Image..!!", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(describe_str) && TextUtils.isEmpty(age_String) &&
                        TextUtils.isEmpty(name_str) && TextUtils.isEmpty(class_taken_String)) {
                    PB.setVisibility(View.GONE);
                    Toast.makeText(ApplyForAdmissionsActivity.this, "All Fields are Required", Toast.LENGTH_SHORT).show();
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

                                                    StudentModel studentModel = new StudentModel(imgUrl,name_str,age_String,class_taken_String,describe_str,FirebaseAuth.getInstance().getUid(),uid);

                                                    StaffRef.child("AdmissionsRequests").child(FirebaseAuth.getInstance().getUid())
                                                            .setValue(studentModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                PB.setVisibility(View.GONE);
                                                                Toast.makeText(ApplyForAdmissionsActivity.this, "Submitted", Toast.LENGTH_SHORT).show();
                                                                startActivity(new Intent(ApplyForAdmissionsActivity.this,StudentDashboard.class));
                                                                finish();
                                                            }
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            PB.setVisibility(View.GONE);
                                                            Toast.makeText(ApplyForAdmissionsActivity.this, "Error\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(ApplyForAdmissionsActivity.this, "Error\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
            student_img_logo.setImageURI(imageUri);
        }
    }
}