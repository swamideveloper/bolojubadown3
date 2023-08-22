package com.free.hdvideodownloaderapp.videodownloader.Social_Download.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.free.hdvideodownloaderapp.videodownloader.Social_Download.Configs.Adapters.freeDownloadsAdapter;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.Configs.SettingsManager;
import com.free.hdvideodownloaderapp.videodownloader.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class VideosFragment extends Fragment {
    private View view;
    private RecyclerView downloadsList;
    private freeDownloadsAdapter adapter;
    com.free.hdvideodownloaderapp.videodownloader.Social_Download.Models.File_type File_type;
    TextView no_data;

    public VideosFragment(com.free.hdvideodownloaderapp.videodownloader.Social_Download.Models.File_type _file_type) {
        File_type=_file_type;
    }

    public static VideosFragment newInstance(com.free.hdvideodownloaderapp.videodownloader.Social_Download.Models.File_type _file_type) {
        VideosFragment fragment = new VideosFragment(_file_type);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view==null)
        {
            view=inflater.inflate(R.layout.fragment_videos, container, false);
            downloadsList = view.findViewById(R.id.downloadsCompletedList);
            no_data = view.findViewById(R.id.no_data);

            File videoFile = new File(SettingsManager.DOWNLOAD_FOLDER_VIDEO);
            if(File_type== com.free.hdvideodownloaderapp.videodownloader.Social_Download.Models.File_type.AUDIO)
            {
                videoFile = new File(SettingsManager.DOWNLOAD_FOLDER_AUDIO);
            }
            if(File_type== com.free.hdvideodownloaderapp.videodownloader.Social_Download.Models.File_type.IMAGE)
            {
                videoFile = new File(SettingsManager.DOWNLOAD_FOLDER_IMAGES);
            }
            if(videoFile.exists())
            {
                List<File> nonExistentFiles = new ArrayList<>(Arrays.asList(videoFile.listFiles()));
                if(!nonExistentFiles.isEmpty())
                {
                    adapter=new freeDownloadsAdapter(nonExistentFiles,getActivity(),File_type);
                    downloadsList.setAdapter(adapter);
                    downloadsList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
                    downloadsList.setHasFixedSize(true);
                    no_data.setVisibility(View.GONE);
                }else{
                    no_data.setVisibility(View.VISIBLE);
                }
            }
        }
        return view;
    }
}