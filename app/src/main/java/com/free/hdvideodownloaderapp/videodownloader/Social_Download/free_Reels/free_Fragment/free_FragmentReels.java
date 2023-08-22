package com.free.hdvideodownloaderapp.videodownloader.Social_Download.free_Reels.free_Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;
import androidx.viewpager.widget.ViewPager;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.free.hdvideodownloaderapp.videodownloader.Log;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.free_Reels.free_ExoPlayerRecyclerView;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.free_Reels.free_model.free_Datum_my;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.free_Reels.free_model.free_MainClass;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.free_Utils.free_UAppPreference;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.gson.Gson;
import com.free.hdvideodownloaderapp.videodownloader.R;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.free_Reels.free_utils.free_DownloadTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.UUID;


public class free_FragmentReels extends Fragment {

    public static free_ExoPlayerRecyclerView mRecyclerView;
    public static int alternet;
    ProgressBar progressBar;
    ArrayList<free_Datum_my> mediaObjectList = new ArrayList<>();
    ArrayList<free_Datum_my> TemLisy = new ArrayList<>();
    ArrayList<free_MainClass> alldatagson = new ArrayList<>();
    free_UAppPreference appPreference;
    RelativeLayout animationView;
    Animation slideupdown;
    ImageView hand, arrow;
    ViewPager viewPager;
    private VideolistAdapter_Portrait mAdapter;

    public free_FragmentReels(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reels, container, false);

