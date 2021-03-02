package com.example.pages;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class fibase_database extends AppCompatActivity {

    private Button choose;
    private Button upload;
    private VideoView videoview;
    private Uri videoUri;
    MediaController media;
    private StorageReference storage;
    private DatabaseReference mDatabase;
    private static  final int PICK_VIDEO_REQUEST=1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fibase_database);
        choose= findViewById(R.id.button3);
        upload= findViewById(R.id.button4);
        videoview= findViewById(R.id.videoView2);

        media=new  MediaController(this);
        storage= FirebaseStorage.getInstance().getReference("videos");
        mDatabase = FirebaseDatabase.getInstance().getReference("videos");
        videoview.setMediaController(media);
        media.setAnchorView(videoview);
        videoview.start();
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            upload();
            }

            private void upload() {
                Intent intent =new Intent();
                intent.setType("videos/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult( intent,PICK_VIDEO_REQUEST);
            }
        });
                                  
            
            choose.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
               choose();
            }

            private void choose() {

            }
        });
    }
}
