package com.example.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.Category;
import com.example.model.LoggedInUser;
import com.example.model.Video;
import com.example.myapplication.R;
import com.example.pages.MainActivity;
import com.example.pages.ui.login.LoginActivity;
import com.example.tools.CategoryAdapter;
import com.example.tools.CategoryListener;
import com.example.tools.VideoSorter;
import com.example.tools.VideoViewAdapter;
import com.example.tools.VideoViewListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class VideoFragment extends Fragment implements VideoViewListener, CategoryListener {
    private RecyclerView vView;
    private VideoViewAdapter vAdapter;
    private RecyclerView.LayoutManager vManager;
    private Spinner spCategories;
    private SearchView searchview;
    private Button searchButton;
    private Spinner sortingSpinner;
    private List<Video> videos;
    private List<String> categories;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String videoCategoryId = "categoriesDoc";

    private FirebaseAuth mAuth;
    private LoggedInUser user;
    private String documentId;

    private CategoryAdapter categoryAdapter;

    public VideoFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);

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
                                Log.d("vidUser", user.getDisplayName());
                            }

                        }
                    });
        }

        vView = (RecyclerView) view.findViewById(R.id.recycler_video);

        searchview = view.findViewById(R.id.searchview);

        vManager = new GridLayoutManager(getActivity(), 2);
        vView.setLayoutManager(vManager);

        spCategories = view.findViewById(R.id.spCategories);


        loadVideos();

        VideoSorter videoSorter = new VideoSorter();

        sortingSpinner = (Spinner) view.findViewById(R.id.video_sort);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.sorting_choices, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortingSpinner.setAdapter(adapter);

        sortingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedSort = (String) parent.getItemAtPosition(position);

                switch(selectedSort){
                    case "Title":
                        videos = videoSorter.sortAlphabetically(videos);
                        break;
                    case "Difficulty":
                        videos = videoSorter.sortByDifficultyThenAlphabetically(videos);
                        break;
                    case "Date":
                        videos = videoSorter.sortByDate(videos);
                        break;
                }

                loadVideosAdapter(videos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        searchButton = (Button) view.findViewById(R.id.video_search);
        searchButton.setOnClickListener(v -> {
            if (searchview.getQuery().toString().isEmpty()){
                Toast.makeText(getActivity(), "Search text cant be empty", Toast.LENGTH_SHORT).show();
            }else{
                searchVideos(searchview.getQuery().toString().trim());
            }
        });

        loadCategories();

        return view;
    }

    private void loadCategories() {

//        categories = new ArrayList<>();
//        categories.add("--Select Category--");
//        categories.add("Yoga");
//        categories.add("Fitness");
//        categories.add("Inner workout");
//        categories.add("Skills");
//        categories.add("Collective conversation");
//
//        ArrayAdapter dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, categories);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spCategories.setAdapter(dataAdapter);
//        spCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
//                if (position!=0){
//                    filterVideosByCategories(categories.get(position));
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
        db.collection("VideoCategories").document(videoCategoryId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
               /* if (documentSnapshot.get("category")!=null) {
                    categories = new ArrayList<>();
                    categories.add("--Select Category--");

                    List<String> list = (List<String>) documentSnapshot.get("category");
                    categories.addAll(list);
                    ArrayAdapter dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, categories);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spCategories.setAdapter(dataAdapter);

                    spCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                            if (position != 0) {
                                filterVideosByCategories(categories.get(position));
                            } else {
                                filterVideosByCategories("");
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }*/
                List<Category> categories = new ArrayList<>();

                if (documentSnapshot.get("categories")!=null){
                    Map<String,Object> map = (Map<String, Object>) documentSnapshot.get("categories");

                    for(Map.Entry<String,Object> entry : map.entrySet()){
                        Category category = new Category(entry.getKey(), (String) entry.getValue());
                        categories.add(category);
                    }
                }

                categoryAdapter = new CategoryAdapter(getContext(),categories);
                categoryAdapter.categoryListener = VideoFragment.this;
                vView.setLayoutManager(new LinearLayoutManager(getContext()));

                vView.setAdapter(categoryAdapter);
            }
        });
//        db.collection("categories").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                DocumentSnapshot ds = queryDocumentSnapshots.getDocuments().get(0);
//                if (ds.get("category")!=null){
//                    categories = new ArrayList<>();
//                    categories.add("--Select Category--");
//
//                    List<String> list = (List<String>) ds.get("category");
//                    categories.addAll(list);
//                    ArrayAdapter dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, categories);
//                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spCategories.setAdapter(dataAdapter);
//
//                    spCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                        @Override
//                        public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
//                            if (position!=0){
//                                filterVideosByCategories(categories.get(position));
//                            }else{
//                                filterVideosByCategories("");
//                            }
//                        }
//
//                        @Override
//                        public void onNothingSelected(AdapterView<?> adapterView) {
//
//                        }
//                    });
//                }
//            }
//        });

    }

    private void filterVideosByCategories(String s) {
        List<Video> tempFavs = new ArrayList();
        for(Video v: videos){
            if (v==null || v.getCategory()==null || v.getCategory().isEmpty())
            {
                continue;
            }

            if(v.getCategory().toLowerCase().contains(s.toLowerCase())){
                tempFavs.add(v);
            }
        }

        if (tempFavs.isEmpty()){
            Toast.makeText(getActivity(), "No Videos Available", Toast.LENGTH_SHORT).show();
        }
        //update recyclerview
        videos = tempFavs;
        loadVideosAdapter(videos);

    }

    private void searchVideos(String s) {
        List<Video> tempFavs = new ArrayList();
        for(Video v: videos){
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if(v.getTitle().toLowerCase().contains(s.toLowerCase())){
                tempFavs.add(v);
            }
        }

        if (tempFavs.isEmpty()){
            Toast.makeText(getActivity(), "No Videos Available", Toast.LENGTH_SHORT).show();
        }

        vAdapter.setVideos(tempFavs);
        vAdapter.notifyDataSetChanged();

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

                    Long difficultyLong = (Long) ds.get("difficulty");
                    video.setDifficulty(difficultyLong.intValue());

                    Timestamp timestamp = (Timestamp) ds.get("dateUploaded");
                    video.setDateUploaded(timestamp.toDate());

                    videos.add(video);
                }
            }
        });

        loadVideosAdapter(videos);
    }

    private void loadVideosAdapter(List<Video> videos) {
        vAdapter = new VideoViewAdapter(getContext(),videos);
        vAdapter.videoViewListener = this;

        vView.setAdapter(vAdapter);
    }

    @Override
    public void onVideoClick(Video video) {
        WatchVideoFragment fragment = WatchVideoFragment.newInstance(video, user);
        ((MainActivity)getActivity()).loadFragment(fragment);
    }

    @Override
    public void onCategoryClick(Category category) {
        loadVideoView(category.getCategory());
    }

    private void loadVideoView(String category){
        filterVideosByCategories(category);

        getView().findViewById(R.id.filter_layout).setVisibility(View.VISIBLE);
//        searchview.setVisibility(View.VISIBLE);
//        searchButton.setVisibility(View.VISIBLE);
//        sortingSpinner.setVisibility(View.VISIBLE);
        spCategories.setVisibility(View.GONE);
        vView.setVisibility(View.VISIBLE);


    }
}
