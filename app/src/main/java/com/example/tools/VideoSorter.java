package com.example.tools;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.model.Video;

import java.util.List;

public class VideoSorter {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Video> sortByDifficultyThenAlphabetically(List<Video> vids){

        vids.sort((Video v1, Video v2) ->{
            return 1000 * (v1.getDifficulty().getDifficultyLevel() - v2.getDifficulty().getDifficultyLevel()) + compareAlphabetically(v1.getTitle(), v2.getTitle());
        });

        return vids;
    }

    private int compareAlphabetically(String s1, String s2){
        boolean s1Shorter = s1.length() - s2.length() < 0;

        int shorterLength = -1;

        if(s1Shorter) shorterLength = s1.length();

        for(int i = 0; i < shorterLength; i++){
            if(s1.charAt(i) != s2.charAt(i)) return s1.charAt(i) - s2.charAt(i);
        }

        if(!s1Shorter){
            return 1;
        }
        else{
            return -1;
        }
    }
}
