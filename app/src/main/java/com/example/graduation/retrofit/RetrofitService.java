package com.example.graduation.retrofit;

import com.google.gson.Gson;

import java.net.CookieManager;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private Retrofit retrofit;
    public RetrofitService() {
        initializeRetrofit();
    }


    private void initializeRetrofit() {
        CookieManager cookieManager = new CookieManager();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient = builder
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.228.8:8080") //연결한 와이파이 보고 다시 입력해야함
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();

    }
    public Retrofit getRetrofit() {
        return retrofit;
    }
}