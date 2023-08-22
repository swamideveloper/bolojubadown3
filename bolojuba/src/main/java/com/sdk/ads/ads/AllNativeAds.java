package com.sdk.ads.ads;

import android.app.Activity;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.NativeAd;
import com.sdk.ads.Comman;
import com.sdk.ads.LocalSave.ModelPrefrences;
import com.sdk.ads.R;
import com.sdk.ads.ResModel.AdsUnit;
import com.sdk.ads.ResModel.CustomAd;
import com.sdk.ads.databinding.AdsAmActivityNativeAdsTempBinding;
import com.sdk.ads.databinding.AdsAmActivityNativeAdsTempNewBinding;
import com.sdk.ads.databinding.SdkDefaultAdsContainer12Binding;
import com.sdk.ads.databinding.SdkDefaultAdsContainer1Binding;
import com.sdk.ads.databinding.SdkDefaultAdsContainerBinding;


import java.util.ArrayList;

public class AllNativeAds {

    //========================================================= Native Banner Ads Code ================================================
    private static NativeAd unifiedNativeBannerAd;
    private static NativeAd unifiedNativeAd;
    private static AdView largeBannerAdView, rectangleAdView;
    private static int showNativeCounter, showNativeBannerCounter;

    //========================================================= Native Ads Code =======================================================


