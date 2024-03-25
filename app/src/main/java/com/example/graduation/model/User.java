package com.example.graduation.model;

import com.google.gson.annotations.SerializedName;


public class User { // 회원가입 요청시 보낼 데이터

    @SerializedName("id")
    private String id; // 이게 id
    @SerializedName("password")
    private String password;

    @SerializedName("name")
    private String name;



    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }


    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                '}';
    }


}