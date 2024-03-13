package com.example.graduation.retrofit;

import com.example.graduation.model.User;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.Call;

public interface UserApi {

    @GET("/user/get-all")
    Call<List<User>> getAllUers();

    @POST("/user/save")
    Call<User> save(@Body User user);
}