package com.free.hdvideodownloaderapp.videodownloader.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;

import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.free.hdvideodownloaderapp.videodownloader.Adapter.free_BottomShitVideosList_adapter;
import com.free.hdvideodownloaderapp.videodownloader.Def.Common;
import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.AppPref;
import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.PlayerVideoModel;
import com.free.hdvideodownloaderapp.videodownloader.BuildConfig;
import com.free.hdvideodownloaderapp.videodownloader.Log;
import com.free.hdvideodownloaderapp.videodownloader.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.sdk.ads.ads.IntertitialAdsEvent;


import org.slf4j.Marker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class free_VideoPlayingActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;
    TextView appVideoBrightness;
    LinearLayout appVideoBrightnessBox;
    ImageView appVideoBrightnessIcon;
    TextView appVideoFastForward;
    TextView appVideoFastForwardAll;
    LinearLayout appVideoFastForwardBox;
    TextView appVideoFastForwardTarget;
    TextView appVideoVolume;
    LinearLayout appVideoVolumeBox;
    ImageView appVideoVolumeIcon;
    AtomicInteger atomicInteger;
    public WindowManager.LayoutParams attributes;
    public AudioManager audioManager;
    private ImageButton back;
    public BottomSheetDialog bottomSheetDialog;
    private float brightnesses = -1.0f;
    private float brightnessv;
    AlertDialog.Builder builder;
    private ImageButton crop;
    private TextView dspeed;
    //    private ImageButton equalizer;
    public boolean isPaused = false;
    int j;
    public ArrayList<PlayerVideoModel> list;
    ArrayList<PlayerVideoModel> list2;
    private ImageButton lock;
    public Boolean lockstatus = false;
    private int mMaxVolume;
    private ImageButton next;
    private PlaybackParameters parameters;
    public SimpleExoPlayer player;
    PlayerView playerView;
    private ImageButton playlist;
    private ImageButton popup;
    public int position;
    private ImageButton prev;
    private TextView pspeed;
    DisplayMetrics realDisplayMetrics;
    private ImageButton repeat;
    private ImageButton rotate;
    private ImageButton share;
    private float speedv;
    TextView text;
    public int time = 0;
    private Timer timer;
    public TextView title;
    public TextView tvolume;
    private ImageButton unlock;
    private int view = 0;
    private ImageButton volume;
    private int volumes = -1;
    public SeekBar vseekBar;
    public int width;

    private class PlayerGestureListener implements GestureDetector.OnGestureListener {
        int clickPos;
        private boolean firstTouch;
        private boolean toSeek;
        private boolean volumeControl;

        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return false;
        }

        public void onLongPress(MotionEvent motionEvent) {
        }

        public void onShowPress(MotionEvent motionEvent) {
        }

        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return false;
        }

        private PlayerGestureListener() {
            this.clickPos = 0;
        }

        public boolean onDown(MotionEvent motionEvent) {
            this.firstTouch = true;
            if (this.clickPos == 0) {
                this.clickPos = 1;
                free_VideoPlayingActivity.this.playerView.hideController();
            } else {
                this.clickPos = 0;
                free_VideoPlayingActivity.this.playerView.showController();
            }
            return true;
        }

        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            float x = motionEvent.getX();
            float y = motionEvent.getY() - motionEvent2.getY();
            float x2 = x - motionEvent2.getX();
            if (this.firstTouch) {
                this.toSeek = Math.abs(f) >= Math.abs(f2);
                this.volumeControl = x > ((float) free_VideoPlayingActivity.this.width) * 0.5f;
                this.firstTouch = false;
            }
            if (!this.toSeek) {
                float height = y / ((float) free_VideoPlayingActivity.this.playerView.getHeight());
                if (!free_VideoPlayingActivity.this.lockstatus.booleanValue()) {
                    if (this.volumeControl) {
                        free_VideoPlayingActivity.this.onVolumeSlide(height);
                    } else {
                        free_VideoPlayingActivity.this.onBrightnessSlide(height);
                    }
                }
            } else if (!free_VideoPlayingActivity.this.lockstatus.booleanValue()) {
                free_VideoPlayingActivity AVDVideoPlayingActivity = free_VideoPlayingActivity.this;
                AVDVideoPlayingActivity.onProgressSlide((-x2) / ((float) AVDVideoPlayingActivity.playerView.getWidth()));
            }
            return true;
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_video_playing);

        getWindow().setFlags(1024, 1024);

        
        IntertitialAdsEvent.CallInterstitial(this);
