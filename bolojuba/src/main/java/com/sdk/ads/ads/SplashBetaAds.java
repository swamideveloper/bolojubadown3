package com.sdk.ads.ads;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.sdk.ads.Comman;
import com.sdk.ads.LocalSave.ModelPrefrences;
import com.sdk.ads.ResModel.AdsUnit;
import com.sdk.ads.SdkAppController;

import java.util.ArrayList;

public class SplashBetaAds {
    public static String USER_PREFS = "Purchase PREFS";
    public static SharedPreferences appSharedPref;
    private static Context mContext;
    private static InterCloseCallBack adsListener1;
    private static AppOpenAd openAd;
    private static boolean isShowingAd = false;
    static ArrayList<AdsUnit> adsUnits;
    static ModelPrefrences modelPrefrences;
    public static int splashBetaCounter, SplashInterCounter;
    private static InterstitialAd SplashAMInterstitial;


    public static void ShowInterstitialAd(Context context, InterCloseCallBack interstitialAdsListener1) {
        mContext = context;

        adsListener1 = interstitialAdsListener1;
        appSharedPref = mContext.getSharedPreferences(USER_PREFS, Activity.MODE_PRIVATE);
        modelPrefrences = new ModelPrefrences(context);
        if (!appSharedPref.getBoolean("Purchase", false)) {
            if (Comman.mainResModel!=null&&Comman.mainResModel.getData().getExtraFields().getAds().equalsIgnoreCase("on")) {
                adsUnits = (ArrayList<AdsUnit>) Comman.mainResModel.getData().getAdsUnits();
                IntertitialAdsEvent.AppOpenKey = adsUnits.get(0).getAppOpen();
                if (!IntertitialAdsEvent.AppOpenKey.equalsIgnoreCase("")) {
                    if (Comman.mainResModel!=null && Comman.mainResModel.getData().getExtraFields().getAnytime_ads().equalsIgnoreCase("on")){
                        IntertitialAdsEvent.CallInterstitial(SdkAppController.getInstance());
                        new AppOpenManager(SdkAppController.getInstance(),false);
                        adsListener1.onAdsClose();
                        IntertitialAdsEvent.Strcheckad = "StrClosed";
                    }
                        try {
                            AppOpenAd.AppOpenAdLoadCallback loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
                                @Override
                                public void onAdLoaded(AppOpenAd ad) {
                                    openAd = ad;
                                    IntertitialAdsEvent.CallInterstitial(SdkAppController.getInstance());
                                    new AppOpenManager(SdkAppController.getInstance(), false);
                                    if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getAnytime_ads().equalsIgnoreCase("on")) {
                                        FullScreenContentCallback fullScreenContentCallback = new FullScreenContentCallback() {
                                            @Override
                                            public void onAdDismissedFullScreenContent() {
                                                openAd = null;
                                                isShowingAd = false;
                                                IntertitialAdsEvent.Strcheckad = "StrClosed";
                                            }

                                            @Override
                                            public void onAdFailedToShowFullScreenContent(com.google.android.gms.ads.AdError adError) {
                                                IntertitialAdsEvent.Strcheckad = "StrClosed";
                                            }

                                            @Override
                                            public void onAdShowedFullScreenContent() {
                                                isShowingAd = true;
                                                IntertitialAdsEvent.Strcheckad = "StrOpen";
                                            }
                                        };
                                        openAd.setFullScreenContentCallback(fullScreenContentCallback);
                                        openAd.show(SdkAppController.getInstance().currentActivity);
                                    } else {
                                        FullScreenContentCallback fullScreenContentCallback = new FullScreenContentCallback() {
                                            @Override
                                            public void onAdDismissedFullScreenContent() {
                                                openAd = null;
                                                isShowingAd = false;
                                                adsListener1.onAdsClose();
                                                IntertitialAdsEvent.Strcheckad = "StrClosed";
                                            }

                                            @Override
                                            public void onAdFailedToShowFullScreenContent(com.google.android.gms.ads.AdError adError) {
                                                adsListener1.onAdsClose();
                                                IntertitialAdsEvent.Strcheckad = "StrClosed";
                                            }

                                            @Override
                                            public void onAdShowedFullScreenContent() {
                                                isShowingAd = true;
                                                IntertitialAdsEvent.Strcheckad = "StrOpen";
                                            }
                                        };
                                        openAd.setFullScreenContentCallback(fullScreenContentCallback);
                                        openAd.show((Activity) mContext);
                                    }

                                }

                                @Override
                                public void onAdFailedToLoad(LoadAdError loadAdError) {
                                    openAd = null;
                                    isShowingAd = false;
                                    if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getAnytime_ads().equalsIgnoreCase("off")) {
                                        IntertitialAdsEvent.SplashInterstitialKey = adsUnits.get(0).getSplashInter();
                                        if (!IntertitialAdsEvent.SplashInterstitialKey.equalsIgnoreCase("")) {
                                            try {
                                                InterstitialAd.load(mContext, IntertitialAdsEvent.SplashInterstitialKey, new AdRequest.Builder().build(), new InterstitialAdLoadCallback() {
                                                    @Override
                                                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                                                        super.onAdLoaded(interstitialAd);
                                                        IntertitialAdsEvent.CallInterstitial(SdkAppController.getInstance());
                                                        new AppOpenManager(SdkAppController.getInstance(), false);
                                                        ;
                                                        SplashAMInterstitial = interstitialAd;
                                                        SplashAMInterstitial.setFullScreenContentCallback(new FullScreenContentCallback() {
                                                            @Override
                                                            public void onAdDismissedFullScreenContent() {
                                                                adsListener1.onAdsClose();
                                                                SplashAMInterstitial = null;
                                                                IntertitialAdsEvent.Strcheckad = "StrClosed";
                                                            }

                                                            @Override
                                                            public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                                                                super.onAdFailedToShowFullScreenContent(adError);
                                                                SplashAMInterstitial = null;
                                                                adsListener1.onAdsClose();
                                                                IntertitialAdsEvent.Strcheckad = "StrClosed";
                                                            }

                                                            @Override
                                                            public void onAdShowedFullScreenContent() {
                                                                IntertitialAdsEvent.Strcheckad = "StrOpen";
                                                            }
                                                        });
                                                        SplashAMInterstitial.show((Activity) context);
                                                    }

                                                    @Override
                                                    public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                                                        super.onAdFailedToLoad(adError);
                                                        IntertitialAdsEvent.CallInterstitial(SdkAppController.getInstance());
                                                        new AppOpenManager(SdkAppController.getInstance(), false);
                                                        SplashAMInterstitial = null;
                                                        adsListener1.onAdsClose();
                                                        IntertitialAdsEvent.Strcheckad = "StrClosed";

                                                    }
                                                });
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        } else {

                                            IntertitialAdsEvent.CallInterstitial(SdkAppController.getInstance());
                                            new AppOpenManager(SdkAppController.getInstance(), false);
                                            adsListener1.onAdsClose();
                                            IntertitialAdsEvent.Strcheckad = "StrClosed";
                                        }
                                    }


                                }
                            };

