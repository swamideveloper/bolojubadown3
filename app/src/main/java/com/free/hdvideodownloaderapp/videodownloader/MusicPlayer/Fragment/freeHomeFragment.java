package com.free.hdvideodownloaderapp.videodownloader.MusicPlayer.Fragment;

import android.content.Intent;
import android.os.Bundle;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.SongModel;
import com.free.hdvideodownloaderapp.videodownloader.Log;
import com.free.hdvideodownloaderapp.videodownloader.MusicPlayer.Activity.free_GlobalDetailActivity;
import com.free.hdvideodownloaderapp.videodownloader.MusicPlayer.Adapter.freeAdapterFavList;
import com.free.hdvideodownloaderapp.videodownloader.Utils.ImageUtils;
import com.free.hdvideodownloaderapp.videodownloader.Utils.SongsUtils;
import com.free.hdvideodownloaderapp.videodownloader.R;
import com.commonsware.cwac.merge.MergeAdapter;

import java.util.ArrayList;

public class freeHomeFragment extends Fragment {

    SongsUtils songsUtils;
    CardView ll1, ll2, ll3, ll4;
    MergeAdapter mergeAdapter;
    ArrayList<SongModel> songsList = new ArrayList<>();
    public freeAdapterFavList adapter;
    public String field = "albums", raw = "A Sky Full Of Stars";
    LinearLayout big_history,big_last,big_most,big_shuffle;
    TextView small_history,small_last,small_most,small_shuffle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_new, container, false);

        field = requireActivity().getIntent().getExtras().getString("field");
        songsUtils = new SongsUtils(getActivity());
        ListView listView = view.findViewById(R.id.listView);
        mergeAdapter = new MergeAdapter();

        if (!songsUtils.allSongs().isEmpty()) {
            mergeAdapter.addView(FourGridView());
        } else {
            View heading = View.inflate(getActivity(), R.layout.heading, null);
            TextView textView = heading.findViewById(R.id.titleTextView);
            textView.setText("Unable to find any music in your device. if you have just added music then click on three dots at the top right and choose 'Sync Library'");
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setLineSpacing(0f, 1.2f);
            mergeAdapter.addView(heading);
        }
        listView.setAdapter(mergeAdapter);
        return view;
    }

    public RecyclerView rvFav;
    LinearLayout ll_fv_lyout;

    private View FourGridView() {
        View three_grid = View.inflate(getActivity(), R.layout.home_three_grid, null);
        ll_fv_lyout = three_grid.findViewById(R.id.ll_fv_lyout);

        ImageView imageView1 = three_grid.findViewById(R.id.home_three_grid_imageView_1);
        ImageView imageView2 = three_grid.findViewById(R.id.home_three_grid_imageView_2);
        ImageView imageView3 = three_grid.findViewById(R.id.home_three_grid_imageView_3);
        TextView textView1 = three_grid.findViewById(R.id.home_three_grid_textView_1);
        TextView textView2 = three_grid.findViewById(R.id.home_three_grid_textView_2);
        TextView textView3 = three_grid.findViewById(R.id.home_three_grid_textView_3);

        ll1 = three_grid.findViewById(R.id.llHistory);
        ll2 = three_grid.findViewById(R.id.llAdded);
        ll3 = three_grid.findViewById(R.id.llMostPlayed);
        ll4 = three_grid.findViewById(R.id.llShuffle);
        rvFav = three_grid.findViewById(R.id.rvFavList);

//        big_history=three_grid.findViewById(R.id.big_history);
//        small_history=three_grid.findViewById(R.id.small_history);
//        big_last=three_grid.findViewById(R.id.big_last);
//        small_last=three_grid.findViewById(R.id.small_last);
//        big_most=three_grid.findViewById(R.id.big_most);
//        small_most=three_grid.findViewById(R.id.small_most);
//        big_shuffle=three_grid.findViewById(R.id.big_shuffle);
//        small_shuffle=three_grid.findViewById(R.id.small_shuffle);


//        big_history.setVisibility(View.GONE);
//        big_last.setVisibility(View.GONE);
//        big_most.setVisibility(View.GONE);
//        big_shuffle.setVisibility(View.GONE);
//        small_history.setVisibility(View.VISIBLE);
//        small_last.setVisibility(View.VISIBLE);
//        small_most.setVisibility(View.VISIBLE);
//        small_shuffle.setVisibility(View.VISIBLE);

        if (songsUtils.allSongs().size() > 0) {

            ll1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


//                    big_history.setVisibility(View.VISIBLE);
//                    big_last.setVisibility(View.GONE);
//                    big_most.setVisibility(View.GONE);
//                    big_shuffle.setVisibility(View.GONE);
//                    small_history.setVisibility(View.GONE);
//                    small_last.setVisibility(View.VISIBLE);
//                    small_most.setVisibility(View.VISIBLE);
//                    small_shuffle.setVisibility(View.VISIBLE);

                    Intent intent = new Intent(getContext(), free_GlobalDetailActivity.class);
                    intent.putExtra("name", "History");
                    intent.putExtra("field", "history");
                    startActivity(intent);
                }
            });

            ll2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    big_history.setVisibility(View.GONE);
//                    big_last.setVisibility(View.VISIBLE);
//                    big_most.setVisibility(View.GONE);
//                    big_shuffle.setVisibility(View.GONE);
//                    small_history.setVisibility(View.VISIBLE);
//                    small_last.setVisibility(View.GONE);
//                    small_most.setVisibility(View.VISIBLE);
//                    small_shuffle.setVisibility(View.VISIBLE);

                    Intent intent = new Intent(getContext(), free_GlobalDetailActivity.class);
                    intent.putExtra("name", "Recently Added");
                    intent.putExtra("field", "recent");
                    startActivity(intent);
                }
            });
            ll3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    big_history.setVisibility(View.GONE);
