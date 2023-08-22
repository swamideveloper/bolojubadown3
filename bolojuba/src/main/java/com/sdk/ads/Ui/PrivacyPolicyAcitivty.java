package com.sdk.ads.Ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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
import com.sdk.ads.adapter.MoreAppAdapter;
import com.sdk.ads.ads.AllNativeAds;
import com.sdk.ads.ads.InterCloseCallBack;
import com.sdk.ads.ads.IntertitialAdsEvent;
import com.sdk.ads.ads.TransferClass;

import java.util.ArrayList;

public class PrivacyPolicyAcitivty extends AppCompatActivity implements View.OnClickListener {


    ModelPrefrences modelPrefrences;
    ImageView tvAccept, iv_close;
    TextView tv_policy1, tv_policy2, tv_policy3, tv_diffaccept;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        IntertitialAdsEvent.CallInterstitial(this);
//        AllNativeAds.NativeAds(this, (ViewGroup) findViewById(R.id.adsContainer));
        AllNativeAds.NativeBannerNew(this, (ViewGroup) findViewById(R.id.adsContainer));

        if (getIntent().getBooleanExtra("show", false))
            IntertitialAdsEvent.ShowInterstitialAdsOnCreate(this);
        modelPrefrences = new ModelPrefrences(this);
        init();

        String str1 = "Following link :" + " <a href=\"https://androidappsforplaystore.blogspot.com/2022/09/terms-conditions.html\"> <b> Terms and conditions of use </b> </a> ";
        tv_policy1.setText(Html.fromHtml(str1));
        tv_policy1.setLinkTextColor(getResources().getColor(R.color.blue));
        tv_policy1.setMovementMethod(LinkMovementMethod.getInstance());

        String str2 = "Following Link :" + " <a href=\"https://androidappsforplaystore.blogspot.com/2022/09/privacy-policy.html\"> <b> Privacy policy </b> </a> ";
        tv_policy2.setText(Html.fromHtml(str2));
        tv_policy2.setLinkTextColor(getResources().getColor(R.color.blue));
        tv_policy2.setMovementMethod(LinkMovementMethod.getInstance());

        String str3 = "Following Link :" + " <a href=\"https://androidappsforplaystore.blogspot.com/2022/09/app-community-guidelines.html\"> <b> App Community Guidelines </b> </a> ";
        tv_policy3.setText(Html.fromHtml(str3));
        tv_policy3.setLinkTextColor(getResources().getColor(R.color.blue));
        tv_policy3.setMovementMethod(LinkMovementMethod.getInstance());

        iv_close.setOnClickListener(v -> onBackPressed());

        tvAccept.setOnClickListener(v -> {
            IntertitialAdsEvent.waiteInterCall(PrivacyPolicyAcitivty.this, new InterCloseCallBack() {
                @Override
                public void onAdsClose() {
                    extraclass(PrivacyPolicyAcitivty.this, TransferClass.Destination);

                }
            });

        });
    }


    private void init() {
        iv_close = findViewById(R.id.iv_close);
        tvAccept = findViewById(R.id.tvText);

        tv_policy1 = findViewById(R.id.tv_policy1);
        tv_policy2 = findViewById(R.id.tv_policy2);
        tv_policy3 = findViewById(R.id.tv_policy3);
        tv_diffaccept = findViewById(R.id.tv_diffaccept);
    }

    @Override
    public void onClick(View view) {


    }

    public static void extraclass(Context sourceActivity, Class DestinationClass) {
//        ModelPrefrences modelPrefrences = new ModelPrefrences(sourceActivity);
        if (Comman.mainResModel.getData().getExtraFields().getLanguage().equalsIgnoreCase("on")) {
            sourceActivity.startActivity(new Intent(sourceActivity, LanguageActivity.class).putExtra("show", true));
        } else if (Comman.mainResModel.getData().getExtraFields().getPermission().equalsIgnoreCase("on")) {
            sourceActivity.startActivity(new Intent(sourceActivity, PermissionActivity.class).putExtra("show", true));
        } else if (Comman.mainResModel.getData().getExtraFields().getStartButton().equalsIgnoreCase("on")) {
            sourceActivity.startActivity(new Intent(sourceActivity, StartButtonActivity.class).putExtra("show", true));
        }  else if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getDoPermission().equalsIgnoreCase("on")) {
            sourceActivity.startActivity(new Intent(sourceActivity, DoPermissionActivity.class).putExtra("show", true));
        }else {
            sourceActivity.startActivity(new Intent(sourceActivity, DestinationClass).putExtra("show", true));
        }
    }
    @Override
    protected void onResume() {
        IntertitialAdsEvent.CallInterstitial(this);
        super.onResume();
    }
    @Override
    public void onBackPressed() {
        exitDialog(this);


    }


    static boolean doubleBackToExitPressedOnce = false;

    public static void exitDialog(Context context) {
        if (Comman.mainResModel.getData().getExtraFields().getCountry().equalsIgnoreCase("on")) {
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
