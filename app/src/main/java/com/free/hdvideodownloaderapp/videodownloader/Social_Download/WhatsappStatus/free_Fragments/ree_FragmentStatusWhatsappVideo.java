package com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_Adapter.freeVideoAdapterWhatsappStatus;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_Models.ModelWhatsappStatus;
import com.free.hdvideodownloaderapp.videodownloader.R;
import com.free.hdvideodownloaderapp.videodownloader.databinding.WassFragmentWhatsappImageBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;


public class ree_FragmentStatusWhatsappVideo extends Fragment {
    private File[] allfiles;
    WassFragmentWhatsappImageBinding binding;
    ArrayList<ModelWhatsappStatus> statusModelArrayList;
    private freeVideoAdapterWhatsappStatus whatsappStatusAdapter;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        binding =  DataBindingUtil.inflate(layoutInflater, R.layout.wass_fragment_whatsapp_image, viewGroup, false);
        initViews();
        return binding.getRoot();
    }

    private void initViews() {
        statusModelArrayList = new ArrayList<>();
        getData();
        binding.swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public final void onRefresh() {
                lambda$initViews$0$Status_WhatsappVideoFragment();
            }
        });
    }

    public  void lambda$initViews$0$Status_WhatsappVideoFragment() {
        statusModelArrayList = new ArrayList<>();
        getData();
        binding.swiperefresh.setRefreshing(false);
    }

    private void getData() {
        allfiles = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp/Media/.Statuses").listFiles();

        if (allfiles != null) {
            try {
                Arrays.sort(allfiles, freeStatusWhatsappImageFragment3.INSTANCE);
                int i = 0;
                while (true) {
                    File[] fileArr = allfiles;
                    if (i < fileArr.length) {
                        File file = fileArr[i];
                        if (Uri.fromFile(file).toString().endsWith(".mp4")) {
                            statusModelArrayList.add(new ModelWhatsappStatus("WhatsStatus: " + (i + 1), Uri.fromFile(file), allfiles[i].getAbsolutePath(), file.getName()));
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
                    statusModelArrayList.add(new ModelWhatsappStatus("WhatsStatusB: " + (i2 + 1), Uri.fromFile(file2), allfiles[i2].getAbsolutePath(), file2.getName()));
                }
            }
            if (statusModelArrayList.size() != 0) {
                binding.tvNoResult.setVisibility(View.GONE);
            } else {
                binding.tvNoResult.setVisibility(View.VISIBLE);
            }
            whatsappStatusAdapter = new freeVideoAdapterWhatsappStatus(getActivity(), statusModelArrayList);
            binding.rvFileList.setAdapter(whatsappStatusAdapter);
        } else {
            if (statusModelArrayList.size() != 0) {
                binding.tvNoResult.setVisibility(View.GONE);
            } else {
                binding.tvNoResult.setVisibility(View.VISIBLE);
            }
        }

    }

    static  int lambda$getData$1(Object obj, Object obj2) {
        File file = (File) obj;
        File file2 = (File) obj2;
        if (file.lastModified() > file2.lastModified()) {
            return -1;
        }
        return file.lastModified() < file2.lastModified() ? 1 : 0;
    }

    static  int lambda$getData$2(Object obj, Object obj2) {
        File file = (File) obj;
        File file2 = (File) obj2;
        if (file.lastModified() > file2.lastModified()) {
            return -1;
        }
        return file.lastModified() < file2.lastModified() ? 1 : 0;
    }
}