//                    big_last.setVisibility(View.GONE);
//                    big_most.setVisibility(View.VISIBLE);
//                    big_shuffle.setVisibility(View.GONE);
//                    small_history.setVisibility(View.VISIBLE);
//                    small_last.setVisibility(View.VISIBLE);
//                    small_most.setVisibility(View.GONE);
//                    small_shuffle.setVisibility(View.VISIBLE);

                    if (songsUtils.mostPlayedSongs().size() != 0) {
                        songsUtils.play(getContext(), 0, songsUtils.mostPlayedSongs());
                    }
                    Intent intent = new Intent(getContext(), free_GlobalDetailActivity.class);
                    intent.putExtra("name", "Most Played");
                    intent.putExtra("field", "mostplayed");
                    startActivity(intent);
                }
            });
            ll4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    big_history.setVisibility(View.GONE);
//                    big_last.setVisibility(View.GONE);
//                    big_most.setVisibility(View.GONE);
//                    big_shuffle.setVisibility(View.VISIBLE);
//                    small_history.setVisibility(View.VISIBLE);
//                    small_last.setVisibility(View.VISIBLE);
//                    small_most.setVisibility(View.VISIBLE);
//                    small_shuffle.setVisibility(View.GONE);

                    songsUtils.shufflePlay(getActivity(), songsUtils.allSongs());
                }
            });

            TextView ivMore = three_grid.findViewById(R.id.ivMore);
            ivMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), free_GlobalDetailActivity.class);
                    intent.putExtra("name", "Favorites");
                    intent.putExtra("field", "favourites");
                    getActivity().startActivity(intent);
                }
            });
        }

        songsList.addAll(songsUtils.favouriteSongs());
        adapter = new freeAdapterFavList(songsList, getActivity(), field);
        rvFav.setLayoutManager(new GridLayoutManager(getActivity(), 3, RecyclerView.VERTICAL, false));
        Log.e("Size", "Size: " + songsList.size());
        rvFav.setAdapter(adapter);

//        imageView1.setOutlineProvider(new ViewOutlineProvider() {
//            @Override
//            public void getOutline(View view, Outline outline) {
//                outline.setRoundRect(0, 0, view.getWidth(), Math.round(view.getHeight()), 40F);
//            }
//        });
//        imageView1.setClipToOutline(true);
//        imageView2.setOutlineProvider(new ViewOutlineProvider() {
//            @Override
//            public void getOutline(View view, Outline outline) {
//                outline.setRoundRect(0, 0, view.getWidth(), Math.round(view.getHeight()), 40F);
//            }
//        });
//        imageView3.setOutlineProvider(new ViewOutlineProvider() {
//            @Override
//            public void getOutline(View view, Outline outline) {
//                outline.setRoundRect(0, 0, view.getWidth(), Math.round(view.getHeight()), 40F);
//            }
//        });
//        imageView3.setClipToOutline(true);
//        imageView2.setClipToOutline(true);

        if (songsUtils.allSongs().size() > 0) {
            imageView1.setImageDrawable(getResources().getDrawable(R.drawable.app_shuffle));
            imageView1.setPadding(76, 76, 76, 76);
            textView1.setText("Shuffle All");
            imageView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            if (songsUtils.mostPlayedSongs().size() > 0) {
                (new ImageUtils(getActivity())).setAlbumArt(songsUtils.mostPlayedSongs(), imageView2);
                textView2.setText("Most Played");
                imageView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                if (songsUtils.favouriteSongs().size() > 0) {

                    ll_fv_lyout.setVisibility(View.VISIBLE);
                    rvFav.setVisibility(View.VISIBLE);

                    (new ImageUtils(getActivity())).setAlbumArt(songsUtils.favouriteSongs(), imageView3);
                    textView3.setText("Favorites");

                    imageView3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), free_GlobalDetailActivity.class);
                            intent.putExtra("name", "Favorites");
                            intent.putExtra("field", "favourites");
                            getActivity().startActivity(intent);
                        }
                    });
                } else {
                    ll_fv_lyout.setVisibility(View.GONE);
                    rvFav.setVisibility(View.GONE);
                }
            } else {
                (new ImageUtils(getActivity())).setAlbumArt(songsUtils.newSongs(), imageView2);
                textView2.setText("Recently Added");
                imageView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        songsUtils.play(getContext(), 0, songsUtils.newSongs());
                    }
                });
                imageView3.setVisibility(View.GONE);
                textView3.setVisibility(View.INVISIBLE);
            }
        } else {
            three_grid.setVisibility(View.GONE);
        }
        return three_grid;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("asldmicasc", "==1111");
        if (adapter != null) {
            songsList.clear();
            songsList.addAll(songsUtils.favouriteSongs());
            if (songsList.size() > 0) {
                ll_fv_lyout.setVisibility(View.VISIBLE);
                rvFav.setVisibility(View.VISIBLE);
                adapter = new freeAdapterFavList(songsList, getActivity(), field);
                rvFav.setLayoutManager(new GridLayoutManager(getActivity(), 3, RecyclerView.VERTICAL, false));
                Log.e("Size", "Size::: " + songsList.size());
                rvFav.setAdapter(adapter);
            } else {
                ll_fv_lyout.setVisibility(View.GONE);
                rvFav.setVisibility(View.GONE);
            }
        }
    }
}
