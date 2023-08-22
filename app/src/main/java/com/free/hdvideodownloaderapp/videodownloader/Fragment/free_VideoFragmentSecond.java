package com.free.hdvideodownloaderapp.videodownloader.Fragment;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.HistoryUtility;
import com.free.hdvideodownloaderapp.videodownloader.Log;
import com.free.hdvideodownloaderapp.videodownloader.Utils.SingleClickListener;
import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Adapter.HistoryAdapter;
import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Fragment.free_VideoPlaylistFragment;
import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.GetMedia;
import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.VideoFolder;
import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.PlayerVideoModel;
import com.free.hdvideodownloaderapp.videodownloader.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class free_VideoFragmentSecond extends Fragment {

    ViewPager viewPager;
    TextView txt_Folder, txt_playlist, txt_Video1;
    public static ImageView gridlist, listlist;
    public static RelativeLayout history;
    RecyclerView historyrecyclerview;
    List<PlayerVideoModel> playerVideoModelList;
    List<PlayerVideoModel> playerVideoModelList_final;
    GetMedia mPlayerPlayerproGetMedia;
    public static HistoryAdapter adapter;
    free_VideoFragmentSecond activity;
    RelativeLayout t1;
    List<String> list;
    public ImageView bg_img;
    ImageView delete;
    private View view_1, view_2, view_3;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.video_player_mainlayout, container, false);

        activity = this;
        txt_Folder = view.findViewById(R.id.txt_Folder);
        txt_Video1 = view.findViewById(R.id.txt_Video1);
        txt_playlist = view.findViewById(R.id.txt_playlist);
        gridlist = view.findViewById(R.id.gridlist);
        listlist = view.findViewById(R.id.listlist);
        history = view.findViewById(R.id.history);
        delete = view.findViewById(R.id.delete);
        t1 = view.findViewById(R.id.t1);
        bg_img = view.findViewById(R.id.bg_img);

        view_1 = view.findViewById(R.id.view_1);
        view_2 = view.findViewById(R.id.view_2);
        view_3 = view.findViewById(R.id.view_3);

        historyrecyclerview = view.findViewById(R.id.historyrecyclerview);
        mPlayerPlayerproGetMedia = new GetMedia(getActivity());

        String[] favorites = HistoryUtility.getHistoryList(getActivity());
        list = new ArrayList<String>(Arrays.asList(favorites));

