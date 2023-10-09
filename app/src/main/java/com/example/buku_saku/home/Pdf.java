package com.example.buku_saku.home;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.KeyEvent;
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
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Pdf extends AppCompatActivity {
    private PDFView pdfView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        pdfView = findViewById(R.id.pdfview);

        String pdf = getIntent().getStringExtra("data");

        new LoadPdfTask().execute(pdf);

    }
    private class LoadPdfTask extends AsyncTask<String, Void, InputStream> {

        @Override
        protected InputStream doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    return connection.getInputStream();
                } else {
                   Toast.makeText(getApplicationContext(),"error bro",Toast.LENGTH_SHORT).show();
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            if (inputStream != null) {
                pdfView.fromStream(inputStream).load();
            } else {
                Toast.makeText(getApplicationContext(),"error bro",Toast.LENGTH_SHORT).show();
                // Handle error
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // Pada metode ini, Anda dapat menambahkan logika untuk mengatasi perilaku tombol kembali
            // Contoh: Jika Anda ingin kembali ke HomesActivity
            Intent intent = new Intent(this, HomesActivity.class);
            startActivity(intent);

            // Kembalikan nilai true untuk menunjukkan bahwa Anda telah menangani peristiwa tombol kembali
            return true;
        }
        // Jika bukan tombol kembali, biarkan perilaku default bekerja
        return super.onKeyDown(keyCode, event);
    }

//    private void openPdfWithIntent(File file) {
//
//        pdfView.fromFile(file).swipeVertical(true).enableSwipe(true)
//                .showPageWithAnimation(true)
//                .enableAnnotationRendering(true)
//                .enableDoubletap(true)
//        .load();
//    }


}





    // Ubah "file.pdf" dengan alamat file PDF Anda di penyimpanan perangkat.


