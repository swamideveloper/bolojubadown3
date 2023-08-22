package com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.activities;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_SCUtils.WAVideo_SingleClickListener;
import com.free.hdvideodownloaderapp.videodownloader.R;
import com.sdk.ads.ads.IntertitialAdsEvent;


public class freeVideoPlayerActivityGAWhatsappTracker extends AppCompatActivity {
    VideoView videoView;
    ImageView back;

    @Override
    public void onBackPressed() {
        IntertitialAdsEvent.ShowInterstitialAdsOnBack(this);
    }
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.wass_activity_video_player);

        TextView tvTitle = findViewById(R.id.title);
        tvTitle.setText(Html.fromHtml(getResources().getString(R.string.title1)));
        videoView = findViewById(R.id.player_view);
        String stringExtra = getIntent().getStringExtra("PathVideo");
        back = findViewById(R.id.back);
        Uri parse = Uri.parse(stringExtra);
        videoView.setVideoURI(parse);
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(final MediaPlayer mp) {
                MediaController mediaController = new MediaController(freeVideoPlayerActivityGAWhatsappTracker.this) {
                    @Override
                    public boolean dispatchKeyEvent(KeyEvent event) {
                        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                            mp.release();
                            super.hide();
                            finish();
                            return true;
                        }
                        return super.dispatchKeyEvent(event);
                    }
                };
                videoView.setMediaController(mediaController);
            }
        });
        back.setOnClickListener(new WAVideo_SingleClickListener() {
            @Override
            public void performClick(View v) {
                onBackPressed();
            }
        });
    }

    private void playvideo() {
        videoView.start();
    }

    public void onResume() {
        super.onResume();
    }
}
