package com.example.buku_saku.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.buku_saku.R;
import com.example.buku_saku.koneksi.ApiConnect;
import com.joanzapata.pdfview.PDFView;

import java.io.File;
import java.io.IOException;

public class Pdf extends AppCompatActivity {

    private ImageView pdfImageView;
    private PdfRenderer pdfRenderer;
    private PdfRenderer.Page currentPage;
    private ParcelFileDescriptor parcelFileDescriptor;

    private PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        pdfImageView = findViewById(R.id.pdfImageView);

        pdfView = findViewById(R.id.pdf);


        downloadAndDisplayPdf();

        // Ubah "file.pdf" dengan alamat file PDF Anda di penyimpanan perangkat.
        File file = new File(getFilesDir(), "file.pdf");

        try {
            openPdfRenderer(file);
            displayPage(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void downloadAndDisplayPdf() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiConnect.url_file, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String pdfUrl = response;
                downloadPdfFile(pdfUrl);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    private void downloadPdfFile(String pdfUrl) {

    }

    private void openPdfRenderer(File file) throws IOException {
        parcelFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
        if (parcelFileDescriptor != null) {
            pdfRenderer = new PdfRenderer(parcelFileDescriptor);
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