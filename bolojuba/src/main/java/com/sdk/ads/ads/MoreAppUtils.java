package com.sdk.ads.ads;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
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

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sdk.ads.BuildConfig;
import com.sdk.ads.Comman;
import com.sdk.ads.LocalSave.ModelPrefrences;
import com.sdk.ads.R;
import com.sdk.ads.ResModel.GroupApp;
import com.sdk.ads.Ui.Country_ConnectActivity;
import com.sdk.ads.Ui.DoPermissionActivity;
import com.sdk.ads.Ui.LanguageActivity;
import com.sdk.ads.Ui.MoreAppActivity;
import com.sdk.ads.Ui.PermissionActivity;
import com.sdk.ads.Ui.PrivacyPolicyAcitivty;
import com.sdk.ads.Ui.StartButtonActivity;
import com.sdk.ads.Ui.ThankyouActivity;
import com.sdk.ads.adapter.MoreAppAdapter;

import java.util.ArrayList;

public class MoreAppUtils {
    static boolean doubleBackToExitPressedOnce = false;

    public static void exitDialog(Context context) {
        if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getDoPermission().equalsIgnoreCase("on")) {
            IntertitialAdsEvent.ShowInterstitialAdsWithCallBack(context, new InterCloseCallBack() {
                @Override
                public void onAdsClose() {
                    context.startActivity(new Intent(context, DoPermissionActivity.class).putExtra("show", false));
                    ((Activity) context).finish();
                }
            });

        } else if (Comman.mainResModel.getData().getExtraFields().getStartButton().equalsIgnoreCase("on")) {
            IntertitialAdsEvent.ShowInterstitialAdsWithCallBack(context, new InterCloseCallBack() {
                @Override
                public void onAdsClose() {
                    context.startActivity(new Intent(context, StartButtonActivity.class).putExtra("show", false));
                    ((Activity) context).finish();
                }
            });

        }else if (Comman.mainResModel.getData().getExtraFields().getPermission().equalsIgnoreCase("on")) {
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


    public static void moreApps(Context context, ImageView iconContainer) {
        try {


            ModelPrefrences modelPrefrences = new ModelPrefrences(context);
            if (Comman.mainResModel != null && Comman.mainResModel.getSuccess()) {
                if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getMoreapps().equalsIgnoreCase("on")) {
                    iconContainer.setVisibility(View.VISIBLE);
                } else {
                    iconContainer.setVisibility(View.GONE);
                }
            } else {
                iconContainer.setVisibility(View.GONE);
            }

            iconContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, MoreAppActivity.class));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void moreApps(Context context, ViewGroup iconContainer) {
        try {


            ModelPrefrences modelPrefrences = new ModelPrefrences(context);
            if (Comman.mainResModel != null && Comman.mainResModel.getSuccess()) {
                if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getMoreapps().equalsIgnoreCase("on")) {
                    iconContainer.setVisibility(View.VISIBLE);
                } else {
                    iconContainer.setVisibility(View.GONE);
                }
            } else {
                iconContainer.setVisibility(View.GONE);
            }

            iconContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, MoreAppActivity.class));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
