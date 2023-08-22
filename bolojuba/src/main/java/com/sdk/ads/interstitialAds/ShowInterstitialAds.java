package com.sdk.ads.interstitialAds;

import android.app.Activity;
import android.view.KeyEvent;

import com.sdk.ads.R;
import com.sdk.ads.interstitialAds.callback.setOnBackPressListener;


public class ShowInterstitialAds {

    Activity activity;
    String DesignNo = "0";

    setOnBackPressListener.OnNoRecordFoundListener adsListener1;
    int Theme = R.style.interstitial_full_screen_theme;

    public ShowInterstitialAds(Activity activity, setOnBackPressListener.OnNoRecordFoundListener interstitialAdsListener1, String DesignNo, int Theme) {
        this.activity = activity;
        adsListener1 = interstitialAdsListener1;
        this.DesignNo = DesignNo;
        this.Theme = Theme;
    }

    public ShowInterstitialAds(Activity activity, setOnBackPressListener.OnNoRecordFoundListener interstitialAdsListener1, String DesignNo) {
        this.activity = activity;
        adsListener1 = interstitialAdsListener1;
        this.DesignNo = DesignNo;
    }

    public void onBackPressed() {
        if (DesignNo.equals("01") || DesignNo.equals("1")) {
            final D01InterstitialAds interstitialAds = new D01InterstitialAds(activity, Theme);
            interstitialAds.setCanceledOnTouchOutside(false);
            interstitialAds.setAnimationEnable(true);
            interstitialAds.setOnCloseListener(() -> {
                interstitialAds.dismiss();
                adsListener1.onAdsClose();

            });

            interstitialAds.setOnKeyListener((dialog, keyCode, event) -> {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    interstitialAds.dismiss();
                    adsListener1.onAdsClose();
                }
                return false;
            });

            interstitialAds.show();
        } else {
            adsListener1.onNoRecordFound();
        }
    }

    public void InterstitialAds() {
        if (DesignNo.equals("01") || DesignNo.equals("1")) {
            final D01InterstitialAds interstitialAds = new D01InterstitialAds(activity, Theme);
            interstitialAds.setCanceledOnTouchOutside(false);
            interstitialAds.setAnimationEnable(true);
            interstitialAds.setOnCloseListener(() -> {
                interstitialAds.dismiss();
                adsListener1.onAdsClose();

            });

            interstitialAds.setOnKeyListener((dialog, keyCode, event) -> {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    interstitialAds.dismiss();
                    adsListener1.onAdsClose();
                }
                return false;
            });

            interstitialAds.show();
        } else {
            adsListener1.onNoRecordFound();
        }
    }

}