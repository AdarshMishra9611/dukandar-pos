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
import com.example.dukandar20.models.due_customer_model;

import java.util.ArrayList;

public class DueCustomerRecyclerViewAdapter extends RecyclerView.Adapter<DueCustomerRecyclerViewAdapter.ViewHolder> {

    public DueCustomerRecyclerViewAdapter( Context context,ArrayList<due_customer_model> dataset) {

        this.context = context;
        this.dataset = dataset;
    }
    public void updateList(ArrayList<due_customer_model> newList) {
        dataset = newList;
        notifyDataSetChanged();
    }

    ArrayList<due_customer_model> dataset;
    Context context ;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_due_customer,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        due_customer_model items = dataset.get(position);
        holder.firstlater.setText(String.valueOf(items.customerName.charAt(0)));

        holder.customerName.setText(items.customerName);
        holder.phoneNumber.setText(items.customerPhone);
        holder.balanceAmount.setText(String.valueOf(items.balanceAmount));
    }



    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView balanceAmount,customerName,phoneNumber,firstlater;
        CardView cardLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            balanceAmount = itemView.findViewById(R.id.textViewBalanceAmount);
            customerName = itemView.findViewById(R.id.textViewCustomername);
            phoneNumber = itemView.findViewById(R.id.textViewPhoneNumber);
            cardLayout = itemView.findViewById(R.id.CustomerCardView);
            firstlater =itemView.findViewById(R.id.textViewFirstLater);






        }
    }
}
