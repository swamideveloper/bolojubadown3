package com.free.hdvideodownloaderapp.videodownloader.Social_Download.free_Utils;


import android.content.Context;
import android.content.SharedPreferences;

public class free_UAppPreference {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String FirstTimeswipe = "FirstTimeswipe";
    String swipeeee = "swipeeee";
    String video_url = "video_url";
    String video_folder = "video_folder";
    String video_thumb = "video_thumb";
    String short_video_file = "short_video_file";
    String ADSShowPosition = "ADSShowPosition";

    public free_UAppPreference(Context context) {

        sharedPreferences = context.getSharedPreferences("AdsKeyStore", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public boolean getFirstTimeswipe() {
        return sharedPreferences.getBoolean(FirstTimeswipe, false);
    }

    public void setFirstTimeswipe(boolean firsttime) {
        editor.putBoolean(FirstTimeswipe, firsttime).apply();
    }

    public boolean getswipeeee() {
        return sharedPreferences.getBoolean(swipeeee, false);
    }

    public void setswipeeee(boolean firsttimee) {
        editor.putBoolean(swipeeee, firsttimee).apply();
    }

    public String getvideo_url() {
        return sharedPreferences.getString(video_url, "");
    }

    public void setvideo_url(String video_urlf) {
        editor.putString(video_url, video_urlf).apply();
    }

    public String getVideo_folder() {
        return sharedPreferences.getString(video_folder, "");
    }

    public void setVideo_folder(String video_folderr) {
        editor.putString(video_folder, video_folderr).apply();
    }

    public String getVideo_thumb() {
        return sharedPreferences.getString(video_thumb, "");
    }

    public void setVideo_thumb(String video_thumbb) {
        editor.putString(video_thumb, video_thumbb).apply();
    }

    public String getShort_video_file() {
        return sharedPreferences.getString(short_video_file, "");
    }

    public void setShort_video_file(String short_video_files) {
        editor.putString(short_video_file, short_video_files).apply();
    }

    public int getADSShowPosition() {
        return sharedPreferences.getInt(ADSShowPosition, 0);
    }

    public void setADSShowPosition(int ADSShowPositionn) {
        editor.putInt(ADSShowPosition, ADSShowPositionn).apply();
    }

}
