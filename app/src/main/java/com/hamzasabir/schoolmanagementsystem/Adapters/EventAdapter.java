package com.hamzasabir.schoolmanagementsystem.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hamzasabir.schoolmanagementsystem.Models.SchoolEventModel;
import com.hamzasabir.schoolmanagementsystem.R;
import com.hamzasabir.schoolmanagementsystem.Services.PicassoImageLoadingService;
import com.hamzasabir.schoolmanagementsystem.ViewImageActivity;
import com.hamzasabir.schoolmanagementsystem.ViewSchoolActivity;

import java.util.ArrayList;

import ss.com.bannerslider.Slider;
import ss.com.bannerslider.event.OnSlideClickListener;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    ArrayList<SchoolEventModel> schoolEventModelArrayList;
    Context mContext;

    public EventAdapter(ArrayList<SchoolEventModel> schoolEventModelArrayList, Context mContext) {
        this.schoolEventModelArrayList = schoolEventModelArrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final SchoolEventModel model = schoolEventModelArrayList.get(position);

        MainSliderAdapter adapter = new MainSliderAdapter(model.getImg1(), model.getImg2(), model.getImg3());
        holder.slider.setAdapter(adapter);

        holder.eventDate.setText(model.getEventDate());
        holder.eventDescribe.setText(model.getEventDescription());
        holder.eventName.setText(model.getEventName());

        Slider.init(new PicassoImageLoadingService(mContext));

        holder.slider.setOnSlideClickListener(new OnSlideClickListener() {
            @Override
            public void onSlideClick(int position) {
                Intent intent = new Intent(mContext, ViewImageActivity.class);
                if (position == 0) {
                    intent.putExtra("url", model.getImg1());
                }
                if (position == 1) {
                    intent.putExtra("url", model.getImg2());
                }
                if (position == 2) {
                    intent.putExtra("url", model.getImg3());
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return schoolEventModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private Slider slider;
        TextView eventName, eventDate, eventDescribe;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            slider = itemView.findViewById(R.id.banner_slider1);
            eventName = itemView.findViewById(R.id.eventName);
            eventDate = itemView.findViewById(R.id.eventDate);
            eventDescribe = itemView.findViewById(R.id.eventDescribe);


        }
    }
}
