package com.mymedicine;

import java.util.Calendar;

public class User {
    String name,email,gender,dob;
    String id,dp;

    public User(String name, String email, String gender, String id,String dob) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.id = id;
        this.dob= dob;
    }

    public User() {
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
