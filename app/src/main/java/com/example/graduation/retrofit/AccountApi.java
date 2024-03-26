package com.example.graduation.retrofit;

import com.example.graduation.model.Account;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AccountApi {

    @GET("/account/get-all")
    Call<List<Account>> getAllUser1();

    @POST("/account/save")
    Call<Account> save1(@Body Account account);
}