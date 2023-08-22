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

public class AllInterstitialAdPriorityOne {

    private static Context mContext;
    private static InterCloseCallBack adsListener1;
    public static InterstitialAd AMInterstitial;
    private static boolean aBoolean = false;
    public static int interOneCounter = 0;
    public static ModelPrefrences prefrences;
    public static ArrayList<AdsUnit> adsUnits;
    static boolean isRequestOne;
    private static AppOpenAd appOpenAdOne = null;
    private static AppOpenAd.AppOpenAdLoadCallback loadCallbackOne;
    private static boolean isShowingAdOne = false;
    private static boolean isFirstInterAds;


    public static void LoadInterstitialAd(Context context) {
        try {
            mContext = context;
            if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getAds().equalsIgnoreCase("on")) {
                adsUnits = (ArrayList<AdsUnit>) Comman.mainResModel.getData().getAdsUnits();
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
            adsUnits = (ArrayList<AdsUnit>) Comman.mainResModel.getData().getAdsUnits();
            IntertitialAdsEvent.AppOpenKey = adsUnits.get(0).getAppOpen();
            if (!isAppOpenAvailable() && !isRequestOne) {

                if (!IntertitialAdsEvent.AppOpenKey.equalsIgnoreCase("")) {
                    isRequestOne = true;
                    loadCallbackOne = new AppOpenAd.AppOpenAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull AppOpenAd ad) {
                            appOpenAdOne = ad;
                        }

                        @Override
                        public void onAdFailedToLoad(LoadAdError loadAdError) {
                            isRequestOne = false;
                            appOpenAdOne = null;
                        }

                    };

                    AppOpenAd.load(context, IntertitialAdsEvent.AppOpenKey, new AdRequest.Builder().build(),
                            AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallbackOne);
                }
            }

        }


    }


    public static boolean isAppOpenAvailable() {
        return appOpenAdOne != null;
    }


    private static void CallAdmobInter() {

        if (!isloaded() && !aBoolean) {
            aBoolean = true;
            IntertitialAdsEvent.InterstitialKey = adsUnits.get(0).getInterFrequency();
            if (!IntertitialAdsEvent.InterstitialKey.equalsIgnoreCase("")) {
                try {
                    InterstitialAd.load(mContext, IntertitialAdsEvent.InterstitialKey, new AdRequest.Builder().build(), new InterstitialAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                            super.onAdLoaded(interstitialAd);
                            AMInterstitial = interstitialAd;
                            aBoolean = true;
                            AMCallBack();
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                            super.onAdFailedToLoad(adError);
                            aBoolean = false;
                            AMInterstitial = null;

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static boolean isloaded() {
        return AMInterstitial != null;
    }

    private static void AMCallBack() {

        AMInterstitial.setFullScreenContentCallback(new FullScreenContentCallback() {
            @Override
            public void onAdDismissedFullScreenContent() {
                AMInterstitial = null;
                aBoolean = false;
                if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("on") && !isShowingAdOne && isAppOpenAvailable() && isFirstInterAds) {

                } else {
                    adsListener1.onAdsClose();
                }
                IntertitialAdsEvent.Strcheckad = "StrClosed";
                AllInterstitialAdPriorityOne.LoadInterstitialAd(mContext);
            }

            @Override
            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                super.onAdFailedToShowFullScreenContent(adError);
                aBoolean = false;
                AMInterstitial = null;
                adsListener1.onAdsClose();
                AllInterstitialAdPriorityOne.LoadInterstitialAd(mContext);
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
        adsListener1 = interstitialAdsListener1;
        if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("off") && Comman.mainResModel.getData().getExtraFields().getAlternetAppOpen().equalsIgnoreCase("off") && Comman.mainResModel.getData().getExtraFields().getClickAppOpen().equalsIgnoreCase("on") && IntertitialAdsEvent.Strcheckad.equalsIgnoreCase("StrClosed") && !isShowingAdOne && isAppOpenAvailable()) {
            FullScreenContentCallback fullScreenContentCallback =
                    new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            appOpenAdOne = null;
                            isRequestOne = false;
                            isShowingAdOne = false;
                            IntertitialAdsEvent.Strcheckad = "StrClosed";
                            appOpenLoad(context);
                            adsListener1.onAdsClose();


                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            IntertitialAdsEvent.Strcheckad = "StrClosed";
                            adsListener1.onAdsClose();
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            IntertitialAdsEvent.Strcheckad = "StrOpen";
                            isShowingAdOne = true;
                        }
                    };
            isFirstInterAds = false;
            appOpenAdOne.setFullScreenContentCallback(fullScreenContentCallback);
            appOpenAdOne.show((Activity) context);
            if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("on") && AMInterstitial != null && !isFirstInterAds) {
                AMInterstitial.show((Activity) context);
            }


        } else if (AMInterstitial != null && IntertitialAdsEvent.Strcheckad.equals("StrClosed")) {
            isFirstInterAds = true;
            AMInterstitial.show((Activity) context);
            if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("on") && !isShowingAdOne && isAppOpenAvailable() && isFirstInterAds) {
                FullScreenContentCallback fullScreenContentCallback =
                        new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                appOpenAdOne = null;
                                isRequestOne = false;
                                isShowingAdOne = false;
                                IntertitialAdsEvent.Strcheckad = "StrClosed";
                                appOpenLoad(mContext);

                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                IntertitialAdsEvent.Strcheckad = "StrClosed";
                                adsListener1.onAdsClose();
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                IntertitialAdsEvent.Strcheckad = "StrOpen";
                                isShowingAdOne = true;
                            }
                        };
                appOpenAdOne.setFullScreenContentCallback(fullScreenContentCallback);
                appOpenAdOne.show((Activity) mContext);
            }

        } else {
            if (Comman.mainResModel!=null && Comman.mainResModel.getData().getInterAds().size()!=0) {

                isFirstInterAds = true;
                ShowInterstitialAds showInterstitialAds = new ShowInterstitialAds((Activity) context, new setOnBackPressListener.OnNoRecordFoundListener() {
                    @Override
                    public void onNoRecordFound() {
                        if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("on") && !isShowingAdOne && isAppOpenAvailable() && isFirstInterAds) {

                        } else {
                            adsListener1.onAdsClose();
                        }
                    }

                    @Override
                    public void onAdsClose() {
                        if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("on") && !isShowingAdOne && isAppOpenAvailable() && isFirstInterAds) {

                        } else {
                            adsListener1.onAdsClose();
                        }
                    }
                }, "01");
                showInterstitialAds.InterstitialAds();
                if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("on") && !isShowingAdOne && isAppOpenAvailable() && isFirstInterAds) {
                    FullScreenContentCallback fullScreenContentCallback =
                            new FullScreenContentCallback() {
                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    appOpenAdOne = null;
                                    isRequestOne = false;
                                    isShowingAdOne = false;
                                    IntertitialAdsEvent.Strcheckad = "StrClosed";
                                    appOpenLoad(mContext);

                                }

                                @Override
                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                    IntertitialAdsEvent.Strcheckad = "StrClosed";
                                    adsListener1.onAdsClose();
                                }

                                @Override
                                public void onAdShowedFullScreenContent() {
                                    IntertitialAdsEvent.Strcheckad = "StrOpen";
                                    isShowingAdOne = true;
                                }
                            };
                    appOpenAdOne.setFullScreenContentCallback(fullScreenContentCallback);
                    appOpenAdOne.show((Activity) mContext);
                }

            }else {
                adsListener1.onAdsClose();
            }
        }

    }

    public static void ShowInterstitialBackAd(Context context, InterCloseCallBack interstitialAdsListener1) {
        mContext = context;
        adsListener1 = interstitialAdsListener1;
        if (Comman.mainResModel != null &&Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("off") && Comman.mainResModel.getData().getExtraFields().getAlternetAppOpen().equalsIgnoreCase("off") && Comman.mainResModel.getData().getExtraFields().getBackAppOpen().equalsIgnoreCase("on") && IntertitialAdsEvent.Strcheckad.equalsIgnoreCase("StrClosed") && !isShowingAdOne && isAppOpenAvailable()) {
            FullScreenContentCallback fullScreenContentCallback =
                    new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            appOpenAdOne = null;
                            isRequestOne = false;
                            isShowingAdOne = false;
                            IntertitialAdsEvent.Strcheckad = "StrClosed";
                            adsListener1.onAdsClose();
                            appOpenLoad(context);

                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            IntertitialAdsEvent.Strcheckad = "StrClosed";
                            adsListener1.onAdsClose();
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            IntertitialAdsEvent.Strcheckad = "StrOpen";
                            isShowingAdOne = true;
                        }
                    };
            isFirstInterAds = false;
            appOpenAdOne.setFullScreenContentCallback(fullScreenContentCallback);
            appOpenAdOne.show((Activity) context);
            if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("on") && AMInterstitial != null && !isFirstInterAds) {
                AMInterstitial.show((Activity) context);
            }

        } else if (AMInterstitial != null && IntertitialAdsEvent.Strcheckad.equals("StrClosed")) {
            isFirstInterAds = true;
            AMInterstitial.show((Activity) context);
            if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("on") && !isShowingAdOne && isAppOpenAvailable() && isFirstInterAds) {
                FullScreenContentCallback fullScreenContentCallback =
                        new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                appOpenAdOne = null;
                                isRequestOne = false;
                                isShowingAdOne = false;
                                IntertitialAdsEvent.Strcheckad = "StrClosed";
                                appOpenLoad(mContext);

                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                IntertitialAdsEvent.Strcheckad = "StrClosed";
                                adsListener1.onAdsClose();
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                IntertitialAdsEvent.Strcheckad = "StrOpen";
                                isShowingAdOne = true;
                            }
                        };
                appOpenAdOne.setFullScreenContentCallback(fullScreenContentCallback);
                appOpenAdOne.show((Activity) mContext);
            }


        } else {
            if (Comman.mainResModel!=null && Comman.mainResModel.getData().getInterAds().size()!=0) {

                isFirstInterAds = true;
                ShowInterstitialAds showInterstitialAds = new ShowInterstitialAds((Activity) context, new setOnBackPressListener.OnNoRecordFoundListener() {
                    @Override
                    public void onNoRecordFound() {
                        if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("on") && !isShowingAdOne && isAppOpenAvailable() && isFirstInterAds) {

                        } else {
                            adsListener1.onAdsClose();
                        }
                    }

                    @Override
                    public void onAdsClose() {
                        if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("on") && !isShowingAdOne && isAppOpenAvailable() && isFirstInterAds) {

                        } else {
                            adsListener1.onAdsClose();
                        }
                    }
                }, "01");
                showInterstitialAds.InterstitialAds();
                if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("on") && !isShowingAdOne && isAppOpenAvailable() && isFirstInterAds) {
                    FullScreenContentCallback fullScreenContentCallback =
                            new FullScreenContentCallback() {
                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    appOpenAdOne = null;
                                    isRequestOne = false;
                                    isShowingAdOne = false;
                                    IntertitialAdsEvent.Strcheckad = "StrClosed";
                                    appOpenLoad(mContext);

                                }

                                @Override
                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                    IntertitialAdsEvent.Strcheckad = "StrClosed";
                                    adsListener1.onAdsClose();
                                }

                                @Override
                                public void onAdShowedFullScreenContent() {
                                    IntertitialAdsEvent.Strcheckad = "StrOpen";
                                    isShowingAdOne = true;
                                }
                            };
                    appOpenAdOne.setFullScreenContentCallback(fullScreenContentCallback);
                    appOpenAdOne.show((Activity) mContext);
                }
            }else {
                adsListener1.onAdsClose();
            }
        }

    }


    public static void clearInstance() {
        AMInterstitial = null;
        aBoolean = false;
        isRequestOne = false;
        isShowingAdOne = false;
        appOpenAdOne = null;
    }

}
