package com.sdk.ads.LocalSave;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.sdk.ads.ResModel.MainResModel;

public class ModelPrefrences {

    final String USER_PREFS = "Main_Model_Prefrences";
    SharedPreferences appSharedPref;
    SharedPreferences.Editor prefEditor;


    public ModelPrefrences(Context context) {
        this.appSharedPref = context.getSharedPreferences(USER_PREFS, Activity.MODE_PRIVATE);
        this.prefEditor = appSharedPref.edit();
    }

    public void setPurchase(boolean firstTime) {
        prefEditor.putBoolean("Purchase", firstTime);
        prefEditor.commit();
    }

    public boolean getPurchase() {
        return appSharedPref.getBoolean("Purchase", false);
    }

}
