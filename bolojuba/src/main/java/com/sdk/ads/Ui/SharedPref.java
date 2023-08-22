package com.sdk.ads.Ui;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SharedPref {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String sound = "SOUND";
    String vibration = "VIBRATION";
    String dflash = "DFLASH";
    String dvibration = "DVIBRATION";
    String audioMusic = "AUDIOMUSIC";
    String audioRing = "AUDIORING";
    String CallerScreen = "CALLERSCREEN";
    String ShotCut = "SHOTCUT";
    String AutoStart = "AUTOSTART";
    String RandomVideo = "RANDOMVIDEO";
    String ThemeImage = "THEMEIMAGE";
    String SPEAK_CALLER_NAME_KEY = "speak_caller_name";
    public SharedPref(Context context) {
        sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public boolean getSPEAK_CALLER_NAME_KEY() {
        return sharedPreferences.getBoolean(SPEAK_CALLER_NAME_KEY, false);
    }

    public void setSPEAK_CALLER_NAME_KEY(boolean speakCallerNameKey) {
        editor.putBoolean(SPEAK_CALLER_NAME_KEY, speakCallerNameKey);
        editor.commit();
    }
    public boolean getSound() {
        return sharedPreferences.getBoolean(sound, false);
    }

    public void setSound(boolean Sound) {
        editor.putBoolean(sound, Sound);
        editor.commit();
    }

    public boolean getVibration() {
        return sharedPreferences.getBoolean(vibration, false);
    }

    public void setVibration(boolean Vibration) {
        editor.putBoolean(vibration, Vibration);
        editor.commit();
    }

    public boolean getDFlash() {
        return sharedPreferences.getBoolean(dflash, false);
    }

    public void setDFlash(boolean Sound) {
        editor.putBoolean(dflash, Sound);
        editor.commit();
    }

    public boolean getDVibration() {
        return sharedPreferences.getBoolean(dvibration, false);
    }

    public void setDVibration(boolean Vibration) {
        editor.putBoolean(dvibration, Vibration);
        editor.commit();
    }

    public int getAudioMusic() {
        return sharedPreferences.getInt(audioMusic, 0);
    }

    public void setAudioMusic(int audiomusic) {
        editor.putInt(audioMusic, audiomusic);
        editor.commit();
    }

    public int getAudioRing() {
        return sharedPreferences.getInt(audioRing, 0);
    }

    public void setAudioRing(int audioring) {
        editor.putInt(audioRing, audioring);
        editor.commit();
    }

    public String getCallerScreen() {
        return sharedPreferences.getString(CallerScreen, "");
    }

    public void setCallerScreen(String callerScreen) {
        editor.putString(CallerScreen, callerScreen);
        editor.commit();
    }

    public boolean getShotCut() {
        return sharedPreferences.getBoolean(ShotCut, false);
    }

    public void setShotCut(boolean shotcut) {
        editor.putBoolean(ShotCut, shotcut);
        editor.commit();
    }

    public boolean getAutoStart() {
        return sharedPreferences.getBoolean(AutoStart, false);
    }

    public void setAutoStart(boolean shotcut) {
        editor.putBoolean(AutoStart, shotcut);
        editor.commit();
    }

    public boolean getRandomVideo() {
        return sharedPreferences.getBoolean(RandomVideo, false);
    }

    public void setRandomVideo(boolean randomvideo) {
        editor.putBoolean(RandomVideo, randomvideo);
        editor.commit();
    }

    public String getThemeImageList() {
        return sharedPreferences.getString(ThemeImage, null);
    }

    public void setThemeImageList(String themeimage) {
        editor.putString(ThemeImage, themeimage);
        editor.commit();
    }


    public ArrayList<String> loadData() {

        Gson gson = new Gson();

        String json = sharedPreferences.getString("courses", null);

        Type type = new TypeToken<ArrayList<String>>() {}.getType();
       ArrayList<String> courseModalArrayList = gson.fromJson(json, type);

        if (courseModalArrayList == null) {
            courseModalArrayList = new ArrayList<>();

        }
        return courseModalArrayList;
    }

    public void saveData(ArrayList<String> courseModalArrayList) {

        Gson gson = new Gson();

        String json = gson.toJson(courseModalArrayList);
        editor.putString("courses", json);
        editor.apply();
    }
}
