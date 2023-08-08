package com.example.buku_saku.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
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
import java.io.FileOutputStream;
import java.io.IOException;

public class Pdf extends AppCompatActivity {

    private ImageView pdfImageView;
    private PdfRenderer pdfRenderer;
    private PdfRenderer.Page currentPage;
    private ParcelFileDescriptor parcelFileDescriptor;

    private WebView webView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        pdfImageView = findViewById(R.id.pdfImageView);

        webView = findViewById(R.id.webView);




        downloadAndDisplayPdf();
//        File file = new File(getFilesDir(), "file.pdf");
//
//        try {
//            openPdfRenderer(file);
//            displayPage(0);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
    private void downloadAndDisplayPdf() {
        String id = getIntent().getStringExtra("ID");
        String pdfUrl = ApiConnect.url_download + id;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, pdfUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

               downloadAndSavePdf(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);requestQueue.add(stringRequest);
    }

    private void downloadAndSavePdf(String pdfUrl) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String filename = "downloaded_pdf.pdf"; // Nama berkas yang akan disimpan
        File pdfFile = new File(getFilesDir(), filename);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, pdfUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // Simpan berkas PDF ke penyimpanan perangkat
                    FileOutputStream outputStream = new FileOutputStream(pdfFile);
                    outputStream.write(response.getBytes());
                    outputStream.close();

                    // Buka dan tampilkan berkas PDF menggunakan PdfRenderer
                    openAndDisplayPdf(pdfFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);
    }

    private void openAndDisplayPdf(File pdfFile) {
        try {
            // Buka PdfRenderer dengan berkas yang telah diunduh
            parcelFileDescriptor = ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY);
            if (parcelFileDescriptor != null) {
                pdfRenderer = new PdfRenderer(parcelFileDescriptor);
                displayPage(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayPage(int pageNumber) {
        if (pdfRenderer != null && pdfRenderer.getPageCount() > pageNumber) {
            if (currentPage != null) {
                currentPage.close();
            }

            currentPage = pdfRenderer.openPage(pageNumber);

            Bitmap bitmap = Bitmap.createBitmap(currentPage.getWidth(), currentPage.getHeight(), Bitmap.Config.ARGB_8888);
            currentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
            pdfImageView.setImageBitmap(bitmap);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (currentPage != null) {
            currentPage.close();
        }
        if (pdfRenderer != null) {
            pdfRenderer.close();
        }
        try {
            if (parcelFileDescriptor != null) {
                parcelFileDescriptor.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
      
    }



    // Ubah "file.pdf" dengan alamat file PDF Anda di penyimpanan perangkat.


