package com.example.model;

public class Video {

    private int id;
    private VideoDifficulty difficulty;
    private String title;

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
}
