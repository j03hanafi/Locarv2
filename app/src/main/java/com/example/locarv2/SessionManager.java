package com.example.locarv2;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";
    public static final String USER_ID = "USER_ID";
    public static final String USER_TYPE = "USER_TYPE";
    public static final String USER_CRED = "USER_CRED";
    public static final String COUNTRY_CODE = "COUNTRY_CODE";
    public static final String SANCTUM_TOKEN = "SANCTUM_TOKEN";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();

    }

    public void createSession(String user_id, String user_type, String user_cred, String country_code, String sanctum_token) {

        editor.putBoolean(LOGIN, true);
        editor.putString(USER_ID, user_id);
        editor.putString(USER_TYPE, user_type);
        editor.putString(USER_CRED, user_cred);
        editor.putString(COUNTRY_CODE, country_code);
        editor.putString(SANCTUM_TOKEN, sanctum_token);
        editor.apply();
    }

    public boolean isLogin() {
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public HashMap<String, String> getUserDetails() {

        HashMap<String, String> user = new HashMap<>();
        user.put(USER_ID, sharedPreferences.getString(USER_ID, null));
        user.put(USER_TYPE, sharedPreferences.getString(USER_TYPE, null));
        user.put(USER_CRED, sharedPreferences.getString(USER_CRED, null));
        user.put(COUNTRY_CODE, sharedPreferences.getString(COUNTRY_CODE, null));
        user.put(SANCTUM_TOKEN, sharedPreferences.getString(SANCTUM_TOKEN, null));


        return user;
    }

}
