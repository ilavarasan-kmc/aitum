package com.urufit.aitum.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.urufit.aitum.R;

public class AssessmentTestActivity extends AppCompatActivity {

    Toolbar toolbar;
    String TestName;
    MaterialButton btnPhysicalTest,btnLiveRecording,btnComTracking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_test);
        toolbar = findViewById(R.id.toolbar_customs);
        btnPhysicalTest = findViewById(R.id.btn_physical_test);
        btnLiveRecording = findViewById(R.id.btn_live_recording);
        btnComTracking = findViewById(R.id.btn_com_tracking);
        Intent intent=getIntent();
        TestName=intent.getStringExtra("testname");
        toolbar.setTitle(TestName);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

       btnPhysicalTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPhysicalTest=new Intent(getApplicationContext(),PhysicalTestingActivity.class);
                intentPhysicalTest.putExtra("testname",TestName);
                startActivity(intentPhysicalTest);
            }
        });
        btnLiveRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLiveRecording=new Intent(getApplicationContext(),ComingSoonActivity.class);
                startActivity(intentLiveRecording);
            }
        });

        btnComTracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentComTracking=new Intent(getApplicationContext(),ComingSoonActivity.class);
                startActivity(intentComTracking);
            }
        });
    }
}