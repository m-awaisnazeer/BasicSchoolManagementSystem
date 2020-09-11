package com.hamzasabir.schoolmanagementsystem.Fragmets.BottomNavFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hamzasabir.schoolmanagementsystem.AllSchoolsAdapter;
import com.hamzasabir.schoolmanagementsystem.Models.SchoolModel;
import com.hamzasabir.schoolmanagementsystem.R;

import java.util.ArrayList;


public class AllSchoolsFragment extends Fragment {

    RecyclerView all_schoolsRV;
    DatabaseReference allSchoolsRef;
    ArrayList<SchoolModel> schoolModelArrayList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_schools, container, false);

        all_schoolsRV = view.findViewById(R.id.all_schoolsRV);
        all_schoolsRV.setHasFixedSize(true);
        all_schoolsRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        allSchoolsRef = FirebaseDatabase.getInstance().getReference();

        populateRV();

        return view;
    }

    private void populateRV() {
        allSchoolsRef.child("Schools").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        if (snapshot1.exists()) {
                            SchoolModel schoolModel = snapshot1.getValue(SchoolModel.class);
                            schoolModelArrayList.add(schoolModel);
                        }
                    }
                    AllSchoolsAdapter allSchoolsAdapter = new AllSchoolsAdapter(getActivity(), schoolModelArrayList);
                    all_schoolsRV.setAdapter(allSchoolsAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}