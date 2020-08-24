package com.urufit.aitum.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.urufit.aitum.MainActivity;
import com.urufit.aitum.R;
import com.urufit.aitum.ui.SharedPreferenc;

import me.anwarshahriar.calligrapher.Calligrapher;

public class LauncherActivity extends AppCompatActivity {

    private static final String LOGIN_KEY = "LOGIN_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        SharedPreferences pref = getPreferences(Context.MODE_PRIVATE);

        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"Lato-Regular.ttf",true);

        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Boolean name=SharedPreferenc.getBoolean(getApplicationContext(),"LOGIN_KEY");
                LauncherActivity.this.startActivity(new Intent(LauncherActivity.this,

                        SharedPreferenc.getBoolean(LauncherActivity.this, LOGIN_KEY)
                                ? MainActivity.class
                                : LoginActivity.class)
                );
                LauncherActivity.this.finish();
            }
        }, 1000);*/

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(LauncherActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        }, 1000);
    }
}
