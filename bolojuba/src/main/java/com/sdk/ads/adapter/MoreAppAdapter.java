package com.sdk.ads.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sdk.ads.R;
import com.sdk.ads.ResModel.Data;
import com.sdk.ads.ResModel.GroupApp;

import java.util.ArrayList;

public class MoreAppAdapter extends RecyclerView.Adapter<MoreAppAdapter.ViewHolder> {
    Context context;
    ArrayList<GroupApp> listdata;
    String type;
    View listItem;

    // RecyclerView recyclerView;
    public MoreAppAdapter(Context context, ArrayList<GroupApp> listdata, String type) {
        this.listdata = listdata;
        this.context = context;
        this.type = type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (type.equalsIgnoreCase("exit")) {
            listItem = layoutInflater.inflate(R.layout.item_more_apps, parent, false);
        } else if (type.equalsIgnoreCase("more")) {
            listItem = layoutInflater.inflate(R.layout.item_more_app_act, parent, false);
        }

        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(listdata.get(holder.getAdapterPosition()).getAppname());
        Glide.with(context).load(listdata.get(holder.getAdapterPosition()).getLogo()).into(holder.imageView);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent httpIntent = new Intent(Intent.ACTION_VIEW);
                    httpIntent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + listdata.get(holder.getAdapterPosition()).getPackagename()));
                    context.startActivity(httpIntent);
                } catch (Exception e) {
                    Intent httpIntent = new Intent(Intent.ACTION_VIEW);
                    httpIntent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + listdata.get(holder.getAdapterPosition()).getPackagename()));
                    context.startActivity(httpIntent);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView, instl;
        public TextView textView;
        public LinearLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
            this.textView = (TextView) itemView.findViewById(R.id.textView);
            instl = itemView.findViewById(R.id.instl);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
        }
    }
}
