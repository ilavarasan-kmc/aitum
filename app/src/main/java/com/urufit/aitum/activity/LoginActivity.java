package com.urufit.aitum.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.SignInUIOptions;
import com.amazonaws.mobile.client.UserStateDetails;

import com.urufit.aitum.R;
import com.urufit.aitum.ui.SharedPreferenc;

import java.util.Map;

import me.anwarshahriar.calligrapher.Calligrapher;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Lato-Regular.ttf", true);

        AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {
            @Override
            public void onResult(UserStateDetails result) {
                Log.i("User", String.valueOf(result.getUserState()));
                switch (result.getUserState()) {
                    case SIGNED_IN:
                        SharedPreferences sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("log_in",true);
                        editor.apply();
                        Intent i = new Intent(LoginActivity.this, EmptyActivity.class);
                        startActivity(i);
                        finish();
                        break;
                    case SIGNED_OUT:
                        showSignIn();
                        break;
                }
            }

            @Override
            public void onError(Exception e) {
            }
        });
    }

    private void showSignIn() {
        try {
            AWSMobileClient.getInstance().showSignIn(this, SignInUIOptions.builder().nextActivity(EmptyActivity.class).build());
        } catch (Exception e) {
        }
    }
}
