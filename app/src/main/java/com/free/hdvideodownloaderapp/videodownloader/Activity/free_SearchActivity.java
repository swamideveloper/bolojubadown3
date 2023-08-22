package com.free.hdvideodownloaderapp.videodownloader.Activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.free.hdvideodownloaderapp.videodownloader.Utils.SongsUtils;
import com.free.hdvideodownloaderapp.videodownloader.MusicPlayer.Adapter.freeCustomAdapterSearch;
import com.free.hdvideodownloaderapp.videodownloader.R;
import com.sdk.ads.ads.IntertitialAdsEvent;


public class free_SearchActivity extends AppCompatActivity {

    freeCustomAdapterSearch customAdapter;
    SongsUtils songsUtils;
    private RecyclerView lv;
    public static LinearLayout txt_not;
    public static View fragmentsearch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        
        IntertitialAdsEvent.CallInterstitial(this);
IntertitialAdsEvent.ShowInterstitialAdsOnCreate(this);

        ImageView imgBack = findViewById(R.id.imgBack);
        txt_not = findViewById(R.id.txt_not);
        fragmentsearch = findViewById(R.id.fragment);
        fragmentsearch.setVisibility(View.GONE);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        EditText search = findViewById(R.id.editText);
        search.requestFocus();
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                customAdapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        songsUtils = new SongsUtils(this);

        lv = findViewById(R.id.listView1);
        lv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        customAdapter = new freeCustomAdapterSearch(this, songsUtils.allSongs());
        lv.setAdapter(customAdapter);

    }

    @Override
    public void onBackPressed() {
        IntertitialAdsEvent.ShowInterstitialAdsOnBack(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
