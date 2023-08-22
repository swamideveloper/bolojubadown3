package com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_Adapter.freeVideoSCFilesAdapter;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_Models.SC_Status;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_SCUtils.SC_Common1;
import com.free.hdvideodownloaderapp.videodownloader.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class freeSCSavedFilesFragment extends Fragment {

    private freeVideoSCFilesAdapter a1;
    private final Handler b1 = new Handler();
    private RelativeLayout c1;
    private ProgressBar d1;
    private RecyclerView e1;
    private List<SC_Status> f1 = new ArrayList();
    RelativeLayout h1;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.wass_fragment_saved_files, viewGroup, false);
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);

        this.e1 =  view.findViewById(R.id.recyclerViewFiles);
        this.d1 =  view.findViewById(R.id.progressBar);
        this.c1 =  view.findViewById(R.id.no_files_found);
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().build());
        this.e1.setHasFixedSize(true);
        this.e1.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        h1 = view.findViewById(R.id.iv_no_Found_data);
        getFiles();
    }

    public void getFiles() {
        try {
            File file = new File(SC_Common1.SaveFilePath);
            if (file.exists()) {

                 h1.setVisibility(View.GONE);
                new Thread(new Runnable() {
                    public final File f1;
                    final File valfile;
                    {
                        this.valfile = file;
                        this.f1 = file;
                    }
                    public final void run() {
                        freeSCSavedFilesFragment.this.SavedFilesFragment(this.f1);
                    }
                }).start();
                return;
            }

            h1.setVisibility(View.VISIBLE);
            this.d1.setVisibility(View.GONE);
        } catch (Exception e) {
        }
    }

    public void SavedFilesFragment(File file) {
        File[] listFiles = file.listFiles();
        this.f1.clear();
        if (listFiles == null || listFiles.length <= 0) {
            this.b1.post(new Runnable() {
                public final void run() {
                    freeSCSavedFilesFragment.this.ShowProgress();
                }
            });
        } else {
            Arrays.sort(listFiles);
            for (File file2 : listFiles) {
                this.f1.add(new SC_Status(file2, file2.getName(), file2.getAbsolutePath()));
            }
            this.b1.post(new Runnable() {
                public final void run() {
                    a1 = new freeVideoSCFilesAdapter(f1);
                    e1.setAdapter(a1);
                    removeDuplicates(f1);
                    d1.setVisibility(View.GONE);
                }
            });
        }
    }

    private List<SC_Status> removeDuplicates(List<SC_Status> list) {
        int count = list.size();
        f1 = list;
        for (int i = 0; i < count; i++) {
            for (int j = i + 1; j < count; j++) {
                if (f1.get(i).getPath().equals(f1.get(j).getPath())) {
                    f1.remove(j--);
                    count--;
                }
            }
        }
        return f1;
    }

    @SuppressLint("WrongConstant")
    public void ShowProgress() {
        this.d1.setVisibility(8);
        this.c1.setVisibility(0);
    }

    @Override
    public void onResume() {
        super.onResume();
        getFiles();
    }
}
