package com.hamzasabir.schoolmanagementsystem.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.hamzasabir.schoolmanagementsystem.Models.StudentModel;
import com.hamzasabir.schoolmanagementsystem.R;
import com.hamzasabir.schoolmanagementsystem.callbacks.mOnItemClickListner;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {

    DatabaseReference mRef;
    ArrayList<StudentModel> models;
    Context mContext;
    mOnItemClickListner itemClickListener;

    public ResultAdapter(ArrayList<StudentModel> models, Context mContext, mOnItemClickListner itemClickListener) {
        this.models = models;
        this.mContext = mContext;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.release_admission_student_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final StudentModel model = models.get(position);
        Picasso.get().load(model.getImgURl()).into(holder.student_img_logo);
        holder.age.setText(model.getAge());
        holder.classTV.setText(model.getClassTaken());
        holder.name.setText(model.getName());

        holder.release_admission_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(model);
            }
        });

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView student_img_logo;
        TextView name, age, classTV, describe, accept, decline;
        CardView release_admission_cv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            student_img_logo = itemView.findViewById(R.id.student_img_logo);
            name = itemView.findViewById(R.id.name);
            age = itemView.findViewById(R.id.age);
            classTV = itemView.findViewById(R.id.classTV);
            release_admission_cv = itemView.findViewById(R.id.release_admission_cv);

        }
    }


}

