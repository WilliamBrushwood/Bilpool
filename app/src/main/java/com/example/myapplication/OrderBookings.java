package com.example.myapplication;

// class that contains the object of a booking

public class OrderBookings {
    private String car;
    private   String date;
    private  String hours;
   private String location;
    private   String pris;


   public OrderBookings(){
       //not needed
   }

    public OrderBookings(String car, String date, String hours, String location, String pris) {
        this.car = car;
        this.date = date;
        this.hours = hours;
        this.location = location;
        this.pris = pris;
    }
    public String getCar() {
        return "Car: " +car;
    }



    public String getDate() {
        return "Date: " + date;
    }



    public String getHours() {
        return "Hours: " +hours;
    }



    public String getLocation() {
        return "Location: " +location;
    }



    public String getPris() {
        return "Price: " +pris+"kr";
    }



}
