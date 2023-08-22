package com.sdk.ads.interstitialAds;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.sdk.ads.Comman;
import com.sdk.ads.R;
import com.sdk.ads.ResModel.InterAd;
import com.sdk.ads.interstitialAds.callback.InterCloseCallBack;
import com.sdk.ads.interstitialAds.lib.LibAnimationLoader;
import com.sdk.ads.interstitialAds.lib.LibDisplayUtil;

import java.util.ArrayList;
import java.util.Collections;

public class D01InterstitialAds extends Dialog {
    public Context mContext;
    ImageView ImgAppPromo;
    ImageView ImgClose;
    TextView ImgSponsor;
    ArrayList<InterAd> arrayList;
    Display display;
    int height;
    int width;
    private AnimationSet mAnimIn;
    private AnimationSet mAnimOut;
    private View mDialogView;
    private boolean mIsShowAnim;
    private InterCloseCallBack mOnPositiveListener;

    private VideoView vView;

    public D01InterstitialAds(Context context) {
        this(context, R.style.interstitial_theme);
        this.mContext = context;
        display = ((Activity) this.mContext).getWindowManager().getDefaultDisplay();
        this.width = display.getWidth();
        this.height = this.display.getHeight();
    }

    public D01InterstitialAds(Context context, int i) {
        super(context, i);
        this.mContext = context;
        display = ((Activity) this.mContext).getWindowManager().getDefaultDisplay();
        this.width = display.getWidth();
        this.height = this.display.getHeight();
        init();
    }

    private void init() {
        this.mAnimIn = LibAnimationLoader.getInAnimation(getContext());
        this.mAnimOut = LibAnimationLoader.getOutAnimation(getContext());
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initView();
        initListener();
    }

    private void initView() {
        View inflate = View.inflate(getContext(), R.layout.custom_interstitialads, null);
        setContentView(inflate);
        resizeDialog();
        initAnimListener();
        this.mDialogView = getWindow().getDecorView().findViewById(16908290);
        this.ImgSponsor = (TextView) inflate.findViewById(R.id.ImgSponsor);
        this.ImgClose = (ImageView) inflate.findViewById(R.id.ImgClose);
        this.ImgAppPromo = (ImageView) inflate.findViewById(R.id.ImgAppPromo);
        this.vView = (VideoView) inflate.findViewById(R.id.vView);

        arrayList = new ArrayList<>();
        arrayList.addAll(Comman.mainResModel.getData().getInterAds());
        Collections.shuffle(arrayList);
       Glide.with(this.mContext).load(Comman.mainResModel.getData().getBucketLink() + arrayList.get(0).getInterstitialImage()).into(this.ImgAppPromo);
        if (arrayList.get(0).getAdstype().equalsIgnoreCase("video")&&!arrayList.get(0).getVideourl().equalsIgnoreCase("")){
            Uri uri = Uri.parse(arrayList.get(0).getVideourl());
            vView.setVideoURI(uri);
            vView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                    mp.setLooping(true);
                    ImgAppPromo.setVisibility(View.GONE);
                }
            });
            vView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    return true;
                }
            });
        }
        new Handler().postDelayed(() -> ImgClose.setVisibility(View.VISIBLE), 500);

        this.ImgAppPromo.setOnClickListener(view -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(arrayList.get(0).getRedirectUrl()));
            mContext.startActivity(browserIntent);
        });
        this.vView.setOnClickListener(view -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(arrayList.get(0).getRedirectUrl()));
            mContext.startActivity(browserIntent);
        });
    }

    private void resizeDialog() {
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.width = LibDisplayUtil.getScreenSize(getContext()).x;
        attributes.height = LibDisplayUtil.getScreenSize(getContext()).y;
        getWindow().setLayout(-1, -1);
    }

    public void dismiss() {
        dismissWithAnimation(this.mIsShowAnim);
    }

    private void startWithAnimation(boolean z) {
        if (z) {
            this.mDialogView.startAnimation(this.mAnimIn);
        }
    }

    private void dismissWithAnimation(boolean z) {
        if (z) {
            try {
                this.mDialogView.startAnimation(this.mAnimOut);
            } catch (Exception unused) {
                super.dismiss();
            }
        } else {
            super.dismiss();
        }
    }

    public D01InterstitialAds setAnimationEnable(boolean z) {
        this.mIsShowAnim = z;
        return this;
    }

    public void onStart() {
        super.onStart();
        startWithAnimation(this.mIsShowAnim);
    }

    private void initAnimListener() {
        this.mAnimOut.setAnimationListener(new Animation.AnimationListener() {

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                mDialogView.post(new Runnable() {
                    public void run() {
                        callDismiss();
                    }
                });
            }
        });
    }

    private void callDismiss() {
        super.dismiss();
    }

    public D01InterstitialAds setAnimationIn(AnimationSet animationSet) {
        this.mAnimIn = animationSet;
        return this;
    }

    public D01InterstitialAds setAnimationOut(AnimationSet animationSet) {
        this.mAnimOut = animationSet;
        initAnimListener();
        return this;
    }

    private void initListener() {
        this.ImgClose.setOnClickListener(view -> {
            if (mOnPositiveListener != null) {
                mOnPositiveListener.onAdsClose();
            }
        });
        initAnimListener();
    }

    public D01InterstitialAds setOnCloseListener(InterCloseCallBack onCloseListener) {
        this.mOnPositiveListener = onCloseListener;
        return this;
    }
}