package com.sdk.ads.ResModel;

import androidx.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
@Keep
public class Data {

    @SerializedName("Appname")
    @Expose
    private String appname;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("downloads")
    @Expose
    private Integer downloads;
    @SerializedName("extra_fields")
    @Expose
    private ExtraFields extraFields;
    @SerializedName("ads_units")
    @Expose
    private List<AdsUnit> adsUnits = null;
    @SerializedName("group_apps")
    @Expose
    private List<GroupApp> groupApps = null;
    @SerializedName("customAds")
    @Expose
    private List<CustomAd> customAds;

    @SerializedName("inter_ads")
    @Expose
    private List<InterAd> interAds;

    public List<CustomAd> getCustomAds() {
        return customAds;
    }

    public void setCustomAds(List<CustomAd> customAds) {
        this.customAds = customAds;
    }

    public List<InterAd> getInterAds() {
        return interAds;
    }

    public void setInterAds(List<InterAd> interAds) {
        this.interAds = interAds;
    }

    public String getBucketLink() {
        return bucketLink;
    }

    public void setBucketLink(String bucketLink) {
        this.bucketLink = bucketLink;
    }

    @SerializedName("bucket_link")
    @Expose
    private String bucketLink;
    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    public ExtraFields getExtraFields() {
        return extraFields;
    }

    public void setExtraFields(ExtraFields extraFields) {
        this.extraFields = extraFields;
    }

    public List<AdsUnit> getAdsUnits() {
        return adsUnits;
    }

    public void setAdsUnits(List<AdsUnit> adsUnits) {
        this.adsUnits = adsUnits;
    }

    public List<GroupApp> getGroupApps() {
        return groupApps;
    }

    public void setGroupApps(List<GroupApp> groupApps) {
        this.groupApps = groupApps;
    }

}