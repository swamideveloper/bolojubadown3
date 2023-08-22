package com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Fragment;

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
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.NewVoiceCacheListModel;
import com.free.hdvideodownloaderapp.videodownloader.Utils.MyDatabase;
import com.free.hdvideodownloaderapp.videodownloader.Utils.VideoUtils;
import com.free.hdvideodownloaderapp.videodownloader.View.NoScrollGridView;
import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Adapter.VideoListGridAdapter;
import com.free.hdvideodownloaderapp.videodownloader.R;
import com.commonsware.cwac.merge.MergeAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class free_VideoPlaylistFragment extends Fragment {

    Context context;
    LayoutInflater mInflater;
    VideoListGridAdapter playlistGridAdapter;
    public static ListView listView;
    public static MyDatabase myDatabase;
    public static JSONArray jsonArray = new JSONArray();
    public static JSONObject ConversationObj = new JSONObject();

    public static LinearLayout llImpty;

    NoScrollGridView noScrollGridView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        mInflater = LayoutInflater.from(context);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_video_playlist, container, false);

        listView = view.findViewById(R.id.listView);
        llImpty = view.findViewById(R.id.llImpty);
        final MergeAdapter mergeAdapter = new MergeAdapter();
        myDatabase = new MyDatabase(getActivity());
        ImageView addPlaylist = view.findViewById(R.id.button);
        addPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog alertDialog = new Dialog(getActivity(), R.style.MinWidth);
                alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                alertDialog.setContentView(R.layout.dialog_add_playlist);

                final EditText input = alertDialog.findViewById(R.id.editText);
                input.requestFocus();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

                alertDialog.findViewById(R.id.btnCreate).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = input.getText().toString();

                        List<NewVoiceCacheListModel> data = myDatabase.getSearchHistory();

                        int on_name = 0;
                        for (int i = 0; i < data.size(); i++) {
                            if (data.get(i).getString().trim().equals(name.toString().trim())) {
                                on_name = 1;
                            }
                        }
                        if (name.isEmpty()) {
                            Toast.makeText(context, "Please Enter Playlist Name", Toast.LENGTH_SHORT).show();

                        }else {
                            if (on_name == 0) {

                                (new VideoUtils(getActivity())).addVideolist(name);

                                try {
                                    ConversationObj.put("Conversation", jsonArray);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                myDatabase.insertData(ConversationObj.toString(), "" + name);

                                playlistGridAdapter = new VideoListGridAdapter(getActivity());
                                noScrollGridView.setAdapter(playlistGridAdapter);
                                alertDialog.cancel();
                            } else {
                                Toast.makeText(getContext(), "Same Name not Create Playlist.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

                alertDialog.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });
                alertDialog.findViewById(R.id.cancle).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });
                alertDialog.show();
            }
        });
        View playlistGrid = mInflater.inflate(R.layout.scroll_disabled_grid_view, null);
        noScrollGridView = playlistGrid.findViewById(R.id.scroll_disabled_grid_view);
        noScrollGridView.setExpanded(true);
        playlistGridAdapter = new VideoListGridAdapter(getActivity());
        noScrollGridView.setAdapter(playlistGridAdapter);
        mergeAdapter.addView(noScrollGridView);
        listView.setAdapter(mergeAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (playlistGridAdapter != null) {
            playlistGridAdapter.notifyDataSetChanged();
        }
    }
}