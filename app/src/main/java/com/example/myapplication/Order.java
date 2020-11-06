package com.example.myapplication;
// order class that contains the car price and amount of hours

public class Order {

    int carType_Price;
    int hoursPrice;

    public Order() {
    }

    public double getCarType_Price() {
        return carType_Price;
    }

    public void setCarType_Price(int carType_Price) {
        this.carType_Price = carType_Price;
    }

    public double getHoursPrice() {
        return hoursPrice;
    }

    public void setHoursPrice(int hoursPrice) {
        this.hoursPrice = hoursPrice;
    }
}
