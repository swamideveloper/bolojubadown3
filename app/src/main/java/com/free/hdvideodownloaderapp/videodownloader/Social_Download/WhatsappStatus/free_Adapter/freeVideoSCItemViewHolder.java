package com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_Adapter;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.recyclerview.widget.RecyclerView;

import com.free.hdvideodownloaderapp.videodownloader.R;


public class freeVideoSCItemViewHolder extends RecyclerView.ViewHolder {
    public ImageView imageView,video_icon;
    public ImageView save;
    public ImageView save_done;
    public ImageButton share;
    public VideoView videoView;

    public freeVideoSCItemViewHolder(View view) {
        super(view);
        this.imageView =  view.findViewById(R.id.ivThumbnail);
        this.save =  view.findViewById(R.id.save);
        this.video_icon =  view.findViewById(R.id.video_icon);
        this.save_done =  view.findViewById(R.id.save_done);
        this.share =  view.findViewById(R.id.share);
        this.videoView =  view.findViewById(R.id.video_full);
    }
}
