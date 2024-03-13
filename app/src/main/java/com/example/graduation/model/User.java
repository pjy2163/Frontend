package com.example.graduation.model;

import com.google.gson.annotations.SerializedName;


public class User { // 회원가입 요청시 보낼 데이터
    private String id; // 이게 id
    private String password;
    //private String passwordCheck;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return id;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "User{" + "id='" + id + '\'' + "password='" + password +
                '}';
    }
}
