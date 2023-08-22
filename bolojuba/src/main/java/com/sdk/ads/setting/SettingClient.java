package com.sdk.ads.setting;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SettingClient {

    public static String URL = "http://decodeking.com/";
    public static Retrofit retrofit = null;

    public static Retrofit getData() {

        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}