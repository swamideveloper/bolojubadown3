package com.free.hdvideodownloaderapp.videodownloader.Utils;

import android.app.Activity;
import android.content.SharedPreferences;


import com.free.hdvideodownloaderapp.videodownloader.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyUtility {
    public static boolean removeFavoriteItem(Activity activity, String favoriteItem) {
        String favoriteList = getStringFromPreferences(activity, null, "favorites");


        String[] favorites = MyUtility.getFavoriteList(activity);
        List<String> list = new ArrayList<String>(Arrays.asList(favorites));

        for (int i = 0; i < list.size(); i++) {

            if (favoriteItem.toString().equals("" + list.get(i))) {
                list.remove(i);
            }
        }
        favorites = list.toArray(new String[0]);
        favoriteList = "";
        for (int i = 0; i < list.size(); i++) {

            if (favoriteList != null && !favoriteList.equals("")) {
                Log.e("#####", "remve===11" );
                favoriteList = favoriteList + "," + list.get(i);
            } else {
                Log.e("#####", "remve===222" );
                favoriteList = list.get(i);
            }

        }
        return putStringInPreferences(activity, favoriteList, "favorites");
    }


    public static boolean addFavoriteItem(Activity activity, String favoriteItem) {
        String favoriteList = getStringFromPreferences(activity, null, "favorites");

        if (favoriteList != null) {
            favoriteList = favoriteList + "," + favoriteItem;
        } else {
            favoriteList = favoriteItem;
        }
        Log.e("#####", "add===" + favoriteList);
        return putStringInPreferences(activity, favoriteList, "favorites");
    }


    public static String[] getFavoriteList(Activity activity) {
        String favoriteList = getStringFromPreferences(activity, null, "favorites");
        return convertStringToArray(favoriteList);
    }


    private static boolean putStringInPreferences(Activity activity, String nick, String key) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("ABC",Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, nick);
        editor.commit();
        return true;
    }


    public static String getStringFromPreferences(Activity activity, String defaultValue, String key) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("ABC",Activity.MODE_PRIVATE);
        String temp = sharedPreferences.getString(key, defaultValue);
        return temp;
    }

    private static String[] convertStringToArray(String str) {
        Log.e("TAF", "11===str=" +str);
        if (str != null) {
            String[] arr = str.split(",");
            return arr;
        }

        return new String[0];
    }



}
