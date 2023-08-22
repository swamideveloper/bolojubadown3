package com.sdk.ads.Ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.sdk.ads.BuildConfig;
import com.sdk.ads.Comman;
import com.sdk.ads.LocalSave.ModelPrefrences;
import com.sdk.ads.R;
import com.sdk.ads.ResModel.GroupApp;
import com.sdk.ads.adapter.MoreAppAdapter;
import com.sdk.ads.ads.AllNativeAds;
import com.sdk.ads.ads.IntertitialAdsEvent;

import java.util.ArrayList;

public class ThankyouActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ModelPrefrences modelPrefrences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thankyou);
        if (getIntent().getBooleanExtra("show", false))
            IntertitialAdsEvent.ShowInterstitialAdsOnCreate(this);
        modelPrefrences = new ModelPrefrences(this);
        init();
        findViewById(R.id.llYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                System.exit(1);
            }
        });
        findViewById(R.id.llNo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IntertitialAdsEvent.ShowInterstitialAdsOnBack(ThankyouActivity.this);

            }
        });


    }

    @Override
    protected void onResume() {
        IntertitialAdsEvent.CallInterstitial(this);
        super.onResume();
    }

    public void init() {
        recyclerView = findViewById(R.id.recyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 5);
        recyclerView.setLayoutManager(gridLayoutManager);
        ArrayList<GroupApp> groupApps = new ArrayList<>();
        LinearLayout llMore = findViewById(R.id.llMore);
        if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getMoreapps()!=null&& Comman.mainResModel.getData().getExtraFields().getMoreapps().equalsIgnoreCase("on") && Comman.mainResModel.getData().getGroupApps().size() != 0) {

            for (int i = 0; i < Comman.mainResModel.getData().getGroupApps().size(); i++) {
                if (!Comman.mainResModel.getData().getGroupApps().get(i).getPackagename().equalsIgnoreCase(BuildConfig.PARENT_PACKAGE)) {
                    groupApps.add(Comman.mainResModel.getData().getGroupApps().get(i));
                }
            }
            MoreAppAdapter adapter = new MoreAppAdapter(this, groupApps, "exit");
            recyclerView.setAdapter(adapter);
        } else {
            llMore.setVisibility(View.GONE);
            AllNativeAds.NativeAds(this, findViewById(R.id.adsContainer));
        }
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        System.exit(1);
    }
}