package com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.activities.freeFullViewActivityGAWhatsappTracker;
import com.free.hdvideodownloaderapp.videodownloader.R;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_Models.SC_Status;

import java.io.File;
import java.util.List;


public class freeVideoFilesAdapterWhatsapp extends RecyclerView.Adapter<freeVideoSCItemViewHolder> {

    private Context context;
     List<SC_Status> b1;
    public static int ramu = 0;
    public static int bapuji;
    public freeVideoFilesAdapterWhatsapp(List<SC_Status> list) {
        this.b1 = list;
    }

    @Override
    public freeVideoSCItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Context context2 = viewGroup.getContext();
        this.context = context2;
        return new freeVideoSCItemViewHolder(LayoutInflater.from(context2).inflate(R.layout.wass_item_saved_files, viewGroup, false));
    }

    public void onBindViewHolder(freeVideoSCItemViewHolder itemViewHolder, @SuppressLint("RecyclerView") int i) {

        itemViewHolder.save.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.ic_baseline_delete_24));
        itemViewHolder.share.setVisibility(View.VISIBLE);
        itemViewHolder.save.setVisibility(View.VISIBLE);
        final SC_Status status = this.b1.get(i);

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
                Uri uri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".fileprovider", new File(status.getFile().getAbsolutePath()));
                intent.putExtra("android.intent.extra.STREAM", uri);
                context.startActivity(Intent.createChooser(intent, "Share"));
            }
        });

        itemViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String s = status.getFile().getAbsolutePath();
                Intent intent = new Intent(context, freeFullViewActivityGAWhatsappTracker.class);
                intent.putExtra("path", s);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return this.b1.size();
    }
}
