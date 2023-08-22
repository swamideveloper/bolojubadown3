package com.free.hdvideodownloaderapp.videodownloader.mvvmbalaji;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;


import com.free.hdvideodownloaderapp.videodownloader.R;
import com.free.hdvideodownloaderapp.videodownloader.databinding.ActivitySelectLanguageBinding;
import com.free.hdvideodownloaderapp.videodownloader.databinding.ExitDialoggBinding;
import com.sdk.ads.ads.AllNativeAds;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SelectLanguage_Activity extends Base_Dating_Call_Activity {

    ActivitySelectLanguageBinding binding;
    private String selectedCode = "en";
    private String selectedLanguage = "English";
    public int selected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectLanguageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AllNativeAds.NativeAds(this, findViewById(R.id.adsContainer));

        selectedCode = appPrefs.getLanguageCode();
        selectedLanguage = appPrefs.getLanguageCode();
        selected = Integer.parseInt(appPrefs.getLanguageint());

        setData();



        binding.imgDone.setOnClickListener(v -> {
            appPrefs.setLanguage(true);

            changeLng();
            changeScreen();

        });

    }

    private void changeLng() {
        Locale locale;
        this.appPrefs.setLanguageCode(selectedCode);
        this.appPrefs.setLanguageName(selectedLanguage);
        this.appPrefs.setLanguageint(String.valueOf(selected));
        if (!this.selectedCode.isEmpty()) {
            if (this.selectedCode.equals("zh")) {
                locale = Locale.SIMPLIFIED_CHINESE;
            } else if (this.selectedCode.equals("zh-rTW")) {
                locale = Locale.TRADITIONAL_CHINESE;
            } else {
                locale = new Locale(this.selectedCode);
            }


            Resources resources = getResources();
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            Configuration configuration = resources.getConfiguration();
            configuration.setLayoutDirection(locale);
            configuration.locale = locale;
            resources.updateConfiguration(configuration, displayMetrics);
        }

    }

    private void changeScreen() {
            Intent intent = new Intent(this, Dating_Call_Onboarding_Activity.class);
            startActivity(intent);
    }

    private void setData() {
        ArrayList<LanguageModel> arrayList = new ArrayList<>();
        arrayList.add(new LanguageModel(R.drawable.lan_eng, "English", "en"));
        arrayList.add(new LanguageModel(R.drawable.lan_hindi, "Hindi", "hi"));
        arrayList.add(new LanguageModel(R.drawable.lan_port, "Portuguese", "pt"));
        arrayList.add(new LanguageModel(R.drawable.lan_spain, "Spanish", "es"));
        arrayList.add(new LanguageModel(R.drawable.lan_indo, "Indonesian", "in"));
        arrayList.add(new LanguageModel(R.drawable.lan_korean, "Korean", "ko"));

        arrayList.add(new LanguageModel(R.drawable.lan_bengali, "Bengali", "bn"));
        arrayList.add(new LanguageModel(R.drawable.lan_russian, "Russian", "ru"));
        arrayList.add(new LanguageModel(R.drawable.lan_japanese, "Japanese", "ja"));

        Language_Adapter adapter = new Language_Adapter(arrayList, this, selected);
        binding.recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new Language_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(List<LanguageModel> list, int i, String str) {
                selected = i;
                selectedCode = str;
                selectedLanguage = list.get(i).getName();
            }
        });

    }


    @Override
    public void onBackPressed() {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        ExitDialoggBinding binding1 = ExitDialoggBinding.inflate(((Activity) this).getLayoutInflater());
        dialog.setContentView(binding1.getRoot());
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        AllNativeAds.NativeAdsNew(this, binding1.adsContainer1);
        binding1.done.setOnClickListener(v -> {

            finishAffinity();

        });

        binding1.cancel.setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }
}