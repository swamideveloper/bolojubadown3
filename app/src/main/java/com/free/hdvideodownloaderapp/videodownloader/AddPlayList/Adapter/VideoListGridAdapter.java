package com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.PopupMenu;

import com.free.hdvideodownloaderapp.videodownloader.Activity.free_PlayListDetailActivity;
import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Fragment.free_VideoPlaylistFragment;
import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.NewVoiceCacheListModel;
import com.free.hdvideodownloaderapp.videodownloader.Utils.CommonUtils;
import com.free.hdvideodownloaderapp.videodownloader.Utils.MyDatabase;
import com.free.hdvideodownloaderapp.videodownloader.Video.Utils;
import com.free.hdvideodownloaderapp.videodownloader.R;

import java.util.List;

public class VideoListGridAdapter extends BaseAdapter {

    private Context context;
    private List<NewVoiceCacheListModel> mobileValues;
    private LayoutInflater inflater;
    MyDatabase myDatabase;

    public VideoListGridAdapter(Context context) {
        this.context = context;
        myDatabase=new MyDatabase(context);
        this.mobileValues = myDatabase.getSearchHistory();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        mobileValues.clear();
        mobileValues =  myDatabase.getSearchHistory();
    }

    private static class ViewHolder {
        TextView text, subtext;
        ImageView img, btn;
        RelativeLayout liner;
    }

    public View getView(final int position, final View convertView, ViewGroup parent) {
        ViewHolder holder;
        View gridView = convertView;
        if (convertView == null) {
            holder = new ViewHolder();
            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.videoplaylist_item, parent, false);
            holder.img = gridView.findViewById(R.id.img);
            holder.liner = gridView.findViewById(R.id.liner);
//            holder.image.setOutlineProvider(new ViewOutlineProvider() {
//                @Override
//                public void getOutline(View view, Outline outline) {
//                    outline.setRoundRect(0, 0, view.getWidth(), Math.round(view.getHeight()), 20F);
//                }
//            });
//            holder.image.setClipToOutline(true);
            holder.text = gridView.findViewById(R.id.grid_item_label);
            holder.subtext = gridView.findViewById(R.id.grid_item_sublabel);
            holder.btn = gridView.findViewById(R.id.imageOverflow);
            gridView.setTag(holder);
        } else {
            holder = (ViewHolder) gridView.getTag();
        }
        if (getCount() >= 1) {

//            if (position % 4 == 0) {
//                holder.liner.setBackground(context.getResources().getDrawable(R.drawable.color_back1));
//                holder.img.setImageDrawable(context.getResources().getDrawable(R.drawable.color_folder1));
//            } else if (position % 4 == 1) {
//                holder.liner.setBackground(context.getResources().getDrawable(R.drawable.color_back2));
//                holder.img.setImageDrawable(context.getResources().getDrawable(R.drawable.color_folder2));
//            } else if (position % 4 == 2) {
//                holder.liner.setBackground(context.getResources().getDrawable(R.drawable.color_back3));
//                holder.img.setImageDrawable(context.getResources().getDrawable(R.drawable.color_folder3));
//            } else if (position % 4 == 3) {
//                holder.liner.setBackground(context.getResources().getDrawable(R.drawable.color_back4));
//                holder.img.setImageDrawable(context.getResources().getDrawable(R.drawable.color_folder4));
//            }


            holder.text.setText(mobileValues.get(position).getString());
            holder.subtext.setText(mobileValues.get(position).getVoiceChatItems().size() + " tracks");

            final PopupMenu pop = new PopupMenu(context, holder.btn);
            int[] j = new int[1];
            j[0] = R.id.remove_musicUtils;

            int i = 0;
            while (i < j.length) {
                String name = "";
                if (j[i] == R.id.remove_musicUtils) {
                    name = "Remove";
                }
                pop.getMenu().add(0, j[i], 1, name);
                MenuItem menuItem = pop.getMenu().getItem(i);
                CharSequence menuTitle = menuItem.getTitle();
                SpannableString styledMenuTitle = new SpannableString(menuTitle);
                styledMenuTitle.setSpan(new ForegroundColorSpan(Color.parseColor("#333333")), 0, menuTitle.length(), 0);
                menuItem.setTitle(styledMenuTitle);
                i++;
            }

            pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.remove_musicUtils:
                            myDatabase.Delete_History_id(mobileValues.get(position).getId());
                            notifyDataSetChanged();
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
                    if (mobileValues.get(position).getVoiceChatItems().size() > 0) {
                        Intent intent = new Intent(context, free_PlayListDetailActivity.class);
                        intent.putExtra("id", position);
                        intent.putExtra("paly_name", mobileValues.get(position).getString());
                        Utils.my_list=mobileValues.get(position).getVoiceChatItems();
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
            if(mobileValues.size()>0){
                free_VideoPlaylistFragment.listView.setVisibility(View.VISIBLE);
                free_VideoPlaylistFragment.llImpty.setVisibility(View.GONE);
                return mobileValues.size();

            }else {
                free_VideoPlaylistFragment.listView.setVisibility(View.GONE);
                free_VideoPlaylistFragment.llImpty.setVisibility(View.VISIBLE);
                return 0;
            }
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
