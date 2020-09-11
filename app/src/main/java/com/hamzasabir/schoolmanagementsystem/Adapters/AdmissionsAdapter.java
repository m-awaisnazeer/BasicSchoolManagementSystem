package com.hamzasabir.schoolmanagementsystem.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hamzasabir.schoolmanagementsystem.AdmissionRequestActivity;
import com.hamzasabir.schoolmanagementsystem.Models.StudentModel;
import com.hamzasabir.schoolmanagementsystem.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdmissionsAdapter extends RecyclerView.Adapter<AdmissionsAdapter.ViewHolder> {

    DatabaseReference mRef;
    ArrayList<StudentModel> models;
    Context mContext;

    public AdmissionsAdapter(ArrayList<StudentModel> models, Context mContext) {
        this.models = models;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.admission_request_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final StudentModel model = models.get(position);
        Picasso.get().load(model.getImgURl()).into(holder.student_img_logo);
        holder.age.setText(model.getAge());
        holder.classTV.setText(model.getClassTaken());
        holder.name.setText(model.getName());
        holder.describe.setText(model.getDescribe());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, AdmissionRequestActivity.class);
                intent.putExtra("uid",model.getUid());
                mContext.startActivity(intent);

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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            student_img_logo = itemView.findViewById(R.id.student_img_logo);
            name = itemView.findViewById(R.id.name);
            age = itemView.findViewById(R.id.age);
            classTV = itemView.findViewById(R.id.classTV);
            describe = itemView.findViewById(R.id.describe);


        }
    }
}
