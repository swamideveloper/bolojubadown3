package com.free.hdvideodownloaderapp.videodownloader.MykeepClass;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SettingInterface {

    @GET("getApps/dev/video_download_bc/WCE465B7/setting.json")
    Call<Setting> getSetting();
}