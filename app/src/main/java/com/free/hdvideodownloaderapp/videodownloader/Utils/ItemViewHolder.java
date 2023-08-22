package com.free.hdvideodownloaderapp.videodownloader.Utils;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.free.hdvideodownloaderapp.videodownloader.R;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    public final TextView text, text1;
    public final ImageView image;
    public final   RelativeLayout liner;
    public final ImageView albumArtImageView;

    public ItemViewHolder(View itemView) {
        super(itemView);
        text = itemView.findViewById(R.id.text);
        text1 = itemView.findViewById(R.id.text1);
        image = itemView.findViewById(R.id.image);
        liner = itemView.findViewById(R.id.liner);
        albumArtImageView = itemView.findViewById(R.id.albumArtImageView);
    }
}