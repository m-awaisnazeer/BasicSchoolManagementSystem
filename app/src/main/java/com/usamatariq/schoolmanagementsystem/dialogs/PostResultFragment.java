package com.usamatariq.schoolmanagementsystem.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.hamzasabir.schoolmanagementsystem.R;

import java.util.HashMap;

import static com.usamatariq.schoolmanagementsystem.common.Common.selectedStudent;


public class PostResultFragment extends DialogFragment {

    TextView selected_student_name;
    EditText edt_enter_total_marks, edt_enter_obtained_marks;
    Button btn_submit_result;

    public static PostResultFragment newInstance() {
        return new PostResultFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_result, container, false);

        selected_student_name = view.findViewById(R.id.selected_student_name);
        edt_enter_total_marks = view.findViewById(R.id.edt_enter_total_marks);
        edt_enter_obtained_marks = view.findViewById(R.id.edt_enter_obtained_marks);
        btn_submit_result = view.findViewById(R.id.btn_submit_result);

        if (selectedStudent != null) {
            selected_student_name.setText(selectedStudent.getName());
        }

        btn_submit_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_enter_total_marks.getText().toString().isEmpty()) {
                    edt_enter_total_marks.setError("Enter Total Marks");
                    return;
                }

                if (edt_enter_obtained_marks.getText().toString().isEmpty()) {
                    edt_enter_obtained_marks.setError("Enter Obtained Marks");
                    return;
                }

                HashMap<String, Object> resultMap = new HashMap<>();
                resultMap.put("obtainedMarks", Integer.valueOf(edt_enter_obtained_marks.getText().toString()));
                resultMap.put("totalMarks", Integer.valueOf(edt_enter_total_marks.getText().toString()));

                FirebaseDatabase.getInstance().getReference().child("Students").child(selectedStudent.getUid())
                        .updateChildren(resultMap);
                getDialog().dismiss();

//                FirebaseDatabase.getInstance().getReference().child("Schools")
//                        .child(FirebaseAuth.getInstance().getUid()).child("MyStudents")
//                        .child(selectedStudent.getUid()).updateChildren(resultMap)
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(Task<Void> task) {
//                                if (task.isSuccessful()) {
//                                    getDialog().dismiss();
//                                }
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(Exception e) {
//
//                    }
//                });
//
            }
        });

        return view;
    }
}