package com.sdk.ads.Ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sdk.ads.Comman;
import com.sdk.ads.R;
import com.sdk.ads.ResModel.GroupApp;
import com.sdk.ads.adapter.MoreAppAdapter;
import com.sdk.ads.ads.AllNativeAds;
import com.sdk.ads.ads.InterCloseCallBack;
import com.sdk.ads.ads.IntertitialAdsEvent;
import com.sdk.ads.ads.TransferClass;

import java.util.ArrayList;

public class DoPermissionActivity extends AppCompatActivity {

    ImageView imgOver;
    RelativeLayout rlPermission;
    boolean isPermission;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.sdk.ads.R.layout.activity_extra_overlay);
        IntertitialAdsEvent.CallInterstitial(this);
//        AllNativeAds.NativeBannerNew(this, (ViewGroup) findViewById(R.id.adsContainer));
        AllNativeAds.NativeAds(this, findViewById(R.id.adsContainer));
        if (getIntent().getBooleanExtra("show", false)) {
            IntertitialAdsEvent.ShowInterstitialAdsOnCreate(this);
        }
        init();
        setListenetr();
    }

    private void setListenetr() {
        imgOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (!Settings.canDrawOverlays(DoPermissionActivity.this)) {
//                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
//                    startActivityForResult(intent, 100);
//                }
            }
        });
        rlPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPermission) {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                    startActivityForResult(intent, 100);
                } else {
                    IntertitialAdsEvent.waiteInterCall(DoPermissionActivity.this, new InterCloseCallBack() {
                        @Override
                        public void onAdsClose() {
                            startActivity(new Intent(DoPermissionActivity.this, TransferClass.Destination).putExtra("show", true));
                        }
                    });
                }
            }
        });
    }

    public void init() {
        imgOver = findViewById(R.id.imgOver);
        rlPermission = findViewById(R.id.rlPermission);
//        if (Settings.canDrawOverlays(DoPermissionActivity.this)) {
//            imgOver.setImageResource(R.drawable.ic_check1);
//            isPermission = true;
//        }else {
//            isPermission = false;
//        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
//                    if (Settings.canDrawOverlays(DoPermissionActivity.this)) {
//                        imgOver.setImageResource(R.drawable.ic_check1);
//                    }
                    isPermission = true;

                }
            }, 500);

        }
    }

    static boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        exitDialog(this);
    }

    public static void exitDialog(Context context) {

        if (Comman.mainResModel.getData().getExtraFields().getStartButton().equalsIgnoreCase("on")) {
            IntertitialAdsEvent.ShowInterstitialAdsWithCallBack(context, new InterCloseCallBack() {
                @Override
                public void onAdsClose() {
                    context.startActivity(new Intent(context, StartButtonActivity.class).putExtra("show", false));
                    ((Activity) context).finish();
                }
            });

        } else if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getPermission().equalsIgnoreCase("on")) {
            IntertitialAdsEvent.ShowInterstitialAdsWithCallBack(context, new InterCloseCallBack() {
                @Override
                public void onAdsClose() {
                    context.startActivity(new Intent(context, PermissionActivity.class).putExtra("show", false));
                    ((Activity) context).finish();
                }
            });

        }else if (Comman.mainResModel.getData().getExtraFields().getLanguage().equalsIgnoreCase("on")) {
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

        } else if (Comman.mainResModel.getData().getExtraFields().getCountry().equalsIgnoreCase("on")) {
            IntertitialAdsEvent.ShowInterstitialAdsWithCallBack(context, new InterCloseCallBack() {
                @Override
                public void onAdsClose() {
                    context.startActivity(new Intent(context, Country_ConnectActivity.class).putExtra("show", false));
                    ((Activity) context).finish();
                }
            });

        } else {
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
//                            if (!Comman.mainResModel.getData().getGroupApps().get(i).getPackagename().equalsIgnoreCase(BuildConfig.PARENT_PACKAGE)) {
//                                groupApps.add(Comman.mainResModel.getData().getGroupApps().get(i));
//                            }
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
