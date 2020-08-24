package com.urufit.aitum.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class ManagerActivityTableModel extends BaseObservable {

    @Bindable
    private  String name;
    @Bindable
    private  String description;
    @Bindable
    private  String duration;
    @Bindable
    private  String cost;

    public ManagerActivityTableModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
