package com.urufit.aitum.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.urufit.aitum.R;
import com.urufit.aitum.model.UserTokenModel;
import com.urufit.aitum.ui.APIClient;
import com.urufit.aitum.ui.Api;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagerDashboardFragment extends Fragment {
    public static ManagerDashboardFragment newInstance() {
        return  new ManagerDashboardFragment();
    }

    Api apiInterface;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        apiInterface = APIClient.getClient().create(Api.class);
        fetch_dashboard();
        return inflater.inflate(R.layout.fragment_manager_dashboard, container, false);


    }

    private void fetch_dashboard() {

        apiInterface.getDash().enqueue(new Callback<ArrayList<UserTokenModel>>() {
            @Override
            public void onResponse(Call<ArrayList<UserTokenModel>> call, Response<ArrayList<UserTokenModel>> response) {

            }

            @Override
            public void onFailure(Call<ArrayList<UserTokenModel>> call, Throwable t) {

            }
        });
    }
}
