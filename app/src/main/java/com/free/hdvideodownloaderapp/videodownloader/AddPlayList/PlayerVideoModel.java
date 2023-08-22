package com.free.hdvideodownloaderapp.videodownloader.AddPlayList;


import java.util.ArrayList;

public class PlayerVideoModel {

    String DateList;
    ArrayList<com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.PlayerVideoModel> datewisevideolist;

    public String getDateList() {
        return DateList;
    }

    public void setDateList(String dateList) {
        DateList = dateList;
    }

    public ArrayList<com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.PlayerVideoModel> getDatewisevideolist() {
        return datewisevideolist;
    }

    public void setDatewisevideolist(ArrayList<com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.PlayerVideoModel> datewisevideolist) {
        this.datewisevideolist = datewisevideolist;
    }

}
