package com.example.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    Button login_btn;
    TextView register_btn;
    EditText email_edtxt;
    EditText password_edtxt;
    CheckBox savelogin;
    TextView forget_pass_btn;
   // ProgressBar progressBar;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login_btn = findViewById(R.id.login_btn);
        register_btn = findViewById(R.id.register_btn);
        email_edtxt = findViewById(R.id.edtxt_email);
        password_edtxt = findViewById(R.id.edtxt_pass);
        savelogin = findViewById(R.id.saveLoginCheckBox);
        forget_pass_btn = findViewById(R.id.forget_pass_btn);

        forget_pass_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                forgetpassDialog();
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(valid())
                {
                    signIn(email_edtxt.getText().toString(),password_edtxt.getText().toString());
                }

            }
        });
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });
    }


    private void signIn(String email, String password) {

//        progressBar.setVisibility(View.VISIBLE);
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
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            if(savelogin.isChecked())
                            {
                                createPref(email,password);
                            }
                            finish();
                        }
                        else {
                          //  progressBar.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this, "Error: " +
                                    task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }

    private void createPref(String email, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREF", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.putString("password", password);
        editor.putBoolean("autologin",true);
        editor.apply();
    }


    Boolean valid()
    {
        if(email_edtxt.getText().toString().isEmpty())
        {
            email_edtxt.setError("Empty");
            return false;
        }
        else if(password_edtxt.getText().toString().isEmpty())
        {
            password_edtxt.setError("Empty");
            return false;
        }
        return true;

    }
    private void forgetpassDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Forget Password");
        LayoutInflater inflater = LayoutInflater.from(this);
        View custom_Layout = inflater.inflate(R.layout.dialog_forgetpass,null);
        // int variable and view
        final EditText editText_email = custom_Layout.findViewById(R.id.edtxt_email);
        dialog.setView(custom_Layout);

        // set buttons

        dialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!editText_email.getText().toString().isEmpty()) {
                    auth = FirebaseAuth.getInstance();
                    if (auth != null) {
                        Toast.makeText(LoginActivity.this, "Recovery Email has been  sent to " + editText_email.getText().toString(), Toast.LENGTH_SHORT).show();
                        auth.sendPasswordResetEmail(editText_email.getText().toString());
                    } else {
                        Log.d(" error ", " bad entry ");
                        Toast.makeText(LoginActivity.this, "Error Occured", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }
                else
                {
                    editText_email.setError("Empty");
                }

            }
        });

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
}