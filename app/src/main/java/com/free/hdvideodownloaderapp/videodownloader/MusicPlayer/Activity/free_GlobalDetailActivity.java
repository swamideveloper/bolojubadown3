package com.free.hdvideodownloaderapp.videodownloader.MusicPlayer.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;

import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.HistoryUtility;
import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.SongModel;
import com.free.hdvideodownloaderapp.videodownloader.Log;
import com.free.hdvideodownloaderapp.videodownloader.MusicPlayer.Adapter.freeRecyclerViewAdapter;
import com.free.hdvideodownloaderapp.videodownloader.Utils.AsyncTaskCompletionCallback;
import com.free.hdvideodownloaderapp.videodownloader.Utils.CommonUtils;
import com.free.hdvideodownloaderapp.videodownloader.Utils.SharedPrefsUtils;
import com.free.hdvideodownloaderapp.videodownloader.Utils.SingleClickListener;
import com.free.hdvideodownloaderapp.videodownloader.Utils.SongsUtils;
import com.free.hdvideodownloaderapp.videodownloader.MusicPlayer.Fragment.freeMusicDockFragment;
import com.free.hdvideodownloaderapp.videodownloader.R;
import com.sdk.ads.ads.IntertitialAdsEvent;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

public class free_GlobalDetailActivity extends AppCompatActivity implements AsyncTaskCompletionCallback {

    freeRecyclerViewAdapter adapter;
    ArrayList<SongModel> songsListdemo = new ArrayList<>();
    ArrayList<SongModel> songsList = new ArrayList<>();
    String field = "albums", raw = "A Sky Full Of Stars";
    PerformBackgroundTasks performBackgroundTasks = null;
    SongsUtils songsUtils;
    LinearLayout txt_not;
    RelativeLayout rl;
    public ImageView bg_img;
    public static View fragment;
    ImageView iv_delete;

