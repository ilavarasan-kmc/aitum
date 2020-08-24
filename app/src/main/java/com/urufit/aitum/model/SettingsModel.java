package com.urufit.aitum.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class SettingsModel extends BaseObservable {
    public SettingsModel() {
    }
    @Bindable
    private String name;
    @Bindable
    private String dob;
    @Bindable
    private String email;
    @Bindable
    private String address;
    @Bindable
    private String primary_mobile;
    @Bindable
    private String secondary_mobile;
    @Bindable
    private String city;
    @Bindable
    private String state;
    @Bindable
    private String country;
    @Bindable
    private String pincode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrimary_mobile() {
        return primary_mobile;
    }

    public void setPrimary_mobile(String primary_mobile) {
        this.primary_mobile = primary_mobile;
    }

    public String getSecondary_mobile() {
        return secondary_mobile;
    }

    public void setSecondary_mobile(String secondary_mobile) {
        this.secondary_mobile = secondary_mobile;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}
