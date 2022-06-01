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
import com.google.common.collect.HashBiMap;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private EditText ed_name, ed_email, ed_pass;
    private Button btn_signup;
    private TextView tv_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        initViews();
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();

            }
        });
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });

    }


    private void initViews() {
        ed_name = findViewById(R.id.ed_name);
        ed_email = findViewById(R.id.ed_email);
        ed_pass = findViewById(R.id.ed_pass);
        btn_signup = findViewById(R.id.btn_signup);
        tv_login = findViewById(R.id.tv_login);
    }

    private void signup() {
        String fullName = ed_name.getText().toString();
        String email = ed_email.getText().toString();
        String password = ed_pass.getText().toString();
        if (fullName.isEmpty()) {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        } else if (email.isEmpty()) {
            Toast.makeText(this, "email can not be empty", Toast.LENGTH_SHORT).show();
        } else if (password.length() < 6) {
            Toast.makeText(this, "password can not be less than 6 digits", Toast.LENGTH_SHORT).show();
        } else {
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(SignupActivity.this, "created account", Toast.LENGTH_SHORT).show();
                        FirebaseUser user=firebaseAuth.getCurrentUser();
                        if(user!=null){
                            String uid =user.getUid();
                            HashMap<String,String>data=new HashMap<>();
                            data.put("uid",uid);
                            data.put("fullName",fullName);
                            data.put("email",email);
                            firebaseFirestore.collection("users")
                                    .add(data).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    if(task.isSuccessful()){
                                        System.out.println("insert user to Firestore");
                                    }else{
                                        System.out.println("insert failed");
                                    }
                                }
                            });

                        }


                        startActivity(new Intent(SignupActivity.this, MainActivity.class));

                    } else {
                        Toast.makeText(SignupActivity.this, "Error while create new account", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }

    }
}