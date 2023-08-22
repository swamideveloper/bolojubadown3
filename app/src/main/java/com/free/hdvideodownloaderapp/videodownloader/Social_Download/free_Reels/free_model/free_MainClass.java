package com.free.hdvideodownloaderapp.videodownloader.Social_Download.free_Reels.free_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class free_MainClass {

    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("video_folder")
    @Expose
    private String videoFolder;
    @SerializedName("thumb_folder")
    @Expose
    private String thumbFolder;
    @SerializedName("short_thumb_folder")
    @Expose
    private String shortThumbFolder;
    @SerializedName("data")
    @Expose
    private List<free_Datum> data = null;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getVideoFolder() {
        return videoFolder;
    }

    public void setVideoFolder(String videoFolder) {
        this.videoFolder = videoFolder;
    }

    public String getThumbFolder() {
        return thumbFolder;
    }

    public void setThumbFolder(String thumbFolder) {
        this.thumbFolder = thumbFolder;
    }

    public String getShortThumbFolder() {
        return shortThumbFolder;
    }

    public void setShortThumbFolder(String shortThumbFolder) {
        this.shortThumbFolder = shortThumbFolder;
    }

    public List<free_Datum> getData() {
        return data;
    }

    public void setData(List<free_Datum> data) {
        this.data = data;
    }

}