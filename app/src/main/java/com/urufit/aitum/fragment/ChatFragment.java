package com.urufit.aitum.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.urufit.aitum.R;

public class ChatFragment extends Fragment {
    public static ChatFragment newInstance() {
        return  new ChatFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_manager_chat, container, false);
    }
}
