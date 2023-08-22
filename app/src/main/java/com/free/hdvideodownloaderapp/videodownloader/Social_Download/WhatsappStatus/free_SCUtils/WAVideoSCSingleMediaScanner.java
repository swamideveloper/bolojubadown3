package com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_SCUtils;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;

import java.io.File;

public class WAVideoSCSingleMediaScanner implements MediaScannerConnection.MediaScannerConnectionClient {
    private final File a1;
    private final MediaScannerConnection b1;

    public WAVideoSCSingleMediaScanner(Context context, File file) {
        this.a1 = file;
        MediaScannerConnection mediaScannerConnection = new MediaScannerConnection(context, this);
        this.b1 = mediaScannerConnection;
        mediaScannerConnection.connect();
    }

    public void onMediaScannerConnected() {
        this.b1.scanFile(this.a1.getAbsolutePath(), null);
    }

    public void onScanCompleted(String str, Uri uri) {
        this.b1.disconnect();
    }
}
