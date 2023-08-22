package com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public abstract class freeSCBaseActivity extends AppCompatActivity {

    public abstract int setLayout();

    public abstract void setView();

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(setLayout());

        try {
            setLayout();
            setView();
        } catch (Exception e) {
            Toast.makeText(freeSCBaseActivity.this, "Your device can't support this service!", Toast.LENGTH_SHORT).show();
        }

    }
}
