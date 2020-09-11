package com.urufit.aitum.ui;

import java.util.ArrayList;

public class SingletonSession {

    private static SingletonSession instance;
    private String username;
    private String email;
    private String role;
    private String Scope;
    private String userId;
    private ArrayList<String>ScopeList;
    private ArrayList<String>RoleList;
    private SingletonSession() {}

    public static SingletonSession Instance()
    {
        //if no instance is initialized yet then create new instance
        //else return stored instance
        if (instance == null)
        {
            instance = new SingletonSession();
        }
        return instance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getScope() {
        return Scope;
    }

    public void setScope(String scope) {
        Scope = scope;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ArrayList<String> getScopeList() {
        return ScopeList;
    }

    public void setScopeList(ArrayList<String> scopeList) {
        ScopeList = scopeList;
    }

    public ArrayList<String> getRoleList() {
        return RoleList;
    }

    public void setRoleList(ArrayList<String> roleList) {
        RoleList = roleList;
    }
}
