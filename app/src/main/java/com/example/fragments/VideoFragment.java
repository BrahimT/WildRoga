package com.example.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.tools.VideoViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class VideoFragment extends Fragment {
    private RecyclerView vView;
    private RecyclerView.Adapter vAdapter;
    private RecyclerView.LayoutManager vManager;

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
        vView.setAdapter(vAdapter);

        Button vFilter = (Button) view.findViewById(R.id.video_filter);
        vFilter.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "Not yet implemented", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}
