package com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.activities;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.free.hdvideodownloaderapp.videodownloader.R;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_Fragments.freeSCImageFragment;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_Fragments.freeSCSavedFilesFragment;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_Fragments.freeSCVideoFragment_Video;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_Fragments.freeTrackerStatusWhatsappImageFragment;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_Fragments.free_GA_WhatsappTrackerStatusWhatsappVideoFragment;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_SCUtils.WAV_Utils;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_SCUtils.WAVideo_SingleClickListener;
import com.sdk.ads.ads.AllNativeAds;
import com.sdk.ads.ads.IntertitialAdsEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class free_SC_StatusActivity extends freeSCBaseActivity {
    TextView tab1, tab2, tab3;
    View view1, view2, view3;
    private View view_1, view_2, view_3;

    ImageView back;
    ViewPager viewPager;
    public static File f = new File(Environment.getExternalStorageDirectory() + "/WhatsApp");

    public int setLayout() {
        return R.layout.wass_activity_status;
    }

    @Override
    public void setView() {

        ads();

        view1 = findViewById(R.id.view1);
        view2 = findViewById(R.id.view2);
        tab1 = findViewById(R.id.tab1);
        tab2 = findViewById(R.id.tab2);
        view3 = findViewById(R.id.view3);
        tab3 = findViewById(R.id.tab3);
        viewPager = findViewById(R.id.viewPager);

        TextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(Html.fromHtml(getResources().getString(R.string.whatsap)));
        tab1.setBackground(getDrawable(R.drawable.orange_rects));
        tab2.setBackground(getDrawable(R.drawable.plan_rect));
        tab3.setBackground(getDrawable(R.drawable.plan_rect));
        tab1.setTextColor(getResources().getColor(R.color.white));
        tab2.setTextColor(getResources().getColor(R.color.wass_grey));
        tab3.setTextColor(getResources().getColor(R.color.wass_grey));

//        view1.setVisibility(View.VISIBLE);
//        view2.setVisibility(View.INVISIBLE);
//        view3.setVisibility(View.INVISIBLE);


        boolean installed = isAppInstalled("com.whatsapp");
        if (!installed) {
            findViewById(R.id.iv_no_Found_data).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.iv_no_Found_data).setVisibility(View.GONE);
            viewPager.setCurrentItem(0);

            try {
                setUpViewPager();
                setupViewPager1(viewPager);
            } catch (Exception e){
                Toast.makeText(free_SC_StatusActivity.this, "Your device can't support this service!", Toast.LENGTH_SHORT).show();
            }

        }
        tabClick();
        findViewById(R.id.drawer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        findViewById(R.id.icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WAV_Utils.OpenApp(free_SC_StatusActivity.this, "com.whatsapp");
            }
        });

        back = findViewById(R.id.drawer);
        back.setOnClickListener(new WAVideo_SingleClickListener() {
            @Override
            public void performClick(View v) {
                onBackPressed();
            }
        });
    }

    private void ads() {
        IntertitialAdsEvent.CallInterstitial(this);
        IntertitialAdsEvent.ShowInterstitialAdsOnCreate(this);
        AllNativeAds.NativeBanner(this, findViewById(R.id.adsContainer));

    }

    private boolean isAppInstalled(String packageName) {
        PackageManager pm = getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    private void tabClick() {

        tab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tab1.setBackground(getDrawable(R.drawable.orange_rects));
                tab2.setBackground(getDrawable(R.drawable.plan_rect));
                tab3.setBackground(getDrawable(R.drawable.plan_rect));
                tab1.setTextColor(getResources().getColor(R.color.white));
                tab2.setTextColor(getResources().getColor(R.color.wass_grey));
                tab3.setTextColor(getResources().getColor(R.color.wass_grey));
//                view1.setVisibility(View.VISIBLE);
//                view2.setVisibility(View.INVISIBLE);
//                view3.setVisibility(View.INVISIBLE);

                viewPager.setCurrentItem(0);

            }
        });

        tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tab2.setBackground(getDrawable(R.drawable.orange_rects));
                tab1.setBackground(getDrawable(R.drawable.plan_rect));
                tab3.setBackground(getDrawable(R.drawable.plan_rect));
                tab2.setTextColor(getResources().getColor(R.color.white));
                tab1.setTextColor(getResources().getColor(R.color.wass_grey));
                tab3.setTextColor(getResources().getColor(R.color.wass_grey));
