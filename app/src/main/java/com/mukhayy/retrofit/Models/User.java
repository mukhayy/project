package com.mukhayy.retrofit.Models;

public class User {
    private String Name;
    private String phone;

    public User() {
    }

    public User(String name, String phone) {
        Name = name;
        this.phone = phone;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
