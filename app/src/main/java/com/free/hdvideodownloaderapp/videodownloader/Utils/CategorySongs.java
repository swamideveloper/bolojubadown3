package com.free.hdvideodownloaderapp.videodownloader.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.SongModel;

import java.util.ArrayList;

public class CategorySongs {

    String TEXT_TYPE = " TEXT";
    String COMMA_SEP = ",";
    String TABLE_NAME = "category";
    String COLUMN_NAME_ID = "id";
    String CATEGORY = "category";
    String VOTES = "rank";
    String TITLE = "title";
    String PATH = "path";
    String ARTIST = "artist";
    String ALBUM = "album";
    String NAME = "name";
    String ALBUMID = "albumid";
    String FAKEPATH = "fakepath";
    String DURATION = "duration";
    String[] ALL_KEYS = new String[]
            {COLUMN_NAME_ID, CATEGORY, VOTES, TITLE, PATH, ARTIST, ALBUM, NAME, DURATION, ALBUMID, FAKEPATH};
    String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                    CATEGORY + TEXT_TYPE + COMMA_SEP +
                    VOTES + " INTEGER " + COMMA_SEP +
                    TITLE + TEXT_TYPE + COMMA_SEP +
                    PATH + TEXT_TYPE + COMMA_SEP +
                    ARTIST + TEXT_TYPE + COMMA_SEP +
                    ALBUM + TEXT_TYPE + COMMA_SEP +
                    NAME + TEXT_TYPE + COMMA_SEP +
                    DURATION + TEXT_TYPE + COMMA_SEP +
                    ALBUMID + TEXT_TYPE + COMMA_SEP +
                    FAKEPATH + TEXT_TYPE +
                    ");";
    String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
    SQLiteDatabase db;
    ReaderDB myDBHelper;
    Context context;

    public CategorySongs(Context ctx) {
        this.context = ctx;
        myDBHelper = new ReaderDB(context);
    }

    public CategorySongs open() {
        db = myDBHelper.getWritableDatabase();
        return this;
    }

    public CategorySongs close() {
        myDBHelper.close();
        return this;
    }

    private String dropInvalidString(String string) {
        return string.replaceAll("[^A-Za-z0-9()\\[\\]]", "");
    }

    public long addRow(long playlistID, SongModel hashRow) {
        ContentValues values = new ContentValues();
        values.put(TITLE, hashRow.getTitle());
        values.put(CATEGORY, Long.toString(playlistID));
        values.put(VOTES, "0");
        values.put(PATH, hashRow.getPath());
        values.put(ARTIST, hashRow.getArtist());
        values.put(ALBUM, hashRow.getAlbum());
        values.put(NAME, hashRow.getFileName());
        values.put(FAKEPATH, dropInvalidString(hashRow.getPath()));
        values.put(DURATION, hashRow.getDuration());
        values.put(ALBUMID, hashRow.getAlbumID());
        return db.insert(TABLE_NAME, "NULL", values);
    }


    public ArrayList<SongModel> getAllRows(int playlistID) {
        String where = CATEGORY + "=" + Integer.toString(playlistID);
        Cursor c = db.query(TABLE_NAME, ALL_KEYS, where, null, null, null, VOTES + " DESC");

        ArrayList<SongModel> profilesArray = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                SongModel song = new SongModel();
                song.setTitle(c.getString(3));
                song.setPath(c.getString(4));
                song.setArtist(c.getString(5));
                song.setAlbum(c.getString(6));
                song.setFileName(c.getString(7));
                song.setDuration(c.getString(8));
                song.setAlbumID(c.getString(9));
                profilesArray.add(song);
            } while (c.moveToNext());
        }
        c.close();

        return profilesArray;
    }


    public boolean deleteRow(long rowId, long category) {
        String where = COLUMN_NAME_ID + "=" + rowId + " AND " + CATEGORY + "=" + category;
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
                " WHERE " + CATEGORY + "=" + Long.toString(playlist);
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

    public boolean checkRow(String path) {
        String where = FAKEPATH + " = '" + dropInvalidString(path) + "';";
        Cursor c = db.query(TABLE_NAME, ALL_KEYS,
                where, null, null, null, null);
        if (c.getCount() > 0) {
            c.close();
            return true;
        } else {
            c.close();
            return false;
        }
    }


    public boolean updateRow(String nameF) {
        String where = FAKEPATH + " = '" + dropInvalidString(nameF) + "';";

        Cursor c = db.query(TABLE_NAME, ALL_KEYS,
                where, null, null, null, null);
        int votes = 0;
        String title = null, path = null, artist = null, albumid = null, album = null, name = null, duration = null, playlistID = null;
        if (c != null) {
            c.moveToFirst();
            playlistID = c.getString(1);
            votes = c.getInt(2) + 1;
            title = c.getString(3);
            path = c.getString(4);
            artist = c.getString(5);
            album = c.getString(6);
            name = c.getString(7);
            duration = c.getString(8);
            albumid = c.getString(9);
        }
        if (c != null) {
            c.close();
        }

        ContentValues newValues = new ContentValues();
        newValues.put(CATEGORY, playlistID);
        newValues.put(VOTES, votes);
        newValues.put("title", title);
        newValues.put("path", path);
        newValues.put("artist", artist);
        newValues.put("album", album);
        newValues.put("name", name);
        newValues.put("duration", duration);
        newValues.put("albumid", albumid);
        newValues.put(FAKEPATH, dropInvalidString(nameF));
        return db.update(TABLE_NAME, newValues, where, null) != 0;
    }


    public class ReaderDB extends SQLiteOpenHelper {
        static final int DATABASE_VERSION = 3;
        static final String DATABASE_NAME = "categories.db";

        ReaderDB(Context context) {
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