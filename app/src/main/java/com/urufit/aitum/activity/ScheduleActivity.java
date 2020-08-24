package com.urufit.aitum.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.urufit.aitum.R;
import com.urufit.aitum.adapter.ScheduleAdapter;
import com.urufit.aitum.adapter.TodoAdapter;
import com.urufit.aitum.model.ScheduleModel;
import com.urufit.aitum.model.TodoModel;
import com.urufit.aitum.ui.Toolbar_customs;

import java.util.Arrays;

public class ScheduleActivity extends Toolbar_customs {

    public Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        toolbar = findViewById(R.id.toolbar_customs);
        toolbar.setTitle("Todo");
        setSupportActionBar(toolbar);

        ScheduleModel[] myListData = new ScheduleModel[] {
                new ScheduleModel("WellBeing","1"),
                new ScheduleModel("Load Quantification","2"),
                new ScheduleModel("Kinanthropometry","3"),
                new ScheduleModel("Technical Assessment","4"),
                new ScheduleModel("Rehab Knee","5"),
                new ScheduleModel("Hamstring","6"),
        };

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        ScheduleAdapter adapter = new ScheduleAdapter(Arrays.asList(myListData));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
