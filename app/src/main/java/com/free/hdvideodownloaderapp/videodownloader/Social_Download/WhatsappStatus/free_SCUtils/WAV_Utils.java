package com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_SCUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.free.hdvideodownloaderapp.videodownloader.Log;
import com.free.hdvideodownloaderapp.videodownloader.R;

import java.io.File;
import java.io.FileNotFoundException;


public class WAV_Utils {
    public static final File ROOTDIRECTORYCHINGARISHOW = new File(Environment.getExternalStorageDirectory() + "/Download/Video Downloader/Chingari");
    public static final File ROOTDIRECTORYMITRONSHOW = new File(Environment.getExternalStorageDirectory() + "/Download/Video Downloader/Mitron");
    public static final File ROOTDIRECTORYMOJSHOW = new File(Environment.getExternalStorageDirectory() + "/Download/Video Downloader/Moj");
    public static final File ROOTDIRECTORYMXSHOW = new File(Environment.getExternalStorageDirectory() + "/Download/Video Downloader/Mxtakatak");
    public static File RootDirectoryFacebookShow = new File(Environment.getExternalStorageDirectory() + "/Download/Video Downloader/Facebook");
    public static File RootDirectoryInstaShow = new File(Environment.getExternalStorageDirectory() + "/Download/Video Downloader/Insta");
    public static File RootDirectoryRoposoShow = new File(Environment.getExternalStorageDirectory() + "/Download/Video Downloader/Roposo");
    public static File RootDirectoryShareChatShow = new File(Environment.getExternalStorageDirectory() + "/Download/Video Downloader/ShareChat");
    public static File RootDirectorySnackVideoShow = new File(Environment.getExternalStorageDirectory() + "/Download/Video Downloader/SnackVideo");
    public static File RootDirectoryTwitterShow = new File(Environment.getExternalStorageDirectory() + "/Download/Video Downloader/Twitter");
    public static File RootDirectoryPinterestShow = new File(Environment.getExternalStorageDirectory() + "/Download/Video Downloader/Pinterest");
    public static File RootDirectoryWhatsappShow = new File(Environment.getExternalStorageDirectory() + "/Download/Video Downloader/Whatsapp");
    private static Context context;
    public static Dialog customDialog;
    public static String StaticShareDownloadRepost = "";
    public static String FilePath = "";

    public WAV_Utils(Context context2) {
        context = context2;
    }

    public static void setToast(Context context2, String str) {
        @SuppressLint("WrongConstant") Toast makeText = Toast.makeText(context2, str, 0);
        makeText.setGravity(17, 0, 0);
        makeText.show();
    }

    public static void createFileFolder() {

        if (!RootDirectoryWhatsappShow.exists()) {
            RootDirectoryWhatsappShow.mkdirs();
        }
        if (!RootDirectoryFacebookShow.exists()) {
            RootDirectoryFacebookShow.mkdirs();
        }
        if (!RootDirectoryInstaShow.exists()) {
            RootDirectoryInstaShow.mkdirs();
        }

        if (!ROOTDIRECTORYMXSHOW.exists()) {
            ROOTDIRECTORYMXSHOW.mkdirs();
        }

        if (!ROOTDIRECTORYMOJSHOW.exists()) {
            ROOTDIRECTORYMOJSHOW.mkdirs();
        }
        if (!ROOTDIRECTORYCHINGARISHOW.exists()) {
            ROOTDIRECTORYCHINGARISHOW.mkdirs();
        }
        if (!RootDirectoryTwitterShow.exists()) {
            RootDirectoryTwitterShow.mkdirs();
        }
        if (!ROOTDIRECTORYMITRONSHOW.exists()) {
            ROOTDIRECTORYMITRONSHOW.mkdirs();
        }
        if (!RootDirectoryRoposoShow.exists()) {
            RootDirectoryRoposoShow.mkdirs();
        }
        if (!RootDirectorySnackVideoShow.exists()) {
            RootDirectorySnackVideoShow.mkdirs();
        }
        if (!RootDirectoryShareChatShow.exists()) {
            RootDirectoryShareChatShow.mkdirs();
        }
        if (!RootDirectoryPinterestShow.exists()) {
            RootDirectoryPinterestShow.mkdirs();
        } if (!RootDirectoryWhatsappShow.exists()) {
            RootDirectoryWhatsappShow.mkdirs();
        }

        File file1 = RootDirectoryWhatsappShow;
        if (!file1.exists()) {
            file1.mkdirs();
        }
        File file2 = ROOTDIRECTORYCHINGARISHOW;
        if (!file2.exists()) {
            file2.mkdirs();
        }
        File file3 = RootDirectoryInstaShow;
        if (!file3.exists()) {
            file3.mkdirs();
        }

        File file4 = ROOTDIRECTORYMXSHOW;
        if (!file4.exists()) {
            file4.mkdirs();
        }

        File file6 = ROOTDIRECTORYMOJSHOW;
        if (!file6.exists()) {
            file6.mkdirs();
        }

        File file7 = ROOTDIRECTORYCHINGARISHOW;
        if (!file7.exists()) {
            file7.mkdirs();
        }
        File file8 = RootDirectoryTwitterShow;
        if (!file8.exists()) {
            file8.mkdirs();
        }
        File file9 = ROOTDIRECTORYMITRONSHOW;
        if (!file9.exists()) {
            file9.mkdirs();
        }

        File file10 = RootDirectoryRoposoShow;
        if (!file10.exists()) {
            file10.mkdirs();
        }
        File file11 = RootDirectorySnackVideoShow;
        if (!file11.exists()) {
            file11.mkdirs();
        }
        File file12 = RootDirectoryShareChatShow;
        if (!file12.exists()) {
            file12.mkdirs();
        }
        File file13 = RootDirectoryPinterestShow;
        if (!file13.exists()) {
            file13.mkdirs();
        }
        File file14 = RootDirectoryWhatsappShow;
        if (!file14.exists()) {
            file14.mkdirs();
        }
    }

