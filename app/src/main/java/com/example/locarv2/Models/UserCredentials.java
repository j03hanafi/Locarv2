package com.example.locarv2.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserCredentials {

    @SerializedName("user")
    @Expose
    private Users user;
    @SerializedName("token")
    @Expose
    private String token;

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
