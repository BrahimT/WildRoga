package com.example.pages.loginData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBhelper extends SQLiteOpenHelper {
    public static final String DBNAME = "login.db";
    SQLiteDatabase MyDB;
    private static final int DATABASE_VERSION = 1;


    private static final String COL_EMAIL= "email";
    private static final String COL_PASSWORD= "password";



    public DBhelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DBNAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase  MyDB) {
        MyDB.execSQL("create table users(username TEXT primary key, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        MyDB.execSQL("drop table if exists users");
    }
 public  boolean  insertdata(String p, String f){
     MyDB = this.getWritableDatabase();

     ContentValues values = new ContentValues();
     values.put(COL_EMAIL, f);
     values.put(COL_PASSWORD, p);

     long r=MyDB.insert("users", null, values);
     if(r==-1)
     return false;
     else
         return true;
 }
    public boolean checkExist(String title, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM users WHERE " + COL_EMAIL + " =? AND " + COL_PASSWORD + " =?", null);
        boolean exists = c.getCount() > 0;
        c.close();
        return exists;
    }
}
