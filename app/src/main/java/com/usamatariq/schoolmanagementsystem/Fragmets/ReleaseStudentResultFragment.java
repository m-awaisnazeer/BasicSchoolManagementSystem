package com.usamatariq.schoolmanagementsystem.Fragmets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.usamatariq.schoolmanagementsystem.Adapters.ResultAdapter;
import com.usamatariq.schoolmanagementsystem.Models.StudentModel;
import com.hamzasabir.schoolmanagementsystem.R;
import com.usamatariq.schoolmanagementsystem.common.Common;
import com.usamatariq.schoolmanagementsystem.dialogs.PostResultFragment;

import java.util.ArrayList;


public class ReleaseStudentResultFragment extends Fragment implements com.usamatariq.schoolmanagementsystem.callbacks.mOnItemClickListner {
    DatabaseReference StudentsRef;
    ArrayList<StudentModel> models;
    RecyclerView all_students;
    FragmentManager fm;
    PostResultFragment postResultFragment;


    com.usamatariq.schoolmanagementsystem.callbacks.mOnItemClickListner mOnItemClickListner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_release_student_result, container, false);

        fm = getChildFragmentManager();

        mOnItemClickListner = this;
        all_students = view.findViewById(R.id.all_students);
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
                            ResultAdapter adapter = new ResultAdapter(models, getActivity(),mOnItemClickListner);
                            all_students.setAdapter(adapter);

                        } else {
                            if (isAdded()) {
                                Toast.makeText(requireContext(), "No Students Found", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        return view;
    }

    @Override
    public void onItemClick(StudentModel model) {
        postResultFragment = PostResultFragment.newInstance();
        postResultFragment.show(fm,"dummyyy");
        Common.selectedStudent = model;
    }
}