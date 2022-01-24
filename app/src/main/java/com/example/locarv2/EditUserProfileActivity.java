package com.example.locarv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.locarv2.Adaptor.ListMobilAdaptor;

public class EditUserProfileActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    String cars[], prices[];
    int images[] = {R.drawable.bmw_3series,R.drawable.bmw_x6,R.drawable.tesla_3,R.drawable.tesla_s,R.drawable.toyota_rush,R.drawable.toyota_vios,R.drawable.bmw_3series,R.drawable.bmw_x6,R.drawable.tesla_3,R.drawable.tesla_s,R.drawable.toyota_rush,R.drawable.toyota_vios};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        recyclerView = findViewById(R.id.recyclerView);

        cars = getResources().getStringArray(R.array.cars);
        prices = getResources().getStringArray(R.array.price);

        ListMobilAdaptor listMobilAdaptor = new ListMobilAdaptor(this, cars, prices, images);
        recyclerView.setAdapter(listMobilAdaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}