package com.free.hdvideodownloaderapp.videodownloader.mvvmbalaji;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public abstract class VD_CommonActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        set_onCreate_Common();
    }

    public abstract void set_onCreate_Common();

}