//        view_1.setVisibility(View.VISIBLE);
//        view_2.setVisibility(View.INVISIBLE);
//        view_3.setVisibility(View.INVISIBLE);

        txt_Video1.setBackground(getResources().getDrawable(R.drawable.b_t_s));
        txt_Folder.setBackground(getResources().getDrawable(R.drawable.b_t_u));
        txt_playlist.setBackground(getResources().getDrawable(R.drawable.b_t_u));

        txt_Video1.setTextColor(getResources().getColor(R.color.white));
        txt_Folder.setTextColor(getResources().getColor(R.color.gray));
        txt_playlist.setTextColor(getResources().getColor(R.color.gray));


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog alertDialog = new Dialog(getActivity(), R.style.MinWidth);
                alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                alertDialog.setContentView(R.layout.dialog_delete);

                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                alertDialog.findViewById(R.id.btnDelete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        t1.setVisibility(View.GONE);
                        HistoryUtility.DeleteAllHistoryList(getActivity());
                        onResume();

                        alertDialog.cancel();
                    }
                });

                alertDialog.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });

                alertDialog.findViewById(R.id.cancle).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });
                alertDialog.show();

            }
        });

        for (int i = 0; i < list.size(); i++) {
            Log.e("sdfdvdvdf", "====" + list.get(i));
        }
        playerVideoModelList = new ArrayList<>();
        playerVideoModelList_final = new ArrayList<>();
        playerVideoModelList.clear();
        playerVideoModelList_final.clear();
        playerVideoModelList = mPlayerPlayerproGetMedia.getFVAllVideos(getActivity(), list);

        for (int j = 0; j < list.size(); j++) {
            for (int i = 0; i < playerVideoModelList.size(); i++) {
                if (list.get(j).equals(playerVideoModelList.get(i).getId())) {
                    playerVideoModelList_final.add(playerVideoModelList.get(i));
                }
            }
        }
        Collections.reverse(playerVideoModelList_final);


        if (playerVideoModelList_final.size() > 0) {
            history.setVisibility(View.VISIBLE);
        } else {
            history.setVisibility(View.GONE);
        }


        historyrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        adapter = new HistoryAdapter(getActivity(), playerVideoModelList_final, 2);
        historyrecyclerview.setAdapter(adapter);
        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setCurrentItem(0);
        gridlist.setVisibility(View.VISIBLE);
        listlist.setVisibility(View.GONE);
        setUpViewPager();
        setupViewPager1(viewPager);
        tabClick();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (free_VideoFragmentSecond.adapter != null) {
            String[] favorites = HistoryUtility.getHistoryList(getActivity());
            list = new ArrayList<String>(Arrays.asList(favorites));

            if (list.size() == 0) {
                history.setVisibility(View.GONE);
                t1.setVisibility(View.GONE);
            } else {
                history.setVisibility(View.VISIBLE);
                t1.setVisibility(View.VISIBLE);
            }

            playerVideoModelList = new ArrayList<>();
            playerVideoModelList_final = new ArrayList<>();
            playerVideoModelList.clear();
            playerVideoModelList_final.clear();
            playerVideoModelList = mPlayerPlayerproGetMedia.getFVAllVideos(getActivity(), list);
            Log.e("TAF", "===s=" + playerVideoModelList.size());

            for (int j = 0; j < list.size(); j++) {
                for (int i = 0; i < playerVideoModelList.size(); i++) {
                    if (list.get(j).equals(playerVideoModelList.get(i).getId())) {
                        playerVideoModelList_final.add(playerVideoModelList.get(i));
                    }
                }
            }

            Collections.reverse(playerVideoModelList_final);

            if (playerVideoModelList_final.size() > 0) {
                history.setVisibility(View.VISIBLE);
                t1.setVisibility(View.VISIBLE);
            } else {
                history.setVisibility(View.GONE);
                t1.setVisibility(View.GONE);
            }

            historyrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
            adapter = new HistoryAdapter(getActivity(), playerVideoModelList_final, 2);
            historyrecyclerview.setAdapter(adapter);
        }
    }

    private void setUpViewPager() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
