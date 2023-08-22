package com.free.hdvideodownloaderapp.videodownloader.Utils;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.SpannableString;

import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;

import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Adapter.VideoListFragmentAdapterSimple;
import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.PlayerVideoModel;
import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.NewVoiceCacheListModel;
import com.free.hdvideodownloaderapp.videodownloader.Log;
import com.free.hdvideodownloaderapp.videodownloader.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Fragment.free_VideoPlaylistFragment.ConversationObj;
import static com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Fragment.free_VideoPlaylistFragment.jsonArray;

public class VideoUtils {
    Context context;
    private static SharedPrefsUtils sharedPrefsUtils;
    private static final ArrayList<PlayerVideoModel> mainList = new ArrayList<>();
    private static final ArrayList<PlayerVideoModel> queue = new ArrayList<>();

    public VideoUtils(Context context) {
        this.context = context;
        sharedPrefsUtils = new SharedPrefsUtils(context);
        grabIfEmpty();
        if (queue.isEmpty()) {
            try {
                Type type = new TypeToken<ArrayList<PlayerVideoModel>>() {
                }.getType();
                ArrayList<PlayerVideoModel> restoreData = new Gson().fromJson(sharedPrefsUtils.readSharedPrefsString("key", null), type);
                replaceQueue(restoreData);
                Log.e("TAG", "Retrieved queue from storage in SongsUtils. " + restoreData.size() + " songs!");
            } catch (Exception e) {
                Log.e("TAG", "Unable to retrieve data while queue is empty.");
            }
        }
    }


    private static void clearQueue() {
        queue.clear();
    }

    public void addVideolist(String name) {
        Videolist db = new Videolist(context);

        Log.e("DATA", "db===" + db);

        db.open();
        db.addRow(name);
    }

    private void grabIfEmpty() {
        if (mainList.isEmpty()) {
            getFVAllVideos();
            Log.e("TAG", "Grabbing data for player...");
        } else {
            Log.e("TAG", "Data is present. Just setting context.");
        }
    }

    List<String> list;

