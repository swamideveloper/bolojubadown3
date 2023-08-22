package com.free.hdvideodownloaderapp.videodownloader.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.free.hdvideodownloaderapp.videodownloader.Utils.ImageUtils;
import com.free.hdvideodownloaderapp.videodownloader.Utils.SongsUtils;
import com.free.hdvideodownloaderapp.videodownloader.R;


public class free_ImageDetailFragment extends Fragment {
    private static final String IMAGE_DATA_EXTRA = "resId";
    private int mImageNum;
    private ImageView mImageView;

    public static free_ImageDetailFragment newInstance(int imageNum) {
        final free_ImageDetailFragment f = new free_ImageDetailFragment();
        final Bundle args = new Bundle();
        args.putInt(IMAGE_DATA_EXTRA, imageNum);
        f.setArguments(args);
        return f;
    }

    public free_ImageDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageNum = getArguments() != null ? getArguments().getInt(IMAGE_DATA_EXTRA) : -1;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.play_activity_viewpager_image, container, false);
        mImageView = v.findViewById(R.id.imageView);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Runnable run = new Runnable() {
            @Override
            public void run() {
                (new ImageUtils(getContext())).getFullImageByPicasso(((new SongsUtils(getActivity())).queue()).get(mImageNum).getAlbumID(), mImageView);
            }
        };
        run.run();
    }
}
