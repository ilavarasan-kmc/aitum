package com.urufit.aitum.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.urufit.aitum.R;

public class ManagerSettingsFragment extends Fragment {

    public static ManagerSettingsFragment newInstance() {
        return  new ManagerSettingsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_manager_settings, container, false);
    }
}
