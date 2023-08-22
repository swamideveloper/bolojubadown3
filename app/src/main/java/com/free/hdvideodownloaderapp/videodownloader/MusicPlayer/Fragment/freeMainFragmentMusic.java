package com.free.hdvideodownloaderapp.videodownloader.MusicPlayer.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.free.hdvideodownloaderapp.videodownloader.Activity.free_SearchActivity;
import com.free.hdvideodownloaderapp.videodownloader.Log;
import com.free.hdvideodownloaderapp.videodownloader.MusicPlayer.freeViewPagerAdapter;
import com.free.hdvideodownloaderapp.videodownloader.Utils.SharedPrefsUtils;
import com.free.hdvideodownloaderapp.videodownloader.Utils.SingleClickListener;
import com.free.hdvideodownloaderapp.videodownloader.R;

public class freeMainFragmentMusic extends Fragment {


    ViewPager viewPager;
    TextView txt_Foryou;
    TextView txt_Songs;
    TextView txt_album;
    TextView txt_Artist;
    TextView txt_Playlist;
    TextView txt_folderPath, tvTitle;
    ImageView ivSearchView;
    LinearLayout ll1, ll2, ll3, ll4, ll5, ll6;
    HorizontalScrollView nav_view;
    RelativeLayout toolbar;
    public static View fragmentmain;
    private View view_1, view_2, view_3, view_4, view_5, view_6;

    @Override
    public void onResume() {
        super.onResume();

        if (fragmentmain != null) {
            fragmentmain.setVisibility(View.GONE);
            Log.e("sdkjf", "==" + freeMusicDockFragment.strtital);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (freeMusicDockFragment.strtital == null || freeMusicDockFragment.strtital.equalsIgnoreCase("")) {
                        fragmentmain.setVisibility(View.GONE);
                    } else {
                        fragmentmain.setVisibility(View.VISIBLE);
                    }
                }
            }, 400);
        }

    }

    @SuppressLint("MissingInflatedId")
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.activity_home_music, viewGroup, false);
        (new SharedPrefsUtils(getContext())).writeSharedPrefs("homeActivityUp", true);

        viewPager = inflate.findViewById(R.id.viewPager);

        txt_Foryou = inflate.findViewById(R.id.txt_Foryou);
        txt_Songs = inflate.findViewById(R.id.txt_songs);
        txt_album = inflate.findViewById(R.id.txt_Album);
        txt_Artist = inflate.findViewById(R.id.txt_Artists);
        txt_Playlist = inflate.findViewById(R.id.txt_Playlist);
        txt_folderPath = inflate.findViewById(R.id.txt_folderPath);

        tvTitle = inflate.findViewById(R.id.tvTitle);
        ivSearchView = inflate.findViewById(R.id.ivSearchView);
        fragmentmain = inflate.findViewById(R.id.fragment);
        nav_view = inflate.findViewById(R.id.nav_view);

        ll1 = inflate.findViewById(R.id.ll1);
        ll2 = inflate.findViewById(R.id.ll2);
        ll3 = inflate.findViewById(R.id.ll3);
        ll4 = inflate.findViewById(R.id.ll4);
        ll5 = inflate.findViewById(R.id.ll5);
        ll6 = inflate.findViewById(R.id.ll6);

        view_1 = inflate.findViewById(R.id.view_1);
        view_2 = inflate.findViewById(R.id.view_2);
        view_3 = inflate.findViewById(R.id.view_3);
        view_4 = inflate.findViewById(R.id.view_4);
        view_5 = inflate.findViewById(R.id.view_5);
        view_6 = inflate.findViewById(R.id.view_6);



        setupViewPager(viewPager);
        viewPager.setCurrentItem(1);

