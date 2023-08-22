package com.sdk.ads.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sdk.ads.Comman;
import com.sdk.ads.LocalSave.ModelPrefrences;
import com.sdk.ads.R;
import com.sdk.ads.Ui.DoPermissionActivity;
import com.sdk.ads.Ui.PermissionActivity;
import com.sdk.ads.Ui.StartButtonActivity;
import com.sdk.ads.ads.InterCloseCallBack;
import com.sdk.ads.ads.IntertitialAdsEvent;
import com.sdk.ads.ads.TransferClass;
import com.sdk.ads.other.LanguageModel;

import org.jetbrains.annotations.NotNull;

import static com.sdk.ads.Ui.LanguageActivity.isLangSelect;

import java.util.ArrayList;
import java.util.List;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.MyHolder> {

    ArrayList<LanguageModel> arrayImage;
    Activity activity;
    public int selected = 0;
    private OnItemClickListener mOnItemClickListener;
    ImageView imgDone;

    public interface OnItemClickListener {
        void onItemClick(List<LanguageModel> list, int i, String str);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public LanguageAdapter(ArrayList<LanguageModel> arrayImage, Activity activity , int selectedis ,ImageView imgDone) {
        this.arrayImage = arrayImage;
        this.activity = activity;
        this.selected = selectedis;
        this.imgDone = imgDone;
    }


    public static void extraclass(Context sourceActivity, Class DestinationClass) {
        ModelPrefrences modelPrefrences = new ModelPrefrences(sourceActivity);
        if (Comman.mainResModel.getData().getExtraFields().getPermission().equalsIgnoreCase("on")) {
            sourceActivity.startActivity(new Intent(sourceActivity, PermissionActivity.class).putExtra("show", true));
//            ((Activity) sourceActivity).finish();
        } else if (Comman.mainResModel.getData().getExtraFields().getStartButton().equalsIgnoreCase("on")) {
            sourceActivity.startActivity(new Intent(sourceActivity, StartButtonActivity.class).putExtra("show", true));
//            ((Activity) sourceActivity).finish();
        } else if (Comman.mainResModel != null && Comman.mainResModel.getData().getExtraFields().getDoPermission().equalsIgnoreCase("on")) {
            sourceActivity.startActivity(new Intent(sourceActivity, DoPermissionActivity.class).putExtra("show", true));
        } else {
            sourceActivity.startActivity(new Intent(sourceActivity, DestinationClass).putExtra("show", true));
//            ((Activity) sourceActivity).finish();
        }
    }


    @NonNull
    @NotNull
    @Override
    public LanguageAdapter.MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.langs_item, parent, false);
        return new LanguageAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull LanguageAdapter.MyHolder holder, int position) {



        try {


            holder.imageView2.setImageResource(arrayImage.get(position).getImage());
            holder.txtLanguage.setText(arrayImage.get(position).getName());
            if(selected==position){
                holder.imageRadio.setImageResource(R.drawable.language_radio_button_checked);
            }else {
                holder.imageRadio.setImageResource(R.drawable.language_radio_button_unchecked);
            }


            holder.itemView.setOnClickListener(v -> {
                selected = position;
                notifyDataSetChanged();
//                    mOnItemClickListener.onItemClick( arrayImage,position, arrayImage.get(position).getSelectionCode());
                isLangSelect = true;

            });

            imgDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntertitialAdsEvent.waiteInterCall(activity, new InterCloseCallBack() {
                        @Override
                        public void onAdsClose() {
                            extraclass(activity,TransferClass.Destination);

                        }
                    });


                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return arrayImage.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        ImageView imageView2, imageRadio;
        TextView txtLanguage;


        public MyHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imageView2 = itemView.findViewById(R.id.imageView2);
            txtLanguage = itemView.findViewById(R.id.txtLanguage);
            imageRadio = itemView.findViewById(R.id.imageRadio);

        }
    }
}
