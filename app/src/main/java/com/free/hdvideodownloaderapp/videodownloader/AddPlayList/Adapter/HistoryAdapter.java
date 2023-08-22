package com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.media.session.MediaControllerCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;


import com.free.hdvideodownloaderapp.videodownloader.Activity.free_Fragment_Tab_Activity;
import com.free.hdvideodownloaderapp.videodownloader.Activity.free_PlayListDetailActivity;
import com.free.hdvideodownloaderapp.videodownloader.Activity.free_VideoPlayingActivity;
import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.GetMedia;
import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.PlayerActVideoDetailsDialog;
import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.PlayerVideoModel;
import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.NewVoiceCacheListModel;
import com.free.hdvideodownloaderapp.videodownloader.Def.Common;
import com.free.hdvideodownloaderapp.videodownloader.Log;
import com.free.hdvideodownloaderapp.videodownloader.Utils.MyDatabase;
import com.free.hdvideodownloaderapp.videodownloader.Utils.MyUtility;
import com.free.hdvideodownloaderapp.videodownloader.Utils.SharedPrefsUtils;
import com.free.hdvideodownloaderapp.videodownloader.Utils.VideoUtils;
import com.free.hdvideodownloaderapp.videodownloader.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyFolder> {

    List<PlayerVideoModel> modelList;
    Activity activity;
    int my_vla;
    VideoUtils songsUtils;
    SharedPrefsUtils sharedPrefsUtils;

    public HistoryAdapter(Activity context, List<PlayerVideoModel> modelList, int my_vla) {
        this.activity = context;
        this.modelList = modelList;
        this.activity = (Activity) context;
        this.my_vla=my_vla;
        songsUtils = new VideoUtils(context);
        sharedPrefsUtils = new SharedPrefsUtils(context);
    }

    public MyFolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_grid, parent, false);
        return new MyFolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull MyFolder holder, @SuppressLint("RecyclerView") int position) {
        PlayerVideoModel model = modelList.get(position);
        holder.thume_g.setVisibility(View.VISIBLE);

        Glide.with(activity)
                .load(new File(model.getData()))
                .centerCrop()
                .placeholder(R.color.gray_them)
                  .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable @org.jetbrains.annotations.Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.thume_g.setVisibility(View.VISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.thume_g.setVisibility(View.GONE);
                        return false;
                    }
                }).into(holder.ivThumbnail);

        holder.tvName.setText(model.getDisplayName());
        holder.tvDuration.setText(model.getDuartion());
        holder.tvDate.setText(model.getDate());
        holder.tvSize.setText(model.getSize());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (MediaControllerCompat.getMediaController((free_Fragment_Tab_Activity.tab_activity)) != null) {
                    MediaControllerCompat.getMediaController((free_Fragment_Tab_Activity.tab_activity)).getTransportControls().stop();
                }

                // TODO: 03-02-23
                Intent intent = new Intent(activity, free_VideoPlayingActivity.class);
                Common.dataparse= (Serializable) modelList;
                Common.commonpos=position;
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                activity.startActivity(intent);
            }
        });

        holder.ivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoDetails(activity, model, position);
            }
        });
    }


    public int getItemCount() {
        return Math.min(modelList.size(), 10);
    }

    class MyFolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDuration, tvDate, tvSize,folder,resolution;
        ImageView ivThumbnail, ivMore,thume_g;

        public MyFolder(@NonNull View itemView) {
            super(itemView);
            ivThumbnail = itemView.findViewById(R.id.ivThumbnail);
            ivMore = itemView.findViewById(R.id.ivMore);
            thume_g = itemView.findViewById(R.id.thume_g);
            tvName = itemView.findViewById(R.id.tvName);
            tvDuration = itemView.findViewById(R.id.tvDuration);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvSize = itemView.findViewById(R.id.tvSize);

        }
    }

    private void videoDetails(Context context, PlayerVideoModel model, int i) {
        BottomSheetDialog sheetDialog = new BottomSheetDialog(context, R.style.BottomSheetDialog);
        sheetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        sheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        sheetDialog.setCancelable(true);
        sheetDialog.setContentView(R.layout.player_dialog_video_option);
        getId(sheetDialog);

        if (MyUtility.getFavoriteList(activity) != null) {
            for (int ii = 0; ii < MyUtility.getFavoriteList(activity).length; ii++) {

                if (model.getId().toString().equals("" + MyUtility.getFavoriteList(activity)[ii])) {
                    iv_notFav.setVisibility(View.GONE);
                    iv_Fav.setVisibility(View.VISIBLE);
                }
            }
        }

        if(my_vla==4) {

            remove.setVisibility(View.VISIBLE);
            add_playlist.setVisibility(View.GONE);

            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MyDatabase myDatabase=new MyDatabase(context);

                    List<NewVoiceCacheListModel> data = myDatabase.getSearchHistory();

                    JSONArray jsonArray = new JSONArray();
                    JSONObject ConversationObj = new JSONObject();

                    for(int i = 0; i<data.get(free_PlayListDetailActivity.posssss).getVoiceChatItems().size(); i++){
                        JSONObject student1 = new JSONObject();

                        if(model.getId().equals(""+data.get(free_PlayListDetailActivity.posssss).getVoiceChatItems().get(i).getLan1())){

                        }else {
                            try {
                                student1.put("video_id", data.get(free_PlayListDetailActivity.posssss).getVoiceChatItems().get(i).getLan1());
                                jsonArray.put(student1);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    try {
                        ConversationObj.put( "Conversation", jsonArray );
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    myDatabase.Update_converation( ConversationObj.toString(), "" + data.get(free_PlayListDetailActivity.posssss).getString().toString().trim() );
                    notifyDataSetChanged();
                    sheetDialog.dismiss();
                }
            });

        }else {
            add_playlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    add_playlist.setVisibility(View.VISIBLE);
                    remove.setVisibility(View.GONE);
                    songsUtils.addToPlaylist(model);
                    notifyDataSetChanged();

                    sheetDialog.dismiss();

                }
            });
        }

        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (iv_notFav.getVisibility() == View.VISIBLE) {
                    iv_notFav.setVisibility(View.GONE);
                    iv_Fav.setVisibility(View.VISIBLE);
                    MyUtility.addFavoriteItem(activity, "" + model.getId());
                    sheetDialog.dismiss();
                    notifyDataSetChanged();
                } else {
                    iv_notFav.setVisibility(View.VISIBLE);
                    iv_Fav.setVisibility(View.GONE);
                    MyUtility.removeFavoriteItem(activity, "" + model.getId());
                    if(my_vla==2){
                        modelList.remove(i);
                        notifyDataSetChanged();
                        sheetDialog.dismiss();
                    }
                }
                Log.e("#####", "" + model.getId());
                Log.e("#####", "MyUtility.getFavoriteList(activity)====" + MyUtility.getFavoriteList(activity).length);


            }
        });


        tvTitle.setText(model.getTitle());

        llShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetMedia.shareVideo(context, model.getDisplayName(), model.getData());
                sheetDialog.dismiss();
            }
        });

        llProperties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayerActVideoDetailsDialog.show(context, model);
                sheetDialog.dismiss();
            }
        });

        int finalI = i;
        llDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetMedia.moveToPrivate(context, model.getData(), model.getDisplayName());
                sheetDialog.dismiss();
                File file = new File(model.getData());
                String[] strArr = {file.getAbsolutePath()};
                ContentResolver contentResolver = context.getContentResolver();
                Uri contentUri = MediaStore.Files.getContentUri("external");
                contentResolver.delete(contentUri, "_data=?", strArr);
                if (file.exists()) {
                    contentResolver.delete(contentUri, "_data=?", strArr);
                }
                remove(finalI);
            }
        });

        sheetDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        sheetDialog.show();
    }

    TextView tvTitle;
    LinearLayout   llShare,  llProperties,  llDelete;
    ImageView iv_Fav, iv_notFav;
    LinearLayout favourite,add_playlist,remove;

    private void getId(BottomSheetDialog dialog) {
        tvTitle = dialog.findViewById(R.id.tvTitle);
        llShare = dialog.findViewById(R.id.llShare);
        llDelete = dialog.findViewById(R.id.llDelete);
        llProperties = dialog.findViewById(R.id.llProperties);
        iv_Fav = dialog.findViewById(R.id.iv_Fav);
        iv_notFav = dialog.findViewById(R.id.iv_notFav);
        favourite = dialog.findViewById(R.id.favourite);
        add_playlist = dialog.findViewById(R.id.add_playlist);
        remove = dialog.findViewById(R.id.remove);
    }

    public void remove(int i) {
        modelList.remove(i);
        notifyItemRemoved(i);
        notifyItemRangeChanged(i, modelList.size());
        Toast.makeText(activity, "file_suzzessfully_deleted", Toast.LENGTH_SHORT).show();
    }
}
