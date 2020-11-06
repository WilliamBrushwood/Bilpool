package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText nEmail,nPassword;
    Button nLoginBtn;
    TextView nCreateBtn;
    FirebaseAuth fireAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Easy Car");

        nEmail = findViewById(R.id.email);
        nPassword = findViewById(R.id.password);
        fireAuth = FirebaseAuth.getInstance();
        nLoginBtn = findViewById(R.id.LoginBtn);
        nCreateBtn = findViewById(R.id.createText);

        //Check if the user has filled in the details correctly

        nLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=nEmail.getText().toString().trim();
                String password=nPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    nEmail.setError("Email is Required");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    nPassword.setError("Password is Required");
                    return;

                }
                if (password.length() < 6) {
                    nPassword.setError("Password must be mote than 6 characters");
                    return;
                }

                //authorize user
                fireAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                     if (task.isSuccessful()){

                         Toast.makeText(Login.this,"Login in successful.", Toast.LENGTH_SHORT).show();
                         startActivity(new Intent(getApplicationContext(),MainActivity.class));

                     }else{

                         Toast.makeText(Login.this,"Error!" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                     }

                    }
                });
            }
        });
        nCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });


    }
}
