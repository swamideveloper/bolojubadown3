package com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class PlayerVideoModel implements Serializable, Comparable<PlayerVideoModel> {
    public String playlist_name;
    public String id;
    public String displayName;
    public String title;
    private String data;
    public String date;
    private String duartion;
    private String resolution;
    private String size;
    private String Foldername;

    public String getFoldername() {
        return Foldername;
    }

    public void setFoldername(String foldername) {
        Foldername = foldername;
    }

    private boolean isSelected = false;

    public String getPlaylist_name() {
        return playlist_name;
    }

    public void setPlaylist_name(String playlist_name) {
        this.playlist_name = playlist_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuartion() {
        return duartion;
    }

    public void setDuartion(String duartion) {
        this.duartion = duartion;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public int compareTo(PlayerVideoModel o) {
        return getId().compareTo(getId());
    }

    public static Comparator<PlayerVideoModel> sort_date = new Comparator<PlayerVideoModel>() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        @Override
        public int compare(PlayerVideoModel o1, PlayerVideoModel o2) {
            try {
                return format.parse(folderDate(o1.getDate())).compareTo(format.parse(folderDate(o2.getDate())));
            } catch (ParseException e) {
                throw new IllegalArgumentException(e);
            }
        }
    };

    public static Comparator<PlayerVideoModel> sort_length = new Comparator<PlayerVideoModel>() {
        SimpleDateFormat format1 = new SimpleDateFormat("hh:mm:ss");

        @Override
        public int compare(PlayerVideoModel o1, PlayerVideoModel o2) {
            try {
                return format1.parse(duration(o1.getDuartion())).compareTo(format1.parse(duration(o2.getDuartion())));
            } catch (ParseException e) {
                throw new IllegalArgumentException(e);
            }
        }
    };

    public static Comparator<PlayerVideoModel> sort_name = new Comparator<PlayerVideoModel>() {
        @Override
        public int compare(PlayerVideoModel o1, PlayerVideoModel o2) {
            return o1.getTitle().compareTo(o2.getTitle());
        }
    };

    public static Comparator<PlayerVideoModel> sort_size = new Comparator<PlayerVideoModel>() {
        @Override
        public int compare(PlayerVideoModel o1, PlayerVideoModel o2) {
            return o1.getSize().compareTo(o2.getSize());
        }
    };

    public static Comparator<PlayerVideoModel> sort_resolution = new Comparator<PlayerVideoModel>() {
        @Override
        public int compare(PlayerVideoModel o1, PlayerVideoModel o2) {
            return o1.getResolution().compareTo(o2.getResolution());
        }
    };

    public static Comparator<PlayerVideoModel> sort_location = new Comparator<PlayerVideoModel>() {
        @Override
        public int compare(PlayerVideoModel o1, PlayerVideoModel o2) {
            return o1.getData().compareTo(o2.getData());
        }
    };

    private static String folderDate(String str) {
        return new SimpleDateFormat("dd/MM/yyyy").format(new Date(Long.parseLong(str) * 1000));
    }

    private static String duration(String str) {
        try {
            long parseInt = (long) Integer.parseInt(str);
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
