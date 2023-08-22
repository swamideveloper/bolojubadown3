package com.free.hdvideodownloaderapp.videodownloader.MusicPlayer.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.SongModel;
import com.free.hdvideodownloaderapp.videodownloader.Log;
import com.free.hdvideodownloaderapp.videodownloader.Utils.ImageUtils;
import com.free.hdvideodownloaderapp.videodownloader.Utils.SingleClickListener;
import com.free.hdvideodownloaderapp.videodownloader.Utils.SongsUtils;
import com.free.hdvideodownloaderapp.videodownloader.MusicPlayer.Activity.free_GlobalDetailActivity;
import com.free.hdvideodownloaderapp.videodownloader.MusicPlayer.Fragment.freeMusicDockFragment;
import com.free.hdvideodownloaderapp.videodownloader.MusicPlayer.Fragment.freePlaylistFragment;
import com.free.hdvideodownloaderapp.videodownloader.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.sdk.ads.ads.AllNativeAds;


import java.util.ArrayList;

public class freeRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    SongsUtils songsUtils;
    private ArrayList<SongModel> data;
    private ArrayList<SongModel> data_onlyPlass;
    private String listOrigin;
    private Activity activity;
    private static final int LIST_ITEM = 0;
    private static final int LIST_ITEM_ADS = 2;

    class ViewHolderR extends RecyclerView.ViewHolder {
        TextView txtHeader, txtSubHeader;
        ImageView imageOverflow;
        ImageView albumArt;
        RelativeLayout view,liner;


        ViewHolderR(View v) {
            super(v);
            txtHeader = v.findViewById(R.id.text);
            txtSubHeader = v.findViewById(R.id.text1);
//            duration = v.findViewById(R.id.textTime);
            imageOverflow = v.findViewById(R.id.albumArtImageView);
            albumArt = v.findViewById(R.id.image);
            liner = v.findViewById(R.id.liner);
        }
    }


    public freeRecyclerViewAdapter(ArrayList<SongModel> myDataSet, Activity act, String type, ArrayList<SongModel> myDataSet_onlyPass) {
        data = myDataSet;
        data_onlyPlass = myDataSet_onlyPass;
        activity = act;
        listOrigin = type;
        songsUtils = new SongsUtils(act);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = null;
        if (viewType == LIST_ITEM) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_all_song, parent, false);
            return new ViewHolderR(itemView);
        } else if (viewType == LIST_ITEM_ADS) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_item_music_list_ads, parent, false);
            return new ADViewHolder(itemView);
        }  else {
            return null;
        }
    }

    class ADViewHolder extends RecyclerView.ViewHolder {

        FrameLayout adsContainer;

        public ADViewHolder(@NonNull View itemView) {
            super(itemView);
            adsContainer=itemView.findViewById(R.id.adsContainer);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder0, int position) {


        if (getItemViewType(position) == LIST_ITEM ) {

            ViewHolderR holder = (ViewHolderR) holder0;
            SongModel tempValues = data.get(position);

            String duration = tempValues.getDuration();
            String artist = tempValues.getArtist();
            String songName = tempValues.getFileName();
            String title = tempValues.getTitle();

//            holder.textTrackID.setText((holder.getAdapterPosition() + 1) + "");

            String finalTitle;
            if (title != null) {
                finalTitle = title;
            } else {
                finalTitle = songName;
            }
//            if (position % 4 == 0) {
//                holder.liner.setBackgroundTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.color1)));
//                holder.albumArt.setImageDrawable(activity.getResources().getDrawable(R.drawable.music1));
//            } else if (position % 4 == 1) {
//                holder.liner.setBackgroundTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.color2)));
//                holder.albumArt.setImageDrawable(activity.getResources().getDrawable(R.drawable.music2));
//            } else if (position % 4 == 2) {
//                holder.liner.setBackgroundTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.color3)));
//                holder.albumArt.setImageDrawable(activity.getResources().getDrawable(R.drawable.music3));
//            } else if (position % 4 == 3) {
//                holder.liner.setBackgroundTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.color4)));
//                holder.albumArt.setImageDrawable(activity.getResources().getDrawable(R.drawable.music4));
//            }

            holder.txtHeader.setText(finalTitle);
            Log.e("finalTitle", "finalTitle: " + finalTitle);
            Log.e("getAlbumID", "getAlbumID: " + tempValues.getAlbumID());
            if (listOrigin.equals("artists")) {
                holder.txtSubHeader.setText(tempValues.getAlbum().trim());
            } else {
                holder.txtSubHeader.setText(artist);
            }
//            holder.duration.setText(duration);

            Glide.with(activity).asBitmap().load((new ImageUtils(activity)).getSongUri(Long.parseLong(tempValues.getAlbumID().toString())))
                    .placeholder( R.drawable.empty_music)
                    .error(R.drawable.empty_music)
                    .listener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            Log.e("my_imgae","faild");

                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                            Log.e("my_imgae","Ready");

                            return false;
                        }
                    }).into(holder.albumArt);



            final PopupMenu pop = new PopupMenu(activity, holder.imageOverflow);
            int[] j;
            switch (listOrigin) {
                case "albums":
                    j = new int[6];
                    break;
                case "artists":
                    j = new int[6];
                    break;
                case "mostplayed":
                    j = new int[7];
                    break;
                case "history":
                    j = new int[6];
                    break;
                case "folder_list":
                    j = new int[6];
                    break;
                default:
                    if (isInteger(listOrigin) || listOrigin.equals("favourites")) {
                        j = new int[8];
                        j[j.length - 1] = R.id.remove_musicUtils;
                    } else {
                        j = new int[7];
                    }
                    break;
            }
            j[0] = R.id.play_next_musicUtils;
            j[1] = R.id.shuffle_play_musicUtils;
            j[2] = R.id.add_to_queue_musicUtils;
            j[3] = R.id.add_to_playlist_musicUtils;
            switch (listOrigin) {
                case "albums":
                    j[4] = R.id.goto_artist_musicUtils;
                    j[5] = R.id.info_musicUtils;
                    break;
                case "artists":
                    j[4] = R.id.goto_album_musicUtils;
                    j[5] = R.id.info_musicUtils;
                    break;
                case "history":
                    j[4] = R.id.goto_album_musicUtils;
                    j[5] = R.id.info_musicUtils;
                    break;
                case "folder_list":
                    j[4] = R.id.goto_album_musicUtils;
                    j[5] = R.id.info_musicUtils;
                    break;
                default:
                    j[4] = R.id.goto_album_musicUtils;
                    j[5] = R.id.goto_artist_musicUtils;
                    j[6] = R.id.info_musicUtils;
                    break;
            }
            songsUtils.generateMenu(pop, j);
            final SongModel finalTempValues = tempValues;
            pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.info_musicUtils:
                            songsUtils.info(data.get(holder.getAdapterPosition())).show();
                            return true;
                        case R.id.remove_musicUtils:

                            int poss=0;
                            for (int i = 0; i < data_onlyPlass.size() ; i++) {
                                if(data_onlyPlass.get(i).getTitle().equals(data.get(holder.getAdapterPosition()).getTitle())){
                                    poss=i;
                                    break;
                                }
                            }
                            int finalPoss = poss;
                            Log.e("nsfjidk","=="+finalPoss);
                            ArrayList<SongModel> aaa;
                            if (!listOrigin.equals("favourites")) {
                                aaa = new ArrayList<>(songsUtils.playlistSongs(Integer.parseInt(listOrigin)));
                                aaa.remove(finalPoss);
                                songsUtils.removePlaylistSong(Integer.parseInt(listOrigin)
                                        , aaa);
                            } else {
                                aaa = new ArrayList<>(songsUtils.favouriteSongs());
                                aaa.remove(finalPoss);
                                songsUtils.updateFavouritesList(aaa);
                            }
                            data_onlyPlass.remove(finalPoss);
                            data.remove(holder.getAdapterPosition());
                            notifyDataSetChanged();

                            if(freePlaylistFragment.playerproPlaylistGridAdapter !=null){
                                freePlaylistFragment.playerproPlaylistGridAdapter.notifyDataSetChanged();
                            }
                            return true;
                        case R.id.play_next_musicUtils:
                            songsUtils.playNext(data.get(holder.getAdapterPosition()));
                            return true;
                        case R.id.add_to_queue_musicUtils:
                            songsUtils.addToQueue(data.get(holder.getAdapterPosition()));
                            return true;
                        case R.id.add_to_playlist_musicUtils:
                            songsUtils.addToPlaylist(data.get(holder.getAdapterPosition()));
                            return true;
                        case R.id.shuffle_play_musicUtils:


                            int pos=0;
                            for (int i = 0; i < data_onlyPlass.size() ; i++) {
                                if(data_onlyPlass.get(i).getTitle().equals(data.get(holder.getAdapterPosition()).getTitle())){
                                    pos=i;
                                    break;
                                }
                            }
                            int finalPos = pos;
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    songsUtils.play(activity, finalPos, data_onlyPlass);
                                }
                            },400);

                            return true;
                        case R.id.goto_album_musicUtils:
                            Intent intent = new Intent(activity, free_GlobalDetailActivity.class);
                            intent.putExtra("name", finalTempValues.getAlbum());
                            intent.putExtra("field", "albums");
                            activity.startActivity(intent);
                            return true;
                        case R.id.goto_artist_musicUtils:
                            Intent intent1 = new Intent(activity, free_GlobalDetailActivity.class);
                            intent1.putExtra("name", finalTempValues.getArtist());
                            intent1.putExtra("field", "artists");
                            activity.startActivity(intent1);
                            return true;
                        default:
                            return false;
                    }
                }
            });

            holder.imageOverflow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pop.show();
                }
            });

            holder.itemView.setOnClickListener(new SingleClickListener() {
                @Override
                public void performClick(View v) {

                    int pos=0;
                    for (int i = 0; i < data_onlyPlass.size() ; i++) {
                        if(data_onlyPlass.get(i).getTitle().equals(data.get(holder.getAdapterPosition()).getTitle())){
                            pos=i;
                            break;
                        }
                    }
                    int finalPos = pos;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            songsUtils.play(activity, finalPos, data_onlyPlass);
                        }
                    },400);

                    if(free_GlobalDetailActivity.fragment!=null){
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if(freeMusicDockFragment.strtital==null || freeMusicDockFragment.strtital.equalsIgnoreCase("") ){
                                    free_GlobalDetailActivity.fragment.setVisibility(View.GONE);
                                }else {
                                    free_GlobalDetailActivity.fragment.setVisibility(View.VISIBLE);
                                }
                            }
                        },400);
                    }

                }
            });
        }else if(getItemViewType(position) == LIST_ITEM_ADS){
            ADViewHolder holder = (ADViewHolder) holder0;
            AllNativeAds.NativeBanner((Activity) activity, holder.adsContainer);
        }


    }

    private boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    @Override
    public int getItemViewType(int position) {

            if (data.get(position) == null) {

                return LIST_ITEM_ADS;
            }
            return LIST_ITEM;
    }
}