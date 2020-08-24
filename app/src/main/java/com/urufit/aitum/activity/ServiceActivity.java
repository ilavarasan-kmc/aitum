package com.urufit.aitum.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.results.Tokens;
import com.urufit.aitum.R;
import com.urufit.aitum.adapter.ServiceAdapter;
import com.urufit.aitum.databinding.ActivityServiceBinding;
import com.urufit.aitum.model.ServiceModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ServiceActivity extends AppCompatActivity {
    String Token;
    ActivityServiceBinding binding;
    List<ServiceModel> list=new ArrayList<>();
    ServiceAdapter   adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      binding= DataBindingUtil.setContentView(this,R.layout.activity_service);

        AWSMobileClient.getInstance().getTokens(new com.amazonaws.mobile.client.Callback<Tokens>() {
            @Override
            public void onResult(Tokens result) {
                Token = result.getIdToken().getTokenString();
                Log.d("Token=", Token);
                fetchUsersdata();
            }
            @Override
            public void onError(Exception e) {

            }
        });
        adapter = new ServiceAdapter(list);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        binding.recyclerView.setLayoutManager(mLayoutManager);
        binding.recyclerView.setAdapter(adapter);
    }
    private void fetchUsersdata() {
        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://y16dosf4mh.execute-api.ap-south-1.amazonaws.com/main/v1/clients/testClient/services").newBuilder();
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
                    JSONArray jsonArray=new JSONArray(mMessage);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);

                        ServiceModel serviceModel=new ServiceModel();
                        serviceModel.setName(jsonObject.getString("name"));
                        serviceModel.setDescription(jsonObject.getString("description"));
                        list.add(serviceModel);
                    }
                    ServiceActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
