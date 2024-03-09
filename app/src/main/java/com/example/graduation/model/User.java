package com.example.graduation.model;

public class User {
    private String name;
    private String id; // 이게 id
    private String password;

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id; //이메일 형식의 아이디이므로 id라 지칭함.
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "User{" + "name='" + name + '\'' +
                "id='" + id + '\'' +
                ", password='" + password +
                '}';
    }
}

