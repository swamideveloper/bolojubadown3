package com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_Models.FullViewModel;
import com.free.hdvideodownloaderapp.videodownloader.R;

import java.util.ArrayList;


public class freeVideoViewPageAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<FullViewModel> mListenerList;

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    @Override
    public void restoreState(Parcelable parcelable, ClassLoader classLoader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    public freeVideoViewPageAdapter(ArrayList<FullViewModel> arrayList, Context context2) {
        this.context = context2;
        this.mListenerList = arrayList;
    }

    @Override
    public int getCount() {
        return this.mListenerList.size();
    }

    @SuppressLint("WrongConstant")
    @Override
    public Object instantiateItem(ViewGroup viewGroup, final int i) {
        LayoutInflater layoutInflater2 = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layoutInflater = layoutInflater2;
        View inflate = layoutInflater2.inflate(R.layout.wass_slidingimages_layout, (ViewGroup) null);
        ImageView imageView =  inflate.findViewById(R.id.im_fullViewImage);
        ImageView imageView2 =  inflate.findViewById(R.id.im_vpPlay);
        final VideoView videoView =  inflate.findViewById(R.id.videoView);
        if (this.mListenerList.get(i).isVideo()) {
            imageView2.setVisibility(0);
        } else {
            imageView2.setVisibility(8);
        }
        ((RequestBuilder) Glide.with(this.context).load(this.mListenerList.get(i).getImageUrl()).diskCacheStrategy(DiskCacheStrategy.ALL)).into(imageView);
        imageView2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                videoView.setVisibility(0);
                try {
                    MediaController mediaController = new MediaController(freeVideoViewPageAdapter.this.context);
                    mediaController.setAnchorView(videoView);
                    Uri parse = Uri.parse(( freeVideoViewPageAdapter.this.mListenerList.get(i)).getVideoUrl());
                    videoView.setMediaController(mediaController);
                    videoView.setVideoURI(parse);
                    videoView.start();
                    videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                        public void onPrepared(MediaPlayer mediaPlayer) {
                        }
                    });
                    videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                        public void onCompletion(MediaPlayer mediaPlayer) {
                            videoView.setVisibility(8);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        ( viewGroup).addView(inflate, 0);
        return inflate;
    }

    @Override
    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        ( viewGroup).removeView((View) obj);
    }
}
