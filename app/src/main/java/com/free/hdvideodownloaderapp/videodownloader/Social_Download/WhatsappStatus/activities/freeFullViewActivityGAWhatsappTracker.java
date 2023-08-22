package com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_SCUtils.WAVideo_SingleClickListener;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_SCUtils.WAV_Utils;
import com.free.hdvideodownloaderapp.videodownloader.R;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_Adapter.freeVideoShowImagesAdapter;
//import com.free.hdvideodownloaderapp.videodownloader.databinding.WassActivityFullViewBinding;
import com.free.hdvideodownloaderapp.videodownloader.databinding.WassActivityFullViewBinding;
import com.sdk.ads.ads.IntertitialAdsEvent;


import java.io.File;
import java.util.ArrayList;

public class freeFullViewActivityGAWhatsappTracker extends AppCompatActivity {

    public int Position = 0;
    public freeFullViewActivityGAWhatsappTracker activity;
    private WassActivityFullViewBinding binding;
    public ArrayList<File> fileArrayList;
    freeVideoShowImagesAdapter showImagesAdapter;
    public Context context;


    @Override
    public void onBackPressed() {
        IntertitialAdsEvent.ShowInterstitialAdsOnBack(this);
    }
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.binding = DataBindingUtil.setContentView(this, R.layout.wass_activity_full_view);


        
        IntertitialAdsEvent.CallInterstitial(this);
IntertitialAdsEvent.ShowInterstitialAdsOnCreate(this);



        fileArrayList = new ArrayList<>();
        TextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText("Video Downloader");
        activity = this;

        if (getIntent().getExtras() != null) {
            fileArrayList = (ArrayList) getIntent().getSerializableExtra("ImageDataFile");
            Position = getIntent().getIntExtra("Position", 0);
        }

        ImageView icn_menu =  findViewById(R.id.back);
        icn_menu.setOnClickListener(new WAVideo_SingleClickListener() {
            @Override
            public void performClick(View v) {
                onBackPressed();
            }
        });

        initViews();
    }

    public void initViews() {
        showImagesAdapter = new freeVideoShowImagesAdapter(this, fileArrayList, this);
        binding.include.vpView.setAdapter(showImagesAdapter);
        binding.include.vpView.setCurrentItem(this.Position);
        binding.include.vpView.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int i) {
            }
            public void onPageScrolled(int i, float f, int i2) {
            }
            public void onPageSelected(int i) {
            }
        });
        binding.include.imDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(freeFullViewActivityGAWhatsappTracker.this.activity);
                builder.setPositiveButton( freeFullViewActivityGAWhatsappTracker.this.getResources().getString(R.string.yes), (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (( fileArrayList.get(Position)).delete()) {
                            deleteFileAA(Position);
                        }
                    }
                });

                builder.setNegativeButton( freeFullViewActivityGAWhatsappTracker.this.getResources().getString(R.string.no), (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog create = builder.create();
                create.setTitle(freeFullViewActivityGAWhatsappTracker.this.getResources().getString(R.string.do_u_want_to_dlt));
                create.show();
            }
        });
        this.binding.include.imShare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (((File) freeFullViewActivityGAWhatsappTracker.this.fileArrayList.get(freeFullViewActivityGAWhatsappTracker.this.Position)).getName().contains(".mp4")) {
                     WAV_Utils.shareVideo(freeFullViewActivityGAWhatsappTracker.this.activity, ((File) freeFullViewActivityGAWhatsappTracker.this.fileArrayList.get(freeFullViewActivityGAWhatsappTracker.this.Position)).getPath());
                    return;
                }
                WAV_Utils.shareImage(freeFullViewActivityGAWhatsappTracker.this.activity, ((File) freeFullViewActivityGAWhatsappTracker.this.fileArrayList.get(freeFullViewActivityGAWhatsappTracker.this.Position)).getPath());
            }
        });
        this.binding.include.imWhatsappShare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (((File) freeFullViewActivityGAWhatsappTracker.this.fileArrayList.get(freeFullViewActivityGAWhatsappTracker.this.Position)).getName().contains(".mp4")) {
                    WAV_Utils.shareImageVideoOnWhatsapp(freeFullViewActivityGAWhatsappTracker.this.activity, ((File) freeFullViewActivityGAWhatsappTracker.this.fileArrayList.get(freeFullViewActivityGAWhatsappTracker.this.Position)).getPath(), true);
                } else {
                    WAV_Utils.shareImageVideoOnWhatsapp(freeFullViewActivityGAWhatsappTracker.this.activity, ((File) freeFullViewActivityGAWhatsappTracker.this.fileArrayList.get(freeFullViewActivityGAWhatsappTracker.this.Position)).getPath(), false);
                }
            }
        });
    }

    public void onResume() {
        super.onResume();
    }

    public void deleteFileAA(int i) {
        this.fileArrayList.remove(i);
        this.showImagesAdapter.notifyDataSetChanged();
        WAV_Utils.setToast(this.activity, getResources().getString(R.string.file_deleted));
        if (this.fileArrayList.size() == 0) {
            onBackPressed();
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
