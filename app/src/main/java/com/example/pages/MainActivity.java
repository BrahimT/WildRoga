package com.example.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.myapplication.R;

import java.util.Set;

import entities.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Converts User Object data to a String Set by passing the data to a method that adds
    //String Sets to Shared Preferences
    public void addUserToSharedPreferences(User user){
        addStringSetToShardPreferences("userData", user.getUserData());
    }

    //Adds any String Set to SharedPreferences so it can be accessed in a different page
    //This is the best way I can think of to send object data from page to page
    public void addStringSetToShardPreferences(String key, Set<String> set){
        SharedPreferences sharedPref = getSharedPreferences("MainActivity.java", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putStringSet(key, set);
        editor.apply();
    }

}