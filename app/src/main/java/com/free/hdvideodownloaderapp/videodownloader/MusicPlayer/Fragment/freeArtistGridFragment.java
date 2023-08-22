package com.free.hdvideodownloaderapp.videodownloader.MusicPlayer.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.free.hdvideodownloaderapp.videodownloader.MusicPlayer.Adapter.freeArtistGridAdapter;
import com.free.hdvideodownloaderapp.videodownloader.Utils.SongsUtils;
import com.free.hdvideodownloaderapp.videodownloader.MusicPlayer.Activity.free_GlobalDetailActivity;
import com.free.hdvideodownloaderapp.videodownloader.R;
import com.commonsware.cwac.merge.MergeAdapter;


public class freeArtistGridFragment extends Fragment {
    GridView gridView;
    SongsUtils songsUtils;
    LinearLayout txt_not;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_artist_grid, container, false);

        MergeAdapter mergeAdapter = new MergeAdapter();
        gridView = v.findViewById(R.id.gridView);
        txt_not = v.findViewById(R.id.txt_not);
        songsUtils = new SongsUtils(getActivity());

        if (songsUtils.artists().size() == 0) {
            txt_not.setVisibility(View.VISIBLE);
        } else {
            txt_not.setVisibility(View.GONE);
        }

        mergeAdapter.addAdapter(new freeArtistGridAdapter(getActivity(), songsUtils.artists()));
        gridView.setAdapter(mergeAdapter);
        gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if (position >= 0) {
                    Intent intent = new Intent(getActivity(), free_GlobalDetailActivity.class);
                    intent.putExtra("id", position);
                    intent.putExtra("name", songsUtils.artists().get(position).get("artist"));
                    intent.putExtra("field", "artists");
                    startActivity(intent);
                }
            }
        });
        return v;
    }
}
