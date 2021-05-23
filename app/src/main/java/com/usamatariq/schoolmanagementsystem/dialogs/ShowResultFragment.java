package com.usamatariq.schoolmanagementsystem.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.usamatariq.schoolmanagementsystem.Models.StudentModel;
import com.hamzasabir.schoolmanagementsystem.R;


public class ShowResultFragment extends DialogFragment {
    DatabaseReference StudentRef;

    ProgressBar pb_show_result;
    TextView result_total_marks, txt_obtained_marks, txt_result;

    public static ShowResultFragment newInstance() {
        return new ShowResultFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_result, container, false);
        pb_show_result = view.findViewById(R.id.pb_show_result);
        result_total_marks = view.findViewById(R.id.result_total_marks);
        txt_obtained_marks = view.findViewById(R.id.txt_obtained_marks);
        txt_result = view.findViewById(R.id.txt_result);

        StudentRef = FirebaseDatabase.getInstance().getReference();
        StudentRef.child("Students").child(FirebaseAuth.getInstance().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            StudentModel studentModel = snapshot.getValue(StudentModel.class);
                            result_total_marks.append(String.valueOf(studentModel.getTotalMarks()));
                            txt_obtained_marks.append(String.valueOf(studentModel.getObtainedMarks()));
                            txt_result.append(paresResult(studentModel.getObtainedMarks(), studentModel.getTotalMarks()));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
        return view;
    }

    private String paresResult(int obtainedMarks, int totalMarks) {
        if (obtainedMarks / totalMarks >= 80) {
            return "Grade A";
        } else if (obtainedMarks / totalMarks >= 70 && obtainedMarks / totalMarks <= 80) {
            return "Grade B";

        } else if (obtainedMarks / totalMarks >= 60 && obtainedMarks / totalMarks <= 70) {
            return "Grade C";
        } else if (obtainedMarks / totalMarks >= 50 && obtainedMarks / totalMarks <= 60) {
            return "Grade D";
        } else if (obtainedMarks / totalMarks >= 40 && obtainedMarks / totalMarks <= 50) {
            return "Grade E";
        } else {
            return "Fail";
        }
    }
}