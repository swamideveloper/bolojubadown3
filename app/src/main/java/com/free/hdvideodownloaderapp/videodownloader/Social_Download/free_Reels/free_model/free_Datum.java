package com.free.hdvideodownloaderapp.videodownloader.Social_Download.free_Reels.free_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class free_Datum {

    @SerializedName("VideoName")
    @Expose
    private String videoName;
    @SerializedName("ThumbName")
    @Expose
    private String thumbName;
    @SerializedName("SmallTumbName")
    @Expose
    private String smallTumbName;
    @SerializedName("UserName")
    @Expose
    private String userName;

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getThumbName() {
        return thumbName;
    }

    public void setThumbName(String thumbName) {
        this.thumbName = thumbName;
    }

    public String getSmallTumbName() {
        return smallTumbName;
    }

    public void setSmallTumbName(String smallTumbName) {
        this.smallTumbName = smallTumbName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}