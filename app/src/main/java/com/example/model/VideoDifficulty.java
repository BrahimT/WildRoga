package com.example.model;

public enum VideoDifficulty {
    EASY(1),
    MEDIUM(2),
    HARD(3);

    private final int level;

    VideoDifficulty(int level){
        this.level = level;
    }

    public int getDifficultyLevel(){
        return this.level;
    }

//    @Override
//    public String toString(){
//
//    }
}
