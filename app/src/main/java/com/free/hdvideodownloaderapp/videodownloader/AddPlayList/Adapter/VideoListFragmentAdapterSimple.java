package com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.NewVoiceCacheListModel;
import com.free.hdvideodownloaderapp.videodownloader.Utils.MyDatabase;
import com.free.hdvideodownloaderapp.videodownloader.Utils.VideoUtils;
import com.free.hdvideodownloaderapp.videodownloader.R;

import java.util.List;

public class VideoListFragmentAdapterSimple extends BaseAdapter {

    private List<NewVoiceCacheListModel> data;
    private LayoutInflater inflater;
    VideoUtils songsUtils;

    MyDatabase myDatabase;

    public VideoListFragmentAdapterSimple(Context context) {

        songsUtils = new VideoUtils(context);
        myDatabase=new MyDatabase(context);

        data = myDatabase.getSearchHistory();

          inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        data.clear();
        data = myDatabase.getSearchHistory();
    }

    public int getCount() {

        if (data.size() <= 0)
            return 1;
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private static class ViewHolder {

        public TextView text;

    }
    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;

        if (convertView == null) {

             vi = inflater.inflate(R.layout.playlist_row_simple, parent, false);


            holder = new ViewHolder();
            holder.text = vi.findViewById(R.id.titleTextView);

            vi.setTag(holder);

        } else {
            holder = (ViewHolder) vi.getTag();
        }

        if (data.size() <= 0) {
            vi.setVisibility(View.GONE);
        } else {
            vi.setVisibility(View.VISIBLE);

            holder.text.setText(data.get(position).getString());

        }

        return vi;
    }


}
