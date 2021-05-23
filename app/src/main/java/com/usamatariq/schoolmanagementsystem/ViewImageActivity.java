package com.usamatariq.schoolmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.chrisbanes.photoview.PhotoView;
import com.hamzasabir.schoolmanagementsystem.R;
import com.squareup.picasso.Picasso;

public class ViewImageActivity extends AppCompatActivity {

    PhotoView fullimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

        fullimage = findViewById(R.id.fullimage);

        Picasso.get().load(getIntent().getStringExtra("url")).into(fullimage);
    }
}