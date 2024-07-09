package com.example.dukandar20.adapter;


import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dukandar20.R;

import java.util.ArrayList;

public class Item_RecyclerViewAdapter extends RecyclerView.Adapter<Item_RecyclerViewAdapter.ViewHolder> {
   private Context mcontext ;
   private ArrayList<Item_model> dataSet;

    public Item_RecyclerViewAdapter(Context mcontext, ArrayList<Item_model> dataSet) {
        this.mcontext = mcontext;
        this.dataSet = dataSet;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       View v = LayoutInflater.from(mcontext).inflate(R.layout.item__row,parent,false);
       ViewHolder  view = new ViewHolder(v);
       return view;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      Item_model item = dataSet.get(position);


      holder.item_imageView.setImageBitmap(item.item_image);
      holder.item_textView.setText(item.item_name);
      holder.item_price_textView.setText(String.valueOf(item.item_price));
      holder.productQuantity.setText(String.valueOf(item.productQuantity));

      //increase button

        holder.buttonIncrease.setOnClickListener(view -> {
              int currentQuantity = item.productQuantity;

              currentQuantity++;
              item.productQuantity = currentQuantity;

              holder.productQuantity.setText(String.valueOf(currentQuantity));
              notifyItemChanged(position);

        });
        holder.buttonDecrease.setOnClickListener(view -> {
            int currentQuantity =  item.productQuantity;

            if (currentQuantity >=0)
                currentQuantity=0;
            item.productQuantity = currentQuantity;

            holder.productQuantity.setText(String.valueOf(currentQuantity));
            notifyItemChanged(position);

        });

        holder.productQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                if (!charSequence.toString().isEmpty()) {
                    int quantity = Integer.parseInt(charSequence.toString());
                    item.productQuantity = quantity;
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }



    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {

        ImageView item_imageView;
        TextView item_textView ,item_price_textView;
        Button buttonIncrease,buttonDecrease;
        EditText productQuantity;
        LinearLayoutCompat cardlayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            item_imageView = itemView.findViewById(R.id.item_image);
            item_textView = itemView.findViewById(R.id.item_name);
            item_price_textView = itemView.findViewById(R.id.item_price);
            cardlayout = itemView.findViewById(R.id.itemcard);
            buttonIncrease = itemView.findViewById(R.id.itembButtonIncrese);
            buttonDecrease = itemView.findViewById(R.id.itemButtonDecrease);
            productQuantity = itemView.findViewById(R.id.item_textViewQuantity);
        }
    }
}
