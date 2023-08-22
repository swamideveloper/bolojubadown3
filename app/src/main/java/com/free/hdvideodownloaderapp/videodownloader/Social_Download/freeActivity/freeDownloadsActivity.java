package com.free.hdvideodownloaderapp.videodownloader.Social_Download.freeActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.free.hdvideodownloaderapp.videodownloader.Social_Download.Configs.Adapters.freeTabAdapter;
import com.free.hdvideodownloaderapp.videodownloader.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.sdk.ads.ads.IntertitialAdsEvent;
import com.sdk.ads.ads.AllNativeAds;


public class freeDownloadsActivity extends AppCompatActivity {
    TextView tab1, tab2, tab3;
    TabLayout tabLayout;
    ViewPager2 viewPager;
    private View view1,view2,view3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloads);

        ads();

        tab1 = findViewById(R.id.tab1);
        tab2 = findViewById(R.id.tab2);
        tab3 = findViewById(R.id.tab3);

        view1 = findViewById(R.id.view1);
        view2 = findViewById(R.id.view2);
        view3 = findViewById(R.id.view3);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        findViewById(R.id.drawer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        tab1.setBackground(getDrawable(R.drawable.orange_rects));
        tab2.setBackground(getDrawable(R.drawable.plan_rect));
        tab3.setBackground(getDrawable(R.drawable.plan_rect));


//        view1.setVisibility(View.VISIBLE);
//        view2.setVisibility(View.INVISIBLE);
//        view3.setVisibility(View.INVISIBLE);

        tab1.setTextColor(getResources().getColor(R.color.white));
        tab2.setTextColor(getResources().getColor(R.color.wass_grey));
        tab3.setTextColor(getResources().getColor(R.color.wass_grey));

        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.Videos)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.Audios)));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        freeTabAdapter tabAdapter = new freeTabAdapter(this);
        viewPager.setAdapter(tabAdapter);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    if (position == 0) {
                        tab.setText(getString(R.string.Videos));
                    } else if (position == 1) {
                        tab.setText(getString(R.string.Audios));
                    } else if (position == 2) {
                        tab.setText(getString(R.string.Images));
                    }
                }).attach();


        tab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tab1.setBackground(getDrawable(R.drawable.orange_rects));
                tab2.setBackground(getDrawable(R.drawable.plan_rect));
                tab3.setBackground(getDrawable(R.drawable.plan_rect));

//                view1.setVisibility(View.VISIBLE);
//                view2.setVisibility(View.INVISIBLE);
//                view3.setVisibility(View.INVISIBLE);

                tab1.setTextColor(getResources().getColor(R.color.white));
                tab2.setTextColor(getResources().getColor(R.color.wass_grey));
                tab3.setTextColor(getResources().getColor(R.color.wass_grey));
                viewPager.setCurrentItem(0);
            }
        });

        tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tab2.setBackground(getDrawable(R.drawable.orange_rects));
                tab1.setBackground(getDrawable(R.drawable.plan_rect));
                tab3.setBackground(getDrawable(R.drawable.plan_rect));

//                view1.setVisibility(View.INVISIBLE);
//                view2.setVisibility(View.VISIBLE);
//                view3.setVisibility(View.INVISIBLE);

                tab2.setTextColor(getResources().getColor(R.color.white));
                tab1.setTextColor(getResources().getColor(R.color.wass_grey));
                tab3.setTextColor(getResources().getColor(R.color.wass_grey));
                viewPager.setCurrentItem(1);
            }
        });

        tab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tab3.setBackground(getDrawable(R.drawable.orange_rects));
                tab2.setBackground(getDrawable(R.drawable.plan_rect));
                tab1.setBackground(getDrawable(R.drawable.plan_rect));

//                view1.setVisibility(View.INVISIBLE);
//                view2.setVisibility(View.INVISIBLE);
//                view3.setVisibility(View.VISIBLE);

                tab3.setTextColor(getResources().getColor(R.color.white));
                tab1.setTextColor(getResources().getColor(R.color.wass_grey));
                tab2.setTextColor(getResources().getColor(R.color.wass_grey));
                viewPager.setCurrentItem(2);
            }
        });
        setUpViewPager();
    }

    private void ads() {
        AllNativeAds.NativeBanner(this, findViewById(R.id.nativeContainer));


        IntertitialAdsEvent.CallInterstitial(this);
IntertitialAdsEvent.ShowInterstitialAdsOnCreate(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpViewPager() {
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    tab1.setBackground(getDrawable(R.drawable.orange_rects));
                    tab2.setBackground(getDrawable(R.drawable.plan_rect));
                    tab3.setBackground(getDrawable(R.drawable.plan_rect));

//                    view1.setVisibility(View.VISIBLE);
//                    view2.setVisibility(View.INVISIBLE);
//                    view3.setVisibility(View.INVISIBLE);

                    tab1.setTextColor(getResources().getColor(R.color.white));
                    tab2.setTextColor(getResources().getColor(R.color.wass_grey));
                    tab3.setTextColor(getResources().getColor(R.color.wass_grey));
                    viewPager.setCurrentItem(0);
                } else if (position == 1) {
                    tab2.setBackground(getDrawable(R.drawable.orange_rects));
                    tab1.setBackground(getDrawable(R.drawable.plan_rect));
                    tab3.setBackground(getDrawable(R.drawable.plan_rect));

//                    view1.setVisibility(View.INVISIBLE);
//                    view2.setVisibility(View.VISIBLE);
//                    view3.setVisibility(View.INVISIBLE);

                    tab2.setTextColor(getResources().getColor(R.color.white));
                    tab1.setTextColor(getResources().getColor(R.color.wass_grey));
                    tab3.setTextColor(getResources().getColor(R.color.wass_grey));
                    viewPager.setCurrentItem(1);
                } else if (position == 2) {
                    tab3.setBackground(getDrawable(R.drawable.orange_rects));
                    tab2.setBackground(getDrawable(R.drawable.plan_rect));
                    tab1.setBackground(getDrawable(R.drawable.plan_rect));

//                    view1.setVisibility(View.INVISIBLE);
//                    view2.setVisibility(View.INVISIBLE);
//                    view3.setVisibility(View.VISIBLE);

                    tab3.setTextColor(getResources().getColor(R.color.white));
                    tab1.setTextColor(getResources().getColor(R.color.wass_grey));
                    tab2.setTextColor(getResources().getColor(R.color.wass_grey));
                    viewPager.setCurrentItem(2);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

    }

    @Override
    public void onBackPressed() {
        IntertitialAdsEvent.ShowInterstitialAdsOnBack(this);
    }
}