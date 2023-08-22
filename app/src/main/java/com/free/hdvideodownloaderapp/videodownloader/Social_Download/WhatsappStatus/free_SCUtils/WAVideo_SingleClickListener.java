package com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_SCUtils;

import android.os.SystemClock;
import android.view.View;

public abstract class WAVideo_SingleClickListener implements View.OnClickListener {

    protected int defaultInterval;
    private long lastTimeClicked = 0;

    public WAVideo_SingleClickListener() {
        this(1000);
    }

    public WAVideo_SingleClickListener(int minInterval) {
        this.defaultInterval = minInterval;
    }

    @Override
    public void onClick(View v) {
        if (SystemClock.elapsedRealtime() - lastTimeClicked < defaultInterval) {
            return;
        }
        lastTimeClicked = SystemClock.elapsedRealtime();
        performClick(v);
    }
    public abstract void performClick(View v);
}

