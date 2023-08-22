package com.free.hdvideodownloaderapp.videodownloader.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class Playlist {

    String TEXT_TYPE = " TEXT";
    String COMMA_SEP = ",";
    String TABLE_NAME = "queue";
    String COLUMN_NAME_ID = "id";
    String TITLE = "title";
    String[] ALL_KEYS = new String[]
            {COLUMN_NAME_ID, TITLE};
    String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                    TITLE + TEXT_TYPE +
                    ");";
    String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
    SQLiteDatabase db;
    ReaderDB myDBHelper;
    Context context;


    public Playlist(Context ctx) {
        this.context = ctx;
        myDBHelper = new ReaderDB(context);
    }

    public Playlist open() {
        db = myDBHelper.getWritableDatabase();
        return this;
    }

    public Playlist close() {
        myDBHelper.close();
        return this;
    }


    public long addRow(String title) {
        ContentValues values = new ContentValues();
        values.put(TITLE, title);

        return db.insert(TABLE_NAME,"NULL", values);
    }

     public ArrayList<HashMap<String, String>> getAllRows() {

        Cursor c = db.query(TABLE_NAME, ALL_KEYS, null, null, null, null, null);

        ArrayList<HashMap<String, String>> profilesArray = new ArrayList<HashMap<String, String>>();
        if (c.moveToFirst()) {
            do {
                HashMap<String, String> song = new HashMap<String, String>();
                song.put("ID", c.getString(0));
                song.put("title", c.getString(1));
                profilesArray.add(song);
            } while (c.moveToNext());
        }
        c.close();

        return profilesArray;
    }

    public int getCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }


    public HashMap<String, String> getRow(long rowId) {
        String where = COLUMN_NAME_ID + "=" + rowId;
        Cursor c = 	db.query(true, TABLE_NAME, ALL_KEYS,
                where, null, null, null, null, null);
        String title = null;
        if (c != null) {
            c.moveToFirst();
            title = c.getString(1);
        }
        c.close();
        HashMap<String, String> song = new HashMap<String, String>();
        song.put("title", title);
        return song;
    }


    public boolean deleteRow(long rowId) {
        String where = COLUMN_NAME_ID + "=" + rowId;
        return db.delete(TABLE_NAME, where, null) != 0;
    }


    public boolean deleteAll() {
        try {
            Cursor c = getAllRowsCursor();
            long rowId = c.getColumnIndexOrThrow(COLUMN_NAME_ID);
            if (c.moveToFirst()) {
                do {
                    deleteRow(c.getLong((int) rowId));
                } while (c.moveToNext());
            }
            c.close();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }


    public Cursor getAllRowsCursor() {
        String where = null;
        Cursor c = 	db.query(true, TABLE_NAME, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }


    public boolean updateRow(long ID,  String title) {
        String where = COLUMN_NAME_ID + "=" + ID + ";";

        Cursor c = 	db.query(true, TABLE_NAME, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        c.close();

        ContentValues newValues = new ContentValues();
        newValues.put(TITLE, title);

        return db.update(TABLE_NAME, newValues, where, null) != 0;
    }


    public boolean searchPlaylist(String name) {
        Cursor cursor=db.query(true,TABLE_NAME,ALL_KEYS,TITLE + "==" + name + ";",null,null
        ,null,null,null);
        if (cursor != null) {
            cursor.close();
            return true;
        } else {
            return false;
        }
    }


    public class ReaderDB extends SQLiteOpenHelper {
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "Playlist.db";

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