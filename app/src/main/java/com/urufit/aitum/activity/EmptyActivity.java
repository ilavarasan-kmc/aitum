package com.urufit.aitum.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.urufit.aitum.R;
import com.urufit.aitum.ui.SingletonSession;
import java.util.ArrayList;
import java.util.Map;

public class EmptyActivity extends AppCompatActivity {
    String a;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> CustomScopeList = new ArrayList<>();
    public String MY_PREFS_NAME = "User_Profile";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AWSMobileClient.getInstance().getUserAttributes(new Callback<Map<String, String>>() {
            @Override
            public void onResult(Map<String, String> result) {

                String name = result.get("given_name");
                String email = result.get("email");
                String mRole = result.get("custom:Role");
                String customScope = result.get("custom:Scope");
                String userID = result.get("sub");
                SingletonSession.Instance().setUsername(name);
                SingletonSession.Instance().setEmail(email);
                SingletonSession.Instance().setUserId(userID);
                SingletonSession.Instance().setScope(customScope);
                SingletonSession.Instance().setRole(mRole);
                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("Name", name);
                editor.putString("Email", email);
                editor.putString("Role", mRole);
                editor.putString("Scope", customScope);
                editor.apply();


                String[] arrOfScope = customScope.split(";");
                for (String a : arrOfScope)
                    CustomScopeList.add(a);
                SingletonSession.Instance().setScope(CustomScopeList.get(0));

                if ("athlete".equalsIgnoreCase(mRole)) {

                } else {

                    String[] arrOfStr = mRole.split(";");
                    for (String a : arrOfStr)
                        arrayList.add(a);
                    SingletonSession.Instance().setRole(arrayList.get(0));
                }

                if ("athlete".equalsIgnoreCase(mRole)) {
                    Intent intent = new Intent(getApplicationContext(), Athlete_Home_Activity.class);
                    intent.putExtra("Name", name);
                    intent.putExtra("Email", email);
                    intent.putExtra("Role", mRole);
                    intent.putExtra("scopelist", CustomScopeList);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    intent.putExtra("Name", name);
                    intent.putExtra("Email", email);
                    intent.putExtra("Role", mRole);
                    intent.putExtra("arraylist", arrayList);
                    intent.putExtra("scopelist", CustomScopeList);
                    startActivity(intent);
                }
            }

            @Override
            public void onError(Exception e) {
            }
        });
    }
}
