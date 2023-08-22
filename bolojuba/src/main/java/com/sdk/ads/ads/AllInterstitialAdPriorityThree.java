package com.sdk.ads.ads;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;

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

public class AllInterstitialAdPriorityThree {

    private static Context mContext;
    private static InterCloseCallBack adsListener3;
    public static InterstitialAd AMInterstitial3;
    private static boolean aBoolean3 = false;
    public static int interOneCounter = 0;
    public static ModelPrefrences prefrences;
    public static ArrayList<AdsUnit> adsUnits;
    static boolean isRequestThree;
    private static AppOpenAd appOpenAdThree = null;
    private static AppOpenAd.AppOpenAdLoadCallback loadCallbackThree;
    private static boolean isShowingAdThree = false;
    private static boolean isFirstInterAdsThree;


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
            if (!isAppOpenAvailable() && !isRequestThree) {

                if (!IntertitialAdsEvent.AppOpenKey.equalsIgnoreCase("")) {
                    isRequestThree = true;
                    loadCallbackThree = new AppOpenAd.AppOpenAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull AppOpenAd ad) {
                            appOpenAdThree = ad;
                        }

                        @Override
                        public void onAdFailedToLoad(LoadAdError loadAdError) {
                            isRequestThree = false;
                            appOpenAdThree = null;
                        }

                    };

