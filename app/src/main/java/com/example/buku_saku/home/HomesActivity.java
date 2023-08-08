package com.example.buku_saku.home;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.buku_saku.POJO.Materi;
import com.example.buku_saku.R;
import com.example.buku_saku.adapter.MateriArrayAdapter;
import com.example.buku_saku.koneksi.ApiConnect;
import com.example.buku_saku.menu.Menus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomesActivity extends AppCompatActivity {

    private ImageView list;
    private ListView ls;
    private TextView notfound;
    private ArrayList<Materi> arrayList;
    private MateriArrayAdapter adapter;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homes);

        notfound = findViewById(R.id.not);
        ls = findViewById(R.id.listView);
        list = findViewById(R.id.mn);
        arrayList = new ArrayList<>();
        adapter = new MateriArrayAdapter(this,arrayList);
        ls.setAdapter(adapter);



        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, ApiConnect.url_file, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++){
                        JSONObject jsonObject = response.getJSONObject(i);

                        String id = jsonObject.getString("id");
                        String materi = jsonObject.getString("materi");
                        String fileMateri = jsonObject.getString("fileMateri");
                        String ms = "not found";
                        System.out.println(materi);
                        Materi dataMateri = new Materi(materi, fileMateri, id);

                        if (dataMateri != null){
                            arrayList.add(dataMateri);
                        } else {
                            notfound.setText("NOT found");
                        }




                    }
                    adapter.notifyDataSetChanged();
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

        System.out.println(jsonArrayRequest);


        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Menus.class));
                finish();
            }
        });

        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              Materi pilih = arrayList.get(position);
              String materi = pilih.getMateri();
              String fileMateri = pilih.getFileMateri();
                String idString = String.valueOf(pilih.getId());


                Intent intent = new Intent(getApplicationContext(), Pdf.class);
                intent.putExtra("ID",idString);
                intent.putExtra("MATERI", materi);
                intent.putExtra("FILE",fileMateri);
                startActivity(intent);
            }
        });
    }
}