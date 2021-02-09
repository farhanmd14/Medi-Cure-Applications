package com.mymedicine;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class Show_image extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image);

        String img = getIntent().getStringExtra("img");
        ImageView image = findViewById(R.id.img);

        Glide.with(this).load(img).into(image);

    }
}
