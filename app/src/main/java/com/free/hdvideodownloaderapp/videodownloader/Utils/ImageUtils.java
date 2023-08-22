package com.free.hdvideodownloaderapp.videodownloader.Utils;

import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;


import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.SongModel;
import com.free.hdvideodownloaderapp.videodownloader.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ImageUtils {

    private Context context;
    public ImageUtils(Context context) {
        this.context = context;
    }

    public void setAlbumArt(String albumId, ImageView imageView) {
        try {
            Picasso.get().load(getSongUri(Long.parseLong(albumId)))
                    .placeholder(Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.empty_music)))
                    .centerCrop()
                    .resize(400,400)
                    .onlyScaleDown()
                    .into(imageView);}
        catch (Exception ignored) {}
    } public void smallsetAlbumArt(String albumId, ImageView imageView) {
        try {
            Picasso.get().load(getSongUri(Long.parseLong(albumId)))
                    .placeholder(Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.empty_music)))
                    .centerCrop()
                    .resize(400,400)
                    .onlyScaleDown()
                    .into(imageView);}
        catch (Exception ignored) {}
    }

    public void setAlbumArt(ArrayList<SongModel> arrayList, ImageView imageView) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            list.add(arrayList.get(i).getAlbumID());
            if (i == 20) {break; }
        }
        setAlbumArt(list, imageView, 0, list.size() - 1);
    }
 public void smallsetAlbumArt(ArrayList<SongModel> arrayList, ImageView imageView) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            list.add(arrayList.get(i).getAlbumID());
            if (i == 20) {break; }
        }
        smallsetAlbumArt(list, imageView, 0, list.size() - 1);
    }

    public void getFullImageByPicasso(String albumID, ImageView imageView) {
        try {
            Picasso.get().load(getSongUri(Long.parseLong(albumID)))
                    .placeholder(Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.vec)))
                    .into(imageView);}
        catch (Exception ignored) {}
    }

    private void smallsetAlbumArt(final List albumSongs, final ImageView imageView, final int i, final int max) {
        try {
            if (i < max) Picasso.get().load(getSongUri(Long.parseLong(albumSongs.get(i).toString())))
                    .placeholder(Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.p)))
                    .centerCrop()
                    .resize(400,400)
                    .onlyScaleDown()
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {

                        }
                        @Override
                        public void onError(Exception e) {
                            setAlbumArtsmall(albumSongs, imageView, i + 1, max);
                        }
                    });
            else if (i == max) {
                Picasso.get().load(getSongUri(Long.parseLong(albumSongs.get(i).toString())))
                        .placeholder(Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.p))).into(imageView);
            }}
        catch (Exception ignored) {}
    }


    private void setAlbumArt(final List albumSongs, final ImageView imageView, final int i, final int max) {
        try {
            if (i < max) Picasso.get().load(getSongUri(Long.parseLong(albumSongs.get(i).toString())))
                    .placeholder(Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.playlist_)))
                    .centerCrop()
                    .resize(400,400)
                    .onlyScaleDown()
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                        }
                        @Override
                        public void onError(Exception e) {
                            setAlbumArt(albumSongs, imageView, i + 1, max);
                        }
                    });
            else if (i == max) {
                Picasso.get().load(getSongUri(Long.parseLong(albumSongs.get(i).toString())))
                        .placeholder(Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.playlist_))).into(imageView);
            }}
        catch (Exception ignored) {}
    }

    private void setAlbumArtsmall(final List albumSongs, final ImageView imageView, final int i, final int max) {
        try {
            if (i < max) Picasso.get().load(getSongUri(Long.parseLong(albumSongs.get(i).toString())))
                    .placeholder(Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.p)))
                    .centerCrop()
                    .resize(400,400)
                    .onlyScaleDown()
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError(Exception e) {
                            setAlbumArtsmall(albumSongs, imageView, i + 1, max);
                        }
                    });
            else if (i == max) {
                Picasso.get().load(getSongUri(Long.parseLong(albumSongs.get(i).toString())))
                        .placeholder(Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.p))).into(imageView);
            }}
        catch (Exception ignored) {}
    }

    public static Uri getSongUri(Long albumID) {
        return ContentUris.withAppendedId(Uri
                .parse("content://media/external/audio/albumart"), albumID);
    }
}
