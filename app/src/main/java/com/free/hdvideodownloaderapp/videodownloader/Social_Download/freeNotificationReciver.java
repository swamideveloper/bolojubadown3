package com.free.hdvideodownloaderapp.videodownloader.Social_Download;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class freeNotificationReciver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String whichAction = intent.getAction();

        switch (whichAction) {
            case "quit_action": {
                context.stopService(new Intent(context, freeDownloaderService.class));
            }
            return;
        }
    }
}
