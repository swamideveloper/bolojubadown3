package com.free.hdvideodownloaderapp.videodownloader.Social_Download.freeActivity;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Patterns;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.URLUtil;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

import com.anthonycr.progress.AnimatedProgressBar;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.free.hdvideodownloaderapp.videodownloader.Aico_Socket.Aico_VC_Manager;
import com.free.hdvideodownloaderapp.videodownloader.BuildConfig;
import com.free.hdvideodownloaderapp.videodownloader.Log;
import com.free.hdvideodownloaderapp.videodownloader.R;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.Configs.Adapters.freeAutoCompleteAdapter;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.Configs.SettingsManager;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.Grabber.AdBlocker;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.Grabber.ContentSearch;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.Grabber.CustomSearch;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.Grabber.URLAddFilter;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.Models.CustomGrabberModel;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.Models.File_type;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.Models.ResourceHolderModel;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.Models.SiteData;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.Models.downloadable_resource_model;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.activities.free_SC_StatusActivity;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.extra.Common;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.free_Utils.free_UCommons;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.free_Utils.free_UUtils;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.free__Statics.free_StaticVariables;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.popups.AvailableFilesDialog;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.sdk.ads.Comman;
import com.sdk.ads.ads.AllNativeAds;
import com.sdk.ads.ads.IntertitialAdsEvent;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class freeMainActivity extends AppCompatActivity implements View.OnClickListener {

    public static boolean flagj = false;
    public static int urlInterCounter_j = 0;
    public static int everyUrlClicksManager_j = 3;
    AnimatedProgressBar loadingPageProgress_j;
    CountDownTimer countDownTimer_j;
    AutoCompleteTextView et_search_bar_j;
    WebView simpleWebView;
    Context mContext;
    Timer timer = null;
    FloatingActionButton download_fab, download_images_close, download_video_fab, download_audio_fab, download_images_fab;
    FloatingActionButton reels;
    TextView download_video_fab_text, download_audio_fab_text, download_images_fab_text;
    Boolean isAllFabsVisible;
    ImageView btn_home, btn_search, btn_search_cancel, btn_settings;
    AvailableFilesDialog _available_files_dialog;
    AppUpdateManager mAppUpdateManager;
    NestedScrollView mainLayout;
    CoordinatorLayout coordinator;
    ArrayList<SiteData> siteData = new ArrayList<>();
    RelativeLayout facebook_button;
    RelativeLayout twitter_button;
    RelativeLayout gag9;
    RelativeLayout imdb_button;
    RelativeLayout sharechat_button;
    RelativeLayout tumblr_button;
    RelativeLayout bitchute_button;
    RelativeLayout ifunny_button;
    RelativeLayout imgur_button;
    RelativeLayout reddit_button;
    RelativeLayout rumble_button;
    RelativeLayout vlipsy_button;
    Animation fadeIn;
    Animation fadeout;
    LinearLayout download;
    LinearLayout whatsapp;
    BottomNavigationView navigationView;
    private AdBlocker adBlock;
    private SSLSocketFactory defaultSSLSF;
    private boolean isRedirected;
    LinearLayout llHowToDownload;
    LinearLayout llFeedback;
    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        havePermissionForWriteStorage();
        ads();


        siteData.add(new SiteData("facebook", "https://facebook.com"));
        siteData.add(new SiteData("twitter", "https://twitter.com"));
        siteData.add(new SiteData("imdb", "https://www.imdb.com/"));
        siteData.add(new SiteData("sharechat", "https://www.sharechat.com/"));
        siteData.add(new SiteData("tumblr", "https://www.tumblr.com/"));
        siteData.add(new SiteData("bitchute", "https://www.bitchute.com/"));
        siteData.add(new SiteData("ifunny", "https://ifunny.co/"));
        siteData.add(new SiteData("imgur", "https://imgur.com/"));
        siteData.add(new SiteData("reddit", "https://reddit.com/"));
        siteData.add(new SiteData("rumble", "https://rumble.com/"));
        siteData.add(new SiteData("9gag", "https://9gag.com"));
        siteData.add(new SiteData("vlipsy", "https://vlipsy.com"));

        fadeIn = AnimationUtils.loadAnimation(freeMainActivity.this, R.anim.fade_in);

        fadeout = AnimationUtils.loadAnimation(freeMainActivity.this, R.anim.fade_out);

        SharedPreferences.Editor editor = getSharedPreferences("IS_EXTRA_SCREEN_VISITED", MODE_PRIVATE).edit();
        editor.putBoolean("is_extra_screen_visited", true);
        editor.apply();

        mContext = this;
        init_components();
        defaultSSLSF = HttpsURLConnection.getDefaultSSLSocketFactory();
        set_button_click_events();
        wv_go_to_home();
        disable_fab_button();
        disableCenterItem();

//        reels.setVisibility(View.GONE);

        if (restorePrefData()) {
            llHowToDownload.setVisibility(View.GONE);
        } else {
            llHowToDownload.setVisibility(View.VISIBLE);
        }

        navigationView = findViewById(R.id.bottomNavigationView);
        navigationView.setOnNavigationItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) item -> {
            switch (item.getItemId()) {
                case R.id.nav_download:
                    Intent intent = new Intent(this, freeDownloadsActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_status_saver:
                    try {
                        Intent intent1 = new Intent(freeMainActivity.this, free_SC_StatusActivity.class);
                        startActivity(intent1);
                    } catch (Exception e) {
                        Toast.makeText(mContext, "Your device can't support this service!", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }

            if (item.isChecked()) {
                item.setChecked(false);
            } else {
                item.setChecked(true);
            }

            return false;
        });

        facebook_button.setOnClickListener(this);
        twitter_button.setOnClickListener(this);
        gag9.setOnClickListener(this);
        imdb_button.setOnClickListener(this);
        sharechat_button.setOnClickListener(this);
        tumblr_button.setOnClickListener(this);
        bitchute_button.setOnClickListener(this);
        ifunny_button.setOnClickListener(this);
        imgur_button.setOnClickListener(this);
        reddit_button.setOnClickListener(this);
        rumble_button.setOnClickListener(this);
        vlipsy_button.setOnClickListener(this);

        et_search_bar_j.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0)
                    btn_search_cancel.setVisibility(View.GONE);
                else
                    btn_search_cancel.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        try {
            onSharedIntent();
        } catch (Exception ex) {
        }
        checkAppUpdate();

        Intent appLinkIntent = getIntent();
        Uri appLinkData = appLinkIntent.getData();
        if (appLinkData != null) {
            et_search_bar_j.setText(appLinkData.toString());
            navigate_browser();
        }
        PrepareForAdBlockers();

        simpleWebView.setDownloadListener((url, userAgent, contentDisposition, mimetype, contentLength) -> {
            try {
                if (!url.startsWith("blob:")) {

                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

                    request.setMimeType(mimetype);
                    String cookies = CookieManager.getInstance().getCookie(url);
                    request.addRequestHeader("cookie", cookies);
                    request.addRequestHeader("User-Agent", userAgent);
                    request.setDescription("Downloading file...");
                    request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimetype));
                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, contentDisposition, mimetype));
                    DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                    dm.enqueue(request);
                    Toast.makeText(getApplicationContext(), "Downloading File", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

    }

    private void ads() {


        if (getIntent().getBooleanExtra("show", false)) {
            IntertitialAdsEvent.CallInterstitial(this);
            IntertitialAdsEvent.ShowInterstitialAdsOnCreate(this);
        }

        AllNativeAds.NativeAds(this, findViewById(R.id.adsContainer1));
        AllNativeAds.NativeBanner(this, findViewById(R.id.adsContainer));

    }

    private void onSharedIntent() {
        Intent receiverdIntent = getIntent();
        String receivedAction = receiverdIntent.getAction();
        String receivedType = receiverdIntent.getType();
        if (receivedAction.equals(Intent.ACTION_SEND)) {
            if (receivedType.startsWith("text/")) {
                String receivedText = receiverdIntent
                        .getStringExtra(Intent.EXTRA_TEXT);
                if (receivedText != null) {
                    CheckUrls(receivedText);
                }
            }
        }
    }

    private void CheckUrls(String text) {
        List<String> result = free_UCommons.extractUrls(text);
        if (result.size() == 0) {
            new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText(mContext.getString(R.string.Wait))
                    .setContentText(mContext.getString(R.string.NoUrlFound))
                    .show();
        } else {
            et_search_bar_j.setText(result.get(0));
            navigate_browser();
        }
    }

    private void PrepareForAdBlockers() {

        File file = new File(mContext.getFilesDir(), "ad_filters.dat");
        try {
            if (file.exists()) {
                FileInputStream fileInputStream = new FileInputStream(file);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                adBlock = (AdBlocker) objectInputStream.readObject();
                objectInputStream.close();
                fileInputStream.close();
            } else {
                adBlock = new AdBlocker();
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(adBlock);
                objectOutputStream.close();
                fileOutputStream.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            adBlock = new AdBlocker();
        }
        updateAdFilters();
    }

    private boolean havePermissionForWriteStorage() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                }
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 950);
                return false;
            } else {
                initFolers();
                return true;
            }
        } else {
            initFolers();
            return true;
        }
    }


    public void updateAdFilters() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                adBlock.update(mContext);
            }
        });
    }

    public boolean checkUrlIfAds(String url) {
        return adBlock.checkThroughFilters(url);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 950:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initFolers();
                } else {
                    havePermissionForWriteStorage();
                }
                break;
        }
    }


    private void initFolers() {
        try {
            mkdirs(new File(SettingsManager.DOWNLOAD_FOLDER));
            mkdirs(new File(SettingsManager.DOWNLOAD_FOLDER_IMAGES));
            mkdirs(new File(SettingsManager.DOWNLOAD_FOLDER_AUDIO));
            mkdirs(new File(SettingsManager.DOWNLOAD_FOLDER_VIDEO));
        } catch (Exception ex) {

        }
    }

    private void mkdirs(File _dir) {
        if (!_dir.exists())
            _dir.mkdir();
    }

    private void enable_fab_button() {
        download_fab.setEnabled(true);
        download_fab.setClickable(true);
    }

    private void enable_audio_fab() {
        enable_fab_button();
        download_audio_fab.setEnabled(true);
        download_audio_fab.setClickable(true);
    }

    private void enable_video_fab() {
        enable_fab_button();
    }

    private void enable_images_fab() {
        enable_fab_button();
        download_images_fab.setEnabled(true);
        download_images_fab.setClickable(true);
    }

    private void update_audio_fab_text() {
        try {
            download_audio_fab_text.setText(free_StaticVariables.resourse_holder.getAudio_files().size() + "");
        } catch (Exception ex) {
        }
    }

    private void update_image_fab_text() {
        try {
            download_images_fab_text.setText(free_StaticVariables.resourse_holder.getImage_files().size() + "");
        } catch (Exception e) {
        }
    }

    private void update_video_fab_text() {
        try {
            download_video_fab_text.setText(free_StaticVariables.resourse_holder.getVideo_files().size() + "");
        } catch (Exception ex) {

        }
    }

    private void disable_fab_button() {
        if (isAllFabsVisible) {
            toggle_fab_buttons();
        }
        download_fab.setEnabled(true);
        download_fab.setClickable(true);
        download_audio_fab_text.setText("0");
        download_video_fab_text.setText("0");
        download_images_fab_text.setText("0");
        download_audio_fab.setEnabled(true);
        download_images_fab.setEnabled(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void wv_go_to_home() {

        if (Comman.mainResModel.getData().getExtraFields().getVideo_call().equalsIgnoreCase("on")) {
            findViewById(R.id.ic_reels).setVisibility(View.VISIBLE);

        } else {
            findViewById(R.id.ic_reels).setVisibility(View.INVISIBLE);
        }


        simpleWebView.onPause();
        simpleWebView.stopLoading();
        WebStorage.getInstance().deleteAllData();
        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookies(null);
        cookieManager.flush();
        simpleWebView.clearHistory();
        simpleWebView.clearFormData();
        simpleWebView.clearSslPreferences();
        simpleWebView.clearCache(true);
        mainLayout.startAnimation(fadeIn);
        mainLayout.setVisibility(View.VISIBLE);
        coordinator.setVisibility(View.VISIBLE);
        simpleWebView.setVisibility(View.GONE);
        btn_home.setImageResource(R.drawable.home);
        disable_fab_button();
        et_search_bar_j.setText("");
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init_components() {
        loadingPageProgress_j = findViewById(R.id.loadingPageProgress);
        simpleWebView = findViewById(R.id.simpleWebView);
        simpleWebView.getSettings().setJavaScriptEnabled(true);
        simpleWebView.getSettings().setDomStorageEnabled(true);
        simpleWebView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            simpleWebView.getSettings().setSafeBrowsingEnabled(false);
        }
        simpleWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        simpleWebView.setWebViewClient(new customWebClient());

        et_search_bar_j = findViewById(R.id.et_search_bar);
        int layout = android.R.layout.simple_list_item_1;
        freeAutoCompleteAdapter adapter = new freeAutoCompleteAdapter(mContext, layout);
        et_search_bar_j.setAdapter(adapter);

        download_fab = findViewById(R.id.download_fab);
        download_images_close = findViewById(R.id.download_images_close);
        download_fab.bringToFront();
        download_video_fab = findViewById(R.id.download_video_fab);
        download_video_fab.bringToFront();

        download_audio_fab = findViewById(R.id.download_audio_fab);
        download_audio_fab.bringToFront();

        download_images_fab = findViewById(R.id.download_images_fab);
        download_images_fab.bringToFront();

        download_video_fab_text = findViewById(R.id.download_video_fab_text);
        download_audio_fab_text = findViewById(R.id.download_audio_fab_text);
        download_images_fab_text = findViewById(R.id.download_images_fab_text);

        btn_home = findViewById(R.id.btn_home);
        btn_search_cancel = findViewById(R.id.btn_search_cancel);
        btn_search = findViewById(R.id.btn_search);
        btn_settings = findViewById(R.id.btn_settings);

        mainLayout = findViewById(R.id.MainLayout);
        coordinator = findViewById(R.id.coordinator_1);
        llHowToDownload = findViewById(R.id.ll_how_to_download);
        llFeedback = findViewById(R.id.ll_feedback);
        llFeedback.setClickable(true);
        download = findViewById(R.id.ll_download);
        whatsapp = findViewById(R.id.ll_whatsapp);
        reels = findViewById(R.id.reel_fab);

        Common.Animation(llFeedback);
        Common.Animation(llHowToDownload);
        if (flagj == false) {

        } else {
            llHowToDownload.setVisibility(View.GONE);
        }
        facebook_button = findViewById(R.id.facebook_button);
        twitter_button = findViewById(R.id.twitter_button);
        gag9 = findViewById(R.id.gag9);
        imdb_button = findViewById(R.id.imdb_button);
        sharechat_button = findViewById(R.id.shareChat_button);
        tumblr_button = findViewById(R.id.tumblr_button);
        bitchute_button = findViewById(R.id.bitchute_button);
        ifunny_button = findViewById(R.id.ifunny_button);
        imgur_button = findViewById(R.id.imgur_button);
        reddit_button = findViewById(R.id.reddit_button);
        rumble_button = findViewById(R.id.rumble_button);
        vlipsy_button = findViewById(R.id.vlipsy_button);


        registerForContextMenu(btn_settings);
        reels.setOnClickListener(v -> findViewById(R.id.ic_reels).performClick());
        llHowToDownload.setOnClickListener(view -> {
            if (flagj == false) {

            } else {
                llHowToDownload.setVisibility(View.GONE);
            }
            Intent intent = new Intent(this, freeIntroActivity.class);
            startActivity(intent);
        });

        llFeedback.setOnClickListener(view -> {
            llFeedback.setClickable(false);
            Intent intent = new Intent(this, freeFeedbackActivity.class);
            startActivity(intent);
        });

        download.setOnClickListener(view -> {
            Intent intent = new Intent(this, freeDownloadsActivity.class);
            startActivity(intent);
        });

        whatsapp.setOnClickListener(view -> {
            Intent intent = new Intent(this, freeDownloadsActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.ic_reels).setOnClickListener(view -> {

            if (Comman.mainResModel.getData().getExtraFields().getVideo_call().equalsIgnoreCase("on")) {


                if (isStoragePermissionGranted(0)) {

                    startActivity(new Intent(freeMainActivity.this, Aico_VC_Manager.class));
                } else {
                    Toast.makeText(mContext, "Camera Permission Required for use this service!", Toast.LENGTH_SHORT).show();
                }


            } else {
                Toast.makeText(mContext, "Video Not Available!!", Toast.LENGTH_SHORT).show();
            }


        });

        isAllFabsVisible = false;

        findViewById(R.id.download_images_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                download_video_fab.hide();
                download_images_close.hide();
                download_audio_fab.hide();
                download_images_fab.hide();
                download_video_fab_text.setVisibility(View.GONE);
                download_audio_fab_text.setVisibility(View.GONE);
                download_images_fab_text.setVisibility(View.GONE);
                isAllFabsVisible = false;

            }
        });
    }


    @Override
    public void onCreateContextMenu(
            ContextMenu menu,
            View v,
            ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
    }

    private void toggle_fab_buttons() {
        if (!isAllFabsVisible) {
            download_video_fab.show();
            download_images_close.show();
            download_audio_fab.show();
            download_images_fab.show();
            download_video_fab_text.setVisibility(View.VISIBLE);
            download_audio_fab_text.setVisibility(View.VISIBLE);
            download_images_fab_text.setVisibility(View.VISIBLE);
            isAllFabsVisible = true;
        } else {
            download_video_fab.hide();
            download_images_close.hide();
            download_audio_fab.hide();
            download_images_fab.hide();
            download_video_fab_text.setVisibility(View.GONE);
            download_audio_fab_text.setVisibility(View.GONE);
            download_images_fab_text.setVisibility(View.GONE);
            isAllFabsVisible = false;
        }
    }

    public boolean isStoragePermissionGranted(int request) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
                //Log.v("TAG", "Permission is granted");
                return true;
            } else {
                //Log.v("TAG", "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.CAMERA
                        , Manifest.permission.INTERNET}, request);
                return false;
            }
        } else {
            //Log.v("TAG", "Permission is granted");
            return true;
        }
    }

    private void set_button_click_events() {
        download_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle_fab_buttons();
            }
        });

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mainLayout.getVisibility() == View.GONE)
                    wv_go_to_home();
                else {
                    simpleWebView.onResume();
                    simpleWebView.loadUrl("www.google.com");
                    simpleWebView.startAnimation(fadeIn);
                    simpleWebView.setVisibility(View.VISIBLE);
                    btn_home.setImageResource(R.drawable.home);
                    mainLayout.setVisibility(View.GONE);
                    coordinator.setVisibility(View.GONE);
                    findViewById(R.id.ic_reels).setVisibility(View.VISIBLE);
                }
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigate_browser();
            }
        });

        btn_search_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_searchbar_text("");
            }
        });

        et_search_bar_j.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus && et_search_bar_j.getText().toString().equals(getResources().getString(R.string.home))) {
                    set_searchbar_text("");
                }
            }
        });

        et_search_bar_j.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_ENTER:
                            navigate_browser();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        download_video_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _available_files_dialog = new AvailableFilesDialog(File_type.VIDEO);
                _available_files_dialog.show(getSupportFragmentManager(), "Videos");
            }
        });

        download_images_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _available_files_dialog = new AvailableFilesDialog(File_type.IMAGE);
                _available_files_dialog.show(getSupportFragmentManager(), "Images");
            }
        });

        download_audio_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _available_files_dialog = new AvailableFilesDialog(File_type.AUDIO);
                _available_files_dialog.show(getSupportFragmentManager(), "Audios");
            }
        });

        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    btn_settings.showContextMenu(0, 0);
                } else {
                    btn_settings.showContextMenu();
                }
            }
        });

        et_search_bar_j.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                navigate_browser();
            }
        });
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(this.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void findLocal() {
        if (et_search_bar_j.getText().toString().contains("facebook.com")) {
            simpleWebView.evaluateJavascript("(function() {return document.getElementsByTagName('html')[0].outerHTML;})();",
                    new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String html) {

                            try {
                                if (html != null) {
                                    JsonReader reader = new JsonReader(new StringReader(html));
                                    reader.setLenient(true);
                                    try {
                                        if (reader.peek() == JsonToken.STRING) {
                                            String domStr = reader.nextString();
                                            if (domStr != null) {
                                                Document document = Jsoup.parse(domStr);
                                                String videoUrl = document.select("meta[property=\"og:video\"]").last().attr("content");
                                                ArrayList<downloadable_resource_model> video_files = new ArrayList<>();
                                                free_StaticVariables.resourse_holder.setVideo_files(video_files);
                                                free_StaticVariables.resourse_holder.add_Video(null, "video", videoUrl, "Video", "page");
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        update_image_fab_text();
                                                        enable_Buttons();
                                                    }
                                                });

                                            }
                                        }
                                    } catch (IOException e) {
                                    } finally {
                                    }
                                }

                            } catch (Exception ex) {
                            }
                        }
                    });
        }

        String[] customised_searches_filters = mContext.getResources().getStringArray(R.array.customised_searches);
        boolean MatchCustomSearch = false;
        for (String filter : customised_searches_filters) {
            if (et_search_bar_j.getText().toString().contains(filter)) {
                MatchCustomSearch = true;
            }
        }
        if (MatchCustomSearch) {
            simpleWebView.evaluateJavascript("(function() {return document.getElementsByTagName('html')[0].outerHTML;})();",
                    new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String html) {
                            List<CustomGrabberModel> customGrabberModelList = CustomSearch.Search(mContext, et_search_bar_j.getText().toString(), html);
                            if (customGrabberModelList.size() > 0) {
                                free_StaticVariables.resourse_holder = new ResourceHolderModel();
                            }
                            for (CustomGrabberModel customGrabberModel : customGrabberModelList) {
                                if (customGrabberModel.getVideoUrl() != null && customGrabberModel.getVideoUrl() != "") {
                                    if (customGrabberModel.getM3u8()) {
                                        free_StaticVariables.resourse_holder.add_Video(null, "m3u8", customGrabberModel.getVideoUrl(), "Video", "page");
                                    } else {
                                        if (free_StaticVariables.resourse_holder == null) {
                                            free_StaticVariables.resourse_holder = new ResourceHolderModel();
                                        }
                                        free_StaticVariables.resourse_holder.add_Video(null, "video", customGrabberModel.getVideoUrl(), "Video", "page");
                                    }
                                }
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    update_image_fab_text();
                                    enable_Buttons();
                                }
                            });
                        }
                    });
        }

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_pp: {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://pazabiullapolicy.blogspot.com/2023/07/privacy-policy.html"));
                startActivity(intent);
                return true;
            }

            case R.id.action_help: {
                if (flagj == false) {

                } else {
                    llHowToDownload.setVisibility(View.GONE);
                }
                Intent intent = new Intent(this, freeIntroActivity.class);
                startActivity(intent);
                return true;
            }
            case R.id.action_share_app: {
                Intent _myIntent = new Intent(Intent.ACTION_SEND);
                _myIntent.setType("text/plain");
                String ShareBody = "Hi \nPlease check this Awesome Application. '" + getResources().getString(R.string.app_name) + "'\nYou'll love it. \n\nhttps://play.google.com/store/apps/details?id=" + getPackageName();
                String ShareSub = getString(R.string.hithere);
                _myIntent.putExtra(Intent.EXTRA_SUBJECT, ShareSub);
                _myIntent.putExtra(Intent.EXTRA_TEXT, ShareBody);
                startActivity(Intent.createChooser(_myIntent, getString(R.string.Shareusing)));
                return true;
            }
        }
        return true;
    }

    private void navigate_browser() {
        simpleWebView.onResume();
        mainLayout.setVisibility(View.GONE);
        coordinator.setVisibility(View.GONE);
        simpleWebView.startAnimation(fadeIn);
        simpleWebView.setVisibility(View.VISIBLE);
        btn_home.setImageResource(R.drawable.home);
        hideKeyboard();
        if (!Patterns.WEB_URL.matcher(et_search_bar_j.getText()).matches()) {
            et_search_bar_j.setText("https://www.google.com/search?q=" + et_search_bar_j.getText());
        }
        Log.e("564545", "navigate_browser: ");
        simpleWebView.loadUrl(et_search_bar_j.getText().toString());
    }

    private void startTimer() {
        final int secs = 10;
        disable_fab_button();
        loadingPageProgress_j.setProgress(0);
        countDownTimer_j = new CountDownTimer((secs + 1) * 100, 1000) {
            @Override
            public final void onTick(final long millisUntilFinished) {
                if (loadingPageProgress_j.getProgress() < 80) {
                    loadingPageProgress_j.setProgress(loadingPageProgress_j.getProgress() + 8);
                }
            }

            @Override
            public final void onFinish() {
            }
        };
        countDownTimer_j.start();
        loadingPageProgress_j.setVisibility(View.VISIBLE);
    }

    private void stopTimer() {
        try {
            countDownTimer_j.cancel();
        } catch (Exception ex) {
        }
        loadingPageProgress_j.setProgress(100);
        new Handler(Looper.myLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingPageProgress_j.setProgress(0);
                loadingPageProgress_j.setVisibility(View.GONE);
            }
        }, 500);
    }

    private void set_searchbar_text(String text) {
        if (text.equals(getResources().getString(R.string.index_page)) || text.equals("about:blank")) {
            text = "";
        } else if (text.equals("")) {
            btn_search_cancel.setVisibility(View.INVISIBLE);
            et_search_bar_j.requestFocus();
        } else {
            btn_search_cancel.setVisibility(View.VISIBLE);
            et_search_bar_j.clearFocus();
        }
        et_search_bar_j.setText(text);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.facebook_button:
                Loadurl("facebook");
                break;
            case R.id.twitter_button:
                Loadurl("twitter");
                break;
            case R.id.gag9:
                Loadurl("9gag");
                break;
            case R.id.imdb_button:
                Loadurl("imdb");
                break;
            case R.id.shareChat_button:
                Loadurl("sharechat");
                break;
            case R.id.tumblr_button:
                Loadurl("tumblr");
                break;
            case R.id.bitchute_button:
                Loadurl("bitchute");
                break;
            case R.id.ifunny_button:
                Loadurl("ifunny");
                break;
            case R.id.imgur_button:
                Loadurl("imgur");
                break;
            case R.id.reddit_button:
                Loadurl("reddit");
                break;
            case R.id.rumble_button:
                Loadurl("rumble");
                break;
            case R.id.vlipsy_button:
                Loadurl("vlipsy");
                break;
        }
    }

    private void enable_Buttons() {
        if ((free_StaticVariables.resourse_holder.getVideo_files().size() > 0) || (free_StaticVariables.resourse_holder.getAudio_files().size() > 0) || (free_StaticVariables.resourse_holder.getImage_files().size() > 0)) {
            enable_audio_fab();
            enable_fab_button();
        }
        if ((free_StaticVariables.resourse_holder.getVideo_files().size() > 0)) {
            enable_video_fab();
            update_video_fab_text();
        }
        if ((free_StaticVariables.resourse_holder.getImage_files().size() > 0)) {
            enable_images_fab();
            update_video_fab_text();
        }
    }

    public void popupSnackbarForCompleteUpdate() {
        try {
            Snackbar make = Snackbar.make(findViewById(R.id.simpleWebView), (CharSequence) "An update has just been downloaded.", 2);
            make.setAction((CharSequence) "RESTART", (View.OnClickListener) new View.OnClickListener() {
                public void onClick(View view) {
                    if (mAppUpdateManager != null) {
                        mAppUpdateManager.completeUpdate();
                    }
                }
            });
            make.setDuration(50000);
            make.show();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    private void checkAppUpdate() {
        try {
            mAppUpdateManager = AppUpdateManagerFactory.create(this);
            mAppUpdateManager.registerListener(this.installStateUpdatedListener);
            mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
                public void onSuccess(AppUpdateInfo appUpdateInfo) {
                    if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                        try {
                            freeMainActivity.this.mAppUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.FLEXIBLE, freeMainActivity.this, 201);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                        }
                    } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                        freeMainActivity.this.popupSnackbarForCompleteUpdate();
                    } else {
                        Log.e("MainActivity", "checkForAppUpdateAvailability: something else");
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //back------------------------------------------------------------------------------------------
    boolean doubleBackToExitPressedOnce = false;


    @Override
    public void onBackPressed() {
//        MoreAppUtils.exitDialog(JK_MainActivity.this);

        if (simpleWebView.copyBackForwardList().getCurrentIndex() > 0 && simpleWebView.getVisibility() != View.GONE) {
            simpleWebView.goBack();
            et_search_bar_j.setText(simpleWebView.getUrl());
        } else if (simpleWebView.getVisibility() == View.VISIBLE) {

            wv_go_to_home();

        } else {

            IntertitialAdsEvent.ShowInterstitialAdsOnBack(this);

        }
    }


    private void Loadurl(String name) {

        IntertitialAdsEvent.CallInterstitial(this);
        IntertitialAdsEvent.ShowInterstitialAdsOnCreate(this);

        findViewById(R.id.ic_reels).setVisibility(View.GONE);
        if (urlInterCounter_j % everyUrlClicksManager_j == 0) {
            for (SiteData studentObj : siteData) {
                if (studentObj.getName().equalsIgnoreCase(name)) {
                    simpleWebView.onResume();
                    simpleWebView.loadUrl(studentObj.getLink());
                    simpleWebView.setVisibility(View.VISIBLE);
                    btn_home.setImageResource(R.drawable.home);
                    et_search_bar_j.setText(studentObj.getLink());
                    mainLayout.setVisibility(View.GONE);
                    coordinator.setVisibility(View.GONE);
                }
            }

        } else {
            for (SiteData studentObj : siteData) {
                if (studentObj.getName().equalsIgnoreCase(name)) {
                    simpleWebView.onResume();
                    simpleWebView.loadUrl(studentObj.getLink());
                    simpleWebView.setVisibility(View.VISIBLE);
                    btn_home.setImageResource(R.drawable.home);
                    et_search_bar_j.setText(studentObj.getLink());
                    mainLayout.setVisibility(View.GONE);
                    coordinator.setVisibility(View.GONE);
                }
            }
        }
        urlInterCounter_j++;

    }

    @Override
    public void onDestroy() {
        if (simpleWebView != null) {
            simpleWebView.clearHistory();
            simpleWebView.clearView();
            simpleWebView.clearCache(true);
            simpleWebView.destroy();
        }
        super.onDestroy();
    }

    InstallStateUpdatedListener installStateUpdatedListener = new InstallStateUpdatedListener() {
        public void onStateUpdate(InstallState installState) {
            try {
                if (installState.installStatus() == InstallStatus.DOWNLOADED) {
                    freeMainActivity.this.popupSnackbarForCompleteUpdate();
                } else if (installState.installStatus() != InstallStatus.INSTALLED) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("InstallStateUpdatedListener: state: ");
                    sb.append(installState.installStatus());
                } else if (freeMainActivity.this.mAppUpdateManager != null) {
                    freeMainActivity.this.mAppUpdateManager.unregisterListener(freeMainActivity.this.installStateUpdatedListener);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void disableCenterItem() {
        BottomNavigationView navView = findViewById(R.id.bottomNavigationView);
        Menu menuNav = navView.getMenu();
        MenuItem placeHolderItem = menuNav.findItem(R.id.placeholder);
        placeHolderItem.setEnabled(false);
    }

    public class customWebClient extends WebViewClient {
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String request) {
            String url = request;
            if ((url.contains("ad") || url.contains("banner") || url.contains("pop")) && checkUrlIfAds(url)) {
                return new WebResourceResponse(null, null, null);
            }
            return super.shouldInterceptRequest(view, request);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            if (url.startsWith("intent://")) {
                try {
                    Context context = simpleWebView.getContext();
                    Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                    if (intent != null) {
                        PackageManager packageManager = context.getPackageManager();
                        ResolveInfo info = packageManager.resolveActivity(intent,
                                PackageManager.MATCH_DEFAULT_ONLY);
                        if ((intent != null) && ((intent.getScheme().equals("https"))
                                || (intent.getScheme().equals("http")))) {
                            String fallbackUrl = intent.getStringExtra(
                                    "browser_fallback_url");
                            simpleWebView.loadUrl(fallbackUrl);
                            return true;
                        }
                        if (info != null) {
                            context.startActivity(intent);
                        } else {
                            String fallbackUrl = intent.getStringExtra(
                                    "browser_fallback_url");
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                                    Uri.parse(fallbackUrl));
                            context.startActivity(browserIntent);
                        }
                        return true;
                    } else {
                        return false;
                    }
                } catch (Exception e) {
                    return false;
                }
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            startTimer();
            isRedirected = false;
            super.onPageStarted(view, url, favicon);
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
            set_searchbar_text(url);
            free_StaticVariables.resourse_holder = new ResourceHolderModel();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if (free_StaticVariables.resourse_holder == null) {
                free_StaticVariables.resourse_holder = new ResourceHolderModel();
            }
            free_StaticVariables.resourse_holder.setPage_title(view.getTitle());
            if (!isRedirected) {
                stopTimer();
                enable_Buttons();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    findLocal();
                }
                if (download_fab.isEnabled() == true) {
                    YoYo.with(Techniques.Tada)
                            .duration(300)
                            .repeat(5)
                            .playOn(findViewById(R.id.download_fab));
                }
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onLoadResource(final WebView view, final String url) {
            if (!URLAddFilter.DoNotCheckIf(mContext, et_search_bar_j.getText().toString())) {
                final String viewUrl = view.getUrl();
                final String title = view.getTitle();

                new ContentSearch(mContext, url, viewUrl, title) {
                    @Override
                    public void onStartInspectingURL() {
                        free_UUtils.disableSSLCertificateChecking();
                    }

                    @Override
                    public void onFinishedInspectingURL(boolean finishedAll) {
                        HttpsURLConnection.setDefaultSSLSocketFactory(defaultSSLSF);
                    }

                    @Override
                    public void onVideoFound(String size, String type, String link, String name, String page, boolean chunked, String website, boolean audio) {
                        free_StaticVariables.resourse_holder.add_Video(size, type, link, name, page);
                        if (free_StaticVariables.resourse_holder.getVideo_files().size() > 0) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    update_image_fab_text();
                                    enable_Buttons();
                                }
                            });
                        }
                    }

                    @Override
                    public void onImageFound(String size, String type, String link, String name, String page, boolean chunked, String website, boolean audio) {
                        try {
                            free_StaticVariables.resourse_holder.add_Image(size, type, link, name, page);
                            if (free_StaticVariables.resourse_holder.getImage_files().size() > 0) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        update_image_fab_text();
                                        enable_Buttons();
                                    }
                                });
                            }
                        } catch (Exception ex) {
                        }
                    }

                    @Override
                    public void onAudioFound(String size, String type, String link, String name, String page, boolean chunked, String website, boolean audio) {
                        free_StaticVariables.resourse_holder.add_Audio(size, type, link, name, page);
                        if (free_StaticVariables.resourse_holder.getImage_files().size() > 0) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    update_audio_fab_text();
                                    enable_Buttons();
                                }
                            });
                        }
                    }
                }.start();
            }
        }
    }

    public boolean isDevMode() {
        if (BuildConfig.DEBUG) {
            return false;
        } else {

            if (Integer.valueOf(android.os.Build.VERSION.SDK) == 16) {
                return android.provider.Settings.Secure.getInt(getApplicationContext().getContentResolver(),
                        android.provider.Settings.Secure.DEVELOPMENT_SETTINGS_ENABLED, 0) != 0;
            } else if (Integer.valueOf(android.os.Build.VERSION.SDK) >= 17) {
                return android.provider.Settings.Secure.getInt(getApplicationContext().getContentResolver(),
                        android.provider.Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0) != 0;
            } else return false;
        }
    }

    public void showDialog() {
        builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
        builder.setTitle("Warning!!");
        builder.setMessage("Please Turn Off Developer Option And Enjoy App Continue!!");
        builder.setPositiveButton("Turn Off", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivityForResult(new Intent(android.provider.Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS), 1000);

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
                System.exit(1);
            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        llFeedback.setClickable(true);
        if (flagj == false) {

        } else {
            llHowToDownload.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
//            if (isDevMode()) {
//                showDialog();
//            } else {
//
//                if (builder != null) {
//                    builder.create().dismiss();
//                }
//            }
        }
    }

    private boolean restorePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        Boolean isIntroActivityOpnendBefore = pref.getBoolean("isIntroOpnend", false);
        return isIntroActivityOpnendBefore;
    }

}