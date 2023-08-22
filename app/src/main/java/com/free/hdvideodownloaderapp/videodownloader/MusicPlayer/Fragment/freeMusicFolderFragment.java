package com.free.hdvideodownloaderapp.videodownloader.MusicPlayer.Fragment;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.free.hdvideodownloaderapp.videodownloader.Log;
import com.free.hdvideodownloaderapp.videodownloader.MusicPlayer.Activity.free_GlobalDetailActivity;
import com.free.hdvideodownloaderapp.videodownloader.MusicPlayer.Adapter.freeAudioFolderAdapter;
import com.free.hdvideodownloaderapp.videodownloader.Video.BucketBean;
import com.free.hdvideodownloaderapp.videodownloader.R;

import java.util.ArrayList;
import java.util.List;

public class freeMusicFolderFragment extends Fragment {
    public static List<BucketBean> nList;
    public String bucketId;
    public Context context;
    public LinearLayout lin_noVideo;
    public freeAudioFolderAdapter.LoadPicAdapterListener listener;
    public freeAudioFolderAdapter sub_adapter;
    public List<Object> listWithAds = new ArrayList<>();
    public RecyclerView tablayout;
    public TextView tvLocation;
    public freeMusicFolderFragment() {

    }

    public class loaddata extends AsyncTask<Void, Void, Void> {
        public loaddata() {
        }

        public Void doInBackground(Void... voidArr) {
            nList = getAllBucket(getActivity());
            return null;
        }

        @SuppressLint("WrongConstant")
        public void onPostExecute(Void r7) {
            super.onPostExecute(r7);
            if (nList.size() > 0) {
                listWithAds=new ArrayList<>();
                listWithAds.clear();
                listWithAds.addAll(nList);
                setListener();
                tablayout.setLayoutManager(new LinearLayoutManager(context,RecyclerView.VERTICAL,false));
                sub_adapter = new freeAudioFolderAdapter(getActivity(), freeMusicFolderFragment.this.listWithAds,
                        freeMusicFolderFragment.this.listener, "Audios");
                tablayout.setAdapter(sub_adapter);
            }
            List<BucketBean> list = freeMusicFolderFragment.nList;

            if (list == null || list.size() <= 0) {
                lin_noVideo.setVisibility(0);
                tablayout.setVisibility(8);
                return;
            }
            lin_noVideo.setVisibility(8);
            tablayout.setVisibility(0);
        }
    }

    @SuppressLint("Range")
    public static List<BucketBean> getAllBucket(Context context2) {
        ContentResolver contentResolver;
        ArrayList arrayList = new ArrayList();
        ContentResolver contentResolver2 = context2.getContentResolver();
        String[] strArr = {"bucket_id", "_data", "bucket_display_name"};

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = null;
        try {
            cursor = contentResolver2.query(uri, strArr, null, null, "date_added DESC");
        } catch (Exception unused) {
        }
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (true) {
                BucketBean bucketBean2 = new BucketBean();
                @SuppressLint("Range") String string = cursor.getString(cursor.getColumnIndex("bucket_id"));
                bucketBean2.setBucketId(string);
                bucketBean2.setBucketName(cursor.getString(cursor.getColumnIndex("bucket_display_name")));
                String string2 = cursor.getString(cursor.getColumnIndex("_data"));

                if (arrayList.contains(bucketBean2)) {
                    contentResolver = contentResolver2;
                } else {
                    contentResolver = contentResolver2;
                    Cursor query2 = contentResolver2.query(uri, strArr, "bucket_id=?", new String[]{string}, null);
                    if (query2 != null && query2.getCount() > 0) {
                        bucketBean2.setImageCount(query2.getCount());
                    }
                    bucketBean2.setCover(string2);
                    if (query2 != null && !query2.isClosed()) {
                        query2.close();
                    }
                    if (!arrayList.contains(bucketBean2)) {
                        arrayList.add(bucketBean2);
                    }
                }
                if (!cursor.moveToNext()) {
                    break;
                }
                contentResolver2 = contentResolver;
            }
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return arrayList;
    }

    private void initUi(View view) {
        this.tablayout =  view.findViewById(R.id.tablayout);
        this.lin_noVideo =  view.findViewById(R.id.lin_noVideo);
        this.tvLocation =  view.findViewById(R.id.tvLocation);

    }


    private void setListener() {
        this.listener = new freeAudioFolderAdapter.LoadPicAdapterListener() {
            @Override
            public void onClick(View view, int i) {
                BucketBean bucketBean = (BucketBean) listWithAds.get(i);
                bucketId = bucketBean.getBucketId();

                Intent intent = new Intent(getContext(), free_GlobalDetailActivity.class);
                intent.putExtra("name", "Music Folder");
                intent.putExtra("field", "folder_list");
                intent.putExtra("Sub_Bucket_Video_Id", bucketId);
                intent.putExtra("sub_bucket_name", bucketBean.getBucketName());

                Log.e("@hari", "getAllBucket: " + bucketBean.getBucketName());
                tvLocation.setText("Internal storage > "+bucketBean.getBucketName() + " > ");
                Log.e("@set", "set: " + tvLocation.getText().toString());
                startActivity(intent);
            }
        };
    }

    private void updateUi() {
        nList = new ArrayList();
        new loaddata().execute();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context =  getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music_folder_, container, false);
        return view;
    }

    @Override
    public void onResume() {
        updateUi();
        super.onResume();
    }

    @Override
    @SuppressLint({"CommitPrefEdits"})
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.context = getActivity();
        initUi(view);
    }
}