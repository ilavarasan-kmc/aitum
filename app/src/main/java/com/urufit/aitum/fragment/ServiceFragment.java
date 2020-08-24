package com.urufit.aitum.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.results.Tokens;
import com.urufit.aitum.R;
import com.urufit.aitum.adapter.ServiceAdapter;
import com.urufit.aitum.databinding.FragmentServiceBinding;
import com.urufit.aitum.model.ServiceModel;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ServiceFragment extends Fragment {
    public static ServiceFragment newInstance() {
        return  new ServiceFragment();
    }
ActionBar toolbar;
    FragmentServiceBinding binding;
    String Token;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        toolbar.setTitle("Service");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_service, container, false);

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

        return binding.getRoot();
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
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    // do something wih the result
                }
            }
        });

    }
}
