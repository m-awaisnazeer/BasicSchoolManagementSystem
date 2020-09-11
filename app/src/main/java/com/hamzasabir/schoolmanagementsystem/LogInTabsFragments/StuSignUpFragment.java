package com.hamzasabir.schoolmanagementsystem.LogInTabsFragments;

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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hamzasabir.schoolmanagementsystem.MainActivity;
import com.hamzasabir.schoolmanagementsystem.R;
import com.hamzasabir.schoolmanagementsystem.StudentDashboard;

import java.util.HashMap;


public class StuSignUpFragment extends Fragment {

    EditText email, username, fullname, password, confirm_password;
    Button signup_btn;
    ProgressBar progressBar;
    DatabaseReference StudentRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stu_sign_up, container, false);

        email = view.findViewById(R.id.email);
        username = view.findViewById(R.id.username);
        fullname = view.findViewById(R.id.fullname);
        password = view.findViewById(R.id.password);
        confirm_password = view.findViewById(R.id.confirm_password);
        signup_btn = view.findViewById(R.id.signup_btn);
        progressBar = view.findViewById(R.id.progressBar);

        StudentRef = FirebaseDatabase.getInstance().getReference();
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String email_string = email.getText().toString();
                final String username_string = username.getText().toString();
                String password_string = password.getText().toString();
                String confirm_pass_string = confirm_password.getText().toString();
                final String fullname_string = fullname.getText().toString();

                if (TextUtils.isEmpty(email_string)) {
                    Toast.makeText(getActivity(), "Email Required", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                } else if (TextUtils.isEmpty(username_string)) {
                    Toast.makeText(getActivity(), "User Name Required", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

                } else if (TextUtils.isEmpty(fullname_string)) {
                    Toast.makeText(getActivity(), "Full Name Required", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

                } else if (TextUtils.isEmpty(password_string)) {
                    Toast.makeText(getActivity(), "Password Required", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

                } else if (TextUtils.isEmpty(confirm_pass_string)) {
                    Toast.makeText(getActivity(), "Confirm Password Required", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

                } else if (!password_string.equals(confirm_pass_string)) {
                    Toast.makeText(getActivity(), "Confirm Password doesn't match with Password", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

                }
                else {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email_string, password_string)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        HashMap<String, String> hashMap = new HashMap<>();
                                        hashMap.put("uid", FirebaseAuth.getInstance().getUid());
                                        hashMap.put("fullname", fullname_string);
                                        hashMap.put("username", username_string);
                                        hashMap.put("profileimg","https://ceris-gratton-jones.com/wp-content/uploads/2018/10/profile-picture-placeholder.png");

                                        StudentRef.child("Students").child(FirebaseAuth.getInstance().getUid())
                                                .setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    progressBar.setVisibility(View.GONE);
                                                    startActivity(new Intent(getActivity(), StudentDashboard.class));
                                                    getActivity().finish();
                                                } else {
                                                    progressBar.setVisibility(View.GONE);
                                                    Toast.makeText(getActivity(), "Error:\n" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    } else {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(getActivity(), "Error:\n" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        return view;
    }
}