                            final AdRequest adRequest = new AdRequest.Builder().build();
                            AppOpenAd.load(mContext, IntertitialAdsEvent.AppOpenKey, adRequest, AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                } else {

                    IntertitialAdsEvent.SplashInterstitialKey = adsUnits.get(0).getSplashInter();

                    if (!IntertitialAdsEvent.SplashInterstitialKey.equalsIgnoreCase("")) {
                        try {
                            InterstitialAd.load(mContext, IntertitialAdsEvent.SplashInterstitialKey, new AdRequest.Builder().build(), new InterstitialAdLoadCallback() {
                                @Override
                                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                                    super.onAdLoaded(interstitialAd);

                                    IntertitialAdsEvent.CallInterstitial(SdkAppController.getInstance());
                                    new AppOpenManager(SdkAppController.getInstance(),false);
                                    SplashAMInterstitial = interstitialAd;
                                    SplashAMInterstitial.setFullScreenContentCallback(new FullScreenContentCallback() {
                                        @Override
                                        public void onAdDismissedFullScreenContent() {
                                            adsListener1.onAdsClose();
                                            SplashAMInterstitial = null;
                                            IntertitialAdsEvent.Strcheckad = "StrClosed";
                                        }

                                        @Override
                                        public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                                            super.onAdFailedToShowFullScreenContent(adError);
                                            SplashAMInterstitial = null;
                                            adsListener1.onAdsClose();
                                            IntertitialAdsEvent.Strcheckad = "StrClosed";
                                        }

                                        @Override
                                        public void onAdShowedFullScreenContent() {
                                            IntertitialAdsEvent.Strcheckad = "StrOpen";
                                        }
                                    });
                                    SplashAMInterstitial.show((Activity) context);
                                }

                                @Override
                                public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                                    super.onAdFailedToLoad(adError);
                                    IntertitialAdsEvent.CallInterstitial(SdkAppController.getInstance());
                                    new AppOpenManager(SdkAppController.getInstance(),false);
                                    SplashAMInterstitial = null;
                                    adsListener1.onAdsClose();
                                    IntertitialAdsEvent.Strcheckad = "StrClosed";

                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {

                        IntertitialAdsEvent.CallInterstitial(SdkAppController.getInstance());
                        new AppOpenManager(SdkAppController.getInstance(),false);
                        adsListener1.onAdsClose();
                        IntertitialAdsEvent.Strcheckad = "StrClosed";
                    }
                }
            } else {

                IntertitialAdsEvent.CallInterstitial(SdkAppController.getInstance());
                new AppOpenManager(SdkAppController.getInstance(),false);
                adsListener1.onAdsClose();
                IntertitialAdsEvent.Strcheckad = "StrClosed";
            }

        } else {

            IntertitialAdsEvent.CallInterstitial(SdkAppController.getInstance());
            new AppOpenManager(SdkAppController.getInstance(),false);
            adsListener1.onAdsClose();
            IntertitialAdsEvent.Strcheckad = "StrClosed";
        }

    }


}
