package com.sdk.ads;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.sdk.ads.LocalSave.Common;
import com.sdk.ads.LocalSave.ModelPrefrences;
import com.sdk.ads.ResModel.Country;
import com.sdk.ads.ResModel.MainResModel;
import com.sdk.ads.ads.AppOpenManager;
import com.sdk.ads.ads.IntertitialAdsEvent;
import com.sdk.ads.ads.SplashBetaAds;
import com.sdk.ads.client.APIClient;
import com.sdk.ads.client.APIInterface;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SdkAppController extends Application implements Application.ActivityLifecycleCallbacks {

    private static SdkAppController mInstance;
    APIInterface apiInterface;
    ModelPrefrences modelPrefrences;
    public Activity currentActivity;

    public static SdkAppController getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        modelPrefrences = new ModelPrefrences(this);
        MobileAds.initialize(this, initializationStatus -> {
        });
        MobileAds.setRequestConfiguration(new RequestConfiguration.Builder().setTestDeviceIds(IntertitialAdsEvent.TestDeviceID).build());

        registerActivityLifecycleCallbacks(this);


    }


    public SharedPreferences getPrefs() {
        return getSharedPreferences(Common.SHARED_PREFS, Context.MODE_PRIVATE);
    }


    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {


    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        currentActivity = activity;
//        todo isDevMode()
//        openFileInput()

        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);


    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        currentActivity = activity;
//        if (isDevMode()) {
//            showDialog();
//        }
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
        currentActivity = activity;
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        currentActivity = activity;
    }

    public boolean isDevMode() {
        if (Integer.valueOf(android.os.Build.VERSION.SDK) == 16) {
            return android.provider.Settings.Secure.getInt(getApplicationContext().getContentResolver(),
                    android.provider.Settings.Secure.DEVELOPMENT_SETTINGS_ENABLED, 0) != 0;
        } else if (Integer.valueOf(android.os.Build.VERSION.SDK) >= 17) {
            return android.provider.Settings.Secure.getInt(getApplicationContext().getContentResolver(),
                    android.provider.Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0) != 0;
        } else return false;
    }

    public void showDialog() {

        final Dialog dialog = new Dialog(currentActivity);
        dialog.setContentView(com.sdk.ads.R.layout.debug_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        window.setAttributes(wlp);
        RelativeLayout rlCancel = dialog.findViewById(R.id.rlCancel);
        rlCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                currentActivity.finishAffinity();
                System.exit(1);
            }
        });
        RelativeLayout rlOff = dialog.findViewById(R.id.rlOff);
        rlOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                currentActivity.startActivityForResult(new Intent(android.provider.Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS), 1000);
            }
        });
        dialog.show();

    }
}
