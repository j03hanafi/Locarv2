package com.example.locarv2;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.locarv2.Fragments.HomeFragment;
import com.example.locarv2.Fragments.ListMobilFragment;
import com.example.locarv2.Services.Constants;
import com.example.locarv2.Services.LocationService;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.locarv2.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;
    private SessionManager sessionManager;
    private DrawerLayout drawer;
    private long back_pressed;
    private ImageView menuBtn;

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Check internet connection
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if (activeNetwork == null) {
            Intent intent = new Intent(HomeActivity.this, NoInternetActivity.class);
            startActivity(intent);
            finish();
            Animatoo.animateSlideLeft(this);
        }

        menuBtn = findViewById(R.id.imageView4);
        sessionManager = new SessionManager(this);

        // Check login
        if (!sessionManager.isLogin()) {
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            Animatoo.animateSlideRight(this);
            finish();
        }

        binding.appBarHome.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.nav_home) {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.nav_host_fragment_content_home, new HomeFragment()).commit();

                    drawer.close();
                }
                if (item.getItemId() == R.id.nav_setting) {
                    Intent intent = new Intent(HomeActivity.this, EditUserProfileActivity.class);
                    startActivity(intent);
                    Animatoo.animateSlideLeft(HomeActivity.this);

                    drawer.close();
                }
                if (item.getItemId() == R.id.nav_switch) {
                    Toast.makeText(HomeActivity.this, "Switch to Admin", Toast.LENGTH_SHORT).show();
                    drawer.close();

                }
                if (item.getItemId() == R.id.nav_order) {
                    Toast.makeText(HomeActivity.this, "Order", Toast.LENGTH_SHORT).show();
                    drawer.close();
                }
                if (item.getItemId() == R.id.nav_logout) {
                    sessionManager.editor.clear();
                    sessionManager.editor.commit();

                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    Animatoo.animateSlideRight(HomeActivity.this);

                    drawer.close();
                }

                return true;
            }
        });

        // Menu button
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.open();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_switch:
                Toast.makeText(this, "Switch to Admin", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_settings:
                Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_logout:
                sessionManager.editor.clear();
                sessionManager.editor.commit();

                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                Animatoo.animateSlideRight(HomeActivity.this);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(this, "Tekan lagi untuk keluar", Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }

    private boolean isLocationServiceRunning() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if(activityManager != null) {
            for(ActivityManager.RunningServiceInfo serviceInfo: activityManager.getRunningServices(Integer.MAX_VALUE)) {
                if(LocationService.class.getName().equals(serviceInfo.service.getClassName())) {
                    if(serviceInfo.foreground) {
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(HomeActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION_PERMISSION);
        } else {
            startLocationService();
        }
    }

    private void startLocationService() {

        if(!isLocationServiceRunning()) {
            Intent intent = new Intent(getApplicationContext(), LocationService.class);
            intent.setAction(Constants.ACTION_START_LOCATION_SERVICE);
            startService(intent);
        }
    }

    private void stopLocationService() {

        if(isLocationServiceRunning()) {
            Intent intent = new Intent(getApplicationContext(), LocationService.class);
            intent.setAction(Constants.ACTION_STOP_LOCATION_SERVICE);
            stopService(intent);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        stopLocationService();
    }
}