package com.urufit.aitum.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.urufit.aitum.R;

public class ActivityFragment extends AppCompatActivity {
    public static ActivityFragment newInstance() {
        return  new ActivityFragment();
    }
ActionBar toolbar;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity);
        toolbar = ((AppCompatActivity) getApplicationContext()).getSupportActionBar();
        toolbar.setTitle("Activity");
    }
}

