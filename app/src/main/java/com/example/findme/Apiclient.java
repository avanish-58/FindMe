package com.example.findme;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Apiclient {
    public static Retrofit getRetrofit(){
        HttpLoggingInterceptor httpLoggingInterceptor =new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient=new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.skybiometry.com")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        return retrofit;
    }
}
