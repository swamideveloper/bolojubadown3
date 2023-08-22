package com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_SCUtils;

import android.content.Context;
import android.content.SharedPreferences;

public class WAV_Pref {

    public String USER_PREFS = "USER PREFS";
    public SharedPreferences.Editor prefEditor;
    public SharedPreferences appSharedPref;

    String WhatsApp_Uri = "WhatsApp_Uri";

    public WAV_Pref(Context context) {
        this.appSharedPref = context.getSharedPreferences(this.USER_PREFS, 0);
        this.prefEditor = this.appSharedPref.edit();
    }

    public String get_WhatsApp_Uri() {
        return this.appSharedPref.getString(this.WhatsApp_Uri, "");
    }

    public void set_WhatsApp_Uri(String str) {
        this.prefEditor.putString(this.WhatsApp_Uri, str).commit();
    }

}
