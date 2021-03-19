package com.example.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fragments.data.SearchModel;
import com.example.myapplication.R;
import com.example.tools.VideoViewAdapter;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.FutureTask;

public class VideoFragment extends Fragment {
    private RecyclerView vView;
    private RecyclerView.Adapter vAdapter;
    private RecyclerView.LayoutManager vManager;

    private final FirebaseFirestore FIRESTORE = FirebaseFirestore.getInstance();
    private final String TAG = "Search Error";
    private List<SearchModel> searchList = new ArrayList<>();

    public VideoFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);

        addVideosToFirestore();

        vView = (RecyclerView) view.findViewById(R.id.recycler_video);

        vManager = new GridLayoutManager(getActivity(), 2);
        vView.setLayoutManager(vManager);

        List<String> dataset = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            dataset.add("Thumbnail " + i);
        }

        //vAdapter = new VideoViewAdapter(dataset);
        vView.setAdapter(vAdapter);

        Button vFilter = (Button) view.findViewById(R.id.video_filter);
        vFilter.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "Not yet implemented", Toast.LENGTH_SHORT).show();
        });

        return view;
    }

    private void addVideosToFirestore(){
        addToFirestore("How to do a handstand");
        addToFirestore("The only yoga video you need");
        addToFirestore("Yoga for beginners");
    }

    //https://www.youtube.com/watch?v=2z0HlIY7M9s consulted
    private void addToFirestore(String input){
        Map<String, Object> searchMap = new HashMap<>();
        searchMap.put("title", input);
        searchMap.put("search_keywords", generateSearchKeywords(input));

        FIRESTORE.collection("Videos").add(searchMap).addOnCompleteListener(task ->{
            if(!task.isSuccessful()){
                Log.d(TAG, task.getException().getMessage());
            }

        });
    }

    private void searchInFirestone(String searchText){
        FIRESTORE.collection("Videos").whereArrayContains("search_keywords", searchText)
                .startAt(searchText).endAt(searchText + "\uf8ff").get().addOnCompleteListener((task)->{
                    if(task.isSuccessful()){
                        searchList = task.getResult().toObjects(SearchModel.class);
                        //TODO update search list adapter
                    }
                    else{
                        Log.d(TAG, task.getException().getMessage());
                    }
        });
    }

    private List<String> generateSearchKeywords(String input){
        input = input.toLowerCase();
        List<String> keywords = new ArrayList<>();

        String[] words = input.split(" ");

        for(String word: words){
            keywords.add(word);
        }

        return keywords;
    }



}
