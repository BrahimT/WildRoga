package com.example.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
import com.example.model.LoggedInUser;
import com.example.model.Video;
import com.example.myapplication.R;
import com.example.pages.ui.login.LoginActivity;
import com.example.tools.VideoViewAdapter;
import com.example.tools.VideoViewListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

    private LoggedInUser user;


    public WatchVideoFragment() {

    }

    public static WatchVideoFragment newInstance(Video video, LoggedInUser user) {
        WatchVideoFragment myFragment = new WatchVideoFragment();

        Bundle args = new Bundle();
        args.putSerializable("video", video);
        args.putSerializable("user", user);
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
            user = (LoggedInUser) getArguments().getSerializable("user");
            videoPlayerView.setSource(currentVideo.getVideoURL());
            videoTitle.setText(currentVideo.getTitle());
            setupFavorites(currentVideo);
        }
    }

    public void loadVideos() {

        videos = new ArrayList<>();

        db.collection("Video").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot ds : queryDocumentSnapshots.getDocuments()){
                    Video video = new Video();
                    video.setId(ds.getId());
                    video.setTitle((String) ds.get("title"));
                    video.setVideoURL((String) ds.get("url"));
                    video.setThumbnail((String) ds.get("thumbnail"));
                    video.setCategory((String) ds.get("category"));
                    videos.add(video);
                }
                if (getArguments().containsKey("category")){
                    loadCategoryVideo(videos);
                }else {
                    loadVideosAdapter(videos);
                }
            }
        });
    }

    private void loadCategoryVideo(List<Video> videos) {
        this.videos = videos;
        List<Video> filteredVideos = filterVideosByCategories(getArguments().getString("category"));

        if (filteredVideos.isEmpty()){
            Toast.makeText(getActivity(), "No Videos Available", Toast.LENGTH_SHORT).show();
        }else {
            hAdapter = new VideoViewAdapter(getContext(),filteredVideos);
            hAdapter.videoViewListener = this;
            hView.setAdapter(hAdapter);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    onVideoClick(filteredVideos.get(0));
                }
            },1000);
        }
    }

    private List<Video> filterVideosByCategories(String s) {
        List<Video> tempFavs = new ArrayList();
        for(Video v: videos){
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if (v==null || v.getCategory()==null || v.getCategory().isEmpty())
            {
                continue;
            }

            if(v.getCategory().toLowerCase().contains(s.toLowerCase())){
                tempFavs.add(v);
            }
        }

        return tempFavs;
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

        if (user.getFavorites().contains(video)){
            addToFavorites.setText("Unsave");
            addToFavorites.setOnClickListener((a)-> {
                user.removeFromFavorites(video);

                FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                firestore.collection("users").document(user.getUserId()).update("favorites", user.getFavorites())
                        .addOnSuccessListener(task ->{
                            //TODO alert user of success

                        })
                        .addOnFailureListener(getActivity(), storeTask ->{
                            // TODO alert user of failure, ask them to try again
                            user.removeFromFavorites(video);
                        });

//                    new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (videoDB.delete(video.getId())){
//                        Toast.makeText(getActivity(), "Deleted from Favorites", Toast.LENGTH_SHORT).show();
//                        setupFavorites(video);
//                    } else{
//                        Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
//                    }
//                }
            });
        } else{
            addToFavorites.setText("Save");
            addToFavorites.setOnClickListener( (a) ->{


                user.addVideoToFavorites(video);

                FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                firestore.collection("users").document(user.getDocumentId()).update("favorites", user.getFavorites())
                        .addOnSuccessListener(task ->{
                            //TODO alert user of success
                            setupFavorites(video);
                        })
                        .addOnFailureListener(getActivity(), storeTask ->{
                            // TODO alert user of failure, ask them to try again
                            user.removeFromFavorites(video);
                        });


                //                @Override
//                public void onClick(View view) {
//                    if (!videoDB.exists(video.getId())){
//                        videoDB.insertData(video.getId(),video.getTitle(),video.getVideoURL(),video.getThumbnail());
//                        setupFavorites(video);
//                        Toast.makeText(getActivity(), "Added to Favorites", Toast.LENGTH_SHORT).show();
//                    }else{
//                        Toast.makeText(getActivity(), "Already in Favorites", Toast.LENGTH_SHORT).show();
//                    }
//                }
            });

        }
    }
}
