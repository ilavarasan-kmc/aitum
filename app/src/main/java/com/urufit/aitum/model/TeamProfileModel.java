package com.urufit.aitum.model;

import java.util.ArrayList;
import java.util.List;

public class TeamProfileModel {

        private String name;
        private String teammembers;
        private ArrayList<String> users = new ArrayList<>();

    public TeamProfileModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeammembers() {
        return teammembers;
    }

    public void setTeammembers(String teammembers) {
        this.teammembers = teammembers;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }
}
