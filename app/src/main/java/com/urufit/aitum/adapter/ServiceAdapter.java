package com.urufit.aitum.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.urufit.aitum.R;
import com.urufit.aitum.model.ServiceModel;

import java.util.List;


public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder>{
    private List<ServiceModel> listdata;

    // RecyclerView recyclerView;
    public ServiceAdapter(List<ServiceModel> listdata) {
        this.listdata = listdata;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.itemview_service, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ServiceModel myListData = listdata.get(position);
        holder.textName.setText(myListData.getName());
        holder.textDesc.setText(myListData.getDescription());
    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textName,textDesc;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.textName = (TextView) itemView.findViewById(R.id.txt_name);
            this.textDesc = (TextView) itemView.findViewById(R.id.text_desc);
        }
    }
}
