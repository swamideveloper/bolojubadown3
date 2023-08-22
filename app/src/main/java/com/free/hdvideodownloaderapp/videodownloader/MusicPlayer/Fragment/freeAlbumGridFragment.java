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


import com.free.hdvideodownloaderapp.videodownloader.MusicPlayer.Activity.free_GlobalDetailActivity;
import com.free.hdvideodownloaderapp.videodownloader.MusicPlayer.Adapter.freeAlbumGridAdapter;
import com.free.hdvideodownloaderapp.videodownloader.Utils.SongsUtils;
import com.free.hdvideodownloaderapp.videodownloader.R;

import java.util.ArrayList;
import java.util.HashMap;

public class freeAlbumGridFragment extends Fragment {

    GridView rvAlbum;
	SongsUtils songsUtils;
	ArrayList<HashMap<String,String>> CustomArray;
	LinearLayout txt_not;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_album_grid, container, false);
        songsUtils = new SongsUtils(getActivity());
        CustomArray = songsUtils.albums();
		txt_not = v.findViewById(R.id.txt_not);
        rvAlbum = v.findViewById(R.id.gridView1);

		if(CustomArray.size()==0){
			txt_not.setVisibility(View.VISIBLE);
		}else {
			txt_not.setVisibility(View.GONE);
		}


		rvAlbum.setAdapter(new freeAlbumGridAdapter(getActivity(), CustomArray));

		rvAlbum.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent intent = new Intent(getActivity(), free_GlobalDetailActivity.class);
                intent.putExtra("id", position);
                intent.putExtra("name", songsUtils.albums().get(position).get("album"));
                intent.putExtra("field", "albums");
                startActivity(intent);
            }
        });
		return v;
	}

	boolean _areLecturesLoaded = false;
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser && !_areLecturesLoaded ) {
			_areLecturesLoaded = true;
		}
	}

}