    public List<PlayerVideoModel> getFVAllVideos() {
        String selection = null;
        String str2 = "date_modified DESC";
        String[] projection = {MediaStore.Video.Media._ID,
                MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.DATE_MODIFIED,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.RESOLUTION,
                MediaStore.Video.Media.SIZE,
        };
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        Cursor query = contentResolver.query(uri, projection, selection, null, str2);
        List<PlayerVideoModel> modelList = new ArrayList<>();
        if (modelList != null) {
            modelList.clear();
        }

        list = new ArrayList<String>();

        while (query.moveToNext()) {


            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).toString().equals("" + query.getString(0))) {
                    PlayerVideoModel model = new PlayerVideoModel();
                    model.setId(query.getString(0));
                    model.setTitle(query.getString(1));
                    model.setDisplayName(query.getString(2));

                    model.setData(query.getString(3));
                    model.setDate(videoDate(query.getString(4)));
                    model.setDuartion(duration(query.getString(5)));


                    model.setResolution(query.getString(6));
                    model.setSize(size(query.getString(7)));
                    modelList.add(model);
                }
            }
        }
        return modelList;
    }

    private String folderDate(String str) {
        return new SimpleDateFormat("yyyy-MMM-dd").format(new Date(Long.parseLong(str) * 1000));
    }

    private String videoDate(String str) {
        return new SimpleDateFormat("dd MMM").format(new Date(Long.parseLong(str) * 1000));
    }

    private String size(String str) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setRoundingMode(RoundingMode.FLOOR);
        double parseDouble = Double.parseDouble(str) / 1024.0d;
        if (parseDouble < 1024.0d) {
            return decimalFormat.format(parseDouble) + " KB";
        }
        double d = parseDouble / 1024.0d;
        if (d < 1024.0d) {
            return decimalFormat.format(d) + " MB";
        }
        return decimalFormat.format(d / 1024.0d) + " GB";
    }

    public static String duration(String str) {
        try {
            long parseInt = (long) Integer.parseInt(str);
            long hours = TimeUnit.MILLISECONDS.toHours(parseInt);
            long minutes = TimeUnit.MILLISECONDS.toMinutes(parseInt) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(parseInt));
            long seconds = TimeUnit.MILLISECONDS.toSeconds(parseInt) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(parseInt));
            if (hours >= 1) {
                return String.format("%02d:%02d:%02d", new Object[]{Long.valueOf(hours), Long.valueOf(minutes), Long.valueOf(seconds)});
            }
            return String.format("%02d:%02d", new Object[]{Long.valueOf(minutes), Long.valueOf(seconds)});
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public ArrayList<HashMap<String, String>> getAllPlaylists() {
        Videolist db = new Videolist(context);
        db.open();
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        if (db.getCount() > 0) {
            list = db.getAllRows();
        }
        db.close();
        return list;
    }

    public void generateMenu(PopupMenu popupMenus, int[] options) {
        int i = 0;
        while (i < options.length) {
            String name = "";
            if (options[i] == R.id.remove_musicUtils) {
                name = "Remove";
            }

            popupMenus.getMenu().add(0, options[i], 1, name);
            MenuItem menuItem = popupMenus.getMenu().getItem(i);
            CharSequence menuTitle = menuItem.getTitle();
            SpannableString styledMenuTitle = new SpannableString(menuTitle);
            menuItem.setTitle(styledMenuTitle);
            i++;
        }
    }

    public ArrayList<PlayerVideoModel> playlistSongs(int playlistID) {
        ArrayList<PlayerVideoModel> list = new ArrayList<>();
        VideolistSongs db = new VideolistSongs(context);
        db.open();
        if (db.getCount(playlistID) > 0) {
            list = db.getAllRows(playlistID);
        }
        db.close();
        return list;
    }

    public void deletePlaylist(int id) {
        Videolist db = new Videolist(context);
        db.open();
        db.deleteRow(id);
        db.close();
    }

    MyDatabase myDatabase;

    public void addToPlaylist(final PlayerVideoModel hash) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_addtoplaylist);
        myDatabase = new MyDatabase(context);
        GridView listView = dialog.findViewById(R.id.listView);
        LinearLayout txtNoData = dialog.findViewById(R.id.txtNoData);
        ImageView relAdd = dialog.findViewById(R.id.add_playlist);

        List<NewVoiceCacheListModel> data = new ArrayList<>();
        data = myDatabase.getSearchHistory();
        if (data.size() > 0) {
            listView.setVisibility(View.VISIBLE);
            txtNoData.setVisibility(View.GONE);
        } else {
            listView.setVisibility(View.GONE);
            txtNoData.setVisibility(View.VISIBLE);
        }

        VideoListFragmentAdapterSimple playlistAdapter = new VideoListFragmentAdapterSimple
                (context);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<NewVoiceCacheListModel> data = myDatabase.getSearchHistory();
                JSONArray jsonArray = new JSONArray();
                JSONObject ConversationObj = new JSONObject();

                for (int i = 0; i < data.get(position).getVoiceChatItems().size(); i++) {
                    JSONObject student1 = new JSONObject();
                    try {
                        student1.put("video_id", data.get(position).getVoiceChatItems().get(i).getLan1());
                        jsonArray.put(student1);
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
                JSONObject student111 = new JSONObject();
                try {
                    student111.put("video_id", "" + hash.getId());
                    jsonArray.put(student111);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    ConversationObj.put("Conversation", jsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                myDatabase.Update_converation(ConversationObj.toString(), "" + data.get(position).getString().toString().trim());


                if (dialog.isShowing()) {
                    dialog.cancel();
                }

            }
        });
        EditText input = dialog.findViewById(R.id.editText);
        input.requestFocus();
        dialog.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.btnCancel1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.btnCreate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = input.getText().toString();


                if (name.equals("") || name.toString() == null) {
                    Toast.makeText(context, " Please enter Playlist.", Toast.LENGTH_SHORT).show();
                } else {

                    List<NewVoiceCacheListModel> data = myDatabase.getSearchHistory();
                    int on_name = 0;
                    for (int i = 0; i < data.size(); i++) {
                        if (data.get(i).getString().trim().equals(name.toString().trim())) {
                            on_name = 1;
                        }
                    }

                    if (on_name == 0) {
                        (new SongsUtils(context)).addPlaylist(name);
                        (new VideoUtils(context)).addVideolist(name);

                        try {
                            ConversationObj.put("Conversation", jsonArray);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        myDatabase.insertData(ConversationObj.toString(), "" + name);

                        dialog.cancel();
                    } else {
                        Toast.makeText(context, "Same Name not Create Playlist.", Toast.LENGTH_SHORT).show();

                    }


                }
            }
        });


        relAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPlayListDialog(playlistAdapter);
            }
        });
        listView.setAdapter(playlistAdapter);
        dialog.show();
    }

    private void addPlayListDialog(final VideoListFragmentAdapterSimple playlistAdapter) {
        final Dialog alertDialog = new Dialog(context);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(R.layout.dialog_add_playlist);

        final EditText input = alertDialog.findViewById(R.id.editText);
        input.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, (new CommonUtils(context)).accentColor(sharedPrefsUtils))));
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT);

        Button btnCreate = alertDialog.findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = input.getText().toString();
                if (!name.isEmpty()) {
                    addPlaylist(name);
                    playlistAdapter.notifyDataSetChanged();
                    alertDialog.cancel();
                } else {
                    Toast.makeText(context, "Please enter playlist name.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button btnCancel = alertDialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
        alertDialog.show();
    }

    public void addPlaylist(String name) {
        Videolist db = new Videolist(context);
        db.open();
        db.addRow(name);
        db.close();
    }

    public static ArrayList<PlayerVideoModel> queue() {
        if (queue.isEmpty()) {
            ArrayList<PlayerVideoModel> list = new ArrayList<>(mainList);
            Collections.reverse(list);
            replaceQueue(list);
        }
        return (queue);
    }

    public static boolean replaceQueue(final ArrayList<PlayerVideoModel> list) {
        if (list != null && !list.isEmpty()) {
            clearQueue();
            queue.addAll(list);
            try {
                new Thread(new Runnable() {
                    public void run() {
                        sharedPrefsUtils.writeSharedPrefs("key", new Gson().toJson(list));
                    }
                }).start();
            } catch (Exception e) {
                e.getStackTrace();
            }
            return true;
        } else {
            return false;
        }
    }

}
