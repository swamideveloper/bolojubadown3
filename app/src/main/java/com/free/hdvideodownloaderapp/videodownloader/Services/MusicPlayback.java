package com.free.hdvideodownloaderapp.videodownloader.Services;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.audiofx.BassBoost;
import android.media.audiofx.Equalizer;
import android.media.audiofx.Virtualizer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.PowerManager;
import android.os.SystemClock;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.media.MediaBrowserServiceCompat;
import androidx.media.app.NotificationCompat.MediaStyle;
import androidx.media.session.MediaButtonReceiver;

import com.free.hdvideodownloaderapp.videodownloader.Activity.free_Music_PlayActivity;
import com.free.hdvideodownloaderapp.videodownloader.Helpers.MediaStyleHelper;
import com.free.hdvideodownloaderapp.videodownloader.Log;
import com.free.hdvideodownloaderapp.videodownloader.Utils.CategorySongs;
import com.free.hdvideodownloaderapp.videodownloader.Utils.CommonUtils;
import com.free.hdvideodownloaderapp.videodownloader.Utils.SharedPrefsUtils;
import com.free.hdvideodownloaderapp.videodownloader.Utils.SongsUtils;
import com.free.hdvideodownloaderapp.videodownloader.R;


import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.List;

public class MusicPlayback extends MediaBrowserServiceCompat implements
        MediaPlayer.OnCompletionListener, AudioManager.OnAudioFocusChangeListener, MediaPlayer.OnPreparedListener {

    Equalizer eq;
    BassBoost bassBoost;
    Virtualizer virtualizer;
    public static final String ACTION_CLOSE = "com.free.hdvideodownloaderapp.videodownloader.action.CLOSE";
    public static final String ACTION_PLAY = "com.free.hdvideodownloaderapp.videodownloader.action.PLAY";
    public static final String ACTION_PLAY_PAUSE = "com.free.hdvideodownloaderapp.videodownloader.action.PLAY_PAUSE";
    public static final String ACTION_TRACK_PREV = "com.free.hdvideodownloaderapp.videodownloader.action.TRACK_PREV";
    public static final String ACTION_TRACK_NEXT = "com.free.hdvideodownloaderapp.videodownloader.action.TRACK_NEXT";
    public static final String ACTION_REPEAT = "com.free.hdvideodownloaderapp.videodownloader.action.REPEAT";
    public static final String ACTION_PERSISTENT_NOTIFICATION = "com.free.hdvideodownloaderapp.videodownloader.action.PERSISTENT_NOTIFICATION";
    private final String TAG = "PlaybackServiceConsole";
    private MediaPlayer mMediaPlayer;
    private MediaSessionCompat mMediaSessionCompat;
    private PlaybackStateCompat.Builder mPlaybackStateBuilder;
    private SharedPrefsUtils sharedPrefsUtils;
    private SongsUtils songsUtils;
    private int currentMediaPlayer = 0;
    boolean autoPaused = false;
    private final int NOTIFICATION_ID = 34213134;

    public MusicPlayback() {
        super();
    }


    @Override
    public void onCreate() {
        super.onCreate();

        sharedPrefsUtils = new SharedPrefsUtils(this);
        songsUtils = new SongsUtils(this);

        checkErrorInPrefs();
        initMediaPlayer();
        initMediaSession();
        initNoisyReceiver();
        initNotification();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {

            MediaButtonReceiver.handleIntent(mMediaSessionCompat, intent);
            Log.e(TAG, "Intent received.");
            String action = intent.getAction();
            if (action != null) {
                switch (action) {
                    case ACTION_PLAY: {
                        Log.e(TAG, "Intent received...");
                        resetMediaPlayerPosition();
                        processPlayRequest();
                        break;
                    }
                    case ACTION_PLAY_PAUSE: {
                        resetMediaPlayerPosition();
                        processPlayPause();
                        break;
                    }
                    case ACTION_TRACK_PREV: {
                        processPrevRequest();
                        break;
                    }
                    case ACTION_TRACK_NEXT: {
                        processNextRequest();
                        break;
                    }
                    case ACTION_REPEAT: {
                        musicWidgetsReset();
                        break;
                    }
                    case ACTION_CLOSE: {
                        if (!sharedPrefsUtils.readSharedPrefsBoolean("persistentNotificationPref", false)) {
                            processCloseRequest();
                        } else {
                            processPauseRequest();
                        }
                        break;
                    }
                    case ACTION_PERSISTENT_NOTIFICATION:
                        if (mPlaybackStateBuilder.build().getState() != PlaybackStateCompat.STATE_PLAYING) {
                            showPausedNotification();
                        }
                        break;
                    default: {
                        initNotification();
                    }
                }
            } else {
                initNotification();
            }
        }
        return START_STICKY;
    }

    private MediaPlayer getCurrentMediaPlayer() {
        return mMediaPlayer;
    }

    private void checkErrorInPrefs() {
        if (songsUtils.getCurrentMusicID() > songsUtils.queue().size() - 1) {
            songsUtils.setCurrentMusicID(0);
        }
    }


    private void saveData() {
        int musicID = songsUtils.getCurrentMusicID();
        sharedPrefsUtils.writeSharedPrefs("audio_session_id", getCurrentMediaPlayer().getAudioSessionId());
        try {
            sharedPrefsUtils.writeSharedPrefs("musicID", musicID);
            sharedPrefsUtils.writeSharedPrefs("title", songsUtils.queue().get(musicID).getTitle());
            sharedPrefsUtils.writeSharedPrefs("artist", songsUtils.queue().get(musicID).getArtist());
            sharedPrefsUtils.writeSharedPrefs("album", songsUtils.queue().get(musicID).getAlbum());
            sharedPrefsUtils.writeSharedPrefs("albumid", songsUtils.queue().get(musicID).getAlbumID());
            sharedPrefsUtils.writeSharedPrefs("raw_path", songsUtils.queue().get(musicID).getPath());
            sharedPrefsUtils.writeSharedPrefs("duration", songsUtils.queue().get(musicID).getDuration());
            sharedPrefsUtils.writeSharedPrefs("durationInMS", getCurrentMediaPlayer().getDuration());
        } catch (Exception e) {
            Log.e(TAG, "Unable to save song info in persistent storage. MusicID " + musicID);
        }
    }

    private void doPushPlay(int id) {
        songsUtils.setCurrentMusicID(id);
        getCurrentMediaPlayer().reset();
        if (successfullyRetrievedAudioFocus()) {
            showPausedNotification();
            return;
        }
        setMediaPlayer(songsUtils.queue().get(id).getPath());
    }

    private void processNextRequest() {
        if (songsUtils.getCurrentMusicID() + 1 == songsUtils.queue().size()) {
            return;
        }
        resetMediaPlayerPosition();

        int musicID = songsUtils.getCurrentMusicID();

        if (musicID + 1 != songsUtils.queue().size()) {
            musicID++;
        } else {
            musicID = 0;
        }
        songsUtils.setCurrentMusicID(musicID);
        if (successfullyRetrievedAudioFocus()) {
            showPausedNotification();
            return;
        }

        Log.e(TAG, "Skipping to Next track.");
        setMediaPlayer(songsUtils.queue().get(musicID).getPath());
    }

    private boolean isMusicPlaying() {
        return mPlaybackStateBuilder.build().getState() == PlaybackStateCompat.STATE_PLAYING;
    }

    private void processPrevRequest() {
        resetMediaPlayerPosition();

        if (getCurrentMediaPlayer().getCurrentPosition() < 5000) {
            int musicID = songsUtils.getCurrentMusicID();
            if (musicID > 0) {
                musicID--;
                songsUtils.setCurrentMusicID(musicID);
                Log.e(TAG, "Skipping to Previous track.");
                if (successfullyRetrievedAudioFocus()) {
                    showPausedNotification();
                    return;
                }
                setMediaPlayer(songsUtils.queue().get(musicID).getPath());
            } else {
                mMediaSessionCompat.getController().getTransportControls().seekTo(0);
            }
        } else {
            mMediaSessionCompat.getController().getTransportControls().seekTo(0);
        }
    }

    private void processCloseRequest() {
        stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sharedPrefsUtils.writeSharedPrefs("song_position", getCurrentMediaPlayer().getCurrentPosition());
        Log.e(TAG, "Shutting MediaPlayer service down...");
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        assert audioManager != null;
        audioManager.abandonAudioFocus(this);
        unregisterReceiver(mNoisyReceiver);
        setMediaPlaybackState(PlaybackStateCompat.STATE_STOPPED);
        mMediaSessionCompat.setActive(false);
        mMediaSessionCompat.release();
        getCurrentMediaPlayer().release();
        try {
            eq.release();
            bassBoost.release();
            virtualizer.release();
        } catch (Exception ignored) {
        }
        stopForeground(true);
        NotificationManagerCompat.from(this).cancel(NOTIFICATION_ID);
    }

    private void processPauseRequest() {
        if (mPlaybackStateBuilder.build().getState() == PlaybackStateCompat.STATE_PLAYING) {
            mMediaSessionCompat.setActive(false);
            getCurrentMediaPlayer().pause();
            sharedPrefsUtils.writeSharedPrefs("song_position", getCurrentMediaPlayer().getCurrentPosition());
            setMediaPlaybackState(PlaybackStateCompat.STATE_PAUSED);
            showPausedNotification();
        }
    }

    private void processPlayRequest() {
        if (successfullyRetrievedAudioFocus()) {
            showPausedNotification();
            return;
        }
        Log.e(TAG, "Processing Play Request for musicID: " + songsUtils.getCurrentMusicID());

        setMediaPlayer(songsUtils.queue().get(songsUtils.getCurrentMusicID()).getPath());
    }

    private void processPlayPause() {
        Log.e(TAG, "Play/Pausing");
        if (mPlaybackStateBuilder.build().getState() == PlaybackStateCompat.STATE_PLAYING) {
            processPauseRequest();
        } else if (mPlaybackStateBuilder.build().getState() == PlaybackStateCompat.STATE_PAUSED) {
            if (successfullyRetrievedAudioFocus()) {
                return;
            }
            mMediaSessionCompat.setActive(true);
            getCurrentMediaPlayer().start();
            setMediaPlaybackState(PlaybackStateCompat.STATE_PLAYING);
            showPlayingNotification();
            autoPaused = false;
        } else {
            processPlayRequest();
        }
    }

    private void setMetaData() {
        if (songsUtils.queue().size() == 0) {
            MediaMetadataCompat mMediaMetadataCompat = new MediaMetadataCompat.Builder()
                    .putString(MediaMetadataCompat.METADATA_KEY_TITLE, sharedPrefsUtils.readSharedPrefsString("title", "Placeholder Title"))
                    .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, sharedPrefsUtils.readSharedPrefsString("album", "Placeholder Album"))
                    .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, sharedPrefsUtils.readSharedPrefsString("artist", "Placeholder Artist"))
                    .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, (long) sharedPrefsUtils.readSharedPrefsInt("durationInMS", 0))
                    .putBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART, grabAlbumArt(sharedPrefsUtils.readSharedPrefsString("albumid", "0")))
                    .build();
            mMediaSessionCompat.setMetadata(mMediaMetadataCompat);
        } else {
            int musicID = songsUtils.getCurrentMusicID();
            MediaMetadataCompat mMediaMetadataCompat = new MediaMetadataCompat.Builder()
                    .putString(MediaMetadataCompat.METADATA_KEY_TITLE, songsUtils.queue().get(musicID).getTitle())
                    .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, songsUtils.queue().get(musicID).getAlbum())
                    .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, songsUtils.queue().get(musicID).getArtist())
                    .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, (long) sharedPrefsUtils.readSharedPrefsInt("durationInMS", 0))
                    .putBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART, grabAlbumArt(songsUtils.queue().get(musicID).getAlbumID()))
                    .build();
            mMediaSessionCompat.setMetadata(mMediaMetadataCompat);
        }
    }

    private void setGraphics() {
        saveData();
        setMetaData();
    }

    private Bitmap grabAlbumArt(String albumID) {
        Bitmap art = null;
        try {
            try {
                final Uri sArtworkUri = Uri
                        .parse("content://media/external/audio/albumart");

                Uri uri = ContentUris.withAppendedId(sArtworkUri,
                        Long.parseLong(albumID));

                ParcelFileDescriptor pfd = getApplicationContext().getContentResolver()
                        .openFileDescriptor(uri, "r");

                if (pfd != null) {
                    FileDescriptor fd = pfd.getFileDescriptor();
                    art = BitmapFactory.decodeFileDescriptor(fd);
                }
            } catch (Exception ignored) {
            }
            if (art == null) {
                return drawableToBitmap(ContextCompat.getDrawable(this, R.drawable.empty_music));
            } else {
                return art;
            }
        } catch (Exception e) {
            return drawableToBitmap(ContextCompat.getDrawable(MusicPlayback.this,
                    R.drawable.empty_music));
        }
    }

    public Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 300;
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 300;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }


    private final MediaSessionCompat.Callback mMediaSessionCallback = new MediaSessionCompat.Callback() {
        @Override
        public void onPlayFromUri(Uri uri, Bundle extras) {
            super.onPlayFromUri(uri, extras);
        }

        @Override
        public void onPlay() {
            super.onPlay();
            processPlayPause();
        }

        @Override
        public void onPause() {
            super.onPause();
            processPauseRequest();
        }

        @Override
        public void onSkipToNext() {
            super.onSkipToNext();
            processNextRequest();
        }

        @Override
        public void onSkipToPrevious() {
            super.onSkipToPrevious();
            processPrevRequest();
        }

        @Override
        public void onRewind() {
            super.onRewind();
        }

        @Override
        public void onStop() {
            super.onStop();
            stopSelf();
        }

        @Override
        public void onSetRepeatMode(int repeatMode) {
            super.onSetRepeatMode(repeatMode);
        }

        @Override
        public void onSkipToQueueItem(long id) {
            super.onSkipToQueueItem(id);
            doPushPlay((int) id);
        }

        @Override
        public void onSeekTo(long pos) {
            super.onSeekTo(pos);
            getCurrentMediaPlayer().seekTo((int) (long) pos);
            Log.e(TAG, "onSeekTo: " + pos);
            setMediaPlaybackState(mPlaybackStateBuilder.build().getState());
        }
    };

    private void setMediaPlaybackState(int state) {
        mPlaybackStateBuilder.setActions(PlaybackStateCompat.ACTION_PLAY
                | PlaybackStateCompat.ACTION_PAUSE | PlaybackStateCompat.ACTION_SKIP_TO_NEXT
                | PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS | PlaybackStateCompat.ACTION_STOP
                | PlaybackStateCompat.ACTION_SEEK_TO);

        mPlaybackStateBuilder.setState(state, getCurrentMediaPlayer().getCurrentPosition(), 1.0f, SystemClock.elapsedRealtime());
        if (mPlaybackStateBuilder.build().getState() == PlaybackStateCompat.STATE_PLAYING) {
            Log.e("PlaybackServiceConsole", "State Changed to Playing");
        } else if (mPlaybackStateBuilder.build().getState() == PlaybackStateCompat.STATE_PAUSED) {
            Log.e("PlaybackServiceConsole", "State Changed to Paused");
        } else if (mPlaybackStateBuilder.build().getState() == PlaybackStateCompat.STATE_STOPPED) {
            Log.e("PlaybackServiceConsole", "State Changed to Stopped");
        }
        mMediaSessionCompat.setPlaybackState(mPlaybackStateBuilder.build());
    }

    private BroadcastReceiver mNoisyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (getCurrentMediaPlayer() != null && getCurrentMediaPlayer().isPlaying()) {
                processPauseRequest();
            }
        }
    };

    private void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager
                    mNotificationManager =
                    (NotificationManager) this
                            .getSystemService(Context.NOTIFICATION_SERVICE);
            String id = "channel_music_playback";
            CharSequence name = "Media Playback";
            String description = "Media playback controls";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel mChannel = new NotificationChannel(id, name, importance);
            mChannel.setDescription(description);
            mChannel.setShowBadge(false);
            mChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(mChannel);
        }
    }

    @SuppressLint("RestrictedApi")
    private void showPlayingNotification() {
        musicWidgetsReset();

        createChannel();
        NotificationCompat.Builder builder
                = MediaStyleHelper.from(MusicPlayback.this, mMediaSessionCompat);

        PendingIntent pCloseIntent = PendingIntent.getService(this, 0,
                (new Intent(this, MusicPlayback.class)).setAction(MusicPlayback.ACTION_CLOSE), PendingIntent.FLAG_IMMUTABLE);

        PendingIntent prevIntent = PendingIntent.getService(this, 0,
                (new Intent(this, MusicPlayback.class)).setAction(MusicPlayback.ACTION_TRACK_PREV), PendingIntent.FLAG_IMMUTABLE);
        PendingIntent playPauseIntent = PendingIntent.getService(this, 0,
                (new Intent(this, MusicPlayback.class)).setAction(MusicPlayback.ACTION_PLAY_PAUSE), PendingIntent.FLAG_IMMUTABLE);
        PendingIntent nextIntent = PendingIntent.getService(this, 0,
                (new Intent(this, MusicPlayback.class)).setAction(MusicPlayback.ACTION_TRACK_NEXT), PendingIntent.FLAG_IMMUTABLE);

        builder.addAction(new NotificationCompat.Action(R.drawable.app_previous, "Previous", prevIntent));
        builder.addAction(new NotificationCompat.Action(R.drawable.pause_noti, "Play", playPauseIntent));
        builder.addAction(new NotificationCompat.Action(R.drawable.app_next, "Next", nextIntent));
        builder.addAction(new NotificationCompat.Action(R.drawable.ic_close_black_24dp, "Close", pCloseIntent));
        builder.setStyle(new MediaStyle().setShowActionsInCompactView(0).setMediaSession(mMediaSessionCompat.getSessionToken()));
        builder.setSmallIcon(R.drawable.ic_music_notes_padded);
        builder.setStyle(new MediaStyle().setShowActionsInCompactView(1, 2).setMediaSession(getSessionToken()));
        builder.setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, free_Music_PlayActivity.class), PendingIntent.FLAG_IMMUTABLE));
        builder.setDeleteIntent(pCloseIntent);
        builder.setColor(getResources().getColor(R.color.black));
        builder.setShowWhen(false);
        startForeground(NOTIFICATION_ID, builder.build());
    }

    private void showPausedNotification() {
        musicWidgetsReset();

        createChannel();
        NotificationCompat.Builder builder
                = MediaStyleHelper.from(MusicPlayback.this, mMediaSessionCompat);

        PendingIntent pCloseIntent = PendingIntent.getService(this, 0,
                (new Intent(this, MusicPlayback.class)).setAction(MusicPlayback.ACTION_CLOSE), PendingIntent.FLAG_IMMUTABLE);

        PendingIntent prevIntent = PendingIntent.getService(this, 0,
                (new Intent(this, MusicPlayback.class)).setAction(MusicPlayback.ACTION_TRACK_PREV), PendingIntent.FLAG_IMMUTABLE);
        PendingIntent playPauseIntent = PendingIntent.getService(this, 0,
                (new Intent(this, MusicPlayback.class)).setAction(MusicPlayback.ACTION_PLAY_PAUSE), PendingIntent.FLAG_IMMUTABLE);
        PendingIntent nextIntent = PendingIntent.getService(this, 0,
                (new Intent(this, MusicPlayback.class)).setAction(MusicPlayback.ACTION_TRACK_NEXT), PendingIntent.FLAG_IMMUTABLE);

        builder.addAction(new NotificationCompat.Action(R.drawable.app_previous, "Previous", prevIntent));
        builder.addAction(new NotificationCompat.Action(R.drawable.play_noti, "Play", playPauseIntent));
        builder.addAction(new NotificationCompat.Action(R.drawable.app_next, "Next", nextIntent));
        builder.addAction(new NotificationCompat.Action(R.drawable.ic_close_black_24dp, "Close", pCloseIntent));
        builder.setStyle(new MediaStyle().setShowActionsInCompactView(0).setMediaSession(mMediaSessionCompat.getSessionToken()));
        builder.setSmallIcon(R.drawable.ic_music_notes_padded);
        builder.setStyle(new MediaStyle().setShowActionsInCompactView(1, 2).setMediaSession(getSessionToken()));
        builder.setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, free_Music_PlayActivity.class), PendingIntent.FLAG_IMMUTABLE));
        builder.setDeleteIntent(pCloseIntent);
        builder.setColor(getResources().getColor(R.color.black));
        builder.setShowWhen(false);
        startForeground(NOTIFICATION_ID, builder.build());
        if (!sharedPrefsUtils.readSharedPrefsBoolean("persistentNotificationPref", false)) {
            stopForeground(false);
        }
    }

    private void initNotification() {
        if (songsUtils.getMainListSize() == 0) return;
        createChannel();
        NotificationCompat.Builder builder
                = MediaStyleHelper.from(MusicPlayback.this, mMediaSessionCompat);

        PendingIntent pCloseIntent = PendingIntent.getService(this, 0,
                (new Intent(this, MusicPlayback.class)).setAction(MusicPlayback.ACTION_CLOSE), PendingIntent.FLAG_IMMUTABLE);

        PendingIntent prevIntent = PendingIntent.getService(this, 0,
                (new Intent(this, MusicPlayback.class)).setAction(MusicPlayback.ACTION_TRACK_PREV), PendingIntent.FLAG_IMMUTABLE);
        PendingIntent playPauseIntent = PendingIntent.getService(this, 0,
                (new Intent(this, MusicPlayback.class)).setAction(MusicPlayback.ACTION_PLAY_PAUSE), PendingIntent.FLAG_IMMUTABLE);
        PendingIntent nextIntent = PendingIntent.getService(this, 0,
                (new Intent(this, MusicPlayback.class)).setAction(MusicPlayback.ACTION_TRACK_NEXT), PendingIntent.FLAG_IMMUTABLE);

        builder.addAction(new NotificationCompat.Action(R.drawable.app_previous, "Previous", prevIntent));
        if (isMusicPlaying()) {
            builder.addAction(new NotificationCompat.Action(R.drawable.pause_noti, "Pause", playPauseIntent));
        } else {
            builder.addAction(new NotificationCompat.Action(R.drawable.play_noti, "Play", playPauseIntent));
        }
        builder.addAction(new NotificationCompat.Action(R.drawable.app_next, "Next", nextIntent));
        builder.addAction(new NotificationCompat.Action(R.drawable.ic_close_black_24dp, "Close", pCloseIntent));
        builder.setStyle(new MediaStyle().setShowActionsInCompactView(0).setMediaSession(mMediaSessionCompat.getSessionToken()));
        builder.setSmallIcon(R.drawable.ic_music_note_black_24dp);
        builder.setStyle(new MediaStyle()
                .setShowActionsInCompactView(1, 2).setMediaSession(getSessionToken()));
        builder.setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, free_Music_PlayActivity.class), PendingIntent.FLAG_IMMUTABLE));
        builder.setDeleteIntent(pCloseIntent);
        builder.setColor(getResources().getColor(R.color.black));
        builder.setShowWhen(false);
        startForeground(NOTIFICATION_ID, builder.build());
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        getCurrentMediaPlayer().reset();
        resetMediaPlayerPosition();
        if (!sharedPrefsUtils.readSharedPrefsBoolean("repeat", false)) {
            Log.e(TAG, "OnCompletion playing next track");
            processNextRequest();
        } else {
            setMediaPlayer((songsUtils.queue().get(songsUtils.getCurrentMusicID()).getPath()));
        }
    }

    void resetMediaPlayerPosition() {
        sharedPrefsUtils.writeSharedPrefs("song_position", 0);
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {

        if (mPlaybackStateBuilder.build().getState() == PlaybackStateCompat.STATE_NONE &&
                sharedPrefsUtils.readSharedPrefsString("raw_path", "").equals(songsUtils.queue().get(songsUtils.getCurrentMusicID()).getPath())) {
            mMediaSessionCompat.getController().getTransportControls().seekTo(sharedPrefsUtils.readSharedPrefsInt("song_position", 0));
        }

        setEqualizer();


        setGraphics();
        Log.e(TAG, "starting playback");

        mMediaSessionCompat.setActive(true);
        autoPaused = false;
        getCurrentMediaPlayer().start();
        setMediaPlaybackState(PlaybackStateCompat.STATE_PLAYING);
        showPlayingNotification();
    }

    private void initMediaPlayer() {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mMediaPlayer.setAudioAttributes(
                new AudioAttributes
                        .Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build());
        mMediaPlayer.setVolume(1.0f, 1.0f);
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnPreparedListener(this);
        sharedPrefsUtils.writeSharedPrefs("audio_session_id", mMediaPlayer.getAudioSessionId());

    }

    private void setMediaPlayer(String path) {
        getCurrentMediaPlayer().reset();
        File file = new File(path);
        if (file.exists()) {
            try {
                addVoteToTrack(path);
                getCurrentMediaPlayer().setDataSource(path);
            } catch (IOException e) {
                processNextRequest();
                e.printStackTrace();
            }

            try {
                getCurrentMediaPlayer().prepare();
            } catch (Exception e) {
                e.printStackTrace();
                (new CommonUtils(this)).showTheToast("Unable to play music file :/");
            }
        } else {
            processNextRequest();
            Log.e(TAG, "Error finding file so we skipped to next.");
            (new CommonUtils(this)).showTheToast("Error finding music file");
        }
    }

    private void initMediaSession() {
        ComponentName mediaButtonReceiver = new ComponentName(getApplicationContext(), MediaButtonReceiver.class);
        mMediaSessionCompat = new MediaSessionCompat(getApplicationContext(), "Tag", mediaButtonReceiver,
                PendingIntent.getActivity(getApplicationContext(), 0, new Intent(), PendingIntent.FLAG_IMMUTABLE));

        mMediaSessionCompat.setCallback(mMediaSessionCallback);
        Intent mediaButtonIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
        mediaButtonIntent.setClass(this, MediaButtonReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, mediaButtonIntent, PendingIntent.FLAG_IMMUTABLE);
        mMediaSessionCompat.setMediaButtonReceiver(pendingIntent);

        mPlaybackStateBuilder = new PlaybackStateCompat.Builder();

        setSessionToken(mMediaSessionCompat.getSessionToken());

        setMetaData();
    }

    private void initNoisyReceiver() {
        IntentFilter filter = new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
        registerReceiver(mNoisyReceiver, filter);
    }


    @Override
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_LOSS:
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT: {
                if (getCurrentMediaPlayer().isPlaying()) {
                    processPauseRequest();
                    autoPaused = true;
                    Log.e(TAG, "Auto paused enabled");
                }
                break;
            }
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK: {
                if (getCurrentMediaPlayer() != null) {
                    getCurrentMediaPlayer().setVolume(0.3f, 0.3f);
                }
                break;
            }
            case AudioManager.AUDIOFOCUS_GAIN: {
                if (getCurrentMediaPlayer() != null) {
                    Log.e(TAG, "Auto-paused is " + ((autoPaused) ? "enabled" : "disabled"));
                    if (!getCurrentMediaPlayer().isPlaying() && autoPaused) {
                        processPlayPause();
                        Log.e(TAG, "Auto paused disabled");
                    }
                    getCurrentMediaPlayer().setVolume(1.0f, 1.0f);
                }
                break;
            }
        }
    }

    private boolean successfullyRetrievedAudioFocus() {
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        assert audioManager != null;
        int result = audioManager.requestAudioFocus(this,
                AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

        if (result != AudioManager.AUDIOFOCUS_GAIN) {
            Log.e(TAG, "Failed to gain AudioFocus");
        }

        return result != AudioManager.AUDIOFOCUS_GAIN;
    }

    private void setEqualizer() {
        boolean isEqInSettings = sharedPrefsUtils.readSharedPrefsBoolean("turnEqualizer", false);
        int currentEqProfile = sharedPrefsUtils.readSharedPrefsInt("currentEqProfile", 0);
        try {
            eq = new Equalizer(0, getCurrentMediaPlayer().getAudioSessionId());
            eq.setEnabled(isEqInSettings);
            for (int i = 0; i < eq.getNumberOfBands(); i++) {
                eq.setBandLevel((short) i, (short) sharedPrefsUtils.readSharedPrefsInt(
                        "profile" + currentEqProfile + "Band" + i, 0));
            }
            Log.e(TAG, "Equalizer successfully initiated with profile " + currentEqProfile);
        } catch (Exception e) {
            (new CommonUtils(this)).showTheToast("Unable to run Equalizer");
        }
        try {
            bassBoost = new BassBoost(0, getCurrentMediaPlayer().getAudioSessionId());
            bassBoost.setEnabled(isEqInSettings);
            bassBoost.setStrength((short) sharedPrefsUtils.readSharedPrefsInt("bassLevel" + currentEqProfile, 0));
        } catch (Exception ignored) {
        }
        try {
            virtualizer = new Virtualizer(0, getCurrentMediaPlayer().getAudioSessionId());
            virtualizer.setEnabled(isEqInSettings);
            virtualizer.setStrength((short) sharedPrefsUtils.readSharedPrefsInt("vzLevel" + currentEqProfile, 0));
        } catch (Exception ignored) {
        }
    }

    void addVoteToTrack(String path) {
        path = path.trim();
        try {
            CategorySongs categorySongs = new CategorySongs(getApplicationContext());
            categorySongs.open();
            if (categorySongs.checkRow(path)) {
                categorySongs.updateRow(path);
            } else {
                categorySongs.addRow(1, songsUtils.queue().get(songsUtils.getCurrentMusicID()));
            }
            categorySongs.close();
        } catch (Exception e) {
            Log.e(TAG, "addVoteToTrack crashed.");
        }
    }


    @Nullable
    @Override
    public BrowserRoot onGetRoot(@NonNull String clientPackageName, int clientUid, @Nullable Bundle rootHints) {
        if (TextUtils.equals(clientPackageName, getPackageName())) {
            return new BrowserRoot(getString(R.string.app_name), null);
        }

        return null;
    }

    @Override
    public void onLoadChildren(@NonNull String parentId, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result) {
        result.sendResult(null);
    }


    private void musicWidgetsReset() {
    }

    private void updateMusicWidget(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra("state", mPlaybackStateBuilder.build().getState());
        int[] ids = AppWidgetManager.getInstance(context)
                .getAppWidgetIds(new ComponentName(context, cls));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        context.sendBroadcast(intent);
    }

}
