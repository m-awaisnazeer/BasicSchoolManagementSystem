package com.usamatariq.schoolmanagementsystem.Fragmets.BottomNavFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hamzasabir.schoolmanagementsystem.R;
import com.usamatariq.schoolmanagementsystem.SplashActivity;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {


    Button logout_btn;
    CircleImageView profile_CI;
    TextView name, email, username, txt_logout;

    DatabaseReference StudentRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        logout_btn = view.findViewById(R.id.logout_btn);
        profile_CI = view.findViewById(R.id.profile_CI);
        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        username = view.findViewById(R.id.username);
        txt_logout = view.findViewById(R.id.txt_logout);


        StudentRef = FirebaseDatabase.getInstance().getReference();
        StudentRef.child("Students").child(FirebaseAuth.getInstance().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            username.append(" " + snapshot.child("username").getValue().toString());

                            Picasso.get().load(snapshot.child("profileimg").getValue().toString()).into(profile_CI);
                            name.append(" " + snapshot.child("fullname").getValue().toString());
                            email.append(" " + FirebaseAuth.getInstance().getCurrentUser().getEmail().toString());

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        txt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), SplashActivity.class));
                getActivity().finish();
            }
        });

        return view;
    }
}