package com.usamatariq.schoolmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hamzasabir.schoolmanagementsystem.R;

public class SplashActivity extends AppCompatActivity {
    Button admin_btn;
    Button student_btn;
    DatabaseReference CheckRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        admin_btn = findViewById(R.id.admin_btn);
        student_btn = findViewById(R.id.student_btn);
        CheckRef = FirebaseDatabase.getInstance().getReference();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    CheckRef.child("Admins").child(FirebaseAuth.getInstance().getUid())
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                        finish();
                                    } else {
                                        startActivity(new Intent(SplashActivity.this, StudentDashboard.class));
                                        finish();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                } else {
                    student_btn.setVisibility(View.VISIBLE);
                    admin_btn.setVisibility(View.VISIBLE);

                }

            }
        };

        Handler handler = new Handler();
        handler.postDelayed(runnable, 4000);
    }

    public void Student_button(View v) {
        startActivity(new Intent(SplashActivity.this,StudentLogin.class));
        finish();
    }

    public void Teacher_button(View v) {
        startActivity(new Intent(SplashActivity.this,AdminLogin.class));
        finish();
    }
}