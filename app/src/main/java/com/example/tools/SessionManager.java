package com.example.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.pages.ui.login.LoginActivity;

import java.util.HashMap;

// THIS IS NOT NEEDED ANYMORE, CAN BE DELETED IN CLEAN UP.

//TODO change to user object
public class SessionManager {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context context;

    private static final String SESSION_PREFS = "UserSession";
    private static final String LOGGED_IN = "LoggedIn";
    private static final String EMAIL = "email";
    
    public SessionManager(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences(SESSION_PREFS, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void createLogin(String email) {
        editor.putBoolean(LOGGED_IN, true);
        editor.putString(EMAIL, email);
        editor.commit();
    }

    public void checkLogin() {
        if (!this.isLoggedIn()) {
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
        }else{
            Log.d("user", prefs.getString("email", "no user"));
        }
    }

    public HashMap<String, String> getUserEmail() {
        HashMap<String, String> user = new HashMap<>();
        user.put(EMAIL, prefs.getString(EMAIL, null));
        return user;
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(LOGGED_IN, false);
    }

    public void logout() {
        editor.clear().commit();

        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
}
