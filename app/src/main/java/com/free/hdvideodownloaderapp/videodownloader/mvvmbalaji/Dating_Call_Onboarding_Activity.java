package com.free.hdvideodownloaderapp.videodownloader.mvvmbalaji;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.ViewPager;

import com.free.hdvideodownloaderapp.videodownloader.Activity.free_Option_Activity;
import com.free.hdvideodownloaderapp.videodownloader.MykeepClass.AppPrefs;
import com.free.hdvideodownloaderapp.videodownloader.R;
import com.free.hdvideodownloaderapp.videodownloader.databinding.ExitDialoggBinding;
import com.sdk.ads.ads.AllNativeAds;
import com.sdk.ads.ads.IntertitialAdsEvent;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;


public class Dating_Call_Onboarding_Activity extends Base_Dating_Call_Activity {

    public static boolean isfirst = true;

    DotsIndicator dot1;


    ImageView imageview;

    View include;
    AppPrefs appPrefs;


    Dating_Call_AllGuide_Adapter viewAdapter;
    ViewPager viewPager;


    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dating_call_onboarding);
        IntertitialAdsEvent.CallInterstitial(this);
        IntertitialAdsEvent.ShowInterstitialAdsOnCreate(this);

        AllNativeAds.NativeAds(this, findViewById(R.id.adsContainer));

        this.viewPager = (ViewPager) findViewById(R.id.view_pager);

        this.dot1 = (DotsIndicator) findViewById(R.id.dot1);
        this.imageview = (ImageView) findViewById(R.id.imageview);

        appPrefs = new AppPrefs(this);


        Dating_Call_AllGuide_Adapter allGuideAdapter = new Dating_Call_AllGuide_Adapter(this);
        this.viewAdapter = allGuideAdapter;
        this.viewPager.setAdapter(allGuideAdapter);
        this.dot1.setViewPager(this.viewPager);
        viewPager.setOffscreenPageLimit(3);
        this.viewPager.setCurrentItem(0);

//        RemoteConstant.nativeOnboarding.observe(this, nativeAd -> {
//
//            if (nativeAd != null) {
//                AdsUtils.Companion.getnativeOnboarding().showAds(this, findViewById(R.id.layoutAdNative), true);
//            }
//        });


        this.imageview.setOnClickListener(view -> onClick_whistle(view));
        this.viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int i) {
            }

            @Override
            public void onPageScrolled(int i, float f, int i2) {
            }

            @Override
            public void onPageSelected(int i) {

//                if (i == 2) {
//                    imageview.setText("" + getString(R.string.start));
//                } else {
//                    imageview.setText("" + getString(R.string.next));
//                }
            }
        });

    }

    public void onClick_whistle(View view) {
        int currentItem = this.viewPager.getCurrentItem();
        if (this.viewPager.getCurrentItem() == 2) {
            appPrefs.setOnboarding(true);

            skip(view);

            return;
        }
        this.viewPager.setCurrentItem(currentItem + 1);
    }

    public void skip(View view) {
        Intent intent = new Intent(Dating_Call_Onboarding_Activity.this, free_Option_Activity.class).putExtra("show", true);
        startActivity(intent);

    }


    @Override
    public void onBackPressed() {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));


        ExitDialoggBinding binding1 = ExitDialoggBinding.inflate(((Activity) this).getLayoutInflater());
        dialog.setContentView(binding1.getRoot());
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        AllNativeAds.NativeAdsNew(this, binding1.adsContainer1);
        binding1.done.setOnClickListener(v -> {

            finishAffinity();

        });

        binding1.cancel.setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }
}