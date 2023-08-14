package com.example.buku_saku.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.buku_saku.R;
import com.example.buku_saku.home.HomesActivity;
import com.example.buku_saku.koneksi.ApiConnect;
import com.example.buku_saku.session.SharedPref;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    
    private EditText l_username, l_password;
    private Button login;
    String username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        l_username = findViewById(R.id.user);
        l_password = findViewById(R.id.pass);
        login = findViewById(R.id.btn_login);

        if (SharedPref.getInstance(this).isLoggedIn()){
            startActivity(new Intent(getApplicationContext(),HomesActivity.class));
        } else {

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = l_username.getText().toString().trim();
                password = l_password.getText().toString().trim();
                
                if (username.equals("")){
                    l_username.setError("Username harus di isi");
                } else if (password.equals("")) {
                    l_password.setError("Password harus diisi");
                }else {
                    try {
                        aksesLogin();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        }

    }

    private void aksesLogin() throws JSONException{

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username",username);
        jsonObject.put("password",password);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,ApiConnect.url_login,jsonObject,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject js = response.getJSONObject("data");
                    String message = response.getString("message");
                  if (response.getInt("status") == 200){
                      SharedPref.getInstance(getApplicationContext())
                              .session(js.getString("id_login")
                              ,js.getString("nik")
                              ,js.getString("username"));

                      Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                      startActivity(new Intent(getApplicationContext(), HomesActivity.class));
                  } else {
                      Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(this);requestQueue.add(request);

    }

    
}