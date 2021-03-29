package com.example.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.Video;
import com.example.myapplication.R;
import com.example.pages.MainActivity;
import com.example.tools.VideoViewAdapter;
import com.example.tools.VideoViewListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class VideoFragment extends Fragment implements VideoViewListener {
    private RecyclerView vView;
    private VideoViewAdapter vAdapter;
    private RecyclerView.LayoutManager vManager;
    private List<Video> videos;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public VideoFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);

        vView = (RecyclerView) view.findViewById(R.id.recycler_video);

        vManager = new GridLayoutManager(getActivity(), 2);
        vView.setLayoutManager(vManager);

        List<String> dataset = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            dataset.add("Thumbnail " + i);
        }

        //vAdapter = new VideoViewAdapter(dataset);
        //vView.setAdapter(vAdapter);

        loadVideos();

        Button vFilter = (Button) view.findViewById(R.id.video_filter);
        vFilter.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "Not yet implemented", Toast.LENGTH_SHORT).show();
        });

        return view;
    }

    public void loadVideos() {

        videos = new ArrayList<>();

        db.collection("Videos").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot ds : queryDocumentSnapshots.getDocuments()){
                    Video video = new Video();
                    video.setId(ds.getId());
                    video.setTitle((String) ds.get("title"));
                    video.setVideoURL((String) ds.get("url"));
                    video.setThumbnail((String) ds.get("thumbnail"));
                    videos.add(video);
                }

                loadVideosAdapter(videos);
            }
        });
    }

    private void loadVideosAdapter(List<Video> videos) {
        vAdapter = new VideoViewAdapter(getContext(),videos);
        vAdapter.videoViewListener = this;

        vView.setAdapter(vAdapter);
    }

    @Override
    public void onVideoClick(Video video) {

        WatchVideoFragment fragment = WatchVideoFragment.newInstance(video.getVideoURL(),video.getTitle());
        ((MainActivity)getActivity()).loadFragment(fragment);

    }
}
