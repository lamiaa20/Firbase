package com.alqsa.firbase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private EditText edt_email, edt_pass;
    private Button btn_login;
    private TextView tv_sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        initViews();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        tv_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void login() {
        String email = edt_email.getText().toString();
        String password = edt_pass.getText().toString();
        if (email.isEmpty()) {
            Toast.makeText(this, "Email required", Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty()) {
            Toast.makeText(this, "password required", Toast.LENGTH_SHORT).show();

        } else {

            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "something error", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }

    private void initViews() {
        edt_email = findViewById(R.id.edt_email);
        edt_pass = findViewById(R.id.edt_pass);
        btn_login = findViewById(R.id.btn_login);
        tv_sign_up = findViewById(R.id.tv_sign_up);
    }
}
