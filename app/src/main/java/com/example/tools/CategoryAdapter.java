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
import com.example.model.Category;
import com.example.model.Video;
import com.example.myapplication.R;

import java.util.List;

public class CategoryAdapter  extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    //This is for testing/demo purposes, remove when DB connection is implemented and replace with video thumbnails in imageview
    private List<Category> categories;
    private Context context;
    public CategoryListener categoryListener;

    public CategoryAdapter(Context context){
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_categories, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(categories.get(position).getCategoryPicURL()).into(holder.getThumbnailView());

        holder.itemView.setOnClickListener(view -> {
            if(categoryListener!=null){
                categoryListener.onCategoryClick(categories.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }



    public CategoryAdapter(Context context,List<Category> videos) {
        this.context = context;
        this.categories = videos;
    }

    public void setVideos(List<Category> tempFavs) {
        this.categories = tempFavs;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView videoThumbnail;


        public ViewHolder(@NonNull View view) {
            super(view);
            videoThumbnail = view.findViewById(R.id.ivCategory);
        }

        public ImageView getThumbnailView() {
            return videoThumbnail;
        }
    }

}
