package com.free.hdvideodownloaderapp.videodownloader.Social_Download.freeActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.free.hdvideodownloaderapp.videodownloader.R;
import com.sdk.ads.ads.IntertitialAdsEvent;
import com.sdk.ads.ads.AllNativeAds;

public class freeFeedbackActivity extends AppCompatActivity {

    TextView submit_j;
    ImageView back_j;
    EditText feedback_j;
    CheckBox checkBox1_j;
    CheckBox checkBox2_j;
    CheckBox checkBox3_j;
    CheckBox checkBox4_j;

    @Override
    public void onBackPressed() {
        IntertitialAdsEvent.ShowInterstitialAdsOnBack(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        ads();
        submit_j = findViewById(R.id.submit);
        back_j = findViewById(R.id.ivBack);
        feedback_j = findViewById(R.id.et_feedback);
        checkBox1_j = findViewById(R.id.cb_cant_browse);
        checkBox2_j = findViewById(R.id.cb_no_download_resources);
        checkBox3_j = findViewById(R.id.cb_many_ads);
        checkBox4_j = findViewById(R.id.cb_other);

        back_j.setOnClickListener(view -> onBackPressed());

        feedback_j.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.length() >= 6) {
                    submit_j.setVisibility(View.VISIBLE);
                } else {
                    submit_j.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        submit_j.setOnClickListener(view -> {

            StringBuilder result = new StringBuilder();

            if (checkBox1_j.isChecked()) {
                result.append("\n" + checkBox1_j.getText());
            }

            if (checkBox2_j.isChecked()) {
                result.append("\n" + checkBox2_j.getText());
            }

            if (checkBox3_j.isChecked()) {
                result.append("\n" + checkBox3_j.getText());
            }

            if (checkBox4_j.isChecked()) {
                result.append("\n" + checkBox4_j.getText());
            }

            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"pazabiulla@gmail.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback for " + getString(R.string.app_name));
            intent.putExtra(Intent.EXTRA_TEXT, "ID: " + getPackageName() + "\n\n"
                    + result + "\n\n\n" + feedback_j.getText());
            try {
                startActivity(Intent.createChooser(intent, "send mail"));
                finish();
            } catch (ActivityNotFoundException ex) {
                Toast.makeText(this, "No mail app found!!!", Toast.LENGTH_SHORT).show();
            } catch (Exception ex) {
                Toast.makeText(this, "Unexpected Error!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ads() {
        
        IntertitialAdsEvent.CallInterstitial(this);
IntertitialAdsEvent.ShowInterstitialAdsOnCreate(this);
        AllNativeAds.NativeBanner(this, findViewById(R.id.adsContainer));

    }

}