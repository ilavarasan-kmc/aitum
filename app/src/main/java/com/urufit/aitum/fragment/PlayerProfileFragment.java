package com.urufit.aitum.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.results.Tokens;
import com.urufit.aitum.R;
import com.urufit.aitum.activity.ServiceActivity;
import com.urufit.aitum.adapter.PlayerprofileAdapter;
import com.urufit.aitum.adapter.ScheduleAdapter;
import com.urufit.aitum.model.PlayerProfileModel;
import com.urufit.aitum.model.ScheduleModel;
import com.urufit.aitum.model.ServiceModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread;

public class PlayerProfileFragment extends Fragment {
    RecyclerView recyclerView;
    String Token,ClientName;
    List<PlayerProfileModel>list=new ArrayList<>();
    PlayerprofileAdapter adapter;

    public PlayerProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_player_profile, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        /*PlayerProfileModel[] myListData = new PlayerProfileModel[]{
                new PlayerProfileModel("NeelKanth", "001"),
                new PlayerProfileModel("Dinakaran", "002"),
                new PlayerProfileModel("ilavarasan", "003"),
                new PlayerProfileModel("Murugesh", "004"),
                new PlayerProfileModel("Rehab", "005"),
                new PlayerProfileModel("Chola", "006"),
        };*/
        //PlayerprofileAdapter adapter = new PlayerprofileAdapter(getActivity(),Arrays.asList(myListData));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        AWSMobileClient.getInstance().getUserAttributes(new com.amazonaws.mobile.client.Callback<Map<String, String>>() {
            @Override
            public void onResult(Map<String, String> result) {
                String name = result.get("given_name");
               // ClientName = result.get("email");
            }

            @Override
            public void onError(Exception e) {

            }
        });

        AWSMobileClient.getInstance().getTokens(new com.amazonaws.mobile.client.Callback<Tokens>() {
            @Override
            public void onResult(Tokens result) {
                Token = result.getIdToken().getTokenString();
                Log.d("Token=", Token);
                fetchPlayerName();
            }
            @Override
            public void onError(Exception e) {

            }
        });
        return rootView;
    }

    private void fetchPlayerName() {
        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.atium.in/v1/clients/urufit/users").newBuilder();
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

                        PlayerProfileModel playerProfileModel=new PlayerProfileModel(jsonObject.getString("name"),jsonObject.getString("email"));
                        list.add(playerProfileModel);
                        PlayerprofileAdapter adapter=new PlayerprofileAdapter(getActivity(),list);
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();

                            }
                        });

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
