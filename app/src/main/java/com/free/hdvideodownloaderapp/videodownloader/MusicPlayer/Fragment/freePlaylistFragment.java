package com.free.hdvideodownloaderapp.videodownloader.MusicPlayer.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.free.hdvideodownloaderapp.videodownloader.MusicPlayer.Adapter.freePlaylistGridAdapter;
import com.free.hdvideodownloaderapp.videodownloader.Utils.SongsUtils;
import com.free.hdvideodownloaderapp.videodownloader.View.NoScrollGridView;
import com.free.hdvideodownloaderapp.videodownloader.R;
import com.commonsware.cwac.merge.MergeAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class freePlaylistFragment extends Fragment {
    Context context;
    LayoutInflater mInflater;
    public static freePlaylistGridAdapter playerproPlaylistGridAdapter;
    ListView listView;
    ImageView ivAddPlayList;
    private SongsUtils songsUtils;
    private ArrayList<HashMap<String, String>> mobileValues;
    public static LinearLayout txt_not;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist_new, container, false);

        songsUtils = new SongsUtils(context);

        listView = view.findViewById(R.id.listView);
        txt_not = view.findViewById(R.id.txt_not);
        ivAddPlayList = view.findViewById(R.id.ivAddPlayList);
        final MergeAdapter mergeAdapter = new MergeAdapter();
        View Header2 = mInflater.inflate(R.layout.heading_with_button, null);
        TextView textView2 = Header2.findViewById(R.id.titleTextView);
        textView2.setText(getString(R.string.created_playlists));

        mobileValues = songsUtils.getAllPlaylists();


        ivAddPlayList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog alertDialog = new Dialog(getActivity(), R.style.MinWidth);
                alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                alertDialog.setContentView(R.layout.dialog_add_playlist);
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                final EditText input = alertDialog.findViewById(R.id.editText);
                input.requestFocus();
                alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

                alertDialog.findViewById(R.id.btnCreate).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = input.getText().toString();
                        int flg = 0;
                        for (HashMap<String, String> hashMap : mobileValues) {
                            if (hashMap.get("title").equalsIgnoreCase(name)) {
                                flg = 1;
                            } else {
                                flg = 0;
                            }
                        }
                        if (name.isEmpty()) {
                            Toast.makeText(context, "Please Enter Playlist Name", Toast.LENGTH_SHORT).show();

                        } else {
                            if (flg == 0) {
                                (new SongsUtils(getActivity())).addPlaylist(name);
                                alertDialog.cancel();
                            } else {
                                Toast.makeText(context, "Already Added", Toast.LENGTH_SHORT).show();
                            }
                            playerproPlaylistGridAdapter.notifyDataSetChanged();

                        }

                    }
                });

                alertDialog.findViewById(R.id.btnCancel).

                        setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.cancel();
                            }
                        });
                alertDialog.findViewById(R.id.cancle).

                        setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.cancel();
                            }
                        });

                alertDialog.show();
            }
        });

        View playlistGrid = mInflater.inflate(R.layout.scroll_disabled_grid_view1, null);
        NoScrollGridView noScrollGridView = playlistGrid.findViewById(R.id.scroll_disabled_grid_view);
        noScrollGridView.setExpanded(true);
        playerproPlaylistGridAdapter = new freePlaylistGridAdapter(getActivity());
        noScrollGridView.setAdapter(playerproPlaylistGridAdapter);
        mergeAdapter.addView(Header2);
        mergeAdapter.addView(noScrollGridView);
        listView.setAdapter(mergeAdapter);


        SongsUtils songsUtils = new SongsUtils(context);
        if (songsUtils.getAllPlaylists().

                size() == 0) {
            txt_not.setVisibility(View.VISIBLE);
        } else {
            txt_not.setVisibility(View.GONE);
        }

        return view;
    }

    boolean _areLecturesLoaded = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !_areLecturesLoaded) {
            _areLecturesLoaded = true;
        }
    }

}
