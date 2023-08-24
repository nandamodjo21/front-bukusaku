package com.example.buku_saku.home;

import androidx.appcompat.app.AppCompatActivity;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomesActivity extends AppCompatActivity {

    private ImageView image;
    private String pdApiUrl;
    private TextView materi;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homes);

        image = findViewById(R.id.imagePdg);
        materi = findViewById(R.id.textMateri);







        RequestQueue requestQueue = Volley.newRequestQueue(this);




        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, ApiConnect.url_soal, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++){
                        JSONObject jsonObject = response.getJSONObject(i);

                        String id = jsonObject.getString("id_soal");
                        String nama_materi = jsonObject.getString("materi");
                        String fileMateri = jsonObject.getString("file_materi");
                        String soal = jsonObject.getString("soal");

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