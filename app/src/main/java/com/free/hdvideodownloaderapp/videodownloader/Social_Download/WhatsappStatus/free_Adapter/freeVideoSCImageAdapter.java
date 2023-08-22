package com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.activities.freeFullStatusViewActivity;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_Models.SC_Status;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_Models.StatusModel;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_SCUtils.SC_Common1;
import com.free.hdvideodownloaderapp.videodownloader.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class freeVideoSCImageAdapter extends RecyclerView.Adapter<freeVideoSCItemViewHolder> {

    private final RelativeLayout a1;
    private Context context;
    private final List<StatusModel> c1;
    public static int ramu = 0;

    List<SC_Status> f1list = new ArrayList();
    ArrayList<String> imageDownloads = new ArrayList<>();
    ArrayList<String> mySteingList;

    public freeVideoSCImageAdapter(List<StatusModel> list, RelativeLayout relativeLayout, int chaku) {
        c1 = list;
        a1 = relativeLayout;

        f1list = new ArrayList();
        File file = new File(Environment.getExternalStorageDirectory() + "/Download/Video Downloader/Whatsapp/");
        File[] listFiles = file.listFiles();
        f1list = new ArrayList<>();
        if (listFiles == null || listFiles.length <= 0) {

        } else {
            Arrays.sort(listFiles);
            for (File file2 : listFiles) {
                this.f1list.add(new SC_Status(file2, file2.getName(), file2.getAbsolutePath()));
            }
        }
        mySteingList = new ArrayList<>();
        for (int z = 0; z < c1.size(); z++) {
            if (chaku == 1) {
                if (c1.get(z).getFilePath().contains(".jpg") || c1.get(z).getFilePath().contains(".png)")) {
                    mySteingList.add(c1.get(z).getFilePath());
                }
            }
            if (chaku == 2) {
                if (c1.get(z).getFilePath().contains(".mp4")) {
                    mySteingList.add(c1.get(z).getFilePath());
                }
            }
        }
    }

    @NonNull
    @Override
    public freeVideoSCItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Context context2 = viewGroup.getContext();
        this.context = context2;
        return new freeVideoSCItemViewHolder(LayoutInflater.from(context2).inflate(R.layout.wass_item_status, viewGroup, false));
    }

    public void onBindViewHolder(freeVideoSCItemViewHolder itemViewHolder, int i) {
        StatusModel d1 = this.c1.get(i);

        Glide.with(context).load(Uri.parse(d1.getFilePath())).into(itemViewHolder.imageView);
        String extension = d1.getFilePath().substring(d1.getFilePath().lastIndexOf("."));

        for (int z = 0; z < f1list.size(); z++) {
            String path = c1.get(itemViewHolder.getAdapterPosition()).getFilePath();
            String substring = path.substring(path.lastIndexOf("/") + 1);

            if (f1list.get(z).getTitle().toString().equalsIgnoreCase(substring)) {
                imageDownloads.add(substring);
                itemViewHolder.save.setImageResource(R.drawable.img_download);
                itemViewHolder.save.setClickable(false);
            }
        }

        if (extension.equalsIgnoreCase(".mp4")) {
            itemViewHolder.video_icon.setVisibility(View.VISIBLE);
            itemViewHolder.save.setVisibility(View.GONE);
        }

        itemViewHolder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                itemViewHolder.save.setImageResource(R.drawable.img_download);
                SC_Common1.download(context, d1.getFilePath());
                String btn_extension = d1.getFilePath().substring(d1.getFilePath().lastIndexOf("Statuses%"));
                String s = btn_extension.substring(9);
            }
        });

        itemViewHolder.share.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("image/*");
                intent.putExtra("android.intent.extra.STREAM", Uri.parse(d1.getFilePath()));
                context.startActivity(Intent.createChooser(intent, "Share"));

            }
        });

        itemViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                freeVideoFilesAdapterWhatsapp.bapuji = 1;
                View inflate = LayoutInflater.from(context).inflate(R.layout.wass_view_image_full_screen, (ViewGroup) null);
                Glide.with(context).load(Uri.parse(d1.getFilePath())).into((ImageView) inflate.findViewById(R.id.img));
                Intent intent = new Intent(context, freeFullStatusViewActivity.class);
                intent.putExtra("path", d1.getFilePath());
                intent.putExtra("myPagerList", mySteingList);
                intent.putExtra("myAdapterpos", itemViewHolder.getAdapterPosition());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.c1.size();
    }
}