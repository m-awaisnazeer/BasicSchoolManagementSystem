package com.hamzasabir.schoolmanagementsystem.Fragmets;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hamzasabir.schoolmanagementsystem.Adapters.MyStaffAdapter;
import com.hamzasabir.schoolmanagementsystem.Adapters.StaffAdapter;
import com.hamzasabir.schoolmanagementsystem.AddStaffActivity;
import com.hamzasabir.schoolmanagementsystem.MainActivity;
import com.hamzasabir.schoolmanagementsystem.Models.SchoolModel;
import com.hamzasabir.schoolmanagementsystem.Models.StaffModel;
import com.hamzasabir.schoolmanagementsystem.R;
import com.hamzasabir.schoolmanagementsystem.ViewSchoolActivity;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


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