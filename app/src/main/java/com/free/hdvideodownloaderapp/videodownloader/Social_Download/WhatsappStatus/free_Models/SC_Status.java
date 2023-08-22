package com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_Models;

import java.io.File;

public class SC_Status {
    private File file;
    private boolean isVideo;
    private String path;
    private String title;

    public SC_Status() {

    }

    public SC_Status(File file2, String str, String str2) {
        this.file = file2;
        this.title = str;
        this.path = str2;

        this.isVideo = file2.getName().endsWith(".mp4");
    }

    public File getFile() {
        return this.file;
    }

    public void setFile(File file2) {
        this.file = file2;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String str) {
        this.path = str;
    }

    public boolean isVideo() {
        return this.isVideo;
    }

    public void setVideo(boolean z) {
        this.isVideo = z;
    }
}