                    AppOpenAd.load(context, IntertitialAdsEvent.AppOpenKey, new AdRequest.Builder().build(),
                            AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallbackThree);
                }
            }

        }


    }


    public static boolean isAppOpenAvailable() {
        return appOpenAdThree != null;
    }


    private static void CallAdmobInter() {

        if (!isloaded() && !aBoolean3) {
            aBoolean3 = true;
            IntertitialAdsEvent.InterstitialKey = adsUnits.get(0).getInterFrequency();
            if (!IntertitialAdsEvent.InterstitialKey.equalsIgnoreCase("")) {
                try {
                    InterstitialAd.load(mContext, IntertitialAdsEvent.InterstitialKey, new AdRequest.Builder().build(), new InterstitialAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                            super.onAdLoaded(interstitialAd);
                            AMInterstitial3 = interstitialAd;
                            aBoolean3 = true;
                            AMCallBack();
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                            super.onAdFailedToLoad(adError);
                            aBoolean3 = false;
                            AMInterstitial3 = null;

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static boolean isloaded() {
        return AMInterstitial3 != null;
    }

    private static void AMCallBack() {

        AMInterstitial3.setFullScreenContentCallback(new FullScreenContentCallback() {
            @Override
            public void onAdDismissedFullScreenContent() {
                AMInterstitial3 = null;
                aBoolean3 = false;
                if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("on") && !isShowingAdThree && isAppOpenAvailable() && isFirstInterAdsThree) {

                } else {
                    adsListener3.onAdsClose();
                }
                IntertitialAdsEvent.Strcheckad = "StrClosed";
                AllInterstitialAdPriorityOne.LoadInterstitialAd(mContext);
            }

            @Override
            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                super.onAdFailedToShowFullScreenContent(adError);
                aBoolean3 = false;
                AMInterstitial3 = null;
                adsListener3.onAdsClose();
                AllInterstitialAdPriorityOne.LoadInterstitialAd(mContext);
                IntertitialAdsEvent.Strcheckad = "StrClosed";
            }

            @Override
            public void onAdShowedFullScreenContent() {
                aBoolean3 = false;
                IntertitialAdsEvent.Strcheckad = "StrOpen";
            }
        });

    }

    public static void ShowInterstitialFrontAd(Context context, InterCloseCallBack interstitialadsListener3) {
        mContext = context;
        adsListener3 = interstitialadsListener3;
        if (Comman.mainResModel != null &&Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("off") && Comman.mainResModel.getData().getExtraFields().getAlternetAppOpen().equalsIgnoreCase("off") && Comman.mainResModel.getData().getExtraFields().getClickAppOpen().equalsIgnoreCase("on") && IntertitialAdsEvent.Strcheckad.equalsIgnoreCase("StrClosed") && !isShowingAdThree && isAppOpenAvailable()) {
            FullScreenContentCallback fullScreenContentCallback =
                    new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            appOpenAdThree = null;
                            isRequestThree = false;
                            isShowingAdThree = false;
                            IntertitialAdsEvent.Strcheckad = "StrClosed";
                            appOpenLoad(context);
                            adsListener3.onAdsClose();


                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            IntertitialAdsEvent.Strcheckad = "StrClosed";
                            adsListener3.onAdsClose();
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            IntertitialAdsEvent.Strcheckad = "StrOpen";
                            isShowingAdThree = true;
                        }
                    };
            isFirstInterAdsThree = false;
            appOpenAdThree.setFullScreenContentCallback(fullScreenContentCallback);
            appOpenAdThree.show((Activity) context);
            if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("on") && AMInterstitial3 != null && !isFirstInterAdsThree) {
                AMInterstitial3.show((Activity) context);
            }

        } else if (AMInterstitial3 != null && IntertitialAdsEvent.Strcheckad.equals("StrClosed")) {
            isFirstInterAdsThree = true;
            AMInterstitial3.show((Activity) context);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("on") && !isShowingAdThree && isAppOpenAvailable() && isFirstInterAdsThree) {
                        FullScreenContentCallback fullScreenContentCallback =
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        appOpenAdThree = null;
                                        isRequestThree = false;
                                        isShowingAdThree = false;
                                        IntertitialAdsEvent.Strcheckad = "StrClosed";
                                        appOpenLoad(mContext);

                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        IntertitialAdsEvent.Strcheckad = "StrClosed";
                                        adsListener3.onAdsClose();
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        IntertitialAdsEvent.Strcheckad = "StrOpen";
                                        isShowingAdThree = true;
                                    }
                                };
                        appOpenAdThree.setFullScreenContentCallback(fullScreenContentCallback);
                        appOpenAdThree.show((Activity) mContext);
                    }
                }
            }, 300);
        } else {
            if (Comman.mainResModel!=null && Comman.mainResModel.getData().getInterAds().size()!=0) {

                isFirstInterAdsThree = true;
                ShowInterstitialAds showInterstitialAds = new ShowInterstitialAds((Activity) context, new setOnBackPressListener.OnNoRecordFoundListener() {
                    @Override
                    public void onNoRecordFound() {
                        if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("on") && !isShowingAdThree && isAppOpenAvailable() && isFirstInterAdsThree) {

                        } else {
                            adsListener3.onAdsClose();
                        }
                    }

                    @Override
                    public void onAdsClose() {
                        if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("on") && !isShowingAdThree && isAppOpenAvailable() && isFirstInterAdsThree) {

                        } else {
                            adsListener3.onAdsClose();
                        }
                    }
                }, "01");
                showInterstitialAds.InterstitialAds();
                if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("on") && !isShowingAdThree && isAppOpenAvailable() && isFirstInterAdsThree) {
                    FullScreenContentCallback fullScreenContentCallback =
                            new FullScreenContentCallback() {
                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    appOpenAdThree = null;
                                    isRequestThree = false;
                                    isShowingAdThree = false;
                                    IntertitialAdsEvent.Strcheckad = "StrClosed";
                                    appOpenLoad(mContext);

                                }

                                @Override
                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                    IntertitialAdsEvent.Strcheckad = "StrClosed";
                                    adsListener3.onAdsClose();
                                }

                                @Override
                                public void onAdShowedFullScreenContent() {
                                    IntertitialAdsEvent.Strcheckad = "StrOpen";
                                    isShowingAdThree = true;
                                }
                            };
                    appOpenAdThree.setFullScreenContentCallback(fullScreenContentCallback);
                    appOpenAdThree.show((Activity) mContext);
                }

            }else {
                adsListener3.onAdsClose();
            }
        }

    }

    public static void ShowInterstitialBackAd(Context context, InterCloseCallBack interstitialadsListener3) {
        mContext = context;
        adsListener3 = interstitialadsListener3;
        if (Comman.mainResModel != null &&Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("off") && Comman.mainResModel.getData().getExtraFields().getAlternetAppOpen().equalsIgnoreCase("off") && Comman.mainResModel.getData().getExtraFields().getBackAppOpen().equalsIgnoreCase("on") && IntertitialAdsEvent.Strcheckad.equalsIgnoreCase("StrClosed") && !isShowingAdThree && isAppOpenAvailable()) {
            FullScreenContentCallback fullScreenContentCallback =
                    new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            appOpenAdThree = null;
                            isRequestThree = false;
                            isShowingAdThree = false;
                            IntertitialAdsEvent.Strcheckad = "StrClosed";
                            adsListener3.onAdsClose();
                            appOpenLoad(context);

                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            IntertitialAdsEvent.Strcheckad = "StrClosed";
                            adsListener3.onAdsClose();
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            IntertitialAdsEvent.Strcheckad = "StrOpen";
                            isShowingAdThree = true;
                        }
                    };
            isFirstInterAdsThree = false;
            appOpenAdThree.setFullScreenContentCallback(fullScreenContentCallback);
            appOpenAdThree.show((Activity) context);
            if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("on") && AMInterstitial3 != null && !isFirstInterAdsThree) {
                AMInterstitial3.show((Activity) context);
            }
        } else if (AMInterstitial3 != null && IntertitialAdsEvent.Strcheckad.equals("StrClosed")) {
            isFirstInterAdsThree = true;
            AMInterstitial3.show((Activity) context);
            if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("on") && !isShowingAdThree && isAppOpenAvailable() && isFirstInterAdsThree) {
                FullScreenContentCallback fullScreenContentCallback =
                        new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                appOpenAdThree = null;
                                isRequestThree = false;
                                isShowingAdThree = false;
                                IntertitialAdsEvent.Strcheckad = "StrClosed";
                                appOpenLoad(mContext);

                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                IntertitialAdsEvent.Strcheckad = "StrClosed";
                                adsListener3.onAdsClose();
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                IntertitialAdsEvent.Strcheckad = "StrOpen";
                                isShowingAdThree = true;
                            }
                        };
                appOpenAdThree.setFullScreenContentCallback(fullScreenContentCallback);
                appOpenAdThree.show((Activity) mContext);
            }


        } else {
            if (Comman.mainResModel!=null && Comman.mainResModel.getData().getInterAds().size()!=0) {

                isFirstInterAdsThree = true;
                ShowInterstitialAds showInterstitialAds = new ShowInterstitialAds((Activity) context, new setOnBackPressListener.OnNoRecordFoundListener() {
                    @Override
                    public void onNoRecordFound() {
                        if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("on") && !isShowingAdThree && isAppOpenAvailable() && isFirstInterAdsThree) {

                        } else {
                            adsListener3.onAdsClose();
                        }
                    }

                    @Override
                    public void onAdsClose() {
                        if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("on") && !isShowingAdThree && isAppOpenAvailable() && isFirstInterAdsThree) {

                        } else {
                            adsListener3.onAdsClose();
                        }
                    }
                }, "01");
                showInterstitialAds.InterstitialAds();
                if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("on") && !isShowingAdThree && isAppOpenAvailable() && isFirstInterAdsThree) {
                    FullScreenContentCallback fullScreenContentCallback =
                            new FullScreenContentCallback() {
                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    appOpenAdThree = null;
                                    isRequestThree = false;
                                    isShowingAdThree = false;
                                    IntertitialAdsEvent.Strcheckad = "StrClosed";
                                    appOpenLoad(mContext);

                                }

                                @Override
                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                    IntertitialAdsEvent.Strcheckad = "StrClosed";
                                    adsListener3.onAdsClose();
                                }

                                @Override
                                public void onAdShowedFullScreenContent() {
                                    IntertitialAdsEvent.Strcheckad = "StrOpen";
                                    isShowingAdThree = true;
                                }
                            };
                    appOpenAdThree.setFullScreenContentCallback(fullScreenContentCallback);
                    appOpenAdThree.show((Activity) mContext);
                }
            }else {
                adsListener3.onAdsClose();
            }
        }

    }


    public static void clearInstance() {
        AMInterstitial3 = null;
        aBoolean3 = false;
        isRequestThree = false;
        isShowingAdThree = false;
        appOpenAdThree = null;
    }
}
