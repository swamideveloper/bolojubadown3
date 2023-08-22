package com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


import com.free.hdvideodownloaderapp.videodownloader.Video.MediaBean;

import java.util.List;

public class AppPref {
    private static final String LAST_BRIGHTNESS = "last_brightness";
    private static final String LAST_SPEED = "last_speed";
    private static final String LOCK = "lock";
    private static final String SORT_ORDER = "sort_order";
    private static final String THEME = "theme";
    private static final String VIEW_TYPE = "view_type";
    public static List<MediaBean> arrayList = null;
    private static AppPref sInstance = null;
    private static String size = "size";
    private static String start = "start";
    private final SharedPreferences mPreferences;

    private AppPref(Context context) {
        this.mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static AppPref getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new AppPref(context.getApplicationContext());
        }
        return sInstance;
    }

    public void saveLastBrightness(float f) {
        this.mPreferences.edit().putFloat(LAST_BRIGHTNESS, f).commit();
    }

    public float getLastBrightness() {
        return this.mPreferences.getFloat(LAST_BRIGHTNESS, 0.5f);
    }

    public void saveLastSpeed(float f) {
        this.mPreferences.edit().putFloat(LAST_SPEED, f).commit();
    }

    public float getLastSpeed() {
        return this.mPreferences.getFloat(LAST_SPEED, 1.0f);
    }

    public void setLock(Boolean bool) {
        this.mPreferences.edit().putBoolean(LOCK, bool.booleanValue()).commit();
    }

    public static boolean getStart(Context context) {
        return context.getSharedPreferences(context.getPackageName(), 0).getBoolean(start, true);
    }

    public static void setStart(Context context, boolean z) {
        context.getSharedPreferences(context.getPackageName(), 0).edit().putBoolean(start, z).commit();
    }

    public static boolean getSize(Context context) {
        return context.getSharedPreferences(context.getPackageName(), 0).getBoolean(size, true);
    }

    public static void setSize(Context context, boolean z) {
        context.getSharedPreferences(context.getPackageName(), 0).edit().putBoolean(size, z).commit();
    }
}
