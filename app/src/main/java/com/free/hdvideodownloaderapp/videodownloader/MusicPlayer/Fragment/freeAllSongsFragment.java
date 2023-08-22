package com.free.hdvideodownloaderapp.videodownloader.MusicPlayer.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.SongModel;
import com.free.hdvideodownloaderapp.videodownloader.Utils.SongsUtils;
import com.free.hdvideodownloaderapp.videodownloader.MusicPlayer.Adapter.freeCustomAdapterAllSong;
import com.free.hdvideodownloaderapp.videodownloader.R;

import java.util.ArrayList;

public class freeAllSongsFragment extends Fragment {

	RecyclerView list;
	freeCustomAdapterAllSong adapter;
    ArrayList<SongModel> CustomListViewValuesArr = new ArrayList<>();
    ArrayList<SongModel> CustomListViewValuesArrdemo = new ArrayList<>();
    SongsUtils songsUtils;
    Context context;
    ImageView suffle;
    LayoutInflater mInflater;
    LinearLayout txt_not;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context =  getActivity();
		mInflater = LayoutInflater.from(context);
	}
	
	@SuppressLint({ "SdCardPath", "SimpleDateFormat" })
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_songs, container, false);

        songsUtils = new SongsUtils(getActivity());

        setListData();

        list = v.findViewById( R.id.listView1 );
        txt_not = v.findViewById( R.id.txt_not );
        suffle = v.findViewById(R.id.suffles);

        suffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                songsUtils.shufflePlay(getActivity(), songsUtils.allSongs());
            }
        });
        
        if(CustomListViewValuesArr.size()==0){
            txt_not.setVisibility(View.VISIBLE);
        }else {
            txt_not.setVisibility(View.GONE);
        }

        list.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        adapter=new freeCustomAdapterAllSong( getActivity(), CustomListViewValuesArr,CustomListViewValuesArrdemo);

        list.setAdapter(adapter);

        return v;
        
    }

    boolean _areLecturesLoaded = false;
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !_areLecturesLoaded ) {
            _areLecturesLoaded = true;
        }
    }

	public void setListData()
    {
        CustomListViewValuesArr.clear();
        CustomListViewValuesArrdemo.clear();
        CustomListViewValuesArrdemo = new ArrayList<>(songsUtils.allSongs());

        int j=0;
        for (int i = 0; i < CustomListViewValuesArrdemo.size(); i++) {
            if (i == 3) {
                try {
                    j = i;

                    CustomListViewValuesArr.add(null);
                    CustomListViewValuesArr.add(CustomListViewValuesArrdemo.get(i));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (i == (j + 8)) {
                try {

                    j = i;
                    CustomListViewValuesArr.add(null);
                    CustomListViewValuesArr.add(CustomListViewValuesArrdemo.get(i));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                CustomListViewValuesArr.add(CustomListViewValuesArrdemo.get(i));
            }

        }

    }
}
