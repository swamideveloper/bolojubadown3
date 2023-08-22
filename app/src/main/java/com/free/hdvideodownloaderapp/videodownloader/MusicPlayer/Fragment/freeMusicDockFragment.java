package com.free.hdvideodownloaderapp.videodownloader.MusicPlayer.Fragment;

import static android.support.v4.media.session.PlaybackStateCompat.STATE_BUFFERING;
import static android.support.v4.media.session.PlaybackStateCompat.STATE_CONNECTING;
import static android.support.v4.media.session.PlaybackStateCompat.STATE_ERROR;
import static android.support.v4.media.session.PlaybackStateCompat.STATE_FAST_FORWARDING;
import static android.support.v4.media.session.PlaybackStateCompat.STATE_NONE;
import static android.support.v4.media.session.PlaybackStateCompat.STATE_PAUSED;
import static android.support.v4.media.session.PlaybackStateCompat.STATE_PLAYING;
import static android.support.v4.media.session.PlaybackStateCompat.STATE_REWINDING;
import static android.support.v4.media.session.PlaybackStateCompat.STATE_SKIPPING_TO_NEXT;
import static android.support.v4.media.session.PlaybackStateCompat.STATE_SKIPPING_TO_PREVIOUS;
import static android.support.v4.media.session.PlaybackStateCompat.STATE_SKIPPING_TO_QUEUE_ITEM;
import static android.support.v4.media.session.PlaybackStateCompat.STATE_STOPPED;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.free.hdvideodownloaderapp.videodownloader.Activity.free_Music_PlayActivity;
import com.free.hdvideodownloaderapp.videodownloader.Log;
import com.free.hdvideodownloaderapp.videodownloader.Services.MusicPlayback;
import com.free.hdvideodownloaderapp.videodownloader.Utils.CommonUtils;
import com.free.hdvideodownloaderapp.videodownloader.Utils.SharedPrefsUtils;
import com.free.hdvideodownloaderapp.videodownloader.Utils.SongsUtils;
import com.free.hdvideodownloaderapp.videodownloader.R;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("ConstantConditions")
public class freeMusicDockFragment extends Fragment {

    private TextView title,current_time,total_time;
    private TextView artist;
    private ImageView btnPlay;
    private ImageView albumArt;
    private SeekBar progressBar;
    private MediaBrowserCompat mMediaBrowser;
    private SongsUtils songsUtils;
    private RelativeLayout musicDockRoot;

    private final Runnable mUpdateProgressTask = new Runnable() {
        @Override
        public void run() {
            updateProgress();
        }
    };

    private final ScheduledExecutorService mExecutorService =
            Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> mScheduleFuture;
    private final Handler mHandler = new Handler();

    public static String strtital = "";

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("==>", String.valueOf(songsUtils.getMainListSize()));
        if (songsUtils.getMainListSize() == 0) {
            musicDockRoot.setVisibility(View.INVISIBLE);
        } else {
            musicDockRoot.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music_dock, container,
                false);

        musicDockRoot = view.findViewById(R.id.root_music_dock);
        title = view.findViewById(R.id.XtextView1);
        artist = view.findViewById(R.id.XtextView2);
        btnPlay = view.findViewById(R.id.XbtnPlay);
        current_time = view.findViewById(R.id.current_time);
        total_time = view.findViewById(R.id.total_time);
        albumArt = view.findViewById(R.id.albumArt);
        progressBar = view.findViewById(R.id.progressBar);
        final Button btnPlayActivity = view.findViewById(R.id.Xbutton1);
        title.setSelected(true);



        songsUtils = new SongsUtils(getActivity());


        if (!songsUtils.queue().isEmpty()) {
            if (songsUtils.getCurrentMusicID() == 0) {
                musicDockRoot.setVisibility(View.INVISIBLE);
            } else {
                musicDockRoot.setVisibility(View.VISIBLE);
            }
        } else {
            musicDockRoot.setVisibility(View.INVISIBLE);
        }

