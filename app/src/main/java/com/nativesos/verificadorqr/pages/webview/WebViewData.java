package com.nativesos.verificadorqr.pages.webview;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nativesos.verificadorqr.R;
import com.nativesos.verificadorqr.utility.Utility;

public class WebViewData extends AppCompatActivity {

    WebView webViewData;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_data);


        webViewData = (WebView) findViewById(R.id.webViewData);


        getWebView( getIntent().getExtras().getString(Utility.URL) );


    }


    @SuppressLint("SetJavaScriptEnabled")
    private void getWebView(String url){

        // se muestra el dato en la pantalla
        try {

            webViewData.getSettings().setJavaScriptEnabled(true);
            webViewData.getSettings().setAllowFileAccess(true);
            webViewData.getSettings().setAllowContentAccess(true);
            webViewData.getSettings().setAllowUniversalAccessFromFileURLs(true);

            webViewData.loadUrl(url);

        } catch (Exception e) {

            e.printStackTrace();

        }



    }

}