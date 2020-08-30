package com.urufit.aitum.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.results.Tokens;
import com.urufit.aitum.R;
import com.urufit.aitum.adapter.PlayerprofileAdapter;
import com.urufit.aitum.adapter.TeamprofileAdapter;
import com.urufit.aitum.model.PlayerProfileModel;
import com.urufit.aitum.model.TeamProfileModel;
import com.urufit.aitum.ui.SingletonSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread;

public class TeamProfileFragment extends Fragment {
    RecyclerView recyclerView;
    String Token;
    List<TeamProfileModel> list=new ArrayList<>();
    TeamprofileAdapter adapter;
    ProgressBar progressBar;

    public TeamProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_team_profile, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progress);
       /* TeamProfileModel[] myListData = new TeamProfileModel[]{
                new TeamProfileModel("Great Mates", "Team members : 04"),
                new TeamProfileModel("On the Wire", "Team members : 03"),
                new TeamProfileModel("Virtual Reality", "Team members : 06"),
                new TeamProfileModel("Quarter Life", "Team members : 10"),
        };
        */
     //   TeamprofileAdapter adapter = new TeamprofileAdapter(getActivity(),Arrays.asList(myListData));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        AWSMobileClient.getInstance().getTokens(new com.amazonaws.mobile.client.Callback<Tokens>() {
            @Override
            public void onResult(Tokens result) {
                Token = result.getIdToken().getTokenString();
                Log.d("Token=", Token);
                fetchTeamName();
            }
            @Override
            public void onError(Exception e) {

            }
        });
        return rootView;
    }

    private void fetchTeamName() {
        progressBar.setVisibility(View.VISIBLE);
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.atium.in/v1/clients/"+ SingletonSession.Instance().getScope() +"/teams").newBuilder();
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
                runOnUiThread(new Runnable() {
                                  @Override
                                  public void run() {
                                      progressBar.setVisibility(View.GONE);
                                  }
                              });

                Log.w("Sucess Response", response.toString());
                String mMessage = response.body().string();
                try {
                    JSONArray jsonArray=new JSONArray(mMessage);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        TeamProfileModel teamProfileModel=new TeamProfileModel();
                        teamProfileModel.setName(jsonObject.getString("name"));
                        JSONArray jsonUsers=jsonObject.getJSONArray("users");
                        teamProfileModel.setTeammembers(String.valueOf(jsonUsers.length()));
                        ArrayList<String>users=new ArrayList<String>();
                        for(int j=0; j<jsonUsers.length(); j++){
                            JSONObject jsonUserObject=jsonUsers.getJSONObject(j);
                            users.add(jsonUserObject.getString("name"));
                            teamProfileModel.setUsers(users);
                        }
                        list.add(teamProfileModel);
                        adapter=new TeamprofileAdapter(getActivity(),list);
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
}
