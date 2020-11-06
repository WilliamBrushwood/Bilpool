package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText nFullName,nEmail,nPassword,nPhone;
    Button nRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fireAuth;
    FirebaseFirestore fStore;
    String userID;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setTitle("Easy Car");

        nFullName= findViewById(R.id.username);
        nEmail= findViewById(R.id.email);
        nPassword= findViewById(R.id.password);
        nPhone= findViewById(R.id.phone);
        nRegisterBtn= findViewById(R.id.RegisterBtn);
        mLoginBtn= findViewById(R.id.createText1);

        fireAuth= FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        if(fireAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        // Get the register details from the user
        //Reference to a youtube tutorial https://www.youtube.com/watch?v=tbh9YaWPKKs

        nRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email=nEmail.getText().toString().trim();
                String password=nPassword.getText().toString().trim();
                final String phone=nPhone.getText().toString();
                final String fullname= nFullName.getText().toString().trim();

                //Adding some exceptions

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

                // if everything is ok we can redgister the user here

                fireAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            Toast.makeText(Register.this,"User Created.", Toast.LENGTH_SHORT).show();
                            userID=fireAuth.getCurrentUser().getUid();
                            DocumentReference documentReference= fStore.collection("users").document(userID);
                            Map<String,Object> user =new HashMap<>();
                            user.put("fName", fullname);
                            user.put("email",email);
                            user.put("phone",phone);

                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user Profile is created for "+ userID);

                                }
                            });
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));

                        }else{
                            Toast.makeText(Register.this,"Error!" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();



                        }



                    }
                });


            }
        });
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });


    }
}
