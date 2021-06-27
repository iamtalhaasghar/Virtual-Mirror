package com.example.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.BoringLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    Button signup_btn;
    EditText email_edtxt;
    EditText pass_edtxt;
    EditText confirm_pass_edtxt;
    ProgressBar progressBar;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        signup_btn = findViewById(R.id.reg_sign_up);
        email_edtxt = findViewById(R.id.register_email_edtxt);
        pass_edtxt = findViewById(R.id.register_pass_edtxt);
        confirm_pass_edtxt = findViewById(R.id.register_confirm_pass_edtxt);
        progressBar = findViewById(R.id.progress_circular);
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }
    private void registerUser() {
        if(valid())
        {
            progressBar.setVisibility(View.VISIBLE);
            FirebaseApp.initializeApp(RegisterActivity.this);
            auth = FirebaseAuth.getInstance();
            auth.createUserWithEmailAndPassword(email_edtxt.getText().toString(),pass_edtxt.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                UploadUserData();

                            } else {

                                progressBar.setVisibility(View.GONE);
                                //Registration failed:
                                Toast.makeText(RegisterActivity.this, "Error: " +
                                        task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }

                        }
                    });
        }

    }

    private void UploadUserData() {
        //Registration success:
        final FirebaseUser user = auth.getCurrentUser();
        assert user != null;
        HashMap<String, Object> map = new HashMap<>();
        map.put("email",email_edtxt.getText().toString());
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
        reference.child(user.getUid())
                .setValue(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {

                            Toast.makeText(RegisterActivity.this, "Registered Successfully",
                                    Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

                            finish();

                        } else {
                            Toast.makeText(RegisterActivity.this, "Error: " +
                                            task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }

                        progressBar.setVisibility(View.GONE);

                    }
                });


    }
    Boolean valid()
    {
        if(email_edtxt.getText().toString().isEmpty())
        {
            email_edtxt.setError("Empty");
            return false;
        }
        else if(pass_edtxt.getText().toString().isEmpty())
        {
            pass_edtxt.setError("Empty");
            return false;
        }
        else if(confirm_pass_edtxt.getText().toString().isEmpty())
        {
            confirm_pass_edtxt.setError("Empty");
            return false;
        }
        else if(!confirm_pass_edtxt.getText().toString().equals(pass_edtxt.getText().toString()))
        {
            confirm_pass_edtxt.setError("Confirm Password not match");
            return false;
        }
        return true;

    }

}