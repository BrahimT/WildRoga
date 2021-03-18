package com.example.tools;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.Video;
import com.example.myapplication.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class VideoViewAdapter extends RecyclerView.Adapter<VideoViewAdapter.ViewHolder> {
    //This is for testing/demo purposes, remove when DB connection is implemented and replace with video thumbnails in imageview
    private List<Video> videos;

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_cardview, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getThumbnailView().setImageBitmap(videos.get(position).getThumbnail());
        holder.getTitleView().setText(videos.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public void add(int position, Video video) {
        videos.add(position, video);
        notifyItemInserted(position);
    }

    public VideoViewAdapter(List<Video> videos) {
        this.videos = videos;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView videoThumbnail;
        private final TextView videoTitle;


        public ViewHolder(@NonNull View view) {
            super(view);
            videoThumbnail = view.findViewById(R.id.video_thumbnail);
            videoTitle =  view.findViewById(R.id.video_title);
        }

        public ImageView getThumbnailView() {
            return videoThumbnail;
        }

        public TextView getTitleView() {
            return videoTitle;
        }
    }
}
