package com.free.hdvideodownloaderapp.videodownloader.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.PlayerVideoModel;
import com.free.hdvideodownloaderapp.videodownloader.R;
import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.SimpleExoPlayer;

import java.util.List;

public class free_BottomShitVideosList_adapter extends RecyclerView.Adapter<free_BottomShitVideosList_adapter.holder> {
    private final Context ctx;
    private final List<PlayerVideoModel> list;
    public final SimpleExoPlayer player;
    class holder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        int position;
        final TextView text1;
        final TextView txt2;

        holder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.thumb);
            this.text1 = (TextView) view.findViewById(R.id.name);
            this.txt2 = (TextView) view.findViewById(R.id.duration);
            this.imageView.setOnClickListener(new View.OnClickListener() {
                public final void onClick(View view) {
                }
            });
        }
    }

    public free_BottomShitVideosList_adapter(Context context, List<PlayerVideoModel> list2, SimpleExoPlayer simpleExoPlayer) {
        this.ctx = context;
        this.list = list2;
        this.player = simpleExoPlayer;
    }

    public holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new holder(LayoutInflater.from(this.ctx).inflate(R.layout.bottomshit_videoslist_item, viewGroup, false));
    }

    public void onBindViewHolder(holder holder2, int i) {
        try {
            holder2.position = i;
            Glide.with(this.ctx).load(this.list.get(i).getData()).into(holder2.imageView);
            holder2.text1.setText(this.list.get(i).getTitle());
            holder2.txt2.setText(this.list.get(i).getDuartion());
        } catch (Exception unused) {
        }
    }

    public int getItemCount() {
        return this.list.size();
    }
}
