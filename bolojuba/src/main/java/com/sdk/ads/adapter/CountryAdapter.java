package com.sdk.ads.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sdk.ads.R;
import com.sdk.ads.ResModel.Country_Model;
import com.sdk.ads.client.Country_Callback;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.MyHolder> {

    Context context;
    ArrayList<Country_Model> country_models;
    Country_Callback country_callback;
    String[] strings = new String[]{"au", "us", "gb", "ch", "ca"};
    public int selected = 0;
    public CountryAdapter(Context context, ArrayList<Country_Model> country_models, Country_Callback country_callback,int selectedis) {
        this.context = context;
        this.country_models = country_models;
        this.country_callback = country_callback;
        this.selected = selectedis;
    }


    @NonNull
    @NotNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.country_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyHolder holder, int position) {
        holder.imageView2.setImageResource(country_models.get(position).getResourceId());
        holder.txtLanguage.setText(country_models.get(position).getName1());
        holder.txtLanguage1.setText("("+country_models.get(position).getName()+")");


        if(selected==position){
            holder.imageRadio.setImageResource(R.drawable.language_radio_button_checked);
        }else {
            holder.imageRadio.setImageResource(R.drawable.language_radio_button_unchecked);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected = position;
                notifyDataSetChanged();

                if (country_models.get(position).getCode().equalsIgnoreCase("all")) {
                    country_callback.callback(strings[new Random().nextInt(4 - 0 + 1) + 0]);
                } else {
                    country_callback.callback(country_models.get(position).getCode());
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return country_models.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        ImageView imageView2, imageRadio;
        TextView txtLanguage,txtLanguage1;


        public MyHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imageView2 = itemView.findViewById(R.id.imageView2);
            txtLanguage = itemView.findViewById(R.id.txtLanguage);
            txtLanguage1 = itemView.findViewById(R.id.txtLanguage1);
            imageRadio = itemView.findViewById(R.id.imageRadio);

        }
    }
}
