package com.free.hdvideodownloaderapp.videodownloader.MusicPlayer.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v4.media.session.MediaControllerCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.free.hdvideodownloaderapp.videodownloader.Fragment.free_QueueFragment;
import com.free.hdvideodownloaderapp.videodownloader.Utils.SingleClickListener;

import com.free.hdvideodownloaderapp.videodownloader.R;
import com.sdk.ads.ads.IntertitialAdsEvent;


public class free_ListActivity extends AppCompatActivity implements free_QueueFragment.MyFragmentCallbackOne {

    FrameLayout frm;
    ImageView ivBack;

    @Override
    public void onBackPressed() {
        IntertitialAdsEvent.ShowInterstitialAdsOnBack(this);
    }
    public  static  int idf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        
        IntertitialAdsEvent.CallInterstitial(this);
IntertitialAdsEvent.ShowInterstitialAdsOnCreate(this);

        idf=R.id.fragment;
        frm=findViewById(R.id.frm);
        ivBack=findViewById(R.id.ivBack);

        ivBack.setOnClickListener(new SingleClickListener() {
            @Override
            public void performClick(View v) {
                onBackPressed();
            }
        });

        free_QueueFragment playerproQueueFragment = (free_QueueFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        if (playerproQueueFragment != null) {
            playerproQueueFragment.notifyFragmentQueueUpdate();
        }
    }

    @Override
    public void viewPagerRefreshOne() {

    }
    public void onItemClick(int mPosition) {
        if (mPosition >= 0) {
            MediaControllerCompat.getMediaController(this).getTransportControls().skipToQueueItem(mPosition);
        }
    }
}