package com.free.hdvideodownloaderapp.videodownloader.Social_Download.Configs.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.Configs.SettingsManager;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.Models.File_type;
import com.free.hdvideodownloaderapp.videodownloader.BuildConfig;
import com.free.hdvideodownloaderapp.videodownloader.R;

import java.io.File;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class freeDownloadsAdapter extends RecyclerView.Adapter<freeDownloadsAdapter.SavedHolder> {

    private List<File> list_j;
    private Context context_j;
    File_type File_type_j;

    public freeDownloadsAdapter(List<File> list, Context context, File_type _file_type) {
        this.list_j = list;
        this.context_j = context;
        this.File_type_j = _file_type;
    }

    @NonNull
    @Override
    public SavedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context_j);
        View view = inflater.inflate(R.layout.download_item, parent, false);
        return new SavedHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedHolder holder, final int position) {
        holder.name.setText(list_j.get(position).getName());

        Uri u = Uri.fromFile(list_j.get(position).getAbsoluteFile());

        if(File_type_j== File_type.AUDIO){
            holder.video_icon.setVisibility(View.GONE);
        }
        else if (File_type_j== File_type.IMAGE){
            holder.video_icon.setVisibility(View.GONE);
        }
        else if(File_type_j== File_type.VIDEO){
            holder.video_icon.setVisibility(View.VISIBLE);
        }
        if (File_type_j == File_type.AUDIO) {
            u = Uri.parse("android.resource://" + context_j.getApplicationContext().getPackageName() + "/drawable/vec");
        }

        Glide.with(context_j)
                .asBitmap()
                .load(u)
                .into(holder.thumbnail);

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri fileUri = FileProvider.getUriForFile(context_j, context_j.getApplicationContext().getPackageName()
                        + ".fileprovider", list_j.get(position));
                if (File_type_j == File_type.VIDEO) {

                    intent.setDataAndType(fileUri, "video/*");
                } else if (File_type_j == File_type.IMAGE) {
                    intent.setDataAndType(fileUri, "image/*");
                } else if (File_type_j == File_type.AUDIO) {
                    intent.setDataAndType(fileUri, "audio/*");
                }
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                context_j.startActivity(intent);
            }
        });

        holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUp(view, list_j.get(position), File_type_j);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_j.size();
    }

    public static class SavedHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private ImageView thumbnail,video_icon;
        private ImageView menu;

        public SavedHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.downloadCompletedName);
            thumbnail = itemView.findViewById(R.id.downloadThumnail);
            menu = itemView.findViewById(R.id.download_menu);
            video_icon = itemView.findViewById(R.id.video_icon);
        }
    }

    private void popUp(View view, final File f, File_type type) {
        final PopupMenu popup = new PopupMenu(context_j.getApplicationContext(), view);
        popup.getMenuInflater().inflate(R.menu.download_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                int i = item.getItemId();
                if (i == R.id.download_delete) {
                    new AlertDialog.Builder(context_j, R.style.AlertDialogTheme)
                            .setMessage(context_j.getString(R.string.suretodelete))
                            .setPositiveButton(context_j.getString(R.string.yes), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (f.delete()) {
                                        updateList(type);
                                    }
                                }
                            })
                            .setNegativeButton(context_j.getString(R.string.no), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create()
                            .show();
                    return true;
                } else if (i == R.id.download_share) {
                    File file = f;
                    Uri uri = FileProvider.getUriForFile(context_j, BuildConfig.APPLICATION_ID + ".fileprovider", file);
                    Intent intentShareFile = new Intent(Intent.ACTION_SEND);
                    intentShareFile.setType(URLConnection.guessContentTypeFromName(file.getName()));
                    intentShareFile.putExtra(Intent.EXTRA_STREAM, uri);
                    context_j.startActivity(Intent.createChooser(intentShareFile, context_j.getString(R.string.share)));
                    return true;
                } else {
                    return onMenuItemClick(item);
                }
            }
        });
        popup.show();
    }

    public void updateList(File_type fileType) {
        File videoFile = null;
        if (fileType == File_type.VIDEO) {
            videoFile = new File(SettingsManager.DOWNLOAD_FOLDER_VIDEO);
        } else if (fileType == File_type.IMAGE) {
            videoFile = new File(SettingsManager.DOWNLOAD_FOLDER_IMAGES);
        } else if (fileType == File_type.AUDIO) {
            videoFile = new File(SettingsManager.DOWNLOAD_FOLDER_AUDIO);
        }
        if (videoFile.exists()) {
            List<File> nonExistentFiles = new ArrayList<>();
            nonExistentFiles.addAll(Arrays.asList(videoFile.listFiles()));
            this.list_j = nonExistentFiles;
            notifyDataSetChanged();
        }
    }
}
