package com.example.fragments;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.LoggedInUser;
import com.example.model.Video;
import com.example.myapplication.R;
import com.example.pages.MainActivity;
import com.example.pages.ui.login.LoginActivity;
import com.example.tools.VideoViewAdapter;
import com.example.tools.VideoViewListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.potyvideo.library.AndExoPlayerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements VideoViewListener {
    private RecyclerView hView;
    private VideoViewAdapter hAdapter;
    private RecyclerView.LayoutManager hManager;
    private Video v;
    private List<Video> videos;
    private AndExoPlayerView videoPlayerView;
    private TextView videoTitle;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private LoggedInUser user;
    private String userDocumentId;

    private static TextView tvWelcome;

    public HomeFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //Verify if user is logged in to Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser fbUser = mAuth.getCurrentUser();

        //Redirect to login if user not in Firebase Auth
        if(fbUser == null){
            this.startActivity(new Intent(getActivity(), LoginActivity.class));
        }else{

            //TODO add error handling if user in Auth but not Firestore - Max

            //Get associated user data from Firestore
            FirebaseFirestore firestore = FirebaseFirestore.getInstance();
            firestore.collection("users")
                    .whereEqualTo("userId", fbUser.getUid())
                    .get()
                    .addOnCompleteListener(task ->{
                        if(task.isSuccessful()){
                            List<LoggedInUser> users = task.getResult().toObjects(LoggedInUser.class);

                            if(users.size() == 1){
                                user = users.get(0);
                                user.setDocumentId(task.getResult().getDocuments().get(0).getId());
                                Log.d("user", user.getDisplayName());

                                String firstName = user.getFirstName();
                                Resources res = getResources();

                                String formattedString = res.getString(R.string.welcome_user, firstName);

                                tvWelcome = view.findViewById(R.id.welcome_home);
                                tvWelcome.setText(formattedString);
                            }

                        }
                    });
        }

        hView = view.findViewById(R.id.recycler_home);
        hManager = new LinearLayoutManager(getActivity());
        hView.setLayoutManager(hManager);

        loadVideos();

        return view;
    }

//    public Button addToFavouritesButton(LoggedInUser user, Video video){
//        //Todo get real find button
//        Button b = (Button) getView();
//
//
//
//        return b;
//    }

//    public Button downloadButton(Video video){
//        Button b = (Button) getView();
//
//        b.setOnClickListener((a )->{
//            startDownloading(video.getVideoURL());
//            //TODO add some type of permissions check
//        });
//
//        return b;
//    }

//    //https://www.youtube.com/watch?v=c-SDbITS_R4 consulted
//    private void startDownloading(String url){
//        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
//
//        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
//        request.setTitle("Download");
//        request.setDescription("Downloading file...");
//
//        request.allowScanningByMediaScanner();
//        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "" + System.currentTimeMillis());
//
//        DownloadManager manager = (DownloadManager)getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
//        manager.enqueue(request);
//    }

    public void loadVideos() {
        videos = new ArrayList<>();

        db.collection("Video").get().addOnSuccessListener(queryDocumentSnapshots -> {

            for (DocumentSnapshot ds : queryDocumentSnapshots.getDocuments()){
                    Video video = new Video();
                    video.setId(ds.getId());
                    video.setTitle((String) ds.get("title"));
                    video.setVideoURL((String) ds.get("url"));
                    video.setThumbnail((String) ds.get("thumbnail"));
                    video.setCategory((String) ds.get("category"));

                    Long difficultyLong = (Long) ds.get("difficulty");
                    video.setDifficulty(difficultyLong.intValue());

                    Timestamp timestamp = (Timestamp) ds.get("dateUploaded");
                    video.setDateUploaded(timestamp.toDate());

                    videos.add(video);
            }

            loadVideosAdapter(videos);
        });
    }

    private void loadVideosAdapter(List<Video> videos) {
        hAdapter = new VideoViewAdapter(getContext(),videos);
        hAdapter.videoViewListener = this;

        hView.setAdapter(hAdapter);
    }

    @Override
    public void onVideoClick(Video video) {
        WatchVideoFragment fragment = WatchVideoFragment.newInstance(video, user);
        ((MainActivity)getActivity()).loadFragment(fragment);
    }
}
