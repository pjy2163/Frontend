package com.example.graduation.retrofit;

import com.example.graduation.model.User;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserApi {
    @GET("/User/get-all")
    Call<List<User>> getAllUsers();

    @POST("/User/save")
    Call<User> save(@Body User user);
}
