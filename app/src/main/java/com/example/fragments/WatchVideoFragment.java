package com.example.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.db.VideoDB;
import com.example.model.Video;
import com.example.myapplication.R;
import com.example.tools.VideoViewAdapter;
import com.example.tools.VideoViewListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.potyvideo.library.AndExoPlayerView;

import java.util.ArrayList;
import java.util.List;

public class WatchVideoFragment extends Fragment implements VideoViewListener {
    private RecyclerView hView;
    private VideoViewAdapter hAdapter;
    private RecyclerView.LayoutManager hManager;
    private Video v;
    private List<Video> videos;
    private AndExoPlayerView videoPlayerView;
    private TextView videoTitle;
    private Button addToFavorites;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private VideoDB videoDB;
    private Video currentVideo;

    public WatchVideoFragment() { }

    public static WatchVideoFragment newInstance(Video video) {
        WatchVideoFragment myFragment = new WatchVideoFragment();

        Bundle args = new Bundle();
        args.putSerializable("video", video);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_video, container, false);
        v = new Video();

        hView = view.findViewById(R.id.recycler_home);
        videoPlayerView = view.findViewById(R.id.videoPlayerView);
        videoTitle = view.findViewById(R.id.videoTitle);
        addToFavorites = view.findViewById(R.id.addToFavorites);

        hManager = new LinearLayoutManager(getActivity());
        hView.setLayoutManager(hManager);

        videoDB = new VideoDB(getActivity());

        loadVideos();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments()!=null){
            currentVideo = (Video) getArguments().getSerializable("video");
            videoPlayerView.setSource(currentVideo.getVideoURL());
            videoTitle.setText(currentVideo.getTitle());
            setupFavorites(currentVideo);
        }
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
        hAdapter = new VideoViewAdapter(getContext(),videos);
        hAdapter.videoViewListener = this;

        hView.setAdapter(hAdapter);

//        videoPlayerView.setSource(videos.get(0).getVideoURL());
//        videoTitle.setText(videos.get(0).getTitle());
    }

    //TEMPORARY METHOD TO CONVERT DRAWABLE TO BITMAP
    public Bitmap drawableToBitmap(int drawable) {
        Bitmap bm = BitmapFactory.decodeResource(getResources(), drawable);
        return bm;
    }

    @Override
    public void onVideoClick(Video video) {
        currentVideo= video;
        videoPlayerView.setSource(video.getVideoURL());
        videoTitle.setText(video.getTitle());
        setupFavorites(video);
    }

    private void setupFavorites(Video video) {
        if (videoDB.exists(video.getId())){
            addToFavorites.setText("Unsave");
            addToFavorites.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (videoDB.delete(video.getId())){
                        Toast.makeText(getActivity(), "Deleted from Favorites", Toast.LENGTH_SHORT).show();
                        setupFavorites(video);
                    } else{
                        Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else{
            addToFavorites.setText("Save");
            addToFavorites.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!videoDB.exists(video.getId())){
                        videoDB.insertData(video.getId(),video.getTitle(),video.getVideoURL(),video.getThumbnail());
                        setupFavorites(video);
                        Toast.makeText(getActivity(), "Added to Favorites", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(), "Already in Favorites", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