IntertitialAdsEvent.ShowInterstitialAdsOnCreate(this);


        this.playerView = (PlayerView) findViewById(R.id.player_view);
        this.title = (TextView) findViewById(R.id.title);
        this.rotate = (ImageButton) findViewById(R.id.rotate);
        this.lock = (ImageButton) findViewById(R.id.lock);
        this.volume = (ImageButton) findViewById(R.id.volume);
        this.unlock = (ImageButton) findViewById(R.id.unlock);
        this.crop = (ImageButton) findViewById(R.id.exo_crop);
        this.back = (ImageButton) findViewById(R.id.back);
        this.share = (ImageButton) findViewById(R.id.share);
        this.text = (TextView) findViewById(R.id.text);
        this.pspeed = (TextView) findViewById(R.id.pspeed);
        this.repeat = (ImageButton) findViewById(R.id.repeat);
        this.popup = (ImageButton) findViewById(R.id.popup);
        this.playlist = (ImageButton) findViewById(R.id.playlist);
//        this.equalizer = (ImageButton) findViewById(R.id.equalizer);
        this.next = (ImageButton) findViewById(R.id.next);
        this.prev = (ImageButton) findViewById(R.id.prev);
        this.appVideoVolumeIcon = (ImageView) findViewById(R.id.app_video_volume_icon);
        this.appVideoBrightnessIcon = (ImageView) findViewById(R.id.app_video_brightness_icon);
        this.appVideoBrightness = (TextView) findViewById(R.id.app_video_brightness);
        this.appVideoFastForward = (TextView) findViewById(R.id.app_video_fastForward);
        this.appVideoFastForwardAll = (TextView) findViewById(R.id.app_video_fastForward_all);
        this.appVideoFastForwardTarget = (TextView) findViewById(R.id.app_video_fastForward_target);
        this.appVideoVolume = (TextView) findViewById(R.id.app_video_volume);
        this.appVideoBrightnessBox = (LinearLayout) findViewById(R.id.app_video_brightness_box);
        this.appVideoFastForwardBox = (LinearLayout) findViewById(R.id.app_video_fastForward_box);
        this.appVideoVolumeBox = (LinearLayout) findViewById(R.id.app_video_volume_box);
        // TODO: 03-02-23

