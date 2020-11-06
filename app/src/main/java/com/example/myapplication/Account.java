package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class Account extends AppCompatActivity {

    FirebaseAuth fireAuth;
    FirebaseFirestore fstore;

    TextView firstName,email,phone;
    Button mHistory,contactbtn;

    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        getSupportActionBar().setTitle("My Account");

        fireAuth = FirebaseAuth.getInstance();
        fstore= FirebaseFirestore.getInstance();
        userID = fireAuth.getCurrentUser().getUid();

        firstName = findViewById(R.id.prName);
        email = findViewById(R.id.prEmail);
        phone = findViewById(R.id.prPhone);
        mHistory=findViewById(R.id.HistoryBtn);
        contactbtn=findViewById(R.id.contactBtn);

        // Get the user details from the database

        DocumentReference documentReference= fstore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                String Name1=(documentSnapshot.getString("fName"));
                String Email1=(documentSnapshot.getString("email"));
                String Phone1=(documentSnapshot.getString("phone"));

                firstName.setText("Name: " + Name1);
                email.setText("Email: " + Email1);
                phone.setText("Phone: " + Phone1);

            }
        });

        mHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),History.class));

            }
        });
        contactbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Contact.class));
            }
        });



    }


}
