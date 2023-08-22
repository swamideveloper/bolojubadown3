package com.free.hdvideodownloaderapp.videodownloader.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.viewpager.widget.ViewPager;

import com.free.hdvideodownloaderapp.videodownloader.Adapter.free_MainTabPagerAdapter;
import com.free.hdvideodownloaderapp.videodownloader.Def.LockableViewPager;
import com.free.hdvideodownloaderapp.videodownloader.Log;
import com.free.hdvideodownloaderapp.videodownloader.Utils.CommonUtils;
import com.free.hdvideodownloaderapp.videodownloader.Utils.Playlist;
import com.free.hdvideodownloaderapp.videodownloader.Utils.SharedPrefsUtils;
import com.free.hdvideodownloaderapp.videodownloader.Utils.SongsUtils;
import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.VideoFragment;
import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.SongModel;
import com.free.hdvideodownloaderapp.videodownloader.Fragment.free_Setting_Fragment;
import com.free.hdvideodownloaderapp.videodownloader.MusicPlayer.Fragment.freeMainFragmentMusic;
import com.free.hdvideodownloaderapp.videodownloader.R;
import com.sdk.ads.ads.IntertitialAdsEvent;


import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public class free_Fragment_Tab_Activity extends AppCompatActivity {

    LockableViewPager mainGbdashboardContainer;
    public static String string;
    public ImageView bg_img;
    public static free_Fragment_Tab_Activity tab_activity;
    Boolean sync = false;

    @Override
    public void onBackPressed() {
        IntertitialAdsEvent.ShowInterstitialAdsOnBack(this);
    }

    @Override
    protected void onResume() {
        tab_activity = this;

        setupViewPager(mainGbdashboardContainer);

        if (string == null || string.equals(null) || string.equals("")) {
            string = "V";
        }

        if (string.equalsIgnoreCase("V")) {
            mainGbdashboardContainer.setCurrentItem(0);
            progressbar.setVisibility(View.GONE);
        } else if (string.equalsIgnoreCase("M")) {
            mainGbdashboardContainer.setCurrentItem(1);
            progressbar.setVisibility(View.GONE);
        } else if (string.equalsIgnoreCase("S")) {
            mainGbdashboardContainer.setCurrentItem(2);
            progressbar.setVisibility(View.GONE);
        }
        super.onResume();
    }

    ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_tab);

        
        if (getIntent().getBooleanExtra("show", false)) {
            IntertitialAdsEvent.CallInterstitial(this);
IntertitialAdsEvent.ShowInterstitialAdsOnCreate(this);
        }

        progressbar = findViewById(R.id.progressbar);
        mainGbdashboardContainer = findViewById(R.id.mainGbdashboardContainer);
        mainGbdashboardContainer.setSwipeLocked(true);

        mainGbdashboardContainer.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @SuppressLint("UseCompatLoadingForDrawables")
            public void onPageSelected(int position) {
            }
        });

        mainGbdashboardContainer.setOffscreenPageLimit(3);

        try {
            string = getIntent().getStringExtra("type");
        } catch (Exception e) {
            e.printStackTrace();
            string = "V";
        }

        if (string == null || string.equals(null) || string.equals("")) {
            string = "V";
        }

        if (string.equalsIgnoreCase("V")) {
            mainGbdashboardContainer.setCurrentItem(0);
        } else if (string.equalsIgnoreCase("M")) {
            mainGbdashboardContainer.setCurrentItem(1);
        } else if (string.equalsIgnoreCase("S")) {
            mainGbdashboardContainer.setCurrentItem(2);
        }

        AllData();

    }

    void setupViewPager(ViewPager viewPager) {
        free_MainTabPagerAdapter adapter = new free_MainTabPagerAdapter(getSupportFragmentManager());
        VideoFragment f1 = new VideoFragment();
        freeMainFragmentMusic f2 = new freeMainFragmentMusic();
        free_Setting_Fragment f5 = new free_Setting_Fragment();
        adapter.addFragment(f1);
        adapter.addFragment(f2);
        adapter.addFragment(f5);
        viewPager.setAdapter(adapter);
    }

    private void AllData() {
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        if ((getIntent().getBooleanExtra("sync", false))) {
            SongsUtils songsUtils = new SongsUtils(this);
            songsUtils.sync();
            sync = true;
        } else {
            sync = false;
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PermissionChecker.PERMISSION_GRANTED) {
            new PerformBackgroundTasks(this, sync).execute("task");
        } else {
            (new CommonUtils(this)).showTheToast("Please enable permission from " +
                    "Settings > Apps > Noad Player > Permissions.");
        }
    }

    private static class PerformBackgroundTasks extends AsyncTask<String, Integer, Long> {
        private WeakReference<Activity> weakReference;
        private Boolean sync;
        private String TAG = "SplashActivityAsyncTaskLog";
        private SongsUtils songsUtils;
        private SharedPrefsUtils sharedPrefsUtils;
        private Playlist playlist;

        PerformBackgroundTasks(Activity activity, Boolean sync) {
            this.weakReference = new WeakReference<>(activity);
            this.sync = sync;
            this.songsUtils = new SongsUtils(activity);
            this.sharedPrefsUtils = new SharedPrefsUtils(activity);
            this.playlist = new Playlist(activity);
        }

        @Override
        protected Long doInBackground(String... params) {

            ArrayList<HashMap<String, String>> artists = songsUtils.artists();
            if (artists.size() > 0) {
                sharedPrefsUtils.writeSharedPrefs("home_artist",
                        artists.get((new Random()).nextInt(artists.size())).get("artist"));
            }

            try {
                playlist.open();
                if (playlist.getCount() == 0) {
                    songsUtils.addPlaylist("Playlist 1");
                }
                playlist.close();

                if (sync) {
                    for (int s = 0; s < songsUtils.getAllPlaylists().size(); s++) {
                        int playlistID = Integer.parseInt(Objects.requireNonNull(songsUtils.getAllPlaylists().get(s).get("ID")));
                        ArrayList<SongModel> playListSongs = songsUtils.playlistSongs(playlistID);
                        if (!playListSongs.isEmpty()) {
                            for (int j = 0; j < playListSongs.size(); j++) {
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

                    }

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
                        }
                        Log.e(TAG, "Favourites: done!");
                        songsUtils.updateFavouritesList(favSongs);
                    }

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
                        }
                        Log.e(TAG, "MostPlayed: done!");
                        songsUtils.updateMostPlayedList(mostPlayed);
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "Unable to perform sync");
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
        }

        @Override
        protected void onPostExecute(Long aLong) {
            weakReference.get();
        }
    }
}