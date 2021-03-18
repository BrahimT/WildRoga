package com.example.tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.model.Video;
import com.example.myapplication.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class VideoViewAdapter extends RecyclerView.Adapter<VideoViewAdapter.ViewHolder> {
    //This is for testing/demo purposes, remove when DB connection is implemented and replace with video thumbnails in imageview
    private List<Video> videos;
    private Context context;
    public VideoViewListener videoViewListener;

    public VideoViewAdapter(Context context){
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_cardview, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(videos.get(position).getThumbnail()).into(holder.getThumbnailView());
        holder.getTitleView().setText(videos.get(position).getTitle());

        holder.itemView.setOnClickListener(view -> {
            if(videoViewListener!=null){
                videoViewListener.onVideoClick(videos.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public void add(int position, Video video) {
        videos.add(position, video);
        notifyItemInserted(position);
    }

    public VideoViewAdapter(Context context,List<Video> videos) {
        this.context = context;
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
