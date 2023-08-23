package com.example.buku_saku.home;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.buku_saku.R;
import com.example.buku_saku.koneksi.ApiConnect;
import com.github.barteksc.pdfviewer.PDFView;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Pdf extends AppCompatActivity {
    private PDFView pdfView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        pdfView = findViewById(R.id.pdfview);

        String pdf = getIntent().getStringExtra("pdf");

        openPdfWithIntent(new File(pdf));

    }

    private void openPdfWithIntent(File file) {

        pdfView.fromFile(file).swipeVertical(true).enableSwipe(true)
                .showPageWithAnimation(true)
                .enableAnnotationRendering(true)
                .enableDoubletap(true)
        .load();
    }


}





    // Ubah "file.pdf" dengan alamat file PDF Anda di penyimpanan perangkat.


