package com.free.hdvideodownloaderapp.videodownloader.MusicPlayer.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.free.hdvideodownloaderapp.videodownloader.Activity.free_SearchActivity;
import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.SongModel;
import com.free.hdvideodownloaderapp.videodownloader.Log;
import com.free.hdvideodownloaderapp.videodownloader.MusicPlayer.Activity.free_GlobalDetailActivity;
import com.free.hdvideodownloaderapp.videodownloader.Utils.ImageUtils;
import com.free.hdvideodownloaderapp.videodownloader.Utils.SingleClickListener;
import com.free.hdvideodownloaderapp.videodownloader.Utils.SongsUtils;
import com.free.hdvideodownloaderapp.videodownloader.MusicPlayer.Fragment.freeMusicDockFragment;
import com.free.hdvideodownloaderapp.videodownloader.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.sdk.ads.ads.AllNativeAds;


import java.util.ArrayList;
import java.util.Locale;

public class freeCustomAdapterSearch extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity activity;
    private ArrayList<SongModel> data;
    private ArrayList<SongModel> virginArrayList;
    private static final int LIST_ITEM = 0;
    private static final int LIST_ITEM_ADS = 2;
    private static final int ITEM_FEED_Count = 8;

    public freeCustomAdapterSearch(Activity a, ArrayList<SongModel> d) {
        activity = a;
        data = new ArrayList<>(d);
        virginArrayList = new ArrayList<>(d);
    }

    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;
        if (viewType == LIST_ITEM) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_all_song, parent, false);
            return new MyFolderM(itemView);
        } else if (viewType == LIST_ITEM_ADS) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_item_video_list_ads, parent, false);
            return new ADViewHolder(itemView);
        } else {
            return null;
        }
    }

    class MyFolderM extends RecyclerView.ViewHolder {
        TextView text;
        TextView text1;
        ImageView  imageOverflow;
        ImageView image;
        RelativeLayout liner;

        public MyFolderM(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
            text1 = itemView.findViewById(R.id.text1);
            image = itemView.findViewById(R.id.image);
            imageOverflow = itemView.findViewById(R.id.albumArtImageView);
            liner = itemView.findViewById(R.id.liner);
        }
    }

    class ADViewHolder extends RecyclerView.ViewHolder {

        FrameLayout adsContainer;

        public ADViewHolder(@NonNull View itemView) {
            super(itemView);
            adsContainer = itemView.findViewById(R.id.adsContainer);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return LIST_ITEM;

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder0, @SuppressLint("RecyclerView") int position) {
        if (getItemViewType(position) == LIST_ITEM) {

            MyFolderM holder = (MyFolderM) holder0;

            final SongModel tempValues = data.get(position);
            final String duration = tempValues.getDuration();
            final String artist = tempValues.getArtist();
            final String songName = tempValues.getFileName();
            final String title = tempValues.getTitle();

            String finalTitle;
            if (title != null) {
                finalTitle = title;
            } else {
                finalTitle = songName;
            }

//            if (position % 4 == 0) {
//                holder.liner.setBackgroundTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.color1)));
//                holder.image.setImageDrawable(activity.getResources().getDrawable(R.drawable.music1));
//            } else if (position % 4 == 1) {
//                holder.liner.setBackgroundTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.color2)));
//                holder.image.setImageDrawable(activity.getResources().getDrawable(R.drawable.music2));
//            } else if (position % 4 == 2) {
//                holder.liner.setBackgroundTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.color3)));
//                holder.image.setImageDrawable(activity.getResources().getDrawable(R.drawable.music3));
//            } else if (position % 4 == 3) {
//                holder.liner.setBackgroundTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.color4)));
//                holder.image.setImageDrawable(activity.getResources().getDrawable(R.drawable.music4));
//            }

            final SongsUtils songsUtils = new SongsUtils(activity);
            Glide.with(activity).asBitmap().load((new ImageUtils(activity)).getSongUri(Long.parseLong(tempValues.getAlbumID().toString())))
                    .placeholder(R.drawable.music_default)
                    .error(R.drawable.music_default)
                    .listener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            Log.e("my_imgae", "faild");

                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                            Log.e("my_imgae", "Ready");

                            return false;
                        }
                    }).into(holder.image);


            holder.text.setText(finalTitle);
            holder.text1.setText(String.format("%s • %s", (artist.length() > 25) ? artist.substring(0, 25) + "…" : artist, duration));

            holder.itemView.setOnClickListener(new SingleClickListener() {
                @Override
                public void performClick(View v) {

                    songsUtils.play(activity, position, data);

                    if (free_SearchActivity.fragmentsearch != null) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (freeMusicDockFragment.strtital == null || freeMusicDockFragment.strtital.equalsIgnoreCase("")) {
                                    free_SearchActivity.fragmentsearch.setVisibility(View.GONE);
                                } else {
                                    free_SearchActivity.fragmentsearch.setVisibility(View.VISIBLE);
                                }
                            }
                        }, 400);
                    }
                }
            });
            final PopupMenu pop = new PopupMenu(activity, holder.imageOverflow);
            int[] j = new int[7];
            j[0] = R.id.play_next_musicUtils;
            j[1] = R.id.shuffle_play_musicUtils;
            j[2] = R.id.add_to_queue_musicUtils;
            j[3] = R.id.add_to_playlist_musicUtils;
            j[4] = R.id.goto_album_musicUtils;
            j[5] = R.id.goto_artist_musicUtils;
            j[6] = R.id.info_musicUtils;
            songsUtils.generateMenu(pop, j);
            pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.play_next_musicUtils:
                            songsUtils.playNext(data.get(position));
                            return true;
                        case R.id.add_to_queue_musicUtils:
                            songsUtils.addToQueue(data.get(position));
                            return true;
                        case R.id.add_to_playlist_musicUtils:
                            songsUtils.addToPlaylist(data.get(position));
                            return true;
                        case R.id.shuffle_play_musicUtils:
                            songsUtils.shufflePlay(activity, position, data);
                            return true;
                        case R.id.goto_album_musicUtils:
                            Intent intent = new Intent(activity, free_GlobalDetailActivity.class);
                            intent.putExtra("name", tempValues.getAlbum());
                            intent.putExtra("field", "albums");
                            activity.startActivity(intent);
                            return true;
                        case R.id.goto_artist_musicUtils:
                            Intent intent1 = new Intent(activity, free_GlobalDetailActivity.class);
                            intent1.putExtra("name", tempValues.getArtist());
                            intent1.putExtra("field", "artists");
                            activity.startActivity(intent1);
                            return true;
                        case R.id.info_musicUtils:
                            songsUtils.info(data.get(position)).show();
                            return true;
                        default:
                            return false;
                    }
                }
            });


            holder.imageOverflow.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    pop.show();
                }
            });


        } else if (getItemViewType(position) == LIST_ITEM_ADS) {
            ADViewHolder holder = (ADViewHolder) holder0;
            Log.e("icdnijd", "2==" + position);
            AllNativeAds.NativeBanner((Activity) activity, holder.adsContainer);
        }

    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        data.clear();
        if (charText.length() == 0) {
            data.addAll(virginArrayList);
        } else {
            for (SongModel wp : virginArrayList) {
                if (wp.getTitle().toLowerCase(Locale.getDefault())
                        .contains(charText) || wp.getArtist().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    data.add(wp);
                }
            }
        }

        if (data.size() == 0) {
            if (free_SearchActivity.txt_not != null) {
                free_SearchActivity.txt_not.setVisibility(View.VISIBLE);
            }
        } else {
            if (free_SearchActivity.txt_not != null) {
                free_SearchActivity.txt_not.setVisibility(View.GONE);
            }
        }
        notifyDataSetChanged();
    }

    private int getRealPosition(int position) {
        if (ITEM_FEED_Count == 0) {
            return position;
        } else {
            return position - position / ITEM_FEED_Count;
        }
    }

}
