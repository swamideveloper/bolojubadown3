package com.free.hdvideodownloaderapp.videodownloader.AddPlayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.free.hdvideodownloaderapp.videodownloader.Activity.free_SearchVideoActivity;
import com.free.hdvideodownloaderapp.videodownloader.Def.Common;
import com.free.hdvideodownloaderapp.videodownloader.Fragment.free_VideoFragmentSecond;
import com.free.hdvideodownloaderapp.videodownloader.R;

public class VideoFragment extends Fragment {

    VideoFragment activity;
    RelativeLayout rl;
    public ImageView bg_img;
    ImageView ivSearchView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.video_fragment, container, false);
        activity = this;

        ivSearchView = view.findViewById(R.id.ivSearchView);
        rl = view.findViewById(R.id.rl);

        ivSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), free_SearchVideoActivity.class));
            }
        });

        FragmentManager fragmentManager = getChildFragmentManager();
        fragmentManager
                .beginTransaction()
                .add(R.id.fm_layout, new free_VideoFragmentSecond(), "fragmentOneTag")
                .commit();

        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(getActivity(), rl);
                popup.getMenuInflater().inflate(R.menu.st_option, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.refresh:
                                break;
                            case R.id.share_app:
                                Common.ShareApp(getActivity());
                                break;
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });
        return view;
    }

}