package com.sdk.ads.ads;


import android.content.Context;
import android.content.SharedPreferences;

import java.util.Arrays;
import java.util.List;

public class AppPreference {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public AppPreference(Context context) {
        sharedPreferences = context.getSharedPreferences("AdsKeyStore", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    String appTitle = "appTitle";

    String DefaultappPer = "DefaultappPer";
    String Mainscreen_nativebanner_ads = "Mainscreen_nativebanner_ads";


    public String getAppTitle() {
        return sharedPreferences.getString(appTitle, "Caller Screen");
    }

    public void setAppTitle(String appTitleg) {
        editor.putString(appTitle, appTitleg).apply();
    }


    public boolean getDefaultappPer() {
        return sharedPreferences.getBoolean(DefaultappPer, false);
    }

    public void setDefaultappPer(boolean DefaultappPere) {
        editor.putBoolean(DefaultappPer, DefaultappPere).apply();
    }


    public String getMainscreennativebannerads() {
        return sharedPreferences.getString(Mainscreen_nativebanner_ads, "");
    }


}
