package com.example.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

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