package com.free.hdvideodownloaderapp.videodownloader;

public class Log {

    public static void e(String TAG, String msg, Throwable tr) {
        if (BuildConfig.DEBUG)
            android.util.Log.e(TAG, msg, tr);
    }

    public static void e(String TAG, String msg) {
        if (BuildConfig.DEBUG)
            android.util.Log.e(TAG, msg);
    }

    public static void v(String TAG, String msg, Throwable tr) {
        if (BuildConfig.DEBUG)
            android.util.Log.v(TAG, msg, tr);
    }

    public static void v(String TAG, String msg) {
        if (BuildConfig.DEBUG)
            android.util.Log.v(TAG, msg);
    }
    public static void w(String TAG, String msg, Throwable tr) {
        if (BuildConfig.DEBUG)
            android.util.Log.w(TAG, msg, tr);
    }

    public static void w(String TAG, String msg) {
        if (BuildConfig.DEBUG)
            android.util.Log.w(TAG, msg);
    }

    public static void d(String TAG, String msg, Throwable tr) {
        if (BuildConfig.DEBUG)
            android.util.Log.d(TAG, msg, tr);
    }

    public static void d(String TAG, String msg) {
        if (BuildConfig.DEBUG)
            android.util.Log.d(TAG, msg);
    }
    public static void i(String TAG, String msg, Throwable tr) {
        if (BuildConfig.DEBUG)
            android.util.Log.i(TAG, msg, tr);
    }

    public static void i(String TAG, String msg) {
        if (BuildConfig.DEBUG)
            android.util.Log.i(TAG, msg);
    }
}
