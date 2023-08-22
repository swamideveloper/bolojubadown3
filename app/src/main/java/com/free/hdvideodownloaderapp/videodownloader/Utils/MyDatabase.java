package com.free.hdvideodownloaderapp.videodownloader.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.SameName;
import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.VoiceChatModel;
import com.free.hdvideodownloaderapp.videodownloader.AddPlayList.Model.NewVoiceCacheListModel;
import com.free.hdvideodownloaderapp.videodownloader.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MyDatabase extends SQLiteOpenHelper {

    public MyDatabase(@Nullable Context context) {
        super( context, "myconversation.db", null, 1 );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table conversationsave(id integer primary key, conver text,tebname text)";
        db.execSQL( query );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate( db );
        String query = "drop table if exists conversationsave";
        db.execSQL( query );
    }

    public boolean insertData(String conver, String tebname) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put( "conver", conver );
        contentValues.put( "tebname", tebname );
        database.insert( "conversationsave", null, contentValues );
        return false;
    }

    public void Update_converation(String updatevalue,String str ) {

        SQLiteDatabase db = getWritableDatabase();
        String qr = "update conversationsave set conver='" + updatevalue +  "'  where tebname='" + str+"'";
        db.execSQL(qr);
    }


    public boolean Delete_History_id(String str) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        StringBuilder sb = new StringBuilder();
        sb.append("id=");
        sb.append(str);
        Log.e( "TssAG", "Delete_History_id: "  );
        return writableDatabase.delete("conversationsave", sb.toString(), null) > 0;
    }


    public List<SameName> getSameNameCheck() {
        List<SameName> temp = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        String query = "select * from conversationsave group by tebname";
        Cursor cursor = database.rawQuery( query, null );
        while (cursor.moveToNext()) {
            SameName playerproSame_name = new SameName();
            playerproSame_name.setConver( cursor.getString( 1 ));
            playerproSame_name.setTebname( cursor.getString(2) );
            Log.e( "@tt", "getSameNameCheck: " + cursor.getString(2));
            temp.add(playerproSame_name);
        }
        return temp;
    }



    public List<NewVoiceCacheListModel> getSearchHistory() {
        ArrayList<NewVoiceCacheListModel> arrayList = new ArrayList();
        Cursor rawQuery = getWritableDatabase().rawQuery( "select * from conversationsave group by tebname", null );
        if (!rawQuery.moveToFirst()) {
            return arrayList;
        }
        do {
            try {

                ArrayList<VoiceChatModel> playerproVoiceChatItems = new ArrayList<>();
                JSONObject jsonObject = new JSONObject( rawQuery.getString( 1 ) );

                JSONArray jsonArray = jsonObject.getJSONArray( "Conversation" );

                Log.e( "fsdjkc", "==" + jsonArray.length() );
                for (int i = 0; i < jsonArray.length(); i++) {
                    Log.e( "fsdjkc", "==" + jsonArray.get( i ) );
                    JSONObject jsonObject1 = new JSONObject( String.valueOf( jsonArray.get( i ) ) );

                    VoiceChatModel historyItemne =new VoiceChatModel();
                    historyItemne.setId( rawQuery.getString( 0 ) );
                    historyItemne.setLan1( jsonObject1.getString( "video_id" ) );

                    playerproVoiceChatItems.add( historyItemne );

                }
                arrayList.add( new NewVoiceCacheListModel(playerproVoiceChatItems, rawQuery.getString( 2 ),rawQuery.getString( 0 ) ) );

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } while (rawQuery.moveToNext());
        return arrayList;
    }

}
