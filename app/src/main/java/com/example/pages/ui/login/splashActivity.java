package com.example.pages.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebView;

import com.example.myapplication.R;

public class splashActivity extends Activity {
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final WebView v= findViewById(R.id.webview);
        v.getSettings().setJavaScriptEnabled(true);
        v.loadUrl("file:///android_asset/hello.html");
        handler=new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent intent=new Intent(splashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },5000);

    }
}
