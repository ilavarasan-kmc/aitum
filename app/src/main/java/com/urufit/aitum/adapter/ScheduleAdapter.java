package com.urufit.aitum.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.urufit.aitum.R;
import com.urufit.aitum.activity.Welness_test;
import com.urufit.aitum.model.ScheduleModel;
import com.urufit.aitum.model.TodoModel;

import java.util.ArrayList;
import java.util.List;


public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder>{
    private List<String> listdata;
    Context context;
    int index = -1;

    // RecyclerView recyclerView;
    public ScheduleAdapter(List<String> listdata) {
        this.listdata = listdata;
    }

    public ScheduleAdapter(Context applicationContext, ArrayList<String> teamNameList) {

        this.context = applicationContext;
        this.listdata = teamNameList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.itemview_schedule_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }
    private int selectedItem = 0;
    private int lastSelected = 0;
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textName.setText(listdata.get(position));
        holder.textName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_survey = new Intent(context, Welness_test.class);
                i_survey.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context. startActivity(i_survey);
            }
        });
    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textName;
        public ImageView  imgCompleteStatus;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.textName = (TextView) itemView.findViewById(R.id.textView);
            this.imgCompleteStatus = (ImageView) itemView.findViewById(R.id.img_complete_status);
            this.relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relativeLayout);
        }
    }
}
