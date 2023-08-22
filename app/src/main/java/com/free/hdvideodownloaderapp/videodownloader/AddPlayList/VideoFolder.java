package com.free.hdvideodownloaderapp.videodownloader.AddPlayList;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.free.hdvideodownloaderapp.videodownloader.Fragment.free_VideoFragmentSecond;
import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Adapter.VideoListAdapter;
import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.PlayerVideoModel;
import com.free.hdvideodownloaderapp.videodownloader.Log;
import com.free.hdvideodownloaderapp.videodownloader.R;

import java.util.ArrayList;
import java.util.List;

public class VideoFolder extends Fragment {

    View view;
    RecyclerView rvFolderList;
    GetMedia mPlayerPlayerproGetMedia;
    VideoListAdapter adapter;
    SwipeRefreshLayout refershLayout;
    List<PlayerVideoModel> playerVideoModelList;
    List<PlayerVideoModel> playerVideoModelList0;
    LinearLayout txtNoData;
    public static boolean isSwitched = true;
    private int Storage_Permission_code = 1;
    int spanCount=2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.folder_fragment, container, false);
        try {
            try {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    requestStoragePermission();
                }
                else {
                    new loadVideos().execute();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        getIds();
//        new loadVideos().execute();

        refershLayout.setRefreshing(true);
        refershLayout.setOnRefreshListener(() -> new loadVideos().execute());
        return view;
    }

    @Override
    public void onResume() {
        new loadVideos().execute();
        super.onResume();
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(getContext()).setTitle("Permission Needed").setMessage("Need to read songs from your storage").setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Storage_Permission_code);
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Storage_Permission_code);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Storage_Permission_code) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                new loadVideos().execute();
            } else {
            }
        }
    }
    private void getIds() {
        rvFolderList = view.findViewById(R.id.rvFolderList);
        refershLayout = view.findViewById(R.id.refershLayout);
        txtNoData = view.findViewById(R.id.txtNoData);

        free_VideoFragmentSecond.gridlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSwitched = VideoListAdapter.toggleItemViewType();
                rvFolderList.setLayoutManager(isSwitched ? new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,
                        false) : new GridLayoutManager(getContext(), spanCount, RecyclerView.VERTICAL, false));
                if (playerVideoModelList0.size() != 0) {
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mPlayerPlayerproGetMedia = new GetMedia(getActivity());
    }


    class loadVideos extends AsyncTask<String, String, List<PlayerVideoModel>> {

        protected void onPreExecute() {

            super.onPreExecute();
            txtNoData.setVisibility(View.GONE);
        }

        protected List<PlayerVideoModel> doInBackground(String... strings) {
            playerVideoModelList = new ArrayList<>();
            playerVideoModelList0 = new ArrayList<>();
            playerVideoModelList.clear();
            playerVideoModelList0.clear();
            try {
                playerVideoModelList = mPlayerPlayerproGetMedia.getDEMOVideo0();
            } catch (Exception e) {
                e.printStackTrace();
            }

            int j = 0;
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

            Log.e("OHHH", "onPostExecute: "+playerVideoModelList0.size());

            if (playerVideoModelList0.size() != 0) {

                if (myvalue == 0) {
                    isSwitched = VideoListAdapter.toggleItemViewType();
                    myvalue++;
                }
//
                rvFolderList.setLayoutManager(isSwitched ? new LinearLayoutManager(getContext(),
                        RecyclerView.VERTICAL, false) : new GridLayoutManager(getContext(), spanCount, RecyclerView.VERTICAL, false));
                adapter = new VideoListAdapter(getContext(), playerVideoModelList0,
                        1, isSwitched, playerVideoModelList);
                rvFolderList.setAdapter(adapter);

                txtNoData.setVisibility(View.GONE);

            } else {

                txtNoData.setVisibility(View.VISIBLE);
            }
            refershLayout.setRefreshing(false);
        }
    }

    int myvalue = 0;

}