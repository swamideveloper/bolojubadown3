package com.sdk.ads.Ui;

import android.app.ActivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sdk.ads.BuildConfig;
import com.sdk.ads.Comman;
import com.sdk.ads.LocalSave.ModelPrefrences;
import com.sdk.ads.R;
import com.sdk.ads.ResModel.GroupApp;
import com.sdk.ads.adapter.MoreAppAdapter;
import com.sdk.ads.ads.AllNativeAds;
import com.sdk.ads.ads.IntertitialAdsEvent;
import com.sdk.ads.ads.MoreAppUtils;

import java.util.ArrayList;
import java.util.List;

public class MoreAppActivity extends AppCompatActivity {

    RecyclerView moreappView;
    ModelPrefrences modelPrefrences;
    ImageView imgBack;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moreapp);
        IntertitialAdsEvent.CallInterstitial(this);
        IntertitialAdsEvent.ShowInterstitialAdsOnCreate(this);
        AllNativeAds.NativeBanner(this, findViewById(R.id.adsContainer));
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoreAppActivity.super.onBackPressed();
            }
        });
        modelPrefrences = new ModelPrefrences(this);
        init();
    }

    public void init() {
        moreappView = findViewById(R.id.moreappView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        moreappView.setLayoutManager(gridLayoutManager);
        ArrayList<GroupApp> groupApps = new ArrayList<>();
        for (int i = 0; i < Comman.mainResModel.getData().getGroupApps().size(); i++) {
            if (!Comman.mainResModel.getData().getGroupApps().get(i).getPackagename().equalsIgnoreCase(BuildConfig.PARENT_PACKAGE)) {
                groupApps.add(Comman.mainResModel.getData().getGroupApps().get(i));
            }
        }
        MoreAppAdapter adapter = new MoreAppAdapter(this, groupApps, "more");
        moreappView.setAdapter(adapter);
    }
    @Override
    protected void onResume() {
        IntertitialAdsEvent.CallInterstitial(this);
        super.onResume();
    }
    @Override
    public void onBackPressed() {
        ActivityManager mngr = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

        List<ActivityManager.RunningTaskInfo> taskList = mngr.getRunningTasks(10);

        if (taskList.get(0).numActivities == 1) {
            MoreAppUtils.exitDialog(this);
        } else {
            IntertitialAdsEvent.ShowInterstitialAdsOnBack(this);
        }

    }
}
