package com.example.model;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import com.example.myapplication.R;
import com.example.pages.MainActivity;
import com.google.gson.internal.bind.ArrayTypeAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Video implements Serializable {
    private String id;
    private int difficulty;
    private String title;
    private String thumbnail;
    private String videoURL;
    private String category;
    private Date dateUploaded;

    List<Video> videoList = new ArrayList<>();

    public Video() { }

    public Video(String id, String videoURL, int difficulty, String title, String thumbnail) {
        this(id, videoURL, difficulty, title, thumbnail, "None", Calendar.getInstance().getTime());
    }

    public Video(String id, String videoURL, int difficulty, String title, String thumbnail, String category, Date uploaded){
        this.id = id;
        this.videoURL = videoURL;
        this.difficulty = difficulty;
        this.title = title;
        this.thumbnail = thumbnail;
        this.category = category;
        this.dateUploaded = uploaded;
    }

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public int getDifficulty(){
        return this.difficulty;
    }

    public void setDifficulty(int difficulty){
        this.difficulty = difficulty;
    }

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getThumbnail() {
        return this.thumbnail;
    }

    public void setThumbnail() {
        this.thumbnail = thumbnail;
    }

    public List<Video> getVideoList() {
        return this.videoList;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public void setVideoList(List<Video> videoList) {
        this.videoList = videoList;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getDateUploaded() {return this.dateUploaded;};

    public void setDateUploaded(Date date){
        this.dateUploaded = date;
    }

    @Override
    public boolean equals(Object obj){
        if (!(obj instanceof Video)){
            return false;
        }

        Video video = (Video) obj;

        return video.getId().equals(this.getId());
    }
}
