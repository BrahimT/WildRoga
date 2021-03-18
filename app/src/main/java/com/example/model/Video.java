package com.example.model;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import com.example.myapplication.R;
import com.example.pages.MainActivity;
import com.google.gson.internal.bind.ArrayTypeAdapter;

import java.util.ArrayList;
import java.util.List;

public class Video {
    private int id;
    private VideoDifficulty difficulty;
    private String title;
    private Bitmap thumbnail;

    List<Video> videoList = new ArrayList<>();

    public Video() { }

    public Video(int id, VideoDifficulty difficulty, String title, Bitmap thumbnail) {
        this.id = id;
        this.difficulty = difficulty;
        this.title = title;
        this.thumbnail = thumbnail;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public VideoDifficulty getDifficulty(){
        return this.difficulty;
    }

    public void setDifficulty(VideoDifficulty difficulty){
        this.difficulty = difficulty;
    }

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public Bitmap getThumbnail() {
        return this.thumbnail;
    }

    public void setThumbnail() {
        this.thumbnail = thumbnail;
    }

    public List<Video> getVideoList() {
        return this.videoList;
    }
}
