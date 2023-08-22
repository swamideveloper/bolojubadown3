package com.free.hdvideodownloaderapp.videodownloader.Aico_Socket;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;

import com.free.hdvideodownloaderapp.videodownloader.R;
import com.sdk.ads.Comman;
import com.sdk.ads.LocalSave.ModelPrefrences;
import com.sdk.ads.Ui.StartButtonActivity;
import com.sdk.ads.ads.AllNativeAds;
import com.sdk.ads.ads.IntertitialAdsEvent;

public class Aico_VC_Manager extends AppCompatActivity {

    private TextView note;
    private boolean backer = false;
    private LottieAnimationView lottie;

    ModelPrefrences modelPrefrences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aico_vc_manager);

//        modelPrefrences = new ModelPrefrences(this);

        note = findViewById(R.id.note);
        lottie = findViewById(R.id.lottie);

        IntertitialAdsEvent.CallInterstitial(this);
        IntertitialAdsEvent.ShowInterstitialAdsOnCreate(this);
        AllNativeAds.NativeAds(this, findViewById(R.id.adsContainer));



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                backer = true;
                if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getVideo_call().equalsIgnoreCase("on")) {
                    lottie.setAnimation("suc.json");
                    lottie.playAnimation();
                    lottie.loop(true);
                    note.setTextColor(getResources().getColor(R.color.green));
                    note.setText("Video call connected!");
                    TextView button = findViewById(R.id.my_button);
                    button.setVisibility(View.VISIBLE);
                    button.setText("JOIN");
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (StartButtonActivity.isNetworkConnected(getApplicationContext())) {
                                Show_Dialog();
                            }
                        }
                    });

                } else {
                    lottie.setAnimation("failed.json");
                    lottie.loop(true);
                    lottie.setRepeatMode(LottieDrawable.RESTART);
                    lottie.playAnimation();
                    note.setTextColor(Color.RED);
                    note.setText("People not found!");
                    TextView button = findViewById(R.id.my_button);
                    button.setVisibility(View.VISIBLE);
                    button.setText("TRY AGAIN");
                    button.setBackground(getResources().getDrawable(R.drawable.ad_exit_three));
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (StartButtonActivity.isNetworkConnected(getApplicationContext()))
                                startActivity(new Intent(Aico_VC_Manager.this, Aico_VC_Manager.class));
                            finish();
                        }
                    });
                }
            }
        }, 6000);
    }

    private void Show_Dialog() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(Aico_VC_Manager.this);
            final View view = getLayoutInflater().inflate(R.layout.aico_dlg_sex, null);
            builder.setView(view);
            AlertDialog dialog = builder.create();
            dialog.setCancelable(true);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            TextView txt_con = view.findViewById(R.id.txt_con);
            txt_con.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Aico_VC_Manager.this, Aico_LiveCall.class));
                    finish();
                }
            });
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        if (backer) {
            try {
                IntertitialAdsEvent.ShowInterstitialAdsOnBack(this);
            } catch (Exception e) {
                finish();
                e.printStackTrace();
            }
        }
    }
}