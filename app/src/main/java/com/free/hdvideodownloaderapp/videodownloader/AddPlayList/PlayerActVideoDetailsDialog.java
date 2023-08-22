package com.free.hdvideodownloaderapp.videodownloader.AddPlayList;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.PlayerVideoModel;
import com.free.hdvideodownloaderapp.videodownloader.R;

public class PlayerActVideoDetailsDialog {

    private static Dialog dialog;
    private static TextView tvTitle, tvFile, tvLocation, tvSize, tvDate, tvResolution, tvLength, tvDone;

    private static void getId() {
        tvTitle = dialog.findViewById(R.id.tvTitle);
        tvFile = dialog.findViewById(R.id.tvFile);
        tvLocation = dialog.findViewById(R.id.tvLocation);
        tvSize = dialog.findViewById(R.id.tvSize);
        tvDate = dialog.findViewById(R.id.tvDate);
        tvResolution = dialog.findViewById(R.id.tvResolution);
        tvLength = dialog.findViewById(R.id.tvLength);
        tvDone = dialog.findViewById(R.id.tvDone);
    }

    public static void show(Context context, PlayerVideoModel model) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_player_video_details);
        getId();
        tvTitle.setText(model.getTitle());
        tvFile.setText(model.getDisplayName());
        tvLocation.setText(model.getData());
        tvSize.setText(model.getSize());
        tvDate.setText(model.getDate());
        tvResolution.setText(model.getResolution());
        tvLength.setText(model.getDuartion());

        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}