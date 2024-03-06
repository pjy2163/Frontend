package com.example.graduation.retrofit;

import com.google.gson.Gson;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private Retrofit retrofit;
    public RetrofitService() {
        initializeRetrofit();
    }
    private void initializeRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http:// 172.20.5.52.225:8080")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();

    }
    public Retrofit getRetrofit() {
        return retrofit;
    }
}
