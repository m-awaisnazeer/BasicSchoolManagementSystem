package com.hamzasabir.schoolmanagementsystem.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.hamzasabir.schoolmanagementsystem.R;
import com.hamzasabir.schoolmanagementsystem.common.Common;


public class PostResultFragment extends DialogFragment {

    TextView selected_student_name;

    public static PostResultFragment newInstance() {
        return new PostResultFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_result, container, false);

        selected_student_name = view.findViewById(R.id.selected_student_name);
        if (Common.selectedStudent != null) {
            selected_student_name.setText(Common.selectedStudent.getName());
        }
        return view;
    }
}