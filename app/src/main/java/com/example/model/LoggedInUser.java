package com.example.model;

import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 * TODO Helper class implementation for Firebase collections (user object) MAX
 * Reference: https://stackoverflow.com/questions/47177099/extend-firebase-user
 */
public class LoggedInUser implements Serializable {

    private String userId;
    private String displayName;
    private String email;
    private List<Video> favourites;

    //Required no-arg constructor
    public LoggedInUser(){
    }

    public LoggedInUser(FirebaseUser user, String name){
        this.userId = user.getUid();
        this.displayName = name;
        this.email = user.getEmail();
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail(){
        return this.email;
    }

    public List<Video> getFavourites(){ return favourites; }

    public void addVideoToFavourites(Video video){ favourites.add(video); }
}