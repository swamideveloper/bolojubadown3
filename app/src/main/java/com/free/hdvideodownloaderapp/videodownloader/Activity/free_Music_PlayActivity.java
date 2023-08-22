package com.free.hdvideodownloaderapp.videodownloader.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.free.hdvideodownloaderapp.videodownloader.Fragment.free_ImageDetailFragment;
import com.free.hdvideodownloaderapp.videodownloader.Log;
import com.free.hdvideodownloaderapp.videodownloader.Utils.CommonUtils;
import com.free.hdvideodownloaderapp.videodownloader.Utils.FavouriteList;
import com.free.hdvideodownloaderapp.videodownloader.Utils.PlaylistSongs;
import com.free.hdvideodownloaderapp.videodownloader.Utils.SharedPrefsUtils;
import com.free.hdvideodownloaderapp.videodownloader.Utils.SingleClickListener;
import com.free.hdvideodownloaderapp.videodownloader.Utils.SongsUtils;
import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.SongModel;
import com.free.hdvideodownloaderapp.videodownloader.Fragment.free_QueueFragment;
import com.free.hdvideodownloaderapp.videodownloader.MusicPlayer.Activity.free_ListActivity;
import com.free.hdvideodownloaderapp.videodownloader.R;
import com.free.hdvideodownloaderapp.videodownloader.Services.MusicPlayback;


