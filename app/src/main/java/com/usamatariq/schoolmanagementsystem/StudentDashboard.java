package com.usamatariq.schoolmanagementsystem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.usamatariq.schoolmanagementsystem.Fragmets.BottomNavFragments.AllSchoolsFragment;
import com.usamatariq.schoolmanagementsystem.Fragmets.BottomNavFragments.MySchoolFragment;
import com.usamatariq.schoolmanagementsystem.Fragmets.BottomNavFragments.ProfileFragment;
import com.hamzasabir.schoolmanagementsystem.R;

public class StudentDashboard extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        bottomNavigationView = findViewById(R.id.bottom_nav);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("My School");
        loadFragment(new MySchoolFragment());
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.my_school:
                    toolbar.setTitle("My School");
                    fragment = new MySchoolFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.all_schools:
                    toolbar.setTitle("All Schools");
                    fragment = new AllSchoolsFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.nav_profile:
                    toolbar.setTitle("Profile");
                    fragment = new ProfileFragment();
                    loadFragment(fragment);
                    return true;
            }

            return false;
        }
    };
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}