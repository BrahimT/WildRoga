package com.example.model;

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
    private List<Video> favourites;

    public LoggedInUser(String userId, String displayName) {
        this.userId = userId;
        this.displayName = displayName;
        this.favourites = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public List<Video> getFavourites(){ return favourites; }

    public void addVideoToFavourites(Video video){ favourites.add(video); }
}