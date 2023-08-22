package com.free.hdvideodownloaderapp.videodownloader.Social_Download.popups;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.free.hdvideodownloaderapp.videodownloader.Social_Download.Models.File_type;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.free_Utils.free_UCommons;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.freeDownloaderService;
import com.free.hdvideodownloaderapp.videodownloader.R;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.Configs.SettingsManager;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.Models.downloadable_resource_model;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class RenameDialog extends AppCompatDialogFragment {

    EditText txtFileName;
    TextView btnDownload;
    private downloadable_resource_model result;
    private Context mContext;
    private Activity activity;

    public RenameDialog(downloadable_resource_model _result) {
        result = _result;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.rename_dialog, null);
        mContext = this.getContext();
        String redString = getResources().getString(R.string.Close);
        builder.setView(view).setPositiveButton(((Html.fromHtml(redString))), (dialogInterface, i) -> {});
        btnDownload = view.findViewById(R.id.btnDownloadNow);
        activity = getActivity();

        btnDownload.setOnClickListener(v -> {
            StringBuilder sb = new StringBuilder();
            sb.append(free_UCommons.SanitizeTitle(txtFileName.getText().toString()) + "_");
            sb.append(System.currentTimeMillis());

            if (result.getFile_type() == File_type.IMAGE) {
                String extension = "jpg";
                try {
                    extension = MimeTypeMap.getFileExtensionFromUrl(result.getURL());
                } catch (Exception ex) {
                }
                if (extension == null || extension.equals("")) {
                    extension = "jpg";
                }
                sb.append("." + extension);
            } else if (result.getFile_type() == File_type.VIDEO) {
                sb.append(".mp4");
            } else if (result.getFile_type() == File_type.AUDIO) {
                sb.append(".mp3");
            }

            if (result.getFile_type() == File_type.VIDEO && result.getURL().contains("m3u8")) {
                if (free_UCommons.isMyServiceRunning(freeDownloaderService.class, mContext)) {
                    if (!SettingsManager.IsDownloadComplete == true) {
                        new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText(mContext.getString(R.string.Wait))
                                .setContentText(mContext.getString(R.string.Pleasewaituntilfirstdownloadcomplete))
                                .show();
                    } else {
                        StartM3u8Service();
                    }
                } else {
                    StartM3u8Service();
                }
            } else {
                free_UCommons.startDownload(result.getURL(), "", mContext, sb.toString(), mContext, result.getFile_type());
                dismiss();
            }

        });
        txtFileName = view.findViewById(R.id.txtFileNameNew);
        txtFileName.setText(result.getTitle());
        return builder.create();
    }

    private void StartM3u8Service() {
        Intent serviceIntent = new Intent(mContext, freeDownloaderService.class);
        serviceIntent.putExtra("URL", result.getURL());
        serviceIntent.putExtra("FinalOutputFileName", result.getTitle());
        mContext.startService(serviceIntent);
        dismiss();
    }

}
