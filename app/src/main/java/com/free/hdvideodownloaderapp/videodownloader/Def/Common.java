package com.free.hdvideodownloaderapp.videodownloader.Def;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;

import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.free.hdvideodownloaderapp.videodownloader.BuildConfig;
import com.free.hdvideodownloaderapp.videodownloader.Log;
import com.free.hdvideodownloaderapp.videodownloader.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {

    public static String str = "";
    public static String stackName = "";
    public static Serializable dataparse;
    public static int commonpos;

    public static boolean copyFileInSavedDir(Context context, String str) {
        String aa = str.substring(str.lastIndexOf(".") + 1);
        Log.e("TAG", "CopyFileInSavedDir: =====" + aa);
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
        file.mkdirs();
        String str11 = "image_all" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + "." + aa;
        File file2 = new File(file, str11);
        file2.renameTo(file2);
        Uri fromFile = Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + str11));
        try {
            InputStream openInputStream = context.getContentResolver().openInputStream(Uri.parse(str));
            OutputStream openOutputStream = context.getContentResolver().openOutputStream(fromFile, "w");
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

    public static void privacypolicy(Context context) {


        if (isNetworkConnected(context)) {
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.privacy_dialog);
            dialog.setCancelable(true);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            WebView webView = dialog.findViewById(R.id.webView1);
            webView.loadUrl("https://pazabiullapolicy.blogspot.com/2023/07/privacy-policy.html");

            webView.setWebViewClient(new WebViewClient() {

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                        }
                    },100);
                }
            });
            (dialog.findViewById(R.id.no)).setOnClickListener(v -> dialog.dismiss());
            dialog.show();
        } else {
            Toast.makeText(context, "Check Your Internet Connection!!", Toast.LENGTH_SHORT).show();
        }
        }



    public static boolean download(Context context, String path) {
        if (copyFileInSavedDir(context, path)) {
            Toast.makeText(context, "Download SuccessFull", Toast.LENGTH_SHORT).show();
            return true;    
        } else {
            Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public static void ShareApp(Context context) {
        try {
            Intent intent = new Intent("android.intent.action.SEND");
            final String appName = context.getString(R.string.app_name);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, appName);
            String sb = "\nLet me recommend you this application\n\n" +
                    "https://play.google.com/store/apps/details?id=" +
                    BuildConfig.APPLICATION_ID +
                    "\n\n";
            intent.putExtra("android.intent.extra.TEXT", sb);
            context.startActivity(Intent.createChooser(intent, "choose six"));
        } catch (Exception e) {
            e.toString();
        }
    }



    @SuppressLint("WrongConstant")
    public static void rateUs(Context context) {
        String str = "android.intent.action.VIEW";
        String sb = "market://details?id=" + context.getPackageName();
        Intent intent = new Intent(str, Uri.parse(sb));
        intent.addFlags(1208483840);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException unused) {
            String sb2 = "http://play.google.com/store/apps/details?id=" + context.getPackageName();
            context.startActivity(new Intent(str, Uri.parse(sb2)));
        }
    }


    public static boolean isNetworkConnected(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}
