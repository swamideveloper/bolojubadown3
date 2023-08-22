package com.sdk.ads.ResModel;

import androidx.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Keep
public class ExtraFields {


    public String getAdsbuttonColor() {
        return AdsbuttonColor;
    }

    public void setAdsbuttonColor(String adsbuttonColor) {
        AdsbuttonColor = adsbuttonColor;
    }

    @SerializedName("AdsbuttonColor")
    @Expose
    private String AdsbuttonColor;



    public String getExtraNewScreen() {
        return ExtraNewScreen;
    }

    public void setExtraNewScreen(String extraNewScreen) {
        ExtraNewScreen = extraNewScreen;
    }

    @SerializedName("ExtraNewScreen")
    @Expose
    private String ExtraNewScreen;


    public String getVideoPlayerONOFF() {
        return VideoPlayerONOFF;
    }

    public void setVideoPlayerONOFF(String videoPlayerONOFF) {
        VideoPlayerONOFF = videoPlayerONOFF;
    }

    @SerializedName("VideoPlayerONOFF")
    @Expose
    private String VideoPlayerONOFF;

    @SerializedName("ads")
    @Expose
    private String ads;
    @SerializedName("thankyou")
    @Expose
    private String thankyou;
    @SerializedName("moreapps")
    @Expose
    private String moreapps;
    @SerializedName("extra_screen")
    @Expose
    private String extraScreen;
    @SerializedName("exit_dialogue")
    @Expose
    private String exitDialogue;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("privacy_policy")
    @Expose
    private String privacyPolicy;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("permission")
    @Expose
    private String permission;
    @SerializedName("long_app")
    @Expose
    private String longApp;
    @SerializedName("old_long_app")
    @Expose
    private String old_long_app;
    @SerializedName("Swipe_theme_ads_position")
    @Expose
    private String  swipeTheme_ads_position;
    @SerializedName("Mainscreen_nativebanner_ads")
    @Expose
    private String mainScreenNativeBannerAds;
    @SerializedName("native_on_off")
    @Expose
    private String nativeOnOff;
    @SerializedName("start_button")
    @Expose
    private String startButton;
    @SerializedName("IN_APP_ONOFF")
    @Expose
    private String inAppOnoff;
    @SerializedName("groupname")
    @Expose
    private String groupname;
    @SerializedName("group_id")
    @Expose
    private String groupId;
    @SerializedName("inter_frequency")
    @Expose
    private String interFrequency;
    @SerializedName("native_frequency")
    @Expose
    private String nativeFrequency;
    @SerializedName("back_inter_frequency")
    @Expose
    private String backInterFrequency;
    @SerializedName("LoginONOFF")
    @Expose
    private String loginONOFF;

    public String getComboAds() {
        return comboAds;
    }

    public void setComboAds(String comboAds) {
        this.comboAds = comboAds;
    }

    @SerializedName("combo-ads")
    @Expose
    private String comboAds;

    public String getDoPermission() {
        return doPermission;
    }

    public void setDoPermission(String doPermission) {
        this.doPermission = doPermission;
    }

    @SerializedName("do-permission")
    @Expose
    private String doPermission;

    public String getFakeCall() {
        return FakeCall;
    }

    public void setFakeCall(String fakeCall) {
        FakeCall = fakeCall;
    }

    @SerializedName("FakeCall")
    @Expose
    private String FakeCall;


    public String getExtraModule() {
        return ExtraModule;
    }

    public void setExtraModule(String extraModule) {
        ExtraModule = extraModule;
    }

    @SerializedName("ExtraModule")
    @Expose
    private String ExtraModule;

    public String getAlternetAppOpen() {
        return alternetAppOpen;
    }

    public void setAlternetAppOpen(String alternetAppOpen) {
        this.alternetAppOpen = alternetAppOpen;
    }

    @SerializedName("alternet_app_open")
    @Expose
    private String alternetAppOpen;







    @SerializedName("Video_call")
    @Expose
    private String Video_call;




    @SerializedName("IsLongApp")
    @Expose
    private String IsLongApp;

    public String getClickAppOpen() {
        return clickAppOpen;
    }

    public void setClickAppOpen(String clickAppOpen) {
        this.clickAppOpen = clickAppOpen;
    }

    @SerializedName("click_app_open")
    @Expose
    private String clickAppOpen;

    public String getBackAppOpen() {
        return backAppOpen;
    }

    public void setBackAppOpen(String backAppOpen) {
        this.backAppOpen = backAppOpen;
    }

    @SerializedName("back_app_open")
    @Expose
    private String backAppOpen;


    public String getAnytime_ads() {
        return anytime_ads;
    }

    public void setAnytime_ads(String anytime_ads) {
        this.anytime_ads = anytime_ads;
    }

    @SerializedName("anytime_ads")
    @Expose
    private String anytime_ads;

    public String getAds() {
        return ads;
    }

    public void setAds(String ads) {
        this.ads = ads;
    }

    public String getThankyou() {
        return thankyou;
    }

    public void setThankyou(String thankyou) {
        this.thankyou = thankyou;
    }

    public String getMoreapps() {
        return moreapps;
    }

    public void setMoreapps(String moreapps) {
        this.moreapps = moreapps;
    }

    public String getExtraScreen() {
        return extraScreen;
    }

    public void setExtraScreen(String extraScreen) {
        this.extraScreen = extraScreen;
    }

    public String getExitDialogue() {
        return exitDialogue;
    }

    public void setExitDialogue(String exitDialogue) {
        this.exitDialogue = exitDialogue;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPrivacyPolicy() {
        return privacyPolicy;
    }

    public void setPrivacyPolicy(String privacyPolicy) {
        this.privacyPolicy = privacyPolicy;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getLongApp() {
        return longApp;
    }

    public void setLongApp(String longApps) {
        this.longApp = longApps;
    }

    public String getOldLongApp() {
        return old_long_app;
    }

    public void setOldLongApp(String longoldApps) {
        this.old_long_app = longoldApps;
    }

    public String  getSwip_AdsPosition() {
        return swipeTheme_ads_position;
    }

    public void setSwip_AdsPosition(String longApps) {
        this.swipeTheme_ads_position = longApps;
    }

    public String getMainScreenNativeBannerAds() {
        return mainScreenNativeBannerAds;
    }

    public void setMainScreenNativeBannerAds(String longApps) {
        this.mainScreenNativeBannerAds = longApps;
    }

    public String getNativeOnOff() {
        return nativeOnOff;
    }

    public void setNativeOnOff(String nativeOnOff) {
        this.nativeOnOff = nativeOnOff;
    }

    public String getStartButton() {
        return startButton;
    }

    public void setStartButton(String startButton) {
        this.startButton = startButton;
    }

    public String getInAppOnoff() {
        return inAppOnoff;
    }

    public void setInAppOnoff(String inAppOnoff) {
        this.inAppOnoff = inAppOnoff;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

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

    public String getBackInterFrequency() {
        return backInterFrequency;
    }

    public void setBackInterFrequency(String backInterFrequency) {
        this.backInterFrequency = backInterFrequency;
    }

    public String getLoginONOFF() {
        return loginONOFF;
    }

    public void setLoginONOFF(String loginONOFF) {
        this.loginONOFF = loginONOFF;
    }


    public String getIsLongApp() {
        return IsLongApp;
    }

    public void setIsLongApp(String isLongApp) {
        IsLongApp = isLongApp;
    }

    public String getVideo_call() {
        return Video_call;
    }

    public void setVideo_call(String video_call) {
        Video_call = video_call;
    }
}