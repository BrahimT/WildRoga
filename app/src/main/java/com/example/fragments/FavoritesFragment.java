package com.example.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.db.VideoDB;
import com.example.model.LoggedInUser;
import com.example.model.Video;
import com.example.myapplication.R;
import com.example.pages.MainActivity;
import com.example.pages.ui.login.LoginActivity;
import com.example.tools.VideoViewAdapter;
import com.example.tools.VideoViewListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
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

    private FirebaseAuth mAuth;
    private LoggedInUser user;
    private String userDocumentId;

    public FavoritesFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favorites, container, false);

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
                            }

                            vView = (RecyclerView) view.findViewById(R.id.recycler_video);

                            vManager = new GridLayoutManager(getActivity(), 2);
                            vView.setLayoutManager(vManager);


                            videoDB = new VideoDB(getActivity());

                            loadVideos();

                        }
                    });
        }



        return view;
    }

    public void loadVideos() {
        videos = user.getFavorites();

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
//    TODO cannot find symbol error when running app
    @Override
    public void onVideoClick(Video video) {
        WatchVideoFragment fragment = WatchVideoFragment.newInstance(video, user);
        ((MainActivity)getActivity()).loadFragment(fragment);
    }
}
