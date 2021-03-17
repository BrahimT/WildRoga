package com.example.fragments.data;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

//https://www.youtube.com/watch?v=2z0HlIY7M9s consulted
public class SearchDataSource {

    private FirebaseAuth fbAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    private final String TAG = "SearchDataSourceError";

    public void addToFirestore(String input){
        HashMap<String, String> searchMap = new HashMap<>();
        searchMap.put("title", input);
        searchMap.put("search_keywords", input.toLowerCase());

        firestore.collection("Videos").add(searchMap).addOnCompleteListener((it) -> {
                if(!it.isSuccessful()){
                    Log.d(TAG, it.getException().getMessage());
                }
        });
    }

    public List<SearchModel> searchInFirestore(String searchText){
        AtomicReference<List<SearchModel>> searchList = null;
        firestore.collection("Videos").orderBy("search_keywords")
                .startAt(searchText).endAt(searchText + "\uf8ff").get().addOnCompleteListener((it) ->{
                    if(it.isSuccessful()){
                         searchList.set(it.getResult().toObjects(SearchModel.class));
                    }else{
                        Log.d(TAG, it.getException().getMessage());
                    }
        });
        
        return searchList.get();
    }


}
