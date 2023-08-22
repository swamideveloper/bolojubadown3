package com.free.hdvideodownloaderapp.videodownloader.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.free.hdvideodownloaderapp.videodownloader.Activity.free_VideoFavouritelist;
import com.free.hdvideodownloaderapp.videodownloader.Def.Common;

import com.free.hdvideodownloaderapp.videodownloader.Utils.SingleClickListener;
import com.free.hdvideodownloaderapp.videodownloader.R;
import com.sdk.ads.ads.AllNativeAds;


public class free_Setting_Fragment extends Fragment {
    LinearLayout llfeedback__;
    public free_Setting_Fragment() {
    }

//    LinearLayout llTop;
    ImageView iv_fav, iv_feed, iv_privacy, iv_share, iv_more;
    TextView txt_fav, txt_feed, txt_privacy, txt_share, txt_more;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_setting, container, false);

         AllNativeAds.NativeAds(getActivity(), view.findViewById(R.id.adsContainer));

//        MoreAppUtils.moreApps(getContext(), (ViewGroup) view.findViewById(R.id.llmore__));

//        llTop = view.findViewById(R.id.llTop);
//        iv_fav = view.findViewById(R.id.iv_fav);
//        iv_feed = view.findViewById(R.id.iv_feed);
//        iv_privacy = view.findViewById(R.id.iv_privacy);
//        iv_share = view.findViewById(R.id.iv_share);
//        txt_fav = view.findViewById(R.id.txt_fav);
//        txt_feed = view.findViewById(R.id.txt_feed);
//        txt_privacy = view.findViewById(R.id.txt_privacy);
//        txt_share = view.findViewById(R.id.txt_share);
//        txt_more = view.findViewById(R.id.txt_more);
//        iv_more = view.findViewById(R.id.iv_more);

//        try {
//
//            if (Comman.mainResModel != null && Comman.mainResModel.getSuccess()) {
//                if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getMoreapps().equalsIgnoreCase("on")) {
//                    view.findViewById(R.id.llmore__).setVisibility(View.VISIBLE);
//                } else {
//                    view.findViewById(R.id.llmore__).setVisibility(View.INVISIBLE);
//                }
//            } else {
//                view.findViewById(R.id.llmore__).setVisibility(View.INVISIBLE);
//            }
//        } catch (Exception e) {
//            e.getMessage();
//        }

//        view.findViewById(R.id.llmore__).setOnClickListener(new SingleClickListener() {
//            @Override
//            public void performClick(View v) {
//                try {
//                    getActivity().startActivity(new Intent(getContext(), MoreAppActivity.class));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });

        view.findViewById(R.id.shareapp).setOnClickListener(new SingleClickListener() {
            @Override
            public void performClick(View v) {
                Common.ShareApp(getContext());
            }
        });

//        view.findViewById(R.id.llrateapp__).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                playerpro_Common.rateUs(getContext());
//            }
//        });

        view.findViewById(R.id.llpolicy__).setOnClickListener(new SingleClickListener() {
            @Override
            public void performClick(View v) {
                Common.privacypolicy(getActivity());
            }
        });

        view.findViewById(R.id.llfav_).setOnClickListener(new SingleClickListener() {
            @Override
            public void performClick(View v) {
                startActivity(new Intent(getContext(), free_VideoFavouritelist.class));
            }
        });

        llfeedback__=view.findViewById(R.id.llfeedback__);
        llfeedback__.setClickable(true);
        llfeedback__.setOnClickListener(new SingleClickListener() {
            @Override
            public void performClick(View v) {
                llfeedback__.setClickable(false);
                show_Dialog();
            }
        });

        return view;
    }

    public void show_Dialog() {
        final Dialog dialog = new Dialog(getActivity(), R.style.DialogSlideAnim);
        dialog.setContentView(R.layout.dialog_feedback);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        window.setAttributes(wlp);
        TextView ok = dialog.findViewById(R.id.txt_ok);
        EditText name = dialog.findViewById(R.id.name);
        EditText comment = dialog.findViewById(R.id.comment);
        TextView txt_cancel = dialog.findViewById(R.id.txt_cancel);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llfeedback__.setClickable(true);
                if (name.getText().toString().isEmpty() || comment.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please Enter Your Name And Comments!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Thanks For Your Feedback!", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            }
        });
        txt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llfeedback__.setClickable(true);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        llfeedback__.setClickable(true);
    }
}