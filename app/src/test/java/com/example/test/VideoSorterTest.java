package com.example.test;


import com.example.model.Video;
import com.example.model.VideoDifficulty;
import com.example.tools.PasswordUtilities;
import com.example.tools.VideoSorter;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class VideoSorterTest {

    private final VideoSorter videoSorter = new VideoSorter();
    private List<Video> testVideos;

    @Test
    public void testAlphabeticSort(){
        setTestVideos();

        testVideos = videoSorter.sortAlphabetically(testVideos);

        assertEquals("How to dance", testVideos.get(0).getTitle());
        assertEquals("Why to yoga", testVideos.get(5).getTitle());
    }

    @Test
    public void testSortByDifficulty(){
        setTestVideos();

        testVideos = videoSorter.sortByDifficultyThenAlphabetically(testVideos);

        assertEquals("What is yoga", testVideos.get(0).getTitle());
        assertEquals("Never yoga", testVideos.get(5).getTitle());
    }

    @Test
    public void testSortById(){
        setTestVideos();

        testVideos = videoSorter.sortByDifficultyThenAlphabetically(testVideos);
        testVideos = videoSorter.sortById(testVideos);

        assertEquals("How to yoga", testVideos.get(0).getTitle());
        assertEquals("Why is yoga", testVideos.get(5).getTitle());
    }


    private void setTestVideos(){
        testVideos = new ArrayList<>();

        testVideos.add(new Video("1", "", VideoDifficulty.HARD, "How to yoga", "", "", null));
        testVideos.add(new Video("2", "", VideoDifficulty.EASY, "Why to yoga", "", "", null));
        testVideos.add(new Video("3", "", VideoDifficulty.MEDIUM, "How to dance", "", "", null));
        testVideos.add(new Video("4", "", VideoDifficulty.HARD, "Never yoga", "", "", null));
        testVideos.add(new Video("5", "", VideoDifficulty.EASY, "What is yoga", "", "", null));
        testVideos.add(new Video("6", "", VideoDifficulty.MEDIUM, "Why is yoga", "", "", null));

    }

}
