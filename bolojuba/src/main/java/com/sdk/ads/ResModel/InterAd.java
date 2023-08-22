package com.sdk.ads.ResModel;

import androidx.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Keep
public class InterAd {

@SerializedName("interstitial_image")
@Expose
private String interstitialImage;
@SerializedName("redirect_url")
@Expose
private String redirectUrl;
    @SerializedName("ads_type")
    @Expose
    private String adstype;

    public String getAdstype() {
        return adstype;
    }

    public void setAdstype(String adstype) {
        this.adstype = adstype;
    }

    public String getVideourl() {
        return videourl;
    }

    public void setVideourl(String videourl) {
        this.videourl = videourl;
    }

    @SerializedName("video_url")
    @Expose
    private String videourl;


public String getInterstitialImage() {
return interstitialImage;
}

public void setInterstitialImage(String interstitialImage) {
this.interstitialImage = interstitialImage;
}

public String getRedirectUrl() {
return redirectUrl;
}

public void setRedirectUrl(String redirectUrl) {
this.redirectUrl = redirectUrl;
}

}