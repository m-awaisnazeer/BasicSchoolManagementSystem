package com.hamzasabir.schoolmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hamzasabir.schoolmanagementsystem.Adapters.EventAdapter;
import com.hamzasabir.schoolmanagementsystem.Adapters.MainSliderAdapter;
import com.hamzasabir.schoolmanagementsystem.Adapters.StaffAdapter;
import com.hamzasabir.schoolmanagementsystem.Models.SchoolEventModel;
import com.hamzasabir.schoolmanagementsystem.Models.SchoolModel;
import com.hamzasabir.schoolmanagementsystem.Models.StaffModel;
import com.hamzasabir.schoolmanagementsystem.Services.PicassoImageLoadingService;
import com.hamzasabir.schoolmanagementsystem.dialogs.ShowResultFragment;

import java.util.ArrayList;

import ss.com.bannerslider.Slider;
import ss.com.bannerslider.event.OnSlideClickListener;

public class ViewSchoolActivity extends AppCompatActivity {

    String uid;
    DatabaseReference schoolRef, StaffRef, EventsRef, AdmissionRef;
    private Slider slider;
    private PicassoImageLoadingService PicassoImageLoadingService;
    FragmentManager fm;

    RecyclerView staff_RV, events_RV;
    ArrayList<StaffModel> staffModelArrayList;
    ArrayList<SchoolEventModel> schoolEventModelArrayList;
    TextView schoolName, address, moto, applyForAdmission, message, StaffTextView, eventTextview;
    CardView get_result;
    ShowResultFragment showResultFragmentDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_school);
        uid = getIntent().getStringExtra("uid");
        slider = findViewById(R.id.banner_slider1);
        staff_RV = findViewById(R.id.staff_RV);
        applyForAdmission = findViewById(R.id.applyForAdmission);
        message = findViewById(R.id.message);
        StaffTextView = findViewById(R.id.text);
        eventTextview = findViewById(R.id.eventText);


        fm = getSupportFragmentManager();
        showResultFragmentDialog = ShowResultFragment.newInstance();

        staff_RV.setHasFixedSize(true);
        staff_RV.setLayoutManager(new LinearLayoutManager(ViewSchoolActivity.this, LinearLayoutManager.HORIZONTAL, false));
        events_RV = findViewById(R.id.events_RV);
        events_RV.setHasFixedSize(true);
        events_RV.setLayoutManager(new LinearLayoutManager(ViewSchoolActivity.this));

        schoolName = findViewById(R.id.schoolName);
        address = findViewById(R.id.address);
        moto = findViewById(R.id.moto);
        get_result = findViewById(R.id.get_result);

        schoolRef = FirebaseDatabase.getInstance().getReference();
        AdmissionRef = FirebaseDatabase.getInstance().getReference();

        schoolRef.child("Schools").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final SchoolModel schoolModel = snapshot.getValue(SchoolModel.class);
                schoolName.setText(schoolModel.getName());
                address.setText(schoolModel.getAddress());
                moto.setText(schoolModel.getMoto());
                moto.setSelected(true);


                Slider.init(new PicassoImageLoadingService(ViewSchoolActivity.this));


                MainSliderAdapter adapter = new MainSliderAdapter(schoolModel.getImg1(), schoolModel.getImg2(), schoolModel.getImg3());
                slider.setAdapter(adapter);

                setTitle(schoolModel.getName());

                slider.setOnSlideClickListener(new OnSlideClickListener() {
                    @Override
                    public void onSlideClick(int position) {
                        Intent intent = new Intent(ViewSchoolActivity.this, ViewImageActivity.class);
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


        StaffRef = FirebaseDatabase.getInstance().getReference();
        StaffRef.child("Staffs").child(uid).addValueEventListener(new ValueEventListener() {
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
                        StaffAdapter adapter = new StaffAdapter(staffModelArrayList, ViewSchoolActivity.this);
                        staff_RV.setAdapter(adapter);
                        StaffTextView.setVisibility(View.VISIBLE);

                    } else {
                        StaffTextView.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        applyForAdmission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AdmissionRef.child("AdmissionsRequests").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Toast.makeText(ViewSchoolActivity.this, "You already applied for admission, Wait for Confirmation!", Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(ViewSchoolActivity.this, ApplyForAdmissionsActivity.class).putExtra("uid", uid));
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });


        get_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showResultFragmentDialog.show(fm, "Result Fragment");
            }
        });

        populateEvents();
    }

    private void populateEvents() {
        StaffRef = FirebaseDatabase.getInstance().getReference();
        StaffRef.child("Schools").child(uid).child("Events").addValueEventListener(new ValueEventListener() {
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
                        EventAdapter adapter = new EventAdapter(schoolEventModelArrayList, ViewSchoolActivity.this);
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