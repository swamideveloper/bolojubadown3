package com.sdk.ads.ads;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.sdk.ads.Comman;
import com.sdk.ads.LocalSave.ModelPrefrences;
import com.sdk.ads.ResModel.AdsUnit;
import com.sdk.ads.interstitialAds.ShowInterstitialAds;
import com.sdk.ads.interstitialAds.callback.setOnBackPressListener;

import java.util.ArrayList;

public class AllInterstitialAdPriorityTwo {

    private static Context mContext;
    private static InterCloseCallBack adsListener2;
    public static InterstitialAd AMInterstitial2;
    private static boolean aBoolean = false;
    public static int interTwoCounter = 0;
    public static ModelPrefrences prefrences1;
    public static ArrayList<AdsUnit> adsUnits1;
    static boolean isRequestTwo;
    private static AppOpenAd appOpenAdTwo = null;
    private static AppOpenAd.AppOpenAdLoadCallback loadCallbackTwo;
    private static boolean isShowingAdTwo = false;
    private static boolean isFirstInterAdsTwo;

    public static void LoadInterstitialAd(Context context) {
        try {
            prefrences1 = new ModelPrefrences(context);
            mContext = context;
            if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getAds().equalsIgnoreCase("on")) {
                adsUnits1 = (ArrayList<AdsUnit>) Comman.mainResModel.getData().getAdsUnits();
                if (Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("off") && Comman.mainResModel.getData().getExtraFields().getAlternetAppOpen().equalsIgnoreCase("off") && Comman.mainResModel.getData().getExtraFields().getClickAppOpen().equalsIgnoreCase("on") && Comman.mainResModel.getData().getExtraFields().getBackAppOpen().equalsIgnoreCase("on")) {
                    appOpenLoad(context);
                } else if (Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("off") && Comman.mainResModel.getData().getExtraFields().getAlternetAppOpen().equalsIgnoreCase("off") && Comman.mainResModel.getData().getExtraFields().getClickAppOpen().equalsIgnoreCase("off") && Comman.mainResModel.getData().getExtraFields().getBackAppOpen().equalsIgnoreCase("off")) {
                    CallAdmobInter();
                } else {
                    appOpenLoad(context);
                    CallAdmobInter();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void appOpenLoad(Context context) {
        if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getAds().equalsIgnoreCase("on")) {
            adsUnits1 = (ArrayList<AdsUnit>) Comman.mainResModel.getData().getAdsUnits();
            IntertitialAdsEvent.AppOpenKey = adsUnits1.get(0).getAppOpen();
            if (!isAppOpenAvailable() && !isRequestTwo) {

                if (!IntertitialAdsEvent.AppOpenKey.equalsIgnoreCase("")) {
                    isRequestTwo = true;
                    loadCallbackTwo = new AppOpenAd.AppOpenAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull AppOpenAd ad) {
                            appOpenAdTwo = ad;
                        }

                        @Override
                        public void onAdFailedToLoad(LoadAdError loadAdError) {
                            isRequestTwo = false;
                            appOpenAdTwo = null;
                        }

                    };

                    AppOpenAd.load(context, IntertitialAdsEvent.AppOpenKey, new AdRequest.Builder().build(),
                            AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallbackTwo);
                }
            }

        }


    }


    public static boolean isAppOpenAvailable() {
        return appOpenAdTwo != null;
    }


