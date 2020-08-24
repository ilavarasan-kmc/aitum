package com.urufit.aitum.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.urufit.aitum.R;

public class TeamProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_profile_description);
        TextView textView=findViewById(R.id.dialog_name_id);
        TextView dialog_phone_id=findViewById(R.id.dialog_phone_id);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String desc = intent.getStringExtra("desc");
        textView.setText(name);
        dialog_phone_id.setText(desc);
    }
}
