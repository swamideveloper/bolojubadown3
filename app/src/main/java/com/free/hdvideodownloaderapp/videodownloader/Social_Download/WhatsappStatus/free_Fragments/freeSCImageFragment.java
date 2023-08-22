package com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.storage.StorageManager;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.core.app.ActivityCompat;
import androidx.documentfile.provider.DocumentFile;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.free.hdvideodownloaderapp.videodownloader.Log;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_Adapter.freeVideoSCImageAdapter;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_Models.StatusModel;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_SCUtils.WAV_Pref;
import com.free.hdvideodownloaderapp.videodownloader.Social_Download.WhatsappStatus.free_SCUtils.WAVideo_SingleClickListener;
import com.free.hdvideodownloaderapp.videodownloader.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class freeSCImageFragment extends Fragment {

    private RelativeLayout a1;
    private freeVideoSCImageAdapter c1;
    private final List<StatusModel> d1 = new ArrayList();
    ProgressBar e1;
    private RecyclerView f1;
    RelativeLayout h1;
    int REQUEST_ACTION_OPEN_DOCUMENT_TREE = 101;
    private DocumentFile[] allFiles;
    WAV_Pref pref;
    View view;

    private int PERMISSIONS_CODE = 103;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        view = layoutInflater.inflate(R.layout.wass_fragment_images, viewGroup, false);
        return view;
    }

    @SuppressLint("ResourceType")
    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);

        f1 = view.findViewById(R.id.recyclerViewImage);
        e1 = view.findViewById(R.id.prgressBarImage);
        a1 = view.findViewById(R.id.image_container);
        h1 = view.findViewById(R.id.iv_no_Found_data);
        f1.setHasFixedSize(true);
        f1.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        view.findViewById(R.id.tv_enable_notification).setOnClickListener(new WAVideo_SingleClickListener() {
            @Override
            public void performClick(View v) {

                if (ActivityCompat.checkSelfPermission(getContext(),
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(getContext(),
                                android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_CODE);
                } else {
                    Log.e("DB", "PERMISSION GRANTED");
                    showDialog();
                }
            }
        });
    }

    Dialog dialog;

    private void showDialog() {
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.wass_permisson);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.show();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        dialog.setCancelable(true);
        wlp.gravity = Gravity.BOTTOM;
        window.setAttributes(wlp);

        dialog.findViewById(R.id.txt_Next).setOnClickListener(new WAVideo_SingleClickListener() {
            @Override
            public void performClick(View v) {
                dialog.findViewById(R.id.use_this_folder).setVisibility(View.GONE);
                dialog.findViewById(R.id.got_it).setVisibility(View.VISIBLE);

            }
        });
        dialog.findViewById(R.id.txt_GotIt).setOnClickListener(new WAVideo_SingleClickListener() {
            @Override
            public void performClick(View v) {
                Permission();
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(),
                        android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
        } else {
            pref = new WAV_Pref(getContext());
            if (pref.get_WhatsApp_Uri().equalsIgnoreCase("")) {

            } else {
                view.findViewById(R.id.layout_no_permission).setVisibility(View.GONE);
                view.findViewById(R.id.image_container).setVisibility(View.VISIBLE);
                getWhatsStatus(pref.get_WhatsApp_Uri());
            }
        }
    }

    public void Permission() {
        pref = new WAV_Pref(getContext());
        if (pref.get_WhatsApp_Uri().equalsIgnoreCase("")) {
            try {
                Intent intent;
                StorageManager storageManager = (StorageManager) getActivity().getSystemService(Context.STORAGE_SERVICE);
                String whatsupFolder = Get_WhatsApp_Folder();
                if (Build.VERSION.SDK_INT >= 29) {
                    intent = storageManager.getPrimaryStorageVolume().createOpenDocumentTreeIntent();
                    String replace = ((Uri) intent.getParcelableExtra("android.provider.extra.INITIAL_URI")).toString().replace("/root/", "/document/");
                    intent.putExtra("android.provider.extra.INITIAL_URI", Uri.parse(replace + "%3A" + whatsupFolder));
                } else {
                    intent = new Intent("android.intent.action.OPEN_DOCUMENT_TREE");
                    intent.putExtra("android.provider.extra.INITIAL_URI", Uri.parse("content://com.android.externalstorage.documents/document/primary%3A" + whatsupFolder));
                }
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_PREFIX_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                startActivityForResult(intent, REQUEST_ACTION_OPEN_DOCUMENT_TREE);

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } else {
            view.findViewById(R.id.layout_no_permission).setVisibility(View.GONE);
            view.findViewById(R.id.image_container).setVisibility(View.VISIBLE);
            getWhatsStatus(pref.get_WhatsApp_Uri());

        }
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_ACTION_OPEN_DOCUMENT_TREE && resultCode == -1) {
            Uri data = intent.getData();
            try {
                if (Build.VERSION.SDK_INT >= 19) {
                    getActivity().getContentResolver().takePersistableUriPermission(data, 3);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            pref.set_WhatsApp_Uri(data.toString());
            getWhatsStatus(pref.get_WhatsApp_Uri());
            Log.e("@@TAG", "onActivityResult: " + pref.get_WhatsApp_Uri());
        }
    }

    private void getWhatsStatus(String uri) {
        if (!uri.isEmpty()) {
            d1.clear();
            allFiles = getFromSdcard(uri);

            if (allFiles != null && allFiles.length != 0) {
                for (int i = 0; i < allFiles.length; i++) {
                    if (!allFiles[i].getUri().toString().contains(".nomedia")) {
                        if (allFiles[i].getUri().toString().contains(".jpg") || allFiles[i].getUri().toString().contains(".png")
                                || allFiles[i].getUri().toString().contains(".gif") || allFiles[i].getUri().toString().contains(".jpeg"))
                            d1.add(new StatusModel(this.allFiles[i].getUri().toString()));
                    }
                }

                if (d1 != null && !d1.isEmpty()) {
                    Log.e("TIGER", "Set_Adapter: " + d1.size());
                    c1 = new freeVideoSCImageAdapter(this.d1, this.a1,1);
                    f1.setAdapter(c1);
                }
                if (d1.size() == 0) {
                    h1.setVisibility(View.VISIBLE);
                } else {
                    h1.setVisibility(View.GONE);
                }
            }
        }
    }

    private DocumentFile[] getFromSdcard(String uri) {
        DocumentFile fromTreeUri = DocumentFile.fromTreeUri(getContext(), Uri.parse(uri));
        if (fromTreeUri == null || !fromTreeUri.exists() || !fromTreeUri.isDirectory() || !fromTreeUri.canRead() || !fromTreeUri.canWrite()) {
            return null;
        }
        return fromTreeUri.listFiles();
    }

    public String Get_WhatsApp_Folder() {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory());
        sb.append(File.separator);
        sb.append("Android/media/com.whatsapp/WhatsApp");
        sb.append(File.separator);
        sb.append("Media");
        sb.append(File.separator);
        sb.append(".Statuses");
        return new File(sb.toString()).isDirectory() ? "Android%2Fmedia%2Fcom.whatsapp%2FWhatsApp%2FMedia%2F.Statuses" : "WhatsApp%2FMedia%2F.Statuses";
    }
}
