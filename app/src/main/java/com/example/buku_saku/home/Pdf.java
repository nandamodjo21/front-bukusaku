package com.example.buku_saku.home;

import androidx.appcompat.app.AppCompatActivity;

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


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Pdf extends AppCompatActivity {

    private ImageView pdfImageView;
    private PdfRenderer pdfRenderer;
    private PdfRenderer.Page currentPage;
    private ParcelFileDescriptor parcelFileDescriptor;

    private WebView webView;
    private RequestQueue requestQueue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        pdfImageView = findViewById(R.id.pdfImageView);
        requestQueue = Volley.newRequestQueue(this);

        Intent intent = getIntent();
        String fileMateri = intent.getStringExtra("FILE");
       // Ganti "nama_file.pdf" dengan nama yang sesuai
        String pdfApiUrl = ApiConnect.url_download + "?filename=" + fileMateri;
        downloadPdfFromApi(pdfApiUrl);



    }

    private void downloadPdfFromApi(String pdfApiUrl) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, pdfApiUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    File pdfFile = new File(getExternalFilesDir(null), "materi.pdf");
                    try (OutputStream outputStream = new FileOutputStream(pdfFile)) {
                        byte[] pdfData = response.getBytes();
                        outputStream.write(pdfData);
                        outputStream.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Log.d("DownloadSuccess", "PDF successfully downloaded and saved.");



                    parcelFileDescriptor = ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY);
                    pdfRenderer = new PdfRenderer(parcelFileDescriptor);
                    showPage(0);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();

            }
        }); requestQueue.add(stringRequest);
    }

    private void showPage(int i) {

        if (currentPage != null) {
            currentPage.close();
        }
        currentPage = pdfRenderer.openPage(i);

        // Buat Bitmap untuk menampilkan halaman PDF
        Bitmap bitmap = Bitmap.createBitmap(currentPage.getWidth(), currentPage.getHeight(), Bitmap.Config.ARGB_8888);

        // Atur halaman PDF ke dalam Bitmap
        currentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

        // Tampilkan Bitmap di ImageView
        pdfImageView.setImageBitmap(bitmap);

    }
    }





    // Ubah "file.pdf" dengan alamat file PDF Anda di penyimpanan perangkat.


