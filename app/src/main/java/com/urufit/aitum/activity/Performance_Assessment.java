package com.urufit.aitum.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.urufit.aitum.R;
import com.urufit.aitum.databinding.ActivityPerformanceAssessmentBinding;


public class Performance_Assessment extends AppCompatActivity {

    ActivityPerformanceAssessmentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_performance__assessment);

        binding.cardTech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Technical_Assessment.class);
                startActivity(intent);
            }
        });

        binding.cardSprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Sprint.class);
                startActivity(intent);
            }
        });

        binding.cardPower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Power.class);
                startActivity(intent);
            }
        });

        binding.cardStrength.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Strength.class);
                startActivity(intent);
            }
        });

        binding.cardEndurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Endurance.class);
                startActivity(intent);
            }
        });

        binding.cardCod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),COD.class);
                startActivity(intent);
            }
        });
    }
}
