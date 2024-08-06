package com.example.dukandar20.adapter;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.example.dukandar20.Fragments.Fragment_Add_Item;
import com.example.dukandar20.Item_Activity;
import com.example.dukandar20.R;
import com.example.dukandar20.add_Activity;
import com.example.dukandar20.models.Item_model;
import com.example.dukandar20.models.cart_model;

import java.util.ArrayList;

public class Item_RecyclerViewAdapter extends RecyclerView.Adapter<Item_RecyclerViewAdapter.ViewHolder> {
   private Context mcontext ;
   private ArrayList<Item_model> dataSet;
   private boolean incDceButtonClicked = false;
    private Activity activity;
   DataBaseHelper myDB;





    public Item_RecyclerViewAdapter(Context mcontext, ArrayList<Item_model> dataSet,  Activity activity) {
        this.mcontext = mcontext;
        this.dataSet = dataSet;
        this.activity = activity;
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
        int getPosition = position;
        int itemId = item.item_id;

        int cat_id = item.cat_id;


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
        
        holder.cardlayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(mcontext)
                        .setTitle("Item Action")
                        .setIcon(R.drawable.info_vector_asset)
                        .setMessage("Are you sure you want to Change this Item?")
                        .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(activity, add_Activity.class);
                                intent.putExtra("FRAGMENT_KEY", "updateItem");
                                intent.putExtra("isUpdate",true);
                                intent.putExtra("itemId", String.valueOf(itemId));
//                                Log.v("Apple",String.valueOf(itemId));
                                intent.putExtra("cat_id",String.valueOf(cat_id));
                                activity.startActivity(intent);

                            }
                        })
                        .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteItem(itemId,getPosition);
                            }
                        })
                        .setNeutralButton("Cancel",null).show();

                return false;




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

    // insert into database
    private void  insert(cart_model item){
        myDB = new DataBaseHelper(mcontext);


        myDB.addItemsToCart(item);
    }

    private void deleteItem(int itemId,int getPosition){
        new AlertDialog.Builder(mcontext)
                .setTitle("Delete")
                .setIcon(R.drawable.ic_delete)
                .setMessage("Are you sure you want to Delete this Item?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DataBaseHelper myDB = new DataBaseHelper(mcontext);
                        myDB.deleteItem(itemId);
                        dataSet.remove(getPosition);
                        notifyItemRemoved(getPosition);
                        notifyItemRangeChanged(getPosition,dataSet.size());

                    }
                })
                .setNegativeButton("No",null)
                .show();
    }



}
