package com.free.hdvideodownloaderapp.videodownloader.MusicPlayer.Adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Handler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.SongModel;
import com.free.hdvideodownloaderapp.videodownloader.Log;
import com.free.hdvideodownloaderapp.videodownloader.Utils.ImageUtils;
import com.free.hdvideodownloaderapp.videodownloader.Utils.SongsUtils;
import com.free.hdvideodownloaderapp.videodownloader.MusicPlayer.Fragment.freeMainFragmentMusic;
import com.free.hdvideodownloaderapp.videodownloader.MusicPlayer.Fragment.freeMusicDockFragment;
import com.free.hdvideodownloaderapp.videodownloader.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class freeAdapterFavList extends RecyclerView.Adapter<freeAdapterFavList.ViewHolder> {
    Activity activity;
    ArrayList<SongModel> songsList;
    private String listOrigin;
    SongsUtils songsUtils;

    public freeAdapterFavList(ArrayList<SongModel> songsList, Activity activity, String listOrigin) {
        this.activity = activity;
        this.songsList = songsList;
        this.listOrigin = listOrigin;
        songsUtils = new SongsUtils(activity);
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_favorite_song,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        SongModel tempValues = songsList.get(position);
        holder.tvTitle.setText(tempValues.getTitle());
        holder.tvSubTitle.setText(tempValues.getArtist());
        holder.clickMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                songsUtils.play(activity,holder.getAdapterPosition(), songsList);

                if(freeMainFragmentMusic.fragmentmain!=null){
                    Log.e("sdkjf","=="+ freeMusicDockFragment.strtital);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(freeMusicDockFragment.strtital==null || freeMusicDockFragment.strtital.equalsIgnoreCase("") ){
                                freeMainFragmentMusic.fragmentmain.setVisibility(View.GONE);
                            }else {
                                freeMainFragmentMusic.fragmentmain.setVisibility(View.VISIBLE);
                            }
                        }
                    },400);
                }

            }
        });


        Glide.with(activity).asBitmap().load((new ImageUtils(activity)).getSongUri(Long.parseLong(songsList.get(position).getAlbumID().toString())))
                .placeholder( R.drawable.vec)
                .error(R.drawable.vec)
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {

                        return false;
                    }
                }).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return Math.min(6,songsList.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
    TextView tvTitle,tvSubTitle;
    LinearLayout clickMain;
    ImageView img;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvSubTitle = itemView.findViewById(R.id.tvSubTitle);
            clickMain = itemView.findViewById(R.id.click);
            img=itemView.findViewById(R.id.img);
        }
    }
}
