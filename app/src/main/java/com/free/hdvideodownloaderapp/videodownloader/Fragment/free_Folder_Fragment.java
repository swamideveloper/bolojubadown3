package com.free.hdvideodownloaderapp.videodownloader.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Adapter.PlayerFolderAdapter;
import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.GetMedia;
import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.PlayerFolderModel;
import com.free.hdvideodownloaderapp.videodownloader.R;

import java.util.List;


public class free_Folder_Fragment extends Fragment {

    RecyclerView rvFolderList;
    LinearLayout llImpty;
    GetMedia mPlayerPlayerproGetMedia;
    static PlayerFolderAdapter adapter;
    SwipeRefreshLayout refershLayout;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_folder_, container, false);

        rvFolderList = view.findViewById(R.id.rvFolderList);
        refershLayout = view.findViewById(R.id.refershLayout);
        llImpty = view.findViewById(R.id.llImpty);
        mPlayerPlayerproGetMedia = new GetMedia(getActivity());

        new loadVideos().execute();
        refershLayout.setRefreshing(true);

        refershLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new loadVideos().execute();
            }
        });
        return view;
    }

    class loadVideos extends AsyncTask<String, String, List<PlayerFolderModel>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            llImpty.setVisibility(View.GONE);
        }

        @Override
        protected List<PlayerFolderModel> doInBackground(String... strings) {
            return mPlayerPlayerproGetMedia.getfolderList();
        }

        @Override
        protected void onPostExecute(List<PlayerFolderModel> modelList) {
            super.onPostExecute(modelList);
            if (modelList.size() != 0) {
                rvFolderList.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                adapter = new PlayerFolderAdapter(getActivity(), modelList);
                rvFolderList.setAdapter(adapter);
            } else {
                llImpty.setVisibility(View.VISIBLE);
            }
            refershLayout.setRefreshing(false);

        }
    }

}