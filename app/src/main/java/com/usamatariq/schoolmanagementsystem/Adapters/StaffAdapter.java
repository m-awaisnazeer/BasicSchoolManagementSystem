package com.usamatariq.schoolmanagementsystem.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.usamatariq.schoolmanagementsystem.Models.StaffModel;
import com.hamzasabir.schoolmanagementsystem.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.ViewHolder> {

    ArrayList<StaffModel> staffModelArrayList;
    Context mContext;

    public StaffAdapter(ArrayList<StaffModel> staffModelArrayList, Context mContext) {
        this.staffModelArrayList = staffModelArrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.staff_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StaffModel staffModel = staffModelArrayList.get(position);

        holder.degree.setText(staffModel.getDegree());
        holder.describe.setText(staffModel.getDescribe());
        holder.experience.setText(staffModel.getExperience());
        holder.name.setText(staffModel.getName());

        Picasso.get().load(staffModel.getImgUrl()).into(holder.staff_img_logo);

    }

    @Override
    public int getItemCount() {
        return staffModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView staff_img_logo;
        TextView name,degree,experience,describe;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            staff_img_logo = itemView.findViewById(R.id.staff_img_logo);
            name = itemView.findViewById(R.id.name);
            degree = itemView.findViewById(R.id.degree);
            experience = itemView.findViewById(R.id.experience);
            describe = itemView.findViewById(R.id.describe);

        }
    }
}
