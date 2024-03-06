package com.example.graduation.model;

public class User {
    private String id; // 이게 id
    private String password;

    public void setId(String id) {
        this.id = id; //이메일 형식의 아이디이므로 id라 지칭함.
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", password='" + password +
                '}';
    }
}

