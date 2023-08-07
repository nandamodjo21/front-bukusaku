package com.example.buku_saku.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buku_saku.R;
import com.example.buku_saku.home.HomesActivity;
import com.example.buku_saku.login.Login;
import com.example.buku_saku.session.SharedPref;

public class Menus extends AppCompatActivity {

    private ImageView back,logout;

    private TextView bk,lg;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menus);
        back = findViewById(R.id.backhome);
        logout = findViewById(R.id.log);
        bk = findViewById(R.id.homee);
        lg = findViewById(R.id.lgo);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomesActivity.class));
            }
        });
        bk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomesActivity.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SharedPref.getInstance(getApplicationContext()).logout()) {
                    startActivity(new Intent(getApplicationContext(), Login.class));
                    Toast.makeText(getApplicationContext(), "berhasil logout", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "gagal logout", Toast.LENGTH_SHORT).show();
                }
            }
        });

        lg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SharedPref.getInstance(getApplicationContext()).logout()) {
                    startActivity(new Intent(getApplicationContext(), Login.class));
                    Toast.makeText(getApplicationContext(), "berhasil logout", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "gagal logout", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}