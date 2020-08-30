package com.urufit.aitum.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.urufit.aitum.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TeamProfileActivity extends AppCompatActivity {
    ArrayList<String> users = new ArrayList<>();
    View childView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_profile_description);
        TextView textView = findViewById(R.id.dialog_name_id);
        TextView dialog_phone_id = findViewById(R.id.dialog_phone_id);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String desc = intent.getStringExtra("desc");
        textView.setText(name);
        dialog_phone_id.setText("Team Members :"+desc);
        users = intent.getStringArrayListExtra("users");
        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < users.size(); i++) {
            View v = vi.inflate(R.layout.list_team_members, null);
            Button button = (Button) v.findViewById(R.id.dialog_btn_call);
            button.setText(users.get(i));
            LinearLayout list = findViewById(R.id.list_user);
            list.addView(v);
        }
    }
}
