package com.example.buku_saku.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.buku_saku.R;
import com.example.buku_saku.koneksi.ApiConnect;
import com.example.buku_saku.session.SharedPref;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class Uji extends AppCompatActivity {

    private Button openPdfButton,btnjwb;
    private String pdfApiUrl,jawaban, soalId;
    private TextView textView,tekssoal;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uji);
        openPdfButton = findViewById(R.id.openPdfButton);
        tekssoal = findViewById(R.id.textSoal);
        EditText jawab = findViewById(R.id.ed_jawab);
        btnjwb = findViewById(R.id.btnjawab);
        textView = findViewById(R.id.txt);

        //intent dari home
        Intent intent = getIntent();
        String fileMateri = intent.getStringExtra("FILE");
        String soal = intent.getStringExtra("SOAL");
         soalId = intent.getStringExtra("ID");



        tekssoal.setText(soal);
        textView.setText(fileMateri);
        // Ganti "nama_file.pdf" dengan nama yang sesuai
        pdfApiUrl = ApiConnect.url_download + "?filename=" + fileMateri;

        openPdfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPdf(pdfApiUrl);
            }
        });

        btnjwb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jawaban = jawab.getText().toString().trim();

                if (jawaban.equals("")){
                    jawab.setError("wajib di isi");
                } else {
                    try {
                        saveJawaban();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    private void saveJawaban() throws JSONException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("idUser", SharedPref.getInstance(getApplicationContext()).getKeyId());
        jsonObject.put("soal",soalId);
        jsonObject.put("jawaban",jawaban);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiConnect.url_jawaban, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getInt("code")==200){
                        startActivity(new Intent(getApplicationContext(),HomesActivity.class));
                        Toast.makeText(getApplicationContext(),response.getString("message"),Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(),response.getString("message"),Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext()); requestQueue.add(jsonObjectRequest);

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
            Toast.makeText(getApplicationContext(),"Pdf masih di download",Toast.LENGTH_SHORT).show();
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

            BroadcastReceiver onComplete = new BroadcastReceiver() {
                public void onReceive(Context ctxt, Intent intent) {
                    openPdfWithIntent(pdfFile);
                    unregisterReceiver(this);
                }
            };

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