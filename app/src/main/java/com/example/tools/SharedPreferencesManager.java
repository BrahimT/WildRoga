package com.example.tools;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.model.LoggedInUser;
import com.google.gson.Gson;

import java.util.Set;


public class SharedPreferencesManager {


    private Gson gson = null;

    public SharedPreferencesManager(){
        gson = new Gson();
    }

    public void addObjectToSharedPreferences(Object obj, String key, SharedPreferences sharedPref){
        addStringToSharedPreferences(key, gson.toJson(obj), sharedPref);
    }

    public void addUserToSharedPreferences(LoggedInUser user, SharedPreferences sharedPref){
        addObjectToSharedPreferences(user, "user", sharedPref);
    }

    public LoggedInUser getUserFromSharedPreferences(SharedPreferences sharedPref){
        return gson.fromJson(sharedPref.getString("user", null), LoggedInUser.class);
    }

    public void addStringToSharedPreferences(String key, String s, SharedPreferences sharedPref){
        SharedPreferences.Editor editor= sharedPref.edit();
        editor.putString(key, s);
        editor.apply();
    }




}
