package com.sdk.ads.ResModel;

import androidx.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@Keep
public class AdsUnit {

    @SerializedName("Inter_frequency")
    @Expose
    private String interFrequency;
    @SerializedName("Native_frequency")
    @Expose
    private String nativeFrequency;
    @SerializedName("Bummer")
    @Expose
    private String bummer;
    @SerializedName("App_Open")
    @Expose
    private String appOpen;
    @SerializedName("Rewarded_Video")
    @Expose
    private String rewardedVideo;
    @SerializedName("Rewarded_Inter")
    @Expose
    private String rewardedInter;

    public String getSplashInter() {
        return splashInter;
    }

    public void setSplashInter(String splashInter) {
        this.splashInter = splashInter;
    }

    @SerializedName("Splash_inter")
    @Expose
    private String splashInter;

    public String getExitNative() {
        return exitNative;
    }

    public void setExitNative(String exitNative) {
        this.exitNative = exitNative;
    }

    @SerializedName("Exit_native")
    @Expose
    private String exitNative;

    public String getInterFrequency() {
        return interFrequency;
    }

    public void setInterFrequency(String interFrequency) {
        this.interFrequency = interFrequency;
    }

    public String getNativeFrequency() {
        return nativeFrequency;
    }

    public void setNativeFrequency(String nativeFrequency) {
        this.nativeFrequency = nativeFrequency;
    }

    public String getBummer() {
        return bummer;
    }

    public void setBummer(String bummer) {
        this.bummer = bummer;
    }

    public String getAppOpen() {
        return appOpen;
    }

    public void setAppOpen(String appOpen) {
        this.appOpen = appOpen;
    }

    public String getRewardedVideo() {
        return rewardedVideo;
    }

    public void setRewardedVideo(String rewardedVideo) {
        this.rewardedVideo = rewardedVideo;
    }

    public String getRewardedInter() {
        return rewardedInter;
    }

    public void setRewardedInter(String rewardedInter) {
        this.rewardedInter = rewardedInter;
    }

}