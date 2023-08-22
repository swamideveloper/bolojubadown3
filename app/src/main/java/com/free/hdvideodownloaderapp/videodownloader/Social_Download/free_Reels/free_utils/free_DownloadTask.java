package com.free.hdvideodownloaderapp.videodownloader.Social_Download.free_Reels.free_utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.free.hdvideodownloaderapp.videodownloader.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


@SuppressLint("StaticFieldLeak")
public class free_DownloadTask extends AsyncTask<String, Integer, String> {

    Context context;
    private PowerManager.WakeLock mWakeLock;
    File filename;
    Dialog downloadDialog;
    File path;
    String type;

    public free_DownloadTask(Context context, File path, String types) {
        this.context = context;
        this.path = path;
        this.type = types;
    }

    private ProgressBar bnp;

    @Override
    protected String doInBackground(String... sUrl) {
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(sUrl[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "Server returned HTTP " + connection.getResponseCode()
                        + " " + connection.getResponseMessage();
            }

            int fileLength = connection.getContentLength();

            input = connection.getInputStream();

            filename = new File(path.getAbsolutePath(), sUrl[1]);
            output = new FileOutputStream(filename);

            byte data[] = new byte[4096];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                if (isCancelled()) {
                    input.close();
                    return null;
                }
                total += count;
                if (fileLength > 0)
                    publishProgress((int) (total * 100 / fileLength));
                output.write(data, 0, count);
            }
        } catch (Exception e) {
            return e.toString();
        } finally {
            try {
                if (output != null)
                    output.close();
                if (input != null)
                    input.close();
            } catch (IOException ignored) {
            }

            if (connection != null)
                connection.disconnect();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                getClass().getName());
        mWakeLock.acquire();
        LayoutInflater dialogLayout = LayoutInflater.from(context);
        View DialogView = dialogLayout.inflate(R.layout.progress_dialog, null);
        downloadDialog = new Dialog(context);
        downloadDialog.setContentView(DialogView);
        downloadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        downloadDialog.setCancelable(false);
        downloadDialog.setCanceledOnTouchOutside(false);
        bnp = DialogView.findViewById(R.id.progress_circular_id);
        downloadDialog.show();
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);
    }

    @Override
    protected void onPostExecute(String result) {
        mWakeLock.release();
        downloadDialog.dismiss();
        if (result != null)
            Toast.makeText(context, "Download error: " + result, Toast.LENGTH_LONG).show();
        else
            Toast.makeText(context, "File downloaded", Toast.LENGTH_SHORT).show();
        MediaScannerConnection.scanFile(context,
                new String[]{filename.getAbsolutePath()}, null,
                (newpath, newuri) -> {

                    if (type.equalsIgnoreCase("share")) {
                        Intent shareIntent = new Intent(
                                Intent.ACTION_SEND);
                        shareIntent.setType("video/*");
                        shareIntent.putExtra(
                                Intent.EXTRA_SUBJECT, context.getString(R.string.app_name));
                        shareIntent.putExtra(
                                Intent.EXTRA_TITLE, context.getString(R.string.app_name));
                        shareIntent.putExtra(Intent.EXTRA_STREAM, newuri);
                        String shareMessage = "\nLet me recommend you this application\n\n";
                        shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + context.getPackageName() + "\n\n";
                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                        shareIntent
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                        context.startActivity(Intent.createChooser(shareIntent, "Share My Video"));
                    }
                });
    }
}