    private static void CallAdmobInter() {

        if (!isloaded() && !aBoolean) {
            aBoolean = true;
            IntertitialAdsEvent.InterstitialKey = adsUnits1.get(0).getInterFrequency();
            if (!IntertitialAdsEvent.InterstitialKey.equalsIgnoreCase("")) {
                try {
                    InterstitialAd.load(mContext, IntertitialAdsEvent.InterstitialKey, new AdRequest.Builder().build(), new InterstitialAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                            super.onAdLoaded(interstitialAd);
                            AMInterstitial2 = interstitialAd;
                            aBoolean = true;
                            AMCallBack();

                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                            super.onAdFailedToLoad(adError);
                            aBoolean = false;
                            AMInterstitial2 = null;

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static boolean isloaded() {
        return AMInterstitial2 != null;
    }

    private static void AMCallBack() {
        AMInterstitial2.setFullScreenContentCallback(new FullScreenContentCallback() {
            @Override
            public void onAdDismissedFullScreenContent() {
                adsListener2.onAdsClose();
                AMInterstitial2 = null;
                aBoolean = false;
                AllInterstitialAdPriorityTwo.LoadInterstitialAd(mContext);
                IntertitialAdsEvent.Strcheckad = "StrClosed";
            }

            @Override
            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                super.onAdFailedToShowFullScreenContent(adError);
                aBoolean = false;
                AMInterstitial2 = null;
                if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("on") && !isShowingAdTwo && isAppOpenAvailable() && isFirstInterAdsTwo) {
                } else {
                    adsListener2.onAdsClose();
                }

                AllInterstitialAdPriorityTwo.LoadInterstitialAd(mContext);
                IntertitialAdsEvent.Strcheckad = "StrClosed";
            }

            @Override
            public void onAdShowedFullScreenContent() {
                aBoolean = false;
                IntertitialAdsEvent.Strcheckad = "StrOpen";
            }
        });
    }


    public static void ShowInterstitialFrontAd(Context context, InterCloseCallBack interstitialAdsListener1) {
        mContext = context;
        adsListener2 = interstitialAdsListener1;
        if (Comman.mainResModel != null &&Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("off") && Comman.mainResModel.getData().getExtraFields().getAlternetAppOpen().equalsIgnoreCase("off") && Comman.mainResModel.getData().getExtraFields().getClickAppOpen().equalsIgnoreCase("on") && IntertitialAdsEvent.Strcheckad.equalsIgnoreCase("StrClosed") && !isShowingAdTwo && isAppOpenAvailable()) {
            FullScreenContentCallback fullScreenContentCallback =
                    new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            appOpenAdTwo = null;
                            isRequestTwo = false;
                            isShowingAdTwo = false;
                            IntertitialAdsEvent.Strcheckad = "StrClosed";
                            appOpenLoad(context);
                            adsListener2.onAdsClose();


                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            IntertitialAdsEvent.Strcheckad = "StrClosed";
                            adsListener2.onAdsClose();
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            IntertitialAdsEvent.Strcheckad = "StrOpen";
                            isShowingAdTwo = true;
                        }
                    };
            isFirstInterAdsTwo = false;
            appOpenAdTwo.setFullScreenContentCallback(fullScreenContentCallback);
            appOpenAdTwo.show((Activity) context);
            if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("on") && AMInterstitial2 != null && !isFirstInterAdsTwo) {
                AMInterstitial2.show((Activity) context);
            }
        } else if (AMInterstitial2 != null && IntertitialAdsEvent.Strcheckad.equals("StrClosed")) {
            isFirstInterAdsTwo = true;
            AMInterstitial2.show((Activity) context);
            if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("on") && !isShowingAdTwo && isAppOpenAvailable() && isFirstInterAdsTwo) {
                FullScreenContentCallback fullScreenContentCallback =
                        new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                appOpenAdTwo = null;
                                isRequestTwo = false;
                                isShowingAdTwo = false;
                                IntertitialAdsEvent.Strcheckad = "StrClosed";
                                appOpenLoad(mContext);

                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                IntertitialAdsEvent.Strcheckad = "StrClosed";
                                adsListener2.onAdsClose();
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                IntertitialAdsEvent.Strcheckad = "StrOpen";
                                isShowingAdTwo = true;
                            }
                        };
                appOpenAdTwo.setFullScreenContentCallback(fullScreenContentCallback);
                appOpenAdTwo.show((Activity) mContext);
            }


        } else {
        if (Comman.mainResModel!=null && Comman.mainResModel.getData().getInterAds().size()!=0) {

            isFirstInterAdsTwo = true;
            ShowInterstitialAds showInterstitialAds = new ShowInterstitialAds((Activity) context, new setOnBackPressListener.OnNoRecordFoundListener() {
                @Override
                public void onNoRecordFound() {
                    if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("on") && !isShowingAdTwo && isAppOpenAvailable() && isFirstInterAdsTwo) {

                    } else {
                        adsListener2.onAdsClose();
                    }
                }

                @Override
                public void onAdsClose() {
                    if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("on") && !isShowingAdTwo && isAppOpenAvailable() && isFirstInterAdsTwo) {

                    } else {
                        adsListener2.onAdsClose();
                    }
                }
            }, "01");
            showInterstitialAds.InterstitialAds();
            if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("on") && !isShowingAdTwo && isAppOpenAvailable() && isFirstInterAdsTwo) {
                FullScreenContentCallback fullScreenContentCallback =
                        new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                appOpenAdTwo = null;
                                isRequestTwo = false;
                                isShowingAdTwo = false;
                                IntertitialAdsEvent.Strcheckad = "StrClosed";
                                appOpenLoad(mContext);

                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                IntertitialAdsEvent.Strcheckad = "StrClosed";
                                adsListener2.onAdsClose();
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                IntertitialAdsEvent.Strcheckad = "StrOpen";
                                isShowingAdTwo = true;
                            }
                        };
                appOpenAdTwo.setFullScreenContentCallback(fullScreenContentCallback);
                appOpenAdTwo.show((Activity) mContext);
            }
        }else {
            adsListener2.onAdsClose();
        }
        }

    }

    public static void ShowInterstitialBackAd(Context context, InterCloseCallBack interstitialAdsListener1) {
        mContext = context;
        adsListener2 = interstitialAdsListener1;
        if (Comman.mainResModel != null &&Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("off") && Comman.mainResModel.getData().getExtraFields().getAlternetAppOpen().equalsIgnoreCase("off") && Comman.mainResModel.getData().getExtraFields().getBackAppOpen().equalsIgnoreCase("on") && IntertitialAdsEvent.Strcheckad.equalsIgnoreCase("StrClosed") && !isShowingAdTwo && isAppOpenAvailable()) {
            FullScreenContentCallback fullScreenContentCallback =
                    new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            appOpenAdTwo = null;
                            isRequestTwo = false;
                            isShowingAdTwo = false;
                            IntertitialAdsEvent.Strcheckad = "StrClosed";
                            appOpenLoad(context);
                            adsListener2.onAdsClose();

                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            IntertitialAdsEvent.Strcheckad = "StrClosed";
                            adsListener2.onAdsClose();
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            IntertitialAdsEvent.Strcheckad = "StrOpen";
                            isShowingAdTwo = true;
                        }
                    };
            appOpenAdTwo.setFullScreenContentCallback(fullScreenContentCallback);
            appOpenAdTwo.show((Activity) context);
            isFirstInterAdsTwo = false;
            if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("on") && AMInterstitial2 != null && !isFirstInterAdsTwo) {
                AMInterstitial2.show((Activity) context);
            }
        } else if (AMInterstitial2 != null && IntertitialAdsEvent.Strcheckad.equals("StrClosed")) {
            isFirstInterAdsTwo = true;
            AMInterstitial2.show((Activity) context);
            if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("on") && !isShowingAdTwo && isAppOpenAvailable() && isFirstInterAdsTwo) {
                FullScreenContentCallback fullScreenContentCallback =
                        new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                appOpenAdTwo = null;
                                isRequestTwo = false;
                                isShowingAdTwo = false;
                                IntertitialAdsEvent.Strcheckad = "StrClosed";
                                appOpenLoad(mContext);

                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                IntertitialAdsEvent.Strcheckad = "StrClosed";
                                adsListener2.onAdsClose();
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                IntertitialAdsEvent.Strcheckad = "StrOpen";
                                isShowingAdTwo = true;
                            }
                        };
                appOpenAdTwo.setFullScreenContentCallback(fullScreenContentCallback);
                appOpenAdTwo.show((Activity) mContext);
            }


        } else {
            if (Comman.mainResModel!=null && Comman.mainResModel.getData().getInterAds().size()!=0) {

                isFirstInterAdsTwo = true;
                ShowInterstitialAds showInterstitialAds = new ShowInterstitialAds((Activity) context, new setOnBackPressListener.OnNoRecordFoundListener() {
                    @Override
                    public void onNoRecordFound() {
                        if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("on") && !isShowingAdTwo && isAppOpenAvailable() && isFirstInterAdsTwo) {

                        } else {
                            adsListener2.onAdsClose();
                        }
                    }

                    @Override
                    public void onAdsClose() {
                        if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("on") && !isShowingAdTwo && isAppOpenAvailable() && isFirstInterAdsTwo) {

                        } else {
                            adsListener2.onAdsClose();
                        }
                    }
                }, "01");
                showInterstitialAds.InterstitialAds();
                if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("on") && !isShowingAdTwo && isAppOpenAvailable() && isFirstInterAdsTwo) {
                    FullScreenContentCallback fullScreenContentCallback =
                            new FullScreenContentCallback() {
                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    appOpenAdTwo = null;
                                    isRequestTwo = false;
                                    isShowingAdTwo = false;
                                    IntertitialAdsEvent.Strcheckad = "StrClosed";
                                    appOpenLoad(mContext);

                                }

                                @Override
                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                    IntertitialAdsEvent.Strcheckad = "StrClosed";
                                    adsListener2.onAdsClose();
                                }

                                @Override
                                public void onAdShowedFullScreenContent() {
                                    IntertitialAdsEvent.Strcheckad = "StrOpen";
                                    isShowingAdTwo = true;
                                }
                            };
                    appOpenAdTwo.setFullScreenContentCallback(fullScreenContentCallback);
                    appOpenAdTwo.show((Activity) mContext);
                }

            }else {
                adsListener2.onAdsClose();
            }
        }

    }

    public static void clearInstance() {
        AMInterstitial2 = null;
        aBoolean = false;
        isRequestTwo = false;
        isShowingAdTwo = false;
        appOpenAdTwo = null;
    }

}
