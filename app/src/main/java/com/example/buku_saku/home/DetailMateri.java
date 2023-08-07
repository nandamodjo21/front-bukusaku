package com.example.buku_saku.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.buku_saku.R;

public class DetailMateri extends AppCompatActivity {
    private TextView detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_materi);

        detail = findViewById(R.id.textDetail);

        Bundle extras = getIntent().getExtras();

        if (extras != null){
            String materi = extras.getString("MATERI");
            String fileMateri = extras.getString("FILE_MATERI");

            String detailData = "Materi: " + materi + "\nFile Materi: " + fileMateri;
            detail.setText(detailData);
        }
    }
}