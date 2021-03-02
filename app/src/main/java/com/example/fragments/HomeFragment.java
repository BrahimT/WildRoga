package com.example.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.tools.VideoViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView hView;
    private RecyclerView.Adapter hAdapter;
    private RecyclerView.LayoutManager hManager;

    public HomeFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        hView = (RecyclerView) view.findViewById(R.id.recycler_home);

        hManager = new LinearLayoutManager(getActivity());
        hView.setLayoutManager(hManager);

        List<String> dataset = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            dataset.add("Thumbnail " + i);
        }

        hAdapter = new VideoViewAdapter(dataset);
        hView.setAdapter(hAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


    }
}
