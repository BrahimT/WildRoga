package com.example.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.LoggedInUser;
import com.example.model.Video;
import com.example.myapplication.R;
import com.example.tools.VideoViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView hView;
    private RecyclerView.Adapter hAdapter;
    private RecyclerView.LayoutManager hManager;
    private Video v;
    private List<Video> videos;

    public HomeFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        v = new Video();

        hView = view.findViewById(R.id.recycler_home);

        hManager = new LinearLayoutManager(getActivity());
        hView.setLayoutManager(hManager);

        hAdapter = new VideoViewAdapter(loadVideos());

        hView.setAdapter(hAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

    public Button addToFavouritesButton(LoggedInUser user, Video video){
        //Todo get real find button
        Button b = (Button) getView();

        b.setOnClickListener( (a) ->{
            user.addVideoToFavourites(video);
        });

        return b;
    }

    //TEMPORARY METHOD TO LOAD VIDEOS TO LIST | TESTING
    public List<Video> loadVideos() {
        videos = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            videos.add(new Video(i, null, "video title" + i, drawableToBitmap(R.drawable.banner_home)));
        }

        return videos;
    }

    //TEMPORARY METHOD TO CONVERT DRAWABLE TO BITMAP
    public Bitmap drawableToBitmap(int drawable) {
        Bitmap bm = BitmapFactory.decodeResource(getResources(), drawable);
        return bm;
    }
}
