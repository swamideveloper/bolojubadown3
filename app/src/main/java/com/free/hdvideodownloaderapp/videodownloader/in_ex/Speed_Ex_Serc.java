package com.free.hdvideodownloaderapp.videodownloader.in_ex;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Speed_Ex_Serc {
    @GET("json")
    Call<Speed_Ex_Res> getLocation();
}