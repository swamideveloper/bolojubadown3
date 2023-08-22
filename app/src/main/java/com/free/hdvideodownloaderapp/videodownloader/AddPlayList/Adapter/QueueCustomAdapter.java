package com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Adapter;


import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Handler;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.free.hdvideodownloaderapp.videodownloader.Activity.free_Music_PlayActivity;
import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.SongModel;
import com.free.hdvideodownloaderapp.videodownloader.Utils.ImageUtils;
import com.free.hdvideodownloaderapp.videodownloader.Utils.ItemViewHolder;
import com.free.hdvideodownloaderapp.videodownloader.Utils.SharedPrefsUtils;
import com.free.hdvideodownloaderapp.videodownloader.Utils.SingleClickListener;
import com.free.hdvideodownloaderapp.videodownloader.Utils.SongsUtils;
import com.free.hdvideodownloaderapp.videodownloader.R;

import java.util.ArrayList;
import java.util.Collections;

public class QueueCustomAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    private Activity activity;
    public Resources res;
    SongsUtils songsUtils;
    private SharedPrefsUtils sharedPrefsUtils;
    private ArrayList<SongModel> arrayList = new ArrayList<>();

    public void notifyAdapterDataSetChanged() {
        arrayList.clear();
        arrayList.addAll(songsUtils.queue());
        notifyItemRangeChanged(0,arrayList.size());
    }

    @SuppressWarnings("rawtypes")
    public QueueCustomAdapter(Activity a, Resources resLocal) {

        activity = a;
        res = resLocal;
        songsUtils = new SongsUtils(a);
        sharedPrefsUtils = new SharedPrefsUtils(activity);

        arrayList.addAll(songsUtils.queue());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_all_song, parent, false);
        return new ItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {

        SongModel tempValues = getItem(position);
        final String duration, artist, songName, title, finalTitle;
        String finalTitle1;
        duration = tempValues.getDuration();
        artist = tempValues.getArtist();
        songName = tempValues.getFileName();
        title = tempValues.getTitle();

        finalTitle1 = songName;
        if (title != null) {
            finalTitle1 = title;
        }
        finalTitle = finalTitle1;

        (new ImageUtils(activity)).smallsetAlbumArt(tempValues.getAlbumID(), holder.image);

//        holder.image.setOutlineProvider(new ViewOutlineProvider() {
//            @Override
//            public void getOutline(View view, Outline outline) {
//                outline.setRoundRect(0, 0, view.getWidth(), Math.round(view.getHeight()), 20F);
//            }
//        });
//        holder.image.setClipToOutline(true);
//
//        if (position % 4 == 0) {
//            holder.liner.setBackgroundTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.color1)));
//            holder.image.setImageDrawable(activity.getResources().getDrawable(R.drawable.music1));
//        } else if (position % 4 == 1) {
//            holder.liner.setBackgroundTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.color2)));
//            holder.image.setImageDrawable(activity.getResources().getDrawable(R.drawable.music2));
//        } else if (position % 4 == 2) {
//            holder.liner.setBackgroundTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.color3)));
//            holder.image.setImageDrawable(activity.getResources().getDrawable(R.drawable.music3));
//        } else if (position % 4 == 3) {
//            holder.liner.setBackgroundTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.color4)));
//            holder.image.setImageDrawable(activity.getResources().getDrawable(R.drawable.music4));
//        }
        holder.text.setText(finalTitle);
        holder.text1.setText(artist);

//        holder.duration.setText(duration);

        if (position == sharedPrefsUtils.readSharedPrefsInt("musicID",0)) {
            holder.text.setTextColor(ContextCompat.getColor(activity,R.color.colorAccent));
        } else {
            holder.text.setTextColor(ContextCompat.getColor(activity,R.color.black));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                free_Music_PlayActivity sct = (free_Music_PlayActivity) free_Music_PlayActivity.PlayActivitycontext;
                sct.onItemClick(holder.getAdapterPosition());
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                        activity.finish();
                    }
                }, 300);
            }
        });

        final PopupMenu pop = new PopupMenu(activity, holder.albumArtImageView);
        int[] j = new int[1];
        j[0] = R.id.remove_musicUtils;
        int i = 0;
        while (i < j.length) {
            String name = "";
            if (j[i] == R.id.remove_musicUtils) {
                name = "Remove";
            }
            pop.getMenu().add(0, j[i], 1, name);
            MenuItem menuItem = pop.getMenu().getItem(i);
            CharSequence menuTitle = menuItem.getTitle();
            SpannableString styledMenuTitle = new SpannableString(menuTitle);
            styledMenuTitle.setSpan(new ForegroundColorSpan(Color.parseColor("#333333")), 0, menuTitle.length(), 0);
            menuItem.setTitle(styledMenuTitle);
            i++;
        }

        pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.remove_musicUtils:
                        onItemDismiss(holder.getAdapterPosition());
                        return true;
                    default:
                        return false;
                }
            }
        });

        holder.albumArtImageView.setOnClickListener(new SingleClickListener() {
            @Override
            public void performClick(View v) {
                pop.show();
            }
        });

    }

    private SongModel getItem(int position) {
        return arrayList.get(position);
    }

    public void onItemMove(int i, int i1) {
        int musicID = sharedPrefsUtils.readSharedPrefsInt("musicID",0);
        if (musicID == i && sharedPrefsUtils.readSharedPrefsString("raw_path",null).equals(arrayList.get(i).getPath())) {
            sharedPrefsUtils.writeSharedPrefs("musicID",i1);
        } else if (musicID >= i1 && musicID < i) {
            sharedPrefsUtils.writeSharedPrefs("musicID",musicID + 1);
        } else if (musicID <= i1 && musicID > i) {
            sharedPrefsUtils.writeSharedPrefs("musicID",musicID - 1);
        }

        Collections.swap(arrayList,i,i1);
        notifyItemMoved(i,i1);

        songsUtils.replaceQueue(arrayList);
        callback.viewPagerRefresh();
    }

    public void onItemDismiss(int adapterPosition) {
        int musicID = sharedPrefsUtils.readSharedPrefsInt("musicID",0);
        if (sharedPrefsUtils.readSharedPrefsInt("musicID",0) == adapterPosition &&
                sharedPrefsUtils.readSharedPrefsString("raw_path",null).equals(arrayList.get(adapterPosition).getPath())) {
            Toast.makeText(activity,"Cannot remove now playing song.",Toast.LENGTH_SHORT).show();
            notifyDataSetChanged();
        } else {
            arrayList.remove(adapterPosition);
            songsUtils.replaceQueue(arrayList);
            notifyItemRemoved(adapterPosition);
            if (adapterPosition < sharedPrefsUtils.readSharedPrefsInt("musicID",0)) {
                sharedPrefsUtils.writeSharedPrefs("musicID", musicID - 1);
            }
            callback.viewPagerRefresh();
        }
    }


    public interface MyFragmentCallback {
        void viewPagerRefresh();
    }

    private MyFragmentCallback callback;

    public void setMyFragmentCallback(MyFragmentCallback mCallback) {
        this.callback = mCallback;
    }

}



