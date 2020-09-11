package com.urufit.aitum.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.urufit.aitum.fragment.DataCollectionFragment;
import com.urufit.aitum.fragment.PlayerProfileFragment;
import com.urufit.aitum.fragment.ProtocolFragment;
import com.urufit.aitum.fragment.ResourceFragment;
import com.urufit.aitum.fragment.TeamProfileFragment;

public class PhysicalTestTabAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;

    public PhysicalTestTabAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                DataCollectionFragment dataCollectionFragment = new DataCollectionFragment();
                return dataCollectionFragment;
            case 1:
                ProtocolFragment protocolFragment=new ProtocolFragment();
                return protocolFragment;

            case 2:
                ResourceFragment resourceFragment=new ResourceFragment();
                return resourceFragment;

            default:
                return null;
        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}