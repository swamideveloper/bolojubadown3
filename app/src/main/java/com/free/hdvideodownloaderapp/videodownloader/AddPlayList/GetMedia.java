package com.free.hdvideodownloaderapp.videodownloader.AddPlayList;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;


import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.PlayerFolderModel;
import com.free.hdvideodownloaderapp.videodownloader.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GetMedia {

    public static String mainFolder = "MxVideoPlayer";
    public static String parentFolder = ".mx_private";
    Context context;

    public GetMedia(Context context) {
        this.context = context;
    }

    public ArrayList<PlayerFolderModel> getfolderList() {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        Cursor query = contentResolver.query(uri, new String[]{"bucket_id"}, (String) null, (String[]) null, (String) null);
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        if (linkedHashSet != null) {
            linkedHashSet.clear();
        }
        while (query.moveToNext()) {
            linkedHashSet.add(query.getString(0));
        }
        List arrayList = new ArrayList(linkedHashSet);
        String[] strArr = {MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.BUCKET_ID,
                MediaStore.Video.Media.DATE_MODIFIED,
                MediaStore.Video.Media.SIZE};

        ArrayList<PlayerFolderModel> modelList = new ArrayList<>();
        if (modelList != null) {
            modelList.clear();
        }
        for (int i = 0; i < arrayList.size(); i++) {
            Cursor query2 = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, strArr, "bucket_id =?", new String[]{(String) arrayList.get(i)}, "date_modified DESC");
            if (query2.moveToNext()) {
                PlayerFolderModel model = new PlayerFolderModel();
                model.setBucket(query2.getString(0));
                model.setData(query2.getString(1));
                model.setBid(query2.getString(2));
                model.setVideoCount(String.valueOf(query2.getCount()));
                model.setDate(folderDate(query2.getString(3)));
                model.setSize(size(query2.getString(4)));

                modelList.add(model);
            }
        }
        return modelList;
    }

    public List<com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.PlayerVideoModel> getVideoByFolder(String str) {
        String selection = "bucket_id =?";
        String str2 = "date_modified DESC";
        String[] strArr = {str};
        String[] projection = {MediaStore.Video.Media._ID,
                MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.DATE_MODIFIED,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.RESOLUTION,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media.BUCKET_DISPLAY_NAME
        };
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        Cursor query = contentResolver.query(uri, projection, selection, strArr, str2);
        List<com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.PlayerVideoModel> modelList = new ArrayList<>();
        if (modelList != null) {
            modelList.clear();
        }
        while (query.moveToNext()) {
            com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.PlayerVideoModel model = new com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.PlayerVideoModel();
            model.setId(query.getString(0));
            model.setTitle(query.getString(1));
            model.setDisplayName(query.getString(2));
            model.setData(query.getString(3));
            model.setDate(videoDate(query.getString(4)));
            model.setDuartion(duration(query.getString(5)));
            model.setResolution(query.getString(6));
            model.setSize(size(query.getString(7)));
            model.setFoldername(query.getString(8));
            modelList.add(model);
        }
        return modelList;
    }

    public List<com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.PlayerVideoModel> getAllVideos() {
        String selection = null;
        String str2 = "date_modified DESC";
        String[] projection = {MediaStore.Video.Media._ID,
                MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.DATE_MODIFIED,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.RESOLUTION,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
        };
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        Cursor query = contentResolver.query(uri, projection, selection, null, str2);
        List<com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.PlayerVideoModel> modelList = new ArrayList<>();
        if (modelList != null) {
            modelList.clear();
        }
        while (query.moveToNext()) {

            com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.PlayerVideoModel model = new com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.PlayerVideoModel();
            model.setId(query.getString(0));
            model.setTitle(query.getString(1));
            model.setDisplayName(query.getString(2));

            model.setData(query.getString(3));
            model.setDate(videoDate(query.getString(4)));
            model.setDuartion(duration(query.getString(5)));

            model.setResolution(query.getString(6));
            model.setSize(size(query.getString(7)));
            model.setFoldername(query.getString(8));

            modelList.add(model);
            Log.e("folder_path", "path: =" + model.getDate());
        }
        return modelList;
    }

    public List<com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.PlayerVideoModel> getFVAllVideos(Activity activity, List<String> list) {
        String selection = null;
        String str2 = "date_modified DESC";
        String[] projection = {MediaStore.Video.Media._ID,
                MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.DATE_MODIFIED,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.RESOLUTION,
                MediaStore.Video.Media.SIZE, MediaStore.Video.Media.BUCKET_DISPLAY_NAME
        };
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        Cursor query = contentResolver.query(uri, projection, selection, null, str2);
        List<com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.PlayerVideoModel> modelList = new ArrayList<>();
        if (modelList != null) {
            modelList.clear();
        }
        Log.e("TAF", "11===s=" + list.size());
        while (query.moveToNext()) {

            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).toString().equals("" + query.getString(0))) {
                    com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.PlayerVideoModel model = new com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.PlayerVideoModel();
                    model.setId(query.getString(0));
                    model.setTitle(query.getString(1));
                    model.setDisplayName(query.getString(2));

                    model.setData(query.getString(3));
                    model.setDate(videoDate(query.getString(4)));
                    model.setDuartion(duration(query.getString(5)));

                    model.setResolution(query.getString(6));
                    model.setSize(size(query.getString(7)));
                    model.setFoldername(query.getString(8));
                    modelList.add(model);
                }
            }

        }
        return modelList;
    }

    public static String duration(String str) {
        try {
            long parseInt = (long) Integer.parseInt(str);
            long hours = TimeUnit.MILLISECONDS.toHours(parseInt);
            long minutes = TimeUnit.MILLISECONDS.toMinutes(parseInt) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(parseInt));
            long seconds = TimeUnit.MILLISECONDS.toSeconds(parseInt) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(parseInt));
            if (hours >= 1) {
                return String.format("%02d:%02d:%02d", new Object[]{Long.valueOf(hours), Long.valueOf(minutes), Long.valueOf(seconds)});
            }
            return String.format("%02d:%02d", new Object[]{Long.valueOf(minutes), Long.valueOf(seconds)});
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private String folderDate(String str) {
        return new SimpleDateFormat("yyyy-MMM-dd").format(new Date(Long.parseLong(str) * 1000));
    }

    private static String videoDate(String str) {
        return new SimpleDateFormat("dd MMM yyyy").format(new Date(Long.parseLong(str) * 1000));
    }

    private static String size(String str) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setRoundingMode(RoundingMode.FLOOR);
        double parseDouble = Double.parseDouble(str) / 1024.0d;
        if (parseDouble < 1024.0d) {
            return decimalFormat.format(parseDouble) + " KB";
        }
        double d = parseDouble / 1024.0d;
        if (d < 1024.0d) {
            return decimalFormat.format(d) + " MB";
        }
        return decimalFormat.format(d / 1024.0d) + " GB";
    }

    public static void shareVideo(Context context, String title, String path) {
        try {
            MediaScannerConnection.scanFile(context, new String[]{path},
                    null, new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                            sharingIntent.setType("video/*");
                            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, title);
                            sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
                            sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            context.startActivity(Intent.createChooser(sharingIntent, "share via:"));
                        }
                    });
        } catch (Exception e) {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("video/*");
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, title);
            sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(path));
            sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(Intent.createChooser(sharingIntent, "share via:"));
        }
    }

    public static void moveToPrivate(Context context, String inputPath, String inputFile) {
        InputStream in = null;
        OutputStream out = null;
        try {
            File file = new File(Environment.getExternalStorageDirectory() + "/" + parentFolder);
            if (!file.mkdirs()) {
                file.mkdirs();
            }
            String outputPath = Environment.getExternalStorageDirectory() + "/" + parentFolder + "/";
            in = new FileInputStream(inputPath);
            out = new FileOutputStream(outputPath + inputFile);

            String finalPath = Environment.getExternalStorageDirectory() + "/" + parentFolder + "/" + inputFile;
            MediaScannerConnection.scanFile(context, new String[]{finalPath},
                    null, new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                        }
                    });

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            out.flush();
            out.close();
            out = null;
            new File(inputPath).delete();
        } catch (FileNotFoundException fnfe1) {
            Log.e("tag", fnfe1.getMessage());
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }

    }

    public static void reMoveToPrivate(Context context, String inputPath, String inputFile) {
        InputStream in = null;
        OutputStream out = null;
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), mainFolder);
            if (!file.mkdirs()) {
                file.mkdirs();
            }
            String outputPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + "/" + mainFolder + "/";
            in = new FileInputStream(inputPath);
            out = new FileOutputStream(outputPath + inputFile);
            String finalPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + "/" + mainFolder + "/" + inputFile;
            MediaScannerConnection.scanFile(context, new String[]{finalPath},
                    null, new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                        }
                    });
            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            out.flush();
            out.close();
            out = null;
            new File(inputPath).delete();
        } catch (FileNotFoundException fnfe1) {
            Log.e("tag", fnfe1.getMessage());
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }

    public static void copyFile(Context context, String inputPath, String inputFile) {
        InputStream in = null;
        OutputStream out = null;
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/" + mainFolder + "/Status Saver");
            if (!file.mkdirs()) {
                file.mkdirs();
            }
            String outputPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + "/" + mainFolder + "/Status Saver/";
            in = new FileInputStream(inputPath);
            out = new FileOutputStream(outputPath + inputFile);

            String finalPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + "/" + mainFolder + "/Status Saver/" + inputFile;
            MediaScannerConnection.scanFile(context, new String[]{finalPath},
                    null, new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                        }
                    });
            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;

        } catch (FileNotFoundException fnfe1) {
            Log.e("tag", fnfe1.getMessage());
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }

    public List<PlayerVideoModel> getDEMOVideo() {

        String selection = null;
        String str2 = "date_modified DESC";
        String[] projection = {MediaStore.Video.Media._ID,
                MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.DATE_MODIFIED,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.RESOLUTION,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
        };
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        Cursor query = contentResolver.query(uri, projection, selection, null, str2);


        ArrayList<PlayerVideoModel> videoModelDates = new ArrayList<>();

        ArrayList<String> date_list = new ArrayList<>();


        ArrayList<com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.PlayerVideoModel> modelList = new ArrayList<>();
        if (modelList != null) {
            modelList.clear();
        }


        while (query.moveToNext()) {

            if(query.getString(5)!=null){
                com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.PlayerVideoModel model = new com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.PlayerVideoModel();
                model.setId(query.getString(0));
                model.setTitle(query.getString(1));
                model.setDisplayName(query.getString(2));

                model.setData(query.getString(3));
                model.setDate(videoDate(query.getString(4)));
                model.setDuartion(duration(query.getString(5)));

                model.setResolution(query.getString(6));
                model.setSize(size(query.getString(7)));
                model.setFoldername(query.getString(8));


                if (!date_list.contains(videoDate(query.getString(4)))) {
                    date_list.add(videoDate(query.getString(4)));
                }
                modelList.add(model);

            }


        }

        for (int j = 0; j < date_list.size(); j++) {
            ArrayList<com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.PlayerVideoModel> modelListvsd = new ArrayList<>();
            for (int i = 0; i < modelList.size(); i++) {

                if (date_list.get(j).equals(modelList.get(i).getDate())) {
                    modelListvsd.add(modelList.get(i));

                }

            }
            PlayerVideoModel videoModel_date = new PlayerVideoModel();
            videoModel_date.setDateList(date_list.get(j));
            videoModel_date.setDatewisevideolist(modelListvsd);
            videoModelDates.add(videoModel_date);

        }
         for (int i = 0; i < videoModelDates.size(); i++) {
        }
        return videoModelDates;
    }


    public List<com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.PlayerVideoModel> getDEMOVideo0() {

        String selection = null;
        String str2 = "date_modified DESC";
        String[] projection = {MediaStore.Video.Media._ID,
                MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.DATE_MODIFIED,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.RESOLUTION,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
        };
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        Cursor query = contentResolver.query(uri, projection, selection, null, str2);

        ArrayList<com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.PlayerVideoModel> modelList = new ArrayList<>();
        if (modelList != null) {
            modelList.clear();
        }


        while (query.moveToNext()) {

            com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.PlayerVideoModel model = new com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.PlayerVideoModel();
            model.setId(query.getString(0));
            model.setTitle(query.getString(1));
            model.setDisplayName(query.getString(2));

            model.setData(query.getString(3));
            model.setDate(videoDate(query.getString(4)));
            model.setDuartion(duration(query.getString(5)));

            model.setResolution(query.getString(6));
            model.setSize(size(query.getString(7)));
            model.setFoldername(query.getString(8));

            modelList.add(model);

        }
        return modelList;
    }

}
