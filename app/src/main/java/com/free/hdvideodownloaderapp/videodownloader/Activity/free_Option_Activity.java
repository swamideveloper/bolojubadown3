package com.free.hdvideodownloaderapp.videodownloader.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.SongModel;
import com.free.hdvideodownloaderapp.videodownloader.Log;
import com.free.hdvideodownloaderapp.videodownloader.R;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.freeActivity.freeMainActivity;
import com.free.hdvideodownloaderapp.videodownloader.Utils.CommonUtils;
import com.free.hdvideodownloaderapp.videodownloader.Utils.Playlist;
import com.free.hdvideodownloaderapp.videodownloader.Utils.SharedPrefsUtils;
import com.free.hdvideodownloaderapp.videodownloader.Utils.SongsUtils;
import com.free.hdvideodownloaderapp.videodownloader.databinding.ExitDialoggBinding;
import com.sdk.ads.Comman;
import com.sdk.ads.ads.MoreAppUtils;

import com.sdk.ads.ads.IntertitialAdsEvent;
import com.sdk.ads.ads.AllNativeAds;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public class free_Option_Activity extends AppCompatActivity {

//    public Boolean isPermissionGranted() {
//        return Boolean.valueOf(ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0);
//    }


    private int Storage_Permission_code = 1;
    Boolean sync = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);


         AllNativeAds.NativeAds(this, findViewById(R.id.adsContainer));


//        try {
//            if (getIntent().getBooleanExtra("app_inter", false)) {
//
//                if (ContextCompat.checkSelfPermission(free_Option_Activity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//
//                } else {
//
//                    try {
//                        try {
//                            if (ContextCompat.checkSelfPermission(free_Option_Activity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
//                                requestStoragePermission();
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        findViewById(R.id.music).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(free_Option_Activity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED) {
                    requestStoragePermission();
                } else {
//                    IntertitialAdsEvent.ShowInterstitialAdsOnCreateWithCallBack(playerpro_Option_Activity.this, new InterCloseCallBack() {
//                        @Override
//                        public void onAdsClose() {
                    startActivity(new Intent(free_Option_Activity.this, free_Fragment_Tab_Activity.class).putExtra("type", "M").putExtra("show", true));
//                        }playerpro_Fragment_Tab_Activity
//                    });
                }
            }
        });


        findViewById(R.id.video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(free_Option_Activity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED) {
                    requestStoragePermission();
                } else {
//                    IntertitialAdsEvent.ShowInterstitialAdsOnCreateWithCallBack(playerpro_Option_Activity.this, new InterCloseCallBack() {
//                        @Override
//                        public void onAdsClose() {
                    startActivity(new Intent(free_Option_Activity.this, free_Fragment_Tab_Activity.class).putExtra("type", "V").putExtra("show", true));
//                        }
//                    });
                }
            }
        });

        findViewById(R.id.seting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(free_Option_Activity.this, free_Fragment_Tab_Activity.class).putExtra("type", "S").putExtra("show", true));
            }
        });

        findViewById(R.id.download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(free_Option_Activity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED) {
                    requestStoragePermission();
                } else {
//                    startActivity(new Intent(playerpro_Option_Activity.this, JK_MainActivity.class).putExtra("type", "S"));
                    startActivity(new Intent(free_Option_Activity.this, freeMainActivity.class).putExtra("type", "D").putExtra("show", true));
                }
            }
        });

        AllData();
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
                Manifest.permission.READ_EXTERNAL_STORAGE) != PermissionChecker.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_MEDIA_LOCATION) != PermissionChecker.PERMISSION_GRANTED) {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Request for permissions");
            alertDialog.setMessage("For music player to work we need your permission to access" + " files on your device.");
            alertDialog.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.Q)
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions(free_Option_Activity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.ACCESS_MEDIA_LOCATION},
                            1);
                }
            });
            alertDialog.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    onBackPressed();
                }
            });
            alertDialog.show();

            Log.e("TAG", "asking permission");
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PermissionChecker.PERMISSION_GRANTED) {
            if (getIntent().getBooleanExtra("app_inter", false)) {
                IntertitialAdsEvent.CallInterstitial(this);
IntertitialAdsEvent.ShowInterstitialAdsOnCreate(this);
            }
            new PerformBackgroundTasks(this, sync).execute("task");
        } else {
            (new CommonUtils(this)).showTheToast("Please enable permission from " +
                    "Settings > Apps > Noad Player > Permissions.");
        }
    }

    //back------------------------------------------------------------------------------------------
    boolean doubleBackToExitPressedOnce = false;


    @Override
    public void onBackPressed() {
        if (Comman.mainResModel.getData().getExtraFields().getExtraNewScreen().equalsIgnoreCase("off")) {
            MoreAppUtils.exitDialog(this);
        } else {
            final Dialog dialog = new Dialog(this);
            ExitDialoggBinding binding1 = ExitDialoggBinding.inflate(((Activity) this).getLayoutInflater());
            dialog.setContentView(binding1.getRoot());
            dialog.setCancelable(true);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            AllNativeAds.NativeAdsNew(this, binding1.adsContainer1);
            binding1.done.setOnClickListener(v -> {
                finishAffinity();

            });

            binding1.cancel.setOnClickListener(view -> dialog.dismiss());
            dialog.show();
        }}



    private static class PerformBackgroundTasks extends AsyncTask<String, Integer, Long> {
        WeakReference<Activity> weakReference;
        Boolean sync;
        String TAG = "SplashActivityAsyncTaskLog";
        SongsUtils songsUtils;
        SharedPrefsUtils sharedPrefsUtils;
        Playlist playlist;

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
                        ArrayList<SongModel> playListSongs =
                                songsUtils.playlistSongs(playlistID);
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


    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this).setTitle("Permission Needed").setMessage("Need to read songs from your storage").setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions(free_Option_Activity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Storage_Permission_code);
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Storage_Permission_code);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Storage_Permission_code) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
            }
        }
    }
}