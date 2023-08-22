package com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.activities;

import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.documentfile.provider.DocumentFile;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.free.hdvideodownloaderapp.videodownloader.Log;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_SCUtils.SC_Common1;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_SCUtils.WAVideo_SingleClickListener;
import com.free.hdvideodownloaderapp.videodownloader.BuildConfig;
import com.bumptech.glide.Glide;
import com.free.hdvideodownloaderapp.videodownloader.R;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_Adapter.freeVideoFilesAdapterWhatsapp;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_Models.SC_Status;
import com.sdk.ads.ads.IntertitialAdsEvent;


import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static android.view.View.GONE;

public class freeFullStatusViewActivity extends AppCompatActivity {

    String path;
    public static ImageView download1;
    public static ImageView delete1, shareImage;
    public String SaveFilePath = (SC_Common1.RootDirectoryWhatsappShow + "/");
    CardView card_view;
    VideoView videoView;
    private SC_Status status;
    List<SC_Status> f1list = new ArrayList();
    ViewPagerAdapter adapter;
    ArrayList<String> myPagerList = new ArrayList();
    public static File f = new File(Environment.getExternalStorageDirectory() + "/WhatsApp");
    ViewPagerAdapter mViewPagerAdapter;
    private ViewPager mViewPager;
    private int myAdapterpos;
    ImageView full_iv;
    ImageView imageView;
    View itemView;
    TextView title;
    public static int cnt = 0;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wass_activity_full_status_view);

        
        IntertitialAdsEvent.CallInterstitial(this);
