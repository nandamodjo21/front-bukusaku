package com.example.buku_saku;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.buku_saku.koneksi.ApiConnect;
import com.example.buku_saku.login.Login;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Register extends AppCompatActivity {

    private EditText r_namaL, r_username,r_password,r_nim;

    private String username,password,nim,nama,jenis,Agama;

    private List<String> jenisKelamin = new ArrayList<>();
    private List<String> agama = new ArrayList<>();
    private HashMap<String, String> jenisMap = new HashMap<>();
    private HashMap<String, String> agamaMap = new HashMap<>();
    private TextView login;

    private Button regist;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        r_namaL = findViewById(R.id.r_nama);
        r_username = findViewById(R.id.r_user);
        r_password = findViewById(R.id.r_pass);
        r_nim = findViewById(R.id.r_nim);
        Spinner jkSpinner = findViewById(R.id.jk);
        Spinner agamaSpinner = findViewById(R.id.agama);





        regist = findViewById(R.id.btn_regist);
        login = findViewById(R.id.backLogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = r_username.getText().toString().trim();
                password = r_password.getText().toString().trim();
                nama = r_namaL.getText().toString().trim();
                nim = r_nim.getText().toString().trim();
                Agama = agamaMap.get(agamaSpinner.getSelectedItem().toString().trim());
                jenis = jenisMap.get(jkSpinner.getSelectedItem().toString().trim());

                if (username.equals("")){
                    r_username.setError("harus di isi");
                } else if (password.equals("")) {
                    r_password.setError("harus di isi");
                } else if (nama.equals("")) {
                    r_namaL.setError("harus di isi");
                } else if (nim.equals("")) {
                    r_nim.setError("harus di isi");
                } else {
                    try {
                        registerNow();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });


        StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiConnect.url_jk, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i =0; i<jsonArray.length(); i++){
                        JSONObject js = jsonArray.getJSONObject(i);
                        String id = js.getString("id_jk");
                        String jk = js.getString("jenis_kelamin");
                        jenisKelamin.add(jk);

                        jenisMap.put(jk,id);
                    }
                    ArrayAdapter<String> jkAdapter = new ArrayAdapter<>(Register.this,R.layout.spinner_item_layout, jenisKelamin);
                    jkAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    jkSpinner.setAdapter(jkAdapter);
                    Log.d("data jenis", jenisKelamin.toString());

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

        RequestQueue requestQueue = Volley.newRequestQueue(this); requestQueue.add(stringRequest);

        StringRequest request = new StringRequest(Request.Method.GET, ApiConnect.url_agama, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i =0; i<jsonArray.length(); i++){
                        JSONObject js = jsonArray.getJSONObject(i);
                        String idAgama = js.getString("id_agama");
                        String ag = js.getString("agama");
                        agama.add(ag);
                        agamaMap.put(ag,idAgama);
                    }
                    ArrayAdapter<String> agamaAdapter = new ArrayAdapter<>(Register.this,R.layout.spinner_item_layout, agama);
                    agamaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    agamaSpinner.setAdapter(agamaAdapter);

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

        RequestQueue newRequestQueue = Volley.newRequestQueue(this); newRequestQueue.add(request);


    }




    private void registerNow() throws JSONException{
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("namaLengkap",nama);
        jsonObject.put("nim",nim);
        jsonObject.put("username",username);
        jsonObject.put("password",password);
        jsonObject.put("jk", jenis);
        jsonObject.put("agama", Agama);

        Log.d("data_akhir", jsonObject.toString());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, ApiConnect.url_regis, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if (response.getInt("code")==200){
                        Toast.makeText(getApplicationContext(),response.getString("message"),Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), Login.class));
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(),"register gagal",Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(this); requestQueue.add(jsonObjectRequest);
    }
}