import com.sdk.ads.ads.IntertitialAdsEvent;
import com.sdk.ads.ads.AllNativeAds;
import com.xgc1986.parallaxPagerTransformer.ParallaxPagerTransformer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class free_Music_PlayActivity extends AppCompatActivity implements OnClickListener, free_QueueFragment.MyFragmentCallbackOne {

    ImageView btnPlay;
    ImageView btnNext;
    ImageView btnPrev, imgFav, ivBack;
    TextView title, album;
    ImageView btnRepeat, viewQueue;
    ViewPager albumArtViewpager;
    SeekBar seek_bar;
    View queueFragment;
    boolean isFavourite;
    SongsUtils songsUtils;
    ImagePagerAdapter albumArtAdapter;
    String TAG = "PlayActivityConsole";
    MediaBrowserCompat mMediaBrowser;
    PlaybackStateCompat mLastPlaybackState;
    static TextView curTime, totTime;
    RelativeLayout lowerOne;
    ImageView shuffleImageView;
    public static Context PlayActivitycontext;
    ImageView img;
    private static free_Music_PlayActivity instance;

    private final Runnable mUpdateProgressTask = new Runnable() {
        @Override
        public void run() {
            updateProgress();
        }
    };

    private final ScheduledExecutorService mExecutorService = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> mScheduleFuture;
    private final Handler mHandler = new Handler();
    int accentColor;
    SharedPrefsUtils sharedPrefsUtils;
    Animation animation1, animation2, animation3, animation4, animation5, animation6, animation7;

    public static free_Music_PlayActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        PlayActivitycontext = this;

        sharedPrefsUtils = new SharedPrefsUtils(this);
        songsUtils = new SongsUtils(this);
        animation1 = AnimationUtils.loadAnimation(this, R.anim.bounce);
        animation2 = AnimationUtils.loadAnimation(this, R.anim.bounce);
        animation3 = AnimationUtils.loadAnimation(this, R.anim.bounce);
        animation4 = AnimationUtils.loadAnimation(this, R.anim.bounce);
        animation5 = AnimationUtils.loadAnimation(this, R.anim.bounce);
        animation6 = AnimationUtils.loadAnimation(this, R.anim.bounce);
        animation7 = AnimationUtils.loadAnimation(this, R.anim.bounce);

        mMediaBrowser = new MediaBrowserCompat(this, new ComponentName(this, MusicPlayback.class), mConnectionCallback, null);
        shuffleImageView = findViewById(R.id.shuffleImageView);
        title = findViewById(R.id.title);
        album = findViewById(R.id.album);
        ivBack = findViewById(R.id.ivBack);
        btnPlay = findViewById(R.id.play);
        btnNext = findViewById(R.id.next);
        btnPrev = findViewById(R.id.prev);
        albumArtViewpager = findViewById(R.id.albumArt);
        seek_bar = findViewById(R.id.seekBar1);
        btnRepeat = findViewById(R.id.repeat);
        imgFav = findViewById(R.id.imageFav);
        queueFragment = findViewById(R.id.fragment);
        viewQueue = findViewById(R.id.viewQueue);
        lowerOne = findViewById(R.id.lowerOne);
        totTime = findViewById(R.id.total_time);
        curTime = findViewById(R.id.current_time);
        lowerOne.setVisibility(View.VISIBLE);
        title.setSelected(true);
        accentColor = R.color.colorAccent;

        FrameLayout adsContainer = findViewById(R.id.adsContainer);
        AllNativeAds.NativeBanner(this, findViewById(R.id.adsContainer));


        ivBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
               onBackPressed();
            }
        });

       /* LayerDrawable progressBarDrawable = (LayerDrawable) seek_bar.getProgressDrawable();
        Drawable progressDrawable = progressBarDrawable.getDrawable(1);
        progressDrawable.setColorFilter(ContextCompat.getColor(this, accentColor), PorterDuff.Mode.SRC_IN);*/
        albumArtAdapter = new ImagePagerAdapter(getSupportFragmentManager(), songsUtils.queue().size());
        albumArtViewpager.setPageTransformer(false, new ParallaxPagerTransformer(R.id.imageView));
        albumArtViewpager.setAdapter(albumArtAdapter);
        albumArtViewpager.setCurrentItem(sharedPrefsUtils.readSharedPrefsInt("musicID", 0));
        albumArtViewpager.setOffscreenPageLimit(3);

        albumArtViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageSelected(int position) {
                if (position < 0 || position > songsUtils.queue().size() - 1) {
                    finish();
                    startActivity(getIntent());
                    return;
                }
                if (songsUtils.getCurrentMusicID() < position) {
                    MediaControllerCompat.getMediaController(free_Music_PlayActivity.this).getTransportControls().skipToNext();
                } else if (songsUtils.getCurrentMusicID() > position) {
                    MediaControllerCompat.getMediaController(free_Music_PlayActivity.this).getTransportControls().skipToPrevious();
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        seek_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                stopSeekbarUpdate();
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                MediaControllerCompat.getMediaController(free_Music_PlayActivity.this).getTransportControls().seekTo(seekBar.getProgress());
                scheduleSeekbarUpdate();
            }
        });

        btnPlay.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnPrev.setOnClickListener(this);
        btnRepeat.setOnClickListener(this);

        imgFav.setOnClickListener(new SingleClickListener() {
            @Override
            public void performClick(View v) {
                doItMyFav();
            }
        });


        findViewById(R.id.addToPlayListImageView).setOnClickListener(new SingleClickListener() {
            @Override
            public void performClick(View v) {
                findViewById(R.id.addToPlayListImageView).startAnimation(animation5);
                songsUtils.addToPlaylist(songsUtils.queue().get(sharedPrefsUtils.readSharedPrefsInt("musicID", 0)));

            }
        });



        viewQueue.setOnClickListener(new SingleClickListener() {
            @Override
            public void performClick(View v) {
                    viewQueue.startAnimation(animation2);
                    if (!getResources().getBoolean(R.bool.isLandscape)) {
                        if (queueFragment.getVisibility() == View.VISIBLE) {
                            queueFragment.setVisibility(View.GONE);
                        } else {
                            queueFragment.setVisibility(View.VISIBLE);
                        }
                    }
                    startActivity(new Intent(free_Music_PlayActivity.this, free_ListActivity.class));
                }

        });


        findViewById(R.id.shuffleImageView).setOnClickListener(new SingleClickListener() {
            @Override
            public void performClick(View v) {
                findViewById(R.id.shuffleImageView).startAnimation(animation4);
                shuffleImageView.setImageResource(R.drawable.fill_shuffleimageview);
                if (sharedPrefsUtils.readSharedPrefsBoolean("Shuffle", false)) {
//                    shuffleImageView.clearColorFilter();
                    shuffleImageView.setImageResource(R.drawable.ic_shuffle_11);
                    sharedPrefsUtils.writeSharedPrefs("Shuffle", false);
                    shuffleOFF();
                } else {
//                    shuffleImageView.setColorFilter(ContextCompat.getColor(playerpro_PlayActivity.this, accentColor));
                    shuffleImageView.setImageResource(R.drawable.fill_shuffleimageview);
                    sharedPrefsUtils.writeSharedPrefs("Shuffle", true);
                    shuffle();
                }
            }
        });

        findViewById(R.id.viewshare).setOnClickListener(new SingleClickListener() {
            @Override
            public void performClick(View v) {
                findViewById(R.id.viewshare).startAnimation(animation6);
                String sharePath = songsUtils.queue().get(sharedPrefsUtils.readSharedPrefsInt("musicID", 0)).getPath();
                Uri uri = Uri.parse(sharePath);
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("audio/*");
                share.putExtra(Intent.EXTRA_STREAM, uri);
                startActivity(Intent.createChooser(share, "Share Sound File"));
            }
        });

        findViewById(R.id.infoImageView).setOnClickListener(new SingleClickListener() {
            @Override
            public void performClick(View v) {
                findViewById(R.id.infoImageView).startAnimation(animation7);
                songsUtils.info(
                        songsUtils.queue().get(sharedPrefsUtils.readSharedPrefsInt("musicID", 0))
                ).show();
            }
        });

        queueFragment.setVisibility(View.GONE);
    }

    @Override
    public void viewPagerRefreshOne() {
        albumArtAdapter = new ImagePagerAdapter(getSupportFragmentManager(), songsUtils.queue().size());
        albumArtViewpager.setAdapter(albumArtAdapter);
        albumArtViewpager.setCurrentItem(sharedPrefsUtils.readSharedPrefsInt("musicID", 0));
    }

    private static class ImagePagerAdapter extends FragmentPagerAdapter {

        private final int mSize;

        ImagePagerAdapter(FragmentManager fm, int size) {
            super(fm);
            mSize = size;
        }

        @Override
        public int getCount() {
            return mSize;
        }

        @Override
        public Fragment getItem(int position) {
            return free_ImageDetailFragment.newInstance(position);
        }
    }

    public void setGraphics() {

        if (getIndex(sharedPrefsUtils.readSharedPrefsString("raw_path", null)) != -1) {
            imgFav.setImageDrawable(getResources().getDrawable(R.drawable.app_inheart1));
            isFavourite = true;
        } else {
            imgFav.setImageDrawable(getResources().getDrawable(R.drawable.ic_like_11));
            isFavourite = false;
        }
        if (sharedPrefsUtils.readSharedPrefsBoolean("repeat", false)) {
//            btnRepeat.setColorFilter(ContextCompat.getColor(this, accentColor));
            btnRepeat.setImageResource(R.drawable.ic_repeat_11);
        } else {
            sharedPrefsUtils.writeSharedPrefs("repeat", false);
        }

        if (sharedPrefsUtils.readSharedPrefsBoolean("Shuffle", false)) {
//            shuffleImageView.setColorFilter(ContextCompat.getColor(this, accentColor));
            shuffleImageView.setImageResource(R.drawable.ic_shuffle_11);

        } else {
            sharedPrefsUtils.writeSharedPrefs("Shuffle", false);

        }

        if (albumArtViewpager.getCurrentItem() != sharedPrefsUtils.readSharedPrefsInt("musicID", 0)) {
            albumArtViewpager.setVisibility(View.VISIBLE);
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            albumArtViewpager.setCurrentItem(sharedPrefsUtils.readSharedPrefsInt("musicID", 0));
                        }
                    });
                }
            });
            thread.start();
        }

    }

    public void shuffle() {
        int musicID = sharedPrefsUtils.readSharedPrefsInt("musicID", 0);
        ArrayList<SongModel> arrayList = new ArrayList<>(songsUtils.queue());
        SongModel currentSong = arrayList.get(musicID);
        arrayList.remove(musicID);
        Collections.shuffle(arrayList);
        arrayList.add(0, currentSong);
        songsUtils.replaceQueue(arrayList);
        sharedPrefsUtils.writeSharedPrefs("musicID", 0);

        viewPagerRefreshOne();
        free_QueueFragment playerproQueueFragment = (free_QueueFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        if (playerproQueueFragment != null) {
            playerproQueueFragment.notifyFragmentQueueUpdate();
        }

        free_QueueFragment playerproQueueFragment1 = (free_QueueFragment) getSupportFragmentManager().findFragmentById(free_ListActivity.idf);
        if (playerproQueueFragment1 != null) {
            playerproQueueFragment1.notifyFragmentQueueUpdate();
        }

        (new CommonUtils(free_Music_PlayActivity.this)).showTheToast("Shuffling the list");
    }


    public void shuffleOFF() {
        int musicID = sharedPrefsUtils.readSharedPrefsInt("musicID", 0);
        ArrayList<SongModel> arrayList = new ArrayList<>(songsUtils.queue());
        SongModel currentSong = arrayList.get(musicID);

        arrayList.remove(musicID);
        arrayList.add(0, currentSong);

        songsUtils.replaceQueue(arrayList);
        sharedPrefsUtils.writeSharedPrefs("musicID", 0);

        viewPagerRefreshOne();
        free_QueueFragment playerproQueueFragment = (free_QueueFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        if (playerproQueueFragment != null) {
            playerproQueueFragment.notifyFragmentQueueUpdate();
        }

        free_QueueFragment playerproQueueFragment1 = (free_QueueFragment) getSupportFragmentManager().findFragmentById(free_ListActivity.idf);
        if (playerproQueueFragment1 != null) {
            playerproQueueFragment1.notifyFragmentQueueUpdate();
        }

        (new CommonUtils(free_Music_PlayActivity.this)).showTheToast("Shuffling off");
    }


    private int getIndex(String rawPath) {
        FavouriteList db = new FavouriteList(this);
        db.open();
        ArrayList<SongModel> list;
        list = db.getAllRows();
        db.close();
        int i = 0;
        while (i < list.size()) {
            if (list.get(i).getPath().equals(rawPath)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    private int getIndex(String rawPath, ArrayList<SongModel> list) {
        int i = 0;
        while (i < list.size()) {
            if (list.get(i).getPath().equals(rawPath)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    private void doItMyFav() {
        imgFav.startAnimation(animation3);
        FavouriteList db = new FavouriteList(free_Music_PlayActivity.this);
        db.open();
        if (!isFavourite) {
            if (getIndex(sharedPrefsUtils.readSharedPrefsString("raw_path", null)) == -1) {
                SongModel hashMap = songsUtils.queue().get(sharedPrefsUtils.readSharedPrefsInt("musicID", 0));
                if (songsUtils.addToFavouriteSongs(hashMap)) {

                    imgFav.setImageDrawable(getResources().getDrawable(R.drawable.app_inheart1));

                    isFavourite = true;
                    (new CommonUtils(this)).showTheToast("Favourite Added!");
                } else {
                    (new CommonUtils(this)).showTheToast("Unable to add to Favourite!");
                }
            }
        } else {
            ArrayList<SongModel> list = songsUtils.favouriteSongs();
            if (db.deleteAll()) {

                imgFav.setImageDrawable(getResources().getDrawable(R.drawable.ic_like_11));
                int n = getIndex(sharedPrefsUtils.readSharedPrefsString("raw_path", null), list);
                list.remove(n);

                for (int i = 0; i < list.size(); i++) {
                    db.addRow(list.get(i));
                }
                isFavourite = false;
                (new CommonUtils(this)).showTheToast("Favourite Removed!");
            } else {
                (new CommonUtils(this)).showTheToast("Unable to Remove Favourite!");
            }
        }
        db.close();
    }

    @Override
    public void onClick(View target) {
        if (target == btnPlay) {
            MediaControllerCompat.getMediaController(free_Music_PlayActivity.this).getTransportControls().play();
        } else if (target == btnRepeat) {
            btnRepeat.startAnimation(animation1);
            btnRepeat.setImageResource(R.drawable.fill_ic_repeat_11);
            if (sharedPrefsUtils.readSharedPrefsBoolean("repeat", false)) {
//                btnRepeat.clearColorFilter();
                btnRepeat.setImageResource(R.drawable.ic_repeat_11);
                (new CommonUtils(this)).showTheToast("Repeat Off");

                sharedPrefsUtils.writeSharedPrefs("repeat", false);

            } else {
//                btnRepeat.setColorFilter(ContextCompat.getColor(this, accentColor));
                btnRepeat.setImageResource(R.drawable.fill_ic_repeat_11);
                (new CommonUtils(this)).showTheToast("Repeat On");
                sharedPrefsUtils.writeSharedPrefs("repeat", true);

            }
        } else if (target == btnNext) {
            MediaControllerCompat.getMediaController(free_Music_PlayActivity.this).getTransportControls().skipToNext();
        } else if (target == btnPrev) {
            MediaControllerCompat.getMediaController(free_Music_PlayActivity.this).getTransportControls().skipToPrevious();
        }
    }

    private final MediaControllerCompat.Callback mCallback = new MediaControllerCompat.Callback() {
        @Override
        public void onPlaybackStateChanged(@NonNull PlaybackStateCompat state) {
            Log.e(TAG, "onPlayBackStateChanged" + state);
            updatePlaybackState(state);
        }

        @Override
        public void onMetadataChanged(MediaMetadataCompat metadata) {
            if (metadata != null) {
                updateMediaDescription(metadata);
                updateDuration(metadata);
            }
        }
    };

    private final MediaBrowserCompat.ConnectionCallback mConnectionCallback =
            new MediaBrowserCompat.ConnectionCallback() {
                @Override
                public void onConnected() {
                    Log.e(TAG, "onConnected");
                    try {
                        connectToSession(mMediaBrowser.getSessionToken());
                        ContextCompat.startForegroundService(free_Music_PlayActivity.this,
                                new Intent(free_Music_PlayActivity.this, MusicPlayback.class));
                    } catch (RemoteException e) {
                        Log.e(TAG, "could not connect media controller");
                    }
                }
            };

    private void connectToSession(MediaSessionCompat.Token token) throws RemoteException {
        MediaControllerCompat mediaController = new MediaControllerCompat(
                free_Music_PlayActivity.this, token);

        if (mediaController.getMetadata() == null) {
            finish();
            return;
        }

        MediaControllerCompat.setMediaController(this, mediaController);
        mediaController.registerCallback(mCallback);
        PlaybackStateCompat state = mediaController.getPlaybackState();
        updatePlaybackState(state);
        MediaMetadataCompat metadata = mediaController.getMetadata();
        if (metadata != null) {
            updateMediaDescription(metadata);
            updateDuration(metadata);
        }
        updateProgress();
        if (state != null && (state.getState() == PlaybackStateCompat.STATE_PLAYING ||
                state.getState() == PlaybackStateCompat.STATE_BUFFERING)) {
            scheduleSeekbarUpdate();
        }
    }

    @SuppressLint("SetTextI18n")
    private void updateMediaDescription(MediaMetadataCompat metadata) {
        title.setText(metadata.getText(MediaMetadataCompat.METADATA_KEY_TITLE));
        album.setText(metadata.getText(MediaMetadataCompat.METADATA_KEY_ALBUM));

        free_QueueFragment playerproQueueFragment = (free_QueueFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        if (playerproQueueFragment != null) {
            playerproQueueFragment.notifyFragmentQueueUpdate();
        }
        free_QueueFragment playerproQueueFragment1 = (free_QueueFragment) getSupportFragmentManager().findFragmentById(free_ListActivity.idf);
        if (playerproQueueFragment1 != null) {
            playerproQueueFragment1.notifyFragmentQueueUpdate();
        }
        setGraphics();
    }

    private void scheduleSeekbarUpdate() {
        stopSeekbarUpdate();
        if (!mExecutorService.isShutdown()) {
            long PROGRESS_UPDATE_INTERNAL = 1000;
            long PROGRESS_UPDATE_INITIAL_INTERVAL = 100;
            mScheduleFuture = mExecutorService.scheduleAtFixedRate(
                    new Runnable() {
                        @Override
                        public void run() {
                            mHandler.post(mUpdateProgressTask);
                        }
                    }, PROGRESS_UPDATE_INITIAL_INTERVAL,
                    PROGRESS_UPDATE_INTERNAL, TimeUnit.MILLISECONDS);
        }
    }

    private void stopSeekbarUpdate() {
        if (mScheduleFuture != null) {
            mScheduleFuture.cancel(false);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mMediaBrowser != null) {
            mMediaBrowser.connect();
        }
    }
    @Override
    public void onBackPressed() {
        IntertitialAdsEvent.ShowInterstitialAdsOnBack(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mMediaBrowser != null) {
            mMediaBrowser.disconnect();
            Log.e(TAG, "disconnecting from MediaSession");
        }
        if (MediaControllerCompat.getMediaController(this) != null) {
            MediaControllerCompat.getMediaController(this).unregisterCallback(mCallback);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSeekbarUpdate();
        mExecutorService.shutdown();
    }

    private void updateDuration(MediaMetadataCompat metadata) {
        if (metadata == null) {
            return;
        }
        int duration = (int) metadata.getLong(MediaMetadataCompat.METADATA_KEY_DURATION);
        seek_bar.setMax(duration);
        totTime.setText("" + createTimeLabel((int) duration));
    }

    private void updatePlaybackState(PlaybackStateCompat state) {
        if (state == null) {
            return;
        }
        mLastPlaybackState = state;
        switch (state.getState()) {
            case PlaybackStateCompat.STATE_PLAYING:
                scheduleSeekbarUpdate();
                btnPlay.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_pause_round_11));
                break;
            case PlaybackStateCompat.STATE_PAUSED:
                stopSeekbarUpdate();
                btnPlay.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_play_round_11));
                break;
            case PlaybackStateCompat.STATE_NONE:
            case PlaybackStateCompat.STATE_STOPPED:
                stopSeekbarUpdate();
                btnPlay.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_play_round_11));
                break;
            case PlaybackStateCompat.STATE_BUFFERING:
                stopSeekbarUpdate();
                break;
            default:
            case PlaybackStateCompat.STATE_CONNECTING:
                break;
            case PlaybackStateCompat.STATE_ERROR:
                break;
            case PlaybackStateCompat.STATE_FAST_FORWARDING:
                break;
            case PlaybackStateCompat.STATE_REWINDING:
                break;
            case PlaybackStateCompat.STATE_SKIPPING_TO_NEXT:
                break;
            case PlaybackStateCompat.STATE_SKIPPING_TO_PREVIOUS:
                break;
            case PlaybackStateCompat.STATE_SKIPPING_TO_QUEUE_ITEM:
                break;
        }
    }

    @SuppressLint("ResourceAsColor")
    private void updateProgress() {
        totTime = findViewById(R.id.total_time);
        curTime = findViewById(R.id.current_time);
        if (mLastPlaybackState == null) {
            return;
        }
        long currentPosition = mLastPlaybackState.getPosition();
        if (mLastPlaybackState.getState() == PlaybackStateCompat.STATE_PLAYING) {
            long timeDelta = SystemClock.elapsedRealtime() -
                    mLastPlaybackState.getLastPositionUpdateTime();
            currentPosition += (int) timeDelta * mLastPlaybackState.getPlaybackSpeed();
        }
        seek_bar.setProgress((int) currentPosition);

        curTime.setText("" + createTimeLabel((int) currentPosition));

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

    public void onItemClick(int mPosition) {
        if (mPosition >= 0) {
            MediaControllerCompat.getMediaController(this).getTransportControls().skipToQueueItem(mPosition);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.play, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            backPressed();
        } else if (id == R.id.action_drive_mode) {
        } else if (id == R.id.add_to_playlist) {
            songsUtils.addToPlaylist(songsUtils.queue().get(sharedPrefsUtils.readSharedPrefsInt("musicID", 0)));
        } else if (id == R.id.equalizer) {
        } else if (id == R.id.save_as_playlist) {
            final Dialog alertDialog = new Dialog(this);
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            final EditText input = alertDialog.findViewById(R.id.editText);
            input.requestFocus();

            Button btnCreate = alertDialog.findViewById(R.id.btnCreate);

            Button btnCancel = alertDialog.findViewById(R.id.btnCancel);

            btnCreate.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = input.getText().toString();
                    if (!name.isEmpty()) {
                        songsUtils.addPlaylist(name);
                        (new CommonUtils(free_Music_PlayActivity.this)).showTheToast("Adding songs to list: " + name);
                        alertDialog.cancel();
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                PlaylistSongs db = new PlaylistSongs(free_Music_PlayActivity.this);
                                db.open();
                                ArrayList<SongModel> data = songsUtils.queue();
                                int playlistID = Integer.parseInt(Objects.requireNonNull(songsUtils.getAllPlaylists().get(
                                        songsUtils.getAllPlaylists().size() - 1).get("ID")));
                                for (int i = 0; i < data.size(); i++) {
                                    db.addRow(playlistID, data.get(i));
                                }
                                db.close();
                            }
                        });
                        thread.run();
                    } else {
                        Toast.makeText(free_Music_PlayActivity.this, "Please enter playlist name.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            btnCancel.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.cancel();
                }
            });
            alertDialog.show();
        } else if (id == R.id.goto_album) {
        } else if (id == R.id.goto_artist) {
        } else if (id == R.id.info) {
            songsUtils.info(songsUtils.queue().get(sharedPrefsUtils.readSharedPrefsInt("musicID", 0))).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public String createTimeLabel(int duration) {
        String timeLabel = "";
        int min = duration / 1000 / 60;
        int sec = duration / 1000 % 60;

        timeLabel += min + ":";
        if (sec < 10) timeLabel += "0";
        timeLabel += sec;

        return timeLabel;
    }
}