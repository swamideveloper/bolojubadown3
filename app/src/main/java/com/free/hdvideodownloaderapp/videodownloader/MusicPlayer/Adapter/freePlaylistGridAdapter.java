package com.free.hdvideodownloaderapp.videodownloader.MusicPlayer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;


import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.SongModel;
import com.free.hdvideodownloaderapp.videodownloader.Utils.CommonUtils;
import com.free.hdvideodownloaderapp.videodownloader.Utils.SongsUtils;
import com.free.hdvideodownloaderapp.videodownloader.MusicPlayer.Activity.free_GlobalDetailActivity;
import com.free.hdvideodownloaderapp.videodownloader.MusicPlayer.Fragment.freePlaylistFragment;
import com.free.hdvideodownloaderapp.videodownloader.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class freePlaylistGridAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<HashMap<String, String>> mobileValues;
    private LayoutInflater inflater;
    private SongsUtils songsUtils;

    public freePlaylistGridAdapter(Context context) {
        this.context = context;
        songsUtils = new SongsUtils(context);
        this.mobileValues = songsUtils.getAllPlaylists();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        mobileValues.clear();
        mobileValues = songsUtils.getAllPlaylists();
        if (mobileValues.size() == 0) {
            freePlaylistFragment.txt_not.setVisibility(View.VISIBLE);
        } else {
            freePlaylistFragment.txt_not.setVisibility(View.GONE);
        }

    }

    private static class ViewHolder {
        TextView text, subtext;
        ImageView image, btn;
        CardView liner;
    }

    public View getView(final int position, final View convertView, ViewGroup parent) {
        ViewHolder holder;
        View gridView = convertView;

        if (convertView == null) {
            holder = new ViewHolder();
            gridView = inflater.inflate(R.layout.item_playlist, parent, false);

            holder.image = gridView.findViewById(R.id.grid_item_image2);
//            holder.image.setOutlineProvider(new ViewOutlineProvider() {
//                @Override
//                public void getOutline(View view, Outline outline) {
//                    outline.setRoundRect(0, 0, view.getWidth(), Math.round(view.getHeight()), 20F);
//                }
//            });
//            holder.image.setClipToOutline(true);
            holder.text = gridView.findViewById(R.id.grid_item_label);
            holder.subtext = gridView.findViewById(R.id.grid_item_sublabel);
//            holder.liner = gridView.findViewById(R.id.liner);

            holder.btn = gridView.findViewById(R.id.imageOverflow);
            gridView.setTag(holder);

//            if (position % 4 == 0) {
//                holder.liner.setBackground(context.getResources().getDrawable(R.drawable.color_back1));
//                holder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.music1));
//            } else if (position % 4 == 1) {
//                holder.liner.setBackground(context.getResources().getDrawable(R.drawable.color_back2));
//                holder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.music2));
//            } else if (position % 4 == 2) {
//                holder.liner.setBackground(context.getResources().getDrawable(R.drawable.color_back3));
//                holder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.music3));
//            } else if (position % 4 == 3) {
//                holder.liner.setBackground(context.getResources().getDrawable(R.drawable.color_back4));
//                holder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.music4));
//            }


//            holder.image.setImageResource(R.drawable.music_all);

        } else {
            holder = (ViewHolder) gridView.getTag();
        }
        if (getCount() >= 1) {
            holder.text.setText(mobileValues.get(position).get("title"));
            holder.subtext.setText(songsUtils.playlistSongs(Integer.parseInt(
                    Objects.requireNonNull(mobileValues.get(position).get("ID")))).size() + " tracks");

            final PopupMenu pop = new PopupMenu(context, holder.btn);
            int[] j = new int[5];
            j[0] = R.id.play_musicUtils;
            j[1] = R.id.play_next_musicUtils;
            j[2] = R.id.shuffle_play_musicUtils;
            j[3] = R.id.add_to_queue_musicUtils;
            j[4] = R.id.remove_musicUtils;
            songsUtils.generateMenu(pop, j);
            final ArrayList<SongModel> albumSongs;
            albumSongs = songsUtils.playlistSongs(Integer.parseInt(
                    Objects.requireNonNull(mobileValues.get(position).get("ID"))));
            pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.play_musicUtils:
                            if (albumSongs != null) {
                                if (albumSongs.size() == 0) {
                                    Toast.makeText(context, "Not Music Found", Toast.LENGTH_SHORT).show();
                                } else {
                                    songsUtils.play(context, 0, albumSongs);
                                }
                            } else {
                                Toast.makeText(context, "Not Music Found", Toast.LENGTH_SHORT).show();
                            }

                            return true;
                        case R.id.remove_musicUtils:
                            songsUtils.deletePlaylist(Integer.parseInt(
                                    Objects.requireNonNull(mobileValues.get(position).get("ID"))));
                            mobileValues.clear();
                            mobileValues = songsUtils.getAllPlaylists();
                            notifyDataSetChanged();
                            return true;
                        case R.id.play_next_musicUtils:

                            if (albumSongs != null) {
                                if (albumSongs.size() == 0) {
                                    Toast.makeText(context, "Not Music Found", Toast.LENGTH_SHORT).show();
                                } else {
                                    for (int i = albumSongs.size(); i > 0; i--) {
                                        songsUtils.playNext(albumSongs.get(i - 1));
                                    }
                                }
                            } else {
                                Toast.makeText(context, "Not Music Found", Toast.LENGTH_SHORT).show();
                            }

                            return true;
                        case R.id.add_to_queue_musicUtils:


                            if (albumSongs != null) {
                                if (albumSongs.size() == 0) {
                                    Toast.makeText(context, "Not Music Found", Toast.LENGTH_SHORT).show();
                                } else {
                                    songsUtils.addToQueue(albumSongs);
                                }
                            } else {
                                Toast.makeText(context, "Not Music Found", Toast.LENGTH_SHORT).show();
                            }
                            return true;
                        case R.id.shuffle_play_musicUtils:

                            if (albumSongs != null) {
                                if (albumSongs.size() == 0) {
                                    Toast.makeText(context, "Not Music Found", Toast.LENGTH_SHORT).show();
                                } else {
                                    songsUtils.shufflePlay(context, albumSongs);
                                }
                            } else {
                                Toast.makeText(context, "Not Music Found", Toast.LENGTH_SHORT).show();
                            }

                            return true;
                        default:
                            return false;
                    }
                }
            });

            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pop.show();
                }
            });

            gridView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (albumSongs.size() > 0) {
                        Intent intent = new Intent(context, free_GlobalDetailActivity.class);
                        intent.putExtra("id", position);
                        intent.putExtra("name", mobileValues.get(position).get("title"));
                        intent.putExtra("field", mobileValues.get(position).get("ID"));
                        context.startActivity(intent);
                    } else {
                        (new CommonUtils(context)).showTheToast("The list is empty.");
                    }
                }
            });
        }
        return gridView;
    }

    public int getCount() {
        if (mobileValues != null) {
            return mobileValues.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


}
