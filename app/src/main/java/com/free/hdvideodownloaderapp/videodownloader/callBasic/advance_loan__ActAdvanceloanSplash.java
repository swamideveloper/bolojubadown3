package com.free.hdvideodownloaderapp.videodownloader.callBasic;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.free.hdvideodownloaderapp.videodownloader.Activity.free_Option_Activity;
import com.free.hdvideodownloaderapp.videodownloader.MykeepClass.SplashBaseActivity;
import com.free.hdvideodownloaderapp.videodownloader.R;
import com.free.hdvideodownloaderapp.videodownloader.mvvmbalaji.SelectLanguage_Activity;
import com.sdk.ads.Comman;
import com.sdk.ads.LocalSave.Common;
import com.sdk.ads.ads.SplashBetaAds;
import com.sdk.ads.ads.TransferClass;

public class advance_loan__ActAdvanceloanSplash extends SplashBaseActivity {

    public  static int int_Language=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.free_v_down_activity_splash);


    }


    @SuppressLint("SuspiciousIndentation")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (!isDevMode())
        getSetting();
    }


    public SharedPreferences getPrefs() {
        return getSharedPreferences(Common.SHARED_PREFS, MODE_PRIVATE);
    }

    public void moveToNext() {

        if (Common.isNetworkConnected(advance_loan__ActAdvanceloanSplash.this)) {
            try {
                SplashBetaAds.ShowInterstitialAd(this, () -> {
                    if (Comman.mainResModel.getData().getExtraFields().getExtraNewScreen().equalsIgnoreCase("on")) {
                        int_Language = 1;
                        Intent intent = new Intent(advance_loan__ActAdvanceloanSplash.this, SelectLanguage_Activity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        TransferClass.setToNextActivity(advance_loan__ActAdvanceloanSplash.this, free_Option_Activity.class);

                    }
                });
            } catch (Exception e) {
                SplashBetaAds.ShowInterstitialAd(this, () -> {
                    if (Comman.mainResModel.getData().getExtraFields().getExtraNewScreen().equalsIgnoreCase("on")) {
                        int_Language = 1;
                        Intent intent = new Intent(advance_loan__ActAdvanceloanSplash.this, SelectLanguage_Activity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        TransferClass.setToNextActivity(advance_loan__ActAdvanceloanSplash.this, free_Option_Activity.class);
                    }
                });
            }
        } else {
            Toast.makeText(this, "No Active Internet/WiFi Connection!", Toast.LENGTH_SHORT).show();
        }
    }
}