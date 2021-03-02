package com.example.tools;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class VideoViewAdapter extends RecyclerView.Adapter<VideoViewAdapter.ViewHolder> {

    //This is for testing/demo purposes, remove when DB connection is implemented and replace with video thumbnails in imageview
    private List<String> demoList;

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_row_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getTextView().setText(demoList.get(position));
    }

    @Override
    public int getItemCount() {
        return demoList.size();
    }

    public void add(int position, String item) {
        demoList.add(position, item);
        notifyItemInserted(position);
    }

    public VideoViewAdapter(List<String> dataset) {
        demoList = dataset;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(@NonNull View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.itemText);
        }

        public TextView getTextView() {
            return textView;
        }
    }
}
