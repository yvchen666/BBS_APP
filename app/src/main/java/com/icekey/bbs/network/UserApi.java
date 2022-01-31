package com.icekey.bbs.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;

public interface UserApi {
    public String BaseUrl = "http://139.155.90.20:8080/";
    @GET("/bbs/getPost")
    Call<ResponseBody> getPost();
}
