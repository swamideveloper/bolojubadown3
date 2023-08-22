package com.free.hdvideodownloaderapp.videodownloader.MusicPlayer.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.free.hdvideodownloaderapp.videodownloader.Def.HDVideo_bw;
import com.free.hdvideodownloaderapp.videodownloader.Video.BucketBean;
import com.free.hdvideodownloaderapp.videodownloader.R;

import java.util.List;

public class freeAudioFolderAdapter extends RecyclerView.Adapter<freeAudioFolderAdapter.MyViewHolder> {

    public List<Object> arrayView;
    public Context context;
    public int ist = 0;
    public LoadPicAdapterListener listener;
    public String type;
    public View view;

    public interface LoadPicAdapterListener {
        void onClick(View view, int i);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView imgname;
        public TextView imgname_pic;
        public RelativeLayout linmain,liner;
        ImageView image;

        public MyViewHolder(View view) {
            super(view);
            imgname = view.findViewById(R.id.txt_titile);
            imgname_pic = view.findViewById(R.id.imgname_pic);
            linmain = view.findViewById(R.id.linmain);
            image = view.findViewById(R.id.image);
//            liner = view.findViewById(R.id.liner);
        }
    }

    public freeAudioFolderAdapter(Context context2, List<Object> list, LoadPicAdapterListener loadPicAdapterListener, String str) {
        context = context2;
        arrayView = list;
        listener = loadPicAdapterListener;
        type = str;
        for (int i = 0; i < list.size(); i++) {
            StringBuilder I = HDVideo_bw.I("");
            I.append(this.ist);
        }
    }

    @Override
    public int getItemCount() {
        return this.arrayView.size();
    }

    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public void onBindViewHolder(MyViewHolder d0Var, @SuppressLint("RecyclerView") final int i) {
        TextView textView;
        StringBuilder sb = new StringBuilder();
        TextView textView2;
        String str;

        MyViewHolder myViewHolder =  d0Var;
        BucketBean bucketBean = (BucketBean) this.arrayView.get(i);
        String str2 = "Root";
        if (bucketBean.getBucketName() == null || bucketBean.getBucketName().equals("0")) {
            textView = myViewHolder.imgname;
        } else {
            textView = myViewHolder.imgname;
            str2 = bucketBean.getBucketName();
        }
        textView.setText(str2);
        if (this.type.equals("Audios")) {
            textView2 = myViewHolder.imgname_pic;
            sb.append(bucketBean.getImageCount());
            str = " Audios";
        } else {
            textView2 = myViewHolder.imgname_pic;
            sb.append(bucketBean.getImageCount());
            str = " Songs";
        }
        HDVideo_bw.j0(sb, str, textView2);

//        if (i % 4 == 0) {
//            myViewHolder.liner.setBackground(context.getResources().getDrawable(R.drawable.color_back1));
//            myViewHolder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.color_folder1));
//        } else if (i % 4 == 1) {
//            myViewHolder.liner.setBackground(context.getResources().getDrawable(R.drawable.color_back2));
//            myViewHolder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.color_folder2));
//        } else if (i % 4 == 2) {
//            myViewHolder.liner.setBackground(context.getResources().getDrawable(R.drawable.color_back3));
//            myViewHolder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.color_folder3));
//        } else if (i % 4 == 3) {
//            myViewHolder.liner.setBackground(context.getResources().getDrawable(R.drawable.color_back4));
//            myViewHolder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.color_folder4));
//        }

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                LoadPicAdapterListener loadPicAdapterListener = freeAudioFolderAdapter.this.listener;
                if (loadPicAdapterListener != null) {
                    loadPicAdapterListener.onClick(view, i);
                }
            }
        });
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        this.view = LayoutInflater.from(this.context).inflate(R.layout.list_folder_3, viewGroup, false);
        return new MyViewHolder(this.view);
    }
}
