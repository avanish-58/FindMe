package com.example.findme;

import com.google.firebase.firestore.auth.User;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface Detectinterface {

    @GET("/fc/tags/save")
    Call<Savereceiver> savePosts(
            @Query("api_key") String api_key,
            @Query("api_secret") String api_secret,
            @Query("uid") String uid,
            @Query("tids") String tid

    );



}
