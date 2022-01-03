package com.example.locarv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;

public class NoInternetActivity extends AppCompatActivity {

    private Button retryBtn;
    private ImageView imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);

        imageView2 = findViewById(R.id.imageView2);
        Glide.with(this).load(R.drawable.no_connection_bro).into(imageView2);

        retryBtn = findViewById(R.id.button3);
        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
                if (activeNetwork != null) {
                    Intent intent = new Intent(NoInternetActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                    Animatoo.animateSlideRight(NoInternetActivity.this);
                }
            }
        });
    }
}