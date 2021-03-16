package com.example.pages.loginData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.tools.PasswordUtilities;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "login.db";
    SQLiteDatabase MyDB;
    private static final int DATABASE_VERSION = 1;


    private static final String COL_EMAIL= "email";
    private static final String COL_PASSWORD= "passwordHash";



    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DBNAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("drop table if exists users");
        MyDB.execSQL("create table users(username TEXT primary key, passwordHash BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        MyDB.execSQL("drop table if exists users");
    }

    public  boolean  insertData(String email, byte[] ph){

     MyDB = this.getWritableDatabase();

     ContentValues values = new ContentValues();
     values.put(COL_EMAIL, email);
     values.put(COL_PASSWORD, ph);

     long r=MyDB.insert("users", null, values);

     return r != -1;
    }

    public boolean checkExists(String email, char[] password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT " + COL_EMAIL + ", " + COL_PASSWORD + " FROM users WHERE " + COL_EMAIL + " =?", new String[]{email});

        Log.d("username", c.getString(0));
        byte[] passwordHash = c.getBlob(1);
        c.close();

        return PasswordUtilities.verifyPassword(passwordHash, password);
    }

}
