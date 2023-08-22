//package com.free.hdvideodownloaderapp.videodownloader.Def;
//
//import android.app.Activity;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.os.Build;
//
//import com.facebook.drawee.backends.pipeline.Fresco;
//import com.free.hdvideodownloaderapp.videodownloader.R;
//import com.sdk.ads.SdkAppController;
//
//
//public class AppController extends SdkAppController {
//
//    public static final String channel_1_ID = "channel1";
//    public static final String TAG = AppController.class.getSimpleName();
//    private static final String CHANNEL_ID = "";
//    private static AppController mInstance;
//    public Activity activity;
//    public int width = 720;
//
//    public static synchronized AppController getInstance() {
//        return mInstance;
//    }
//
//    public static int getSelectedColor() {
//        return mInstance.getResources().getColor(R.color.app_color);
//    }
//
//    public static int getUnselectedColor() {
//        return mInstance.getResources().getColor(android.R.color.darker_gray);
//    }
//
//    private void createNofitication() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel ch1 = new NotificationChannel(channel_1_ID, "Song", NotificationManager.IMPORTANCE_LOW);
//            ch1.setDescription("HEY");
//            NotificationManager manager = getSystemService(NotificationManager.class);
//            manager.createNotificationChannel(ch1);
//        }
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        mInstance = this;
//        createNofitication();
//        createNotificationChannel();
//        Fresco.initialize(this);
//    }
//
//
//    private void createNotificationChannel() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = "Sample";
//            String description = "notification";
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
//            channel.setDescription(description);
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
//    }
//}