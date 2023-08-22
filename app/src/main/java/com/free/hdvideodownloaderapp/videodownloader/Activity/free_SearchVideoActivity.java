package com.free.hdvideodownloaderapp.videodownloader.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.free.hdvideodownloaderapp.videodownloader.Def.EmptyRecyclerView;
import com.free.hdvideodownloaderapp.videodownloader.Adapter.free_Search_VideoAdp;
import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.GetMedia;
import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.PlayerVideoModel;
import com.free.hdvideodownloaderapp.videodownloader.R;
import com.sdk.ads.ads.IntertitialAdsEvent;


import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
//import android.widget.SearchView;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class free_SearchVideoActivity extends AppCompatActivity {

    ImageView imgBack;
    SearchView searchView;
    free_Search_VideoAdp adapter;
    EmptyRecyclerView recycleVideoList;
    List<PlayerVideoModel> folderList;
    GetMedia mPlayerPlayerproGetMedia;
    LinearLayout txt_not;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_video);

        
        IntertitialAdsEvent.CallInterstitial(this);
IntertitialAdsEvent.ShowInterstitialAdsOnCreate(this);

        txt_not = findViewById(R.id.txt_not);
        imgBack = findViewById(R.id.imgBack);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mPlayerPlayerproGetMedia = new GetMedia(free_SearchVideoActivity.this);
        recycleVideoList = findViewById(R.id.recycleVideoList);
        recycleVideoList.setEmptyView(txt_not);
        searchView = findViewById(R.id.searchView);
        searchView.requestFocus();
        searchView.setSelected(true);
        searchView.setIconifiedByDefault(false);
        searchView.setFocusable(true);
        searchView.requestFocusFromTouch();

        searchView.setQueryHint(Html.fromHtml("<font color = #000000>" + "Search Here....." + "</font>"));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                if (!folderList.isEmpty()) {
                    txt_not.setVisibility(View.GONE);
                } else {
                    txt_not.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });

        folderList = new ArrayList<>();
        folderList.clear();
        folderList = mPlayerPlayerproGetMedia.getAllVideos();

        adapter = new free_Search_VideoAdp(free_SearchVideoActivity.this, folderList);
        updateData();
        recycleVideoList.setLayoutManager(new LinearLayoutManager(this));
        recycleVideoList.setAdapter(adapter);

    }

    public void updateData() {
        folderList.clear();
        folderList = new GetMedia(free_SearchVideoActivity.this).getAllVideos();
        adapter.updateEmployeeListItems(folderList);
    }

    @Override
    public void onBackPressed() {
        IntertitialAdsEvent.ShowInterstitialAdsOnBack(this);
    }

}