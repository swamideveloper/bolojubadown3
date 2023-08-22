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
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;

import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.sdk.ads.R;
import com.sdk.ads.ResModel.CustomAd;

public class TemplateViewSmall extends FrameLayout {

    private int templateType = R.layout.ad_unit_admob_med;
    private int templateMinHeight = 120;
    private NativeTemplateStyle styles;
    private NativeAd nativeAd;
    private NativeAdView nativeAdView;
    private TextView primaryView;
    private TextView secondaryView;
    private RatingBar ratingBar;
    private TextView tertiaryView;
    private TextView txtAds;
    private ImageView iconView;
    private MediaView mediaView;
    private CardView mainCard;
    private AppCompatButton callToActionView;
//    private LinearLayout background;

    public TemplateViewSmall(Context context) {
        super(context);
    }

    public TemplateViewSmall(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public TemplateViewSmall(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    @SuppressLint("NewApi")
    public TemplateViewSmall(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs);
    }

    public void setStyles(NativeTemplateStyle styles) {
        this.styles = styles;
//        this.applyStyles();
    }


    public void setCustomeStyle(NativeTemplateStyle styles, CustomAd customAd,  AllDimen allDimen){
        int mainSize = Integer.parseInt(customAd.getNativeAdViewSize());
        if (allDimen.getDimen(mainSize)!=0) {
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, allDimen.getDimen(mainSize));
            if (nativeAdView != null)
                nativeAdView.setLayoutParams(layoutParams);
        }


        if (mainCard!=null&&customAd.getNativeAdViewBackground() != null && !customAd.getNativeAdViewBackground().isEmpty()) {
            mainCard.setCardBackgroundColor(Color.parseColor(customAd.getNativeAdViewBackground()));
        }
        if (iconView!=null&&customAd.getIconSize() != null && !customAd.getIconSize().isEmpty() && !customAd.getIconSize().equalsIgnoreCase("0")) {
            int iconSize = Integer.parseInt(customAd.getIconSize());
            LinearLayout.LayoutParams iconlayoutParams = new LinearLayout.LayoutParams(allDimen.getDimen(iconSize), allDimen.getDimen(iconSize));
            iconlayoutParams.gravity = Gravity.CENTER;
            iconView.setLayoutParams(iconlayoutParams);
            iconView.setAdjustViewBounds(true);
        }

        if (primaryView != null&&customAd.getPrimaryTextSize() != null && !customAd.getPrimaryTextSize().isEmpty() && !customAd.getPrimaryTextSize().equalsIgnoreCase("0")) {
            primaryView.setTextSize(allDimen.getDimen(Integer.parseInt(customAd.getPrimaryTextSize())));
        }else {
            float primaryTextSize = styles.getPrimaryTextSize();
            if (primaryTextSize > 0 && primaryView != null) {
                primaryView.setTextSize(primaryTextSize);
            }
        }

        try {
            if (primaryView != null&&customAd.getPrimaryTextColor() != null && !customAd.getPrimaryTextColor().isEmpty()) {
                primaryView.setTextColor(Color.parseColor(customAd.getPrimaryTextColor()));
            }else {
                int primaryTypefaceColor = styles.getPrimaryTextTypefaceColor();
                if (primaryTypefaceColor > 0 && primaryView != null) {
                    primaryView.setTextColor(primaryTypefaceColor);
                }
            }
        } catch (Exception ignored) {

        }


        if (primaryView != null&&customAd.getPrimaryTextMaxLines() != null && !customAd.getPrimaryTextMaxLines().isEmpty() && !customAd.getPrimaryTextMaxLines().equalsIgnoreCase("0"))
            primaryView.setMaxLines(Integer.parseInt(customAd.getPrimaryTextMaxLines()));

        if (primaryView != null&&customAd.getPrimaryTextStyle() != null && customAd.getPrimaryTextStyle().equalsIgnoreCase("normal"))
            primaryView.setTypeface(null, Typeface.NORMAL);
        else if (primaryView != null&&customAd.getPrimaryTextStyle() != null && customAd.getPrimaryTextStyle().equalsIgnoreCase("bold"))
            primaryView.setTypeface(null, Typeface.BOLD);
        else if (primaryView != null&&customAd.getPrimaryTextStyle() != null && customAd.getPrimaryTextStyle().equalsIgnoreCase("italic"))
            primaryView.setTypeface(null, Typeface.ITALIC);
        else if (primaryView != null&&customAd.getPrimaryTextStyle() != null && customAd.getPrimaryTextStyle().equalsIgnoreCase("bold_italic"))
            primaryView.setTypeface(null, Typeface.BOLD_ITALIC);
        else {
            Typeface primary = styles.getPrimaryTextTypeface();
            if (primary != null && primaryView != null) {
                primaryView.setTypeface(primary);
            }
        }
        if (txtAds!=null && customAd.getTxtAdsText() != null&& !customAd.getTxtAdsText().isEmpty())
            txtAds.setText(customAd.getTxtAdsText());

        if (txtAds!=null &&customAd.getTxtAdsTextSize() != null && !customAd.getTxtAdsTextSize().isEmpty() && !customAd.getTxtAdsTextSize().equalsIgnoreCase("0"))
            txtAds.setTextSize(allDimen.getDimen(Integer.parseInt(customAd.getTxtAdsTextSize())));


        try {
            if (txtAds!=null &&customAd.getTxtAdsTextColor() != null && !customAd.getTxtAdsTextColor().isEmpty())
                txtAds.setTextColor(Color.parseColor(customAd.getTxtAdsTextColor()));
        } catch (Exception ignored) {

        }

        try {
            if (txtAds!=null &&customAd.getTxtAdsBackgroundColor() != null && !customAd.getTxtAdsBackgroundColor().isEmpty())
                txtAds.setBackgroundColor(Color.parseColor(customAd.getTxtAdsBackgroundColor()));
        } catch (Exception ignored) {

        }

        if (txtAds!=null &&customAd.getTxtAdsTextStyle() != null && customAd.getTxtAdsTextStyle().equalsIgnoreCase("normal"))
            txtAds.setTypeface(null, Typeface.NORMAL);
        else if (txtAds!=null &&customAd.getTxtAdsTextStyle() != null && customAd.getTxtAdsTextStyle().equalsIgnoreCase("bold"))
            txtAds.setTypeface(null, Typeface.BOLD);
        else if (txtAds!=null &&customAd.getTxtAdsTextStyle() != null && customAd.getTxtAdsTextStyle().equalsIgnoreCase("italic"))
            txtAds.setTypeface(null, Typeface.ITALIC);
        else if (txtAds!=null &&customAd.getTxtAdsTextStyle() != null && customAd.getTxtAdsTextStyle().equalsIgnoreCase("bold_italic"))
            txtAds.setTypeface(null, Typeface.BOLD_ITALIC);


        if (secondaryView!=null && customAd.getSecondaryTextSize() != null && !customAd.getSecondaryTextSize().isEmpty() && !customAd.getSecondaryTextSize().equalsIgnoreCase("0")) {
            secondaryView.setTextSize(allDimen.getDimen(Integer.parseInt(customAd.getSecondaryTextSize())));
        }else {
            float secondaryTextSize = styles.getSecondaryTextSize();
            if (secondaryTextSize > 0 && secondaryView != null) {
                secondaryView.setTextSize(secondaryTextSize);
            }
        }

        try {
            if (secondaryView!=null&&customAd.getSecondaryTextColor() != null && customAd.getSecondaryTextColor().isEmpty()) {
                secondaryView.setTextColor(Color.parseColor(customAd.getSecondaryTextColor()));
            }else {
                int secondaryTypefaceColor = styles.getSecondaryTextTypefaceColor();
                if (secondaryTypefaceColor > 0 && secondaryView != null) {
                    secondaryView.setTextColor(secondaryTypefaceColor);
                }
            }
        } catch (Exception ignored) {
        }



        if (ratingBar!=null && customAd.getRatingBar()!=null && !customAd.getRatingBar().isEmpty() && customAd.getRatingBar().equalsIgnoreCase("on")){
            ratingBar.setVisibility(View.VISIBLE);
        }else {
            ratingBar.setVisibility(View.GONE);
        }



        if (tertiaryView!=null && customAd.getBodyTextSize() != null && !customAd.getBodyTextSize().isEmpty() && !customAd.getBodyTextSize().equalsIgnoreCase("0")) {
            tertiaryView.setTextSize(allDimen.getDimen(Integer.parseInt(customAd.getBodyTextSize())));
        }else {
            float tertiaryTextSize = styles.getTertiaryTextSize();
            if (tertiaryTextSize > 0 && tertiaryView != null) {
                tertiaryView.setTextSize(tertiaryTextSize);
            }
        }

        try {
            if (tertiaryView!=null && customAd.getBodyTextColor() != null && !customAd.getBodyTextColor().isEmpty()) {
                tertiaryView.setTextColor(Color.parseColor(customAd.getBodyTextColor()));
            }else {
                int tertiaryTypefaceColor = styles.getTertiaryTextTypefaceColor();
                if (tertiaryTypefaceColor > 0 && tertiaryView != null) {
                    tertiaryView.setTextColor(tertiaryTypefaceColor);
                }
            }
        } catch (Exception ignored) {
        }

        if (tertiaryView!=null && customAd.getBodyTextMaxLines() != null && !customAd.getBodyTextMaxLines().isEmpty() && !customAd.getBodyTextMaxLines().equalsIgnoreCase("0"))
            tertiaryView.setMaxLines(Integer.parseInt(customAd.getBodyTextMaxLines()));
        if (customAd.getCtaButtonSize()!=null && !customAd.getCtaButtonSize().isEmpty()&&!customAd.getCtaButtonSize().equalsIgnoreCase("0")) {
            int ctasize = Integer.parseInt(customAd.getCtaButtonSize());
            LinearLayout.LayoutParams ctalayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, allDimen.getDimen(ctasize));
            ctalayoutParams.gravity = Gravity.CENTER;
            ctalayoutParams.setMargins(5, 0, 5, 0);
            //    ctalayoutParams.getRule(RelativeLayout.CENTER_HORIZONTAL);
            if (callToActionView != null) {
                callToActionView.setLayoutParams(ctalayoutParams);
                callToActionView.setGravity(Gravity.CENTER);
            }

        }



