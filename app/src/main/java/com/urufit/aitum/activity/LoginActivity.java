package com.urufit.aitum.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.SignInUIOptions;
import com.amazonaws.mobile.client.UserStateDetails;
import com.urufit.aitum.MainActivity;
import com.urufit.aitum.R;
import com.urufit.aitum.ui.SharedPreferenc;

import me.anwarshahriar.calligrapher.Calligrapher;

public class LoginActivity extends AppCompatActivity {

    private static final String LOGIN_KEY = "LOGIN_KEY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Calligrapher calligrapher=new Calligrapher(this);
        calligrapher.setFont(this,"Lato-Regular.ttf",true);

        AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {
            @Override
            public void onResult(UserStateDetails result) {
                Log.i("User", String.valueOf(result.getUserState()));
                switch (result.getUserState()) {
                    case SIGNED_IN:
                        SharedPreferenc.putBoolean(getApplicationContext(),LOGIN_KEY,true).apply();
                        Boolean name=SharedPreferenc.getBoolean(getApplicationContext(),"LOGIN_KEY");
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
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
            AWSMobileClient.getInstance().showSignIn(this, SignInUIOptions.builder().nextActivity(MainActivity.class).build());
        } catch (Exception e) {
        }
    }
}
