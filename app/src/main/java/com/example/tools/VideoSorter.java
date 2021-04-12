package com.example.tools;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.model.Video;

import java.util.List;

public class VideoSorter {

    public List<Video> sortById(List<Video> vids){
        vids.sort((Video v1, Video v2) ->{
            return compareAlphabetically(v1.getId(), v2.getId());
        });

        return vids;
    }
    public List<Video> sortByDate(List<Video> vids){
        vids.sort(this::compareByDate);

        return vids;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Video> sortByDifficultyThenAlphabetically(List<Video> vids){

        vids.sort((Video v1, Video v2) ->{
            return 1000 * compareByDifficulty(v1, v2) + compareAlphabetically(v1.getTitle(), v2.getTitle());
        });

        return vids;
    }

    public List<Video> sortAlphabetically(List<Video> vids){
        vids.sort((Video v1, Video v2) ->{
            return compareAlphabetically(v1.getTitle(), v2.getTitle());
        });

        return vids;
    }

    private int compareByDifficulty(Video v1, Video v2){
        return v1.getDifficulty().getDifficultyLevel() - v2.getDifficulty().getDifficultyLevel();
    }

    private int compareAlphabetically(String s1, String s2){
        boolean s1Shorter = s1.length() - s2.length() < 0;

        int shorterLength = -1;

        if(s1Shorter){
            shorterLength = s1.length();
        }else{
            shorterLength = s2.length();
        }

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

    private int compareByDate(Video v1, Video v2){
        if(v1.getDateUploaded().after(v2.getDateUploaded())){
            return 1;
        }else if(v2.getDateUploaded().after(v1.getDateUploaded())){
            return -1;
        }

        return 1;
    }
}