        btnPlayActivity.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                touchDock();
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!songsUtils.queue().isEmpty()) {
                    MediaControllerCompat.getMediaController(getActivity()).getTransportControls().play();
                }
            }
        });

        mMediaBrowser = new MediaBrowserCompat(getActivity(),
                new ComponentName(getActivity(), MusicPlayback.class), mConnectionCallback, null);


        progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                stopSeekbarUpdate();
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                MediaControllerCompat.getMediaController(getActivity()).getTransportControls().seekTo(seekBar.getProgress());
                scheduleSeekbarUpdate();
            }
        });
        return view;

    }


    void touchDock() {
        if (!songsUtils.queue().isEmpty()) {
            Intent intent = new Intent(getActivity(), free_Music_PlayActivity.class);
            startActivity(intent);
        } else {
            (new CommonUtils(getActivity())).showTheToast("No music found in device, try Sync in options.");
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
            }
        }
    };

    private void updateMediaDescription(MediaMetadataCompat metadata) {
        title.setText(metadata.getText(MediaMetadataCompat.METADATA_KEY_TITLE));
        artist.setText(metadata.getText(MediaMetadataCompat.METADATA_KEY_ARTIST));
        albumArt.setImageBitmap(metadata.getBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART));
        strtital = title.getText().toString();


    }

    private final String TAG = "MusicDockConsole";

    private final MediaBrowserCompat.ConnectionCallback mConnectionCallback =
            new MediaBrowserCompat.ConnectionCallback() {
                @Override
                public void onConnected() {
                    Log.e(TAG, "onConnected");
                    try {
                        connectToSession(mMediaBrowser.getSessionToken());
                        if (songsUtils.getMainListSize() == 0) {
                            Log.e("nasijcdicni", "=6=");
                            return;
                        }
                        ContextCompat.startForegroundService(getActivity(), new Intent(getActivity(), MusicPlayback.class));

                    } catch (RemoteException e) {
                        Log.e(TAG, "could not connect media controller");
                    }
                }
            };

    private void connectToSession(MediaSessionCompat.Token token) throws RemoteException {
        MediaControllerCompat mediaController = new MediaControllerCompat(
                getActivity(), token);
        if (mediaController.getMetadata() == null) {
            return;
        }
        MediaControllerCompat.setMediaController(getActivity(), mediaController);
        mediaController.registerCallback(mCallback);
        PlaybackStateCompat state = mediaController.getPlaybackState();
        updateMediaDescription(mediaController.getMetadata());
        updatePlaybackState(state);
        MediaMetadataCompat metadata = mediaController.getMetadata();
        if (metadata != null) {
            updateMediaDescription(metadata);
        }
        updateDuration(metadata);
        updateProgress();
        if (state != null && (state.getState() == STATE_PLAYING ||
                state.getState() == STATE_BUFFERING)) {
            scheduleSeekbarUpdate();
        }
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

    private void updateDuration(MediaMetadataCompat metadata) {
        if (metadata == null) {
            return;
        }
        if (mLastPlaybackState == null) {
            SharedPrefsUtils sharedPrefsUtils = new SharedPrefsUtils(getActivity());
            if (songsUtils.queue().size() != 0 && sharedPrefsUtils.readSharedPrefsString("raw_path", "").equals(songsUtils.queue().get(songsUtils.getCurrentMusicID()).getPath())) {
                progressBar.setMax(sharedPrefsUtils.readSharedPrefsInt("durationInMS", 0));
                total_time.setText("" + createTimeLabel((int) sharedPrefsUtils.readSharedPrefsInt("durationInMS", 0)));
                musicDockRoot.setVisibility(View.VISIBLE);
            }
            return;
        }
        int duration = (int) metadata.getLong(MediaMetadataCompat.METADATA_KEY_DURATION);
        progressBar.setMax(duration);
        total_time.setText("" + createTimeLabel((int) duration));
        Log.e(TAG, "ooooo: "+ createTimeLabel((int) duration));

    }

    private void updateProgress() {
        if (mLastPlaybackState == null) {
            SharedPrefsUtils sharedPrefsUtils = new SharedPrefsUtils(getActivity());
            if (songsUtils.queue().size() != 0 && sharedPrefsUtils.readSharedPrefsString("raw_path", "").equals(songsUtils.queue().get(songsUtils.getCurrentMusicID()).getPath())) {
                progressBar.setProgress(sharedPrefsUtils.readSharedPrefsInt("song_position", 0));
                musicDockRoot.setVisibility(View.VISIBLE);
            }
            return;
        }
        long currentPosition = mLastPlaybackState.getPosition();
        if (mLastPlaybackState.getState() == STATE_PLAYING) {

            long timeDelta = SystemClock.elapsedRealtime() -
                    mLastPlaybackState.getLastPositionUpdateTime();
            currentPosition += (int) timeDelta * mLastPlaybackState.getPlaybackSpeed();
        }
        progressBar.setProgress((int) currentPosition);
        current_time.setText("" + createTimeLabel((int) currentPosition)+"");
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
            Log.e(TAG, "connecting to MediaSession");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mMediaBrowser != null) {
            mMediaBrowser.disconnect();
            Log.e(TAG, "disconnecting from MediaSession");
        }
        if (MediaControllerCompat.getMediaController(getActivity()) != null) {
            MediaControllerCompat.getMediaController(getActivity()).unregisterCallback(mCallback);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSeekbarUpdate();
        mExecutorService.shutdown();
    }

    PlaybackStateCompat mLastPlaybackState = null;

    private void updatePlaybackState(PlaybackStateCompat state) {
        if (state == null) {
            return;
        }
        mLastPlaybackState = state;

        switch (state.getState()) {
            case STATE_PLAYING:
                scheduleSeekbarUpdate();
                btnPlay.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_pause_round_1));
                break;
            case STATE_PAUSED:
            case STATE_STOPPED:
                stopSeekbarUpdate();
                btnPlay.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_play_round_1));
                break;
            case STATE_NONE:
                btnPlay.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_play_round_1));
                break;
            case STATE_BUFFERING:
                stopSeekbarUpdate();
                break;
            default:
                Log.e(TAG, "Unhandled state " + state.getState());
            case STATE_CONNECTING:
            case STATE_ERROR:
            case STATE_FAST_FORWARDING:
            case STATE_REWINDING:
            case STATE_SKIPPING_TO_NEXT:
            case STATE_SKIPPING_TO_PREVIOUS:
            case STATE_SKIPPING_TO_QUEUE_ITEM:
                break;
        }
    }
}

