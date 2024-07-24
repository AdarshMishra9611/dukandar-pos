package com.example.dukandar20.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dukandar20.R;

public class PaymentModeAdapter extends RecyclerView.Adapter<PaymentModeAdapter.ViewHolder> {


    private String[] paymentModes = {"Cash", "Credit", "UPI"};


    @NonNull
    @Override
    public PaymentModeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_mode_item_row,parent,false);



        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentModeAdapter.ViewHolder holder, int position) {
        holder.paymentModeTextView.setText(paymentModes[position]);

    }

    @Override
    public int getItemCount() {
        return paymentModes.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView paymentModeTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            paymentModeTextView = itemView.findViewById(R.id.paymentModeTextView);
        }
    }
}
