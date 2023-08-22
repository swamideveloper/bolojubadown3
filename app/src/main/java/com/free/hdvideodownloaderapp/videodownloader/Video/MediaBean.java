package com.free.hdvideodownloaderapp.videodownloader.Video;

import android.os.Parcel;
import android.os.Parcelable;

import com.free.hdvideodownloaderapp.videodownloader.Def.HDVideo_bw;

import java.io.File;

public class MediaBean implements Parcelable {
    public static final Creator<MediaBean> CREATOR = new Creator<MediaBean>() {

        @Override
        public MediaBean createFromParcel(Parcel parcel) {
            return new MediaBean(parcel);
        }

        @Override
        public MediaBean[] newArray(int i) {
            return new MediaBean[i];
        }
    };
    public String bucketDisplayName;
    public String bucketId;
    public long createDate;
    public String dicriptpath;
    public String duration;
    public String encryptPath;
    public int height;
    public long id;
    public double latitude;
    public long length;
    public double longitude;
    public String mimeType;
    public long modifiedDate;
    public int orientation;
    public String originalPath;
    public int process;
    public String thumbnailBigPath;
    public String thumbnailSmallPath;
    public String title;
    public int width;

    public MediaBean() {
    }

    public MediaBean(Parcel parcel) {
        this.id = parcel.readLong();
        this.title = parcel.readString();
        this.originalPath = parcel.readString();
        this.createDate = parcel.readLong();
        this.modifiedDate = parcel.readLong();
        this.mimeType = parcel.readString();
        this.bucketId = parcel.readString();
        this.bucketDisplayName = parcel.readString();
        this.thumbnailBigPath = parcel.readString();
        this.thumbnailSmallPath = parcel.readString();
        this.width = parcel.readInt();
        this.height = parcel.readInt();
        this.latitude = parcel.readDouble();
        this.longitude = parcel.readDouble();
        this.orientation = parcel.readInt();
        this.length = parcel.readLong();
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        return obj != null && (obj instanceof MediaBean) && ((MediaBean) obj).getId() == getId();
    }

    public String getBucketDisplayName() {
        return this.bucketDisplayName;
    }

    public String getBucketId() {
        return this.bucketId;
    }

    public long getCreateDate() {
        return this.createDate;
    }

    public String getDuprectSmallPath() {
        return this.dicriptpath;
    }

    public String getDuration() {
        return this.duration;
    }

    public int getHeight() {
        return this.height;
    }

    public long getId() {
        return this.id;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public long getLength() {
        return this.length;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public long getModifiedDate() {
        return this.modifiedDate;
    }

    public int getOrientation() {
        return this.orientation;
    }

    public String getOriginalPath() {
        return this.originalPath;
    }

    public int getProgress() {
        return this.process;
    }

    public String getThumbnailBigPath() {
        return new File(this.thumbnailBigPath).exists() ? this.thumbnailBigPath : "";
    }

    public String getThumbnailSmallPath() {
        return new File(this.thumbnailSmallPath).exists() ? this.thumbnailSmallPath : "";
    }

    public String getTitle() {
        return this.title;
    }

    public int getWidth() {
        return this.width;
    }

    public String getencripySmallPath() {
        return this.encryptPath;
    }

    public void setBucketDisplayName(String str) {
        this.bucketDisplayName = str;
    }

    public void setBucketId(String str) {
        this.bucketId = str;
    }

    public void setCreateDate(long j) {
        this.createDate = j;
    }

    public void setDuprectSmallPath(String str) {
        this.dicriptpath = str;
    }

    public void setDuration(String str) {
        this.duration = str;
    }

    public void setHeight(int i) {
        this.height = i;
    }

    public void setId(long j) {
        this.id = j;
    }

    public void setLatitude(double d) {
        this.latitude = d;
    }

    public void setLength(long j) {
        this.length = j;
    }

    public void setLongitude(double d) {
        this.longitude = d;
    }

    public void setMimeType(String str) {
        this.mimeType = str;
    }

    public void setModifiedDate(long j) {
        this.modifiedDate = j;
    }

    public void setOrientation(int i) {
        this.orientation = i;
    }

    public void setOriginalPath(String str) {
        this.originalPath = str;
    }

    public void setProgress(int i) {
        this.process = i;
    }

    public void setThumbnailBigPath(String str) {
        this.thumbnailBigPath = str;
    }

    public void setThumbnailSmallPath(String str) {
        this.thumbnailSmallPath = str;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public void setWidth(int i) {
        this.width = i;
    }

    public void setencripySmallPath(String str) {
        this.encryptPath = str;
    }

    public String toString() {
        StringBuilder I = HDVideo_bw.I("MediaBean{id=");
        I.append(this.id);
        I.append(", title='");
        HDVideo_bw.g0(I, this.title, '\'', ", originalPath='");
        HDVideo_bw.g0(I, this.originalPath, '\'', ", createDate=");
        I.append(this.createDate);
        I.append(", modifiedDate=");
        I.append(this.modifiedDate);
        I.append(", mimeType='");
        HDVideo_bw.g0(I, this.mimeType, '\'', ", width=");
        I.append(this.width);
        I.append(", height=");
        I.append(this.height);
        I.append(", latitude=");
        I.append(this.latitude);
        I.append(", longitude=");
        I.append(this.longitude);
        I.append(", orientation=");
        I.append(this.orientation);
        I.append(", length=");
        I.append(this.length);
        I.append(", bucketId='");
        HDVideo_bw.g0(I, this.bucketId, '\'', ", bucketDisplayName='");
        HDVideo_bw.g0(I, this.bucketDisplayName, '\'', ", thumbnailBigPath='");
        HDVideo_bw.g0(I, this.thumbnailBigPath, '\'', ", thumbnailSmallPath='");
        I.append(this.thumbnailSmallPath);
        I.append('\'');
        I.append('}');
        return I.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.id);
        parcel.writeString(this.title);
        parcel.writeString(this.originalPath);
        parcel.writeLong(this.createDate);
        parcel.writeLong(this.modifiedDate);
        parcel.writeString(this.mimeType);
        parcel.writeString(this.bucketId);
        parcel.writeString(this.bucketDisplayName);
        parcel.writeString(this.thumbnailBigPath);
        parcel.writeString(this.thumbnailSmallPath);
        parcel.writeInt(this.width);
        parcel.writeInt(this.height);
        parcel.writeDouble(this.latitude);
        parcel.writeDouble(this.longitude);
        parcel.writeInt(this.orientation);
        parcel.writeLong(this.length);
    }
}
