package com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_SCUtils.WAV_Utils;
import com.free.hdvideodownloaderapp.videodownloader.R;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_Models.ModelWhatsappStatus;
import com.free.hdvideodownloaderapp.videodownloader.databinding.WassItemsWhatsappViewNewBinding;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class freeVideoAdapterWhatsappStatus extends RecyclerView.Adapter<freeVideoAdapterWhatsappStatus.ViewHolder> {
    public static String SaveFilePath = (WAV_Utils.RootDirectoryWhatsappShow + "/");
    private Context context;
    private ArrayList<ModelWhatsappStatus> fileArrayList;
    private LayoutInflater layoutInflater;

    public freeVideoAdapterWhatsappStatus(Context context2, ArrayList<ModelWhatsappStatus> arrayList) {
        this.context = context2;
        this.fileArrayList = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (this.layoutInflater == null) {
            this.layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        return new ViewHolder( DataBindingUtil.inflate(this.layoutInflater, R.layout.wass_items_whatsapp_view_new, viewGroup, false));
    }

    @SuppressLint("WrongConstant")
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final ModelWhatsappStatus whatsappStatusModel = this.fileArrayList.get(i);
        if (whatsappStatusModel.getUri().toString().endsWith(".mp4")) {
            viewHolder.binding.ivPlay.setVisibility(0);
        } else {
            viewHolder.binding.ivPlay.setVisibility(8);
        }
        Glide.with(this.context).load(whatsappStatusModel.getPath()).into(viewHolder.binding.pcw);
        viewHolder.binding.tvDownload.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                WAV_Utils.createFileFolder();
                String path = whatsappStatusModel.getPath();
                String substring = path.substring(path.lastIndexOf("/") + 1);
                try {
                    FileUtils.copyFileToDirectory(new File(path), new File(freeVideoAdapterWhatsappStatus.this.SaveFilePath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String substring2 = substring.substring(12);
                MediaScannerConnection.scanFile(freeVideoAdapterWhatsappStatus.this.context, new String[]{new File(freeVideoAdapterWhatsappStatus.this.SaveFilePath + substring2).getAbsolutePath()}, new String[]{whatsappStatusModel.getUri().toString().endsWith(".mp4") ? "video/*" : "image/*"}, new MediaScannerConnection.MediaScannerConnectionClient() {

                    public void onMediaScannerConnected() {
                    }

                    public void onScanCompleted(String str, Uri uri) {
                    }
                });
                new File(freeVideoAdapterWhatsappStatus.this.SaveFilePath, substring).renameTo(new File(freeVideoAdapterWhatsappStatus.this.SaveFilePath, substring2));
                Context context = freeVideoAdapterWhatsappStatus.this.context;
                Toast.makeText(context, freeVideoAdapterWhatsappStatus.this.context.getResources().getString(R.string.saved_to) + freeVideoAdapterWhatsappStatus.this.SaveFilePath + substring2, 1).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        ArrayList<ModelWhatsappStatus> arrayList = this.fileArrayList;
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        WassItemsWhatsappViewNewBinding binding;

        public ViewHolder(WassItemsWhatsappViewNewBinding itemsWhatsappViewBinding) {
            super(itemsWhatsappViewBinding.getRoot());
            this.binding = itemsWhatsappViewBinding;
        }
    }
}
