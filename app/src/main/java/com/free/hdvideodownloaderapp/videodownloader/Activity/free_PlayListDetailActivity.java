package com.free.hdvideodownloaderapp.videodownloader.Activity;

import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.free.hdvideodownloaderapp.videodownloader.Log;
import com.free.hdvideodownloaderapp.videodownloader.Utils.SingleClickListener;
import com.free.hdvideodownloaderapp.videodownloader.Video.Utils;
import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Adapter.PlayerAdapterVideoAdp;
import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.GetMedia;
import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.PlayerVideoModel;
import com.free.hdvideodownloaderapp.videodownloader.R;
import com.sdk.ads.ads.IntertitialAdsEvent;


import java.util.ArrayList;
import java.util.List;

public class free_PlayListDetailActivity extends AppCompatActivity {

    RecyclerView rvFolderList;
    GetMedia mPlayerPlayerproGetMedia;
    PlayerAdapterVideoAdp adapter;
    List<PlayerVideoModel> playerVideoModelList;
    List<PlayerVideoModel> playerVideoModelList0;
    List<String> list;
    public static int posssss = 0;
    String paly_name;
    public ImageView bg_img;
    RelativeLayout toolbar;
    TextView tvTitle;
    public static LinearLayout txtNoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list_detail);


        
        IntertitialAdsEvent.CallInterstitial(this);
IntertitialAdsEvent.ShowInterstitialAdsOnCreate(this);

        rvFolderList = findViewById(R.id.recyclerView);
        toolbar = findViewById(R.id.toolbar);
        tvTitle = findViewById(R.id.tvTitle);
        txtNoData = findViewById(R.id.txtNoData);
        RecyclerView.LayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(this);
        rvFolderList.setLayoutManager(layoutManager);
        rvFolderList.setHasFixedSize(true);

        posssss = getIntent().getIntExtra("id", 0);
        paly_name = getIntent().getStringExtra("paly_name");
        tvTitle.setText("" + paly_name);
        mPlayerPlayerproGetMedia = new GetMedia(free_PlayListDetailActivity.this);
        list = new ArrayList<>();
        for (int i = 0; i < Utils.my_list.size(); i++) {
            Log.e("sdcbdshkjbc", "==" + Utils.my_list.get(i).getLan1());
            list.add(Utils.my_list.get(i).getLan1());
        }

        new loadVideos().execute();

        findViewById(R.id.back).setOnClickListener(new SingleClickListener() {
            @Override
            public void performClick(View v) {
                onBackPressed();
            }
        });
    }

    class loadVideos extends AsyncTask<String, String, List<PlayerVideoModel>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<PlayerVideoModel> doInBackground(String... strings) {
            playerVideoModelList = new ArrayList<>();
            playerVideoModelList0 = new ArrayList<>();
            playerVideoModelList.clear();
            playerVideoModelList0.clear();
            try {
                playerVideoModelList = mPlayerPlayerproGetMedia.getFVAllVideos(free_PlayListDetailActivity.this, list);
                Log.e("TAF", "===s=" + playerVideoModelList.size());
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("TAF", "===" + e.getMessage());
            }


            int j = 0;
            for (int i = 0; i < playerVideoModelList.size(); i++) {
                if (i == 3) {
                    try {
                        j = i;

                        playerVideoModelList0.add(null);
                        playerVideoModelList0.add(playerVideoModelList.get(i));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (i == (j + 9)) {
                    try {
                        j = i;
                        playerVideoModelList0.add(null);
                        playerVideoModelList0.add(playerVideoModelList.get(i));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    playerVideoModelList0.add(playerVideoModelList.get(i));
                }

            }
            return playerVideoModelList0;
        }

        @Override
        protected void onPostExecute(List<PlayerVideoModel> modelList) {
            super.onPostExecute(modelList);
            if (playerVideoModelList0.size() != 0) {
                rvFolderList.setLayoutManager(new LinearLayoutManager(free_PlayListDetailActivity.this));
                adapter = new PlayerAdapterVideoAdp(free_PlayListDetailActivity.this, playerVideoModelList0, 4, playerVideoModelList);
                rvFolderList.setAdapter(adapter);
            } else {
                txtNoData.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void onBackPressed() {
        IntertitialAdsEvent.ShowInterstitialAdsOnBack(this);
    }
}