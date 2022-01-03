package com.example.locarv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.chaos.view.PinView;
import com.example.locarv2.responses.APIClient;
import com.example.locarv2.responses.APIInterface;
import com.example.locarv2.responses.UserCredentials;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.Credentials;
import com.google.android.gms.auth.api.credentials.CredentialsApi;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.installations.FirebaseInstallations;
import com.hbb20.CountryCodePicker;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneLoginActivity extends AppCompatActivity {

    CountryCodePicker cpp;
    private EditText phoneEditText;
    private PinView firstPinView;
    private ConstraintLayout phoneLayout;
    private String selected_country_code = "+62";
    private static final int CREDENTIAL_PICKER_REQUEST = 120 ;
    private ProgressBar progressBar;

    // Firebase phone auth
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResentToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private FirebaseAuth mAuth;

    APIInterface apiInterface;
    private String device_token;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);

        // FCM token
        FirebaseInstallations.getInstance().getId().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    return;
                }
                device_token = task.getResult();
            }
        });

        sessionManager = new SessionManager(this);

        cpp  = findViewById(R.id.ccp);
        phoneEditText  = findViewById(R.id.editTextTextPersonName);
        firstPinView  = findViewById(R.id.firstPinView);
        phoneLayout = findViewById(R.id.phoneLayout);
        progressBar = findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();
        apiInterface = APIClient.getAPIClient().create(APIInterface.class);

        // Country Code Picker
        cpp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                selected_country_code = cpp.getSelectedCountryCodeWithPlus();
            }
        });


        // Phone input listener
        phoneEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.toString().length() == 11) {
                    sendOTP();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // PIN input listener
        firstPinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.toString().length() == 6) {
                    progressBar.setVisibility(View.VISIBLE);

                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, firstPinView.getText().toString().trim());
                    signInWithAuthCredential(credential);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // Phone selector API
        HintRequest hintRequest = new HintRequest.Builder()
                .setPhoneNumberIdentifierSupported(true)
                .build();


        PendingIntent intent = Credentials.getClient(PhoneLoginActivity.this).getHintPickerIntent(hintRequest);
        try
        {
            startIntentSenderForResult(intent.getIntentSender(), CREDENTIAL_PICKER_REQUEST, null, 0, 0, 0,new Bundle());
        }
        catch (IntentSender.SendIntentException e)
        {
            e.printStackTrace();
        }

        // Firebase phone OTP callbacks
        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                String code = phoneAuthCredential.getSmsCode();
                if (code != null) {
                    firstPinView.setText(code);

                    signInWithAuthCredential(phoneAuthCredential);
                }
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

                Toast.makeText(PhoneLoginActivity.this, "Verifikasi gagal. Sesuatu bermasalah.", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                phoneLayout.setVisibility(View.VISIBLE);
                firstPinView.setVisibility(View.GONE);
            }

            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                super.onCodeSent(verificationId, token);

                mVerificationId = verificationId;
                mResentToken = token;

                Toast.makeText(PhoneLoginActivity.this, "Kode OTP dikirim.", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                phoneLayout.setVisibility(View.GONE);
                firstPinView.setVisibility(View.VISIBLE);
            }
        };

    }

    private void sendOTP() {

        progressBar.setVisibility(View.VISIBLE);
        String phoneNumber = selected_country_code + phoneEditText.getText().toString();

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setPhoneNumber(phoneNumber)
                .setActivity(PhoneLoginActivity.this)
                .setCallbacks(callbacks)
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREDENTIAL_PICKER_REQUEST && resultCode == RESULT_OK)
        {
            // Obtain the phone number from the result
            Credential credentials = data.getParcelableExtra(Credential.EXTRA_KEY);

            phoneEditText.setText(credentials.getId().substring(3));


        }
        else if (requestCode == CREDENTIAL_PICKER_REQUEST && resultCode == CredentialsApi.ACTIVITY_RESULT_NO_HINTS_AVAILABLE)
        {
            // *** No phone numbers available ***
            Toast.makeText(PhoneLoginActivity.this, "No phone numbers found", Toast.LENGTH_LONG).show();
        }
    }

    private void signInWithAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            Call<UserCredentials> call = apiInterface.user_login(selected_country_code + phoneEditText.getText().toString(), device_token);
                            call.enqueue(new Callback<UserCredentials>() {
                                @Override
                                public void onResponse(Call<UserCredentials> call, Response<UserCredentials> response) {
                                    if (response.isSuccessful()) {

                                        String sanctum_token = response.body().getToken();
                                        Intent intent;
                                        switch (response.code()) {

                                            case 201:
                                                sessionManager.createSession(device_token, "user", phoneEditText.getText().toString(), selected_country_code, sanctum_token);
                                                Toast.makeText(PhoneLoginActivity.this, "Login berhasil", Toast.LENGTH_SHORT).show();
                                                intent = new Intent(PhoneLoginActivity.this, HomeActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                                finish();
                                                break;

                                            default:
                                                Toast.makeText(PhoneLoginActivity.this, "Login gagal", Toast.LENGTH_SHORT).show();
                                                intent = new Intent(PhoneLoginActivity.this, LoginActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                                finish();
                                                Animatoo.animateSlideUp(PhoneLoginActivity.this);
                                                break;
                                        }
                                    } else {
                                        Toast.makeText(PhoneLoginActivity.this, "Login gagal", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(PhoneLoginActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                        Animatoo.animateSlideUp(PhoneLoginActivity.this);
                                    }
                                }

                                @Override
                                public void onFailure(Call<UserCredentials> call, Throwable t) {

                                    Toast.makeText(PhoneLoginActivity.this, "Login gagal", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(PhoneLoginActivity.this, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                    Animatoo.animateSlideUp(PhoneLoginActivity.this);
                                }
                            });

                        } else {

                            Toast.makeText(PhoneLoginActivity.this, "Login gagal", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(PhoneLoginActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                            Animatoo.animateSlideUp(PhoneLoginActivity.this);
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(PhoneLoginActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
        Animatoo.animateSlideLeft(PhoneLoginActivity.this);
    }
}