package com.urufit.aitum.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.urufit.aitum.R;
import com.urufit.aitum.adapter.TodoAdapter;
import com.urufit.aitum.model.TodoModel;
import com.urufit.aitum.ui.Toolbar_customs;

import java.util.Arrays;

public class TodoActivity extends Toolbar_customs {

    public Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        toolbar = findViewById(R.id.toolbar_customs);
        toolbar.setTitle("Todo");
        setSupportActionBar(toolbar);

        TodoModel[] myListData = new TodoModel[] {
                new TodoModel("Daily Update Questinnaire"),
                new TodoModel("Take Test"),
                new TodoModel("Medical Checkup")
        };

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        TodoAdapter adapter = new TodoAdapter(Arrays.asList(myListData),getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
