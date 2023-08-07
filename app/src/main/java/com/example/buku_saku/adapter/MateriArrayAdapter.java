package com.example.buku_saku.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.buku_saku.POJO.Materi;
import com.example.buku_saku.R;

import java.util.ArrayList;

public class MateriArrayAdapter extends ArrayAdapter<Materi> {

    public MateriArrayAdapter(Context context, ArrayList<Materi> materiList) {
        super(context, 0, materiList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Mengambil objek Materi untuk posisi tertentu
        Materi materi = getItem(position);

        // Mengecek apakah view sudah ada atau perlu di-inflate
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_buku, parent, false);
        }

        // Mengambil elemen TextView di layout list_item_buku dan mengisi datanya
        TextView textView = convertView.findViewById(R.id.list);
        if (materi != null) {
            textView.setText(materi.getMateri());
        }

        return convertView;
    }
}

