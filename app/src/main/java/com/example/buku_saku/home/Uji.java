package com.example.buku_saku.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.buku_saku.R;
import com.example.buku_saku.koneksi.ApiConnect;

public class Uji extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uji);

        webView = findViewById(R.id.webView);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setSupportZoom(true);
        webSettings.setAllowFileAccess(true);

        webView.setWebViewClient( new MyBrowser());

        Intent intent = getIntent();
        String fileMateri = intent.getStringExtra("FILE");
        // Ganti "nama_file.pdf" dengan nama yang sesuai
        String pdfApiUrl = ApiConnect.url_download + "?filename=" + fileMateri;
        webView.loadUrl("http://drive.google.com/viewering/viewer?embedded=true&url=" + pdfApiUrl);
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            view.loadData("internet not connection","text/html","utf-8");
            super.onReceivedError(view, request, error);
        }
    }
}