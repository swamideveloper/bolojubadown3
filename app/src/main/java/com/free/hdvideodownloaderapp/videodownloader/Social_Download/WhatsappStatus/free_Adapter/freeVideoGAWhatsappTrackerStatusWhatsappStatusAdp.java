package com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_Adapter;


import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.activities.freeFullStatusViewActivity;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_Models.SC_Status;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_Models.TrishulWhatsappStatusModel;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_SCUtils.SC_Common1;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_SCUtils.WAV_Utils;
import com.free.hdvideodownloaderapp.videodownloader.R;
import com.free.hdvideodownloaderapp.videodownloader.databinding.WassItemsWhatsappViewNewBinding;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static android.view.View.GONE;


public class freeVideoGAWhatsappTrackerStatusWhatsappStatusAdp
        extends RecyclerView.Adapter<freeVideoGAWhatsappTrackerStatusWhatsappStatusAdp.ViewHolder> {

    public String SaveFilePath = (SC_Common1.RootDirectoryWhatsappShow + "/");
    private Context context;
    private ArrayList<TrishulWhatsappStatusModel> fileArrayList;
    private LayoutInflater layoutInflater;
    public static int videoORimage;
    ArrayList<String> pagerList = new ArrayList<>();
    ArrayList<String> imageDownloads = new ArrayList<>();
    List<SC_Status> f1list = new ArrayList();
    String getdataPath;
    public static int below11Download;
    ArrayList<String> mySteingList;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public freeVideoGAWhatsappTrackerStatusWhatsappStatusAdp(Context context2, ArrayList<TrishulWhatsappStatusModel> arrayList, int chaku) {
        this.context = context2;
        this.fileArrayList = arrayList;

        f1list = new ArrayList();
        File file = new File(Environment.getExternalStorageDirectory() + "/Download/Video Downloader/Whatsapp/");
        File[] listFiles = file.listFiles();
        f1list = new ArrayList<>();
        if (listFiles == null || listFiles.length <= 0) {
        } else {
            Arrays.sort(listFiles, Comparator.comparingLong(File::lastModified).reversed());
            for (File file2 : listFiles) {
                this.f1list.add(new SC_Status(file2, file2.getName(), file2.getAbsolutePath()));
            }
        }
        mySteingList = new ArrayList<>();

        for (int z = 0; z < fileArrayList.size(); z++) {

            if (chaku == 1) {
                if (fileArrayList.get(z).getPath().contains(".jpg") || fileArrayList.get(z).getPath().contains(".png)")) {
                    mySteingList.add(fileArrayList.get(z).getPath());
                }
            }
            if (chaku == 2) {
                if (fileArrayList.get(z).getPath().contains(".mp4")) {
                    mySteingList.add(fileArrayList.get(z).getPath());
                }
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (this.layoutInflater == null) {
            this.layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        return new ViewHolder(DataBindingUtil.inflate(this.layoutInflater, R.layout.wass_items_whatsapp_view_new, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        final TrishulWhatsappStatusModel whatsappStatusModel = fileArrayList.get(viewHolder.getAdapterPosition());

        for (int z = 0; z < f1list.size(); z++) {
            getdataPath = fileArrayList.get(viewHolder.getAdapterPosition()).getPath();
            String substring = getdataPath.substring(getdataPath.lastIndexOf("/") + 1);
            if (f1list.get(z).getTitle().toString().equalsIgnoreCase(substring)) {
                imageDownloads.add(substring);
                viewHolder.binding.tvDownload.setImageResource(R.drawable.wass_download_saver);
                viewHolder.binding.tvDownload.setClickable(false);
            }
        }

        if (whatsappStatusModel.getUri().toString().endsWith(".mp4")) {
            viewHolder.binding.ivPlay.setVisibility(View.VISIBLE);
        } else {
            viewHolder.binding.ivPlay.setVisibility(GONE);
        }

        Glide.with(context).load(whatsappStatusModel.getPath()).into(viewHolder.binding.pcw);

        viewHolder.binding.linClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                freeVideoFilesAdapterWhatsapp.bapuji = 1;
                below11Download = 1;
                WAV_Utils.createFileFolder();
                String path = whatsappStatusModel.getPath();
                Intent intent = new Intent(context, freeFullStatusViewActivity.class);
                intent.putExtra("path", path);
                intent.putExtra("myList", imageDownloads);
                intent.putExtra("myAdapterpos", viewHolder.getAdapterPosition());
                intent.putExtra("myPagerList", mySteingList);
                context.startActivity(intent);
            }
        });
        viewHolder.binding.tvDownload.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                viewHolder.binding.tvDownload.setImageResource(R.drawable.wass_download_saver);
                String path = whatsappStatusModel.getPath();
                WAV_Utils.createFileFolder();
                String substring = path.substring(path.lastIndexOf("/") + 1);
                try {
                    FileUtils.copyFileToDirectory(new File(path), new File(freeVideoGAWhatsappTrackerStatusWhatsappStatusAdp.this.SaveFilePath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String substring2 = substring;
                MediaScannerConnection.scanFile(freeVideoGAWhatsappTrackerStatusWhatsappStatusAdp.this.context, new String[]{new File(freeVideoGAWhatsappTrackerStatusWhatsappStatusAdp.this.SaveFilePath + substring2).getAbsolutePath()}, new String[]{whatsappStatusModel.getUri().toString().endsWith(".mp4") ? "video/*" : "image/*"}, new MediaScannerConnection.MediaScannerConnectionClient() {
                    public void onMediaScannerConnected() {
                    }

                    public void onScanCompleted(String str, Uri uri) {
                    }
                });

                new File(SaveFilePath, substring).renameTo(new File(SaveFilePath, substring2));
                Context context = freeVideoGAWhatsappTrackerStatusWhatsappStatusAdp.this.context;
                Toast.makeText(context, "Downloading Success", Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        ArrayList<TrishulWhatsappStatusModel> arrayList = this.fileArrayList;
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
