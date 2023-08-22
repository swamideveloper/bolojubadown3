package com.free.hdvideodownloaderapp.videodownloader.mvvmbalaji;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.free.hdvideodownloaderapp.videodownloader.databinding.ItemLanguageListBinding;

import java.util.ArrayList;
import java.util.List;

public class Language_Adapter extends RecyclerView.Adapter<Language_Adapter.MyViewModel> {

    ArrayList<LanguageModel> arrayImage;
    Activity activity;
    public int selected = 0;
    private OnItemClickListener mOnItemClickListener;


    public interface OnItemClickListener {
        void onItemClick(List<LanguageModel> list, int i, String str);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public Language_Adapter(ArrayList<LanguageModel> arrayImage, Activity activity, int selectedis) {
        this.arrayImage = arrayImage;
        this.activity = activity;
        this.selected = selectedis;
    }

    @NonNull
    @Override
    public MyViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLanguageListBinding binding = ItemLanguageListBinding.inflate(LayoutInflater.from(activity), parent, false);
        return new MyViewModel(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewModel holder, int position) {
        try {
            ItemLanguageListBinding binding = holder.binding;
            binding.setAdapter(this);
            binding.setModel(arrayImage.get(position));
            binding.setPosition(position);
            binding.executePendingBindings();

            holder.itemView.setOnClickListener(v -> {

                if (mOnItemClickListener != null) {
                    selected = position;
                    notifyDataSetChanged();
                    mOnItemClickListener.onItemClick(arrayImage, position, arrayImage.get(position).getSelectionCode());
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

    public class MyViewModel extends RecyclerView.ViewHolder {

        ItemLanguageListBinding binding;

        public MyViewModel(@NonNull ItemLanguageListBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