//                    view_1.setVisibility(View.VISIBLE);
//                    view_2.setVisibility(View.INVISIBLE);
//                    view_3.setVisibility(View.INVISIBLE);

                    gridlist.setVisibility(View.VISIBLE);
                    listlist.setVisibility(View.GONE);

                    txt_Video1.setBackground(getResources().getDrawable(R.drawable.b_t_s));
                    txt_Folder.setBackground(getResources().getDrawable(R.drawable.b_t_u));
                    txt_playlist.setBackground(getResources().getDrawable(R.drawable.b_t_u));

                    txt_Video1.setTextColor(getResources().getColor(R.color.white));
                    txt_Folder.setTextColor(getResources().getColor(R.color.gray));
                    txt_playlist.setTextColor(getResources().getColor(R.color.gray));

                    viewPager.setCurrentItem(0);


                } else if (position == 1) {
//                    view_1.setVisibility(View.INVISIBLE);
//                    view_2.setVisibility(View.VISIBLE);
//                    view_3.setVisibility(View.INVISIBLE);

                    gridlist.setVisibility(View.GONE);
                    listlist.setVisibility(View.GONE);

                    txt_Video1.setBackground(getResources().getDrawable(R.drawable.b_t_u));
                    txt_Folder.setBackground(getResources().getDrawable(R.drawable.b_t_s));
                    txt_playlist.setBackground(getResources().getDrawable(R.drawable.b_t_u));

                    txt_Video1.setTextColor(getResources().getColor(R.color.gray));
                    txt_Folder.setTextColor(getResources().getColor(R.color.white));
                    txt_playlist.setTextColor(getResources().getColor(R.color.gray));

                    gridlist.setVisibility(View.GONE);
                    listlist.setVisibility(View.GONE);

                    viewPager.setCurrentItem(1);

                } else if (position == 2) {
//                    view_1.setVisibility(View.INVISIBLE);
//                    view_2.setVisibility(View.INVISIBLE);
//                    view_3.setVisibility(View.VISIBLE);

                    gridlist.setVisibility(View.GONE);
                    listlist.setVisibility(View.GONE);

                    txt_Video1.setBackground(getResources().getDrawable(R.drawable.b_t_u));
                    txt_Folder.setBackground(getResources().getDrawable(R.drawable.b_t_u));
                    txt_playlist.setBackground(getResources().getDrawable(R.drawable.b_t_s));

                    txt_Video1.setTextColor(getResources().getColor(R.color.gray));
                    txt_Folder.setTextColor(getResources().getColor(R.color.gray));
                    txt_playlist.setTextColor(getResources().getColor(R.color.white));

                    gridlist.setVisibility(View.GONE);
                    listlist.setVisibility(View.GONE);

                    viewPager.setCurrentItem(2);

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setupViewPager1(ViewPager viewPager2) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment(new VideoFolder());
        viewPagerAdapter.addFragment(new free_Folder_Fragment());
        viewPagerAdapter.addFragment(new free_VideoPlaylistFragment());
        viewPager2.setAdapter(viewPagerAdapter);
    }

    private void tabClick() {
        txt_Folder.setOnClickListener(new SingleClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            public void performClick(View v) {
//                view_1.setVisibility(View.INVISIBLE);
//                view_2.setVisibility(View.VISIBLE);
//                view_3.setVisibility(View.INVISIBLE);

                txt_Video1.setBackground(getResources().getDrawable(R.drawable.b_t_u));
                txt_Folder.setBackground(getResources().getDrawable(R.drawable.b_t_s));
                txt_playlist.setBackground(getResources().getDrawable(R.drawable.b_t_u));

                txt_Video1.setTextColor(getResources().getColor(R.color.gray));
                txt_Folder.setTextColor(getResources().getColor(R.color.white));
                txt_playlist.setTextColor(getResources().getColor(R.color.gray));

                gridlist.setVisibility(View.GONE);
                listlist.setVisibility(View.GONE);
                viewPager.setCurrentItem(1);

            }
        });

        txt_Video1.setOnClickListener(new SingleClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void performClick(View v) {
//                view_1.setVisibility(View.VISIBLE);
//                view_2.setVisibility(View.INVISIBLE);
//                view_3.setVisibility(View.INVISIBLE);

                gridlist.setVisibility(View.VISIBLE);
                listlist.setVisibility(View.GONE);

                txt_Video1.setBackground(getResources().getDrawable(R.drawable.b_t_s));
                txt_Folder.setBackground(getResources().getDrawable(R.drawable.b_t_u));
                txt_playlist.setBackground(getResources().getDrawable(R.drawable.b_t_u));

                txt_Video1.setTextColor(getResources().getColor(R.color.white));
                txt_Folder.setTextColor(getResources().getColor(R.color.gray));
                txt_playlist.setTextColor(getResources().getColor(R.color.gray));

                viewPager.setCurrentItem(0);


            }
        });

        txt_playlist.setOnClickListener(new SingleClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void performClick(View v) {
//                view_1.setVisibility(View.INVISIBLE);
//                view_2.setVisibility(View.INVISIBLE);
//                view_3.setVisibility(View.VISIBLE);

                gridlist.setVisibility(View.GONE);
                listlist.setVisibility(View.GONE);

                txt_Video1.setBackground(getResources().getDrawable(R.drawable.b_t_u));
                txt_Folder.setBackground(getResources().getDrawable(R.drawable.b_t_u));
                txt_playlist.setBackground(getResources().getDrawable(R.drawable.b_t_s));

                txt_Video1.setTextColor(getResources().getColor(R.color.gray));
                txt_Folder.setTextColor(getResources().getColor(R.color.gray));
                txt_playlist.setTextColor(getResources().getColor(R.color.white));

                gridlist.setVisibility(View.GONE);
                listlist.setVisibility(View.GONE);

                viewPager.setCurrentItem(2);

            }
        });
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

}