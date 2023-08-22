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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sdk.ads.BuildConfig;
import com.sdk.ads.Comman;
import com.sdk.ads.LocalSave.ModelPrefrences;
import com.sdk.ads.R;
import com.sdk.ads.ResModel.GroupApp;
import com.sdk.ads.adapter.LanguageAdapter;
import com.sdk.ads.adapter.MoreAppAdapter;
import com.sdk.ads.ads.AllNativeAds;
import com.sdk.ads.ads.InterCloseCallBack;
import com.sdk.ads.ads.IntertitialAdsEvent;
import com.sdk.ads.other.LanguageModel;

import java.util.ArrayList;
import java.util.List;

public class LanguageActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    public void onBackPressed() {
        exitDialog(this);


    }
    ModelPrefrences modelPrefrences;
    RecyclerView rvLeng;
    public static boolean isLangSelect;
    int[] ints = new int[]{R.drawable.leng1, R.drawable.leng2, R.drawable.leng3, R.drawable.leng4, R.drawable.leng5,
            R.drawable.leng6};
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lenguage);
        isLangSelect = false;
        AllNativeAds.NativeBannerNew(this, (ViewGroup) findViewById(R.id.adsContainer));
//        AllNativeAds.NativeAds(this, findViewById(R.id.adsContainer));
        IntertitialAdsEvent.CallInterstitial(this);
        if (getIntent().getBooleanExtra("show", false))
            IntertitialAdsEvent.ShowInterstitialAdsOnCreate(this);
        modelPrefrences = new ModelPrefrences(this);
        init();
        setListener();
    }

    private void setListener() {

    }

    @Override
    protected void onResume() {
        IntertitialAdsEvent.CallInterstitial(this);
        super.onResume();
    }

    public int selected = 0;
    private void init() {


        ArrayList<LanguageModel> arrayList = new ArrayList<>();
//        arrayList.add(new LanguageModel(R.drawable.leng1, "UK", "United Kingdom"));
//        arrayList.add(new LanguageModel(R.drawable.leng1, "USA", "United States"));
//        arrayList.add(new LanguageModel(R.drawable.leng1, "Canada", "Canada"));
//        arrayList.add(new LanguageModel(R.drawable.leng1, "Australia", "Australia"));
//        arrayList.add(new LanguageModel(R.drawable.leng1, "Switzerland", "Switzerland"));
//        arrayList.add(new LanguageModel(R.drawable.leng1, "World Wide", "World Wide"));


        arrayList.add(new LanguageModel(R.drawable.leng1, "English", ""));
        arrayList.add(new LanguageModel(R.drawable.leng2, "Hindi", ""));
        arrayList.add(new LanguageModel(R.drawable.leng3, "Germany", ""));
        arrayList.add(new LanguageModel(R.drawable.leng4, "France", ""));
        arrayList.add(new LanguageModel(R.drawable.leng5, "Zuric", ""));
        arrayList.add(new LanguageModel(R.drawable.leng6, "Urdu", ""));

        rvLeng = findViewById(R.id.rvLeng);
      ImageView imgDone = findViewById(R.id.imgDone);
        rvLeng.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        LanguageAdapter adapter = new LanguageAdapter(arrayList, this, selected,imgDone);
        rvLeng.setAdapter(adapter);
//        adapter.setOnItemClickListener(new LanguageAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(List<LanguageModel> list, int i, String str) {
//                selected = i;
//            }
//        });




//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
//        rvLeng.setLayoutManager(gridLayoutManager);
//        LanguageAdapter languageAdapter = new LanguageAdapter(this, ints);
//        rvLeng.setAdapter(languageAdapter);
    }

    @Override
    public void onClick(View view) {


    }



    static boolean doubleBackToExitPressedOnce = false;

    public static void exitDialog(Context context) {
        if (Comman.mainResModel.getData().getExtraFields().getPrivacyPolicy().equalsIgnoreCase("on")) {
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
