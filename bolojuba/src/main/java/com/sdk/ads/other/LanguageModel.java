package com.sdk.ads.other;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

public class LanguageModel {
    int image;
    String name;
    String selectionCode;



    public LanguageModel(int image, String name, String selectionCode) {
        this.image = image;
        this.name = name;
        this.selectionCode = selectionCode;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @BindingAdapter("android:src")
    public static void setLanImage(ImageView image, int file) {
        image.setImageResource(file);
    }


    public String getSelectionCode() {
        return selectionCode;
    }

    public void setSelectionCode(String selectionCode) {
        this.selectionCode = selectionCode;
    }
}
