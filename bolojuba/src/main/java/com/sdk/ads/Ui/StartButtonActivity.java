package com.sdk.ads.Ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sdk.ads.BuildConfig;

import com.sdk.ads.Comman;
import com.sdk.ads.LocalSave.ModelPrefrences;
import com.sdk.ads.R;
import com.sdk.ads.ResModel.GroupApp;
import com.sdk.ads.SingleClickListener;
import com.sdk.ads.adapter.MoreAppAdapter;
import com.sdk.ads.ads.AllNativeAds;
import com.sdk.ads.ads.InterCloseCallBack;
import com.sdk.ads.ads.IntertitialAdsEvent;
import com.sdk.ads.ads.TransferClass;

import java.util.ArrayList;

public class StartButtonActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imgStart, imgShare, imgRate, imgPrivacy;

    public static boolean isNetworkConnected(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    @Override
    public void onBackPressed() {
        exitDialog(this);
    }

    @Override
    protected void onResume() {
        IntertitialAdsEvent.CallInterstitial(this);
        super.onResume();
    }

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_button);
        IntertitialAdsEvent.CallInterstitial(this);
        if (getIntent().getBooleanExtra("show", false))
            IntertitialAdsEvent.ShowInterstitialAdsOnCreate(this);
        AllNativeAds.NativeAds(this, findViewById(R.id.adsContainer));
        init();
        setListener();

    }

    private void setListener() {

        imgStart.setOnClickListener(this);
//        imgShare.setOnClickListener(this);
        imgRate.setOnClickListener(this);
        imgPrivacy.setOnClickListener(this);

        imgShare.setOnClickListener(new SingleClickListener() {
            @Override
            public void performClick(View v) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                    String shareMessage = "\nLet me recommend you this application\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.PARENT_PACKAGE + "\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch (Exception ignore) {
                }
            }
        });
    }

    public void init() {
        imgStart = findViewById(R.id.imgStart);
        imgShare = findViewById(R.id.imgShare);
        imgRate = findViewById(R.id.imgRate);
        imgPrivacy = findViewById(R.id.imgPrivacy);

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.imgStart) {
//            ModelPrefrences modelPrefrences = new ModelPrefrences(this);
            IntertitialAdsEvent.waiteInterCall(StartButtonActivity.this, new InterCloseCallBack() {
                @Override
                public void onAdsClose() {
//                    if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getDoPermission().equalsIgnoreCase("on")) {
//                        startActivity(new Intent(StartButtonActivity.this, DoPermissionActivity.class).putExtra("show", true));
//                    } else {
//                    }

                    startActivity(new Intent(StartButtonActivity.this, TransferClass.Destination).putExtra("show", true));



                }
            });

        }
