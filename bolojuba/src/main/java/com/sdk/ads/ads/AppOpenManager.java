package com.sdk.ads.ads;

import static androidx.lifecycle.Lifecycle.Event.ON_START;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.sdk.ads.Comman;
import com.sdk.ads.LocalSave.ModelPrefrences;
import com.sdk.ads.ResModel.AdsUnit;
import com.sdk.ads.SdkAppController;

import java.util.ArrayList;

public class AppOpenManager implements LifecycleObserver {
    public static String USER_PREFS = "Purchase PREFS";
    public static SharedPreferences appSharedPref;
    private final SdkAppController myApplication;
    private static AppOpenAd appOpenAd = null;

    private static boolean isShowingAd = false;
    private static AppOpenAd.AppOpenAdLoadCallback loadCallback;
    static ModelPrefrences modelPrefrences;
    public static int openCounter;
    static ArrayList<AdsUnit> adsUnits;
    static boolean isRequest;

    public AppOpenManager(SdkAppController myApplication,boolean isBack) {
        if (!isBack) {
            this.myApplication = myApplication;
            // appOpenAd = null;
            appOpenAd = null;
            isRequest = false;
            isShowingAd = false;
            modelPrefrences = new ModelPrefrences(myApplication);
            ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
        }else {
            this.myApplication = myApplication;
            isBack=false;

        }
    }




    @OnLifecycleEvent(ON_START)
    public void onStart() {

        showAdIfAvailable();


    }

    public void fetchAd() {
        if (Comman.mainResModel!=null&&Comman.mainResModel.getData().getExtraFields().getAds().equalsIgnoreCase("on")) {
            adsUnits = (ArrayList<AdsUnit>) Comman.mainResModel.getData().getAdsUnits();
            IntertitialAdsEvent.AppOpenKey = adsUnits.get(0).getAppOpen();
            if (!isAdAvailable() && !isRequest) {
                if (!IntertitialAdsEvent.AppOpenKey.equalsIgnoreCase("")) {
                    isRequest = true;
                    loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull AppOpenAd ad) {
                            appOpenAd = ad;
                        }

                        @Override
                        public void onAdFailedToLoad(LoadAdError loadAdError) {
                            isRequest = false;
                            appOpenAd = null;
                        }

                    };

                    AppOpenAd.load(myApplication, IntertitialAdsEvent.AppOpenKey, new AdRequest.Builder().build(),
                            AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);
                }
            }

        }


    }

    private void showAdIfAvailable() {

        if (IntertitialAdsEvent.Strcheckad.equalsIgnoreCase("StrClosed") && !isShowingAd && isAdAvailable()) {

            FullScreenContentCallback fullScreenContentCallback =
                    new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            appOpenAd = null;
                            isRequest = false;
                            isShowingAd = false;

                            fetchAd();
                            IntertitialAdsEvent.Strcheckad = "StrClosed";
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            IntertitialAdsEvent.Strcheckad = "StrClosed";
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            IntertitialAdsEvent.Strcheckad = "StrOpen";
                            isShowingAd = true;
                        }
                    };
            appOpenAd.setFullScreenContentCallback(fullScreenContentCallback);
            appOpenAd.show(myApplication.currentActivity);

        } else {
            fetchAd();
        }
    }


    public void showAdIfAvailable(Context context, InterCloseCallBack closeCallBack) {

        if (IntertitialAdsEvent.Strcheckad.equalsIgnoreCase("StrClosed") && !isShowingAd && isAdAvailable()) {
            FullScreenContentCallback fullScreenContentCallback =
                    new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            appOpenAd = null;
                            isRequest = false;
                            isShowingAd = false;
                            fetchAd();
                            IntertitialAdsEvent.Strcheckad = "StrClosed";
                            closeCallBack.onAdsClose();
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            IntertitialAdsEvent.Strcheckad = "StrClosed";
                            closeCallBack.onAdsClose();
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            IntertitialAdsEvent.Strcheckad = "StrOpen";
                            isShowingAd = true;
                        }
                    };
            appOpenAd.setFullScreenContentCallback(fullScreenContentCallback);
            appOpenAd.show(myApplication.currentActivity);

        } else {
            fetchAd();
            closeCallBack.onAdsClose();
        }
    }

    public boolean isAdAvailable() {
        return appOpenAd != null;
    }


}