package com.example.dukandar20.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dukandar20.R;
import com.example.dukandar20.models.customer_model;

import java.util.ArrayList;

public  class CustomerRecyclerViewAdapter extends RecyclerView.Adapter<CustomerRecyclerViewAdapter.ViewHolder> {

    public CustomerRecyclerViewAdapter(Context context,ArrayList<customer_model> dataset) {
        this.context = context;
        this.dataset = dataset;

    }
    public void updateList(ArrayList<customer_model> newList) {
        dataset = newList;
        notifyDataSetChanged();
    }

    ArrayList<customer_model> dataset;
    Context context ;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_customer,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        customer_model items = dataset.get(position);
        holder.firtsLater.setText(String.valueOf(items.customerName.charAt(0)));

        holder.customerName.setText(items.customerName);
        holder.phoneNumber.setText(items.customerPhone);



    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView firtsLater,customerName,phoneNumber;
        CardView cardLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            firtsLater = itemView.findViewById(R.id.textViewFirstLater);
            customerName = itemView.findViewById(R.id.textViewCustomername);
            phoneNumber = itemView.findViewById(R.id.textViewPhoneNumber);
            cardLayout = itemView.findViewById(R.id.CustomerCardView);






        }
    }
}