    public static void NativeBannerAdLoad(final Context context) {

        ModelPrefrences modelPrefrences = new ModelPrefrences(context);
        if (!modelPrefrences.getPurchase() && Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getAds().equalsIgnoreCase("on")) {
            ArrayList<AdsUnit> adsUnits = (ArrayList<AdsUnit>) Comman.mainResModel.getData().getAdsUnits();
            IntertitialAdsEvent.NativeKeyBanner = adsUnits.get(0).getNativeFrequency();
            if (!IntertitialAdsEvent.NativeKeyBanner.equalsIgnoreCase("")) {
                AdLoader.Builder builder = new AdLoader.Builder(context, IntertitialAdsEvent.NativeKeyBanner);

                builder.forNativeAd(_unifiedNativeAd -> {
                    unifiedNativeBannerAd = _unifiedNativeAd;

                });

                AdLoader adLoader = builder.withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {
                        unifiedNativeBannerAd = null;
                    }

                    @Override
                    public void onAdImpression() {

                        unifiedNativeBannerAd = null;
                        NativeBannerAdLoad(context);

                        super.onAdImpression();
                    }

                    @Override
                    public void onAdLoaded() {

                        super.onAdLoaded();
                    }
                }).build();
                adLoader.loadAd(new AdRequest.Builder().build());
            }


        }
    }


    public static void LargeBannerAdLoad(final Context context) {

        ModelPrefrences modelPrefrences = new ModelPrefrences(context);
        if (!modelPrefrences.getPurchase() &&Comman.mainResModel!=null&& Comman.mainResModel.getData().getExtraFields().getAds().equalsIgnoreCase("on")) {
            ArrayList<AdsUnit> adsUnits = (ArrayList<AdsUnit>) Comman.mainResModel.getData().getAdsUnits();
            IntertitialAdsEvent.LargeBanner = adsUnits.get(0).getBummer();
            if (!IntertitialAdsEvent.LargeBanner.equalsIgnoreCase("")) {
                largeBannerAdView = new AdView(context);
                largeBannerAdView.setAdUnitId(IntertitialAdsEvent.LargeBanner);
                largeBannerAdView.setAdSize(AdSize.LARGE_BANNER);
                largeBannerAdView.loadAd(new AdRequest.Builder().build());
                largeBannerAdView.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();

                    }

                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {

                        largeBannerAdView = null;

                    }

                    @Override
                    public void onAdImpression() {

                        super.onAdImpression();

                    }
                });
            }

        }
    }

    public static void NativeBanner(final Activity context, final ViewGroup BannerContainer) {
        LayoutInflater inflater = LayoutInflater.from(context);

        SdkDefaultAdsContainer12Binding bindingdefault = DataBindingUtil.inflate(inflater, R.layout.sdk_default_ads_container12, BannerContainer, false);
        BannerContainer.removeAllViews();
        BannerContainer.addView(bindingdefault.getRoot());
        ModelPrefrences modelPrefrences = new ModelPrefrences(context);
        if (!modelPrefrences.getPurchase() &&Comman.mainResModel!=null&& Comman.mainResModel.getData().getExtraFields().getAds().equalsIgnoreCase("on")) {
            if (Comman.mainResModel.getData().getExtraFields().getNativeOnOff().equalsIgnoreCase("on")) {
                if (showNativeBannerCounter != 0 && Integer.parseInt(Comman.mainResModel.getData().getExtraFields().getNativeFrequency()) != 0 && showNativeBannerCounter % Integer.parseInt(Comman.mainResModel.getData().getExtraFields().getNativeFrequency()) == 0) {
                    ++showNativeBannerCounter;

                    if (unifiedNativeBannerAd != null) {
                        AllDimen allDimen = new AllDimen(context);
                        CustomAd customAd = Comman.mainResModel.getData().getCustomAds().get(1);
                        AdsAmActivityNativeAdsTempBinding binding = DataBindingUtil.inflate(inflater, R.layout.ads_am_activity_native_ads_temp, BannerContainer, false);
                        binding.cardLargeTemplate.setVisibility(View.GONE);
                        binding.cardSmallTemplate.setVisibility(View.VISIBLE);
                        BannerContainer.removeAllViews();
                        BannerContainer.addView(binding.getRoot());
                        NativeTemplateStyle styles = new NativeTemplateStyle.Builder().build();
                        binding.myTemplateSmall.setCustomeStyle(styles);
                        binding.myTemplateSmall.setNativeAd(unifiedNativeBannerAd);
                    }
                    else if (largeBannerAdView != null) {
                        LayoutInflater inflater1 = LayoutInflater.from(context);
                        SdkDefaultAdsContainer12Binding bindingdefault1 = DataBindingUtil.inflate(inflater1, R.layout.sdk_default_ads_container12, BannerContainer, false);
                        bindingdefault1.banner.setVisibility(View.GONE);
                        bindingdefault1.largeContainer.setVisibility(View.VISIBLE);
                        bindingdefault1.largeContainer.removeAllViews();
                        bindingdefault1.largeContainer.addView(largeBannerAdView);
                        BannerContainer.removeAllViews();
                        BannerContainer.addView(bindingdefault1.getRoot());
                        largeBannerAdView = null;
                        LargeBannerAdLoad(context);
                    }
                    else {
                        NativeBannerOld(context, BannerContainer);
                    }
                } else if (Comman.mainResModel!=null&&Integer.parseInt(Comman.mainResModel.getData().getExtraFields().getNativeFrequency()) == 1) {
                    if (unifiedNativeBannerAd != null) {
                        AllDimen allDimen = new AllDimen(context);
                        CustomAd customAd = Comman.mainResModel.getData().getCustomAds().get(1);
                        AdsAmActivityNativeAdsTempBinding binding = DataBindingUtil.inflate(inflater, R.layout.ads_am_activity_native_ads_temp, BannerContainer, false);
                        binding.cardLargeTemplate.setVisibility(View.GONE);
                        binding.cardSmallTemplate.setVisibility(View.VISIBLE);
                        BannerContainer.removeAllViews();
                        BannerContainer.addView(binding.getRoot());
                        NativeTemplateStyle styles = new NativeTemplateStyle.Builder().build();
                        binding.myTemplateSmall.setCustomeStyle(styles);
                        binding.myTemplateSmall.setNativeAd(unifiedNativeBannerAd);
                    }
                    else if (largeBannerAdView != null) {
                        LayoutInflater inflater1 = LayoutInflater.from(context);
                        SdkDefaultAdsContainer12Binding bindingdefault1 = DataBindingUtil.inflate(inflater1, R.layout.sdk_default_ads_container12, BannerContainer, false);
                        bindingdefault1.banner.setVisibility(View.GONE);
                        bindingdefault1.largeContainer.setVisibility(View.VISIBLE);
                        bindingdefault1.largeContainer.removeAllViews();
                        bindingdefault1.largeContainer.addView(largeBannerAdView);
                        BannerContainer.removeAllViews();
                        BannerContainer.addView(bindingdefault1.getRoot());
                        largeBannerAdView = null;
                        LargeBannerAdLoad(context);
                    }
                    else {
                        NativeBannerOld(context, BannerContainer);
                    }
                } else {
                    ++showNativeBannerCounter;
                }
            } else {
                if (Comman.mainResModel!=null&&showNativeBannerCounter != 0 && Integer.parseInt(Comman.mainResModel.getData().getExtraFields().getNativeFrequency()) != 0 && showNativeBannerCounter % Integer.parseInt(Comman.mainResModel.getData().getExtraFields().getNativeFrequency()) == 0) {
                    ++showNativeBannerCounter;
                    if (largeBannerAdView != null) {
                        LayoutInflater inflater1 = LayoutInflater.from(context);
                        SdkDefaultAdsContainer12Binding bindingdefault1 = DataBindingUtil.inflate(inflater1, R.layout.sdk_default_ads_container12, BannerContainer, false);
                        bindingdefault1.banner.setVisibility(View.GONE);
                        bindingdefault1.largeContainer.setVisibility(View.VISIBLE);
                        bindingdefault1.largeContainer.removeAllViews();
                        bindingdefault1.largeContainer.addView(largeBannerAdView);
                        BannerContainer.removeAllViews();
                        BannerContainer.addView(bindingdefault1.getRoot());
                        largeBannerAdView = null;
                        LargeBannerAdLoad(context);
                    } else {
                        NativeBannerOld(context, BannerContainer);
                    }
                } else if (Comman.mainResModel!=null&&Integer.parseInt(Comman.mainResModel.getData().getExtraFields().getNativeFrequency()) == 1) {
                    if (largeBannerAdView != null) {
                        LayoutInflater inflater1 = LayoutInflater.from(context);
                        SdkDefaultAdsContainer12Binding bindingdefault1 = DataBindingUtil.inflate(inflater1, R.layout.sdk_default_ads_container12, BannerContainer, false);
                        bindingdefault1.banner.setVisibility(View.GONE);
                        bindingdefault1.largeContainer.setVisibility(View.VISIBLE);
                        bindingdefault1.largeContainer.removeAllViews();
                        bindingdefault1.largeContainer.addView(largeBannerAdView);
                        BannerContainer.removeAllViews();
                        BannerContainer.addView(bindingdefault1.getRoot());
                        largeBannerAdView = null;
                        LargeBannerAdLoad(context);
                    } else {
                        NativeBannerOld(context, BannerContainer);
                    }
                } else {
                    ++showNativeBannerCounter;
                }
            }
        }
    }

    private static void NativeBannerOld(final Context context, final ViewGroup BannerContainer) {
        LayoutInflater inflater = LayoutInflater.from(context);
        SdkDefaultAdsContainer1Binding bindingdefault = DataBindingUtil.inflate(inflater, R.layout.sdk_default_ads_container1, BannerContainer, false);

        BannerContainer.removeAllViews();
        BannerContainer.addView(bindingdefault.getRoot());
        ModelPrefrences modelPrefrences = new ModelPrefrences(context);
        if (!modelPrefrences.getPurchase() &&Comman.mainResModel!=null&& Comman.mainResModel.getData().getExtraFields().getAds().equalsIgnoreCase("on")) {
            if (Comman.mainResModel!=null&&Comman.mainResModel.getData().getExtraFields().getNativeOnOff().equalsIgnoreCase("on")) {
                ArrayList<AdsUnit> adsUnits = (ArrayList<AdsUnit>) Comman.mainResModel.getData().getAdsUnits();

                if (!adsUnits.get(0).getNativeFrequency().equalsIgnoreCase("")) {
                    try {
                        AdLoader.Builder builder = new AdLoader.Builder(context, adsUnits.get(0).getNativeFrequency());

                        builder.forNativeAd(unifiedNativeAd -> {
                            AllDimen allDimen = new AllDimen(context);
                            CustomAd customAd = Comman.mainResModel.getData().getCustomAds().get(1);
                            AdsAmActivityNativeAdsTempBinding binding = DataBindingUtil.inflate(inflater, R.layout.ads_am_activity_native_ads_temp, BannerContainer, false);
                            binding.cardLargeTemplate.setVisibility(View.GONE);
                            binding.cardSmallTemplate.setVisibility(View.VISIBLE);
                            BannerContainer.removeAllViews();
                            BannerContainer.addView(binding.getRoot());
                            NativeTemplateStyle styles = new NativeTemplateStyle.Builder().build();
                            binding.myTemplateSmall.setCustomeStyle(styles);
                            binding.myTemplateSmall.setNativeAd(unifiedNativeAd);

                        });

                        AdLoader adLoader = builder.withAdListener(new AdListener() {
                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError errorCode) {



                            }
                        }).build();

                        adLoader.loadAd(new AdRequest.Builder().build());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {

                    ArrayList<AdsUnit> adsUnits = (ArrayList<AdsUnit>) Comman.mainResModel.getData().getAdsUnits();
                    if (!adsUnits.get(0).getBummer().equalsIgnoreCase("")) {
                        AdView adView1001 = new AdView(context);
                        adView1001.setAdUnitId(adsUnits.get(0).getBummer());
                        adView1001.setAdSize(AdSize.LARGE_BANNER);
                        adView1001.loadAd(new AdRequest.Builder().build());
                        adView1001.setAdListener(new AdListener() {
                            @Override
                            public void onAdLoaded() {
                                super.onAdLoaded();
                                BannerContainer.removeAllViews();
                                BannerContainer.addView(adView1001);
                            }

                            @Override
                            public void onAdFailedToLoad(LoadAdError adError) {


                            }

                            @Override
                            public void onAdImpression() {
                                super.onAdImpression();
                            }
                        });
                    }

            }
        }

    }
    public static void RectangleLoad(final Context context) {
        ModelPrefrences modelPrefrences = new ModelPrefrences(context);
        if (!modelPrefrences.getPurchase() &&Comman.mainResModel!=null&& Comman.mainResModel.getData().getExtraFields().getAds().equalsIgnoreCase("on")) {

            ArrayList<AdsUnit> adsUnits = (ArrayList<AdsUnit>) Comman.mainResModel.getData().getAdsUnits();
            IntertitialAdsEvent.LargeBanner = adsUnits.get(0).getBummer();
            if (!IntertitialAdsEvent.LargeBanner.equalsIgnoreCase("")) {
                rectangleAdView = new AdView(context);
                rectangleAdView.setAdUnitId(IntertitialAdsEvent.LargeBanner);
                rectangleAdView.setAdSize(AdSize.MEDIUM_RECTANGLE);
                rectangleAdView.loadAd(new AdRequest.Builder().build());
                rectangleAdView.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();

                    }

                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {

                        rectangleAdView = null;

                    }

                    @Override
                    public void onAdImpression() {

                        super.onAdImpression();

                    }
                });
            }
        }
    }
    public static void RectangleLoadNew(final Context context) {
        ModelPrefrences modelPrefrences = new ModelPrefrences(context);
        if (!modelPrefrences.getPurchase() &&Comman.mainResModel!=null&& Comman.mainResModel.getData().getExtraFields().getAds().equalsIgnoreCase("on")) {

            ArrayList<AdsUnit> adsUnits = (ArrayList<AdsUnit>) Comman.mainResModel.getData().getAdsUnits();
            IntertitialAdsEvent.LargeBanner = adsUnits.get(0).getBummer();
            if (!IntertitialAdsEvent.LargeBanner.equalsIgnoreCase("")) {
                rectangleAdView = new AdView(context);
                rectangleAdView.setAdUnitId(IntertitialAdsEvent.LargeBanner);
                rectangleAdView.setAdSize(AdSize.MEDIUM_RECTANGLE);
                rectangleAdView.loadAd(new AdRequest.Builder().build());
                rectangleAdView.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();

                    }

                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {

                        rectangleAdView = null;

                    }

                    @Override
                    public void onAdImpression() {

                        super.onAdImpression();

                    }
                });
            }
        }
    }
    public static void NativeAdsLoad(final Context context) {
        ModelPrefrences modelPrefrences = new ModelPrefrences(context);
        if (!modelPrefrences.getPurchase() &&Comman.mainResModel!=null&& Comman.mainResModel.getData().getExtraFields().getAds().equalsIgnoreCase("on")) {
            ArrayList<AdsUnit> adsUnits = (ArrayList<AdsUnit>) Comman.mainResModel.getData().getAdsUnits();
            IntertitialAdsEvent.NativeKey = adsUnits.get(0).getNativeFrequency();
            AdLoader.Builder builder = new AdLoader.Builder(context, IntertitialAdsEvent.NativeKey);

            builder.forNativeAd(_unifiedNativeAd -> {
                unifiedNativeAd = _unifiedNativeAd;

            });

            AdLoader adLoader = builder.withAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(LoadAdError adError) {

                    unifiedNativeAd = null;


                }

                @Override
                public void onAdImpression() {

                    unifiedNativeAd = null;
                    NativeAdsLoad(context);
                    super.onAdImpression();
                }

                @Override
                public void onAdLoaded() {

                    super.onAdLoaded();
                }
            }).build();
            adLoader.loadAd(new AdRequest.Builder().build());

        }

    }
    public static void NativeAds(final Activity context, final ViewGroup BannerContainer) {
        try {
            ModelPrefrences modelPrefrences = new ModelPrefrences(context);
            LayoutInflater inflater = LayoutInflater.from(context);
            SdkDefaultAdsContainerBinding bindingdefault = DataBindingUtil.inflate(inflater, R.layout.sdk_default_ads_container, BannerContainer, false);
            BannerContainer.removeAllViews();
            BannerContainer.addView(bindingdefault.getRoot());
            if (!modelPrefrences.getPurchase() &&Comman.mainResModel!=null&& Comman.mainResModel.getData().getExtraFields().getAds().equalsIgnoreCase("on")) {
                if (Comman.mainResModel.getData().getExtraFields().getNativeOnOff().equalsIgnoreCase("on")) {
                    if (showNativeCounter != 0 && Integer.parseInt(Comman.mainResModel.getData().getExtraFields().getNativeFrequency()) != 0 && showNativeCounter % Integer.parseInt(Comman.mainResModel.getData().getExtraFields().getNativeFrequency()) == 0) {

                        ++showNativeCounter;

                        if (unifiedNativeAd != null) {
                            AllDimen allDimen = new AllDimen(context);
                            CustomAd customAd = Comman.mainResModel.getData().getCustomAds().get(0);
                            AdsAmActivityNativeAdsTempBinding binding = DataBindingUtil.inflate(inflater, R.layout.ads_am_activity_native_ads_temp, BannerContainer, false);

                            binding.cardLargeTemplate.setVisibility(View.VISIBLE);
                            binding.cardSmallTemplate.setVisibility(View.GONE);
                            BannerContainer.removeAllViews();
                            BannerContainer.addView(binding.getRoot());
                            NativeTemplateStyle styles = new NativeTemplateStyle.Builder().build();
                            binding.myTemplateLarge.setCustomeStyle(styles);
                            binding.myTemplateLarge.setNativeAd(unifiedNativeAd);
                        } else if (rectangleAdView != null) {
                            LayoutInflater inflater1 = LayoutInflater.from(context);
                            SdkDefaultAdsContainerBinding bindingdefault1 = DataBindingUtil.inflate(inflater1, R.layout.sdk_default_ads_container, BannerContainer, false);
                            bindingdefault1.imgContainer.setVisibility(View.GONE);
                            bindingdefault1.rectContainer.setVisibility(View.VISIBLE);
                            bindingdefault1.rectContainer.removeAllViews();
                            bindingdefault1.rectContainer.addView(rectangleAdView);
                            BannerContainer.removeAllViews();
                            BannerContainer.addView(bindingdefault1.getRoot());
                            rectangleAdView = null;
                            RectangleLoad(context);
                        } else {
                            NativeAdsOld(context, BannerContainer);
                        }
                    } else if (Integer.parseInt(Comman.mainResModel.getData().getExtraFields().getNativeFrequency()) != 0 && Integer.parseInt(Comman.mainResModel.getData().getExtraFields().getNativeFrequency()) == 1) {
                        if (unifiedNativeAd != null) {
                            AllDimen allDimen = new AllDimen(context);
                            CustomAd customAd = Comman.mainResModel.getData().getCustomAds().get(0);


                            AdsAmActivityNativeAdsTempBinding binding = DataBindingUtil.inflate(inflater, R.layout.ads_am_activity_native_ads_temp, BannerContainer, false);
                            binding.cardLargeTemplate.setVisibility(View.VISIBLE);
                            binding.cardSmallTemplate.setVisibility(View.GONE);
                            BannerContainer.removeAllViews();
                            BannerContainer.addView(binding.getRoot());
                            NativeTemplateStyle styles = new NativeTemplateStyle.Builder().build();
                            binding.myTemplateLarge.setCustomeStyle(styles);
                            binding.myTemplateLarge.setNativeAd(unifiedNativeAd);
                        }
                        else if (rectangleAdView != null) {
                            LayoutInflater inflater1 = LayoutInflater.from(context);
                            SdkDefaultAdsContainerBinding bindingdefault1 = DataBindingUtil.inflate(inflater1, R.layout.sdk_default_ads_container, BannerContainer, false);
                            bindingdefault1.imgContainer.setVisibility(View.GONE);
                            bindingdefault1.rectContainer.setVisibility(View.VISIBLE);
                            bindingdefault1.rectContainer.removeAllViews();
                            bindingdefault1.rectContainer.addView(rectangleAdView);
                            BannerContainer.removeAllViews();
                            BannerContainer.addView(bindingdefault1.getRoot());
                            rectangleAdView = null;
                            RectangleLoad(context);
                        }
                        else {
                            NativeAdsOld(context, BannerContainer);
                        }
                    } else {
                        ++showNativeCounter;

                    }
                } else {
                    if (showNativeCounter != 0 && Integer.parseInt(Comman.mainResModel.getData().getExtraFields().getNativeFrequency()) != 0 && showNativeCounter % Integer.parseInt(Comman.mainResModel.getData().getExtraFields().getNativeFrequency()) == 0) {
                        ++showNativeCounter;
                        if (rectangleAdView != null) {
                            SdkDefaultAdsContainerBinding bindingdefault1 = DataBindingUtil.inflate(inflater, R.layout.sdk_default_ads_container, BannerContainer, false);
                            bindingdefault1.imgContainer.setVisibility(View.GONE);
                            bindingdefault1.rectContainer.setVisibility(View.VISIBLE);
                            bindingdefault1.rectContainer.removeAllViews();
                            bindingdefault1.rectContainer.addView(rectangleAdView);
                            BannerContainer.removeAllViews();
                            BannerContainer.addView(bindingdefault1.getRoot());
                            rectangleAdView = null;
                            RectangleLoad(context);
                        } else {
                            NativeAdsOld(context, BannerContainer);
                        }
                    } else if (Integer.parseInt(Comman.mainResModel.getData().getExtraFields().getNativeFrequency()) == 1) {
                        if (rectangleAdView != null) {
                            LayoutInflater inflater1 = LayoutInflater.from(context);
                            SdkDefaultAdsContainerBinding bindingdefault1 = DataBindingUtil.inflate(inflater1, R.layout.sdk_default_ads_container, BannerContainer, false);
                            bindingdefault1.imgContainer.setVisibility(View.GONE);
                            bindingdefault1.rectContainer.setVisibility(View.VISIBLE);
                            bindingdefault1.rectContainer.removeAllViews();
                            bindingdefault1.rectContainer.addView(rectangleAdView);
                            BannerContainer.removeAllViews();
                            BannerContainer.addView(bindingdefault1.getRoot());
                            rectangleAdView = null;
                            RectangleLoad(context);
                        } else {
                            NativeAdsOld(context, BannerContainer);
                        }
                    } else {
                        ++showNativeCounter;
                    }
                }
            }
        } catch (Exception e) {

        }



    }

    public static void NativeAdsNew(final Activity context, final ViewGroup BannerContainer) {
        try {
            ModelPrefrences modelPrefrences = new ModelPrefrences(context);
            LayoutInflater inflater = LayoutInflater.from(context);
            SdkDefaultAdsContainerBinding bindingdefault = DataBindingUtil.inflate(inflater, R.layout.sdk_default_ads_container, BannerContainer, false);
            BannerContainer.removeAllViews();
            BannerContainer.addView(bindingdefault.getRoot());
            if (!modelPrefrences.getPurchase() &&Comman.mainResModel!=null&& Comman.mainResModel.getData().getExtraFields().getAds().equalsIgnoreCase("on")) {
                if (Comman.mainResModel.getData().getExtraFields().getNativeOnOff().equalsIgnoreCase("on")) {
                    if (showNativeCounter != 0 && Integer.parseInt(Comman.mainResModel.getData().getExtraFields().getNativeFrequency()) != 0 && showNativeCounter % Integer.parseInt(Comman.mainResModel.getData().getExtraFields().getNativeFrequency()) == 0) {

                        ++showNativeCounter;

                        if (unifiedNativeAd != null) {
                            AllDimen allDimen = new AllDimen(context);
                            CustomAd customAd = Comman.mainResModel.getData().getCustomAds().get(0);
                            AdsAmActivityNativeAdsTempNewBinding binding = DataBindingUtil.inflate(inflater, R.layout.ads_am_activity_native_ads_temp_new, BannerContainer, false);

                            binding.cardLargeTemplate.setVisibility(View.VISIBLE);
                            binding.cardSmallTemplate.setVisibility(View.GONE);
                            BannerContainer.removeAllViews();
                            BannerContainer.addView(binding.getRoot());
                            NativeTemplateStyle styles = new NativeTemplateStyle.Builder().build();
                            binding.myTemplateLarge.setCustomeStyle(styles);
                            binding.myTemplateLarge.setNativeAd(unifiedNativeAd);
                        } else if (rectangleAdView != null) {
                            LayoutInflater inflater1 = LayoutInflater.from(context);
                            SdkDefaultAdsContainerBinding bindingdefault1 = DataBindingUtil.inflate(inflater1, R.layout.sdk_default_ads_container, BannerContainer, false);
                            bindingdefault1.imgContainer.setVisibility(View.GONE);
                            bindingdefault1.rectContainer.setVisibility(View.VISIBLE);
                            bindingdefault1.rectContainer.removeAllViews();
                            bindingdefault1.rectContainer.addView(rectangleAdView);
                            BannerContainer.removeAllViews();
                            BannerContainer.addView(bindingdefault1.getRoot());
                            rectangleAdView = null;
                            RectangleLoadNew(context);
                        } else {
                            NativeAdsOldNew(context, BannerContainer);
                        }
                    } else if (Integer.parseInt(Comman.mainResModel.getData().getExtraFields().getNativeFrequency()) != 0 && Integer.parseInt(Comman.mainResModel.getData().getExtraFields().getNativeFrequency()) == 1) {
                        if (unifiedNativeAd != null) {
                            AllDimen allDimen = new AllDimen(context);
                            CustomAd customAd = Comman.mainResModel.getData().getCustomAds().get(0);


                            AdsAmActivityNativeAdsTempNewBinding binding = DataBindingUtil.inflate(inflater, R.layout.ads_am_activity_native_ads_temp_new, BannerContainer, false);
                            binding.cardLargeTemplate.setVisibility(View.VISIBLE);
                            binding.cardSmallTemplate.setVisibility(View.GONE);
                            BannerContainer.removeAllViews();
                            BannerContainer.addView(binding.getRoot());
                            NativeTemplateStyle styles = new NativeTemplateStyle.Builder().build();
                            binding.myTemplateLarge.setCustomeStyle(styles);
                            binding.myTemplateLarge.setNativeAd(unifiedNativeAd);
                        }
                        else if (rectangleAdView != null) {
                            LayoutInflater inflater1 = LayoutInflater.from(context);
                            SdkDefaultAdsContainerBinding bindingdefault1 = DataBindingUtil.inflate(inflater1, R.layout.sdk_default_ads_container, BannerContainer, false);
                            bindingdefault1.imgContainer.setVisibility(View.GONE);
                            bindingdefault1.rectContainer.setVisibility(View.VISIBLE);
                            bindingdefault1.rectContainer.removeAllViews();
                            bindingdefault1.rectContainer.addView(rectangleAdView);
                            BannerContainer.removeAllViews();
                            BannerContainer.addView(bindingdefault1.getRoot());
                            rectangleAdView = null;
                            RectangleLoadNew(context);
                        }
                        else {
                            NativeAdsOldNew(context, BannerContainer);
                        }
                    } else {
                        ++showNativeCounter;

                    }
                } else {
                    if (showNativeCounter != 0 && Integer.parseInt(Comman.mainResModel.getData().getExtraFields().getNativeFrequency()) != 0 && showNativeCounter % Integer.parseInt(Comman.mainResModel.getData().getExtraFields().getNativeFrequency()) == 0) {
                        ++showNativeCounter;
                        if (rectangleAdView != null) {
                            SdkDefaultAdsContainerBinding bindingdefault1 = DataBindingUtil.inflate(inflater, R.layout.sdk_default_ads_container, BannerContainer, false);
                            bindingdefault1.imgContainer.setVisibility(View.GONE);
                            bindingdefault1.rectContainer.setVisibility(View.VISIBLE);
                            bindingdefault1.rectContainer.removeAllViews();
                            bindingdefault1.rectContainer.addView(rectangleAdView);
                            BannerContainer.removeAllViews();
                            BannerContainer.addView(bindingdefault1.getRoot());
                            rectangleAdView = null;
                            RectangleLoadNew(context);
                        } else {
                            NativeAdsOldNew(context, BannerContainer);
                        }
                    } else if (Integer.parseInt(Comman.mainResModel.getData().getExtraFields().getNativeFrequency()) == 1) {
                        if (rectangleAdView != null) {
                            LayoutInflater inflater1 = LayoutInflater.from(context);
                            SdkDefaultAdsContainerBinding bindingdefault1 = DataBindingUtil.inflate(inflater1, R.layout.sdk_default_ads_container, BannerContainer, false);
                            bindingdefault1.imgContainer.setVisibility(View.GONE);
                            bindingdefault1.rectContainer.setVisibility(View.VISIBLE);
                            bindingdefault1.rectContainer.removeAllViews();
                            bindingdefault1.rectContainer.addView(rectangleAdView);
                            BannerContainer.removeAllViews();
                            BannerContainer.addView(bindingdefault1.getRoot());
                            rectangleAdView = null;
                            RectangleLoadNew(context);
                        } else {
                            NativeAdsOldNew(context, BannerContainer);
                        }
                    } else {
                        ++showNativeCounter;
                    }
                }
            }
        } catch (Exception e) {

        }



    }
    public static void NativeBannerNew(final Activity context, final ViewGroup BannerContainer) {
        LayoutInflater inflater = LayoutInflater.from(context);
        SdkDefaultAdsContainer12Binding bindingdefault = DataBindingUtil.inflate(inflater, R.layout.sdk_default_ads_container12, BannerContainer, false);
        BannerContainer.removeAllViews();
        BannerContainer.addView(bindingdefault.getRoot());
        ModelPrefrences modelPrefrences = new ModelPrefrences(context);
        if (!modelPrefrences.getPurchase() &&Comman.mainResModel!=null&& Comman.mainResModel.getData().getExtraFields().getAds().equalsIgnoreCase("on")) {
            if (Comman.mainResModel.getData().getExtraFields().getNativeOnOff().equalsIgnoreCase("on")) {
                if (showNativeBannerCounter != 0 && Integer.parseInt(Comman.mainResModel.getData().getExtraFields().getNativeFrequency()) != 0 && showNativeBannerCounter % Integer.parseInt(Comman.mainResModel.getData().getExtraFields().getNativeFrequency()) == 0) {
                    ++showNativeBannerCounter;

                    if (unifiedNativeBannerAd != null) {
                        AllDimen allDimen = new AllDimen(context);
                        CustomAd customAd = Comman.mainResModel.getData().getCustomAds().get(1);
                        AdsAmActivityNativeAdsTempNewBinding binding = DataBindingUtil.inflate(inflater, R.layout.ads_am_activity_native_ads_temp_new, BannerContainer, false);
                        binding.cardLargeTemplate.setVisibility(View.GONE);
                        binding.cardSmallTemplate.setVisibility(View.VISIBLE);
                        BannerContainer.removeAllViews();
                        BannerContainer.addView(binding.getRoot());
                        NativeTemplateStyle styles = new NativeTemplateStyle.Builder().build();
                        binding.myTemplateSmall.setCustomeStyle(styles);
                        binding.myTemplateSmall.setNativeAd(unifiedNativeBannerAd);
                    }
                    else if (largeBannerAdView != null) {
                        LayoutInflater inflater1 = LayoutInflater.from(context);
                        SdkDefaultAdsContainer12Binding bindingdefault1 = DataBindingUtil.inflate(inflater1, R.layout.sdk_default_ads_container12, BannerContainer, false);
                        bindingdefault1.banner.setVisibility(View.GONE);
                        bindingdefault1.largeContainer.setVisibility(View.VISIBLE);
                        bindingdefault1.largeContainer.removeAllViews();
                        bindingdefault1.largeContainer.addView(largeBannerAdView);
                        BannerContainer.removeAllViews();
                        BannerContainer.addView(bindingdefault1.getRoot());
                        largeBannerAdView = null;
                        LargeBannerAdLoad(context);
                    }
                    else {
                        NativeBannerOldNew(context, BannerContainer);

                    }
                } else if (Comman.mainResModel!=null&&Integer.parseInt(Comman.mainResModel.getData().getExtraFields().getNativeFrequency()) == 1) {
                    if (unifiedNativeBannerAd != null) {
                        AllDimen allDimen = new AllDimen(context);
                        CustomAd customAd = Comman.mainResModel.getData().getCustomAds().get(1);
                        AdsAmActivityNativeAdsTempNewBinding binding = DataBindingUtil.inflate(inflater, R.layout.ads_am_activity_native_ads_temp_new, BannerContainer, false);
                        binding.cardLargeTemplate.setVisibility(View.GONE);
                        binding.cardSmallTemplate.setVisibility(View.VISIBLE);
                        BannerContainer.removeAllViews();
                        BannerContainer.addView(binding.getRoot());
                        NativeTemplateStyle styles = new NativeTemplateStyle.Builder().build();
                        binding.myTemplateSmall.setCustomeStyle(styles);
                        binding.myTemplateSmall.setNativeAd(unifiedNativeBannerAd);
                    }
                    else if (largeBannerAdView != null) {
                        LayoutInflater inflater1 = LayoutInflater.from(context);
                        SdkDefaultAdsContainer12Binding bindingdefault1 = DataBindingUtil.inflate(inflater1, R.layout.sdk_default_ads_container12, BannerContainer, false);
                        bindingdefault1.banner.setVisibility(View.GONE);
                        bindingdefault1.largeContainer.setVisibility(View.VISIBLE);
                        bindingdefault1.largeContainer.removeAllViews();
                        bindingdefault1.largeContainer.addView(largeBannerAdView);
                        BannerContainer.removeAllViews();
                        BannerContainer.addView(bindingdefault1.getRoot());
                        largeBannerAdView = null;
                        LargeBannerAdLoad(context);
                    }
                    else {
                        NativeBannerOldNew(context, BannerContainer);
                    }
                } else {
                    ++showNativeBannerCounter;
                }
            } else {
                if (Comman.mainResModel!=null&&showNativeBannerCounter != 0 && Integer.parseInt(Comman.mainResModel.getData().getExtraFields().getNativeFrequency()) != 0 && showNativeBannerCounter % Integer.parseInt(Comman.mainResModel.getData().getExtraFields().getNativeFrequency()) == 0) {
                    ++showNativeBannerCounter;
                    if (largeBannerAdView != null) {
                        LayoutInflater inflater1 = LayoutInflater.from(context);
                        SdkDefaultAdsContainer12Binding bindingdefault1 = DataBindingUtil.inflate(inflater1, R.layout.sdk_default_ads_container12, BannerContainer, false);
                        bindingdefault1.banner.setVisibility(View.GONE);
                        bindingdefault1.largeContainer.setVisibility(View.VISIBLE);
                        bindingdefault1.largeContainer.removeAllViews();
                        bindingdefault1.largeContainer.addView(largeBannerAdView);
                        BannerContainer.removeAllViews();
                        BannerContainer.addView(bindingdefault1.getRoot());
                        largeBannerAdView = null;
                        LargeBannerAdLoad(context);
                    } else {
                        NativeBannerOldNew(context, BannerContainer);
                    }
                } else if (Comman.mainResModel!=null&&Integer.parseInt(Comman.mainResModel.getData().getExtraFields().getNativeFrequency()) == 1) {
                    if (largeBannerAdView != null) {
                        LayoutInflater inflater1 = LayoutInflater.from(context);
                        SdkDefaultAdsContainer12Binding bindingdefault1 = DataBindingUtil.inflate(inflater1, R.layout.sdk_default_ads_container12, BannerContainer, false);
                        bindingdefault1.banner.setVisibility(View.GONE);
                        bindingdefault1.largeContainer.setVisibility(View.VISIBLE);
                        bindingdefault1.largeContainer.removeAllViews();
                        bindingdefault1.largeContainer.addView(largeBannerAdView);
                        BannerContainer.removeAllViews();
                        BannerContainer.addView(bindingdefault1.getRoot());
                        largeBannerAdView = null;
                        LargeBannerAdLoad(context);
                    } else {
                        NativeBannerOldNew(context, BannerContainer);
                    }
                } else {
                    ++showNativeBannerCounter;
                }
            }
        }
    }
    private static void NativeBannerOldNew(final Context context, final ViewGroup BannerContainer) {
        LayoutInflater inflater = LayoutInflater.from(context);
        SdkDefaultAdsContainer1Binding bindingdefault = DataBindingUtil.inflate(inflater, R.layout.sdk_default_ads_container1, BannerContainer, false);

        BannerContainer.removeAllViews();
        BannerContainer.addView(bindingdefault.getRoot());
        ModelPrefrences modelPrefrences = new ModelPrefrences(context);
        if (!modelPrefrences.getPurchase() &&Comman.mainResModel!=null&& Comman.mainResModel.getData().getExtraFields().getAds().equalsIgnoreCase("on")) {
            if (Comman.mainResModel!=null&&Comman.mainResModel.getData().getExtraFields().getNativeOnOff().equalsIgnoreCase("on")) {
                ArrayList<AdsUnit> adsUnits = (ArrayList<AdsUnit>) Comman.mainResModel.getData().getAdsUnits();

                if (!adsUnits.get(0).getNativeFrequency().equalsIgnoreCase("")) {
                    try {
                        AdLoader.Builder builder = new AdLoader.Builder(context, adsUnits.get(0).getNativeFrequency());

                        builder.forNativeAd(unifiedNativeAd -> {
                            AllDimen allDimen = new AllDimen(context);
                            CustomAd customAd = Comman.mainResModel.getData().getCustomAds().get(1);
                            AdsAmActivityNativeAdsTempNewBinding binding = DataBindingUtil.inflate(inflater, R.layout.ads_am_activity_native_ads_temp_new, BannerContainer, false);
                            binding.cardLargeTemplate.setVisibility(View.GONE);
                            binding.cardSmallTemplate.setVisibility(View.VISIBLE);
                            BannerContainer.removeAllViews();
                            BannerContainer.addView(binding.getRoot());
                            NativeTemplateStyle styles = new NativeTemplateStyle.Builder().build();
                            binding.myTemplateSmall.setCustomeStyle(styles);
                            binding.myTemplateSmall.setNativeAd(unifiedNativeAd);

                        });

                        AdLoader adLoader = builder.withAdListener(new AdListener() {
                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError errorCode) {


                            }
                        }).build();

                        adLoader.loadAd(new AdRequest.Builder().build());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {

                ArrayList<AdsUnit> adsUnits = (ArrayList<AdsUnit>) Comman.mainResModel.getData().getAdsUnits();
                if (!adsUnits.get(0).getBummer().equalsIgnoreCase("")) {
                    AdView adView1001 = new AdView(context);
                    adView1001.setAdUnitId(adsUnits.get(0).getBummer());
                    adView1001.setAdSize(AdSize.LARGE_BANNER);
                    adView1001.loadAd(new AdRequest.Builder().build());
                    adView1001.setAdListener(new AdListener() {
                        @Override
                        public void onAdLoaded() {
                            super.onAdLoaded();
                            BannerContainer.removeAllViews();
                            BannerContainer.addView(adView1001);
                        }

                        @Override
                        public void onAdFailedToLoad(LoadAdError adError) {


                        }

                        @Override
                        public void onAdImpression() {
                            super.onAdImpression();
                        }
                    });
                }

            }
        }

    }

    private static void NativeAdsOld(final Context context, final ViewGroup BannerContainer) {
        try {
            LayoutInflater inflater = LayoutInflater.from(context);
            SdkDefaultAdsContainerBinding bindingdefault = DataBindingUtil.inflate(inflater, R.layout.sdk_default_ads_container, BannerContainer, false);
            BannerContainer.removeAllViews();
            BannerContainer.addView(bindingdefault.getRoot());
            ModelPrefrences modelPrefrences = new ModelPrefrences(context);
            if (!modelPrefrences.getPurchase() &&Comman.mainResModel!=null&& Comman.mainResModel.getData().getExtraFields().getAds().equalsIgnoreCase("on")) {
                if (Comman.mainResModel.getData().getExtraFields().getNativeOnOff().equalsIgnoreCase("on")) {
                    ArrayList<AdsUnit> adsUnits = (ArrayList<AdsUnit>) Comman.mainResModel.getData().getAdsUnits();

                    AdLoader.Builder builder = new AdLoader.Builder(context, adsUnits.get(0).getNativeFrequency());
                    builder.forNativeAd(unifiedNativeAd -> {
                        AllDimen allDimen = new AllDimen(context);
                        CustomAd customAd = Comman.mainResModel.getData().getCustomAds().get(0);

                        AdsAmActivityNativeAdsTempBinding binding = DataBindingUtil.inflate(inflater, R.layout.ads_am_activity_native_ads_temp, BannerContainer, false);
                        binding.cardLargeTemplate.setVisibility(View.VISIBLE);
                        binding.cardSmallTemplate.setVisibility(View.GONE);
                        BannerContainer.removeAllViews();
                        BannerContainer.addView(binding.getRoot());
                        NativeTemplateStyle styles = new NativeTemplateStyle.Builder().build();
                        binding.myTemplateLarge.setCustomeStyle(styles);
                        binding.myTemplateLarge.setNativeAd(unifiedNativeAd);

                    });

                    AdLoader adLoader = builder.withAdListener(new AdListener() {
                        @Override
                        public void onAdFailedToLoad(LoadAdError errorCode) {

                        }
                    }).build();

                    adLoader.loadAd(new AdRequest.Builder().build());
                } else {
                    ArrayList<AdsUnit> adsUnits = (ArrayList<AdsUnit>) Comman.mainResModel.getData().getAdsUnits();
                    if (!adsUnits.get(0).getBummer().equalsIgnoreCase("")) {
                        AdView adView1001 = new AdView(context);
                        adView1001.setAdUnitId(adsUnits.get(0).getBummer());
                        adView1001.setAdSize(AdSize.MEDIUM_RECTANGLE);
                        adView1001.loadAd(new AdRequest.Builder().build());
                        adView1001.setAdListener(new AdListener() {
                            @Override
                            public void onAdLoaded() {
                                super.onAdLoaded();
                                BannerContainer.removeAllViews();
                                BannerContainer.addView(adView1001);
                            }

                            @Override
                            public void onAdFailedToLoad(LoadAdError adError) {


                            }

                            @Override
                            public void onAdImpression() {
                                super.onAdImpression();
                            }
                        });
                    }
                }
            }
        } catch (Exception e) {

        }


    }
    private static void NativeAdsOldNew(final Context context, final ViewGroup BannerContainer) {
        try {
            LayoutInflater inflater = LayoutInflater.from(context);
            SdkDefaultAdsContainerBinding bindingdefault = DataBindingUtil.inflate(inflater, R.layout.sdk_default_ads_container, BannerContainer, false);
            BannerContainer.removeAllViews();
            BannerContainer.addView(bindingdefault.getRoot());
            ModelPrefrences modelPrefrences = new ModelPrefrences(context);
            if (!modelPrefrences.getPurchase() &&Comman.mainResModel!=null&& Comman.mainResModel.getData().getExtraFields().getAds().equalsIgnoreCase("on")) {
                if (Comman.mainResModel.getData().getExtraFields().getNativeOnOff().equalsIgnoreCase("on")) {
                    ArrayList<AdsUnit> adsUnits = (ArrayList<AdsUnit>) Comman.mainResModel.getData().getAdsUnits();

                    AdLoader.Builder builder = new AdLoader.Builder(context, adsUnits.get(0).getNativeFrequency());
                    builder.forNativeAd(unifiedNativeAd -> {
                        AllDimen allDimen = new AllDimen(context);
                        CustomAd customAd = Comman.mainResModel.getData().getCustomAds().get(0);

                        AdsAmActivityNativeAdsTempNewBinding binding = DataBindingUtil.inflate(inflater, R.layout.ads_am_activity_native_ads_temp_new, BannerContainer, false);
                        binding.cardLargeTemplate.setVisibility(View.VISIBLE);
                        binding.cardSmallTemplate.setVisibility(View.GONE);
                        BannerContainer.removeAllViews();
                        BannerContainer.addView(binding.getRoot());
                        NativeTemplateStyle styles = new NativeTemplateStyle.Builder().build();
                        binding.myTemplateLarge.setCustomeStyle(styles);
                        binding.myTemplateLarge.setNativeAd(unifiedNativeAd);

                    });

                    AdLoader adLoader = builder.withAdListener(new AdListener() {
                        @Override
                        public void onAdFailedToLoad(LoadAdError errorCode) {

                        }
                    }).build();

                    adLoader.loadAd(new AdRequest.Builder().build());
                } else {
                    ArrayList<AdsUnit> adsUnits = (ArrayList<AdsUnit>) Comman.mainResModel.getData().getAdsUnits();
                    if (!adsUnits.get(0).getBummer().equalsIgnoreCase("")) {
                        AdView adView1001 = new AdView(context);
                        adView1001.setAdUnitId(adsUnits.get(0).getBummer());
                        adView1001.setAdSize(AdSize.MEDIUM_RECTANGLE);
                        adView1001.loadAd(new AdRequest.Builder().build());
                        adView1001.setAdListener(new AdListener() {
                            @Override
                            public void onAdLoaded() {
                                super.onAdLoaded();
                                BannerContainer.removeAllViews();
                                BannerContainer.addView(adView1001);
                            }

                            @Override
                            public void onAdFailedToLoad(LoadAdError adError) {


                            }

                            @Override
                            public void onAdImpression() {
                                super.onAdImpression();
                            }
                        });
                    }
                }
            }
        } catch (Exception e) {

        }


    }

    public static void clearInstance() {
        unifiedNativeBannerAd = null;
        unifiedNativeAd = null;

    }

}
