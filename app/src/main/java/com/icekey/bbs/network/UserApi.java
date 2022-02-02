package com.icekey.bbs.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserApi {
    public String BaseUrl = "http://139.155.90.20:8080/";
    @GET("/bbs/getPost")
    Call<ResponseBody> getPost();

    @POST("/login")
    Call<ResponseBody> login(@Query("userName") String userName, @Query("passWord") String passWord);
}
