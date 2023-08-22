package com.free.hdvideodownloaderapp.videodownloader.MykeepClass;

import android.app.Activity;
import android.content.Context;

import com.sdk.ads.SdkAppController;

public class Aico_Controller extends SdkAppController {

    private static Context context;
    private static Aico_Controller mInstance;


    public Activity activity;
    public int width = 720;


    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        context = getApplicationContext();
    }

    public static synchronized Aico_Controller getInstance() {
        return mInstance;
    }
}
