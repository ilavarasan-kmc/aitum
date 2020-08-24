package com.urufit.aitum.model;

public class ScheduleModel {

        private String description;
        private String id;
        public ScheduleModel(String name,String id) {
            this.description = name;
            this.id = id;
        }

    public String getName() {
        return description;
    }

    public void setName(String name) {
        this.description = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
