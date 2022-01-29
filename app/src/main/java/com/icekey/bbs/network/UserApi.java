package com.icekey.bbs.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;

public interface UserApi {

    @GET("/getUser")
    Call<ResponseBody> getUser(@Field("userName") String userName);
}
