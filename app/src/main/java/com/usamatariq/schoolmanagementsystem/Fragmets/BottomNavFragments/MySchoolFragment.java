package com.usamatariq.schoolmanagementsystem.Fragmets.BottomNavFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.usamatariq.schoolmanagementsystem.Adapters.EventAdapter;
import com.usamatariq.schoolmanagementsystem.Adapters.MainSliderAdapter;
import com.usamatariq.schoolmanagementsystem.Adapters.StaffAdapter;
import com.usamatariq.schoolmanagementsystem.Models.SchoolEventModel;
import com.usamatariq.schoolmanagementsystem.Models.SchoolModel;
import com.usamatariq.schoolmanagementsystem.Models.StaffModel;
import com.hamzasabir.schoolmanagementsystem.R;
import com.usamatariq.schoolmanagementsystem.Services.PicassoImageLoadingService;
import com.usamatariq.schoolmanagementsystem.ViewImageActivity;

import java.util.ArrayList;

import ss.com.bannerslider.Slider;
import ss.com.bannerslider.event.OnSlideClickListener;


public class MySchoolFragment extends Fragment {

    String uid;
    DatabaseReference schoolRef, StaffRef, EventsRef;
    private Slider slider;
    private com.usamatariq.schoolmanagementsystem.Services.PicassoImageLoadingService PicassoImageLoadingService;
    TextView schoolExistance, StaffTextView, eventTextview;
    RecyclerView staff_RV, events_RV;
    ArrayList<StaffModel> staffModelArrayList;
    ArrayList<SchoolEventModel> schoolEventModelArrayList;
    TextView schoolName, address, moto, applyForAdmission, message;
    RelativeLayout rl;
    String schoolUid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_my_school, container, false);
//
        schoolExistance = view.findViewById(R.id.schoolExistance);
        StaffTextView = view.findViewById(R.id.text);
        eventTextview = view.findViewById(R.id.eventText);
        rl = view.findViewById(R.id.rl);
        staff_RV = view.findViewById(R.id.staff_RV);
        schoolName = view.findViewById(R.id.schoolName);
        address = view.findViewById(R.id.address);
        moto = view.findViewById(R.id.moto);
        slider = view.findViewById(R.id.banner_slider1);
        staff_RV = view.findViewById(R.id.staff_RV);
        staff_RV.setHasFixedSize(true);
        staff_RV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        events_RV = view.findViewById(R.id.events_RV);
        events_RV.setHasFixedSize(true);
        events_RV.setLayoutManager(new LinearLayoutManager(getActivity()));

        schoolRef = FirebaseDatabase.getInstance().getReference();
        schoolRef.child("Students").child(FirebaseAuth.getInstance().getUid()).child("MySchool").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    schoolUid = snapshot.child("id").getValue().toString();
                    if (schoolUid != null) {
                        schoolExistance.setVisibility(View.GONE);
                        rl.setVisibility(View.VISIBLE);


                        populateEvents();
                        populateStaff();
                        populateSlider();
                    }

                } else {
                    schoolExistance.setVisibility(View.VISIBLE);
                    rl.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//
//
//

//


//
//


        return view;
    }

    private void populateSlider() {
        schoolRef.child("Schools").child(schoolUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final SchoolModel schoolModel = snapshot.getValue(SchoolModel.class);
                schoolName.setText(schoolModel.getName());
                address.setText(schoolModel.getAddress());
                moto.setText(schoolModel.getMoto());
                moto.setSelected(true);
                Slider.init(new PicassoImageLoadingService(getActivity()));


                MainSliderAdapter adapter = new MainSliderAdapter(schoolModel.getImg1(), schoolModel.getImg2(), schoolModel.getImg3());
                slider.setAdapter(adapter);
                slider.setInterval(5000);


                slider.setOnSlideClickListener(new OnSlideClickListener() {
                    @Override
                    public void onSlideClick(int position) {
                        Intent intent = new Intent(getActivity(), ViewImageActivity.class);
                        if (position == 0) {
                            intent.putExtra("url", schoolModel.getImg1());
                        }
                        if (position == 1) {
                            intent.putExtra("url", schoolModel.getImg2());
                        }
                        if (position == 2) {
                            intent.putExtra("url", schoolModel.getImg3());
                        }
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void populateStaff() {

        StaffRef = FirebaseDatabase.getInstance().getReference();
        StaffRef.child("Staffs").child(schoolUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    staffModelArrayList = new ArrayList<>();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (dataSnapshot.exists()) {
                            StaffModel staffModel = dataSnapshot.getValue(StaffModel.class);
                            staffModelArrayList.add(staffModel);
                            staffModelArrayList.add(staffModel);
                            staffModelArrayList.add(staffModel);
                        }
                    }
                    if (staffModelArrayList.size() >= 1) {
                        StaffAdapter adapter = new StaffAdapter(staffModelArrayList, getActivity());
                        staff_RV.setAdapter(adapter);
                        StaffTextView.setVisibility(View.VISIBLE);
                    }else {
                        StaffTextView.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void populateEvents() {
        StaffRef = FirebaseDatabase.getInstance().getReference();
        StaffRef.child("Schools").child(schoolUid).child("Events").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    schoolEventModelArrayList = new ArrayList<>();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (dataSnapshot.exists()) {
                            SchoolEventModel model = dataSnapshot.getValue(SchoolEventModel.class);
                            schoolEventModelArrayList.add(model);
                            schoolEventModelArrayList.add(model);
                            schoolEventModelArrayList.add(model);
                        }
                    }
                    if (schoolEventModelArrayList.size() >= 1) {
                        EventAdapter adapter = new EventAdapter(schoolEventModelArrayList, getActivity());
                        events_RV.setAdapter(adapter);
                        eventTextview.setVisibility(View.VISIBLE);
                    } else {
                        eventTextview.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}