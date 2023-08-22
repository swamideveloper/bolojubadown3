package com.free.hdvideodownloaderapp.videodownloader.Social_Download.freeActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.free.hdvideodownloaderapp.videodownloader.Social_Download.Models.ScreenModel;
import com.google.android.material.tabs.TabLayout;
import com.free.hdvideodownloaderapp.videodownloader.R;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.Configs.Adapters.freeIntroViewPagerAdapter;
import com.sdk.ads.ads.IntertitialAdsEvent;


import java.util.ArrayList;
import java.util.List;

public class freeIntroActivity extends AppCompatActivity {

    private ViewPager screenPager_j;
    freeIntroViewPagerAdapter introViewPagerAdapter_j;
    TabLayout tabIndicator_j;
    TextView btnNext_j;
    int position_j = 0;
    TextView btnGetStarted_j;
    TextView tvSkip_j;

    @Override
    public void onBackPressed() {
        IntertitialAdsEvent.ShowInterstitialAdsOnBack(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.how_to_use);

        ads();
        savePrefsData();

        btnNext_j = findViewById(R.id.btn_next);
        btnGetStarted_j = findViewById(R.id.btn_get_started);
        tabIndicator_j = findViewById(R.id.tab_indicator);
        tvSkip_j = findViewById(R.id.tv_skip);

        final List<ScreenModel> mList = new ArrayList<>();
        mList.add(new ScreenModel("Click the download button", getResources().getString(R.string.intro_txt_3), R.drawable.intro1));
        mList.add(new ScreenModel("Choose any file", getResources().getString(R.string.intro_txt_5), R.drawable.intro2));
        mList.add(new ScreenModel("Type your file name", getResources().getString(R.string.intro_txt_6), R.drawable.intro3));
        mList.add(new ScreenModel("Go to download", getResources().getString(R.string.intro_txt_9), R.drawable.intro4));
        mList.add(new ScreenModel("See Downloaded File", getResources().getString(R.string.intro_txt_10), R.drawable.intro5));

        screenPager_j = findViewById(R.id.screen_viewpager);
        introViewPagerAdapter_j = new freeIntroViewPagerAdapter(this, mList);
        screenPager_j.setAdapter(introViewPagerAdapter_j);

        tabIndicator_j.setupWithViewPager(screenPager_j);

        btnGetStarted_j.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                freeMainActivity.flagj = true;
                onBackPressed();
            }
        });

        btnNext_j.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position_j = screenPager_j.getCurrentItem();
                if (position_j < mList.size()) {
                    position_j++;
                    screenPager_j.setCurrentItem(position_j);
                }
                if (position_j == mList.size() - 1) {
                    loaddLastScreen();
                }
            }
        });

        tvSkip_j.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                screenPager_j.setCurrentItem(mList.size());
            }
        });

        tabIndicator_j.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == mList.size() - 1) {
                    loaddLastScreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void ads() {
        
        IntertitialAdsEvent.CallInterstitial(this);
IntertitialAdsEvent.ShowInterstitialAdsOnCreate(this);
    }

    private void savePrefsData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpnend", true);
        editor.commit();
    }

    private boolean restorePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        Boolean isIntroActivityOpnendBefore = pref.getBoolean("isIntroOpnend", false);
        return isIntroActivityOpnendBefore;
    }

    private void loaddLastScreen() {
        btnNext_j.setVisibility(View.INVISIBLE);
        btnGetStarted_j.setVisibility(View.VISIBLE);
        tvSkip_j.setVisibility(View.INVISIBLE);
        tabIndicator_j.setVisibility(View.INVISIBLE);
    }
}