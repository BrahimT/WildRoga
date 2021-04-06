package com.example.tools;


import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;

public class SessionManager {
    private String uid;

    public SessionManager(){
    }

    public void findLoggedInUser(Context context){
        this.uid = FirebaseAuth.getInstance().getCurrentUser().getUid();


    }

    public String getUserId(){
        return this.uid;
    }
}
