package com.example.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Splash extends AppCompatActivity {
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
// Using handler with postDelayed called runnable run method
            @Override
            public void run() {
                getpref();
            }

        }, 2*1000); // wait for 5 seconds
    }
    private void getpref() {
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREF", MODE_PRIVATE);
        Boolean autologin = sharedPreferences.getBoolean("autologin", false);
        if (autologin) {
            String password = sharedPreferences.getString("password"," ");
            String email = sharedPreferences.getString("email"," ");
            signIn(password,email);
        }
        else
        {
            Intent i = new Intent(Splash.this, WelcomeActivity.class);
            startActivity(i);
            finish();
        }
    }

    private void signIn(String password, String email) {
        auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            //login success
                            FirebaseUser user = auth.getCurrentUser();
                            //Check if user is verified
                            assert user != null;
                            //  progressBar.setVisibility(View.GONE);
                            startActivity(new Intent(Splash.this, MainActivity.class));
                            finish();
                        }
                        else {
                            //  progressBar.setVisibility(View.GONE);
                            Toast.makeText(Splash.this, "Error: " +
                                    task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }
}