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
import android.view.KeyEvent;
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

    private Button btnjwb;
    private String jawaban, soalId;
    private TextView tekssoal;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uji);

        tekssoal = findViewById(R.id.textSoal);
        EditText jawab = findViewById(R.id.ed_jawab);
        btnjwb = findViewById(R.id.btnjawab);

        //intent dari home
        Intent intent = getIntent();

        String soal = intent.getStringExtra("soal");
         soalId = intent.getStringExtra("id");



        tekssoal.setText(soal);
        // Ganti "nama_file.pdf" dengan nama yang sesuai




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

        System.out.println(jsonObject);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiConnect.url_jawaban, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getInt("code")==200){
                        startActivity(new Intent(getApplicationContext(),HomesActivity.class));
                        Toast.makeText(getApplicationContext(),response.getString("message"),Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(),"jawaban sudah diisi!",Toast.LENGTH_SHORT).show();
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

}