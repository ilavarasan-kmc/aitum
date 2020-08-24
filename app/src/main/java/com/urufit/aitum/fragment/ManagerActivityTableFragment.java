package com.urufit.aitum.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.urufit.aitum.R;
import com.urufit.aitum.databinding.FragActivityTableBinding;
import com.urufit.aitum.databinding.ItemviewAcvityTableBinding;
import com.urufit.aitum.model.ManagerActivityTableModel;
import com.urufit.aitum.model.UserTokenModel;

import java.io.Serializable;
import java.util.List;

public class ManagerActivityTableFragment  extends Fragment {
    
    FragActivityTableBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=DataBindingUtil.inflate(getLayoutInflater(),R.layout.frag_activity_table, container, false);
        ManagerActivityTableModel tableModel=new ManagerActivityTableModel();
        for(int i=0;i<=3;i++){
                tableModel.setName("Chola");
                tableModel.setCost("Free");
                tableModel.setDescription("welcome");
                tableModel.setDuration("2");
                renderOderList(tableModel);
        }
        return binding.getRoot();
    }

    private void renderOderList(ManagerActivityTableModel tableModel) {
        ItemviewAcvityTableBinding itemviewAcvityTableBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.itemview_acvity_table, null, false);
        itemviewAcvityTableBinding.setViewModel( tableModel);
        binding.viewOrderList.addView(itemviewAcvityTableBinding.getRoot());
    }

/*    private void renderOderList(List<UserTokenModel.Activity> tableModel) {
        ItemviewAcvityTableBinding itemviewAcvityTableBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.itemview_acvity_table, null, false);
        itemviewAcvityTableBinding.setViewModel((UserTokenModel.Activity) tableModel);
//        binding.viewOrderList.addView(itemviewAcvityTableBinding.getRoot());
    }*/

}
