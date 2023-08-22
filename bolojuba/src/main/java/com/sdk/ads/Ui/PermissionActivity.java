package com.sdk.ads.Ui;

import static android.telecom.TelecomManager.ACTION_CHANGE_DEFAULT_DIALER;
import static android.telecom.TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.role.RoleManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.telecom.TelecomManager;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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

public class PermissionActivity extends AppCompatActivity implements View.OnClickListener {

        public static String[] storge_permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    ImageView iv_set4, iv_setcamera;
    LocationManager lm;
    private Activity mActivity;
    ModelPrefrences modelPrefrences;
    boolean networkEnabled = false;
    TextView next;
    boolean permissionFlag;
    SharedPref sharedPref;
    TextView tvAccept;
    RelativeLayout rel_44, rel_camera;
    boolean isstorage;

    public void onClick(View view) {
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_permission1);
        this.sharedPref = new SharedPref(this);
        IntertitialAdsEvent.CallInterstitial(this);
        AllNativeAds.NativeAds(this, (ViewGroup) findViewById(R.id.adsContainer));
//        AllNativeAds.NativeBannerNew(this, (ViewGroup) findViewById(R.id.adsContainer));
        this.modelPrefrences = new ModelPrefrences(this);
        this.mActivity = this;
        init();

        if (getIntent().getBooleanExtra("show", false)) {
            IntertitialAdsEvent.ShowInterstitialAdsOnCreate(this);
        }
        setDone();

        this.rel_44.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (checkPermission()) {
                    iv_set4.setImageResource(R.drawable.ic_check1);
                    isstorage = true;

                } else {
                    requestPermission();
                }


            }
        });
        this.rel_camera.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {


            }
        });
        TextView textView = (TextView) findViewById(R.id.imgStart);
        this.tvAccept = textView;

        this.tvAccept.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (isstorage) {
                    IntertitialAdsEvent.waiteInterCall(PermissionActivity.this, new InterCloseCallBack() {
                        @Override
                        public void onAdsClose() {
                            PermissionActivity.extraclass(PermissionActivity.this, TransferClass.Destination);
                        }
                    });
                } else {

                        requestPermission();



                }
            }
        });
    }


    private void setDone() {
        if (checkPermission()) {
            isstorage = true;
            iv_set4.setImageResource(R.drawable.ic_check1);

        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1002:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    tvAccept.setText("Finish");
                    IntertitialAdsEvent.waiteInterCall(PermissionActivity.this, new InterCloseCallBack() {
                        @Override
                        public void onAdsClose() {
                            PermissionActivity.extraclass(PermissionActivity.this, TransferClass.Destination);

                        }
                    });

                }
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    isstorage = true;
                    iv_set4.setImageResource(R.drawable.ic_check1);

                    // main logic
                } else {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                            }
                        }
                    }
                }

                break;

        }
    }

    private void requestPermission() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            ActivityCompat.requestPermissions(this, storge_permissions_33, 1002);
//        } else {
//            ActivityCompat.requestPermissions(this, storge_permissions, 1002);
//        }


            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1002);

    }

    private boolean checkPermission() {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ) {
                return false;
            }

            return true;
    }

    public void init() {
        this.permissionFlag = false;
        rel_44 = findViewById(R.id.rel_44);
        rel_camera = findViewById(R.id.rel_camera);
        iv_setcamera = findViewById(R.id.iv_setcamera);

        this.iv_set4 = findViewById(R.id.iv_set4);
    }

    public static void extraclass(Context context, Class cls) {
        ModelPrefrences modelPrefrences2 = new ModelPrefrences(context);
        if (Comman.mainResModel.getData().getExtraFields().getStartButton().equalsIgnoreCase("on")) {
            context.startActivity(new Intent(context, StartButtonActivity.class).putExtra("show", true));

        } else if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getDoPermission().equalsIgnoreCase("on")) {
            context.startActivity(new Intent(context, DoPermissionActivity.class).putExtra("show", true));
        } else {
            context.startActivity(new Intent(context, cls).putExtra("show", true));
        }
    }

    @Override
    protected void onResume() {
        IntertitialAdsEvent.CallInterstitial(this);

        super.onResume();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1002) {
            iv_setcamera.setImageResource(R.drawable.ic_check1);
            if (checkPermission()) {
                tvAccept.setText("Finish");
                IntertitialAdsEvent.waiteInterCall(PermissionActivity.this, new InterCloseCallBack() {
                    @Override
                    public void onAdsClose() {
                        PermissionActivity.extraclass(PermissionActivity.this, TransferClass.Destination);

                    }
                });

            }

        }
    }

    @Override
    public void onBackPressed() {
        exitDialog(this);
    }

    static boolean doubleBackToExitPressedOnce = false;

    public static void exitDialog(Context context) {
        if (Comman.mainResModel.getData().getExtraFields().getLanguage().equalsIgnoreCase("on")) {
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