package com.example.buku_saku.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buku_saku.R;
import com.example.buku_saku.koneksi.ApiConnect;

import java.io.File;

public class Uji extends AppCompatActivity {

    private Button openPdfButton;
    private String pdfApiUrl;
    private TextView textView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uji);
        openPdfButton = findViewById(R.id.openPdfButton);
        textView = findViewById(R.id.txt);
        Intent intent = getIntent();
        String fileMateri = intent.getStringExtra("FILE");

        textView.setText(fileMateri);
        // Ganti "nama_file.pdf" dengan nama yang sesuai
        pdfApiUrl = ApiConnect.url_download + "?filename=" + fileMateri;

        openPdfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPdf(pdfApiUrl);
            }
        });
    }

    private void openPdf(String pdfApiUrl) {

        String pdfFileName = pdfApiUrl;

        File pdfFile = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), pdfFileName);
        if (pdfFile.exists()) {
            // Jika file PDF sudah ada, langsung buka
            openPdfWithIntent(pdfFile);
        } else {
            // Jika file PDF belum ada, unduh terlebih dahulu
            downloadPdf(pdfApiUrl, pdfFile);
        }



    }

    private void downloadPdf(String pdfApiUrl, File pdfFile) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(pdfApiUrl))
                .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle("materi.pdf")
                .setDescription("Downloading PDF file...")
                .setDestinationUri(Uri.fromFile(pdfFile))
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        if (downloadManager != null) {
            long downloadId = downloadManager.enqueue(request);
        }
    }

    private void openPdfWithIntent(File pdfFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri pdfUri = FileProvider.getUriForFile(this, "com.example.buku_saku.fileprovider", pdfFile);

        intent.setDataAndType(pdfUri, "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // Handle jika tidak ada aplikasi yang dapat menangani tipe MIME PDF
            Toast.makeText(this, "Tidak ada aplikasi yang dapat membuka PDF", Toast.LENGTH_SHORT).show();
        }
    }
}