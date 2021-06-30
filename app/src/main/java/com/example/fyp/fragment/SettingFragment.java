package com.example.fyp.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.fyp.R;
import com.example.fyp.WelcomeActivity;


public class SettingFragment extends Fragment {
    LinearLayout logout_btn;
    LinearLayout btn_terms;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        logout_btn = view.findViewById(R.id.logout_btn);
        btn_terms = view.findViewById(R.id.btn_terms);
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WelcomeActivity.class);
                removepref();
                startActivity(intent);
            }
        });
        btn_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Junaidnawab0835/smart-brand-try-it-out-using-augmented-reality"));
                startActivity(browserIntent);
            }
        });
        return view;
    }
    void removepref()
    {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", "");
        editor.putString("password", "");
        editor.putBoolean("autologin",false);
        editor.apply();
    }
}