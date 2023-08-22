package com.sdk.ads.ResModel;

import androidx.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@Keep
public class GroupApp {

    @SerializedName("Appname")
    @Expose
    private String appname;
    @SerializedName("Packagename")
    @Expose
    private String packagename;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("downloads")
    @Expose
    private Integer downloads;
    @SerializedName("short_description")
    @Expose
    private String shortDescription;

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getPackagename() {
        return packagename;
    }

    public void setPackagename(String packagename) {
        this.packagename = packagename;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

}