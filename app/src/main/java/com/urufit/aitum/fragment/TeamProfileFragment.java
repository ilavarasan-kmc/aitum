package com.urufit.aitum.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.urufit.aitum.R;
import com.urufit.aitum.adapter.PlayerprofileAdapter;
import com.urufit.aitum.adapter.TeamprofileAdapter;
import com.urufit.aitum.model.PlayerProfileModel;
import com.urufit.aitum.model.TeamProfileModel;

import java.util.Arrays;

public class TeamProfileFragment extends Fragment {
    RecyclerView recyclerView;
    public TeamProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_team_profile, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        TeamProfileModel[] myListData = new TeamProfileModel[]{
                new TeamProfileModel("Great Mates", "Team members : 04"),
                new TeamProfileModel("On the Wire", "Team members : 03"),
                new TeamProfileModel("Virtual Reality", "Team members : 06"),
                new TeamProfileModel("Quarter Life", "Team members : 10"),
        };
        TeamprofileAdapter adapter = new TeamprofileAdapter(getActivity(),Arrays.asList(myListData));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        return rootView;
    }
}
