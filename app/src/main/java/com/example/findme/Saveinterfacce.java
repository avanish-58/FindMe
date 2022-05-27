package com.example.findme;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Saveinterfacce {
    @GET("/fc/tags/save")
    Call<Savereceiver> savePosts(
            @Query("api_key") String api_key,
            @Query("api_secret") String api_secret,
            @Query("uid") String uid,
            @Query("tids") String tid

    );
    @GET("/fc/faces/train")
    Call<trainreceiver> trainimage(
            @Query("api_key") String api_key,
            @Query("api_secret") String api_secret,
            @Query("uids") String uid


    );
}
