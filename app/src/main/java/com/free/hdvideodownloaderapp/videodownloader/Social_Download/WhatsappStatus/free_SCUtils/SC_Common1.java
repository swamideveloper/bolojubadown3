package com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_SCUtils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;

import com.google.android.material.snackbar.Snackbar;
import com.free.hdvideodownloaderapp.videodownloader.BuildConfig;
import com.free.hdvideodownloaderapp.videodownloader.R;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_Models.SC_Status;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_Models.StatusModel;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


@SuppressLint("WrongConstant")
public class SC_Common1 {


    public static File RootDirectoryWhatsappShow = new File(Environment.getExternalStorageDirectory() +
            "/Download/Video Downloader/Whatsapp");
    public static String SaveFilePath = (RootDirectoryWhatsappShow + "/");
    public static String APP_DIR = null;
    private static final String CHANNEL_NAME = "GAUTHAM";
    public static final File STATUS_DIRECTORY2 = new File(Environment.getExternalStorageDirectory() + File.separator + "Android/media/com.whatsapp/WhatsApp/Media/.Statuses");

    public static void copyFile2(SC_Status status, Context context, RelativeLayout relativeLayout) {

        String str;
        File file = new File(APP_DIR);
        if (!file.exists() && !file.mkdirs()) {
            Snackbar.make(relativeLayout, "Something went wrong", -1).show();
        }
        String format = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        if (status.isVideo()) {
            str = "VID_" + format + ".mp4";
        } else {
            str = "IMG_" + format + ".jpg";
        }
        File file2 = new File(file + File.separator + str);
        try {
            FileUtils.copyFile(status.getFile(), file2);
            file2.setLastModified(System.currentTimeMillis());
            new WAVideoSCSingleMediaScanner(context, file);

            Snackbar.make(relativeLayout, "Saved to " + APP_DIR, 0).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static boolean download(Context context, String path) {
        if (copyFileInSavedDir(context, path)) {
            Toast.makeText(context, "Successfully Downloaded", Toast.LENGTH_SHORT).show();

            return true;
        } else {
            Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public static boolean copyFileInSavedDir(Context context, String str) {
        String str2;
        str2 = getDirectory("Whatsapp").getAbsolutePath();
        Uri fromFile = Uri.fromFile(new File(str2 + File.separator + new File(str).getName()));
        try {
            InputStream openInputStream = context.getContentResolver().openInputStream(Uri.parse(str));
            OutputStream openOutputStream = context.getContentResolver().openOutputStream(fromFile);
            byte[] bArr = new byte[1024];
            while (true) {
                int read = openInputStream.read(bArr);
                if (read > 0) {
                    openOutputStream.write(bArr, 0, read);
                } else {
                    openInputStream.close();
                    openOutputStream.flush();
                    openOutputStream.close();
                    Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                    intent.setData(fromFile);
                    context.sendBroadcast(intent);
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static File getDirectory(String str) {
        File file = new File(SC_Common1.SaveFilePath);

        file.mkdirs();
        return file;
    }

    private static void showNotification(Context context, RelativeLayout relativeLayout, File file, SC_Status status) {

        if (Build.VERSION.SDK_INT >= 26) {
            makeNotificationChannel(context);
        }
        Uri uriForFile = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileprovider", new File(file.getAbsolutePath()));
        Intent intent = new Intent("android.intent.action.VIEW");
        if (status.isVideo()) {
            intent.setDataAndType(uriForFile, "video/*");
        } else {
            intent.setDataAndType(uriForFile, "image/*");
        }
        intent.addFlags(1);
        PendingIntent activity = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        }
        NotificationCompat.Builder contentTitle = new NotificationCompat.Builder(context, CHANNEL_NAME).setSmallIcon(R.drawable.ic_file_download_black).setContentTitle(file.getName());
        contentTitle.setContentText("File Saved to" + APP_DIR).setAutoCancel(true).setContentIntent(activity);
        Snackbar.make(relativeLayout, "Saved to " + APP_DIR, 0).show();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static void makeNotificationChannel(Context context) {
        new NotificationChannel(CHANNEL_NAME, "Saved", 3).setShowBadge(true);
    }

    public static void videoPlayDialog(Context context, StatusModel status) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View inflate = LayoutInflater.from(context).inflate(R.layout.wass_view_video_full_screen, (ViewGroup) null);
        FrameLayout frameLayout =  inflate.findViewById(R.id.videoViewWrapper);
        if (inflate.getParent() != null) {
            ((ViewGroup) inflate.getParent()).removeView(inflate);
        }
        builder.setView(inflate);

        VideoView videoView =  inflate.findViewById(R.id.video_full);
        MediaController mediaController = new MediaController(context, false);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public final void onPrepared(MediaPlayer mediaPlayer) {
                lambda$null$1(mediaController, mediaPlayer);
            }
        });

        videoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoView);
        mediaController.setAnchorView(videoView);
        videoView.setVideoURI(Uri.parse(status.getFilePath()));
        videoView.requestFocus();
        ((ViewGroup) mediaController.getParent()).removeView(mediaController);

        if (frameLayout.getParent() != null) {
            frameLayout.removeView(mediaController);
        }

        frameLayout.addView(mediaController);
        AlertDialog create = builder.create();
        create.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;
        create.requestWindowFeature(1);
        create.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        create.show();

    }

    public static void lambda$null$1(MediaController mediaController, MediaPlayer mediaPlayer) {
        mediaPlayer.start();
        mediaController.show(0);
        mediaPlayer.setLooping(true);
    }
}
