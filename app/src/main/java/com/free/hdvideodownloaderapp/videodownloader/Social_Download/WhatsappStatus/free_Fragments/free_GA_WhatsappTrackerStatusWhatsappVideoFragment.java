package com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_Fragments;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_Adapter.freeVideoGAWhatsappTrackerStatusWhatsappStatusAdp;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_Models.TrishulWhatsappStatusModel;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_SCUtils.WAVideo_SingleClickListener;
import com.free.hdvideodownloaderapp.videodownloader.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;


public class free_GA_WhatsappTrackerStatusWhatsappVideoFragment extends Fragment {

    ArrayList<TrishulWhatsappStatusModel> statusModelArrayList;
    private File[] allfiles;
    private freeVideoGAWhatsappTrackerStatusWhatsappStatusAdp whatsappStatusAdapter;
    private int PERMISSIONS_CODE = 100;
    TextView tvEnableNotification;
    RelativeLayout tv_NoResult,rl_Images;
    LinearLayout layout_no_permission;
    RecyclerView rv_fileList;
    SwipeRefreshLayout swiperefresh;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(),
                        android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
        } else {
            layout_no_permission.setVisibility(View.GONE);
            rl_Images.setVisibility(View.VISIBLE);
            initViews();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
//        this.binding = DataBindingUtil.inflate(layoutInflater, R.layout.wass_whatsapp_image, viewGroup, false);
        View view = layoutInflater.inflate(R.layout.wass_whatsapp_image, viewGroup, false);

        tvEnableNotification=view.findViewById(R.id.tv_enable_notification);
        swiperefresh=view.findViewById(R.id.swiperefresh);
        rl_Images=view.findViewById(R.id.rl_Images);
        tv_NoResult=view.findViewById(R.id.tv_NoResult);
        layout_no_permission=view.findViewById(R.id.layout_no_permission);
        rv_fileList=view.findViewById(R.id.rv_fileList);

        tvEnableNotification.setOnClickListener(new WAVideo_SingleClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void performClick(View v) {

                if (ActivityCompat.checkSelfPermission(getContext(),
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(getContext(),
                                android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_CODE);
                } else {
                    layout_no_permission.setVisibility(View.GONE);
                    rl_Images.setVisibility(View.VISIBLE);
                    initViews();
                }
            }
        });
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initViews() {
        this.statusModelArrayList = new ArrayList<>();
        getData();
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public final void onRefresh() {
                free_GA_WhatsappTrackerStatusWhatsappVideoFragment.this.lambda$initViews$0$Status_WhatsappVideoFragment();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void lambda$initViews$0$Status_WhatsappVideoFragment() {
        this.statusModelArrayList = new ArrayList<>();
        getData();
        swiperefresh.setRefreshing(false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getData() {
        this.allfiles = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp/Media/.Statuses").listFiles();
        try {
            Arrays.sort(this.allfiles, freeStatusWhatsappImageFragment3.INSTANCE);
            int i = 0;
            while (true) {
                File[] fileArr = this.allfiles;
                if (i < fileArr.length) {
                    File file = fileArr[i];
                    if (Uri.fromFile(file).toString().endsWith(".mp4")) {
                        this.statusModelArrayList.add(new TrishulWhatsappStatusModel("WhatsStatus: " + (i + 1), Uri.fromFile(file), this.allfiles[i].getAbsolutePath(), file.getName()));
                    }
                    i++;
                }
                try {
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        Arrays.sort(allfiles, freeStatusWhatsappImageFragment4.INSTANCE);
        for (int i2 = 0; i2 < allfiles.length; i2++) {
            File file2 = allfiles[i2];
            if (Uri.fromFile(file2).toString().endsWith(".mp4")) {
                this.statusModelArrayList.add(new TrishulWhatsappStatusModel("WhatsStatusB: " + (i2 + 1), Uri.fromFile(file2), allfiles[i2].getAbsolutePath(), file2.getName()));
            }
        }
        if (this.statusModelArrayList.size() != 0) {
            tv_NoResult.setVisibility(View.GONE);
        } else {
            tv_NoResult.setVisibility(View.VISIBLE);
        }
        this.whatsappStatusAdapter = new freeVideoGAWhatsappTrackerStatusWhatsappStatusAdp(getActivity(), this.statusModelArrayList, 2);
        rv_fileList.setAdapter(this.whatsappStatusAdapter);
    }
}
