package com.example.dukandar20.adapter;


import android.content.Context;
import android.database.Cursor;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.example.dukandar20.DataBaseHelper;
import com.example.dukandar20.R;
import com.example.dukandar20.models.Item_model;
import com.example.dukandar20.models.cart_model;

import java.util.ArrayList;

public class Item_RecyclerViewAdapter extends RecyclerView.Adapter<Item_RecyclerViewAdapter.ViewHolder> {
   private Context mcontext ;
   private ArrayList<Item_model> dataSet;
   private boolean incDceButtonClicked = false;
   DataBaseHelper myDB;





    public Item_RecyclerViewAdapter(Context mcontext, ArrayList<Item_model> dataSet) {
        this.mcontext = mcontext;
        this.dataSet = dataSet;
        this.myDB = new DataBaseHelper(mcontext);
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
        holder.item_price_textView.setText(String.valueOf("₹"+item.item_price));

        Cursor cursor = myDB.getCartItemQuantity(item.item_name);
        if (cursor != null && cursor.moveToFirst()) {
            int cartQuantity = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelper.PRODUCT_QUANTITY));
            item.productQuantity = cartQuantity;
            cursor.close();
        } else {
            item.productQuantity = 0; // Default quantity if not in cart
        }
        holder.productQuantity.setText(String.valueOf(item.productQuantity));


        // Increase button
        holder.buttonIncrease.setOnClickListener(view -> {

            int currentQuantity = item.productQuantity;
            currentQuantity++;
            item.productQuantity = currentQuantity;
            holder.productQuantity.setText(String.valueOf(currentQuantity));




        });


        // Decrease button
        holder.buttonDecrease.setOnClickListener(view -> {
            int currentQuantity = item.productQuantity;

            if (currentQuantity > 0){
                currentQuantity--;
            }

            if(currentQuantity == 0){
                myDB.removeItemFromCart(item.item_name);
            }

            item.productQuantity = currentQuantity;
            holder.productQuantity.setText(String.valueOf(currentQuantity));



        });

        // TextWatcher for quantity input
        holder.productQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()) {
                    try {
                        int quantity = Integer.parseInt(editable.toString());
                        item.productQuantity = quantity;

                        if(quantity == 0){
                            myDB.removeItemFromCart(item.item_name);
                        }else {
                            insert( new cart_model(item.item_name, item.item_price, item.productQuantity));
                        }

                    } catch (NumberFormatException e) {
                        item.productQuantity = 0; // Set default value if parsing fails
                    }

                }

            }

        });

        // Card click listener to add item to cart
        holder.cardlayout.setOnClickListener(view -> {
            if(item.productQuantity == 0){
                holder.productQuantity.setText(String.valueOf(item.productQuantity+1));
            }

            // to insert
            insert( new cart_model(item.item_name, item.item_price, item.productQuantity));

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

    // insert into database
    private void  insert(cart_model item){
        myDB = new DataBaseHelper(mcontext);


        myDB.addItemsToCart(item);
    }



}
