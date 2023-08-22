package com.free.hdvideodownloaderapp.videodownloader.Def;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePref {
    public static String PREFERENCE = "AllInOneDownloader";


    private Context ctx;
    private SharedPreferences sharedPreferences;
    private static SharePref instance;

    public static String SESSIONID = "session_id";
    public static String USERID = "user_id";

    public SharePref(Context context) {
        ctx = context;
        sharedPreferences = context.getSharedPreferences(PREFERENCE, 0);
    }

    public static SharePref getInstance(Context ctx) {
        if (instance == null) {
            instance = new SharePref(ctx);
        }
        return instance;
    }

    public void putString(String key, String val) {
        sharedPreferences.edit().putString(key, val).apply();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, "");
    }

    public void putInt(String key, Integer val) {
        sharedPreferences.edit().putInt(key, val).apply();
    }

    public void putBoolean(String key, Boolean val) {
        sharedPreferences.edit().putBoolean(key, val).apply();
    }

    public Boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public int getInt(String key) {
        return sharedPreferences.getInt(key, 0);
    }

    public void clearSharePrefs() {
        sharedPreferences.edit().clear().apply();
    }
}
