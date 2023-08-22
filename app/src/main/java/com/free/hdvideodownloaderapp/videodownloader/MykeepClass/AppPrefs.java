package com.free.hdvideodownloaderapp.videodownloader.MykeepClass;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class AppPrefs {
    final String USER_PREFS = "video_call";
    SharedPreferences appSharedPref;
    SharedPreferences.Editor prefEditor;

    public boolean getFirstTimeLogin() {
        return appSharedPref.getBoolean("first_time_login", false);
    }

    public void setFirstTimeLogin(boolean firTime) {
        this.prefEditor.putBoolean("first_time_login", firTime).commit();
    }

    public String getAge() {
        return appSharedPref.getString("first_time_age", "");
    }

    public void setAge(String firage) {
        this.prefEditor.putString("first_time_age", firage).commit();
    }

    public String getUri() {
        return appSharedPref.getString("first_time_uri", "");
    }

    public void setUri(String firuri) {
        this.prefEditor.putString("first_time_uri", firuri).commit();
    }

    public String getName() {
        return appSharedPref.getString("first_time_name", "");
    }

    public void setName(String firname) {
        this.prefEditor.putString("first_time_name", firname).commit();
    }

    public boolean getPurchase() {
        return appSharedPref.getBoolean("purchase", false);
    }

    public void setPurchase(boolean firTime) {
        this.prefEditor.putBoolean("purchase", firTime).commit();
    }


    public String getLanguageint() {
        return appSharedPref.getString("PREF_LNG_int", "0");
    }

    public void setLanguageint(String firname) {
        this.prefEditor.putString("PREF_LNG_int", firname).commit();
    }

    public String getLanguageName() {
        return appSharedPref.getString("PREF_LNG_Name", "");
    }

    public void setLanguageName(String firname) {
        this.prefEditor.putString("PREF_LNG_Name", firname).commit();
    }

    public String getLanguageCode() {
        return appSharedPref.getString("PREF_LNG_CODE", "");
    }

    public void setLanguageCode(String firname) {
        this.prefEditor.putString("PREF_LNG_CODE", firname).commit();
    }

    public boolean getLanguage() {
        return appSharedPref.getBoolean("language", false);
    }

    public void setLanguage(boolean firTime) {
        this.prefEditor.putBoolean("language", firTime).commit();
    }

    public boolean getOnboarding() {
        return appSharedPref.getBoolean("onboarding", false);
    }

    public void setOnboarding(boolean firTime) {
        this.prefEditor.putBoolean("onboarding", firTime).commit();
    }


    public int getCallCount() {
        return appSharedPref.getInt("call_count", 5);
    }

    public void setCallCount(int firTime) {
        this.prefEditor.putInt("call_count", firTime).commit();
    }


    public int getCallNumber() {
        return appSharedPref.getInt("call_number", 1);
    }

    public void setCallNumber(int firTime) {
        this.prefEditor.putInt("call_number", firTime).commit();
    }


    public AppPrefs(Context context) {
        this.appSharedPref = context.getSharedPreferences(USER_PREFS, Activity.MODE_PRIVATE);
        this.prefEditor = appSharedPref.edit();
    }

}