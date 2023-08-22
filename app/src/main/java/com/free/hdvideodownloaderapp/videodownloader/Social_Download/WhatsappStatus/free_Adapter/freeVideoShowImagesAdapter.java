package com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.core.content.FileProvider;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.activities.freeFullViewActivityGAWhatsappTracker;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_SCUtils.WAV_Utils;
import com.free.hdvideodownloaderapp.videodownloader.BuildConfig;
import com.free.hdvideodownloaderapp.videodownloader.R;

import java.io.File;
import java.util.ArrayList;


public class freeVideoShowImagesAdapter extends PagerAdapter {
    public Context context;
    freeFullViewActivityGAWhatsappTracker fullViewActivity;
    public ArrayList<File> imageList;
    private LayoutInflater inflater;


    public int getItemPosition(Object obj) {
        return -2;
    }

    public void restoreState(Parcelable parcelable, ClassLoader classLoader) {
    }

    public Parcelable saveState() {
        return null;
    }

    public freeVideoShowImagesAdapter(Context context2, ArrayList<File> arrayList, freeFullViewActivityGAWhatsappTracker fullViewActivity2) {
        this.context = context2;

        this.imageList = arrayList;
        this.fullViewActivity = fullViewActivity2;
        this.inflater = LayoutInflater.from(context2);
    }

    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        viewGroup.removeView((View) obj);
    }

    public Object instantiateItem(ViewGroup viewGroup, final int i) {
        View inflate = this.inflater.inflate(R.layout.wass_slidingimages_layout, viewGroup, false);
        ImageView imageView = inflate.findViewById(R.id.im_vpPlay);
        ImageView imageView2 = inflate.findViewById(R.id.im_share);
        ImageView imageView3 = inflate.findViewById(R.id.im_delete);

        Glide.with(this.context).load(this.imageList.get(i).getPath()).into((ImageView) inflate.findViewById(R.id.im_fullViewImage));
        viewGroup.addView(inflate, 0);

        if (this.imageList.get(i).getName().substring(this.imageList.get(i).getName().lastIndexOf(".")).equals(".mp4")) {
            imageView.setVisibility(View.VISIBLE);

        } else {
            imageView.setVisibility(View.GONE);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final File videoFile = new File(imageList.get(i).getPath());
                Uri fileUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileprovider", videoFile);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(fileUri, "video/*");
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//DO NOT FORGET THIS EVER
                context.startActivity(intent);
            }
        });


        imageView3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (( imageList.get(i)).delete()) {
                    fullViewActivity.deleteFileAA(i);
                }
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (( imageList.get(i)).getName().substring(( imageList.get(i)).getName().lastIndexOf(".")).equals(".mp4")) {
                    WAV_Utils.shareVideo(context, ( imageList.get(i)).getPath());
                } else {
                    WAV_Utils.shareImage(context, ( imageList.get(i)).getPath());
                }
            }
        });

        return inflate;
    }


    public int getCount() {
        return this.imageList.size();
    }

    public boolean isViewFromObject(View view, Object obj) {
        return view.equals(obj);
    }
}
