package com.example.dukandar20.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dukandar20.ActivityBillPreview;
import com.example.dukandar20.R;
import com.example.dukandar20.models.salse_item_model;

import java.util.ArrayList;


public class SalseRecyclerViewAdapter extends RecyclerView.Adapter<SalseRecyclerViewAdapter.ViewHolder> {

    ArrayList<salse_item_model> dataset;
    Context context ;

    Activity activity;

    public SalseRecyclerViewAdapter( Context context,ArrayList<salse_item_model> dataset ) {
        this.dataset = dataset;
        this.context = context;

    }






    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.salse_card,parent,false);


        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        salse_item_model item = dataset.get(position);

        String date = item.billDate;


        holder.billNumber.setText("AM-"+String.valueOf(item.billNumber));
        holder.billDate.setText(date);
        holder.totalbillAmount.setText(String.valueOf("₹"+item.totalbillAmount));

        holder.cardlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityBillPreview.class);
                intent.putExtra("BILL_ID",item.billNumber);
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView billNumber,billDate,totalbillAmount;
        CardView cardlayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            billNumber=itemView.findViewById(R.id.textViewBillNumber);
            billDate = itemView.findViewById(R.id.textViewBillDate);
            totalbillAmount = itemView.findViewById(R.id.textViewTotalBillAmount);
            cardlayout = itemView.findViewById(R.id.salseCardView);






        }
    }
}
