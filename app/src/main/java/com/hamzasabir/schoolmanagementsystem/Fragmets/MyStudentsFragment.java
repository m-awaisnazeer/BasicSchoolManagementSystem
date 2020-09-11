package com.hamzasabir.schoolmanagementsystem.Fragmets;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hamzasabir.schoolmanagementsystem.Adapters.AdmissionsAdapter;
import com.hamzasabir.schoolmanagementsystem.MainActivity;
import com.hamzasabir.schoolmanagementsystem.Models.StudentModel;
import com.hamzasabir.schoolmanagementsystem.R;

import java.util.ArrayList;


public class MyStudentsFragment extends Fragment {


    DatabaseReference StudentsRef;
    ArrayList<StudentModel> models;
    RecyclerView all_students;

    public MainActivity activity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (MainActivity) activity;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_my_students, container, false);

        all_students = rootView.findViewById(R.id.all_students);
        all_students.setHasFixedSize(true);
        all_students.setLayoutManager(new LinearLayoutManager(getActivity()));
        StudentsRef = FirebaseDatabase.getInstance().getReference();
        StudentsRef.child("Schools").child(FirebaseAuth.getInstance().getUid()).child("MyStudents")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            models = new ArrayList<>();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                if (dataSnapshot.exists()) {
                                    StudentModel model = dataSnapshot.getValue(StudentModel.class);
                                    models.add(model);
                                }
                            }
                            AdmissionsAdapter adapter = new AdmissionsAdapter(models, getActivity());
                            all_students.setAdapter(adapter);

                        } else {
                            Toast.makeText(activity, "No Students Found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        return rootView;
    }
}