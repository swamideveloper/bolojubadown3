package com.free.hdvideodownloaderapp.videodownloader.Def;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaScannerConnection;
import android.net.Uri;


import androidx.core.content.FileProvider;

import com.free.hdvideodownloaderapp.videodownloader.BuildConfig;
import com.free.hdvideodownloaderapp.videodownloader.Log;
import com.free.hdvideodownloaderapp.videodownloader.R;

import java.io.File;

public class Method {

    public Activity activity;
    private SharedPreferences pref;
    public SharedPreferences.Editor editor;
    private final String myPreference = "status";
    public String pref_link = "link";

    public Method(Activity activity) {
        this.activity = activity;
        pref = activity.getSharedPreferences(myPreference, 0);
        editor = pref.edit();
    }

    public boolean isAppWA() {
        String packageName = "com.whatsapp";
        Intent mIntent = activity.getPackageManager().getLaunchIntentForPackage(packageName);
        return mIntent != null;
    }

    public boolean isAppWB() {
        String packageName = "com.whatsapp.w4b";
        Intent mIntent = activity.getPackageManager().getLaunchIntentForPackage(packageName);
        return mIntent != null;
    }

    public String url_type() {
        switch (pref.getString(pref_link, null)) {
            case "w":
                return "w";
            case "wb":
                return "wb";
            case "wball":
                return "wball";
            default:
                return "w";
        }
    }

    public void Image_Share(String link) {
        MediaScannerConnection.scanFile(activity,
                new String[] { link }, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(link));
        activity.startActivity(Intent.createChooser(shareIntent, "Share to"));
    }

    public void Video_Share(String link) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("*/*");
        shareIntent.putExtra(Intent.EXTRA_TEXT, activity.getResources().getString(R.string.app_name));
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(link));
        activity.startActivity(Intent.createChooser(shareIntent, "Share to"));
    }


    public static void shareVideo(Context context, String title, String path) {
        try {
            MediaScannerConnection.scanFile(context, new String[]{path},
                    null, (path1, uri) -> {
                        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                        sharingIntent.setType("video/*");
                        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, title);
                        Uri photoURI = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileprovider", new File(uri.toString()));
                        sharingIntent.putExtra(Intent.EXTRA_STREAM, photoURI);
                        sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        context.startActivity(Intent.createChooser(sharingIntent, "share via:"));
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
