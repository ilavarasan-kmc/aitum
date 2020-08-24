package com.urufit.aitum.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.urufit.aitum.R;


public class Power extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power);

        toolbar = findViewById(R.id.toolbar_customs);
        toolbar.setTitle("Power Test");
        setSupportActionBar(toolbar);
    }
}