    public static void showProgressDialog(Activity activity) {
        System.out.println("Show");
        Dialog dialog = customDialog;
        if (dialog != null) {
            dialog.dismiss();
            customDialog = null;
        }
        customDialog = new Dialog(activity);
        View inflate = LayoutInflater.from(activity).inflate(R.layout.progress_dialog, (ViewGroup) null);
        customDialog.setCancelable(false);
        customDialog.setContentView(inflate);
        if (!customDialog.isShowing() && !activity.isFinishing()) {
            customDialog.show();
        }
    }

    public static ProgressDialog progress = null;

    public static void mediaScanner(Context context, String str, String str2) {
        try {
            if (Build.VERSION.SDK_INT >= 19) {
                MediaScannerConnection.scanFile(context, new String[]{new File(Environment.DIRECTORY_DOWNLOADS + "/" + str + str2).getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {

                    public void onScanCompleted(String str, Uri uri) {
                    }
                });
                return;
            }
            context.sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.fromFile(new File(Environment.DIRECTORY_DOWNLOADS + "/" + str + str2))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isNetworkAvailable() {
        @SuppressLint("WrongConstant") NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static BroadcastReceiver onComplete = new BroadcastReceiver() {
        /* class com.storysaver.instagramdownloader.util.Utils.AnonymousClass2 */

        public void onReceive(Context context, Intent intent) {

            if (StaticShareDownloadRepost.equals("Share")) {
                if (FilePath.contains(".mp4")) {
                    shareVideo(context, FilePath);
                } else {
                    shareImage(context, FilePath);
                }
            } else if (!StaticShareDownloadRepost.equals("Repost")) {
            } else {
                if (FilePath.contains(".mp4")) {
                    shareImageVideoOnInsta(context, FilePath, true);
                } else {
                    shareImageVideoOnInsta(context, FilePath, false);
                }
            }
        }
    };

    public static void shareImage(Context context2, String str) {
        Uri fromFile = Uri.fromFile(new File(str));
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().build());
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra("android.intent.extra.STREAM", fromFile);
        context2.startActivity(intent);
    }

    public static void shareVideo(Context context2, String str) {
        Uri fromFile = Uri.fromFile(new File(str));
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().build());
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("video/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra("android.intent.extra.STREAM", fromFile);
        context2.startActivity(intent);

    }

    public static void OpenApp(Context context2, String str) {
        Intent launchIntentForPackage = context2.getPackageManager().getLaunchIntentForPackage(str);
        if (launchIntentForPackage != null) {
            context2.startActivity(launchIntentForPackage);
        } else {
            setToast(context2, context2.getResources().getString(R.string.app_not_available));
        }
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.length() == 0 || str.equalsIgnoreCase("null") || str.equalsIgnoreCase("0");
    }

    public static void shareImageVideoOnWhatsapp(Context context2, String str, boolean z) {
        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.SEND");
            intent.setPackage("com.whatsapp");
            if (z) {
                intent.setType("video/*");
                intent.putExtra("android.intent.extra.STREAM", MediaStore.Video.Media.getContentUri(str));
            } else {
                intent.setType("image/*");
                intent.putExtra("android.intent.extra.STREAM", Uri.parse(MediaStore.Images.Media.insertImage(context2.getContentResolver(), str, "", (String) null)));
            }
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                context2.startActivity(intent);
            } catch (Exception unused) {
                Log.e("@@TAG", "shareImageVideoOnFb: " + unused.toString());
                setToast(context2, context2.getResources().getString(R.string.whatsapp_not_installed));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void shareImageVideoOnFb(Context context2, String str, boolean z) {
        Uri parse = Uri.parse(str);
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        intent.setPackage("com.facebook.katana");
        intent.putExtra("android.intent.extra.STREAM", parse);
        if (z) {
            intent.setType("video/*");
        } else {
            intent.setType("image/*");
        }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            context2.startActivity(intent);
        } catch (Exception unused) {
        }
    }

    public static void shareImageVideoOnInsta(Context context2, String str, boolean z) {
        Uri parse = Uri.parse(str);
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        intent.setPackage("com.instagram.android");
        intent.putExtra("android.intent.extra.TEXT", "");
        intent.putExtra("android.intent.extra.STREAM", parse);
        if (z) {
            intent.setType("video/*");
        } else {
            intent.setType("image/*");
        }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            context2.startActivity(intent);
        } catch (Exception unused) {
        }
    }
}
