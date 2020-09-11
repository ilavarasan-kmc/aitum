package com.urufit.aitum.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.urufit.aitum.R;

public class ComingSoonActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coming_soon);
        toolbar = findViewById(R.id.toolbar_customs);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextView tv = (TextView) findViewById(R.id.txt_soon);
        Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/pacico.ttf");
        tv.setTypeface(face);

        ImageView imageView = findViewById(R.id.img_coming_soon);
        Glide.with(this).load(R.drawable.comming_soon).into(imageView);
    }
}