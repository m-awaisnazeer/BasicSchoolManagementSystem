package com.usamatariq.schoolmanagementsystem.Fragmets;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.usamatariq.schoolmanagementsystem.Adapters.MyStaffAdapter;
import com.usamatariq.schoolmanagementsystem.AddStaffActivity;
import com.usamatariq.schoolmanagementsystem.Models.StaffModel;
import com.hamzasabir.schoolmanagementsystem.R;

import java.util.ArrayList;


public class StaffFragment extends Fragment {
    RecyclerView staff_RV;
    ArrayList<StaffModel> staffModelArrayList ;
    DatabaseReference schoolRef,StaffRef;
    FloatingActionButton add_staff;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_staff, container, false);

        staff_RV = view.findViewById(R.id.staff_RV);
        add_staff = view.findViewById(R.id.add_staff);
        staff_RV.setHasFixedSize(true);
        staff_RV.setLayoutManager(new LinearLayoutManager(getActivity()));


        add_staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddStaffActivity.class));
            }
        });
        StaffRef = FirebaseDatabase.getInstance().getReference();
        StaffRef.child("Staffs").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    staffModelArrayList = new ArrayList<>();
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        if (dataSnapshot.exists()){
                            StaffModel staffModel = dataSnapshot.getValue(StaffModel.class);
                            staffModelArrayList.add(staffModel);
                            staffModelArrayList.add(staffModel);
                            staffModelArrayList.add(staffModel);
                        }
                    }

                    MyStaffAdapter adapter = new MyStaffAdapter(staffModelArrayList,getActivity());
                    staff_RV.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }


}