//        view_1.setVisibility(View.INVISIBLE);
//        view_2.setVisibility(View.VISIBLE);
//        view_3.setVisibility(View.INVISIBLE);
//        view_4.setVisibility(View.INVISIBLE);
//        view_5.setVisibility(View.INVISIBLE);
//        view_6.setVisibility(View.INVISIBLE);

        txt_Foryou.setBackground(getResources().getDrawable(R.drawable.plan_rect));
        txt_Songs.setBackground(getResources().getDrawable(R.drawable.orange_rects));
        txt_album.setBackground(getResources().getDrawable(R.drawable.plan_rect));
        txt_Artist.setBackground(getResources().getDrawable(R.drawable.plan_rect));
        txt_Playlist.setBackground(getResources().getDrawable(R.drawable.plan_rect));
        txt_folderPath.setBackground(getResources().getDrawable(R.drawable.plan_rect));

        txt_Foryou.setTextColor(getResources().getColor(R.color.gry));
        txt_Songs.setTextColor(getResources().getColor(R.color.white));
        txt_album.setTextColor(getResources().getColor(R.color.gry));
        txt_Artist.setTextColor(getResources().getColor(R.color.gry));
        txt_Playlist.setTextColor(getResources().getColor(R.color.gry));
        txt_folderPath.setTextColor(getResources().getColor(R.color.gry));





        tvTitle.setText("Songs");

        ivSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), free_SearchActivity.class));
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) { }

            @Override
            public void onPageSelected(int i) {

                if (i == 0) {
                    int x, y;
                    x = ll1.getLeft();
                    y = ll1.getTop();
                    nav_view.scrollTo(x, y);

//                    view_1.setVisibility(View.VISIBLE);
//                    view_2.setVisibility(View.INVISIBLE);
//                    view_3.setVisibility(View.INVISIBLE);
//                    view_4.setVisibility(View.INVISIBLE);
//                    view_5.setVisibility(View.INVISIBLE);
//                    view_6.setVisibility(View.INVISIBLE);


                    txt_Foryou.setBackground(getResources().getDrawable(R.drawable.orange_rects));
                    txt_Songs.setBackground(getResources().getDrawable(R.drawable.plan_rect));
                    txt_album.setBackground(getResources().getDrawable(R.drawable.plan_rect));
                    txt_Artist.setBackground(getResources().getDrawable(R.drawable.plan_rect));
                    txt_Playlist.setBackground(getResources().getDrawable(R.drawable.plan_rect));
                    txt_folderPath.setBackground(getResources().getDrawable(R.drawable.plan_rect));

                    txt_Foryou.setTextColor(getResources().getColor(R.color.white));
                    txt_Songs.setTextColor(getResources().getColor(R.color.gry));
                    txt_album.setTextColor(getResources().getColor(R.color.gry));
                    txt_Artist.setTextColor(getResources().getColor(R.color.gry));
                    txt_Playlist.setTextColor(getResources().getColor(R.color.gry));
                    txt_folderPath.setTextColor(getResources().getColor(R.color.gry));


                    tvTitle.setText("Music");
                    freeMainFragmentMusic.this.viewPager.setCurrentItem(0);

                } else if (i == 1) {
                    int x, y;
                    x = ll1.getLeft();
                    y = ll1.getTop();
                    nav_view.scrollTo(x, y);

                   tvTitle.setText("Songs");
                    viewPager.setCurrentItem(1);

//                    view_1.setVisibility(View.INVISIBLE);
//                    view_2.setVisibility(View.VISIBLE);
//                    view_3.setVisibility(View.INVISIBLE);
//                    view_4.setVisibility(View.INVISIBLE);
//                    view_5.setVisibility(View.INVISIBLE);
//                    view_6.setVisibility(View.INVISIBLE);


                    txt_Foryou.setBackground(getResources().getDrawable(R.drawable.plan_rect));
                    txt_Songs.setBackground(getResources().getDrawable(R.drawable.orange_rects));
                    txt_album.setBackground(getResources().getDrawable(R.drawable.plan_rect));
                    txt_Artist.setBackground(getResources().getDrawable(R.drawable.plan_rect));
                    txt_Playlist.setBackground(getResources().getDrawable(R.drawable.plan_rect));
                    txt_folderPath.setBackground(getResources().getDrawable(R.drawable.plan_rect));

                    txt_Foryou.setTextColor(getResources().getColor(R.color.gry));
                    txt_Songs.setTextColor(getResources().getColor(R.color.white));
                    txt_album.setTextColor(getResources().getColor(R.color.gry));
                    txt_Artist.setTextColor(getResources().getColor(R.color.gry));
                    txt_Playlist.setTextColor(getResources().getColor(R.color.gry));
                    txt_folderPath.setTextColor(getResources().getColor(R.color.gry));

                } else if (i == 2) {

                   tvTitle.setText("Album");
                    viewPager.setCurrentItem(2);
//                    view_1.setVisibility(View.INVISIBLE);
//                    view_2.setVisibility(View.INVISIBLE);
//                    view_3.setVisibility(View.VISIBLE);
//                    view_4.setVisibility(View.INVISIBLE);
//                    view_5.setVisibility(View.INVISIBLE);
//                    view_6.setVisibility(View.INVISIBLE);


                    txt_Foryou.setBackground(getResources().getDrawable(R.drawable.plan_rect));
                    txt_Songs.setBackground(getResources().getDrawable(R.drawable.plan_rect));
                    txt_album.setBackground(getResources().getDrawable(R.drawable.orange_rects));
                    txt_Artist.setBackground(getResources().getDrawable(R.drawable.plan_rect));
                    txt_Playlist.setBackground(getResources().getDrawable(R.drawable.plan_rect));
                    txt_folderPath.setBackground(getResources().getDrawable(R.drawable.plan_rect));

                    txt_Foryou.setTextColor(getResources().getColor(R.color.gry));
                    txt_Songs.setTextColor(getResources().getColor(R.color.gry));
                    txt_album.setTextColor(getResources().getColor(R.color.white));
                    txt_Artist.setTextColor(getResources().getColor(R.color.gry));
                    txt_Playlist.setTextColor(getResources().getColor(R.color.gry));
                    txt_folderPath.setTextColor(getResources().getColor(R.color.gry));


                } else if (i == 3) {

                   tvTitle.setText("Artists");
                   viewPager.setCurrentItem(3);

//                    view_1.setVisibility(View.INVISIBLE);
//                    view_2.setVisibility(View.INVISIBLE);
//                    view_3.setVisibility(View.INVISIBLE);
//                    view_4.setVisibility(View.VISIBLE);
//                    view_5.setVisibility(View.INVISIBLE);
//                    view_6.setVisibility(View.INVISIBLE);

                    txt_Foryou.setBackground(getResources().getDrawable(R.drawable.plan_rect));
                    txt_Songs.setBackground(getResources().getDrawable(R.drawable.plan_rect));
                    txt_album.setBackground(getResources().getDrawable(R.drawable.plan_rect));
                    txt_Artist.setBackground(getResources().getDrawable(R.drawable.orange_rects));
                    txt_Playlist.setBackground(getResources().getDrawable(R.drawable.plan_rect));
                    txt_folderPath.setBackground(getResources().getDrawable(R.drawable.plan_rect));

                    txt_Foryou.setTextColor(getResources().getColor(R.color.gry));
                    txt_Songs.setTextColor(getResources().getColor(R.color.gry));
                    txt_album.setTextColor(getResources().getColor(R.color.gry));
                    txt_Artist.setTextColor(getResources().getColor(R.color.white));
                    txt_Playlist.setTextColor(getResources().getColor(R.color.gry));
                    txt_folderPath.setTextColor(getResources().getColor(R.color.gry));


                } else if (i == 4) {
                    int x, y;
                    x = ll5.getLeft();
                    y = ll5.getTop();
                    nav_view.scrollTo(x, y);

                    tvTitle.setText("Playlist");
                    viewPager.setCurrentItem(4);

//                    view_1.setVisibility(View.INVISIBLE);
//                    view_2.setVisibility(View.INVISIBLE);
//                    view_3.setVisibility(View.INVISIBLE);
//                    view_4.setVisibility(View.INVISIBLE);
//                    view_5.setVisibility(View.VISIBLE);
//                    view_6.setVisibility(View.INVISIBLE);

                    txt_Foryou.setBackground(getResources().getDrawable(R.drawable.plan_rect));
                    txt_Songs.setBackground(getResources().getDrawable(R.drawable.plan_rect));
                    txt_album.setBackground(getResources().getDrawable(R.drawable.plan_rect));
                    txt_Artist.setBackground(getResources().getDrawable(R.drawable.plan_rect));
                    txt_Playlist.setBackground(getResources().getDrawable(R.drawable.orange_rects));
                    txt_folderPath.setBackground(getResources().getDrawable(R.drawable.plan_rect));

                    txt_Foryou.setTextColor(getResources().getColor(R.color.gry));
                    txt_Songs.setTextColor(getResources().getColor(R.color.gry));
                    txt_album.setTextColor(getResources().getColor(R.color.gry));
                    txt_Artist.setTextColor(getResources().getColor(R.color.gry));
                    txt_Playlist.setTextColor(getResources().getColor(R.color.white));
                    txt_folderPath.setTextColor(getResources().getColor(R.color.gry));


                } else if (i == 5) {


                   tvTitle.setText("Folder");
                    freeMainFragmentMusic.this.viewPager.setCurrentItem(5);
                    int x, y;
                    x = ll6.getLeft();
                    y = ll6.getTop();
                    nav_view.scrollTo(x, y);

//                   view_1.setVisibility(View.INVISIBLE);
//                    view_2.setVisibility(View.INVISIBLE);
//                    view_3.setVisibility(View.INVISIBLE);
//                    view_4.setVisibility(View.INVISIBLE);
//                    view_5.setVisibility(View.INVISIBLE);
//                    view_6.setVisibility(View.VISIBLE);

                    txt_Foryou.setBackground(getResources().getDrawable(R.drawable.plan_rect));
                    txt_Songs.setBackground(getResources().getDrawable(R.drawable.plan_rect));
                    txt_album.setBackground(getResources().getDrawable(R.drawable.plan_rect));
                    txt_Artist.setBackground(getResources().getDrawable(R.drawable.plan_rect));
                    txt_Playlist.setBackground(getResources().getDrawable(R.drawable.plan_rect));
                    txt_folderPath.setBackground(getResources().getDrawable(R.drawable.orange_rects));

                    txt_Foryou.setTextColor(getResources().getColor(R.color.gry));
                    txt_Songs.setTextColor(getResources().getColor(R.color.gry));
                    txt_album.setTextColor(getResources().getColor(R.color.gry));
                    txt_Artist.setTextColor(getResources().getColor(R.color.gry));
                    txt_Playlist.setTextColor(getResources().getColor(R.color.gry));
                    txt_folderPath.setTextColor(getResources().getColor(R.color.white));


                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        tabClick();

        toolbar = inflate.findViewById(R.id.toolbar);

        if (fragmentmain != null) {
            fragmentmain.setVisibility(View.GONE);
            Log.e("sdkjf", "==" + freeMusicDockFragment.strtital);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (freeMusicDockFragment.strtital == null || freeMusicDockFragment.strtital.equalsIgnoreCase("")) {
                        fragmentmain.setVisibility(View.GONE);
                    } else {
                        fragmentmain.setVisibility(View.VISIBLE);
                    }
                }
            }, 400);
        }
        return inflate;
    }

    @Override
    public void onStop() {
        super.onStop();
        (new SharedPrefsUtils(getContext())).writeSharedPrefs("homeActivityUp", false);
    }

    private void setupViewPager(ViewPager viewPager) {
        freeViewPagerAdapter adapter = new freeViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new freeHomeFragment());
        adapter.addFragment(new freeAllSongsFragment());
        adapter.addFragment(new freeAlbumGridFragment());
        adapter.addFragment(new freeArtistGridFragment());
        adapter.addFragment(new freePlaylistFragment());
        adapter.addFragment(new freeMusicFolderFragment());
        viewPager.setAdapter(adapter);
    }

    private void tabClick() {
        txt_Foryou.setOnClickListener(new SingleClickListener() {
            public void performClick(View view) {
                tvTitle.setText("Music");
                viewPager.setCurrentItem(0);
            }
        });

        txt_Songs.setOnClickListener(new SingleClickListener() {
            public void performClick(View view) {
                tvTitle.setText("Songs");
                viewPager.setCurrentItem(1);
            }
        });

        txt_album.setOnClickListener(new SingleClickListener() {
            public void performClick(View view) {
                tvTitle.setText("Album");
                viewPager.setCurrentItem(2);
            }
        });

        txt_Artist.setOnClickListener(new SingleClickListener() {
            public void performClick(View view) {
                tvTitle.setText("Artist");
                viewPager.setCurrentItem(3);
            }
        });

        txt_Playlist.setOnClickListener(new SingleClickListener() {
            public void performClick(View view) {
                tvTitle.setText("Playlist");
                viewPager.setCurrentItem(4);
            }
        });

        txt_folderPath.setOnClickListener(new SingleClickListener() {
            public void performClick(View view) {
                tvTitle.setText("Folders");
                viewPager.setCurrentItem(5);
            }
        });
    }
}
