package com.usamatariq.schoolmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hamzasabir.schoolmanagementsystem.R;
import com.squareup.picasso.Picasso;
import com.usamatariq.schoolmanagementsystem.Models.StudentModel;

public class AdmissionRequestActivity extends AppCompatActivity {

    ImageView student_img_logo;
    TextView name, age, classTV, describe, accept, decline;
    String uid;
    DatabaseReference AdmissionsRef;
    StudentModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admission_request);


        student_img_logo = findViewById(R.id.student_img_logo);
        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        classTV = findViewById(R.id.classTV);
        describe = findViewById(R.id.describe);
        accept = findViewById(R.id.accept);

        uid = getIntent().getStringExtra("uid");
        AdmissionsRef = FirebaseDatabase.getInstance().getReference();
        AdmissionsRef.child("AdmissionsRequests").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    model = snapshot.getValue(StudentModel.class);
                    name.setText(model.getName());
                    age.setText(model.getAge());
                    classTV.setText(model.getClassTaken());
                    describe.setText(model.getDescribe());
                    Picasso.get().load(model.getImgURl()).into(student_img_logo);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AdmissionsRef.child("AdmissionsRequests").child(uid).setValue(null);
                AdmissionsRef.child("Students").child(uid).child("MySchool").child("id").setValue(FirebaseAuth.getInstance().getUid());
                AdmissionsRef.child("Schools").child(FirebaseAuth.getInstance().getUid()).child("MyStudents").child(model.getUid()).setValue(model);

                startActivity(new Intent(AdmissionRequestActivity.this, MainActivity.class));
                finish();


            }
        });

    }
}