package com.hamzasabir.schoolmanagementsystem.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;

import com.hamzasabir.schoolmanagementsystem.R;


public class ShowResultFragment extends DialogFragment {


    public static ShowResultFragment newInstance() {
        return new ShowResultFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_result, container, false);

        return view;
    }
}