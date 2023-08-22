package com.sdk.ads.client;

import com.sdk.ads.ResModel.MainResModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {
    @GET("getApps/dev/video_download_bc/WCE465B7/getData.json")
    Call<MainResModel> doGetListData();

    @GET("getApps/dev/video_download_bc/WCE465B7/getData2.json")
    Call<MainResModel> doGetListData2();

    @GET("getApps/dev/video_download_bc/WCE465B7/getData.json")
    Call<MainResModel> doGetListData_Def();

}
