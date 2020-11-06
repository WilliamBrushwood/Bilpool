package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.app.DatePickerDialog;
import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.type.Date;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class Booking extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG ="Booking";
    private AdapterView<?> adapterView;
    private View view;
    private int position;
    private long id;

    FirebaseAuth fireAuth;
    FirebaseFirestore fstore;
    String userID;





    Order order;
    TextView total,hours_amount;
    Button mButtonAdd,mButtonConfirm;

    private TextView mDisplayData;
    private DatePickerDialog.OnDateSetListener mDataSetListner;

    String car;
    String location;
    String date1;
    String sum1;
    String hours1;
    String imagecar;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        getSupportActionBar().setTitle("New Booking");


        fireAuth = FirebaseAuth.getInstance();
        fstore= FirebaseFirestore.getInstance();
        userID = fireAuth.getCurrentUser().getUid();

        mDisplayData=(TextView) findViewById(R.id.date);

        // create the calender view
        mDisplayData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal=Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(Booking.this,android.R.style.Theme_DeviceDefault_Light,mDataSetListner,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.show();

            }
        });

        // display the date in a textview

        mDataSetListner = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                Log.d(TAG,"onDataSet: date" + month + "/" + dayOfMonth + "/" + year );
                date1 = month + "/" + dayOfMonth + "/" + year;
                mDisplayData.setText(date1); // here is my date
            }
        };

        // create the spinner view with locations

        Spinner spinner = findViewById(R.id.spinner);
         ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.location,android.R.layout.simple_spinner_item);
         adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         spinner.setAdapter(adapter);
         spinner.setOnItemSelectedListener(this);


         order=new Order();
        total= findViewById(R.id.totalPrice);
        hours_amount=findViewById(R.id.hours);
        mButtonAdd=findViewById(R.id.getpriceBtn);
        mButtonConfirm=findViewById(R.id.confirmBtn);

        // get the current user ID

        userID=fireAuth.getCurrentUser().getUid();


    }
     // Get the location selected by the user

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text=parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
        location=text;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // check which radio button is pressed

    public void radioClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.rd1:
                if (checked)
                    order.setCarType_Price(50);
                car="Fiat  or similar";
                imagecar="Fiat";
                    break;
            case R.id.rd2:
                if (checked)
                    order.setCarType_Price(80);
                car="Mercedes e220  or similar";
                imagecar="Mercedes";
                    break;
            case R.id.rd3:
                if (checked)
                    order.setCarType_Price(120);
                car="Bmw X5  or similar";
                imagecar="Bmw";
                    break;


        }


        // calculate the price for a car and how many hours

        mButtonAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int num1= Integer.parseInt( hours_amount.getText().toString());
                order.setHoursPrice(num1);
                int sum = order.hoursPrice * order.carType_Price;
                sum1 = String.valueOf(sum);
                total.setText("Total: " + sum1 + "Kr");

                hours1  = hours_amount.getText().toString();


            }




        });

        // When the button is pressed save the data in the database
        mButtonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userID=fireAuth.getCurrentUser().getUid();

                Map<String,Object> user =new HashMap<>();
                user.put("car", car);
                user.put("location",location);
                user.put("pris",sum1);
                user.put("hours", hours1);
                user.put("date", date1);



                // send data to the confirmation page


                fstore.collection("users").document(userID).collection("suBooking").add(user);

                Intent intent=new Intent(Booking.this,Confirmation.class);
                intent.putExtra("car",car);
                intent.putExtra("date",date1);
                intent.putExtra("hour",hours1);
                intent.putExtra("location",location);
                intent.putExtra("price",sum1);
                intent.putExtra("image", imagecar);
                startActivity(intent);







            }
        });





    }

}
