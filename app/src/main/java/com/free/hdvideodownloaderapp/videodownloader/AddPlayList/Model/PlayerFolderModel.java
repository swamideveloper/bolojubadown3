package com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model;

import java.util.Comparator;
import java.util.concurrent.TimeUnit;

public class PlayerFolderModel {
    String bid;
    String bucket;
    String data;
    String videoCount;
    String size;
    String date;

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(String videoCount) {
        this.videoCount = videoCount;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public static Comparator<PlayerFolderModel> sort_name = new Comparator<PlayerFolderModel>() {
        @Override
        public int compare(PlayerFolderModel o1, PlayerFolderModel o2) {
            return o1.getBucket().compareTo(o2.getBucket());
        }
    };

    public static Comparator<PlayerFolderModel> sort_size = new Comparator<PlayerFolderModel>() {
        @Override
        public int compare(PlayerFolderModel o1, PlayerFolderModel o2) {
            return o1.getSize().compareTo(o2.getSize());
        }
    };

    private static String duration(String str) {
        try {
            long parseInt =  Integer.parseInt(str);
            long hours = TimeUnit.MILLISECONDS.toHours(parseInt);
            long minutes = TimeUnit.MILLISECONDS.toMinutes(parseInt) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(parseInt));
            long seconds = TimeUnit.MILLISECONDS.toSeconds(parseInt) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(parseInt));
            return String.format("%02d:%02d:%02d", new Object[]{Long.valueOf(hours), Long.valueOf(minutes), Long.valueOf(seconds)});
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
