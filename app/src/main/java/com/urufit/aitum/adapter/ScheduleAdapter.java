package com.urufit.aitum.adapter;

import android.content.Context;
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
    //    final String myListData = listdata.get(position);
        holder.textName.setText(listdata.get(position));

       /* holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index = position;
                notifyDataSetChanged();
            }
        });

        if(index==position){
            holder.relativeLayout.setBackgroundColor(Color.parseColor("#616161"));
            holder.textName.setTextColor(Color.parseColor("#FFFFFF"));
        }else{
            holder.relativeLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.textName.setTextColor(Color.parseColor("#000000"));
        }*/

        /*int backgroundColor = (position == selectedItem) ? R.color.green : R.color.white;

        holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(context, backgroundColor));

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Save the position of the last selected item
                lastSelected = selectedItem;
                //Save the position of the current selected item
                selectedItem = position;

                //This update the last item selected
                notifyItemChanged(lastSelected);

                //This update the item selected
                notifyItemChanged(selectedItem);
            }
        });*/
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
