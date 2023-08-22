package com.free.hdvideodownloaderapp.videodownloader.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.free.hdvideodownloaderapp.videodownloader.Utils.MyUtility;
import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Adapter.PlayerAdapterVideoAdp;
import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.GetMedia;
import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.PlayerVideoModel;
import com.free.hdvideodownloaderapp.videodownloader.R;
import com.sdk.ads.ads.IntertitialAdsEvent;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class free_VideoFavouritelist extends AppCompatActivity {

    RecyclerView rvFolderList;
    List<PlayerVideoModel> playerVideoModelList;
    List<PlayerVideoModel> playerVideoModelList0;
    GetMedia mPlayerPlayerproGetMedia;
    PlayerAdapterVideoAdp adapter;
    LinearLayout txtNoData;
    ImageView back;
    Activity activity;
    List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_favouritelist);

        
        IntertitialAdsEvent.CallInterstitial(this);
IntertitialAdsEvent.ShowInterstitialAdsOnCreate(this);

        setContentView(R.layout.activity_video_favouritelist);
        activity = this;

        String[] favorites = MyUtility.getFavoriteList(activity);
        list = new ArrayList<String>(Arrays.asList(favorites));

        getIds();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new loadVideos().execute();
            }
        }, 400);

    }

    private void getIds() {

        rvFolderList = findViewById(R.id.rvFolderList);
        txtNoData = findViewById(R.id.txtNoData);
        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mPlayerPlayerproGetMedia = new GetMedia(free_VideoFavouritelist.this);

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
                playerVideoModelList = mPlayerPlayerproGetMedia.getFVAllVideos(free_VideoFavouritelist.this, list);
            } catch (Exception e) {
                e.printStackTrace();
            }

            int j=0;
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
                rvFolderList.setLayoutManager(new LinearLayoutManager(free_VideoFavouritelist.this));
                adapter = new PlayerAdapterVideoAdp(free_VideoFavouritelist.this, playerVideoModelList0,2,playerVideoModelList);
                rvFolderList.setAdapter(adapter);
            } else {
                txtNoData.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onBackPressed() {
        IntertitialAdsEvent.ShowInterstitialAdsOnBack(this);
    }
}