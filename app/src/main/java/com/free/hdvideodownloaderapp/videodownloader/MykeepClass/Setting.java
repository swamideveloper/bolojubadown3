package com.free.hdvideodownloaderapp.videodownloader.MykeepClass;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Setting {

    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("settings")
    @Expose
    private Settings settings;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

}