IntertitialAdsEvent.ShowInterstitialAdsOnCreate(this);


        path = getIntent().getStringExtra("path");

        myPagerList = getIntent().getStringArrayListExtra("myPagerList");

        getData();
        myAdapterpos = getIntent().getIntExtra("myAdapterpos", 0);
        status = new SC_Status();
        mViewPager = findViewById(R.id.viewPagerMain);
        findViewById(R.id.drawer).setOnClickListener(new WAVideo_SingleClickListener() {
            @Override
            public void performClick(View v) {
                onBackPressed();
            }
        });
        setViewPager();
    }

    class ViewPagerAdapter extends PagerAdapter {

        Context context;
        ArrayList images;
        LayoutInflater mLayoutInflater;

        public ViewPagerAdapter(Context context, ArrayList images) {
            this.context = context;
            this.images = images;
            mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, final int position) {

            itemView = mLayoutInflater.inflate(R.layout.wass_item, container, false);

            full_iv = itemView.findViewById(R.id.full_iv);
            imageView = itemView.findViewById(R.id.img_VideoPlay);
            videoView = itemView.findViewById(R.id.video_full);
            download1 = itemView.findViewById(R.id.download1);
            shareImage = itemView.findViewById(R.id.shareImage);
            delete1 = itemView.findViewById(R.id.delete1);

            videoView.setVisibility(View.GONE);

            if (freeVideoFilesAdapterWhatsapp.bapuji == 2) {
                delete1.setVisibility(View.VISIBLE);
                shareImage.setVisibility(View.VISIBLE);
                download1.setVisibility(GONE);
            }

            for (int z = 0; z < f1list.size(); z++) {

                String path = myPagerList.get(position).toString();
                String substring = path.substring(path.lastIndexOf("/") + 1);
                if (f1list.get(z).getTitle().toString().equalsIgnoreCase(substring)) {
                    download1.setVisibility(GONE);
                    shareImage.setVisibility(View.VISIBLE);
                }
            }


            if (myPagerList.get(position).toString().contains(".mp4")) {
                imageView.setVisibility(View.VISIBLE);
            } else if (myPagerList.get(position).toString().contains(".jpg") || myPagerList.get(position).toString().contains(".png")) {
                imageView.setVisibility(View.GONE);
            }

            imageView.setOnClickListener(new WAVideo_SingleClickListener() {
                @Override
                public void performClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    Uri fileUri = FileProvider.getUriForFile(JK_Full_StatusView_Activity.this, JK_Full_StatusView_Activity.this.getApplicationContext().getPackageName()
//                            + ".fileprovider",new File( myPagerList.get(position).toString()));
                    intent.setDataAndType(Uri.parse(myPagerList.get(position).toString()), "video/*");
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(intent);
//                    Intent intent = new Intent(context, JK_VideoPlayerActivity_GA_Whatapp_Tracker.class);
//                    intent.putExtra("PathVideo", myPagerList.get(position).toString());
//                    context.startActivity(intent);
                }
            });

            download1.setOnClickListener(new WAVideo_SingleClickListener() {
                @Override
                public void performClick(View v) {
                    if (Build.VERSION.SDK_INT >= 29) {
                        if (f.isDirectory()) {
                            String path = myPagerList.get(position);
                            createFileFolder();
                            String substring = path.substring(path.lastIndexOf("/") + 1);
                            try {
                                Log.e("skdjfbsd", "performClick: " + substring);
                                FileUtils.copyFileToDirectory(new File(path), new File(freeFullStatusViewActivity.this.SaveFilePath));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            String substring2 = substring;
                            MediaScannerConnection.scanFile(freeFullStatusViewActivity.this, new String[]{new File(freeFullStatusViewActivity.this.SaveFilePath + substring2).getAbsolutePath()}, new String[]{myPagerList.get(position).endsWith(".mp4") ? "video/*" : "image/*"}, new MediaScannerConnection.MediaScannerConnectionClient() {
                                public void onMediaScannerConnected() {
                                }

                                public void onScanCompleted(String str, Uri uri) {
                                    download1.setImageDrawable(getResources().getDrawable(R.drawable.wass_ic_shar));
                                }
                            });
                            new File(SaveFilePath, substring).renameTo(new File(SaveFilePath, substring2));
                            Context context = freeFullStatusViewActivity.this;
                            Toast.makeText(context, "Downloading Succsses...", Toast.LENGTH_LONG).show();
                        } else {
                            SC_Common1.download(context, myPagerList.get(position).toString());
                        }
                    } else {

                        String path = myPagerList.get(position);
                        createFileFolder();
                        String substring = path.substring(path.lastIndexOf("/") + 1);
                        try {
                            FileUtils.copyFileToDirectory(new File(path), new File(freeFullStatusViewActivity.this.SaveFilePath));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        String substring2 = substring;
                        MediaScannerConnection.scanFile(freeFullStatusViewActivity.this, new String[]{new File(freeFullStatusViewActivity.this.SaveFilePath + substring2).getAbsolutePath()}, new String[]{myPagerList.get(position).endsWith(".mp4") ? "video/*" : "image/*"}, new MediaScannerConnection.MediaScannerConnectionClient() {
                            public void onMediaScannerConnected() {
                            }

                            public void onScanCompleted(String str, Uri uri) {
                            }
                        });

                        new File(SaveFilePath, substring).renameTo(new File(SaveFilePath, substring2));
                        Context context = freeFullStatusViewActivity.this;
                        download1.setVisibility(GONE);
                        shareImage.setVisibility(View.VISIBLE);
                        Toast.makeText(context, "Downloading Succsses...", Toast.LENGTH_LONG).show();
                    }
                }
            });


            delete1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    File fdelete = new File(myPagerList.get(position).toString());
                    if (fdelete.exists()) {
                        if (fdelete.delete()) {
                            Toast.makeText(freeFullStatusViewActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        } else {
                            Toast.makeText(freeFullStatusViewActivity.this, "not deleted", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            String mystr = myPagerList.get(position).toString();

            shareImage.setOnClickListener(new WAVideo_SingleClickListener() {
                @Override
                public void performClick(View v) {
                    shareFile(freeFullStatusViewActivity.this, isVideoFile(freeFullStatusViewActivity.this, mystr), mystr);
                }
            });

            Glide.with(getApplicationContext()).load(images.get(position)).into(full_iv);
            Objects.requireNonNull(container).addView(itemView);
            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((RelativeLayout) object);
        }
    }

    private void setViewPager() {
        mViewPagerAdapter = new ViewPagerAdapter(freeFullStatusViewActivity.this, myPagerList);

        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPagerAdapter.notifyDataSetChanged();
        mViewPager.setCurrentItem(myAdapterpos);
    }

    public static void shareFile(Context context, boolean z, String str) {
        Uri uri;
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        if (z) {
            intent.setType("Video/*");
        } else {
            intent.setType("image/*");
        }
        if (str.startsWith("content")) {
            uri = Uri.parse(str);
        } else {
            uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileprovider", new File(str));
        }
        intent.putExtra("android.intent.extra.STREAM", uri);
        context.startActivity(intent);
    }

    public static boolean isVideoFile(Context context, String str) {
        if (str.startsWith("content")) {
            String type = DocumentFile.fromSingleUri(context, Uri.parse(str)).getType();
            if (type == null || !type.startsWith("video")) {
                return false;
            }
            return true;
        }
        String guessContentTypeFromName = URLConnection.guessContentTypeFromName(str);
        if (guessContentTypeFromName == null || !guessContentTypeFromName.startsWith("video")) {
            return false;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getData() {
        f1list = new ArrayList();
        File file = new File(Environment.getExternalStorageDirectory() + "/Download/Video Downloader/Whatsapp");
        File[] listFiles = file.listFiles();
        f1list = new ArrayList<>();
        if (listFiles == null || listFiles.length <= 0) {

        } else {
            Arrays.sort(listFiles, Comparator.comparingLong(File::lastModified).reversed());
            for (File file2 : listFiles) {
                this.f1list.add(new SC_Status(file2, file2.getName(), file2.getAbsolutePath()));
            }
        }
    }

    public void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        IntertitialAdsEvent.ShowInterstitialAdsOnBack(this);
    }

    public static File RootDirectoryWhatsappShow = new File(Environment.getExternalStorageDirectory() + "/Download/Video Downloader/Whatsapp");
    public static final File ROOTDIRECTORYCHINGARISHOW = new File(Environment.getExternalStorageDirectory() + "/Download/Video Downloader/Chingari");
    public static final File ROOTDIRECTORYMITRONSHOW = new File(Environment.getExternalStorageDirectory() + "/Download/Video Downloader/Mitron");
    public static final File ROOTDIRECTORYMOJSHOW = new File(Environment.getExternalStorageDirectory() + "/Download/Video Downloader/Moj");
    public static final File ROOTDIRECTORYMXSHOW = new File(Environment.getExternalStorageDirectory() + "/Download/Video Downloader/Mxtakatak");
    public static File RootDirectoryFacebookShow = new File(Environment.getExternalStorageDirectory() + "/Download/Video Downloader/Facebook");
    public static File RootDirectoryInstaShow = new File(Environment.getExternalStorageDirectory() + "/Download/Video Downloader/Insta");
    public static File RootDirectoryRoposoShow = new File(Environment.getExternalStorageDirectory() + "/Download/Video Downloader/Roposo");
    public static File RootDirectoryShareChatShow = new File(Environment.getExternalStorageDirectory() + "/Download/Video Downloader/ShareChat");
    public static File RootDirectorySnackVideoShow = new File(Environment.getExternalStorageDirectory() + "/Download/Video Downloader/SnackVideo");
    public static File RootDirectoryTwitterShow = new File(Environment.getExternalStorageDirectory() + "/Download/Video Downloader/Twitter");
    public static File RootDirectoryPinterestShow = new File(Environment.getExternalStorageDirectory() + "/Download/Video Downloader/Pinterest");

    public static void createFileFolder() {

        if (!RootDirectoryWhatsappShow.exists()) {
            RootDirectoryWhatsappShow.mkdirs();
        }
        if (!RootDirectoryFacebookShow.exists()) {
            RootDirectoryFacebookShow.mkdirs();
        }
        if (!RootDirectoryInstaShow.exists()) {
            RootDirectoryInstaShow.mkdirs();
        }
        if (!ROOTDIRECTORYMXSHOW.exists()) {
            ROOTDIRECTORYMXSHOW.mkdirs();
        }
        if (!ROOTDIRECTORYMOJSHOW.exists()) {
            ROOTDIRECTORYMOJSHOW.mkdirs();
        }
        if (!ROOTDIRECTORYCHINGARISHOW.exists()) {
            ROOTDIRECTORYCHINGARISHOW.mkdirs();
        }
        if (!RootDirectoryTwitterShow.exists()) {
            RootDirectoryTwitterShow.mkdirs();
        }
        if (!ROOTDIRECTORYMITRONSHOW.exists()) {
            ROOTDIRECTORYMITRONSHOW.mkdirs();
        }
        if (!RootDirectoryRoposoShow.exists()) {
            RootDirectoryRoposoShow.mkdirs();
        }
        if (!RootDirectorySnackVideoShow.exists()) {
            RootDirectorySnackVideoShow.mkdirs();
        }
        if (!RootDirectoryShareChatShow.exists()) {
            RootDirectoryShareChatShow.mkdirs();
        }
        if (!RootDirectoryPinterestShow.exists()) {
            RootDirectoryPinterestShow.mkdirs();
        }
        if (!RootDirectoryWhatsappShow.exists()) {
            RootDirectoryWhatsappShow.mkdirs();
        }

        File file1 = RootDirectoryWhatsappShow;
        if (!file1.exists()) {
            file1.mkdirs();
        }
        File file2 = ROOTDIRECTORYCHINGARISHOW;
        if (!file2.exists()) {
            file2.mkdirs();
        }
        File file3 = RootDirectoryInstaShow;
        if (!file3.exists()) {
            file3.mkdirs();
        }

        File file4 = ROOTDIRECTORYMXSHOW;
        if (!file4.exists()) {
            file4.mkdirs();
        }

        File file6 = ROOTDIRECTORYMOJSHOW;
        if (!file6.exists()) {
            file6.mkdirs();
        }

        File file7 = ROOTDIRECTORYCHINGARISHOW;
        if (!file7.exists()) {
            file7.mkdirs();
        }
        File file8 = RootDirectoryTwitterShow;
        if (!file8.exists()) {
            file8.mkdirs();
        }
        File file9 = ROOTDIRECTORYMITRONSHOW;
        if (!file9.exists()) {
            file9.mkdirs();
        }

        File file10 = RootDirectoryRoposoShow;
        if (!file10.exists()) {
            file10.mkdirs();
        }
        File file11 = RootDirectorySnackVideoShow;
        if (!file11.exists()) {
            file11.mkdirs();
        }
        File file12 = RootDirectoryShareChatShow;
        if (!file12.exists()) {
            file12.mkdirs();
        }
        File file13 = RootDirectoryPinterestShow;
        if (!file13.exists()) {
            file13.mkdirs();
        }
        File file14 = RootDirectoryWhatsappShow;
        if (!file14.exists()) {
            file14.mkdirs();
        }
    }
}
