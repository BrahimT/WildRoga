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
    private List<Video> favorites;
    private String documentId;

    //Required no-arg constructor
    public LoggedInUser(){
    }

    public LoggedInUser(FirebaseUser user, String name){
        this.userId = user.getUid();
        this.displayName = name;
        this.email = user.getEmail();
        this.favorites = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }
    public void setDocumentId(String id){
        this.documentId = id;
    }

    public String getDocumentId(){ return documentId;}

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail(){
        return this.email;
    }

    public List<Video> getFavorites(){ return favorites; }

    public void addVideoToFavorites(Video video){ favorites.add(video); }

    public void removeFromFavorites(Video video){ favorites.remove(video);}
}