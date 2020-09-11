package com.hamzasabir.schoolmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hamzasabir.schoolmanagementsystem.Fragmets.AddSchoolFragment;
import com.hamzasabir.schoolmanagementsystem.Fragmets.AdmissionsRequestsFragment;
import com.hamzasabir.schoolmanagementsystem.Fragmets.ChatFragment;
import com.hamzasabir.schoolmanagementsystem.Fragmets.ManageSchoolFragment;
import com.hamzasabir.schoolmanagementsystem.Fragmets.MyStudentsFragment;
import com.hamzasabir.schoolmanagementsystem.Fragmets.StaffFragment;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView profileimage;
    TextView name, email;


    DatabaseReference adminRef,addSchoolcheckRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("My Student");
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new MyStudentsFragment()).commit();
        final NavigationView navigationView = findViewById(R.id.nav_view);


        adminRef = FirebaseDatabase.getInstance().getReference();
        addSchoolcheckRef = FirebaseDatabase.getInstance().getReference();


        adminRef.child("Admins").child(FirebaseAuth.getInstance().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            // add data to nav Header
                            View header_view = navigationView.getHeaderView(0);
                            profileimage = header_view.findViewById(R.id.header_img);
                            Picasso.get().load(snapshot.child("profileimg").getValue().toString()).into(profileimage);
                            email = header_view.findViewById(R.id.header_email);
                            name = header_view.findViewById(R.id.header_name);
                            email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                            name.setText(snapshot.child("fullname").getValue().toString());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        drawerLayout = findViewById(R.id.drawer_layout);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.add_school:
                        addSchoolcheckRef.child("Schools").child(FirebaseAuth.getInstance().getUid())
                                .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()){
                                    Toast.makeText(MainActivity.this, "School Aleadry Added", Toast.LENGTH_SHORT).show();
                                }else {
                                    setTitle("Add School");
                                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                            new AddSchoolFragment()).commit();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        break;

                    case R.id.nav_new_admission:
                        setTitle("Admission Requests");
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new AdmissionsRequestsFragment()).commit();
                        break;
                    case R.id.nav_manage:
                        setTitle("Manage School");
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new ManageSchoolFragment()).commit();
                        break;
                    case R.id.nav_staff:
                        setTitle("My Staff");
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new StaffFragment()).commit();
                        break;
                    case R.id.nav_students:
                        setTitle("My Student");
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new MyStudentsFragment()).commit();
                        break;
                    case R.id.nav_logout:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(MainActivity.this, SplashActivity.class));
                        finish();
                        break;
                    case R.id.nav_send:
                        Toast.makeText(MainActivity.this, "Send", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_share:
                        Toast.makeText(MainActivity.this, "Share", Toast.LENGTH_SHORT).show();
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);


        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ManageSchoolFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_manage);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();

        }
    }

}