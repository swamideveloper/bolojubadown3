package com.free.hdvideodownloaderapp.videodownloader.MusicPlayer;

import android.app.Activity;
import android.content.SharedPreferences;


import com.free.hdvideodownloaderapp.videodownloader.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class freeHistoryUtility {


    public static boolean removeFavoriteItem(Activity activity) {

        String historyList = gethistoryStringFromPreferences(activity, null, "history");


        String[] favorites = freeHistoryUtility.getHistoryList(activity);
        Log.e("ssdvdsvdsv","rev==="+favorites.length);
        List<String> list = new ArrayList<String>(Arrays.asList(favorites));
        List<String> list111 = new ArrayList<String>();
        if (list.size() > 15) {
            list.remove(0);
            list111=list;
        } else {
            list111=list;
        }

        historyList = "";
        for (int i = 0; i < list111.size(); i++) {

            if (historyList != null && !historyList.equals("")) {
                historyList = historyList + "," + list111.get(i);
            } else {
                historyList = list111.get(i);
            }
        }
        return putHistoryStringInPreferences(activity, historyList, "history");
    }

    public static boolean addHistoryItem(Activity activity, String historyitem) {
        String historyList = gethistoryStringFromPreferences(activity, null, "history");

        if (historyList != null) {
            if (!historyList.contains(historyitem)) {
                if (historyList != null) {
                    historyList = historyList + "," + historyitem;
                }
                Log.e("ssdvdsvdsv", "add===" + historyList);
            }
        } else {
            historyList = historyitem;
        }
        return putHistoryStringInPreferences(activity, historyList, "history");
    }
    public static boolean DeleteAllHistoryList(Activity activity) {
        String historyList = null;
        return putHistoryStringInPreferences(activity, historyList, "history");
    }

    public static String[] getHistoryList(Activity activity) {
        String favoriteList = gethistoryStringFromPreferences(activity, null, "history");
        return convertStringToArray(favoriteList);
    }

    private static boolean putHistoryStringInPreferences(Activity activity, String nick, String key) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("ABCD", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, nick);
        editor.commit();
        return true;
    }

    public static String gethistoryStringFromPreferences(Activity activity, String defaultValue, String key) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("ABCD", Activity.MODE_PRIVATE);
        String temp = sharedPreferences.getString(key, defaultValue);
        return temp;
    }
    private static String[] convertStringToArray(String str) {
        Log.e("TAF", "11===str=" + str);
        if (str != null) {
            String[] arr = str.split(",");
            return arr;
        }
        return new String[0];
    }
}
