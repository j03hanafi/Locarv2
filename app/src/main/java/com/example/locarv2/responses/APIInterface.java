package com.example.locarv2.responses;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {

    @FormUrlEncoded
    @POST("login")
    Call<UserCredentials> user_login (
            @Field("phone") String phone,
            @Field("token") String token
    );
}
