package com.example.tools;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.Set;

import entities.User;

public class SharedPreferencesManager {


    private Gson gson = null;

    public SharedPreferencesManager(){
        gson = new Gson();
    }

    public void addObjectToSharedPreferences(Object obj, String key, SharedPreferences sharedPref){
        addStringToSharedPreferences(key, gson.toJson(obj), sharedPref);
    }

    public void addUserToSharedPreferences(User user, SharedPreferences sharedPref){
        addObjectToSharedPreferences(user, "user", sharedPref);
    }

    public User getUserFromSharedPreferences(SharedPreferences sharedPref){
        return gson.fromJson(sharedPref.getString("user", null), User.class);
    }

    public void addStringToSharedPreferences(String key, String s, SharedPreferences sharedPref){
        SharedPreferences.Editor editor= sharedPref.edit();
        editor.putString(key, s);
        editor.apply();
    }




}
