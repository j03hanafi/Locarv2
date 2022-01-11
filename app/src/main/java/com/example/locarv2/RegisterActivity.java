package com.example.locarv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.locarv2.responses.APIClient;
import com.example.locarv2.responses.APIInterface;
import com.example.locarv2.responses.UserCredentials;
import com.hbb20.CountryCodePicker;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    CountryCodePicker cpp;
    private EditText name;
    private EditText email;
    private EditText password;
    private EditText phone;
    private Button signUpBtn;
    private String selected_country_code = "+62";
    private ProgressBar progressBar;

    APIInterface apiInterface;
    private String device_token;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sessionManager = new SessionManager(this);

        cpp  = findViewById(R.id.ccp);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        phone = findViewById(R.id.editTextTextPersonName);
        signUpBtn = findViewById(R.id.button_signUp);
        progressBar = findViewById(R.id.progressBar);
        apiInterface = APIClient.getAPIClient().create(APIInterface.class);

        // Country Code Picker
        cpp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                selected_country_code = cpp.getSelectedCountryCodeWithPlus();
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    private void register() {
        progressBar.setVisibility(View.VISIBLE);


        Call<UserCredentials> call = apiInterface.user_register(
                name.getText().toString(),
                email.getText().toString(),
                selected_country_code + phone.getText().toString(),
                password.getText().toString(),
                password.getText().toString(),
                null,
                null,
                "2"
        );
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
                            Toast.makeText(RegisterActivity.this, "Daftar berhasil", Toast.LENGTH_SHORT).show();
                            intent = new Intent(RegisterActivity.this, HomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                            break;

                        default:
                            Toast.makeText(RegisterActivity.this, "Daftar gagal", Toast.LENGTH_SHORT).show();
                            intent = new Intent(RegisterActivity.this, RegisterActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                            Animatoo.animateSlideUp(RegisterActivity.this);
                            break;
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Daftar gagal", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, RegisterActivity.class);
                    startActivity(intent);
                    finish();
                    Animatoo.animateSlideUp(RegisterActivity.this);
                }
            }

            @Override
            public void onFailure(Call<UserCredentials> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Daftar gagal", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, RegisterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                Animatoo.animateSlideUp(RegisterActivity.this);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
        Animatoo.animateSlideLeft(RegisterActivity.this);
    }

    public void login(View view) {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}