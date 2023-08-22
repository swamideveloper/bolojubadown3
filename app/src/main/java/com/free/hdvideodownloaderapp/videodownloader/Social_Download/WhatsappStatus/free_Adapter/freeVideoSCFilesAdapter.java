package com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_Adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.free.hdvideodownloaderapp.videodownloader.Log;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.activities.freeFullStatusViewActivity;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_Models.SC_Status;
import com.free.hdvideodownloaderapp.videodownloader.BuildConfig;
import com.free.hdvideodownloaderapp.videodownloader.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class freeVideoSCFilesAdapter extends RecyclerView.Adapter<freeVideoSCItemViewHolder> {

    private Context context;
    private List<SC_Status> b1;
    public static String substringZ;
    ArrayList<String> mySteingList;
    public static int ramu = 0;

    public freeVideoSCFilesAdapter(List<SC_Status> list) {
        try {
            this.b1 = list;
            mySteingList = new ArrayList<>();
            for (int z = 0; z < b1.size(); z++) {
                mySteingList.add(b1.get(z).getPath());
            }
        } catch (Exception e) {

        }
    }

    @Override
    public freeVideoSCItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Context context2 = viewGroup.getContext();
        this.context = context2;
        return new freeVideoSCItemViewHolder(LayoutInflater.from(context2).inflate(R.layout.wass_item_saved_files, viewGroup, false));
    }

    public void onBindViewHolder(freeVideoSCItemViewHolder itemViewHolder, @SuppressLint("RecyclerView") int i) {

        final SC_Status status = this.b1.get(i);
        String path = status.getPath();
        substringZ = path.substring(path.lastIndexOf("/") + 1);

        if (status.isVideo()) {
            Glide.with(this.context).load(status.getFile()).into(itemViewHolder.imageView);

            itemViewHolder.video_icon.setVisibility(View.VISIBLE);
        } else {
            Glide.with(context).load(status.getFile()).into(itemViewHolder.imageView);
        }

        itemViewHolder.save.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            public final void onClick(View view) {
                if (status.getFile().delete()) {
                    ramu = 0;
                    b1.remove(i);
                    notifyDataSetChanged();

                    Toast.makeText(context, "File Deleted", 0).show();
                    return;
                }
                Toast.makeText(context, "Unable to Delete File", 0).show();
            }
        });

        itemViewHolder.share.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                Intent intent = new Intent("android.intent.action.SEND");
                if (status.isVideo()) {
                    intent.setType("image/mp4");
                } else {
                    intent.setType("image/*");
                }
                Uri uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileprovider", new File(status.getFile().getAbsolutePath()));
                intent.putExtra("android.intent.extra.STREAM", uri);
                context.startActivity(Intent.createChooser(intent, "Share"));
            }
        });

        itemViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                freeVideoFilesAdapterWhatsapp.bapuji = 2;
                Intent intent = new Intent(context, freeFullStatusViewActivity.class);
                intent.putExtra("path", status.getFile().getAbsolutePath());
                intent.putExtra("myAdapterpos", itemViewHolder.getAdapterPosition());
                intent.putExtra("myPagerList", mySteingList);
                Log.e("VideoIconError", "onClick: " + status.getFile().getAbsolutePath());
                Log.e("VideoIconError", "onClick:khali-- " + status.toString());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.b1.size();
    }
}