        if (callToActionView!=null&&customAd.getCtaButtonTextSize() != null && !customAd.getCtaButtonTextSize().isEmpty() && !customAd.getCtaButtonTextSize().equalsIgnoreCase("0")) {
            callToActionView.setTextSize(allDimen.getDimen(Integer.parseInt(customAd.getCtaButtonTextSize())));
        }else {
            float ctaTextSize = styles.getCallToActionTextSize();
            if (ctaTextSize > 0 && callToActionView != null) {
                callToActionView.setTextSize(ctaTextSize);
            }
        }

        try {
            if (callToActionView!=null&&customAd.getCtaButtonTextColor() != null && !customAd.getCtaButtonTextColor().isEmpty()) {
                callToActionView.setTextColor(Color.parseColor(customAd.getCtaButtonTextColor()));
            }else {
                int ctaTypefaceColor = styles.getCallToActionTypefaceColor();
                if (ctaTypefaceColor > 0 && callToActionView != null) {
                    callToActionView.setTextColor(ctaTypefaceColor);
                }
            }
        } catch (Exception ignored) {
        }

        try {
            if (callToActionView!=null && customAd.getCtaButtonBackgroundColor() != null && !customAd.getCtaButtonBackgroundColor().isEmpty()) {
                //  callToActionView.setBackgroundColor(Color.parseColor(customAd.getCtaButtonBackgroundColor()));
                callToActionView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(customAd.getCtaButtonBackgroundColor())));
            }else {
                Drawable ctaBackground = styles.getCallToActionBackgroundColor();
                if (ctaBackground != null && callToActionView != null) {
                    callToActionView.setBackground(ctaBackground);
                }
            }
        } catch (Exception ignored) {

        }

        if (callToActionView!=null&&customAd.getCtaButtonTextStyle() != null && customAd.getCtaButtonTextStyle().equalsIgnoreCase("normal"))
            callToActionView.setTypeface(null, Typeface.NORMAL);
        else if (callToActionView!=null&&customAd.getCtaButtonTextStyle() != null && customAd.getCtaButtonTextStyle().equalsIgnoreCase("bold"))
            callToActionView.setTypeface(null, Typeface.BOLD);
        else if (callToActionView!=null&&customAd.getCtaButtonTextStyle() != null && customAd.getCtaButtonTextStyle().equalsIgnoreCase("italic"))
            callToActionView.setTypeface(null, Typeface.ITALIC);
        else if (callToActionView!=null&&customAd.getCtaButtonTextStyle() != null && customAd.getCtaButtonTextStyle().equalsIgnoreCase("bold_italic"))
            callToActionView.setTypeface(null, Typeface.BOLD_ITALIC);
        else {
            Typeface ctaTypeface = styles.getCallToActionTextTypeface();
            if (ctaTypeface != null && callToActionView != null) {
                callToActionView.setTypeface(ctaTypeface);
            }
        }
        invalidate();
        requestLayout();
    }

    public NativeAdView getNativeAdView() {
        return nativeAdView;
    }

    private void applyStyles() {

        Drawable mainBackground = styles.getMainBackgroundColor();
        if (mainBackground != null) {
//            background.setBackground(mainBackground);
            if (primaryView != null) {
                primaryView.setBackground(mainBackground);
            }
            if (secondaryView != null) {
                secondaryView.setBackground(mainBackground);
            }
            if (tertiaryView != null) {
                tertiaryView.setBackground(mainBackground);
            }
        }

        Typeface primary = styles.getPrimaryTextTypeface();
        if (primary != null && primaryView != null) {
            primaryView.setTypeface(primary);
        }

        Typeface secondary = styles.getSecondaryTextTypeface();
        if (secondary != null && secondaryView != null) {
            secondaryView.setTypeface(secondary);
        }

        Typeface tertiary = styles.getTertiaryTextTypeface();
        if (tertiary != null && tertiaryView != null) {
            tertiaryView.setTypeface(tertiary);
        }

        Typeface ctaTypeface = styles.getCallToActionTextTypeface();
        if (ctaTypeface != null && callToActionView != null) {
            callToActionView.setTypeface(ctaTypeface);
        }

        int primaryTypefaceColor = styles.getPrimaryTextTypefaceColor();
        if (primaryTypefaceColor > 0 && primaryView != null) {
            primaryView.setTextColor(primaryTypefaceColor);
        }

        int secondaryTypefaceColor = styles.getSecondaryTextTypefaceColor();
        if (secondaryTypefaceColor > 0 && secondaryView != null) {
            secondaryView.setTextColor(secondaryTypefaceColor);
        }

        int tertiaryTypefaceColor = styles.getTertiaryTextTypefaceColor();
        if (tertiaryTypefaceColor > 0 && tertiaryView != null) {
            tertiaryView.setTextColor(tertiaryTypefaceColor);
        }

        int ctaTypefaceColor = styles.getCallToActionTypefaceColor();
        if (ctaTypefaceColor > 0 && callToActionView != null) {
            callToActionView.setTextColor(ctaTypefaceColor);
        }

        float ctaTextSize = styles.getCallToActionTextSize();
        if (ctaTextSize > 0 && callToActionView != null) {
            callToActionView.setTextSize(ctaTextSize);
        }

        float primaryTextSize = styles.getPrimaryTextSize();
        if (primaryTextSize > 0 && primaryView != null) {
            primaryView.setTextSize(primaryTextSize);
        }

        float secondaryTextSize = styles.getSecondaryTextSize();
        if (secondaryTextSize > 0 && secondaryView != null) {
            secondaryView.setTextSize(secondaryTextSize);
        }

        float tertiaryTextSize = styles.getTertiaryTextSize();
        if (tertiaryTextSize > 0 && tertiaryView != null) {
            tertiaryView.setTextSize(tertiaryTextSize);
        }

        Drawable ctaBackground = styles.getCallToActionBackgroundColor();
        if (ctaBackground != null && callToActionView != null) {
            callToActionView.setBackground(ctaBackground);
        }

        Drawable primaryBackground = styles.getPrimaryTextBackgroundColor();
        if (primaryBackground != null && primaryView != null) {
            primaryView.setBackground(primaryBackground);
        }

        Drawable secondaryBackground = styles.getSecondaryTextBackgroundColor();
        if (secondaryBackground != null && secondaryView != null) {
            secondaryView.setBackground(secondaryBackground);
        }

        Drawable tertiaryBackground = styles.getTertiaryTextBackgroundColor();
        if (tertiaryBackground != null && tertiaryView != null) {
            tertiaryView.setBackground(tertiaryBackground);
        }

        invalidate();
        requestLayout();
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
          //  ratingBar.setVisibility(VISIBLE);
            ratingBar.setMax(5);
            ratingBar.setRating(Float.valueOf(String.valueOf(starRating)));
            nativeAdView.setStarRatingView(ratingBar);
            secondaryView.setText(secondaryText);
            secondaryView.setVisibility(VISIBLE);
        } else {
//            secondaryView.setText(secondaryText);
            secondaryView.setVisibility(GONE);
         //   ratingBar.setVisibility(GONE);
        }

        if (icon != null) {
            iconView.setVisibility(GONE);
//            iconView.setVisibility(VISIBLE);
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
        mainCard = findViewById(R.id.mainCard);
        nativeAdView = findViewById(R.id.native_ad_view);
        primaryView = findViewById(R.id.primary);
        secondaryView = findViewById(R.id.secondary);
        tertiaryView = findViewById(R.id.body);
        txtAds = findViewById(R.id.txtAds);

        ratingBar = findViewById(R.id.rating_bar);
        ratingBar.setEnabled(false);

        callToActionView = findViewById(R.id.cta);
        iconView = findViewById(R.id.icon);
        mediaView = findViewById(R.id.media_view);
        mediaView.setOnHierarchyChangeListener(new OnHierarchyChangeListener() {
            @Override
            public void onChildViewAdded(View parent, View child) {
                if (child instanceof ImageView) {
                    ImageView imageView = (ImageView) child;
                    imageView.setAdjustViewBounds(true);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                }
            }

            @Override
            public void onChildViewRemoved(View parent, View child) {}
        });
//        background = findViewById(R.id.background);

    }


}
