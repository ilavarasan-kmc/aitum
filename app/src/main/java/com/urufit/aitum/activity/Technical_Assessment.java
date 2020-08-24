package com.urufit.aitum.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.urufit.aitum.R;


public class Technical_Assessment extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_technical_assessment);

        toolbar = findViewById(R.id.toolbar_customs);
        toolbar.setTitle("Technical Assessment Test");
        setSupportActionBar(toolbar);
    }
}
