package com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_Fragments;


import android.Manifest;
import android.content.Context;
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
import java.util.Comparator;

public class freeTrackerStatusWhatsappImageFragment extends Fragment {

    private File[] allfiles;
    RecyclerView rv_fileList;
    SwipeRefreshLayout swiperefresh;
    LinearLayout layout_no_permission;
    //    WassWhatsappImageBinding binding;
    ArrayList<TrishulWhatsappStatusModel> statusModelArrayList;
    private freeVideoGAWhatsappTrackerStatusWhatsappStatusAdp whatsappStatusAdapter;
    RelativeLayout tv_NoResult,rl_Images;
    private int PERMISSIONS_CODE = 100;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
//        binding = DataBindingUtil.inflate(layoutInflater, R.layout.wass_whatsapp_image, viewGroup, false);

        View view = layoutInflater.inflate(R.layout.wass_whatsapp_image, viewGroup, false);

        tv_NoResult=view.findViewById(R.id.tv_NoResult);
        rv_fileList=view.findViewById(R.id.rv_fileList);
        rl_Images=view.findViewById(R.id.rl_Images);
        layout_no_permission=view.findViewById(R.id.layout_no_permission);
        swiperefresh=view.findViewById(R.id.swiperefresh);

        view.findViewById(R.id.tv_enable_notification).setOnClickListener(new WAVideo_SingleClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void performClick(View v) {

                if (ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(getContext(),
                                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_CODE);
                } else {
                    view.findViewById(R.id.layout_no_permission).setVisibility(View.GONE);
                    view.findViewById(R.id.rl_Images).setVisibility(View.VISIBLE);
                    initViews();
                }

            }
        });
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
        } else {
            layout_no_permission.setVisibility(View.GONE);
            rl_Images.setVisibility(View.VISIBLE);
            initViews();
        }
    }

    public boolean hasPermissionsRam(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {

                    layout_no_permission.setVisibility(View.VISIBLE);
                    rl_Images.setVisibility(View.GONE);
                    return false;
                }
            }
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initViews() {
        statusModelArrayList = new ArrayList<>();
        getData();
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {


            @Override
            public final void onRefresh() {
                lambda$initViews$0$Status_WhatsappImageFragment();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void lambda$initViews$0$Status_WhatsappImageFragment() {
        statusModelArrayList = new ArrayList<>();
        getData();
        swiperefresh.setRefreshing(false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getData() {
        allfiles = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp/Media/.Statuses").listFiles();
        try {
            Arrays.sort(allfiles, new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    File file = (File) o1;
                    File file2 = (File) o2;
                    if (file.lastModified() > file2.lastModified()) {
                        return -1;
                    }
                    return file.lastModified() < file2.lastModified() ? 1 : 0;
                }
            });
            int i = 0;
            while (true) {
                File[] fileArr = allfiles;
                if (i < fileArr.length) {
                    File file = fileArr[i];
                    if (Uri.fromFile(file).toString().endsWith(".png") || Uri.fromFile(file).toString().endsWith(".jpg")) {
                        statusModelArrayList.add(new TrishulWhatsappStatusModel("WhatsStatus: " + (i + 1), Uri.fromFile(file), allfiles[i].getAbsolutePath(), file.getName()));
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
        Arrays.sort(allfiles, freeStatusWhatsappImageFragment2.INSTANCE);
        for (int i2 = 0; i2 < allfiles.length; i2++) {
            File file2 = allfiles[i2];
            if (Uri.fromFile(file2).toString().endsWith(".png") || Uri.fromFile(file2).toString().endsWith(".jpg")) {
                statusModelArrayList.add(new TrishulWhatsappStatusModel("WhatsStatusB: " + (i2 + 1), Uri.fromFile(file2), allfiles[i2].getAbsolutePath(), file2.getName()));
            }
        }
        if (statusModelArrayList.size() != 0) {
            tv_NoResult .setVisibility(View.GONE);
        } else {
            tv_NoResult.setVisibility(View.VISIBLE);
        }
        whatsappStatusAdapter = new freeVideoGAWhatsappTrackerStatusWhatsappStatusAdp(getActivity(), statusModelArrayList, 1);
        rv_fileList.setAdapter(whatsappStatusAdapter);
    }

    static int lambda$getData$1(Object obj, Object obj2) {
        File file = (File) obj;
        File file2 = (File) obj2;
        if (file.lastModified() > file2.lastModified()) {
            return -1;
        }
        return file.lastModified() < file2.lastModified() ? 1 : 0;
    }

    static int lambda$getData$2(Object obj, Object obj2) {
        File file = (File) obj;
        File file2 = (File) obj2;
        if (file.lastModified() > file2.lastModified()) {
            return -1;
        }
        return file.lastModified() < file2.lastModified() ? 1 : 0;
    }
}
