package com.free.hdvideodownloaderapp.videodownloader.Social_Download.Configs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

public class SettingsManager {

    private static final String DEFAULT_PREFERENCES_NAME = "defaultPreferences";
    private static final String PREFERENCE_FIRST_RUN = "isFirstRun";
    public static final String DOWNLOAD_FOLDER_DIR_NAME="DOWNLOAD_AIO_VIDEO";
    public static final String DOWNLOAD_FOLDER_IMAGES_NAME= "images";
    public static final String DOWNLOAD_FOLDER_AUDIO_NAME= "audio";
    public static final String DOWNLOAD_FOLDER_VIDEO_NAME= "video";
    public static final String DOWNLOAD_FOLDER= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + DOWNLOAD_FOLDER_DIR_NAME+  "/";
    public static final String DOWNLOAD_FOLDER_IMAGES=DOWNLOAD_FOLDER + DOWNLOAD_FOLDER_IMAGES_NAME;
    public static final String DOWNLOAD_FOLDER_AUDIO=DOWNLOAD_FOLDER + DOWNLOAD_FOLDER_AUDIO_NAME;
    public static final String DOWNLOAD_FOLDER_VIDEO=DOWNLOAD_FOLDER + DOWNLOAD_FOLDER_VIDEO_NAME;
    public static boolean IsDownloadComplete=false;

    private static SharedPreferences getDefaultPreferences(Context context) {
        return context.getSharedPreferences(DEFAULT_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }


}
