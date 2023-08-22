package com.free.hdvideodownloaderapp.videodownloader.Social_Download.Configs.Adapters;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.Models.downloadable_resource_model;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.Models.File_type;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.free__Statics.free_StaticVariables;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.popups.AvailableFilesDialog;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.popups.RenameDialog;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.popups.VideoPlayer;
import com.free.hdvideodownloaderapp.videodownloader.R;
import com.stfalcon.frescoimageviewer.ImageViewer;


public class freeResultHolderAdapter extends RecyclerView.Adapter<freeResultHolderAdapter.MyViewHolder> {
    File_type _type;
    private Context mContext;
    private Activity activity;

    public freeResultHolderAdapter(
            Context mContext,
            File_type _type,
            Activity mActivity) {
        this.mContext = mContext;
        this.activity = mActivity;
        this._type = _type;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.download_result_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final downloadable_resource_model result = free_StaticVariables.get_by_type_position(_type, position);

        if ((result.getFile_size() != null) && (!result.getFile_size().equals(""))) {
            holder.txtVidSize.setText(result.getFile_size());
        }
        if (result != null) {
            AvailableFilesDialog.nodata.setVisibility(View.GONE);
            AvailableFilesDialog.result_recycler_view.setVisibility(View.VISIBLE);
            holder.tv_film_name.setText(result.getTitle() + "");
            if (_type == File_type.IMAGE) {
                Glide.with(mContext).load(result.getURL()).into(holder.iv_poster);
            } else {
                if (result.getPreview() != null) {
                    holder.iv_poster.setImageBitmap(result.getPreview());
                }
            }

            holder.iv_poster.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (result.getFile_type() == File_type.IMAGE) {
                        String[] list = {result.getURL()};
                        new ImageViewer.Builder(mContext, list)
                                .show();
                    }
                }
            });

            holder.btnDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FragmentActivity activity = (FragmentActivity) (mContext);
                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    RenameDialog _rename_dialog = new RenameDialog(result);
                    _rename_dialog.show(fragmentManager, "TAG");

                }
            });

            holder.btnPreview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (result.getFile_type() == File_type.VIDEO || result.getFile_type() == File_type.AUDIO) {
                        FragmentActivity activity = (FragmentActivity) (mContext);
                        FragmentManager fragmentManager = activity.getSupportFragmentManager();
                        VideoPlayer player = new VideoPlayer(result);
                        player.show(fragmentManager, "TAG");
                    } else if (result.getFile_type() == File_type.IMAGE) {
                        String[] list = {result.getURL()};
                        new ImageViewer.Builder(mContext, list)
                                .show();
                    }
                }
            });
        }else{
            AvailableFilesDialog.nodata.setVisibility(View.VISIBLE);
            AvailableFilesDialog.result_recycler_view.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        return free_StaticVariables.get_downloadable_resource_model_By_Type(_type).size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView iv_poster;
        public TextView tv_film_name;
        public TextView btnDownload, btnPreview;
        public TextView txtVidSize;

        public MyViewHolder(@NonNull View view) {
            super(view);
            iv_poster = view.findViewById(R.id.iv_poster);
            tv_film_name = view.findViewById(R.id.tv_film_name);
            btnDownload = view.findViewById(R.id.btnDownload);
            txtVidSize = view.findViewById(R.id.txtVidSize);
            btnPreview = view.findViewById(R.id.btnPreview);
        }
    }
}
