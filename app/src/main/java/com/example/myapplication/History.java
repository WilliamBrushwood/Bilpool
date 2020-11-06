package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class History extends AppCompatActivity {
    private static final String TAG = "Firelog" ;

    FirebaseAuth fireAuth;
    FirebaseFirestore fstore;
    String userID;

   private CollectionReference OrderrRef;
   private OrderAdapter adapter;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getSupportActionBar().setTitle("My Bookings");

        fireAuth = FirebaseAuth.getInstance();
        fstore= FirebaseFirestore.getInstance();
        userID = fireAuth.getCurrentUser().getUid();

        // get the specific user booking objects from the database
        //below is the reference to where they are stored in the database

       OrderrRef=fstore.collection("users").document(userID).collection("suBooking");


      setUpRecyclerView();








    }

    //Reference to a tutorial i used for this part https://www.youtube.com/watch?v=ub6mNHWGVHw&list=PLrnPJCHvNZuAXdWxOzsN5rgG2M4uJ8bH1

    // populate a recycler view with all the users bookings
    private  void setUpRecyclerView(){
        Query query = OrderrRef.orderBy("date",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<OrderBookings> options = new FirestoreRecyclerOptions.Builder<OrderBookings>()
                .setQuery(query, OrderBookings.class)
                .build();

        adapter=new OrderAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

   @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }




}

