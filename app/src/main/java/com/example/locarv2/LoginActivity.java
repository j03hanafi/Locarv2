package com.example.locarv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class LoginActivity extends AppCompatActivity {

    private Button phoneLoginButton, googleLoginButton;
    Animation phoneLoginAnimate, googleLoginAnimate;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phoneLoginButton = (Button) findViewById(R.id.button);
        googleLoginButton = (Button) findViewById(R.id.button2);
        sessionManager = new SessionManager(this);

        // Check login
        if (sessionManager.isLogin()) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

        phoneLoginAnimate = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.right_animation);
        googleLoginAnimate = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.right_animation);

        phoneLoginButton.setAnimation(phoneLoginAnimate);
        googleLoginButton.setAnimation(googleLoginAnimate);

    }

    public void phoneLoginClick(View view) {
        Intent intent = new Intent(LoginActivity.this, PhoneLoginActivity.class);
        startActivity(intent);
    }
}