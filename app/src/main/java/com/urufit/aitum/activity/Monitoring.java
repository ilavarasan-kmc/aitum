package com.urufit.aitum.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.urufit.aitum.R;
import com.urufit.aitum.databinding.ActivityMonitoringBinding;


public class Monitoring extends AppCompatActivity {

    ActivityMonitoringBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding= DataBindingUtil.setContentView(this,R.layout.activity_monitoring);
       binding.cardViewWelbeing.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(getApplicationContext(),Wellbeing.class);
               startActivity(intent);
           }
       });

        binding.cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Load_Quantification.class);
                startActivity(intent);
            }
        });

        binding.cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Kinanthropometry.class);
                startActivity(intent);
            }
        });

    }
}
