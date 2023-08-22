package com.sdk.ads.LocalSave;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;

public class Common {
//    public static final String BASE_CARRIER_ID = "mbi_videodownloader15";
    public static final String BASE_HOST = "https://d2isj403unfbyl.cloudfront.net";
    public static final String STORED_CARRIER_ID_KEY = "com.northghost.afvclient.CARRIER_ID_KEY";
    public static final String STORED_HOST_URL_KEY = "com.northghost.afvclient.STORED_HOST_KEY";
    public static final String SHARED_PREFS = "NORTHGHOST_SHAREDPREFS";
    public static final String Base_image = "http://datastorage.prelax.in:547/datastore01/VideoCall/FakeGirlData/thumbnail/";
    public static final String Base_url = "http://datastorage.prelax.in:547/datastore01/VideoCall/FakeGirlData/video/";

    @SuppressLint("MissingPermission")
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    }