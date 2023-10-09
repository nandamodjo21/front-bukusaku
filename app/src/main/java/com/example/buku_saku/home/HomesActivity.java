package com.example.buku_saku.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.buku_saku.R;
import com.example.buku_saku.koneksi.ApiConnect;
import com.example.buku_saku.menu.Menus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomesActivity extends AppCompatActivity {

    private ImageView image,mna;
    private String pdApiUrl,soal2,id;
    private TextView materi;



    private AppCompatButton btnSoal;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homes);

        image = findViewById(R.id.imagePdg);
        materi = findViewById(R.id.textMateri);
        btnSoal = findViewById(R.id.btnSoal);
        mna = findViewById(R.id.mn);

        mna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Menus.class));
                finish();
            }
        });






        RequestQueue requestQueue = Volley.newRequestQueue(this);




        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, ApiConnect.url_soal, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++){
                        JSONObject jsonObject = response.getJSONObject(i);

                         id = jsonObject.getString("id_soal");
                        String nama_materi = jsonObject.getString("materi");
                        String fileMateri = jsonObject.getString("file_materi");
                         soal2 = jsonObject.getString("soal");

                        materi.setText(nama_materi);

                        pdApiUrl = ApiConnect.url_download + "?filename=" + fileMateri;

                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();

            }
        }); requestQueue.add(jsonArrayRequest);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              openPDf(pdApiUrl);
            }
        });

        btnSoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Buat intent untuk membuka Activity_Uji
                Intent intent = new Intent(HomesActivity.this, Uji.class);

                // Masukkan parameter soal ke dalam intent
                intent.putExtra("id",id);
                intent.putExtra("soal", soal2);

                // Mulai Activity_Uji dengan intent yang sudah dibuat
                startActivity(intent);
            }
        });

    }

    private void openPDf(String pdApiUrl) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, pdApiUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

              Intent intent = new Intent(getApplicationContext(), Pdf.class);
              intent.putExtra("data", pdApiUrl);
              startActivity(intent);
              finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this); requestQueue.add(stringRequest);
    }


}