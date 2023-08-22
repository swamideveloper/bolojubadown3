package com.free.hdvideodownloaderapp.videodownloader.Def;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.annotation.RequiresApi;
import android.widget.TextView;


import java.io.File;

public class HDVideo_bw {

    public static void g0(StringBuilder sb, String str, char c, String str2) {
        sb.append(str);
        sb.append(c);
        sb.append(str2);
    }

    public static StringBuilder L(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(str2);
        return sb;
    }

    public static class c {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public static File a(Context context) {
            return context.getCodeCacheDir();
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public static Drawable b(Context context, int i) {
            return context.getDrawable(i);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public static File c(Context context) {
            return context.getNoBackupFilesDir();
        }
    }

    public static StringBuilder I(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        return sb;
    }

    public static void j0(StringBuilder sb, String str, TextView textView) {
        sb.append(str);
        textView.setText(sb.toString());
    }
}
