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
                Intent intent=new Intent(getApplicationContext(),AssessmentTestActivity.class);
                intent.putExtra("testname",binding.txtTechAssessment.getText().toString());
                startActivity(intent);
            }
        });

        binding.cardSprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),AssessmentTestActivity.class);
                intent.putExtra("testname",binding.txtSprint.getText().toString());
                startActivity(intent);
            }
        });

        binding.cardPower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),AssessmentTestActivity.class);
                intent.putExtra("testname",binding.txtPower.getText().toString());
                startActivity(intent);
            }
        });

        binding.cardStrength.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),AssessmentTestActivity.class);
                intent.putExtra("testname",binding.txtStrength.getText().toString());
                startActivity(intent);
            }
        });

        binding.cardEndurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),AssessmentTestActivity.class);
                intent.putExtra("testname",binding.txtEndurance.getText().toString());
                startActivity(intent);
            }
        });

        binding.cardCod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),AssessmentTestActivity.class);
                intent.putExtra("testname",binding.txtCod.getText().toString());
                startActivity(intent);
            }
        });
    }
}
