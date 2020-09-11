package com.urufit.aitum.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.results.Tokens;
import com.urufit.aitum.R;
import com.urufit.aitum.adapter.ScheduleAdapter;
import com.urufit.aitum.ui.SingletonSession;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ScheduleActivity extends AppCompatActivity {

    public Toolbar toolbar;
    RecyclerView recyclerView;
    String Token;
    ScheduleAdapter adapter;
    ArrayList<String>teamNameList=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        toolbar = findViewById(R.id.toolbar_customs);
        toolbar.setTitle("Schedule");
        toolbar.setTitleTextAppearance(getApplicationContext(),R.style.CustomFontStyle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
         recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        AWSMobileClient.getInstance().getTokens(new com.amazonaws.mobile.client.Callback<Tokens>() {
            @Override
            public void onResult(Tokens result) {
                Token = result.getIdToken().getTokenString();
                Log.d("Token=", Token);
                getIndividualSchedules();
                getTeamName();
            }
            @Override
            public void onError(Exception e) {

            }
        });
    }
    private void getIndividualSchedules() {
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.atium.in/v1/clients/" + SingletonSession.Instance().getScope() + "/users/"+SingletonSession.Instance().getEmail()+"/schedules").newBuilder();
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .header("Authorization", "Bearer " + Token)
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                String mMessage = e.getMessage().toString();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                Log.w("Sucess Response", response.toString());
                String mMessage = response.body().string();
                try {
                    JSONArray jsonArray = new JSONArray(mMessage);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        JSONArray jsonEvents = jsonObject.getJSONArray("events");
                        for (int j = 0; j < jsonEvents.length(); j++) {
                            JSONObject jsonEventsObject = jsonEvents.getJSONObject(j);
                            JSONArray jsonactivities = jsonEventsObject.getJSONArray("activities");
                            for (int k = 0; k < jsonactivities.length(); k++) {
                                JSONObject jsonActivitiesObject = jsonactivities.getJSONObject(k);
                                String TestName=jsonActivitiesObject.getString("name");
                                teamNameList.add(TestName);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void getTeamName() {

        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.atium.in/v1/clients/" + SingletonSession.Instance().getScope() + "/users/"+SingletonSession.Instance().getEmail()+"/teams?type=member").newBuilder();
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .header("Authorization", "Bearer " + Token)
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                String mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                Log.w("Sucess Response", response.toString());
                String mMessage = response.body().string();
                try {
                    JSONArray jsonArray = new JSONArray(mMessage);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String TeamName=jsonObject.getString("name");
                        getTeamSchedule(TeamName);
                        }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getTeamSchedule(String teamName) {

        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.atium.in/v1/clients/" + SingletonSession.Instance().getScope() + "/teams/"+teamName+"/schedules").newBuilder();
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .header("Authorization", "Bearer " + Token)
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                String mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                Log.w("Sucess Response", response.toString());
                String mMessage = response.body().string();
                try {
                    JSONArray jsonArray = new JSONArray(mMessage);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        JSONArray jsonEvents = jsonObject.getJSONArray("events");
                        for (int j = 0; j < jsonEvents.length(); j++) {
                            JSONObject jsonEventsObject = jsonEvents.getJSONObject(j);
                            JSONArray jsonactivities = jsonEventsObject.getJSONArray("activities");
                            for (int k = 0; k < jsonactivities.length(); k++) {
                                JSONObject jsonActivitiesObject = jsonactivities.getJSONObject(k);
                                String TestName=jsonActivitiesObject.getString("name");
                                teamNameList.add(TestName);
                            }
                        }
                    }
                    adapter = new ScheduleAdapter(getApplicationContext(),teamNameList);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_schedule, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.nav_add:
              Intent intent=new Intent(getApplicationContext(),AddScheduleActivity.class);
                startActivity( intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
