// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.sdk.ads.ads;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.sdk.ads.Comman;
import com.sdk.ads.R;
import com.sdk.ads.ResModel.CustomAd;

public class TemplateViewNew extends FrameLayout {

    private int templateType = R.layout.ad_unit_admob_med;
    private int templateMinHeight = 120;
    private NativeTemplateStyle styles;
    private NativeAd nativeAd;
    private NativeAdView nativeAdView;
    private TextView primaryView;
    private TextView secondaryView;
    private RatingBar ratingBar;
    private TextView tertiaryView;
    private ImageView iconView;
    private MediaView mediaView;
    private AppCompatButton callToActionView;
//    private LinearLayout background;

    public TemplateViewNew(Context context) {
        super(context);
    }

    public TemplateViewNew(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public TemplateViewNew(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    @SuppressLint("NewApi")
    public TemplateViewNew(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs);
    }



    public NativeAdView getNativeAdView() {
        return nativeAdView;
    }


    private boolean adHasOnlyStore(NativeAd nativeAd) {
        String store = nativeAd.getStore();
        String advertiser = nativeAd.getAdvertiser();
        return !TextUtils.isEmpty(store) && TextUtils.isEmpty(advertiser);
    }

    public void setNativeAd(NativeAd nativeAd) {
        this.nativeAd = nativeAd;

        String store = nativeAd.getStore();
        String advertiser = nativeAd.getAdvertiser();
        String headline = nativeAd.getHeadline();
        String body = nativeAd.getBody();
        String cta = nativeAd.getCallToAction();
        Double starRating = nativeAd.getStarRating();
        NativeAd.Image icon = nativeAd.getIcon();

        String secondaryText;

        nativeAdView.setCallToActionView(callToActionView);
        nativeAdView.setHeadlineView(primaryView);
        nativeAdView.setMediaView(mediaView);
        secondaryView.setVisibility(VISIBLE);
        if (adHasOnlyStore(nativeAd)) {
            nativeAdView.setStoreView(secondaryView);
            secondaryText = store;
        } else if (!TextUtils.isEmpty(advertiser)) {
            nativeAdView.setAdvertiserView(secondaryView);
            secondaryText = advertiser;
        } else {
            secondaryText = "";
        }

        primaryView.setText(headline);
        callToActionView.setText(cta);

        //  Set the secondary view to be the star rating if available.
        if (starRating != null && starRating > 0) {
            ratingBar.setVisibility(GONE);
            ratingBar.setMax(5);
            ratingBar.setRating(Float.valueOf(String.valueOf(starRating)));
            nativeAdView.setStarRatingView(ratingBar);
            secondaryView.setText(secondaryText);
            secondaryView.setVisibility(VISIBLE);
        } else {
//            secondaryView.setText(secondaryText);
            secondaryView.setVisibility(GONE);
            ratingBar.setVisibility(GONE);
        }

        if (icon != null) {
//            iconView.setVisibility(GONE);
            iconView.setVisibility(VISIBLE);
            iconView.setImageDrawable(icon.getDrawable());
        } else {
            iconView.setVisibility(GONE);
        }

        if (tertiaryView != null) {
            tertiaryView.setText(body);
            nativeAdView.setBodyView(tertiaryView);
        }

        nativeAdView.setNativeAd(nativeAd);
    }

    public void destroyNativeAd() {
        nativeAd.destroy();
    }

    private void initView(Context context, AttributeSet attributeSet) {

        TypedArray attributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.TemplateView, 0, 0);

        try {
            templateType = attributes.getResourceId(R.styleable.TemplateView_gnt_template_type, templateType);
            templateMinHeight = attributes.getResourceId(R.styleable.TemplateView_gnt_template_min_height, templateMinHeight);
        } finally {
            attributes.recycle();
        }
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(templateType, this);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        nativeAdView = findViewById(R.id.native_ad_view);
        primaryView = findViewById(R.id.primary);
        secondaryView = findViewById(R.id.secondary);
        tertiaryView = findViewById(R.id.body);

        ratingBar = findViewById(R.id.rating_bar);
        ratingBar.setEnabled(false);

        callToActionView = findViewById(R.id.cta);
        iconView = findViewById(R.id.icon);
        mediaView = findViewById(R.id.media_view);
//        mediaView.setOnHierarchyChangeListener(new OnHierarchyChangeListener() {
//            @Override
//            public void onChildViewAdded(View parent, View child) {
//                if (child instanceof ImageView) {
//                    ImageView imageView = (ImageView) child;
//                    imageView.setAdjustViewBounds(true);
//                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                }
//            }
//
//            @Override
//            public void onChildViewRemoved(View parent, View child) {}
//        });


//        background = findViewById(R.id.background);

    }

    public void setCustomeStyle(NativeTemplateStyle styles){
        try {
            if (callToActionView!=null && Comman.mainResModel.getData().getExtraFields().getAdsbuttonColor() != null && !Comman.mainResModel.getData().getExtraFields().getAdsbuttonColor().isEmpty()) {
//                  callToActionView.setBackgroundColor(Color.parseColor(customAd.getCtaButtonBackgroundColor()));
                callToActionView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(Comman.mainResModel.getData().getExtraFields().getAdsbuttonColor())));
            }else {
                Drawable ctaBackground = styles.getCallToActionBackgroundColor();
                if (ctaBackground != null && callToActionView != null) {
                    callToActionView.setBackground(ctaBackground);
                }
            }
        } catch (Exception ignored) {


        }
// invalidate();
// requestLayout();
    }
}
