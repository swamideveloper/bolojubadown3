package com.free.hdvideodownloaderapp.videodownloader.in_ex;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Speed_Ex_Clinet {

    public static final String BASE_URL = "http://ip-api.com/";

    public static Speed_Ex_Serc getGeoApiService() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Speed_Ex_Serc.class);
    }

}
