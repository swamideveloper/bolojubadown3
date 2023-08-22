package com.free.hdvideodownloaderapp.videodownloader.Social_Download.Configs.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.free.hdvideodownloaderapp.videodownloader.Social_Download.Models.File_type;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.fragments.VideosFragment;

public class freeTabAdapter extends FragmentStateAdapter {
    public freeTabAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position==0)
        {
            return VideosFragment.newInstance(File_type.VIDEO);
        }
        else if (position==1)
        {
            return VideosFragment.newInstance(File_type.AUDIO);
        }
        else
        {
            return VideosFragment.newInstance(File_type.IMAGE);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
