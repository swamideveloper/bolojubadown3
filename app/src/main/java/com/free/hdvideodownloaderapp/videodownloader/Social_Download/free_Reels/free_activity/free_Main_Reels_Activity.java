package com.free.hdvideodownloaderapp.videodownloader.Social_Download.free_Reels.free_activity;


import android.os.Bundle;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.free.hdvideodownloaderapp.videodownloader.Social_Download.free_Reels.free_Fragment.free_FragmentReels;
import com.free.hdvideodownloaderapp.videodownloader.R;

import com.sdk.ads.ads.IntertitialAdsEvent;


import java.util.ArrayList;
import java.util.List;

public class free_Main_Reels_Activity extends AppCompatActivity {

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.reels_activity_main);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(4);

        setUpViewPager();
        setupViewPager1(viewPager);
        viewPager.setCurrentItem(0);



    }

    private void setUpViewPager() {

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {

                if (position == 0) {
                    if (free_FragmentReels.mRecyclerView != null)
                        free_FragmentReels.mRecyclerView.realseVideoplay();
                }
                if (position == 1) {
                    if (free_FragmentReels.mRecyclerView != null)
                        free_FragmentReels.mRecyclerView.realseVideostop();
                } else if (position == 2) {
                    //images
                    if (free_FragmentReels.mRecyclerView != null)
                        free_FragmentReels.mRecyclerView.realseVideostop();
                } else if (position == 3) {
                    //images
                    if (free_FragmentReels.mRecyclerView != null)
                        free_FragmentReels.mRecyclerView.realseVideostop();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


    }

    private void setupViewPager1(ViewPager viewPager2) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFragment(new free_FragmentReels(viewPager));

        viewPager2.setAdapter(viewPagerAdapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList();
        private final List<String> mFragmentTitleList = new ArrayList();

        public ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public Fragment getItem(int i) {
            return mFragmentList.get(i);
        }

        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment) {
            mFragmentList.add(fragment);
        }

        public CharSequence getPageTitle(int i) {
            return mFragmentTitleList.get(i);
        }

    }

    public void onResume() {
        super.onResume();
        if (free_FragmentReels.VideolistAdapter_Portrait.exoPlayer != null) {
            free_FragmentReels.VideolistAdapter_Portrait.exoPlayer.setPlayWhenReady(true);
        }
        if (viewPager.getCurrentItem() == 0) {
            if (free_FragmentReels.mRecyclerView != null)
                free_FragmentReels.mRecyclerView.realseVideoplay();
        }
    }

    @Override
    public void onBackPressed() {
        IntertitialAdsEvent.ShowInterstitialAdsOnBack(this);
    }

}