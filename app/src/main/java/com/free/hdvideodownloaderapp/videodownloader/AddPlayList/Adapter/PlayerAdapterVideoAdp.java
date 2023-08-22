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
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.media.session.MediaControllerCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
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
import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.HistoryUtility;
import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.PlayerActVideoDetailsDialog;
import com.free.hdvideodownloaderapp.videodownloader.Def.Common;
import com.free.hdvideodownloaderapp.videodownloader.Fragment.free_VideoFragmentSecond;
import com.free.hdvideodownloaderapp.videodownloader.Log;
import com.free.hdvideodownloaderapp.videodownloader.Utils.MyDatabase;
import com.free.hdvideodownloaderapp.videodownloader.Utils.MyUtility;
import com.free.hdvideodownloaderapp.videodownloader.Utils.SharedPrefsUtils;
import com.free.hdvideodownloaderapp.videodownloader.Utils.VideoUtils;
import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.PlayerVideoModel;
import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.NewVoiceCacheListModel;
import com.free.hdvideodownloaderapp.videodownloader.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.sdk.ads.ads.AllNativeAds;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class PlayerAdapterVideoAdp extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<PlayerVideoModel> modelList;
    List<PlayerVideoModel> modelList_onlypass;
    private static final int LIST_ITEM = 0;
    private static final int LIST_ITEM_ADS = 2;
    private static final int GRID_ITEM = 1;
    private static final int GRID_ITEM_ADS = 3;

    boolean isSwitchView = true;
    Activity activity;
    int my_vla;
    VideoUtils songsUtils;
    SharedPrefsUtils sharedPrefsUtils;

    public PlayerAdapterVideoAdp(Context context, List<PlayerVideoModel> modelList, int my_vla, List<PlayerVideoModel> modelList_onlypass) {
        this.context = context;
        this.modelList = modelList;
        this.modelList_onlypass = modelList_onlypass;
        this.activity = (Activity) context;
        this.my_vla = my_vla;
        songsUtils = new VideoUtils(context);
        sharedPrefsUtils = new SharedPrefsUtils(context);
    }

    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = null;
        if (viewType == LIST_ITEM) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_item_video_list, parent, false);
            return new MyFolder(itemView);
        } else if (viewType == GRID_ITEM) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_item_video_grid, parent, false);
            return new MyFolder(itemView);
        } else if (viewType == LIST_ITEM_ADS) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_item_video_list_ads, parent, false);
            return new ADViewHolder(itemView);
        } else if (viewType == GRID_ITEM_ADS) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_item_video_grid_ads, parent, false);
            return new ADViewHolder(itemView);
        } else {
            return null;
        }

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder0, @SuppressLint("RecyclerView") int position) {

        if (getItemViewType(position) == LIST_ITEM || getItemViewType(position) == GRID_ITEM) {

            MyFolder holder = (MyFolder) holder0;

            PlayerVideoModel model = modelList.get(position);
            holder.thume_g.setVisibility(View.VISIBLE);
            Glide.with(context)
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
            holder.folder.setText(model.getFoldername());
            holder.tvDuration.setText(model.getDuartion());
            holder.tvDate.setText(model.getDate());
            holder.tvSize.setText(model.getSize());
            holder.resolution.setText(model.getResolution());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (MediaControllerCompat.getMediaController((free_Fragment_Tab_Activity.tab_activity)) != null) {
                        MediaControllerCompat.getMediaController((free_Fragment_Tab_Activity.tab_activity)).getTransportControls().stop();
                    }


                    int pos=0;
                    for (int i = 0; i < modelList_onlypass.size() ; i++) {
                        if(modelList_onlypass.get(i).getTitle().equals(modelList.get(position).getTitle())){
                            pos=i;
                            break;
                        }
                    }

                    int finalPos = pos;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            // TODO: 03-02-23 jinal;
                            Intent intent = new Intent(context, free_VideoPlayingActivity.class);
                            Common.dataparse= (Serializable) modelList_onlypass;
                            Common.commonpos=finalPos;
                            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            context.startActivity(intent);
                        }
                    },400);



                    Log.e("assss", "====" + model.getId());
                    HistoryUtility.addHistoryItem(activity, "" + model.getId());
                    HistoryUtility.removeFavoriteItem(activity);

                }
            });

            holder.ivMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    videoDetails(context, model, position);
                }
            });

        }else if(getItemViewType(position) == GRID_ITEM_ADS){
            ADViewHolder holder = (ADViewHolder) holder0;
            Log.e("dfdvfdvdf","1=="+position);

            AllNativeAds.NativeBanner((Activity) context, holder.adsContainer);

        } else if(getItemViewType(position) == LIST_ITEM_ADS){
            ADViewHolder holder = (ADViewHolder) holder0;
            Log.e("dfdvfdvdf","2=="+position);
            AllNativeAds.NativeBanner((Activity) context, holder.adsContainer);

        }

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public boolean toggleItemViewType() {
        isSwitchView = !isSwitchView;
        return isSwitchView;
    }

    @Override
    public int getItemViewType(int position) {
        if (isSwitchView) {
            free_VideoFragmentSecond.gridlist.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_grid));
            if (modelList.get(position) == null) {

                return LIST_ITEM_ADS;
            }
            return LIST_ITEM;

        } else {
            free_VideoFragmentSecond.gridlist.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_listview));
            if (modelList.get(position) == null) {

                return GRID_ITEM_ADS;
            }
            return GRID_ITEM;
        }

    }

    class MyFolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDuration, tvDate, tvSize, folder, resolution;
        ImageView ivThumbnail, ivMore,thume_g;

        public MyFolder(@NonNull View itemView) {
            super(itemView);
            ivThumbnail = itemView.findViewById(R.id.ivThumbnail);
            ivMore = itemView.findViewById(R.id.ivMore);
            tvName = itemView.findViewById(R.id.tvName);
            thume_g = itemView.findViewById(R.id.thume_g);
            tvDuration = itemView.findViewById(R.id.tvDuration);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvSize = itemView.findViewById(R.id.tvSize);
            folder = itemView.findViewById(R.id.folder);
            resolution = itemView.findViewById(R.id.resolution);
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

        if (my_vla == 4) {
            remove.setVisibility(View.VISIBLE);
            add_playlist.setVisibility(View.GONE);

            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MyDatabase myDatabase = new MyDatabase(context);
                    List<NewVoiceCacheListModel> data = myDatabase.getSearchHistory();
                    JSONArray jsonArray = new JSONArray();
                    JSONObject ConversationObj = new JSONObject();

                    for (int i = 0; i < data.get(free_PlayListDetailActivity.posssss).getVoiceChatItems().size(); i++) {
                        JSONObject student1 = new JSONObject();

                        if (model.getId().equals("" + data.get(free_PlayListDetailActivity.posssss).getVoiceChatItems().get(i).getLan1())) {

                        } else {
                            try {
                                student1.put("video_id", data.get(free_PlayListDetailActivity.posssss).getVoiceChatItems().get(i).getLan1());
                                jsonArray.put(student1);
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                    try {
                        ConversationObj.put("Conversation", jsonArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    myDatabase.Update_converation(ConversationObj.toString(), "" + data.get(free_PlayListDetailActivity.posssss).getString().toString().trim());

                    modelList.remove(i);
                    notifyDataSetChanged();

                    if(modelList.size()>0){
                        if(free_PlayListDetailActivity.txtNoData!=null){
                            free_PlayListDetailActivity.txtNoData.setVisibility(View.GONE);
                        }
                    }else {
                        if(free_PlayListDetailActivity.txtNoData!=null){
                            free_PlayListDetailActivity.txtNoData.setVisibility(View.VISIBLE);
                        }
                    }
                    sheetDialog.dismiss();
                }
            });

        } else {
            add_playlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    add_playlist.setVisibility(View.VISIBLE);
                    remove.setVisibility(View.GONE);
                    songsUtils.addToPlaylist(model);
                    notifyDataSetChanged();
                    sheetDialog.dismiss();
//                songsUtils.addToPlaylist(VideoUtils.queue().get(sharedPrefsUtils.readSharedPrefsInt("musicID", 0)));

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
                    if (my_vla == 2) {
                        modelList.remove(i);
                        notifyDataSetChanged();
                        sheetDialog.dismiss();
                    }
                }
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
    LinearLayout llMxShare, llLockVideo, llShare, llRename, llMxSearchSub, llProperties, llAddPlaylist, llDelete;
    ImageView iv_Fav, iv_notFav;
    LinearLayout favourite, add_playlist, remove;

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

    private String videoDate(String str) {
        return new SimpleDateFormat("dd MMM").format(new Date(Long.parseLong(str) * 1000));
    }

    public void remove(int i) {
        modelList.remove(i);
        notifyItemRemoved(i);
        notifyItemRangeChanged(i, modelList.size());
        Toast.makeText(context, "file successfully deleted", Toast.LENGTH_SHORT).show();
    }


    class ADViewHolder extends RecyclerView.ViewHolder {

        FrameLayout adsContainer;

        public ADViewHolder(@NonNull View itemView) {
            super(itemView);
            adsContainer=itemView.findViewById(R.id.adsContainer);
        }
    }
}
