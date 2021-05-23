package com.usamatariq.schoolmanagementsystem.LogInTabsFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hamzasabir.schoolmanagementsystem.R;
import com.usamatariq.schoolmanagementsystem.StudentDashboard;


public class StuLoginFragment extends Fragment {
    EditText email, password;
    Button login;
    DatabaseReference UserCheckRef;
    ProgressBar PB;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stu_login, container, false);


        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        login = view.findViewById(R.id.login);
        PB = view.findViewById(R.id.PB);
        UserCheckRef = FirebaseDatabase.getInstance().getReference();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PB.setVisibility(View.VISIBLE);
                String email_string = email.getText().toString();
                String password_string = password.getText().toString();
                if (TextUtils.isEmpty(email_string)) {
                    Toast.makeText(getActivity(), "Email Requird...!", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(password_string)) {
                    Toast.makeText(getActivity(), "Password Requird...!", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email_string, password_string)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        UserCheckRef.child("Students").child(FirebaseAuth.getInstance().getUid())
                                                .addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        if (snapshot.exists()) {
                                                            PB.setVisibility(View.GONE);
                                                            startActivity(new Intent(getActivity(), StudentDashboard.class));
                                                            getActivity().finish();
                                                        } else {
                                                            PB.setVisibility(View.GONE);
                                                            Toast.makeText(getActivity(), "User didn't Register as Admin", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });
                                    }

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            PB.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "Error\n" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


        return view;
    }


}