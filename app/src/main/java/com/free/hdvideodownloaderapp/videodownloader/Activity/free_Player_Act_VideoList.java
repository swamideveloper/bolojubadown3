package com.free.hdvideodownloaderapp.videodownloader.Activity;

import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.free.hdvideodownloaderapp.videodownloader.Log;
import com.free.hdvideodownloaderapp.videodownloader.Utils.SingleClickListener;
import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Adapter.PlayerAdapterVideoAdp;
import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.GetMedia;
import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.PlayerVideoModel;
import com.free.hdvideodownloaderapp.videodownloader.R;
import com.sdk.ads.ads.IntertitialAdsEvent;


import java.util.ArrayList;
import java.util.List;

public class free_Player_Act_VideoList extends AppCompatActivity {
    RecyclerView rvFolderList;
    GetMedia mPlayerPlayerproGetMedia;
    PlayerAdapterVideoAdp adapter;
    SwipeRefreshLayout refershLayout;
    List<PlayerVideoModel> playerVideoModelList;
    List<PlayerVideoModel> playerVideoModelList0;
    TextView txtNoData;
    String bId;
    RelativeLayout toolbar;
    public ImageView bg_img;

    private void getIds() {
        rvFolderList = findViewById(R.id.rvFolderList);
        refershLayout = findViewById(R.id.refershLayout);
        txtNoData = findViewById(R.id.txtNoData);
        toolbar = findViewById(R.id.toolbar);
        rvFolderList.setLayoutManager(new GridLayoutManager(this, 1));
        mPlayerPlayerproGetMedia = new GetMedia(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_activity_video_list);

        
        IntertitialAdsEvent.CallInterstitial(this);
IntertitialAdsEvent.ShowInterstitialAdsOnCreate(this);

        bId = getIntent().getStringExtra("Bucket");

        findViewById(R.id.back).setOnClickListener(new SingleClickListener() {
            @Override
            public void performClick(View v) {
                onBackPressed();
            }
        });

        getIds();
        new loadVideos().execute();

        refershLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new loadVideos().execute();
            }
        });

    }

    class loadVideos extends AsyncTask<String, String, List<PlayerVideoModel>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            txtNoData.setVisibility(View.GONE);
        }

        @Override
        protected List<PlayerVideoModel> doInBackground(String... strings) {

            playerVideoModelList = new ArrayList<>();
            playerVideoModelList0 = new ArrayList<>();
            playerVideoModelList.clear();
            playerVideoModelList0.clear();

            try {
                playerVideoModelList = mPlayerPlayerproGetMedia.getVideoByFolder(bId);
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
            if (modelList.size() != 0) {
                rvFolderList.setLayoutManager(new LinearLayoutManager(free_Player_Act_VideoList.this));
                adapter = new PlayerAdapterVideoAdp(free_Player_Act_VideoList.this, modelList, 1, playerVideoModelList);
                rvFolderList.setAdapter(adapter);
            } else {
                txtNoData.setVisibility(View.VISIBLE);
            }
            refershLayout.setRefreshing(false);
        }
    }

    @Override
    public void onBackPressed() {
        IntertitialAdsEvent.ShowInterstitialAdsOnBack(this);
    }

    public void onResume() {
        super.onResume();
    }
}