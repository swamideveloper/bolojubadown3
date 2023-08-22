package com.sdk.ads.ads;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.sdk.ads.Comman;
import com.sdk.ads.LocalSave.ModelPrefrences;
import com.sdk.ads.R;
import com.sdk.ads.ResModel.AdsUnit;
import com.sdk.ads.SdkAppController;
import com.sdk.ads.interstitialAds.ShowInterstitialAds;
import com.sdk.ads.interstitialAds.callback.setOnBackPressListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IntertitialAdsEvent {

    public static String USER_PREFS = "Purchase PREFS";
    public static SharedPreferences appSharedPref;
    public static String Strcheckad = "StrClosed";
    public static List<String> TestDeviceID = Arrays.asList("5EF816D21280C1645E5A288960EF8E8F");
    public static String RewardedKey = "";
    public static String InterstitialKey = "";
    public static String SplashInterstitialKey = "";
    public static String NativeKey = "p";
    public static String NativeKeyBanner = "p";
    public static String AppOpenKey = "";
    public static String LargeBanner = "";
    public static int intertitialCounter;
    public static int createFreqInterCounter, backFreqInterCounter;
    public static RewardedAd mRewardedAd;
    public static int RewardedkeyCounter;
    public static int alertnetAppOpenCounter, alternetBackAppOpenCounter;

    public static void CallInterstitial(Context context) {
        if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getAds().equalsIgnoreCase("on")) {
            AllInterstitialAdPriorityOne.LoadInterstitialAd(context);
            AllInterstitialAdPriorityTwo.LoadInterstitialAd(context);
            AllInterstitialAdPriorityThree.LoadInterstitialAd(context);

        }


    }


    public static void giftRewarded(Context context, ViewGroup giftLayout) {
        giftLayout.setVisibility(View.GONE);
        ModelPrefrences prefrences = new ModelPrefrences(context);
        if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getAds().equalsIgnoreCase("on") && Comman.mainResModel.getData().getAdsUnits().size() != 0 && !Comman.mainResModel.getData().getAdsUnits().get(0).getRewardedVideo().equalsIgnoreCase("")) {
            ArrayList<AdsUnit> adsUnits = (ArrayList<AdsUnit>) Comman.mainResModel.getData().getAdsUnits();
            if (adsUnits.size() > 1) {
                if (RewardedkeyCounter == 0)
                    IntertitialAdsEvent.RewardedKey = adsUnits.get(0).getRewardedVideo();
                else
                    IntertitialAdsEvent.RewardedKey = adsUnits.get(1).getRewardedVideo();
            } else {
                IntertitialAdsEvent.RewardedKey = adsUnits.get(0).getRewardedVideo();
            }

            AdRequest adRequest = new AdRequest.Builder().build();
            RewardedAd.load(context, IntertitialAdsEvent.RewardedKey,
                    adRequest, new RewardedAdLoadCallback() {
                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            // Handle the error.

                            if (adsUnits.size() > 1) {

                                if (RewardedkeyCounter == 0) {
                                    ++RewardedkeyCounter;
                                    giftRewarded(context, giftLayout);

                                } else {
                                    RewardedkeyCounter = 0;
                                }
                            }
                        }

                        @Override
                        public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                            mRewardedAd = rewardedAd;
                            giftLayout.setVisibility(View.VISIBLE);

                        }
                    });

            giftLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mRewardedAd.show((Activity) context, rewardItem -> {
                        IntertitialAdsEvent.Strcheckad = "StrClosed";
                        giftLayout.setVisibility(View.GONE);
                        giftRewarded(context, giftLayout);
                    });

                    mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdShowedFullScreenContent() {
                            // Called when ad is shown.
                            IntertitialAdsEvent.Strcheckad = "StrOpen";
                            giftLayout.setVisibility(View.GONE);
                        }


                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            // Called when ad fails to show.
                            mRewardedAd = null;
                            IntertitialAdsEvent.Strcheckad = "StrClosed";
                            giftLayout.setVisibility(View.GONE);
                            giftRewarded(context, giftLayout);

                        }

                        @Override
                        public void onAdDismissedFullScreenContent() {
                            // Called when ad is dismissed.
                            mRewardedAd = null;
                            IntertitialAdsEvent.Strcheckad = "StrClosed";
                            giftLayout.setVisibility(View.GONE);

                        }

                    });
                }
            });

        } else {
            giftLayout.setVisibility(View.GONE);
        }
    }


    public static void ShowInterstitialAdsOnCreate(Context context) {

        ModelPrefrences modelPrefrences = new ModelPrefrences(context);
        if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getAds().equalsIgnoreCase("on")) {
            if (createFreqInterCounter != 0 && Integer.parseInt(Comman.mainResModel.getData().getExtraFields().getInterFrequency()) != 0 && createFreqInterCounter % Integer.parseInt(Comman.mainResModel.getData().getExtraFields().getInterFrequency()) == 0) {
                ++createFreqInterCounter;
                if (Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("off") && Comman.mainResModel.getData().getExtraFields().getAlternetAppOpen().equalsIgnoreCase("on")) {
                    if (alertnetAppOpenCounter == 0) {
                        ++alertnetAppOpenCounter;
                        if (AllInterstitialAdPriorityOne.AMInterstitial != null) {
                            AllInterstitialAdPriorityOne.ShowInterstitialFrontAd(context, () -> {
                            });
                        } else if (AllInterstitialAdPriorityTwo.AMInterstitial2 != null) {
                            AllInterstitialAdPriorityTwo.ShowInterstitialFrontAd(context, () -> {
                            });
                        } else if (AllInterstitialAdPriorityThree.AMInterstitial3 != null) {
                            AllInterstitialAdPriorityThree.ShowInterstitialFrontAd(context, () -> {
                            });
                        } else if (AllInterstitialAdPriorityOne.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityOne.ShowInterstitialFrontAd(context, () -> {
                            });
                        } else if (AllInterstitialAdPriorityTwo.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityTwo.ShowInterstitialFrontAd(context, () -> {
                            });
                        } else if (AllInterstitialAdPriorityThree.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityThree.ShowInterstitialFrontAd(context, () -> {
                            });
                        } else if (Comman.mainResModel != null && Comman.mainResModel.getData().getInterAds().size() != 0) {

                            showCustomInterAds(context, null);
                        }


                    } else if (alertnetAppOpenCounter == 1) {
                        --alertnetAppOpenCounter;
                        new AppOpenManager(SdkAppController.getInstance(), true).showAdIfAvailable(context, new InterCloseCallBack() {
                            @Override
                            public void onAdsClose() {

                            }
                        });
                    } else {
                        if (AllInterstitialAdPriorityOne.AMInterstitial != null) {
                            AllInterstitialAdPriorityOne.ShowInterstitialFrontAd(context, () -> {
                            });
                        } else if (AllInterstitialAdPriorityTwo.AMInterstitial2 != null) {
                            AllInterstitialAdPriorityTwo.ShowInterstitialFrontAd(context, () -> {
                            });
                        } else if (AllInterstitialAdPriorityThree.AMInterstitial3 != null) {
                            AllInterstitialAdPriorityThree.ShowInterstitialFrontAd(context, () -> {
                            });
                        } else if (AllInterstitialAdPriorityOne.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityOne.ShowInterstitialFrontAd(context, () -> {
                            });
                        } else if (AllInterstitialAdPriorityTwo.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityTwo.ShowInterstitialFrontAd(context, () -> {
                            });
                        } else if (AllInterstitialAdPriorityThree.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityThree.ShowInterstitialFrontAd(context, () -> {
                            });
                        } else if (Comman.mainResModel != null && Comman.mainResModel.getData().getInterAds().size() != 0) {

                            showCustomInterAds(context, null);
                        }
                    }
                } else {
                    if (AllInterstitialAdPriorityOne.AMInterstitial != null) {
                        AllInterstitialAdPriorityOne.ShowInterstitialFrontAd(context, () -> {
                        });
                    } else if (AllInterstitialAdPriorityTwo.AMInterstitial2 != null) {
                        AllInterstitialAdPriorityTwo.ShowInterstitialFrontAd(context, () -> {
                        });
                    } else if (AllInterstitialAdPriorityThree.AMInterstitial3 != null) {
                        AllInterstitialAdPriorityThree.ShowInterstitialFrontAd(context, () -> {
                        });
                    } else if (AllInterstitialAdPriorityOne.isAppOpenAvailable()) {
                        AllInterstitialAdPriorityOne.ShowInterstitialFrontAd(context, () -> {
                        });
                    } else if (AllInterstitialAdPriorityTwo.isAppOpenAvailable()) {
                        AllInterstitialAdPriorityTwo.ShowInterstitialFrontAd(context, () -> {
                        });
                    } else if (AllInterstitialAdPriorityThree.isAppOpenAvailable()) {
                        AllInterstitialAdPriorityThree.ShowInterstitialFrontAd(context, () -> {
                        });
                    } else if (Comman.mainResModel != null && Comman.mainResModel.getData().getInterAds().size() != 0) {

                        showCustomInterAds(context, null);
                    }
                }


            } else if (Integer.parseInt(Comman.mainResModel.getData().getExtraFields().getInterFrequency()) != 0 && Integer.parseInt(Comman.mainResModel.getData().getExtraFields().getInterFrequency()) == 1) {
                if (Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("off") && Comman.mainResModel.getData().getExtraFields().getAlternetAppOpen().equalsIgnoreCase("on")) {
                    if (alertnetAppOpenCounter == 0) {
                        ++alertnetAppOpenCounter;
                        if (AllInterstitialAdPriorityOne.AMInterstitial != null) {
                            AllInterstitialAdPriorityOne.ShowInterstitialFrontAd(context, () -> {
                            });
                        } else if (AllInterstitialAdPriorityTwo.AMInterstitial2 != null) {
                            AllInterstitialAdPriorityTwo.ShowInterstitialFrontAd(context, () -> {
                            });
                        } else if (AllInterstitialAdPriorityThree.AMInterstitial3 != null) {
                            AllInterstitialAdPriorityThree.ShowInterstitialFrontAd(context, () -> {
                            });
                        } else if (AllInterstitialAdPriorityOne.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityOne.ShowInterstitialFrontAd(context, () -> {
                            });
                        } else if (AllInterstitialAdPriorityTwo.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityTwo.ShowInterstitialFrontAd(context, () -> {
                            });
                        } else if (AllInterstitialAdPriorityThree.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityThree.ShowInterstitialFrontAd(context, () -> {
                            });
                        } else if (Comman.mainResModel != null && Comman.mainResModel.getData().getInterAds().size() != 0) {

                            showCustomInterAds(context, null);
                        }
                    } else if (alertnetAppOpenCounter == 1) {
                        --alertnetAppOpenCounter;
                        new AppOpenManager(SdkAppController.getInstance(), true).showAdIfAvailable(context, new InterCloseCallBack() {
                            @Override
                            public void onAdsClose() {

                            }
                        });
                    } else {
                        if (AllInterstitialAdPriorityOne.AMInterstitial != null) {
                            AllInterstitialAdPriorityOne.ShowInterstitialFrontAd(context, () -> {
                            });
                        } else if (AllInterstitialAdPriorityTwo.AMInterstitial2 != null) {
                            AllInterstitialAdPriorityTwo.ShowInterstitialFrontAd(context, () -> {
                            });
                        } else if (AllInterstitialAdPriorityThree.AMInterstitial3 != null) {
                            AllInterstitialAdPriorityThree.ShowInterstitialFrontAd(context, () -> {
                            });
                        } else if (AllInterstitialAdPriorityOne.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityOne.ShowInterstitialFrontAd(context, () -> {
                            });
                        } else if (AllInterstitialAdPriorityTwo.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityTwo.ShowInterstitialFrontAd(context, () -> {
                            });
                        } else if (AllInterstitialAdPriorityThree.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityThree.ShowInterstitialFrontAd(context, () -> {
                            });
                        } else if (Comman.mainResModel != null && Comman.mainResModel.getData().getInterAds().size() != 0) {

                            showCustomInterAds(context, null);
                        }
                    }
                } else {
                    if (AllInterstitialAdPriorityOne.AMInterstitial != null) {
                        AllInterstitialAdPriorityOne.ShowInterstitialFrontAd(context, () -> {
                        });
                    } else if (AllInterstitialAdPriorityTwo.AMInterstitial2 != null) {
                        AllInterstitialAdPriorityTwo.ShowInterstitialFrontAd(context, () -> {
                        });
                    } else if (AllInterstitialAdPriorityThree.AMInterstitial3 != null) {
                        AllInterstitialAdPriorityThree.ShowInterstitialFrontAd(context, () -> {
                        });
                    } else if (AllInterstitialAdPriorityOne.isAppOpenAvailable()) {
                        AllInterstitialAdPriorityOne.ShowInterstitialFrontAd(context, () -> {
                        });
                    } else if (AllInterstitialAdPriorityTwo.isAppOpenAvailable()) {
                        AllInterstitialAdPriorityTwo.ShowInterstitialFrontAd(context, () -> {
                        });
                    } else if (AllInterstitialAdPriorityThree.isAppOpenAvailable()) {
                        AllInterstitialAdPriorityThree.ShowInterstitialFrontAd(context, () -> {
                        });
                    } else if (Comman.mainResModel != null && Comman.mainResModel.getData().getInterAds().size() != 0) {

                        showCustomInterAds(context, null);
                    }
                }
            } else {
                ++createFreqInterCounter;
            }
        }


    }

    public static void ShowInterstitialAdsDFrontWithCallBack(Context context, InterCloseCallBack interCloseCallBack) {
        ModelPrefrences modelPrefrences = new ModelPrefrences(context);
        if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getAds().equalsIgnoreCase("on")) {
            if (createFreqInterCounter != 0 && Integer.parseInt(Comman.mainResModel.getData().getExtraFields().getInterFrequency()) != 0 && createFreqInterCounter % Integer.parseInt(Comman.mainResModel.getData().getExtraFields().getInterFrequency()) == 0) {
                ++createFreqInterCounter;
                if (Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("off") && Comman.mainResModel.getData().getExtraFields().getAlternetAppOpen().equalsIgnoreCase("on")) {
                    if (alertnetAppOpenCounter == 0) {
                        ++alertnetAppOpenCounter;
                        if (AllInterstitialAdPriorityOne.AMInterstitial != null) {
                            AllInterstitialAdPriorityOne.ShowInterstitialFrontAd(context, () -> {
                                interCloseCallBack.onAdsClose();
                            });
                        } else if (AllInterstitialAdPriorityTwo.AMInterstitial2 != null) {
                            AllInterstitialAdPriorityTwo.ShowInterstitialFrontAd(context, () -> {
                                interCloseCallBack.onAdsClose();
                            });
                        } else if (AllInterstitialAdPriorityThree.AMInterstitial3 != null) {
                            AllInterstitialAdPriorityThree.ShowInterstitialFrontAd(context, () -> {
                                interCloseCallBack.onAdsClose();
                            });
                        } else if (AllInterstitialAdPriorityOne.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityOne.ShowInterstitialFrontAd(context, () -> {
                                interCloseCallBack.onAdsClose();
                            });
                        } else if (AllInterstitialAdPriorityTwo.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityTwo.ShowInterstitialFrontAd(context, () -> {
                                interCloseCallBack.onAdsClose();
                            });
                        } else if (AllInterstitialAdPriorityThree.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityThree.ShowInterstitialFrontAd(context, () -> {
                                interCloseCallBack.onAdsClose();
                            });
                        } else if (Comman.mainResModel != null && Comman.mainResModel.getData().getInterAds().size() != 0) {

                            showCustomInterAds(context, interCloseCallBack);
                        } else {
                            interCloseCallBack.onAdsClose();
                        }

                    } else if (alertnetAppOpenCounter == 1) {
                        --alertnetAppOpenCounter;
                        new AppOpenManager(SdkAppController.getInstance(), true).showAdIfAvailable(context, new InterCloseCallBack() {
                            @Override
                            public void onAdsClose() {
                                interCloseCallBack.onAdsClose();
                            }
                        });
                    } else {
                        if (AllInterstitialAdPriorityOne.AMInterstitial != null) {
                            AllInterstitialAdPriorityOne.ShowInterstitialFrontAd(context, () -> {
                                interCloseCallBack.onAdsClose();
                            });
                        } else if (AllInterstitialAdPriorityTwo.AMInterstitial2 != null) {
                            AllInterstitialAdPriorityTwo.ShowInterstitialFrontAd(context, () -> {
                                interCloseCallBack.onAdsClose();
                            });
                        } else if (AllInterstitialAdPriorityThree.AMInterstitial3 != null) {
                            AllInterstitialAdPriorityThree.ShowInterstitialFrontAd(context, () -> {
                                interCloseCallBack.onAdsClose();
                            });
                        } else if (AllInterstitialAdPriorityOne.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityOne.ShowInterstitialFrontAd(context, () -> {
                                interCloseCallBack.onAdsClose();
                            });
                        } else if (AllInterstitialAdPriorityTwo.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityTwo.ShowInterstitialFrontAd(context, () -> {
                                interCloseCallBack.onAdsClose();
                            });
                        } else if (AllInterstitialAdPriorityThree.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityThree.ShowInterstitialFrontAd(context, () -> {
                                interCloseCallBack.onAdsClose();
                            });
                        } else if (Comman.mainResModel != null && Comman.mainResModel.getData().getInterAds().size() != 0) {

                            showCustomInterAds(context, interCloseCallBack);
                        } else {
                            interCloseCallBack.onAdsClose();
                        }
                    }
                } else {
                    if (AllInterstitialAdPriorityOne.AMInterstitial != null) {
                        AllInterstitialAdPriorityOne.ShowInterstitialFrontAd(context, () -> {
                            interCloseCallBack.onAdsClose();
                        });
                    } else if (AllInterstitialAdPriorityTwo.AMInterstitial2 != null) {
                        AllInterstitialAdPriorityTwo.ShowInterstitialFrontAd(context, () -> {
                            interCloseCallBack.onAdsClose();
                        });
                    } else if (AllInterstitialAdPriorityThree.AMInterstitial3 != null) {
                        AllInterstitialAdPriorityThree.ShowInterstitialFrontAd(context, () -> {
                            interCloseCallBack.onAdsClose();
                        });
                    } else if (AllInterstitialAdPriorityOne.isAppOpenAvailable()) {
                        AllInterstitialAdPriorityOne.ShowInterstitialFrontAd(context, () -> {
                            interCloseCallBack.onAdsClose();
                        });
                    } else if (AllInterstitialAdPriorityTwo.isAppOpenAvailable()) {
                        AllInterstitialAdPriorityTwo.ShowInterstitialFrontAd(context, () -> {
                            interCloseCallBack.onAdsClose();
                        });
                    } else if (AllInterstitialAdPriorityThree.isAppOpenAvailable()) {
                        AllInterstitialAdPriorityThree.ShowInterstitialFrontAd(context, () -> {
                            interCloseCallBack.onAdsClose();
                        });
                    } else if (Comman.mainResModel != null && Comman.mainResModel.getData().getInterAds().size() != 0) {

                        showCustomInterAds(context, interCloseCallBack);
                    } else {
                        interCloseCallBack.onAdsClose();
                    }
                }
            } else if (Integer.parseInt(Comman.mainResModel.getData().getExtraFields().getInterFrequency()) == 1) {
                if (Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("off") && Comman.mainResModel.getData().getExtraFields().getAlternetAppOpen().equalsIgnoreCase("on")) {
                    if (alertnetAppOpenCounter == 0) {
                        ++alertnetAppOpenCounter;
                        if (AllInterstitialAdPriorityOne.AMInterstitial != null) {
                            AllInterstitialAdPriorityOne.ShowInterstitialFrontAd(context, () -> {
                                interCloseCallBack.onAdsClose();
                            });
                        } else if (AllInterstitialAdPriorityTwo.AMInterstitial2 != null) {
                            AllInterstitialAdPriorityTwo.ShowInterstitialFrontAd(context, () -> {
                                interCloseCallBack.onAdsClose();
                            });
                        } else if (AllInterstitialAdPriorityThree.AMInterstitial3 != null) {
                            AllInterstitialAdPriorityThree.ShowInterstitialFrontAd(context, () -> {
                                interCloseCallBack.onAdsClose();
                            });
                        } else if (AllInterstitialAdPriorityOne.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityOne.ShowInterstitialFrontAd(context, () -> {
                                interCloseCallBack.onAdsClose();
                            });
                        } else if (AllInterstitialAdPriorityTwo.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityTwo.ShowInterstitialFrontAd(context, () -> {
                                interCloseCallBack.onAdsClose();
                            });
                        } else if (AllInterstitialAdPriorityThree.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityThree.ShowInterstitialFrontAd(context, () -> {
                                interCloseCallBack.onAdsClose();
                            });
                        } else if (Comman.mainResModel != null && Comman.mainResModel.getData().getInterAds().size() != 0) {

                            showCustomInterAds(context, interCloseCallBack);
                        } else {
                            interCloseCallBack.onAdsClose();
                        }
                    } else if (alertnetAppOpenCounter == 1) {
                        --alertnetAppOpenCounter;
                        new AppOpenManager(SdkAppController.getInstance(), true).showAdIfAvailable(context, new InterCloseCallBack() {
                            @Override
                            public void onAdsClose() {
                                interCloseCallBack.onAdsClose();
                            }
                        });
                    } else {
                        if (AllInterstitialAdPriorityOne.AMInterstitial != null) {
                            AllInterstitialAdPriorityOne.ShowInterstitialFrontAd(context, () -> {
                                interCloseCallBack.onAdsClose();
                            });
                        } else if (AllInterstitialAdPriorityTwo.AMInterstitial2 != null) {
                            AllInterstitialAdPriorityTwo.ShowInterstitialFrontAd(context, () -> {
                                interCloseCallBack.onAdsClose();
                            });
                        } else if (AllInterstitialAdPriorityThree.AMInterstitial3 != null) {
                            AllInterstitialAdPriorityThree.ShowInterstitialFrontAd(context, () -> {
                                interCloseCallBack.onAdsClose();
                            });
                        } else if (AllInterstitialAdPriorityOne.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityOne.ShowInterstitialFrontAd(context, () -> {
                                interCloseCallBack.onAdsClose();
                            });
                        } else if (AllInterstitialAdPriorityTwo.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityTwo.ShowInterstitialFrontAd(context, () -> {
                                interCloseCallBack.onAdsClose();
                            });
                        } else if (AllInterstitialAdPriorityThree.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityThree.ShowInterstitialFrontAd(context, () -> {
                                interCloseCallBack.onAdsClose();
                            });
                        } else if (Comman.mainResModel != null && Comman.mainResModel.getData().getInterAds().size() != 0) {

                            showCustomInterAds(context, interCloseCallBack);
                        } else {
                            interCloseCallBack.onAdsClose();
                        }
                    }
                } else {
                    if (AllInterstitialAdPriorityOne.AMInterstitial != null) {
                        AllInterstitialAdPriorityOne.ShowInterstitialFrontAd(context, () -> {
                            interCloseCallBack.onAdsClose();
                        });
                    } else if (AllInterstitialAdPriorityTwo.AMInterstitial2 != null) {
                        AllInterstitialAdPriorityTwo.ShowInterstitialFrontAd(context, () -> {
                            interCloseCallBack.onAdsClose();
                        });
                    } else if (AllInterstitialAdPriorityThree.AMInterstitial3 != null) {
                        AllInterstitialAdPriorityThree.ShowInterstitialFrontAd(context, () -> {
                            interCloseCallBack.onAdsClose();
                        });
                    } else if (AllInterstitialAdPriorityOne.isAppOpenAvailable()) {
                        AllInterstitialAdPriorityOne.ShowInterstitialFrontAd(context, () -> {
                            interCloseCallBack.onAdsClose();
                        });
                    } else if (AllInterstitialAdPriorityTwo.isAppOpenAvailable()) {
                        AllInterstitialAdPriorityTwo.ShowInterstitialFrontAd(context, () -> {
                            interCloseCallBack.onAdsClose();
                        });
                    } else if (AllInterstitialAdPriorityThree.isAppOpenAvailable()) {
                        AllInterstitialAdPriorityThree.ShowInterstitialFrontAd(context, () -> {
                            interCloseCallBack.onAdsClose();
                        });
                    } else if (Comman.mainResModel != null && Comman.mainResModel.getData().getInterAds().size() != 0) {

                        showCustomInterAds(context, interCloseCallBack);
                    } else {
                        interCloseCallBack.onAdsClose();
                    }
                }
            } else {
                ++createFreqInterCounter;
                interCloseCallBack.onAdsClose();
            }
        } else {
            interCloseCallBack.onAdsClose();
        }

    }


    public static void ShowInterstitialAdsWithCallBack(Context context, InterCloseCallBack interCloseCallBack) {
        if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getAds().equalsIgnoreCase("on")) {
            if (backFreqInterCounter != 0 && Integer.parseInt(Comman.mainResModel.getData().getExtraFields().getBackInterFrequency()) != 0 && backFreqInterCounter % Integer.parseInt(Comman.mainResModel.getData().getExtraFields().getBackInterFrequency()) == 0) {
                ++backFreqInterCounter;
                if (Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("off") && Comman.mainResModel.getData().getExtraFields().getAlternetAppOpen().equalsIgnoreCase("on")) {
                    if (alertnetAppOpenCounter == 0) {
                        ++alertnetAppOpenCounter;
                        if (AllInterstitialAdPriorityOne.AMInterstitial != null) {
                            AllInterstitialAdPriorityOne.ShowInterstitialBackAd(context, () -> {
                                interCloseCallBack.onAdsClose();
                            });
                        } else if (AllInterstitialAdPriorityTwo.AMInterstitial2 != null) {
                            AllInterstitialAdPriorityTwo.ShowInterstitialBackAd(context, () -> {
                                interCloseCallBack.onAdsClose();
                            });
                        } else if (AllInterstitialAdPriorityThree.AMInterstitial3 != null) {
                            AllInterstitialAdPriorityThree.ShowInterstitialBackAd(context, () -> {
                                interCloseCallBack.onAdsClose();
                            });
                        } else if (AllInterstitialAdPriorityOne.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityOne.ShowInterstitialBackAd(context, () -> {
                                interCloseCallBack.onAdsClose();
                            });
                        } else if (AllInterstitialAdPriorityTwo.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityTwo.ShowInterstitialBackAd(context, () -> {
                                interCloseCallBack.onAdsClose();
                            });
                        } else if (AllInterstitialAdPriorityThree.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityThree.ShowInterstitialBackAd(context, () -> {
                                interCloseCallBack.onAdsClose();
                            });
                        } else if (Comman.mainResModel != null && Comman.mainResModel.getData().getInterAds().size() != 0) {

                            showCustomInterAds(context, interCloseCallBack);
                        } else {
                            interCloseCallBack.onAdsClose();
                        }

                    } else if (alertnetAppOpenCounter == 1) {
                        --alertnetAppOpenCounter;
                        new AppOpenManager(SdkAppController.getInstance(), true).showAdIfAvailable(context, new InterCloseCallBack() {
                            @Override
                            public void onAdsClose() {
                                interCloseCallBack.onAdsClose();
                            }
                        });
                    } else {
                        if (AllInterstitialAdPriorityOne.AMInterstitial != null) {
                            AllInterstitialAdPriorityOne.ShowInterstitialBackAd(context, () -> {
                                interCloseCallBack.onAdsClose();
                            });
                        } else if (AllInterstitialAdPriorityTwo.AMInterstitial2 != null) {
                            AllInterstitialAdPriorityTwo.ShowInterstitialBackAd(context, () -> {
                                interCloseCallBack.onAdsClose();
                            });
                        } else if (AllInterstitialAdPriorityThree.AMInterstitial3 != null) {
                            AllInterstitialAdPriorityThree.ShowInterstitialBackAd(context, () -> {
                                interCloseCallBack.onAdsClose();
                            });
                        } else if (AllInterstitialAdPriorityOne.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityOne.ShowInterstitialBackAd(context, () -> {
                                interCloseCallBack.onAdsClose();
                            });
                        } else if (AllInterstitialAdPriorityTwo.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityTwo.ShowInterstitialBackAd(context, () -> {
                                interCloseCallBack.onAdsClose();
                            });
                        } else if (AllInterstitialAdPriorityThree.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityThree.ShowInterstitialBackAd(context, () -> {
                                interCloseCallBack.onAdsClose();
                            });
                        } else if (Comman.mainResModel != null && Comman.mainResModel.getData().getInterAds().size() != 0) {

                            showCustomInterAds(context, interCloseCallBack);
                        } else {
                            interCloseCallBack.onAdsClose();
                        }
                    }
                } else {
                    if (AllInterstitialAdPriorityOne.AMInterstitial != null) {
                        AllInterstitialAdPriorityOne.ShowInterstitialBackAd(context, () -> {
                            interCloseCallBack.onAdsClose();
                        });
                    } else if (AllInterstitialAdPriorityTwo.AMInterstitial2 != null) {
                        AllInterstitialAdPriorityTwo.ShowInterstitialBackAd(context, () -> {
                            interCloseCallBack.onAdsClose();
                        });
                    } else if (AllInterstitialAdPriorityThree.AMInterstitial3 != null) {
                        AllInterstitialAdPriorityThree.ShowInterstitialBackAd(context, () -> {
                            interCloseCallBack.onAdsClose();
                        });
                    } else if (AllInterstitialAdPriorityOne.isAppOpenAvailable()) {
                        AllInterstitialAdPriorityOne.ShowInterstitialBackAd(context, () -> {
                            interCloseCallBack.onAdsClose();
                        });
                    } else if (AllInterstitialAdPriorityTwo.isAppOpenAvailable()) {
                        AllInterstitialAdPriorityTwo.ShowInterstitialBackAd(context, () -> {
                            interCloseCallBack.onAdsClose();
                        });
                    } else if (AllInterstitialAdPriorityThree.isAppOpenAvailable()) {
                        AllInterstitialAdPriorityThree.ShowInterstitialBackAd(context, () -> {
                            interCloseCallBack.onAdsClose();
                        });
                    } else if (Comman.mainResModel != null && Comman.mainResModel.getData().getInterAds().size() != 0) {

                        showCustomInterAds(context, interCloseCallBack);
                    } else {
                        interCloseCallBack.onAdsClose();
                    }
                }


            } else if (Integer.parseInt(Comman.mainResModel.getData().getExtraFields().getBackInterFrequency()) != 0 && Integer.parseInt(Comman.mainResModel.getData().getExtraFields().getBackInterFrequency()) == 1) {
                if (Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("off") && Comman.mainResModel.getData().getExtraFields().getAlternetAppOpen().equalsIgnoreCase("on")) {
                    if (alertnetAppOpenCounter == 0) {
                        ++alertnetAppOpenCounter;
                        if (AllInterstitialAdPriorityOne.AMInterstitial != null) {
                            AllInterstitialAdPriorityOne.ShowInterstitialBackAd(context, () -> {
                                interCloseCallBack.onAdsClose();
                            });
                        } else if (AllInterstitialAdPriorityTwo.AMInterstitial2 != null) {
                            AllInterstitialAdPriorityTwo.ShowInterstitialBackAd(context, () -> {
                                interCloseCallBack.onAdsClose();
                            });
                        } else if (AllInterstitialAdPriorityThree.AMInterstitial3 != null) {
                            AllInterstitialAdPriorityThree.ShowInterstitialBackAd(context, () -> {
                                interCloseCallBack.onAdsClose();
                            });
                        } else if (AllInterstitialAdPriorityOne.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityOne.ShowInterstitialBackAd(context, () -> {
                                interCloseCallBack.onAdsClose();
                            });
                        } else if (AllInterstitialAdPriorityTwo.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityTwo.ShowInterstitialBackAd(context, () -> {
                                interCloseCallBack.onAdsClose();
                            });
                        } else if (AllInterstitialAdPriorityThree.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityThree.ShowInterstitialBackAd(context, () -> {
                                interCloseCallBack.onAdsClose();
                            });
                        } else if (Comman.mainResModel != null && Comman.mainResModel.getData().getInterAds().size() != 0) {

                            showCustomInterAds(context, interCloseCallBack);
                        } else {
                            interCloseCallBack.onAdsClose();
                        }
                    } else if (alertnetAppOpenCounter == 1) {
                        --alertnetAppOpenCounter;
                        new AppOpenManager(SdkAppController.getInstance(), true).showAdIfAvailable(context, new InterCloseCallBack() {
                            @Override
                            public void onAdsClose() {
                                interCloseCallBack.onAdsClose();
                            }
                        });
                    } else {
                        if (AllInterstitialAdPriorityOne.AMInterstitial != null) {
                            AllInterstitialAdPriorityOne.ShowInterstitialBackAd(context, () -> {
                                interCloseCallBack.onAdsClose();
                            });
                        } else if (AllInterstitialAdPriorityTwo.AMInterstitial2 != null) {
                            AllInterstitialAdPriorityTwo.ShowInterstitialBackAd(context, () -> {
                                interCloseCallBack.onAdsClose();
                            });
                        } else if (AllInterstitialAdPriorityThree.AMInterstitial3 != null) {
                            AllInterstitialAdPriorityThree.ShowInterstitialBackAd(context, () -> {
                                interCloseCallBack.onAdsClose();
                            });
                        } else if (AllInterstitialAdPriorityOne.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityOne.ShowInterstitialBackAd(context, () -> {
                                interCloseCallBack.onAdsClose();
                            });
                        } else if (AllInterstitialAdPriorityTwo.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityTwo.ShowInterstitialBackAd(context, () -> {
                                interCloseCallBack.onAdsClose();
                            });
                        } else if (AllInterstitialAdPriorityThree.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityThree.ShowInterstitialBackAd(context, () -> {
                                interCloseCallBack.onAdsClose();
                            });
                        } else if (Comman.mainResModel != null && Comman.mainResModel.getData().getInterAds().size() != 0) {

                            showCustomInterAds(context, interCloseCallBack);
                        } else {
                            interCloseCallBack.onAdsClose();
                        }
                    }
                } else {
                    if (AllInterstitialAdPriorityOne.AMInterstitial != null) {
                        AllInterstitialAdPriorityOne.ShowInterstitialBackAd(context, () -> {
                            interCloseCallBack.onAdsClose();
                        });
                    } else if (AllInterstitialAdPriorityTwo.AMInterstitial2 != null) {
                        AllInterstitialAdPriorityTwo.ShowInterstitialBackAd(context, () -> {
                            interCloseCallBack.onAdsClose();
                        });
                    } else if (AllInterstitialAdPriorityThree.AMInterstitial3 != null) {
                        AllInterstitialAdPriorityThree.ShowInterstitialBackAd(context, () -> {
                            interCloseCallBack.onAdsClose();
                        });
                    } else if (AllInterstitialAdPriorityOne.isAppOpenAvailable()) {
                        AllInterstitialAdPriorityOne.ShowInterstitialBackAd(context, () -> {
                            interCloseCallBack.onAdsClose();
                        });
                    } else if (AllInterstitialAdPriorityTwo.isAppOpenAvailable()) {
                        AllInterstitialAdPriorityTwo.ShowInterstitialBackAd(context, () -> {
                            interCloseCallBack.onAdsClose();
                        });
                    } else if (AllInterstitialAdPriorityThree.isAppOpenAvailable()) {
                        AllInterstitialAdPriorityThree.ShowInterstitialBackAd(context, () -> {
                            interCloseCallBack.onAdsClose();
                        });
                    } else if (Comman.mainResModel != null && Comman.mainResModel.getData().getInterAds().size() != 0) {

                        showCustomInterAds(context, interCloseCallBack);
                    } else {
                        interCloseCallBack.onAdsClose();
                    }
                }
            } else {
                ++backFreqInterCounter;
                interCloseCallBack.onAdsClose();
            }
        } else {
            interCloseCallBack.onAdsClose();
        }

    }


    public static void ShowInterstitialAdsOnBack(Activity context) {
        if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getAds().equalsIgnoreCase("on")) {
            if (backFreqInterCounter != 0 && Integer.parseInt(Comman.mainResModel.getData().getExtraFields().getBackInterFrequency()) != 0 && backFreqInterCounter % Integer.parseInt(Comman.mainResModel.getData().getExtraFields().getBackInterFrequency()) == 0) {
                ++backFreqInterCounter;
                if (Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("off") && Comman.mainResModel.getData().getExtraFields().getAlternetAppOpen().equalsIgnoreCase("on")) {
                    if (alertnetAppOpenCounter == 0) {
                        ++alertnetAppOpenCounter;
                        if (AllInterstitialAdPriorityOne.AMInterstitial != null) {
                            AllInterstitialAdPriorityOne.ShowInterstitialBackAd(context, () -> {
                                context.finish();
                            });
                        } else if (AllInterstitialAdPriorityTwo.AMInterstitial2 != null) {
                            AllInterstitialAdPriorityTwo.ShowInterstitialBackAd(context, () -> {
                                context.finish();
                            });
                        } else if (AllInterstitialAdPriorityThree.AMInterstitial3 != null) {
                            AllInterstitialAdPriorityThree.ShowInterstitialBackAd(context, () -> {
                                context.finish();
                            });
                        } else if (AllInterstitialAdPriorityOne.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityOne.ShowInterstitialBackAd(context, () -> {
                                context.finish();
                            });
                        } else if (AllInterstitialAdPriorityTwo.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityTwo.ShowInterstitialBackAd(context, () -> {
                                context.finish();
                            });
                        } else if (AllInterstitialAdPriorityThree.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityThree.ShowInterstitialBackAd(context, () -> {
                                context.finish();
                            });
                        } else if (Comman.mainResModel != null && Comman.mainResModel.getData().getInterAds().size() != 0) {

                            showCustomInterAdswithFinish(context);
                        } else {
                            context.finish();
                        }

                    } else if (alertnetAppOpenCounter == 1) {
                        --alertnetAppOpenCounter;
                        new AppOpenManager(SdkAppController.getInstance(), true).showAdIfAvailable(context, new InterCloseCallBack() {
                            @Override
                            public void onAdsClose() {
                                context.finish();
                            }
                        });
                    } else {
                        if (AllInterstitialAdPriorityOne.AMInterstitial != null) {
                            AllInterstitialAdPriorityOne.ShowInterstitialBackAd(context, () -> {
                                context.finish();
                            });
                        } else if (AllInterstitialAdPriorityTwo.AMInterstitial2 != null) {
                            AllInterstitialAdPriorityTwo.ShowInterstitialBackAd(context, () -> {
                                context.finish();
                            });
                        } else if (AllInterstitialAdPriorityThree.AMInterstitial3 != null) {
                            AllInterstitialAdPriorityThree.ShowInterstitialBackAd(context, () -> {
                                context.finish();
                            });
                        } else if (AllInterstitialAdPriorityOne.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityOne.ShowInterstitialBackAd(context, () -> {
                                context.finish();
                            });
                        } else if (AllInterstitialAdPriorityTwo.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityTwo.ShowInterstitialBackAd(context, () -> {
                                context.finish();
                            });
                        } else if (AllInterstitialAdPriorityThree.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityThree.ShowInterstitialBackAd(context, () -> {
                                context.finish();
                            });
                        } else if (Comman.mainResModel != null && Comman.mainResModel.getData().getInterAds().size() != 0) {

                            showCustomInterAdswithFinish(context);
                        } else {
                            context.finish();
                        }
                    }
                } else {
                    if (AllInterstitialAdPriorityOne.AMInterstitial != null) {
                        AllInterstitialAdPriorityOne.ShowInterstitialBackAd(context, () -> {
                            context.finish();
                        });
                    } else if (AllInterstitialAdPriorityTwo.AMInterstitial2 != null) {
                        AllInterstitialAdPriorityTwo.ShowInterstitialBackAd(context, () -> {
                            context.finish();
                        });
                    } else if (AllInterstitialAdPriorityThree.AMInterstitial3 != null) {
                        AllInterstitialAdPriorityThree.ShowInterstitialBackAd(context, () -> {
                            context.finish();
                        });
                    } else if (AllInterstitialAdPriorityOne.isAppOpenAvailable()) {
                        AllInterstitialAdPriorityOne.ShowInterstitialBackAd(context, () -> {
                            context.finish();
                        });
                    } else if (AllInterstitialAdPriorityTwo.isAppOpenAvailable()) {
                        AllInterstitialAdPriorityTwo.ShowInterstitialBackAd(context, () -> {
                            context.finish();
                        });
                    } else if (AllInterstitialAdPriorityThree.isAppOpenAvailable()) {
                        AllInterstitialAdPriorityThree.ShowInterstitialBackAd(context, () -> {
                            context.finish();
                        });
                    } else if (Comman.mainResModel != null && Comman.mainResModel.getData().getInterAds().size() != 0) {

                        showCustomInterAdswithFinish(context);
                    } else {
                        context.finish();
                    }
                }


            } else if (Integer.parseInt(Comman.mainResModel.getData().getExtraFields().getBackInterFrequency()) != 0 && Integer.parseInt(Comman.mainResModel.getData().getExtraFields().getBackInterFrequency()) == 1) {
                if (Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("off") && Comman.mainResModel.getData().getExtraFields().getAlternetAppOpen().equalsIgnoreCase("on")) {
                    if (alertnetAppOpenCounter == 0) {
                        ++alertnetAppOpenCounter;
                        if (AllInterstitialAdPriorityOne.AMInterstitial != null) {
                            AllInterstitialAdPriorityOne.ShowInterstitialBackAd(context, () -> {
                                context.finish();
                            });
                        } else if (AllInterstitialAdPriorityTwo.AMInterstitial2 != null) {
                            AllInterstitialAdPriorityTwo.ShowInterstitialBackAd(context, () -> {
                                context.finish();
                            });
                        } else if (AllInterstitialAdPriorityThree.AMInterstitial3 != null) {
                            AllInterstitialAdPriorityThree.ShowInterstitialBackAd(context, () -> {
                                context.finish();
                            });
                        } else if (AllInterstitialAdPriorityOne.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityOne.ShowInterstitialBackAd(context, () -> {
                                context.finish();
                            });
                        } else if (AllInterstitialAdPriorityTwo.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityTwo.ShowInterstitialBackAd(context, () -> {
                                context.finish();
                            });
                        } else if (AllInterstitialAdPriorityThree.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityThree.ShowInterstitialBackAd(context, () -> {
                                context.finish();
                            });
                        } else if (Comman.mainResModel != null && Comman.mainResModel.getData().getInterAds().size() != 0) {

                            showCustomInterAdswithFinish(context);
                        } else {
                            context.finish();
                        }
                    } else if (alertnetAppOpenCounter == 1) {
                        --alertnetAppOpenCounter;
                        new AppOpenManager(SdkAppController.getInstance(), true).showAdIfAvailable(context, new InterCloseCallBack() {
                            @Override
                            public void onAdsClose() {
                                context.finish();
                            }
                        });
                    } else {
                        if (AllInterstitialAdPriorityOne.AMInterstitial != null) {
                            AllInterstitialAdPriorityOne.ShowInterstitialBackAd(context, () -> {
                                context.finish();
                            });
                        } else if (AllInterstitialAdPriorityTwo.AMInterstitial2 != null) {
                            AllInterstitialAdPriorityTwo.ShowInterstitialBackAd(context, () -> {
                                context.finish();
                            });
                        } else if (AllInterstitialAdPriorityThree.AMInterstitial3 != null) {
                            AllInterstitialAdPriorityThree.ShowInterstitialBackAd(context, () -> {
                                context.finish();
                            });
                        } else if (AllInterstitialAdPriorityOne.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityOne.ShowInterstitialBackAd(context, () -> {
                                context.finish();
                            });
                        } else if (AllInterstitialAdPriorityTwo.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityTwo.ShowInterstitialBackAd(context, () -> {
                                context.finish();
                            });
                        } else if (AllInterstitialAdPriorityThree.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityThree.ShowInterstitialBackAd(context, () -> {
                                context.finish();
                            });
                        } else if (Comman.mainResModel != null && Comman.mainResModel.getData().getInterAds().size() != 0) {

                            showCustomInterAdswithFinish(context);
                        } else {
                            context.finish();
                        }
                    }
                } else {
                    if (AllInterstitialAdPriorityOne.AMInterstitial != null) {
                        AllInterstitialAdPriorityOne.ShowInterstitialBackAd(context, () -> {
                            context.finish();
                        });
                    } else if (AllInterstitialAdPriorityTwo.AMInterstitial2 != null) {
                        AllInterstitialAdPriorityTwo.ShowInterstitialBackAd(context, () -> {
                            context.finish();
                        });
                    } else if (AllInterstitialAdPriorityThree.AMInterstitial3 != null) {
                        AllInterstitialAdPriorityThree.ShowInterstitialBackAd(context, () -> {
                            context.finish();
                        });
                    } else if (AllInterstitialAdPriorityOne.isAppOpenAvailable()) {
                        AllInterstitialAdPriorityOne.ShowInterstitialBackAd(context, () -> {
                            context.finish();
                        });
                    } else if (AllInterstitialAdPriorityTwo.isAppOpenAvailable()) {
                        AllInterstitialAdPriorityTwo.ShowInterstitialBackAd(context, () -> {
                            context.finish();
                        });
                    } else if (AllInterstitialAdPriorityThree.isAppOpenAvailable()) {
                        AllInterstitialAdPriorityThree.ShowInterstitialBackAd(context, () -> {
                            context.finish();
                        });
                    } else if (Comman.mainResModel != null && Comman.mainResModel.getData().getInterAds().size() != 0) {

                        showCustomInterAdswithFinish(context);
                    } else {
                        context.finish();
                    }
                }
            } else {
                ++backFreqInterCounter;
                context.finish();
            }
        } else {
            context.finish();
        }

    }

    public static void ShowInterstitialAdsOnBack(Activity context, Class destClass) {
        ModelPrefrences modelPrefrences = new ModelPrefrences(context);
        if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getAds().equalsIgnoreCase("on")) {
            if (backFreqInterCounter != 0 && Integer.parseInt(Comman.mainResModel.getData().getExtraFields().getBackInterFrequency()) != 0 && backFreqInterCounter % Integer.parseInt(Comman.mainResModel.getData().getExtraFields().getBackInterFrequency()) == 0) {
                ++backFreqInterCounter;
                if (Comman.mainResModel.getData().getExtraFields().getComboAds().equalsIgnoreCase("off") && Comman.mainResModel.getData().getExtraFields().getAlternetAppOpen().equalsIgnoreCase("on")) {
                    if (alertnetAppOpenCounter == 0) {
                        ++alertnetAppOpenCounter;
                        if (AllInterstitialAdPriorityOne.AMInterstitial != null) {
                            AllInterstitialAdPriorityOne.ShowInterstitialBackAd(context, () -> {
                                Intent intent = new Intent(context, destClass);
                                context.startActivity(intent);
                            });
                        } else if (AllInterstitialAdPriorityTwo.AMInterstitial2 != null) {
                            AllInterstitialAdPriorityTwo.ShowInterstitialBackAd(context, () -> {
                                Intent intent = new Intent(context, destClass);
                                context.startActivity(intent);
                            });
                        } else if (AllInterstitialAdPriorityThree.AMInterstitial3 != null) {
                            AllInterstitialAdPriorityThree.ShowInterstitialBackAd(context, () -> {
                                Intent intent = new Intent(context, destClass);
                                context.startActivity(intent);
                            });
                        } else if (AllInterstitialAdPriorityOne.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityOne.ShowInterstitialBackAd(context, () -> {
                                Intent intent = new Intent(context, destClass);
                                context.startActivity(intent);
                            });
                        } else if (AllInterstitialAdPriorityTwo.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityTwo.ShowInterstitialBackAd(context, () -> {
                                Intent intent = new Intent(context, destClass);
                                context.startActivity(intent);
                            });
                        } else if (AllInterstitialAdPriorityThree.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityThree.ShowInterstitialBackAd(context, () -> {
                                Intent intent = new Intent(context, destClass);
                                context.startActivity(intent);
                            });
                        } else if (Comman.mainResModel != null && Comman.mainResModel.getData().getInterAds().size() != 0) {

                            showCustomInterAdswithDestClass(context, destClass);
                        } else {
                            Intent intent = new Intent(context, destClass);
                            context.startActivity(intent);
                        }
                    } else if (alertnetAppOpenCounter == 1) {
                        --alertnetAppOpenCounter;
                        new AppOpenManager(SdkAppController.getInstance(), true).showAdIfAvailable(context, new InterCloseCallBack() {
                            @Override
                            public void onAdsClose() {
                                Intent intent = new Intent(context, destClass);
                                context.startActivity(intent);
                            }
                        });
                    } else {
                        if (AllInterstitialAdPriorityOne.AMInterstitial != null) {
                            AllInterstitialAdPriorityOne.ShowInterstitialBackAd(context, () -> {
                                Intent intent = new Intent(context, destClass);
                                context.startActivity(intent);
                            });
                        } else if (AllInterstitialAdPriorityTwo.AMInterstitial2 != null) {
                            AllInterstitialAdPriorityTwo.ShowInterstitialBackAd(context, () -> {
                                Intent intent = new Intent(context, destClass);
                                context.startActivity(intent);
                            });
                        } else if (AllInterstitialAdPriorityThree.AMInterstitial3 != null) {
                            AllInterstitialAdPriorityThree.ShowInterstitialBackAd(context, () -> {
                                Intent intent = new Intent(context, destClass);
                                context.startActivity(intent);
                            });
                        } else if (AllInterstitialAdPriorityOne.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityOne.ShowInterstitialBackAd(context, () -> {
                                Intent intent = new Intent(context, destClass);
                                context.startActivity(intent);
                            });
                        } else if (AllInterstitialAdPriorityTwo.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityTwo.ShowInterstitialBackAd(context, () -> {
                                Intent intent = new Intent(context, destClass);
                                context.startActivity(intent);
                            });
                        } else if (AllInterstitialAdPriorityThree.isAppOpenAvailable()) {
                            AllInterstitialAdPriorityThree.ShowInterstitialBackAd(context, () -> {
                                Intent intent = new Intent(context, destClass);
                                context.startActivity(intent);
                            });
                        } else if (Comman.mainResModel != null && Comman.mainResModel.getData().getInterAds().size() != 0) {

                            showCustomInterAdswithDestClass(context, destClass);
                        } else {
                            Intent intent = new Intent(context, destClass);
                            context.startActivity(intent);
                        }
                    }
                } else {
                    if (AllInterstitialAdPriorityOne.AMInterstitial != null) {
                        AllInterstitialAdPriorityOne.ShowInterstitialBackAd(context, () -> {
                            Intent intent = new Intent(context, destClass);
                            context.startActivity(intent);
                        });
                    } else if (AllInterstitialAdPriorityTwo.AMInterstitial2 != null) {
                        AllInterstitialAdPriorityTwo.ShowInterstitialBackAd(context, () -> {
                            Intent intent = new Intent(context, destClass);
                            context.startActivity(intent);
                        });
                    } else if (AllInterstitialAdPriorityThree.AMInterstitial3 != null) {
                        AllInterstitialAdPriorityThree.ShowInterstitialBackAd(context, () -> {
                            Intent intent = new Intent(context, destClass);
                            context.startActivity(intent);
                        });
                    } else if (AllInterstitialAdPriorityOne.isAppOpenAvailable()) {
                        AllInterstitialAdPriorityOne.ShowInterstitialBackAd(context, () -> {
                            Intent intent = new Intent(context, destClass);
                            context.startActivity(intent);
                        });
                    } else if (AllInterstitialAdPriorityTwo.isAppOpenAvailable()) {
                        AllInterstitialAdPriorityTwo.ShowInterstitialBackAd(context, () -> {
                            Intent intent = new Intent(context, destClass);
                            context.startActivity(intent);
                        });
                    } else if (AllInterstitialAdPriorityThree.isAppOpenAvailable()) {
                        AllInterstitialAdPriorityThree.ShowInterstitialBackAd(context, () -> {
                            Intent intent = new Intent(context, destClass);
                            context.startActivity(intent);
                        });
                    } else if (Comman.mainResModel != null && Comman.mainResModel.getData().getInterAds().size() != 0) {

                        showCustomInterAdswithDestClass(context, destClass);
                    } else {
                        Intent intent = new Intent(context, destClass);
                        context.startActivity(intent);
                    }
                }


            } else if (Integer.parseInt(Comman.mainResModel.getData().getExtraFields().getBackInterFrequency()) == 1) {
                if (AllInterstitialAdPriorityOne.AMInterstitial != null) {
                    AllInterstitialAdPriorityOne.ShowInterstitialBackAd(context, () -> {
                        Intent intent = new Intent(context, destClass);
                        context.startActivity(intent);
                    });
                } else if (AllInterstitialAdPriorityTwo.AMInterstitial2 != null) {
                    AllInterstitialAdPriorityTwo.ShowInterstitialBackAd(context, () -> {
                        Intent intent = new Intent(context, destClass);
                        context.startActivity(intent);
                    });
                } else if (AllInterstitialAdPriorityThree.AMInterstitial3 != null) {
                    AllInterstitialAdPriorityThree.ShowInterstitialBackAd(context, () -> {
                        Intent intent = new Intent(context, destClass);
                        context.startActivity(intent);
                    });
                } else if (AllInterstitialAdPriorityOne.isAppOpenAvailable()) {
                    AllInterstitialAdPriorityOne.ShowInterstitialBackAd(context, () -> {
                        Intent intent = new Intent(context, destClass);
                        context.startActivity(intent);
                    });
                } else if (AllInterstitialAdPriorityTwo.isAppOpenAvailable()) {
                    AllInterstitialAdPriorityTwo.ShowInterstitialBackAd(context, () -> {
                        Intent intent = new Intent(context, destClass);
                        context.startActivity(intent);
                    });
                } else if (AllInterstitialAdPriorityThree.isAppOpenAvailable()) {
                    AllInterstitialAdPriorityThree.ShowInterstitialBackAd(context, () -> {
                        Intent intent = new Intent(context, destClass);
                        context.startActivity(intent);
                    });
                } else if (Comman.mainResModel != null && Comman.mainResModel.getData().getInterAds().size() != 0) {

                    showCustomInterAdswithDestClass(context, destClass);
                } else {
                    Intent intent = new Intent(context, destClass);
                    context.startActivity(intent);
                }
            } else {
                ++backFreqInterCounter;
                Intent intent = new Intent(context, destClass);
                context.startActivity(intent);
            }
        } else {
            Intent intent = new Intent(context, destClass);
            context.startActivity(intent);
        }

    }

    public static void clearAll(Context context) {
        intertitialCounter = 0;
        alertnetAppOpenCounter = 0;
        alternetBackAppOpenCounter = 0;
        AllInterstitialAdPriorityOne.clearInstance();
        AllInterstitialAdPriorityTwo.clearInstance();
        AllInterstitialAdPriorityThree.clearInstance();
        AllNativeAds.clearInstance();

    }


    public static boolean getAds() {
        if (AllInterstitialAdPriorityOne.AMInterstitial != null) {
            return true;
        } else if (AllInterstitialAdPriorityTwo.AMInterstitial2 != null) {
            return true;
        } else if (AllInterstitialAdPriorityThree.AMInterstitial3 != null) {
            return true;
        } else
            return false;
    }

    static CountDownTimer countDownTimer;

    public static void waiteInterCall(Context context, InterCloseCallBack interCloseCallBack) {


        if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getAds().equalsIgnoreCase("on")) {
            if (createFreqInterCounter != 0 && Integer.parseInt(Comman.mainResModel.getData().getExtraFields().getInterFrequency()) != 0 && createFreqInterCounter % Integer.parseInt(Comman.mainResModel.getData().getExtraFields().getInterFrequency()) == 0) {
                if (getAds()) {
                    interCloseCallBack.onAdsClose();
                } else {
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.loading_dialog);
                    dialog.setCancelable(false);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    Window window = dialog.getWindow();
                    WindowManager.LayoutParams wlp = window.getAttributes();
                    wlp.gravity = Gravity.CENTER;
                    window.setAttributes(wlp);
                    dialog.show();

                    countDownTimer = new CountDownTimer(4000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            if (getAds()) {
                                countDownTimer.cancel();
                                countDownTimer.onFinish();
                            }

                        }

                        @Override
                        public void onFinish() {
                            dialog.dismiss();
                            interCloseCallBack.onAdsClose();
                        }
                    };
                    countDownTimer.start();
                }

            } else if (Integer.parseInt(Comman.mainResModel.getData().getExtraFields().getInterFrequency()) == 1) {
                if (getAds()) {
                    interCloseCallBack.onAdsClose();
                } else {
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.loading_dialog);
                    dialog.setCancelable(false);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    Window window = dialog.getWindow();
                    WindowManager.LayoutParams wlp = window.getAttributes();
                    wlp.gravity = Gravity.CENTER;
                    window.setAttributes(wlp);
                    dialog.show();

                    countDownTimer = new CountDownTimer(4000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            if (getAds()) {
                                countDownTimer.cancel();
                                countDownTimer.onFinish();
                            }

                        }

                        @Override
                        public void onFinish() {
                            dialog.dismiss();
                            interCloseCallBack.onAdsClose();
                        }
                    };
                    countDownTimer.start();
                }
            } else {
                interCloseCallBack.onAdsClose();
            }
        } else {
            interCloseCallBack.onAdsClose();
        }


    }

    public static void showCustomInterAds(Context context, InterCloseCallBack adsListener1) {
        ShowInterstitialAds showInterstitialAds = new ShowInterstitialAds((Activity) context, new setOnBackPressListener.OnNoRecordFoundListener() {
            @Override
            public void onNoRecordFound() {
                if (adsListener1 != null)
                    adsListener1.onAdsClose();
            }

            @Override
            public void onAdsClose() {
                if (adsListener1 != null)
                    adsListener1.onAdsClose();
            }
        }, "01");
        showInterstitialAds.InterstitialAds();

    }

    public static void showCustomInterAdswithFinish(Context context) {
        ShowInterstitialAds showInterstitialAds = new ShowInterstitialAds((Activity) context, new setOnBackPressListener.OnNoRecordFoundListener() {
            @Override
            public void onNoRecordFound() {
                ((Activity) context).finish();
            }

            @Override
            public void onAdsClose() {
                ((Activity) context).finish();
            }
        }, "01");
        showInterstitialAds.InterstitialAds();

    }

    public static void showCustomInterAdswithDestClass(Context context, Class DestinationClass) {
        ShowInterstitialAds showInterstitialAds = new ShowInterstitialAds((Activity) context, new setOnBackPressListener.OnNoRecordFoundListener() {
            @Override
            public void onNoRecordFound() {
                Intent intent = new Intent(context, DestinationClass);
                context.startActivity(intent);
            }

            @Override
            public void onAdsClose() {
                Intent intent = new Intent(context, DestinationClass);
                context.startActivity(intent);
            }
        }, "01");
        showInterstitialAds.InterstitialAds();

    }

}
