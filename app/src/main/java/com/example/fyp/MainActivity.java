package com.example.fyp;

// Created By Junaid Nawab

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.fyp.fragment.DownloadFragment;
import com.example.fyp.fragment.HomeFragment;
import com.example.fyp.fragment.ProfileFragment;
import com.example.fyp.fragment.CartFragment;
import com.example.fyp.fragment.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFragment(new HomeFragment());

        BottomNavigationView navigation = findViewById(R.id.bottomNavigationView);
        navigation.setOnNavigationItemSelectedListener(this);


    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.setting_menu:
                fragment = new SettingFragment();
                break;
            case R.id.home_menu:
                fragment = new HomeFragment();
                break;
            case R.id.login_menu:
                fragment = new ProfileFragment();
                break;
            case R.id.cart_menu:
                fragment = new CartFragment();
                break;
        }
        return loadFragment(fragment);
    }
    @Override
    public void onBackPressed(){
        this.finishAffinity();
    }
}