        mRecyclerView = view.findViewById(R.id.exoPlayerRecyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        animationView = view.findViewById(R.id.animationView);
        hand = view.findViewById(R.id.hand);
        new free_UAppPreference(getActivity()).setswipeeee(false);
        arrow = view.findViewById(R.id.arrow);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        appPreference = new free_UAppPreference(getActivity());
        slideupdown = AnimationUtils.loadAnimation(getActivity(),
                R.anim.slideupdown);
        if (appPreference.getFirstTimeswipe()) {
            animationView.setVisibility(View.GONE);
        } else {
            appPreference.setFirstTimeswipe(true);
            animationView.setVisibility(View.VISIBLE);
            hand.startAnimation(slideupdown);
            arrow.startAnimation(slideupdown);
        }
        animationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animationView.setVisibility(View.GONE);
            }
        });


        new JsonTask().execute(appPreference.getShort_video_file());
        return view;
    }

    private RequestManager initGlide() {
        RequestOptions options = new RequestOptions();

        return Glide.with(this)
                .setDefaultRequestOptions(options);
    }

    @Override
    public void onDestroy() {
        if (mRecyclerView != null) {
            mRecyclerView.releasePlayer();
        }
        super.onDestroy();
    }

    public static class VideolistAdapter_Portrait extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private static final int LOADING = 0;
        private static final int ITEM = 1;
        public static SimpleExoPlayer exoPlayer;
        public static String  ssss;
        public static int randomvaluuu;
        FragmentActivity activity;
        ArrayList<free_Datum_my> mediaObjectList;
        RequestManager requestManager1;
        File path;


        public VideolistAdapter_Portrait(FragmentActivity activity, ArrayList<free_Datum_my> mediaObjectList, RequestManager requestManager) {
            this.activity = activity;
            this.mediaObjectList = mediaObjectList;
            this.requestManager1 = requestManager;
        }

        @Override
        public int getItemCount() {
            return mediaObjectList == null ? 0 : mediaObjectList.size();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.layout_media_list_item, parent, false);
            return new PlayerViewHolderrrr(view);

        }

        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, @SuppressLint({"RecyclerView"}) final int i) {
//
            switch (getItemViewType(i)) {
                case ITEM:
                    ((PlayerViewHolderrrr) viewHolder).onBind(mediaObjectList.get(i), requestManager1);
                    if (mediaObjectList.get(i).getVideoName().equalsIgnoreCase("Native")) {
                        ((PlayerViewHolderrrr) viewHolder).r1.setVisibility(View.GONE);
                        ((PlayerViewHolderrrr) viewHolder).player_view.setVisibility(View.GONE);
                        ((PlayerViewHolderrrr) viewHolder).adcontainer.setVisibility(View.VISIBLE);
                        ((PlayerViewHolderrrr) viewHolder).ivShare.setVisibility(View.GONE);
                        ((PlayerViewHolderrrr) viewHolder).ivMore.setVisibility(View.GONE);
                        ((PlayerViewHolderrrr) viewHolder).tvLike.setVisibility(View.GONE);
                        ((PlayerViewHolderrrr) viewHolder).ivDownload.setVisibility(View.GONE);
                        ((PlayerViewHolderrrr) viewHolder).ivLike.setVisibility(View.GONE);


                        // todo show native ad


                    } else if (mediaObjectList.get(i).getVideoName().equalsIgnoreCase("VideoAds")) {

                        ((PlayerViewHolderrrr) viewHolder).player_view.setVisibility(View.GONE);
                        ((PlayerViewHolderrrr) viewHolder).adcontainer.setVisibility(View.GONE);
                        ((PlayerViewHolderrrr) viewHolder).r1.setVisibility(View.VISIBLE);
                        ((PlayerViewHolderrrr) viewHolder).ivShare.setVisibility(View.GONE);
                        ((PlayerViewHolderrrr) viewHolder).ivDownload.setVisibility(View.GONE);
                        ((PlayerViewHolderrrr) viewHolder).ivLike.setVisibility(View.GONE);
                        ((PlayerViewHolderrrr) viewHolder).ivMore.setVisibility(View.GONE);
                        ((PlayerViewHolderrrr) viewHolder).tvLike.setVisibility(View.GONE);

                    } else {
                        Log.e("videossssssss", mediaObjectList.get(i).getVideoName());
                        ((PlayerViewHolderrrr) viewHolder).player_view.setVisibility(View.GONE);
                        ((PlayerViewHolderrrr) viewHolder).adcontainer.setVisibility(View.GONE);
                        ((PlayerViewHolderrrr) viewHolder).ivShare.setVisibility(View.VISIBLE);
                        ((PlayerViewHolderrrr) viewHolder).ivDownload.setVisibility(View.GONE);
                        ((PlayerViewHolderrrr) viewHolder).ivLike.setVisibility(View.VISIBLE);
                        ((PlayerViewHolderrrr) viewHolder).ivMore.setVisibility(View.VISIBLE);
                        ((PlayerViewHolderrrr) viewHolder).tvLike.setVisibility(View.VISIBLE);
                        ((PlayerViewHolderrrr) viewHolder).r1.setVisibility(View.VISIBLE);
                        ((PlayerViewHolderrrr) viewHolder).ivDownload.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mRecyclerView.realseVideostop();

                                checkFolder();

                                final free_DownloadTask downloadTask = new free_DownloadTask(activity, path, "download");
                                downloadTask.execute(mediaObjectList.get(i).getVideoName(), "FbDownloader_" + UUID.randomUUID().toString().substring(0, 10) + ".mp4");

                            }
                        });
                        ((PlayerViewHolderrrr) viewHolder).ivShare.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mRecyclerView.realseVideostop();
                                checkFolder();

                                final free_DownloadTask downloadTask = new free_DownloadTask(activity, path, "share");
                                downloadTask.execute(mediaObjectList.get(i).getVideoName(), "FbDownloader_" + UUID.randomUUID().toString().substring(0, 10) + ".mp4");

                            }
                        });

                        ((PlayerViewHolderrrr) viewHolder).ivLike.setOnClickListener(view -> {
                        });

                        ((PlayerViewHolderrrr) viewHolder).ivMore.setOnClickListener(view -> {
                            mRecyclerView.realseVideostop();

                            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(activity);
                            bottomSheetDialog.setContentView(R.layout.bottom_sheet_report);
                            bottomSheetDialog.show();

                            TextView textView = bottomSheetDialog.findViewById(R.id.tvReport);
                            textView.setOnClickListener(view1 -> {

                                Dialog dialog = new Dialog(activity);
                                dialog.setContentView(R.layout.report_dialog);

                                TextView report = dialog.findViewById(R.id.tvReport);
                                TextView cancel = dialog.findViewById(R.id.tvCancel);

                                report.setEnabled(false);

                                RadioGroup radioGroup = dialog.findViewById(R.id.radioGroup);
                                radioGroup.setOnCheckedChangeListener((radioGroup1, i1) -> {

                                    RadioButton checkedRadioButton = radioGroup1.findViewById(i1);

                                    report.setEnabled(true);
                                    report.setTextColor(activity.getResources().getColor(R.color.cyan));
                                });

                                report.setOnClickListener(view2 -> {
                                    Toast.makeText(activity, "Thanks For reporting", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                });

                                cancel.setOnClickListener(view2 -> dialog.dismiss());

                                dialog.show();
                            });

                        });

                        ((PlayerViewHolderrrr)viewHolder).ivLike.setOnClickListener(view -> {

                            if(((PlayerViewHolderrrr)viewHolder).ivLike.isChecked()) {

                                final Drawable drawable = ((PlayerViewHolderrrr) viewHolder).heart.getDrawable();
                                ((PlayerViewHolderrrr) viewHolder).heart.setAlpha(0.70f);
                                if (drawable instanceof AnimatedVectorDrawableCompat) {
                                    ((PlayerViewHolderrrr) viewHolder).avd = (AnimatedVectorDrawableCompat) drawable;
                                    ((PlayerViewHolderrrr) viewHolder).avd.start();
                                } else if (drawable instanceof AnimatedVectorDrawable) {
                                    ((PlayerViewHolderrrr) viewHolder).avd2 = (AnimatedVectorDrawable) drawable;
                                    ((PlayerViewHolderrrr) viewHolder).avd2.start();
                                }
                            }
                        });

                        final int random = new Random().nextInt(101) + 1; // [0, 100] + 1 => [1, 100]
                        if(random<5) {
                            ((PlayerViewHolderrrr) viewHolder).tvLike.setText(String.format("%dm", random));
                        } else {
                            ((PlayerViewHolderrrr) viewHolder).tvLike.setText(String.format("%dk", random));
                        }

                    }
                    if (i == 3) {
                        Log.e("callll", "--");
                        if (!new free_UAppPreference(activity).getswipeeee()) {
                            Log.e("callll", "-inninn-");
                            new free_UAppPreference(activity).setswipeeee(true);

                        }
                    }
                    break;

                case LOADING:

                    LoadingViewHolder loadingViewHolder = (LoadingViewHolder) viewHolder;

                    break;
            }


        }

        @Override
        public int getItemViewType(int position) {
            return ITEM;
        }

        public void checkFolder() {
            path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath(), activity.getString(R.string.app_name)+"/Reels");
            if (!path.exists()) {
                path.mkdirs();
            }
        }

        public class PlayerViewHolderrrr extends RecyclerView.ViewHolder {

            public FrameLayout mediaContainer;
            public ImageView mediaCoverImage, volumeControl, ivShare, ivDownload, ivMore;
            TextView tvLike;
            ImageView heart;
            CheckBox ivLike;
            public LinearProgressIndicator progressBar;
            public LottieAnimationView loadingAnim;
            public RequestManager requestManager;
            public SimpleExoPlayerView player_view;
            ConstraintLayout cnmm;
            private final View parent;
            private final RelativeLayout r1;
            private final FrameLayout adcontainer;
            AnimatedVectorDrawable avd2;
            AnimatedVectorDrawableCompat avd;

            public PlayerViewHolderrrr(@NonNull View itemView) {
                super(itemView);
                parent = itemView;
                mediaContainer = itemView.findViewById(R.id.mediaContainer);
                mediaCoverImage = itemView.findViewById(R.id.ivMediaCoverImage);
                player_view = itemView.findViewById(R.id.player_view);

                ivShare = itemView.findViewById(R.id.ivShare);
                ivMore = itemView.findViewById(R.id.ivMore);
                tvLike = itemView.findViewById(R.id.tvLike);
                ivDownload = itemView.findViewById(R.id.ivDownload);
                ivLike = itemView.findViewById(R.id.ivLike);
                cnmm = itemView.findViewById(R.id.cnmm);
                r1 = itemView.findViewById(R.id.r1);
                adcontainer = itemView.findViewById(R.id.adsContainer);
                progressBar = itemView.findViewById(R.id.progressBar);
                loadingAnim = itemView.findViewById(R.id.loadingAnim);
                heart = itemView.findViewById(R.id.heart_image);
                volumeControl = itemView.findViewById(R.id.ivVolumeControl);

            }

            void onBind(free_Datum_my mediaObject, RequestManager requestManager) {
                this.requestManager = requestManager;
                parent.setTag(this);
                requestManager
                        .load(mediaObject.getThumbName())
                        .into(mediaCoverImage);
                Log.e("TAG", "onBind: ==="+ mediaObject.getVideoName());
            }

        }


    }

    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONObject jsonObj = new JSONObject(result);

                appPreference.setvideo_url(jsonObj.getString("link"));
                appPreference.setVideo_folder(jsonObj.getString("video_folder"));
                appPreference.setVideo_thumb(jsonObj.getString("thumb_folder"));


                Gson gson = new Gson();
                free_MainClass themesDataModel = gson.fromJson(result, free_MainClass.class);
                alldatagson.add(themesDataModel);
                Collections.shuffle(alldatagson.get(0).getData());
                int j = 0;
                for (int i = 0; i < alldatagson.get(0).getData().size(); i++) {

                    if (appPreference.getADSShowPosition() != 0) {

                        if (TemLisy.size() != 0 && TemLisy.size() % appPreference.getADSShowPosition() == 0) {
                            if (alternet == 0) {

                                free_Datum_my mediaObject = new free_Datum_my();
                                mediaObject.setVideoName("Native");
                                TemLisy.add(mediaObject);
//                                ++alternet;
                            } else if (alternet == 1) {
                                free_Datum_my mediaObject = new free_Datum_my();
                                mediaObject.setVideoName("VideoAds");
                                TemLisy.add(mediaObject);
                                --alternet;
                            }

                            --i;
                        } else {
                            Log.e("sssss", "-----");

                            free_Datum_my mediaObject = new free_Datum_my();
                            mediaObject.setThumbName(appPreference.getvideo_url() + alldatagson.get(0).getData().get(i).getUserName() + "/" + appPreference.getVideo_thumb() + "/" + alldatagson.get(0).getData().get(i).getThumbName());
                            mediaObject.setVideoName(appPreference.getvideo_url() + alldatagson.get(0).getData().get(i).getUserName() + "/" + appPreference.getVideo_folder() + "/" + alldatagson.get(0).getData().get(i).getVideoName());
                            TemLisy.add(mediaObject);
                        }
                    } else {
                        Log.e("sssss", "---1212121--");

                        free_Datum_my mediaObject = new free_Datum_my();
                        mediaObject.setThumbName(appPreference.getvideo_url() + alldatagson.get(0).getData().get(i).getUserName() + "/" + appPreference.getVideo_thumb() + "/" + alldatagson.get(0).getData().get(i).getThumbName());
                        mediaObject.setVideoName(appPreference.getvideo_url() + alldatagson.get(0).getData().get(i).getUserName() + "/" + appPreference.getVideo_folder() + "/" + alldatagson.get(0).getData().get(i).getVideoName());
                        TemLisy.add(mediaObject);
                    }

                }

                TemLisy.addAll(mediaObjectList);
                PagerSnapHelper snapHelper = new PagerSnapHelper();
                mAdapter = new VideolistAdapter_Portrait(getActivity(), TemLisy, initGlide());
                mRecyclerView.setPager(viewPager);
                mRecyclerView.setAdapter(mAdapter);
                snapHelper.attachToRecyclerView(mRecyclerView);
                mRecyclerView.setMediaObjects(TemLisy);
                mRecyclerView.smoothScrollToPosition(1);
            } catch (Exception e) {
                Log.e("Errorreadfile", "-" + e.getMessage());
            }

        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        private final FrameLayout frameLayout;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            frameLayout = itemView.findViewById(R.id.adsContainer);

        }
    }

    @Override
    public void onPause() {
        mRecyclerView.onPausePlayer();
        super.onPause();
    }
}