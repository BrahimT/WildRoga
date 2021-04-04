package com.example.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.db.VideoDB;
import com.example.model.Video;
import com.example.myapplication.R;
import com.example.pages.MainActivity;
import com.example.tools.VideoViewAdapter;
import com.example.tools.VideoViewListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment implements VideoViewListener {

    private RecyclerView vView;
    private VideoViewAdapter vAdapter;
    private RecyclerView.LayoutManager vManager;
    private List<Video> videos;
    private VideoDB videoDB;
    private View view;

    public FavoritesFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favorites, container, false);


        vView = (RecyclerView) view.findViewById(R.id.recycler_video);

        vManager = new GridLayoutManager(getActivity(), 2);
        vView.setLayoutManager(vManager);


        videoDB = new VideoDB(getActivity());

        loadVideos();

        return view;
    }

    public void loadVideos() {

        videos = new ArrayList<>();

        videos = videoDB.getAllFavVideos();

        if (videos==null || videos.isEmpty()) {
            Toast.makeText(getActivity(), "There's nothing in favorites for now!", Toast.LENGTH_SHORT).show();
        }else{
            loadVideosAdapter(videos);
        }
    }

    private void loadVideosAdapter(List<Video> videos) {
        vAdapter = new VideoViewAdapter(getContext(),videos);
        vAdapter.videoViewListener = this;

        vView.setAdapter(vAdapter);
    }

    @Override
    public void onVideoClick(Video video) {
        WatchVideoFragment fragment = WatchVideoFragment.newInstance(video);
        ((MainActivity)getActivity()).loadFragment(fragment);
    }
}
