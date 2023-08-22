package com.free.hdvideodownloaderapp.videodownloader.Video;

import android.text.TextUtils;

public class BucketBean {
    public String bucketId;
    public String bucketName;
    public String cover;
    public int imageCount;
    public int orientation;

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof BucketBean)) {
            return false;
        }
        return TextUtils.equals(((BucketBean) obj).getBucketId(), getBucketId());
    }

    public String getBucketId() {
        return this.bucketId;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public String getCover() {
        String str = this.cover;
        return str == null ? "" : str;
    }

    public int getImageCount() {
        return this.imageCount;
    }

    public int getOrientation() {
        return this.orientation;
    }

    public void setBucketId(String str) {
        this.bucketId = str;
    }

    public void setBucketName(String str) {
        this.bucketName = str;
    }

    public void setCover(String str) {
        this.cover = str;
    }

    public void setImageCount(int i) {
        this.imageCount = i;
    }

    public void setOrientation(int i) {
        this.orientation = i;
    }
}
