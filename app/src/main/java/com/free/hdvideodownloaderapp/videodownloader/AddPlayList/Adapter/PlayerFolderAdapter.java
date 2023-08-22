package com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Adapter;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.free.hdvideodownloaderapp.videodownloader.Activity.free_Player_Act_VideoList;
import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.GetMedia;
import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.PlayerFolderModel;
import com.free.hdvideodownloaderapp.videodownloader.Fragment.free_VideoFragmentSecond;
import com.free.hdvideodownloaderapp.videodownloader.Log;
import com.free.hdvideodownloaderapp.videodownloader.R;

import java.util.List;

public class PlayerFolderAdapter extends RecyclerView.Adapter<PlayerFolderAdapter.MyFolder> {
//    List<Player_VideoModel> playerVideoModelList;
    GetMedia mPlayerPlayerproGetMedia;
    Context context;
    List<PlayerFolderModel> modelList;
    boolean isSwitchView = true;
    private static final int LIST_ITEM = 0;
    private static final int GRID_ITEM = 1;

    public PlayerFolderAdapter(Context context, List<PlayerFolderModel> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public MyFolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mPlayerPlayerproGetMedia = new GetMedia(context);

        View view;
        if (viewType == LIST_ITEM) {
            view = LayoutInflater.from(context).inflate(R.layout.player_item_folder_list, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_item_folder_grid, parent, false);
        }

        return new MyFolder(view);
    }

    public boolean toggleItemViewType() {
        isSwitchView = !isSwitchView;
        return isSwitchView;
    }

    @Override
    public int getItemViewType(int position) {
        if (isSwitchView) {
            free_VideoFragmentSecond.listlist.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_grid));
            return LIST_ITEM;
        } else {
            free_VideoFragmentSecond.listlist.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_listview));
            return GRID_ITEM;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyFolder holder, int position) {
        PlayerFolderModel model = modelList.get(position);
        holder.tvName.setText(model.getBucket());
        Log.e("Foldername", "===" + model.getBucket());

        if (model.getVideoCount().equals("1")) {
            holder.tvCount.setText(model.getVideoCount() + " Files");
        } else {
            holder.tvCount.setText(model.getVideoCount() + " Files");
        }
//        holder.tvSize.setText(model.getDate());

//        if (position % 4 == 0) {
//            holder.liner.setBackground(context.getResources().getDrawable(R.drawable.color_back1));
//            holder.img.setImageDrawable(context.getResources().getDrawable(R.drawable.color_folder1));
//        } else if (position % 4 == 1) {
//            holder.liner.setBackground(context.getResources().getDrawable(R.drawable.color_back2));
//            holder.img.setImageDrawable(context.getResources().getDrawable(R.drawable.color_folder2));
//        } else if (position % 4 == 2) {
//            holder.liner.setBackground(context.getResources().getDrawable(R.drawable.color_back3));
//            holder.img.setImageDrawable(context.getResources().getDrawable(R.drawable.color_folder3));
//        } else if (position % 4 == 3) {
//            holder.liner.setBackground(context.getResources().getDrawable(R.drawable.color_back4));
//            holder.img.setImageDrawable(context.getResources().getDrawable(R.drawable.color_folder4));
//        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("BID", "onClick: "+  model.getBid());
                context.startActivity(new Intent(context, free_Player_Act_VideoList.class)
                        .putExtra("Bucket", model.getBid()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    class MyFolder extends RecyclerView.ViewHolder {
        TextView tvName, tvCount;
        ImageView img;

        public MyFolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvCount = itemView.findViewById(R.id.tvCount);
            img = itemView.findViewById(R.id.img);
        }
    }

}
