package com.urufit.aitum.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDetailsModel {

    @SerializedName("country")
    @Expose
    private String country;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public class Stats {

        @SerializedName("entry_at")
        @Expose
        private String entryAt;
        @SerializedName("height")
        @Expose
        private Integer height;
        @SerializedName("weight")
        @Expose
        private Integer weight;

        public String getEntryAt() {
            return entryAt;
        }

        public void setEntryAt(String entryAt) {
            this.entryAt = entryAt;
        }

        public Integer getHeight() {
            return height;
        }

        public void setHeight(Integer height) {
            this.height = height;
        }

        public Integer getWeight() {
            return weight;
        }

        public void setWeight(Integer weight) {
            this.weight = weight;
        }

        public class UserTokenModel {

            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("email")
            @Expose
            private String email;
            @SerializedName("dob")
            @Expose
            private String dob;
            @SerializedName("gender")
            @Expose
            private String gender;
            @SerializedName("created_at")
            @Expose
            private String createdAt;
            @SerializedName("address")
            @Expose
            private UserDetailsModel address;
            @SerializedName("primary_ph")
            @Expose
            private String primaryPh;
            @SerializedName("stats")
            @Expose
            private Stats stats;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getDob() {
                return dob;
            }

            public void setDob(String dob) {
                this.dob = dob;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            public UserDetailsModel getAddress() {
                return address;
            }

            public void setAddress(UserDetailsModel address) {
                this.address = address;
            }

            public String getPrimaryPh() {
                return primaryPh;
            }

            public void setPrimaryPh(String primaryPh) {
                this.primaryPh = primaryPh;
            }

            public Stats getStats() {
                return stats;
            }

            public void setStats(Stats stats) {
                this.stats = stats;
            }

        }
    }

}