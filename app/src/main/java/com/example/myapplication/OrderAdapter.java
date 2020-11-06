package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class OrderAdapter extends FirestoreRecyclerAdapter<OrderBookings, OrderAdapter.OrderHolder> {
    public OrderAdapter(@NonNull FirestoreRecyclerOptions<OrderBookings> options) {
        super(options);
    }

    // asign the textviews with data in the viewholder
    @Override
    protected void onBindViewHolder(@NonNull OrderHolder holder, int position, @NonNull OrderBookings model) {
     holder.car.setText(model.getCar());
        holder.date.setText(model.getDate());
        holder.hours.setText(model.getHours());
        holder.location.setText(model.getLocation());
        holder.pris.setText(model.getPris());

    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row1, parent, false);
        return new OrderHolder(v);
    }

    class OrderHolder extends RecyclerView.ViewHolder{
TextView car,date,hours,location,pris;
    public OrderHolder(@NonNull View itemView) {
        super(itemView);
        car=itemView.findViewById(R.id.car);
        date=itemView.findViewById(R.id.date);
        hours=itemView.findViewById(R.id.hours);
        location=itemView.findViewById(R.id.location);
        pris=itemView.findViewById(R.id.pris);

    }
}
}
