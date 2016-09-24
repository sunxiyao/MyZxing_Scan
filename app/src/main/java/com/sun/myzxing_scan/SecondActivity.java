package com.sun.myzxing_scan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        String result = intent.getStringExtra("result");
        WebView webView=new WebView(this);
        webView.loadUrl(result);
        setContentView(webView);
    }
}
