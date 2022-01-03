package com.example.locarv2.responses;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    public static final String BASE_URL = "https://serene-bastion-98138.herokuapp.com/api/";
    public static Retrofit retrofit = null;

    public static Retrofit getAPIClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        }
        return retrofit;
    }
}