    @Override
    public void onBackPressed() {
        IntertitialAdsEvent.ShowInterstitialAdsOnBack(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_detail);

        
        IntertitialAdsEvent.CallInterstitial(this);
IntertitialAdsEvent.ShowInterstitialAdsOnCreate(this);

        field = Objects.requireNonNull(getIntent().getExtras()).getString("field");
        raw = Objects.requireNonNull(getIntent().getExtras()).getString("name");

        performBackgroundTasks = new PerformBackgroundTasks(this, this, field);
        findViewById(R.id.spinner).setVisibility(View.INVISIBLE);
        ((TextView) findViewById(R.id.title)).setText(raw);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        rl = findViewById(R.id.rl);
        iv_delete = findViewById(R.id.iv_delete);
        fragment = findViewById(R.id.fragment);


        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        songsUtils = new SongsUtils(this);
        setListData();

        int playbackTime = getPlayBackTime(songsList);
        String netPlayback = playbackTime + " mins";
        if (playbackTime > 60) {
            netPlayback = playbackTime / 60 + "h " + playbackTime % 60 + "m";
        }
        int numSongs = songsList.size();
        if (numSongs > 0) {
            ((TextView) findViewById(R.id.listInfoTextView))
                    .setText(numSongs + ((songsList.size() > 1) ? " tracks, " : " track, ") +
                            netPlayback + " playback");
        }

        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                songsUtils.play(free_GlobalDetailActivity.this, 0, songsList);
            }
        });

        findViewById(R.id.shuffle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                songsUtils.shufflePlay(free_GlobalDetailActivity.this, songsList);
            }
        });


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        txt_not = findViewById(R.id.txt_not);
        RecyclerView.LayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new freeRecyclerViewAdapter(songsList, this, field, songsListdemo);
        recyclerView.setAdapter(adapter);

        if (songsList.size() == 0) {
            txt_not.setVisibility(View.VISIBLE);
        } else {
            txt_not.setVisibility(View.GONE);
        }

        fragment.setVisibility(View.GONE);
        Log.e("sdkjf", "==" + freeMusicDockFragment.strtital);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (freeMusicDockFragment.strtital == null || freeMusicDockFragment.strtital.equalsIgnoreCase("")) {
                    fragment.setVisibility(View.GONE);
                } else {
                    fragment.setVisibility(View.VISIBLE);
                }
            }
        }, 400);


        iv_delete.setOnClickListener(new SingleClickListener() {
            @Override
            public void performClick(View v) {
                final Dialog alertDialog = new Dialog(free_GlobalDetailActivity.this, R.style.MinWidth);
                alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                alertDialog.setContentView(R.layout.dialog_delete);

                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


                alertDialog.findViewById(R.id.btnDelete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        HistoryUtility.DeleteAllHistoryList(free_GlobalDetailActivity.this);
                        setListData();
                        if (songsList.size() == 0) {
                            txt_not.setVisibility(View.VISIBLE);
                            iv_delete.setVisibility(View.GONE);
                        } else {
                            txt_not.setVisibility(View.GONE);
                        }
                        adapter.notifyDataSetChanged();

                        alertDialog.cancel();
                    }
                });

                alertDialog.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });
                alertDialog.findViewById(R.id.cancle).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });
                alertDialog.show();
            }
        });
    }


    private int getPlayBackTime(ArrayList<SongModel> albumSongs) {
        int pTime = 0;
        for (int i = 0; i < albumSongs.size(); i++) {
            if (albumSongs.get(i) != null) {
                String duration = albumSongs.get(i).getDuration();
                pTime += Integer.parseInt(duration.split(":")[0]) * 60 +
                        Integer.parseInt(duration.split(":")[1]);
            }

        }
        return pTime / 60;
    }


    public void setListData() {
        songsList.clear();
        songsListdemo.clear();
        switch (field) {
            case "albums":
                songsListdemo.addAll(songsUtils.albumSongs(raw));
                ((TextView) findViewById(R.id.category)).setText("Album");
                LoopingData(songsListdemo);
                break;
            case "mostplayed":
                songsListdemo.addAll(songsUtils.mostPlayedSongs());
                ((TextView) findViewById(R.id.category)).setText("Most Played");
                LoopingData(songsListdemo);
                if (performBackgroundTasks.getStatus() != AsyncTask.Status.RUNNING)
                    performBackgroundTasks.execute();
                break;
            case "favourites":
                songsListdemo.addAll(songsUtils.favouriteSongs());
                ((TextView) findViewById(R.id.category)).setText("Favourites");
                LoopingData(songsListdemo);
                if (performBackgroundTasks.getStatus() != AsyncTask.Status.RUNNING)
                    performBackgroundTasks.execute();
                break;
            case "artists":
                songsListdemo.addAll(songsUtils.artistSongs(raw));
                ((TextView) findViewById(R.id.category)).setText("Artist");
                LoopingData(songsListdemo);
                break;
            case "recent":
                songsListdemo.addAll(songsUtils.newSongs());
                ((TextView) findViewById(R.id.category)).setText("Last Added");
                LoopingData(songsListdemo);
                break;
            case "history":
                String[] favorites = HistoryUtility.getHistoryList(this);
                Log.e("resumehistory", "History1----" + favorites.length);
                List<String> list = new ArrayList<String>(Arrays.asList(favorites));
                ArrayList<SongModel> songsListdemomy = new ArrayList<>();
                ArrayList<SongModel> songsListdemomy0000 = new ArrayList<>();
                songsListdemomy0000.addAll(songsUtils.HistorySongs(list));
                Log.e("resumehistory", "History2----" + songsListdemomy0000.size());
                for (int j = 0; j < list.size(); j++) {
                    for (int i = 0; i < songsListdemomy0000.size(); i++) {
                        if (list.get(j).equals(songsListdemomy0000.get(i).getAlbumID())) {
                            songsListdemomy.add(songsListdemomy0000.get(i));
                        }
                    }
                }
                Collections.reverse(songsListdemomy);
                Log.e("resumehistory", "History3----" + songsListdemomy.size());
                songsListdemo.addAll(songsListdemomy);
                ((TextView) findViewById(R.id.category)).setText("History");
                LoopingData(songsListdemo);

                if (songsListdemo.size() == 0) {
                    iv_delete.setVisibility(View.GONE);
                } else {
                    iv_delete.setVisibility(View.VISIBLE);
                }

                break;
            case "folder_list":
                songsListdemo.addAll(getAllAudioFromDevice());
                ((TextView) findViewById(R.id.category)).setText("Music Folder");
                LoopingData(songsListdemo);
                break;
            default:
                songsListdemo.addAll(songsUtils.playlistSongs(Integer.parseInt(field)));
                ((TextView) findViewById(R.id.category)).setText("Playlist");
                LoopingData(songsListdemo);
                if (performBackgroundTasks.getStatus() != AsyncTask.Status.RUNNING)
                    performBackgroundTasks.execute();
                break;
        }
    }

    private void LoopingData(ArrayList<SongModel> songsListdemo) {

        songsList.clear();

        int j = 0;
        for (int i = 0; i < songsListdemo.size(); i++) {
            if (i == 3) {
                try {
                    j = i;
                    songsList.add(null);
                    songsList.add(songsListdemo.get(i));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (i == (j + 8)) {
                try {
                    j = i;
                    songsList.add(null);
                    songsList.add(songsListdemo.get(i));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                songsList.add(songsListdemo.get(i));
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.global_activity, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {

            case R.id.play_next:
                if (songsList.size() > 0) {
                    songsUtils.playNext(songsList);
                    (new CommonUtils(this)).showTheToast("List added for playing next");
                } else {
                    (new CommonUtils(this)).showTheToast("Error adding empty song list to queue");
                }
                break;
            case R.id.add_to_queue:
                songsUtils.addToQueue(songsList);
                break;
            case android.R.id.home:
                if (performBackgroundTasks.getStatus() == AsyncTask.Status.RUNNING) {
                    performBackgroundTasks.cancel(true);
                }
                backPressed();
                break;
            case R.id.add_to_playlist:
                songsUtils.addToPlaylist(songsList);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            backPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    private void backPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (performBackgroundTasks.getStatus() == AsyncTask.Status.RUNNING) {
            performBackgroundTasks.cancel(true);
        }
    }

    @Override
    public void updateViews() {
        findViewById(R.id.spinner).setVisibility(View.INVISIBLE);
        findViewById(R.id.recyclerView).setActivated(true);
        setListData();
        adapter.notifyDataSetChanged();

        if (songsList.size() == 0) {
            txt_not.setVisibility(View.VISIBLE);
        } else {
            txt_not.setVisibility(View.GONE);
        }
    }

    public static class PerformBackgroundTasks extends AsyncTask<String, Integer, Long> {

        private SongsUtils songsUtils;
        private String field;
        private String TAG = "GlobalActivityAsyncTaskConsole";
        private AsyncTaskCompletionCallback callback;

        PerformBackgroundTasks(AsyncTaskCompletionCallback callback, Activity activity, String field) {
            this.songsUtils = new SongsUtils(activity);
            this.callback = callback;
            this.field = field;
        }

        @Override
        protected Long doInBackground(String... params) {

            if (isInteger(field)) {

                int playlistID = Integer.parseInt(field);
                Log.e("aaaaaaaa", "==" + field);
                Log.e("aaaaaaaa", "====" + playlistID);
                ArrayList<SongModel> playListSongs =
                        songsUtils.playlistSongs(playlistID);
                if (!playListSongs.isEmpty()) {
                    for (int j = 0; j < playListSongs.size(); j++) {
                        if (isCancelled()) break;
                        Log.e(TAG, "Playlist: Search if current song " + j + " is not similar with song in new songs list");
                        if (!songsUtils.allSongs().contains(playListSongs.get(j))) {
                            Log.e(TAG, "Playlist: current playlist song doesn't exist in allSongs," +
                                    " so lets see if only path is changed or user has moved the song");
                            boolean isFound = false;
                            for (int k = 0; k < songsUtils.allSongs().size(); k++) {
                                if ((songsUtils.allSongs().get(k).getTitle() +
                                        songsUtils.allSongs().get(k).getDuration())
                                        .equals(playListSongs.get(j).getTitle() +
                                                playListSongs.get(j).getDuration())) {
                                    Log.e(TAG, "Playlist: song " + j + " does exist and is probably moved," +
                                            " so lets change broken song with lasted");
                                    playListSongs.remove(j);
                                    playListSongs.add(j, songsUtils.allSongs().get(k));
                                    Log.e(TAG, "Playlist: index doesn't change and we changed broken song. All good!");
                                    isFound = true;
                                    k = songsUtils.allSongs().size();
                                }
                                if (isCancelled()) {
                                    break;
                                }
                            }
                            if (!isFound) {
                                Log.e(TAG, "Playlist: " + j + " song is deleted from device");
                                playListSongs.remove(j);
                                Log.e(TAG, "Playlist: since a song is removed," +
                                        " on doing next song loop will skip one song");
                                j--;
                                Log.e(TAG, "Playlist: j-- to ensure for loop stays on same song");
                            }
                        } else {
                            Log.e(TAG, "Playlist: Song " + j + " is okay");
                        }
                        if (isCancelled()) {
                            break;
                        }
                    }
                    songsUtils.updatePlaylistSongs(playlistID,
                            playListSongs);
                    Log.e(TAG, "Playlist: done!");
                }
            } else if (field.equals("favourites")) {

                ArrayList<SongModel> favSongs =
                        new ArrayList<>(songsUtils.favouriteSongs());
                if (!favSongs.isEmpty()) {
                    Log.e(TAG, "Favourites: Search if current hashMap is not similar with song in new songs list");
                    for (int j = 0; j < favSongs.size(); j++) {
                        if (!songsUtils.allSongs().contains(favSongs.get(j))) {
                            Log.e(TAG, "Favourites: current favourite doesn't exist in allSongs," +
                                    " so lets see if only path is changed or user has moved the song");
                            boolean isFound = false;
                            for (int i = 0; i < songsUtils.allSongs().size(); i++) {
                                if ((songsUtils.allSongs().get(i).getTitle() +
                                        songsUtils.allSongs().get(i).getDuration())
                                        .equals(favSongs.get(j).getTitle() +
                                                favSongs.get(j).getDuration())) {
                                    Log.e(TAG, "Favourites: songs does exist and is probably moved," +
                                            " so lets change broken song with lasted");
                                    favSongs.remove(j);
                                    favSongs.add(j, songsUtils.allSongs().get(i));
                                    Log.e(TAG, "Favourites: index doesn't change and we changed broken song. All good");
                                    isFound = true;
                                    i = songsUtils.allSongs().size();
                                }
                                if (isCancelled()) break;
                            }
                            if (!isFound) {
                                Log.e(TAG, "Favourites: songs is deleted from device");
                                favSongs.remove(j);
                                Log.e(TAG, "Favourites: since a song is removed," +
                                        " on doing next song loop will skip one song");
                                j--;
                                Log.e(TAG, "Favourites: j-- to ensure for loop stays on same song");
                            }
                        }
                        if (isCancelled()) {
                            break;
                        }
                    }
                    Log.e(TAG, "Favourites: done!");
                    songsUtils.updateFavouritesList(favSongs);
                }
            } else if (field.equals("mostplayed")) {

                ArrayList<SongModel> mostPlayed =
                        songsUtils.mostPlayedSongs();
                if (!mostPlayed.isEmpty()) {
                    Log.e(TAG, "MostPlayed: Search if current hashMap is not similar with song in new songs list");
                    for (int j = 0; j < mostPlayed.size(); j++) {
                        if (!songsUtils.allSongs().contains(mostPlayed.get(j))) {
                            Log.e(TAG, "MostPlayed: current song " + j + " doesn't exist in allSongs," +
                                    " so lets see if only path is changed or user has moved the song");
                            boolean isFound = false;
                            for (int i = 0; i < songsUtils.allSongs().size(); i++) {
                                if ((songsUtils.allSongs().get(i).getTitle() +
                                        songsUtils.allSongs().get(i).getDuration())
                                        .equals(mostPlayed.get(j).getTitle() +
                                                mostPlayed.get(j).getDuration())) {
                                    Log.e(TAG, "MostPlayed: songs does exist and is probably moved," +
                                            " so lets change broken song with lasted");
                                    mostPlayed.remove(j);
                                    mostPlayed.add(j, songsUtils.allSongs().get(i));
                                    Log.e(TAG, "MostPlayed: index doesn't change and we changed broken song. All good!");
                                    isFound = true;
                                    i = songsUtils.allSongs().size();
                                    if (isCancelled()) break;
                                }
                            }
                            if (!isFound) {
                                Log.e(TAG, "MostPlayed: songs is deleted from device");
                                mostPlayed.remove(j);
                                Log.e(TAG, "MostPlayed: since a song is removed," +
                                        " on doing next song loop will skip one song");
                                j--;
                                Log.e(TAG, "MostPlayed: j-- to ensure for loop stays on same song");
                            }
                        }
                        if (isCancelled()) {
                            break;
                        }
                    }
                    Log.e(TAG, "MostPlayed: done!");
                    songsUtils.updateMostPlayedList(mostPlayed);
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
        }

        @Override
        protected void onPostExecute(Long aLong) {
            callback.updateViews();
            Log.e(TAG, "AsyncTask Done!");
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

    }


    public ArrayList<SongModel> getAllAudioFromDevice() {


        String bucketId = getIntent().getStringExtra("Sub_Bucket_Video_Id");
        final ArrayList<SongModel> songs = new ArrayList<>();

        SharedPrefsUtils sharedPrefsUtils;
        sharedPrefsUtils = new SharedPrefsUtils(this);

        boolean excludeShortSounds = sharedPrefsUtils.readSharedPrefsBoolean("excludeShortSounds", false);
        boolean excludeWhatsApp = sharedPrefsUtils.readSharedPrefsBoolean("excludeWhatsAppSounds", false);

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        String[] projection = new String[]{
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ALBUM_ID
        };


        Cursor c = getContentResolver().query(uri, projection, "bucket_id=?", new String[]{bucketId}, null);


        if (c != null) {

            int songNameColumn = c.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
            int durationColumn = c.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION);
            int pathColumn = c.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
            int titleColumn = c.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE);
            int artistColumn = c.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST);
            int albumColumn = c.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM);
            int albumIDColumn = c.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID);

            Log.e("count", c.getCount() + "");
            c.moveToFirst();
            do {
                int currentDuration = Math.round((c.getInt(durationColumn)));
                if (currentDuration > ((excludeShortSounds) ? 60000 : 0)) {
                    if (!excludeWhatsApp || !c.getString(albumColumn).equals("WhatsApp Audio")) {
                        TimeZone tz = TimeZone.getTimeZone("UTC");
                        SimpleDateFormat df = new SimpleDateFormat("mm:ss", Locale.getDefault());
                        df.setTimeZone(tz);
                        String time = String.valueOf(df.format(currentDuration));

                        SongModel playerproSongModel = new SongModel();
                        playerproSongModel.setFileName(c.getString(songNameColumn));
                        playerproSongModel.setTitle(c.getString(titleColumn));
                        playerproSongModel.setArtist(c.getString(artistColumn));
                        playerproSongModel.setAlbum(c.getString(albumColumn));
                        playerproSongModel.setAlbumID(c.getString(albumIDColumn));
                        playerproSongModel.setPath(c.getString(pathColumn));
                        playerproSongModel.setDuration(time);
                        songs.add(playerproSongModel);
                    }
                }

            } while (c.moveToNext());

            c.close();
        }
        return songs;
    }


}