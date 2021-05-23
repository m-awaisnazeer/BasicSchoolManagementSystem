package com.usamatariq.schoolmanagementsystem.Fragmets;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.usamatariq.schoolmanagementsystem.Adapters.AdmissionsAdapter;
import com.usamatariq.schoolmanagementsystem.Models.StudentModel;
import com.hamzasabir.schoolmanagementsystem.R;

import java.util.ArrayList;


public class AdmissionsRequestsFragment extends Fragment {

    RecyclerView all_requests;
    ArrayList<StudentModel> studentModelArrayList;
    DatabaseReference mRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admissions_requests, container, false);
        all_requests = view.findViewById(R.id.all_requests);
        all_requests.setHasFixedSize(true);
        all_requests.setLayoutManager(new LinearLayoutManager(getActivity()));


        mRef = FirebaseDatabase.getInstance().getReference();
        mRef.child("AdmissionsRequests")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            studentModelArrayList = new ArrayList<>();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                if (dataSnapshot.exists()) {
                                    StudentModel model = dataSnapshot.getValue(StudentModel.class);
                                    if (model.getSchoolUid().equals(FirebaseAuth.getInstance().getUid())){
                                        studentModelArrayList.add(model);
                                    }
                                }
                            }

                            AdmissionsAdapter admissionsAdapter = new AdmissionsAdapter(studentModelArrayList,getActivity());
                            all_requests.setAdapter(admissionsAdapter);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        return view;
    }
}