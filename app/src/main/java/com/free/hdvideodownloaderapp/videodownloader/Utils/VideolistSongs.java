package com.free.hdvideodownloaderapp.videodownloader.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.PlayerVideoModel;

import java.util.ArrayList;

public class VideolistSongs {

    String TEXT_TYPE = " TEXT";
    String COMMA_SEP = ",";
    String TABLE_NAME = "VideolistSongs";
    String COLUMN_NAME_ID = "id";
    String PLAYLISTID = "playlist";
    String TITLE = "title";
    String PATH = "path";
    String ARTIST = "artist";
    String ALBUM = "album";
    String NAME = "name";
    String ALBUMID = "albumid";
    String DURATION = "duration";
    String[] ALL_KEYS = new String[]
            {COLUMN_NAME_ID, PLAYLISTID, TITLE, PATH, ARTIST, ALBUM, NAME, DURATION, ALBUMID};
    String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                    PLAYLISTID + TEXT_TYPE + COMMA_SEP +
                    TITLE + TEXT_TYPE + COMMA_SEP +
                    PATH + TEXT_TYPE + COMMA_SEP +
                    ARTIST + TEXT_TYPE + COMMA_SEP +
                    ALBUM + TEXT_TYPE + COMMA_SEP +
                    NAME + TEXT_TYPE + COMMA_SEP +
                    DURATION + TEXT_TYPE + COMMA_SEP +
                    ALBUMID + TEXT_TYPE +
                    ");";
    String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
    SQLiteDatabase db;
    ReaderDB myDBHelper;
    Context context;

    Videolist allPlaylistDB;


    public VideolistSongs(Context ctx) {
        this.context = ctx;
        allPlaylistDB = new Videolist(context);
        myDBHelper = new ReaderDB(context);
    }

    public VideolistSongs open() {
        db = myDBHelper.getWritableDatabase();
        return this;
    }

    public VideolistSongs close() {
        myDBHelper.close();
        return this;
    }


    public long addRow(long playlistID, PlayerVideoModel hashRow) {
        ContentValues values = new ContentValues();
        values.put(TITLE, hashRow.getTitle());
        values.put(PLAYLISTID, Long.toString(playlistID));
        values.put(PATH, hashRow.getData());
        values.put(NAME, hashRow.getDisplayName());
        values.put(DURATION, hashRow.getDuartion());
        values.put(ALBUMID, hashRow.getId());

        return db.insert(TABLE_NAME, "NULL", values);
    }


    public ArrayList<PlayerVideoModel> getAllRows(int playlistID) {
        String where = PLAYLISTID + "=" + Integer.toString(playlistID);
        Cursor c = db.query(TABLE_NAME, ALL_KEYS, where, null, null, null, null);

        ArrayList<PlayerVideoModel> profilesArray = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                PlayerVideoModel song = new PlayerVideoModel();
                song.setTitle(c.getString(2));
                song.setData(c.getString(4));
                song.setDisplayName(c.getString(5));
                song.setId(c.getString(8));
                song.setSize(c.getString(3));
                song.setDuartion(c.getString(7));
                profilesArray.add(song);
            } while (c.moveToNext());
        }
        c.close();

        return profilesArray;
    }


    public boolean deleteRow(long rowId, long playlistid) {
        String where = COLUMN_NAME_ID + "=" + rowId + " AND " + PLAYLISTID + "=" + playlistid;
        return db.delete(TABLE_NAME, where, null) != 0;
    }

    public boolean deleteRowByPath(String path) {
        String where = PATH + "=" + path;
        try {
            return db.delete(TABLE_NAME, where, null) != 0;
        } catch (Exception e) {
            return false;
        }
    }

    public int getCount(long playlist) {
        String countQuery = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + PLAYLISTID + "=" + Long.toString(playlist);
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }


    public boolean deleteAll(int PlaylistID) {
        try {
            Cursor c = getAllRowsCursor();
            long rowId = c.getColumnIndexOrThrow(COLUMN_NAME_ID);
            if (c.moveToFirst()) {
                do {
                    deleteRow(c.getLong((int) rowId), PlaylistID);
                } while (c.moveToNext());
            }
            c.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public Cursor getAllRowsCursor() {
        String where = null;
        Cursor c = db.query(true, TABLE_NAME, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }




    public class ReaderDB extends SQLiteOpenHelper {
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "VideolistSongs.db";

        public ReaderDB(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }

        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }

    }
}