//                view1.setVisibility(View.INVISIBLE);
//                view2.setVisibility(View.VISIBLE);
//                view3.setVisibility(View.INVISIBLE);
                viewPager.setCurrentItem(1);
            }
        });

        tab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tab3.setBackground(getDrawable(R.drawable.orange_rects));
                tab2.setBackground(getDrawable(R.drawable.plan_rect));
                tab1.setBackground(getDrawable(R.drawable.plan_rect));

                tab3.setTextColor(getResources().getColor(R.color.white));
                tab1.setTextColor(getResources().getColor(R.color.wass_grey));
                tab2.setTextColor(getResources().getColor(R.color.wass_grey));

//                view1.setVisibility(View.INVISIBLE);
//                view2.setVisibility(View.INVISIBLE);
//                view3.setVisibility(View.VISIBLE);
//                view3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//                view1.setBackgroundColor(getResources().getColor(R.color.transparent));
//                view2.setBackgroundColor(getResources().getColor(R.color.transparent));
                viewPager.setCurrentItem(2);
            }
        });
    }

    private void setupViewPager1(ViewPager viewPager2) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());


        if (f.isDirectory()) {
            if (Build.VERSION.RELEASE.contains("11")) {
                viewPagerAdapter.addFragment(new freeSCImageFragment());
            } else if (Build.VERSION.RELEASE.contains("12")) {
                viewPagerAdapter.addFragment(new freeSCImageFragment());
            } else {
                viewPagerAdapter.addFragment(new freeTrackerStatusWhatsappImageFragment());
            }
        } else {
            viewPagerAdapter.addFragment(new freeSCImageFragment());
        }

        if (f.isDirectory()) {
            if (Build.VERSION.RELEASE.contains("11")) {
                viewPagerAdapter.addFragment(new freeSCVideoFragment_Video());
            } else if (Build.VERSION.RELEASE.contains("12")) {
                viewPagerAdapter.addFragment(new freeSCVideoFragment_Video());
            } else {
                viewPagerAdapter.addFragment(new free_GA_WhatsappTrackerStatusWhatsappVideoFragment());
            }
        } else {
            viewPagerAdapter.addFragment(new freeSCVideoFragment_Video());
        }
        viewPagerAdapter.addFragment(new freeSCSavedFilesFragment());
        viewPager2.setAdapter(viewPagerAdapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList();
        private final List<String> mFragmentTitleList = new ArrayList();

        public ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public Fragment getItem(int i) {
            return (Fragment) mFragmentList.get(i);
        }

        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment) {
            mFragmentList.add(fragment);
        }

        public CharSequence getPageTitle(int i) {
            return (CharSequence) mFragmentTitleList.get(i);
        }
    }

    private void setUpViewPager() {

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {

//                   view1.setVisibility(View.VISIBLE);
//                   view2.setVisibility(View.INVISIBLE);
//                   view3.setVisibility(View.INVISIBLE);

                    tab1.setBackground(getDrawable(R.drawable.orange_rects));
                    tab2.setBackground(getDrawable(R.drawable.plan_rect));
                    tab3.setBackground(getDrawable(R.drawable.plan_rect));
                    tab1.setTextColor(getResources().getColor(R.color.white));
                    tab2.setTextColor(getResources().getColor(R.color.wass_grey));
                    tab3.setTextColor(getResources().getColor(R.color.wass_grey));

                    viewPager.setCurrentItem(0);
                } else if (position == 1) {

                    tab2.setBackground(getDrawable(R.drawable.orange_rects));
                    tab1.setBackground(getDrawable(R.drawable.plan_rect));
                    tab3.setBackground(getDrawable(R.drawable.plan_rect));
                    tab2.setTextColor(getResources().getColor(R.color.white));
                    tab1.setTextColor(getResources().getColor(R.color.wass_grey));
                    tab3.setTextColor(getResources().getColor(R.color.wass_grey));
//                     view1.setVisibility(View.INVISIBLE);
//                     view2.setVisibility(View.VISIBLE);
//                     view3.setVisibility(View.INVISIBLE);

                    viewPager.setCurrentItem(1);
                } else if (position == 2) {

                    tab3.setBackground(getDrawable(R.drawable.orange_rects));
                    tab2.setBackground(getDrawable(R.drawable.plan_rect));
                    tab1.setBackground(getDrawable(R.drawable.plan_rect));

                    tab3.setTextColor(getResources().getColor(R.color.white));
                    tab1.setTextColor(getResources().getColor(R.color.wass_grey));
                    tab2.setTextColor(getResources().getColor(R.color.wass_grey));

//                    view1.setVisibility(View.INVISIBLE);
//                    view2.setVisibility(View.INVISIBLE);
//                    view3.setVisibility(View.VISIBLE);
                    viewPager.setCurrentItem(2);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public void onResume() {
        super.onResume();

    }

    @Override
    public void onBackPressed() {
        IntertitialAdsEvent.ShowInterstitialAdsOnBack(this);
    }
}
