package com.example.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.model.Video;
import com.example.tools.PasswordUtilities;

import java.util.ArrayList;
import java.util.List;

public class VideoDB extends SQLiteOpenHelper {
    public static final String DBNAME = "video.db";
    SQLiteDatabase MyDB;
    private static final int DATABASE_VERSION = 1;

    private static final String COL_ID = "id";
    private static final String COL_TITLE= "title";
    private static final String COL_URL= "url";
    private static final String COL_THUMBNAIL= "thumbnail";

    public VideoDB(@Nullable Context context) {
        super(context, DBNAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("drop table if exists users");
        MyDB.execSQL("create table FavVideos(id TEXT primary key,title TEXT, url TEXT, thumbnail TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        MyDB.execSQL("drop table if exists FavVideos");
    }

    public boolean insertData(String id,String title, String url, String thumbnail){

        MyDB = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_ID,id);
        values.put(COL_TITLE, title);
        values.put(COL_URL, url);
        values.put(COL_THUMBNAIL,thumbnail);

        long r=MyDB.insert("FavVideos", null, values);

        return r != -1;
    }

    public boolean delete(String id){
        MyDB = this.getWritableDatabase();

       return 0!=MyDB.delete("FavVideos","id=?",new String[]{id});
    }

    public boolean exists(String id) {
        MyDB = this.getWritableDatabase();

        String Query = "Select * from FavVideos where id = '" + id+"'";
        Cursor cursor = MyDB.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public List<Video> getAllFavVideos(){
        ArrayList<Video> videos = new ArrayList<Video>();
        try {
            SQLiteDatabase db = getWritableDatabase();
            Cursor c = null;
            c = db.rawQuery("SELECT * FROM FavVideos", null);

            System.out.println(c);

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                String id = c.getString(c.getColumnIndex(COL_ID));
                String title = c.getString(c.getColumnIndex(COL_TITLE));
                String url = c.getString(c.getColumnIndex(COL_URL));
                String thumbnail = c.getString(c.getColumnIndex(COL_THUMBNAIL));

                Video video = new Video();
                video.setId(Integer.parseInt(id));
                video.setTitle(title);
                video.setVideoURL(url);
                video.setThumbnail(thumbnail);

                videos.add(video);
            }
            c.close();
            db.close();

        } catch (Exception e) {
            Log.e("something went wrong", "" + e);
        }

        return videos;
    }

}

