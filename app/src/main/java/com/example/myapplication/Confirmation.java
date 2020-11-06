package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class Confirmation extends AppCompatActivity {

    FirebaseAuth fireAuth;
    FirebaseFirestore fstore;


    ImageView ImageCar;
    TextView Car,Date,Hour,Location,Price,firstName,Email;
    Button Btn1;
    String UserId;
    String carimage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        firstName = findViewById(R.id.textViewUser);
        Email=findViewById(R.id.textViewEmail);
        fireAuth = FirebaseAuth.getInstance();
        fstore= FirebaseFirestore.getInstance();
        UserId = fireAuth.getCurrentUser().getUid();

        // Get the user name and email from the database and display in taxtviews

        DocumentReference documentReference= fstore.collection("users").document(UserId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                firstName.setText(documentSnapshot.getString("fName"));
                Email.setText(documentSnapshot.getString("email"));
            }
        });

         // get the details about the booking
        Intent intent=getIntent();
        String mCar=intent.getStringExtra("car");
        String mDate=intent.getStringExtra("date");
        String mHour=intent.getStringExtra("hour");
        String mLocation=intent.getStringExtra("location");
        String mPrice=intent.getStringExtra("price");
        carimage =intent.getStringExtra("image");



        Car = findViewById(R.id.editTextCar);
        Date = findViewById(R.id.editTextDate);
        Hour = findViewById(R.id.editTextHours);
        Location = findViewById(R.id.editText4);
        Price = findViewById(R.id.editTextPrice);
        ImageCar=findViewById(R.id.imageViewCar);
        Btn1=findViewById(R.id.buttonClose);

        Car.setText("Car: "+ mCar);
        Date.setText("Date: "+mDate);
        Hour.setText("Hour: " +mHour);
        Location.setText("Location: " +mLocation);
        Price.setText("Price: " + mPrice + "kr");


        Btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });







    }
}