//        this.position = getIntent().getIntExtra("position", 0);
//        ArrayList<playerpro_Player_VideoModel> list3 =  getIntent().getParcelableArrayListExtra("list");



        this.position = Common.commonpos;
        ArrayList<PlayerVideoModel> list3 = (ArrayList<PlayerVideoModel>) Common.dataparse;


        this.list = list3;
        this.list2 = list3;
        this.j = list3.size();
        if (getResources().getConfiguration().orientation == 1) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        }
        init();
        setPlayer();
        setBottomSheet();
        setListner();
        setdata();
        this.player.addListener(new Player.EventListener() {
            public void onLoadingChanged(boolean z) {
            }

            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
            }

            public void onRepeatModeChanged(int i) {
            }

            public void onSeekProcessed() {
            }

            public void onShuffleModeEnabledChanged(boolean z) {
            }

            public void onTimelineChanged(Timeline timeline, Object obj, int i) {
            }

            public void onTracksChanged(TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
            }

            public void onPositionDiscontinuity(int i) {
                try {
                    int currentWindowIndex = free_VideoPlayingActivity.this.player.getCurrentWindowIndex();
                    if (currentWindowIndex != free_VideoPlayingActivity.this.position) {
                        free_VideoPlayingActivity AVDVideoPlayingActivity = free_VideoPlayingActivity.this;
                        AVDVideoPlayingActivity.position = currentWindowIndex;
                        AVDVideoPlayingActivity.title.setText(free_VideoPlayingActivity.this.list.get(currentWindowIndex).getDisplayName());
                        free_VideoPlayingActivity.this.bottomSheetDialog.dismiss();
                    }
                } catch (Exception unused) {
                }
            }

            public void onPlayerStateChanged(boolean z, int i) {
                if (z && i == 3) {
                    try {
                        free_VideoPlayingActivity.this.isPaused = false;
                    } catch (Exception unused) {
                    }
                } else if (z) {
                    free_VideoPlayingActivity.this.isPaused = false;
                } else {
                    free_VideoPlayingActivity.this.isPaused = true;
                }
            }

            public void onPlayerError(ExoPlaybackException exoPlaybackException) {
                free_VideoPlayingActivity.this.player.stop();
            }
        });
        final GestureDetector gestureDetector = new GestureDetector(this, new PlayerGestureListener());
        this.playerView.setClickable(true);
        this.playerView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (gestureDetector.onTouchEvent(motionEvent)) {
                    return true;
                }
                if ((motionEvent.getAction() & 255) != 1) {
                    return false;
                }
                free_VideoPlayingActivity.this.endGesture();
                return false;
            }
        });
        startTimer();
    }

    private void startTimer() {
        try {
            Timer timer2 = new Timer();
            this.timer = timer2;
            timer2.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    boolean z = free_VideoPlayingActivity.this.isPaused;
                    if (free_VideoPlayingActivity.this.time < 2) {
                        free_VideoPlayingActivity.this.time++;
                    }
                }
            }, 60000, 60000);
        } catch (Exception unused) {
        }
    }

    public void finish() {
        super.finish();
        try {
            Timer timer2 = this.timer;
            if (timer2 != null) {
                timer2.cancel();
                this.timer = null;
            }
        } catch (Exception unused) {
        }
    }

    public void onDestroy() {
        super.onDestroy();
        try {
            Timer timer2 = this.timer;
            if (timer2 != null) {
                timer2.cancel();
            }
        } catch (Exception unused) {
        }
    }

    @SuppressLint("WrongConstant")
    public void endGesture() {
        try {
            this.volumes = -1;
            this.brightnesses = -1.0f;
            this.appVideoBrightnessBox.setVisibility(8);
            this.appVideoVolumeBox.setVisibility(8);
            this.appVideoFastForwardBox.setVisibility(8);
        } catch (Exception unused) {
        }
    }

    private void setdata() {
        try {
            this.pspeed.setText(String.format("%sX", new Object[]{Float.valueOf(this.speedv)}));
            if (getResources().getConfiguration().orientation == 1) {
                this.width = this.realDisplayMetrics.widthPixels;
                titlepot();
            } else {
                this.width = this.realDisplayMetrics.heightPixels;
                titleland();
            }
            List<PlayerVideoModel> list3 = this.list;
            if (list3 != null) {
                this.title.setText(list3.get(this.position).getDisplayName());
            } else {
                this.title.setText(fileNameFromUri());
            }
        } catch (Exception unused) {
        }
    }

    @SuppressLint("Range")
    private String fileNameFromUri() {
        Cursor query;
        try {
            String scheme = getIntent().getData().getScheme();
            if (scheme.equals("file")) {
                return getIntent().getData().getLastPathSegment();
            }
            if (!scheme.equals("content") || (query = getContentResolver().query(getIntent().getData(), (String[]) null, (String) null, (String[]) null, (String) null)) == null || !query.moveToFirst()) {
                return "";
            }
            return query.getString(query.getColumnIndex("_display_name"));
        } catch (Exception unused) {
            return "";
        }
    }

    private void titlepot() {
        try {
            ViewGroup.LayoutParams layoutParams = this.title.getLayoutParams();
            layoutParams.width = this.width / 7;
            this.title.setLayoutParams(layoutParams);
        } catch (Exception unused) {
        }
    }

    private void titleland() {
        try {
            ViewGroup.LayoutParams layoutParams = this.title.getLayoutParams();
            layoutParams.width = this.width;
            this.title.setLayoutParams(layoutParams);
        } catch (Exception unused) {
        }
    }

    private void setListner() {
        try {
            this.back.setOnClickListener(this);
            this.share.setOnClickListener(this);
            this.lock.setOnClickListener(this);
            this.volume.setOnClickListener(this);
            this.unlock.setOnClickListener(this);
            this.crop.setOnClickListener(this);
            this.pspeed.setOnClickListener(this);
            this.repeat.setOnClickListener(this);
            this.rotate.setOnClickListener(this);
            this.popup.setOnClickListener(this);
            this.playlist.setOnClickListener(this);
            this.next.setOnClickListener(this);
            this.prev.setOnClickListener(this);
//            this.equalizer.setOnClickListener(this);
        } catch (Exception unused) {
        }
    }

    @SuppressLint("WrongConstant")
    private void setBottomSheet() {
        try {
            bottomSheetDialog = new BottomSheetDialog(this);
            View inflate = getLayoutInflater().inflate(R.layout.bottomshit_videolist, (ViewGroup) null);
            RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.r1);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.getLayoutManager().scrollToPosition(this.position);
            recyclerView.setAdapter(new free_BottomShitVideosList_adapter(this, this.list, this.player));
            this.bottomSheetDialog.setContentView(inflate);
        } catch (Exception unused) {
        }
    }

    private void setPlayer() {
        try {
            List<PlayerVideoModel> list3 = this.list;
            WindowManager.LayoutParams attributes2 = getWindow().getAttributes();
            this.attributes = attributes2;
            attributes2.screenBrightness = this.brightnessv;
            getWindow().setAttributes(this.attributes);
            SimpleExoPlayer newSimpleInstance = ExoPlayerFactory.newSimpleInstance((RenderersFactory) new DefaultRenderersFactory(getBaseContext()), (TrackSelector) new DefaultTrackSelector(), (LoadControl) new DefaultLoadControl());

            this.player = newSimpleInstance;
            newSimpleInstance.prepare(new ExtractorMediaSource.Factory(new DefaultDataSourceFactory(getBaseContext(), Util.getUserAgent(getBaseContext(), getBaseContext().getString(R.string.app_name)))).setExtractorsFactory(new DefaultExtractorsFactory()).createMediaSource(Uri.parse(list3.get(this.position).getData())));
            this.playerView.setPlayer(this.player);
        } catch (Exception unused) {
        }
    }

    @SuppressLint("WrongConstant")
    private void init() {
        try {
            this.brightnessv = AppPref.getInstance(this).getLastBrightness();
            Log.v("brightnessv", "" + this.brightnessv);
            this.audioManager = (AudioManager) getSystemService(MimeTypes.BASE_TYPE_AUDIO);
            this.speedv = AppPref.getInstance(this).getLastSpeed();
            Display defaultDisplay = getWindowManager().getDefaultDisplay();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            this.realDisplayMetrics = displayMetrics;
            defaultDisplay.getRealMetrics(displayMetrics);
            this.mMaxVolume = this.audioManager.getStreamMaxVolume(3);
        } catch (Exception unused) {
        }
    }

    @SuppressLint("WrongConstant")
    public void onClick(View view2) {
        try {
            if (view2 == this.back) {
                onBackPressed();
            } else if (view2 == this.share) {
                Uri uriForFile = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider", new File(Uri.parse(this.list.get(this.position).getData()).toString()));
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("video/*");
                intent.putExtra("android.intent.extra.STREAM", uriForFile);
                startActivity(Intent.createChooser(intent, "Share Sound File"));
                this.player.stop();
                finish();
            } else if (view2 == this.lock) {
                lock();
            } else if (view2 == this.volume) {
                Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.dialog_volume);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                dialog.getWindow().setLayout(-1, -2);
                dialog.getWindow().setGravity(17);
//                dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                this.tvolume = (TextView) dialog.findViewById(R.id.progress);
                this.vseekBar = (SeekBar) dialog.findViewById(R.id.seekBar);
                int streamVolume = this.audioManager.getStreamVolume(3);
                this.vseekBar.setMax(this.audioManager.getStreamMaxVolume(3));
                this.vseekBar.setProgress(streamVolume);
                this.tvolume.setText(Integer.toString(streamVolume));
                this.vseekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }

                    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                        seekBar.setProgress(i);
                        free_VideoPlayingActivity.this.tvolume.setText(Integer.toString(i));
                        free_VideoPlayingActivity.this.audioManager.setStreamVolume(3, i, 0);
                    }
                });
                dialog.show();
            } else if (view2 == this.next) {
                this.position++;
                nextt();
            } else if (view2 == this.prev) {
                this.position--;
                prevv();
            } else if (view2 == this.popup) {
            } else if (view2 == this.unlock) {
                unlock();
            } else if (view2 == this.crop) {
                int i = this.view;
                if (i == 0) {
                    this.playerView.setResizeMode(3);
                    this.view = 3;
                    this.crop.setImageResource(R.drawable.fit);
                } else if (i == 3) {
                    this.playerView.setResizeMode(4);
                    this.crop.setImageResource(R.drawable.zoom);
                    this.view = 4;
                } else if (i == 4) {
                    this.playerView.setResizeMode(0);
                    this.crop.setImageResource(R.drawable.full);
                    this.view = 0;
                }
            } else if (view2 == this.pspeed) {
                handlePsSpeed();
            } else if (view2 == this.repeat) {
                if (AppPref.getStart(getApplicationContext())) {
                    this.repeat.setImageResource(R.drawable.repeat);
                    this.player.setRepeatMode(0);
                    this.next.setImageResource(R.drawable.next);
                    this.prev.setImageResource(R.drawable.previous);
                    this.next.setClickable(true);
                    this.prev.setClickable(true);
                    AppPref.setStart(getApplicationContext(), false);
                    return;
                }
                this.repeat.setImageResource(R.drawable.repeatone);
                this.player.setRepeatMode(1);
                this.next.setImageResource(R.drawable.next);
                this.prev.setImageResource(R.drawable.previous);
                this.prev.setClickable(false);
                this.next.setClickable(false);
                AppPref.setStart(getApplicationContext(), true);
            } else if (view2 == this.rotate) {
                handleRotate();
            } else if (view2 == this.playlist) {
                this.bottomSheetDialog.show();
            }