//        else if (view.getId() == R.id.imgShare) {
//            try {
//                Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                shareIntent.setType("text/plain");
//                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Video Call");
//                String shareMessage = "\nLet me recommend you this application\n\n";
//                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.PARENT_PACKAGE + "\n\n";
//                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
//                startActivity(Intent.createChooser(shareIntent, "choose one"));
//            } catch (Exception ignore) {
//            }
//        }
        else if (view.getId() == R.id.imgRate) {

            String str = "android.intent.action.VIEW";
            String sb = "market://details?id=" + BuildConfig.PARENT_PACKAGE;
            Intent intent = new Intent(str, Uri.parse(sb));
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException unused) {
                String sb2 = "http://play.google.com/store/apps/details?id=" + BuildConfig.PARENT_PACKAGE;
                startActivity(new Intent(str, Uri.parse(sb2)));
            }
        } else if (view.getId() == R.id.imgPrivacy) {
            if (isNetworkConnected(this)) {
                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.privacy_dialog);
                dialog.setCancelable(true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                WebView webView = dialog.findViewById(R.id.webView1);
                webView.loadUrl("https://pazabiullapolicy.blogspot.com/2023/07/privacy-policy.html");

                webView.setWebViewClient(new WebViewClient() {
                    public boolean shouldOverrideUrlLoading(WebView viewx, String urlx) {
                        viewx.loadUrl(urlx);
                        return false;
                    }
                });
                (dialog.findViewById(R.id.no)).setOnClickListener(v -> dialog.dismiss());
                dialog.show();
            } else {
                Toast.makeText(this, "Check Your Internet Connection!!", Toast.LENGTH_SHORT).show();
            }
        }

    }
    static boolean doubleBackToExitPressedOnce = false;

    public static void exitDialog(Context context) {

        if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getPermission().equalsIgnoreCase("on")) {
            IntertitialAdsEvent.ShowInterstitialAdsWithCallBack(context, new InterCloseCallBack() {
                @Override
                public void onAdsClose() {
                    context.startActivity(new Intent(context, PermissionActivity.class).putExtra("show", false));
                    ((Activity) context).finish();
                }
            });

        }  else if (Comman.mainResModel.getData().getExtraFields().getLanguage().equalsIgnoreCase("on")) {
            IntertitialAdsEvent.ShowInterstitialAdsWithCallBack(context, new InterCloseCallBack() {
                @Override
                public void onAdsClose() {
                    context.startActivity(new Intent(context, LanguageActivity.class).putExtra("show", false));
                    ((Activity) context).finish();
                }
            });

        } else if (Comman.mainResModel.getData().getExtraFields().getPrivacyPolicy().equalsIgnoreCase("on")) {
            IntertitialAdsEvent.ShowInterstitialAdsWithCallBack(context, new InterCloseCallBack() {
                @Override
                public void onAdsClose() {
                    context.startActivity(new Intent(context, PrivacyPolicyAcitivty.class).putExtra("show", false));
                    ((Activity) context).finish();
                }
            });

        }else if (Comman.mainResModel.getData().getExtraFields().getCountry().equalsIgnoreCase("on")) {
            IntertitialAdsEvent.ShowInterstitialAdsWithCallBack(context, new InterCloseCallBack() {
                @Override
                public void onAdsClose() {
                    context.startActivity(new Intent(context, Country_ConnectActivity.class).putExtra("show", false));
                    ((Activity) context).finish();
                }
            });

        }else {
            if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getExitDialogue().equalsIgnoreCase("on")) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.more_exit_dialog);
                dialog.setCancelable(true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                dialog.setCancelable(true);
                wlp.gravity = Gravity.BOTTOM;
                window.setAttributes(wlp);

                //todo: exit ads...
//        AllNativeAds.NativeAdsExtraExitOld((Activity) context, dialog.findViewById(R.id.adsContainer));
                AllNativeAds.NativeAds((Activity) context, dialog.findViewById(R.id.adsContainer));

                TextView txt_yes = dialog.findViewById(R.id.yes);

                RecyclerView recyclerView = dialog.findViewById(R.id.recyclerView);
                DisplayMetrics displaymetrics = new DisplayMetrics();
                ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                LinearLayout llMore = dialog.findViewById(R.id.llMore);
                if (Comman.mainResModel != null && Comman.mainResModel.getSuccess()) {
                    if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getMoreapps() != null && Comman.mainResModel.getData().getExtraFields().getMoreapps().equalsIgnoreCase("on") && Comman.mainResModel.getData().getGroupApps().size() != 0) {
                        recyclerView.setVisibility(View.VISIBLE);
                        ArrayList<GroupApp> groupApps = new ArrayList<>();
                        for (int i = 0; i < Comman.mainResModel.getData().getGroupApps().size(); i++) {
                            if (!Comman.mainResModel.getData().getGroupApps().get(i).getPackagename().equalsIgnoreCase(BuildConfig.PARENT_PACKAGE)) {
                                groupApps.add(Comman.mainResModel.getData().getGroupApps().get(i));
                            }
                        }
                        MoreAppAdapter adapter = new MoreAppAdapter(context, groupApps, "exit");
                        recyclerView.setHasFixedSize(true);
                        if (Comman.mainResModel.getData().getGroupApps().size() > 5) {
                            int a = (displaymetrics.heightPixels * 28) / 100;
                            recyclerView.getLayoutParams().height = a;
                            recyclerView.setLayoutManager(new GridLayoutManager(context, 5));
                        } else if (Comman.mainResModel.getData().getGroupApps().size() <= 5) {
                            int a = (displaymetrics.heightPixels * 14) / 100;
                            recyclerView.getLayoutParams().height = a;
                            recyclerView.setLayoutManager(new GridLayoutManager(context, Comman.mainResModel.getData().getGroupApps().size()));
                        }
                        llMore.setVisibility(View.VISIBLE);
                        recyclerView.setAdapter(adapter);
                    } else {
                        llMore.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                    }
                } else {
                    llMore.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                }


                txt_yes.setOnClickListener(v -> {
                    dialog.dismiss();
                    if (Comman.mainResModel.getData().getExtraFields().getThankyou().equalsIgnoreCase("on")) {
                        context.startActivity(new Intent(context, ThankyouActivity.class).putExtra("show", true));
                    } else {
                        ((Activity) context).finishAffinity();
                        System.exit(1);
                    }
                });
                dialog.show();
            } else {
                if (doubleBackToExitPressedOnce) {
                    ((Activity) context).finishAffinity();
                    System.exit(1);
                    return;
                }

                doubleBackToExitPressedOnce = true;
                Toast.makeText(context, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);
            }
        }



    }

}
