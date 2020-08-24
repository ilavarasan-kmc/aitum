package com.urufit.aitum.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.urufit.aitum.R;
import com.urufit.aitum.activity.PlayerProfileActivity;
import com.urufit.aitum.model.PlayerProfileModel;
import com.urufit.aitum.model.ScheduleModel;

import java.util.List;


public class PlayerprofileAdapter extends RecyclerView.Adapter<PlayerprofileAdapter.ViewHolder>{
    private List<PlayerProfileModel> listdata;
    private Context mContext;

    // RecyclerView recyclerView;
    public PlayerprofileAdapter(Context context,List<PlayerProfileModel> listdata ) {
        this.mContext = context;
        this.listdata = listdata;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.item_view_player_profile, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final PlayerProfileModel myListData = listdata.get(position);
        holder.textName.setText(myListData.getName());
        holder.txt_desc.setText(myListData.getDescription());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, PlayerProfileActivity.class);
                intent.putExtra("name",myListData.getName());
                intent.putExtra("email",myListData.getDescription());
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textName,txt_desc;
        public RelativeLayout relativeLayout;
        public CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            this.textName = (TextView) itemView.findViewById(R.id.title);
            this.txt_desc = (TextView) itemView.findViewById(R.id.description);
            this.cardView = (CardView) itemView.findViewById(R.id.cardView);
        }
    }
}
