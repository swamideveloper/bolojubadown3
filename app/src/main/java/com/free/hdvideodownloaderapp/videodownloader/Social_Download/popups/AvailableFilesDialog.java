package com.free.hdvideodownloaderapp.videodownloader.Social_Download.popups;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.free.hdvideodownloaderapp.videodownloader.R;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.Configs.Adapters.freeResultHolderAdapter;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.Models.File_type;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.free__Statics.free_StaticVariables;


public class AvailableFilesDialog extends AppCompatDialogFragment {

    File_type _type;
    public static RecyclerView result_recycler_view;
    public static TextView nodata;

    public AvailableFilesDialog(File_type _type) {
        this._type = _type;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.available_files_dialog, null);
        String redString = getResources().getString(R.string.Close);

        builder.setView(view)
                .setTitle(getString(R.string.AvailableFiles))
                .setPositiveButton(((Html.fromHtml(redString))), (dialogInterface, i) -> {
                });
        result_recycler_view = view.findViewById(R.id.result_recycler_view);
        nodata = view.findViewById(R.id.nodata);
        try {
            if (free_StaticVariables.get_downloadable_resource_model_By_Type(_type).size() == 0) {
                nodata.setVisibility(View.VISIBLE);
                result_recycler_view.setVisibility(View.GONE);
            } else {
                freeResultHolderAdapter adapter = new freeResultHolderAdapter(view.getContext(), this._type, getActivity());
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
                result_recycler_view.setLayoutManager(mLayoutManager);
                result_recycler_view.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                nodata.setVisibility(View.GONE);
                result_recycler_view.setVisibility(View.VISIBLE);

            }
        } catch (Exception e) {
            e.getMessage();
            nodata.setVisibility(View.VISIBLE);
        }


        return builder.create();
    }

}
