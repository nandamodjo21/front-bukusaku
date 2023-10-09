package com.example.buku_saku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.buku_saku.home.HomesActivity;
import com.example.buku_saku.home.Pdf;
import com.example.buku_saku.login.Login;
import com.example.buku_saku.session.SharedPref;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isConnectedToInternet()) {
                    if (SharedPref.getInstance(getApplicationContext()).isLoggedIn()) {
                        startActivity(new Intent(getApplicationContext(), HomesActivity.class));
                    } else {
                        startActivity(new Intent(getApplicationContext(), Login.class));
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Tidak ada koneksi internet.", Toast.LENGTH_SHORT).show();
                }
            }
        },3000);
    }

    private boolean isConnectedToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }
}