//            else if (view2 == this.equalizer) {
//                int audioSessionId = this.player.getAudioSessionId();
//                Log.v("sessionId", "" + audioSessionId);
//                if (audioSessionId == 0) {
//                    Toast.makeText(this, "No Sound", 0).show();
//                } else {
//                    DialogEqualizerFragment.newBuilder().setAudioSessionId(audioSessionId).themeColor(ContextCompat.getColor(this, R.color.primaryColor)).textColor(ContextCompat.getColor(this, R.color.textColor)).accentAlpha(ContextCompat.getColor(this, R.color.playingCardColor)).darkColor(ContextCompat.getColor(this, R.color.primaryDarkColor)).setAccentColor(ContextCompat.getColor(this, R.color.secondaryColor)).build().show(getSupportFragmentManager(), "eq");
//                }
//            }
        } catch (Exception unused) {
        }
    }

    private void prevv() {
        try {
            if (this.position < 0) {
                this.player.stop();
                this.position = 0;
                SimpleExoPlayer newSimpleInstance = ExoPlayerFactory.newSimpleInstance((RenderersFactory) new DefaultRenderersFactory(getBaseContext()), (TrackSelector) new DefaultTrackSelector(), (LoadControl) new DefaultLoadControl());
                this.player = newSimpleInstance;
                newSimpleInstance.prepare(new ExtractorMediaSource.Factory(new DefaultDataSourceFactory(getBaseContext(), Util.getUserAgent(getBaseContext(), getBaseContext().getString(R.string.app_name)))).setExtractorsFactory(new DefaultExtractorsFactory()).createMediaSource(Uri.parse(this.list2.get(0).getData())));
                this.playerView.setPlayer(this.player);
                this.player.setPlayWhenReady(true);
                this.player.seekTo((long) this.position);
                return;
            }
            this.player.stop();
            SimpleExoPlayer newSimpleInstance2 = ExoPlayerFactory.newSimpleInstance((RenderersFactory) new DefaultRenderersFactory(getBaseContext()), (TrackSelector) new DefaultTrackSelector(), (LoadControl) new DefaultLoadControl());
            this.player = newSimpleInstance2;
            newSimpleInstance2.prepare(new ExtractorMediaSource.Factory(new DefaultDataSourceFactory(getBaseContext(), Util.getUserAgent(getBaseContext(), getBaseContext().getString(R.string.app_name)))).setExtractorsFactory(new DefaultExtractorsFactory()).createMediaSource(Uri.parse(this.list2.get(this.position).getData())));
            this.playerView.setPlayer(this.player);
            this.player.setPlayWhenReady(true);
            this.player.seekTo((long) this.position);
        } catch (Exception unused) {
        }
    }

    private void nextt() {
        try {
            if (this.position < this.j) {
                this.player.stop();
                SimpleExoPlayer newSimpleInstance = ExoPlayerFactory.newSimpleInstance((RenderersFactory) new DefaultRenderersFactory(getBaseContext()), (TrackSelector) new DefaultTrackSelector(), (LoadControl) new DefaultLoadControl());
                this.player = newSimpleInstance;
                newSimpleInstance.prepare(new ExtractorMediaSource.Factory(new DefaultDataSourceFactory(getBaseContext(), Util.getUserAgent(getBaseContext(), getBaseContext().getString(R.string.app_name)))).setExtractorsFactory(new DefaultExtractorsFactory()).createMediaSource(Uri.parse(this.list2.get(this.position).getData())));
                this.playerView.setPlayer(this.player);
                this.player.setPlayWhenReady(true);
                this.player.seekTo((long) this.position);
                return;
            }
            this.player.stop();
            finish();
        } catch (Exception unused) {
        }
    }


    @SuppressLint("WrongConstant")
    private void handleRotate() {
        try {
            if (getResources().getConfiguration().orientation == 1) {
                setRequestedOrientation(6);
            } else {
                setRequestedOrientation(1);
            }
        } catch (Exception unused) {
        }
    }

    private void handlePsSpeed() {
        try {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.player_speed_dialog);
            dialog.getWindow().setLayout(-1, -2);
            dialog.getWindow().setGravity(17);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            this.dspeed = (TextView) dialog.findViewById(R.id.dspeed);
            AtomicInteger atomicInteger2 = new AtomicInteger((int) (AppPref.getInstance(this).getLastSpeed() * 100.0f));
            this.atomicInteger = atomicInteger2;
            this.dspeed.setText(Integer.toString(atomicInteger2.get()));
            ((ImageButton) dialog.findViewById(R.id.sdown)).setOnClickListener(new View.OnClickListener() {
                public final void onClick(View view) {
                    free_VideoPlayingActivity AVDVideoPlayingActivity = free_VideoPlayingActivity.this;
                    AVDVideoPlayingActivity.lambda$handlePsSpeed$0$VideoPlayerActivity(AVDVideoPlayingActivity.atomicInteger, view);
                }
            });
            ((ImageButton) dialog.findViewById(R.id.sup)).setOnClickListener(new View.OnClickListener() {
                public final void onClick(View view) {
                    free_VideoPlayingActivity AVDVideoPlayingActivity = free_VideoPlayingActivity.this;
                    AVDVideoPlayingActivity.lambda$handlePsSpeed$1$VideoPlayerActivity(AVDVideoPlayingActivity.atomicInteger, view);
                }
            });
            dialog.show();
        } catch (Exception unused) {
        }
    }

    public void lambda$handlePsSpeed$0$VideoPlayerActivity(AtomicInteger atomicInteger2, View view2) {
        try {
            if (atomicInteger2.get() <= 100) {
                atomicInteger2.set(atomicInteger2.get() - 5);
                if (atomicInteger2.get() < 24) {
                    atomicInteger2.set(atomicInteger2.get() + 5);
                }
            } else {
                atomicInteger2.set(atomicInteger2.get() - 10);
            }
            setSpeed(atomicInteger2.get());
        } catch (Exception unused) {
        }
    }

    public void lambda$handlePsSpeed$1$VideoPlayerActivity(AtomicInteger atomicInteger2, View view2) {
        try {
            if (atomicInteger2.get() < 100) {
                atomicInteger2.set(atomicInteger2.get() + 5);
            } else {
                atomicInteger2.set(atomicInteger2.get() + 10);
                if (atomicInteger2.get() > 401) {
                    atomicInteger2.set(atomicInteger2.get() - 10);
                }
            }
            setSpeed(atomicInteger2.get());
        } catch (Exception unused) {
        }
    }

    private void setSpeed(int i) {
        try {
            this.dspeed.setText(Integer.toString(i));
            float f = ((float) i) / 100.0f;
            this.pspeed.setText(String.format("%sX", new Object[]{Float.valueOf(f)}));
            PlaybackParameters playbackParameters = new PlaybackParameters(f, 1.0f);
            this.parameters = playbackParameters;
            this.player.setPlaybackParameters(playbackParameters);
            AppPref.getInstance(getApplicationContext()).saveLastSpeed(f);
        } catch (Exception unused) {
        }
    }

    private void unlock() {
        try {
            this.lockstatus = Boolean.valueOf(!this.lockstatus.booleanValue());
            ((LinearLayout) findViewById(R.id.bottom_control)).setVisibility(View.VISIBLE);
            ((LinearLayout) findViewById(R.id.center_left_control)).setVisibility(View.VISIBLE);
            ((LinearLayout) findViewById(R.id.top_control)).setVisibility(View.VISIBLE);
            ((LinearLayout) findViewById(R.id.center_right_control)).setVisibility(View.VISIBLE);
            this.unlock.setVisibility(View.INVISIBLE);
            AppPref.getInstance(getApplicationContext()).setLock(false);
        } catch (Exception unused) {
        }
    }

    private void lock() {
        try {
            this.lockstatus = Boolean.valueOf(!this.lockstatus.booleanValue());
            ((LinearLayout) findViewById(R.id.bottom_control)).setVisibility(View.INVISIBLE);
            ((LinearLayout) findViewById(R.id.center_left_control)).setVisibility(View.INVISIBLE);
            ((LinearLayout) findViewById(R.id.top_control)).setVisibility(View.INVISIBLE);
            ((LinearLayout) findViewById(R.id.center_right_control)).setVisibility(View.INVISIBLE);
            this.unlock.setVisibility(View.VISIBLE);
            AppPref.getInstance(getApplicationContext()).setLock(true);
        } catch (Exception unused) {
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        try {
            int i = getResources().getConfiguration().orientation;
            if (i == 2) {
                titleland();
            } else if (i == 1) {
                titlepot();
            }
        } catch (Exception unused) {
        }
    }

    public void onPause() {
        try {
            if (this.lockstatus.booleanValue()) {
                unlock();
            }
        } catch (Exception unused) {
        }
        super.onPause();
    }

    public void onBackPressed() {
        if (!this.lockstatus.booleanValue()) {
            this.player.release();
//            Trans_Adx_Id.ShowInterstitialAdsOnBack(this);
        }
        IntertitialAdsEvent.ShowInterstitialAdsOnBack(this);


//        if (appinuse_or_appoutuse == 1) {
//
//            
//            IntertitialAdsEvent.ShowInterstitialAdsOnCreateWithCallBack(this, new InterCloseCallBack() {
//                @Override
//                public void onAdsClose() {
//                    startActivity(new Intent(VideoPlayingActivity.this, JK_Fragment_Tab_Activity.class).putExtra("type", "V"));
//                }
//
//
//            });
//
//        } else {
//            Trans_Adx_Id.ShowInterstitialAdsOnBack(this);
//        }
//    }

    }

    private void checkSetPer() {
        try {
            AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
            this.builder = builder2;
            builder2.setTitle("Need Permissions");
            this.builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
            this.builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
                public final void onClick(DialogInterface dialogInterface, int i) {
                    free_VideoPlayingActivity.this.lambda$checkSetPer$2$VideoPlayerActivity(dialogInterface, i);
                }
            });
            this.builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            this.builder.show();
        } catch (Exception unused) {
        }
    }

    public void lambda$checkSetPer$2$VideoPlayerActivity(DialogInterface dialogInterface, int i) {
        try {
            dialogInterface.cancel();
            openSettings();
        } catch (Exception unused) {
        }
    }

    private void openSettings() {
        Intent intent;
        try {
            if (Build.VERSION.SDK_INT >= 23) {
                intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse("package:" + getPackageName()));
            } else {
                intent = null;
            }
            startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);
        } catch (Exception unused) {
        }
    }


    public void onProgressSlide(float f) {
        String str;
        StringBuilder sb;
        try {
            long currentPosition = this.player.getCurrentPosition();
            Log.e("dywrhrjrj", "popup: " + currentPosition);
            long duration = this.player.getDuration();
            long min = (long) (((float) Math.min(100000, duration - currentPosition)) * f);
            long j2 = min + currentPosition;
            if (j2 > duration) {
                j2 = duration;
            } else if (j2 <= 0) {
                min = -currentPosition;
                j2 = 0;
            }
            int i = ((int) min) / 1000;
            if (i != 0) {
                this.appVideoFastForwardBox.setVisibility(View.VISIBLE);
                if (i > 0) {
                    sb = new StringBuilder();
                    str = Marker.ANY_NON_NULL_MARKER;
                } else {
                    sb = new StringBuilder();
                    str = "";
                }
                sb.append(str);
                sb.append(i);
                String sb2 = sb.toString();
                this.appVideoFastForward.setText(sb2 + "s");
                this.appVideoFastForwardTarget.setText(generateTime(j2) + "/");
                this.appVideoFastForwardAll.setText(generateTime(duration));
                this.player.seekTo(j2);
            }
        } catch (Exception unused) {
        }
    }

    private String generateTime(long j2) {
        String videoTime;
        int dur = (int) j2;
        int hrs = (dur / 3600000);
        int mns = (dur / 60000) % 60000;
        int scs = dur % 60000 / 1000;

        if (hrs > 0) {
            videoTime = String.format("%02d:%02d:%02d", hrs, mns, scs);
        } else {
            videoTime = String.format("%02d:%02d", mns, scs);
        }
        return videoTime;
    }

    public void onBrightnessSlide(float f) {
        try {
            if (this.brightnesses < 0.0f) {
                float f2 = getWindow().getAttributes().screenBrightness;
                this.brightnesses = f2;
                if (f2 <= 0.0f) {
                    this.brightnesses = 0.5f;
                } else if (f2 < 0.01f) {
                    this.brightnesses = 0.01f;
                }
            }
            this.appVideoBrightnessBox.setVisibility(View.VISIBLE);
            WindowManager.LayoutParams attributes2 = getWindow().getAttributes();
            attributes2.screenBrightness = this.brightnesses + f;
            if (attributes2.screenBrightness > 1.0f) {
                attributes2.screenBrightness = 1.0f;
            } else if (attributes2.screenBrightness < 0.01f) {
                attributes2.screenBrightness = 0.01f;
            }
            TextView textView = this.appVideoBrightness;
            textView.setText(((int) (attributes2.screenBrightness * 100.0f)) + "%");
            getWindow().setAttributes(attributes2);
            AppPref.getInstance(getApplicationContext()).saveLastBrightness(attributes2.screenBrightness);
        } catch (Exception unused) {
        }
    }

    public void onVolumeSlide(float f) {
        try {
            if (this.volumes == -1) {
                int streamVolume = this.audioManager.getStreamVolume(3);
                this.volumes = streamVolume;
                if (streamVolume < 0) {
                    this.volumes = 0;
                }
            }
            int i = this.mMaxVolume;
            int i2 = ((int) (f * ((float) i))) + this.volumes;
            if (i2 <= i) {
                i = i2 < 0 ? 0 : i2;
            }
            this.audioManager.setStreamVolume(3, i, 0);
            int i3 = (int) (((((double) i) * 1.0d) / ((double) this.mMaxVolume)) * 100.0d);
            String str = i3 + "%";
            if (i3 == 0) {
                str = "off";
            }
            this.appVideoVolumeIcon.setImageResource(i3 == 0 ? R.drawable.ic_volume_off_white_36dp : R.drawable.ic_volume_up_white_36dp);
            this.appVideoBrightnessBox.setVisibility(View.GONE);
            this.appVideoVolumeBox.setVisibility(View.VISIBLE);
            this.appVideoVolume.setVisibility(View.VISIBLE);
            this.appVideoVolume.setText(str);
        } catch (Exception unused) {
            Log.e("TAG", "onVolumeSlide: " + unused.getMessage());
        }
    }

    public void onResume() {
        super.onResume();
        try {
            AppPref.getStart(getApplicationContext());
        } catch (Exception unused) {
        }
    }
}
