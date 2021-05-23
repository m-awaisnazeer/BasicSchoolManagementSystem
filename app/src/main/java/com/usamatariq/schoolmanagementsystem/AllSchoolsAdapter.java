package com.usamatariq.schoolmanagementsystem;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.usamatariq.schoolmanagementsystem.Models.SchoolModel;
import com.hamzasabir.schoolmanagementsystem.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AllSchoolsAdapter extends RecyclerView.Adapter<AllSchoolsAdapter.ViewHolder> {

    Context mContext;
    ArrayList<SchoolModel> schoolModelArrayList;

    public AllSchoolsAdapter(Context mContext, ArrayList<SchoolModel> schoolModelArrayList) {
        this.mContext = mContext;
        this.schoolModelArrayList = schoolModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.school_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final SchoolModel schoolModel = schoolModelArrayList.get(position);
        holder.address.setText(schoolModel.getAddress());
        holder.moto.setText(schoolModel.getMoto());
        holder.schoolName.setText(schoolModel.getName());

        Picasso.get().load(schoolModel.getImg1()).into(holder.img1);
        Picasso.get().load(schoolModel.getImg2()).into(holder.img2);
        Picasso.get().load(schoolModel.getImg3()).into(holder.img3);

        holder.moto.setSingleLine();
        holder.moto.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        holder.moto.setMarqueeRepeatLimit(-1);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,ViewSchoolActivity.class);
                intent.putExtra("uid",schoolModel.getUid());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return schoolModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img1,img2,img3;
        TextView schoolName,address,moto;
        View itemView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img1 = itemView.findViewById(R.id.img1);
            img2 = itemView.findViewById(R.id.img2);
            img3 = itemView.findViewById(R.id.img3);
            schoolName = itemView.findViewById(R.id.schoolName);
            address = itemView.findViewById(R.id.address);
            moto = itemView.findViewById(R.id.moto);
            moto.setSelected(true);

            this.itemView = itemView;

        }
    }
}
