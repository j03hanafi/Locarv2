package com.example.locarv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.locarv2.responses.APIClient;
import com.example.locarv2.responses.APIInterface;
import com.example.locarv2.Models.UserCredentials;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmailLoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private ConstraintLayout emailLayout;
    private Button signInBtn;
    private String selected_country_code = "+62";
    private ProgressBar progressBar;

    APIInterface apiInterface;
    private String device_token;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login);

        sessionManager = new SessionManager(this);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        emailLayout = findViewById(R.id.emailLayout);
        signInBtn = findViewById(R.id.button_signUp);
        progressBar = findViewById(R.id.progressBar);
        apiInterface = APIClient.getAPIClient().create(APIInterface.class);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginWithEmail();
            }
        });
    }

    private void loginWithEmail() {
        progressBar.setVisibility(View.VISIBLE);

//        Toast.makeText(this, email.getText().toString(), Toast.LENGTH_SHORT).show();
        Call<UserCredentials> call = apiInterface.user_login_email(email.getText().toString(), password.getText().toString());
        call.enqueue(new Callback<UserCredentials>() {
            @Override
            public void onResponse(Call<UserCredentials> call, Response<UserCredentials> response) {
                if (response.isSuccessful()) {

                    Intent intent;
                    switch (response.code()) {

                        case 201:
                            String user_id = response.body().getUser().getId().toString();
                            String user_type = response.body().getUser().getGroupUser().toString();
                            String user_cred = response.body().getUser().getEmail();
                            String sanctum_token = response.body().getToken();

                            sessionManager.createSession(user_id, user_type, user_cred, selected_country_code, sanctum_token);
                            Toast.makeText(EmailLoginActivity.this, "Login berhasil", Toast.LENGTH_SHORT).show();
                            intent = new Intent(EmailLoginActivity.this, HomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                            break;

                        default:
                            Toast.makeText(EmailLoginActivity.this, "Login gagal", Toast.LENGTH_SHORT).show();
                            intent = new Intent(EmailLoginActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                            Animatoo.animateSlideUp(EmailLoginActivity.this);
                            break;
                    }

                } else {
                    Toast.makeText(EmailLoginActivity.this, "Login gagal", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EmailLoginActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    Animatoo.animateSlideUp(EmailLoginActivity.this);
                }
            }

            @Override
            public void onFailure(Call<UserCredentials> call, Throwable t) {
                Toast.makeText(EmailLoginActivity.this, "Login gagal", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EmailLoginActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                Animatoo.animateSlideUp(EmailLoginActivity.this);

            }
        });
    }

    public void forgetPass(View view) {
        Toast.makeText(this, "Lupa Password", Toast.LENGTH_SHORT).show();
    }

    public void register(View view) {
        Intent intent = new Intent(EmailLoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(EmailLoginActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
        Animatoo.animateSlideLeft(EmailLoginActivity.this);
    }
}