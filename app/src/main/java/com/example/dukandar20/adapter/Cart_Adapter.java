package com.example.dukandar20.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dukandar20.DataBaseHelper;
import com.example.dukandar20.R;
import com.example.dukandar20.models.cart_model;

import java.util.ArrayList;

public class Cart_Adapter extends RecyclerView.Adapter<Cart_Adapter.ViewHolder> {

    Context context;
    ArrayList<cart_model> dataset;
    OnCartItemChangeListener onCartItemChangeListener;
    DataBaseHelper myDB;

    public Cart_Adapter(Context context, ArrayList<cart_model> dataset, OnCartItemChangeListener onCartItemChangeListener) {
        this.context = context;
        this.dataset = dataset;
        this.onCartItemChangeListener = onCartItemChangeListener;
        this.myDB = new DataBaseHelper(context); // Initialize DB here
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_cardlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        cart_model item = dataset.get(position);

        holder.productName.setText(item.productName);
        holder.productPrice.setText("₹" + (item.productPrice * item.productQuantity));
        holder.productQuantity.setText(String.valueOf(item.productQuantity));

        // Remove any previous listeners from EditText
        holder.productQuantity.setOnFocusChangeListener(null);

        // Set OnFocusChangeListener to update quantity
        holder.productQuantity.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) { // When EditText loses focus
                if (!holder.productQuantity.getText().toString().isEmpty()) {
                    try {
                        int newQuantity = Integer.parseInt(holder.productQuantity.getText().toString());
                        if (item.productQuantity != newQuantity) {
                            item.productQuantity = newQuantity;
                            updateQuantity(holder, item);
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // Increase button
        holder.buttonIncrease.setOnClickListener(view -> {
            item.productQuantity++;
            updateQuantity(holder, item);
        });

        // Decrease button
        holder.buttonDecrease.setOnClickListener(view -> {
            if (item.productQuantity > 0) {
                item.productQuantity--;
                if (item.productQuantity == 0) {
                    myDB.removeItemFromCart(item.productName);
                    dataset.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, dataset.size());
                } else {
                    updateQuantity(holder, item);
                }
            }
        });

        // Delete button
        holder.deleteButton.setOnClickListener(view -> {
            myDB.removeItemFromCart(item.productName);
            dataset.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, dataset.size());
            if (onCartItemChangeListener != null) {
                onCartItemChangeListener.onCartItemChange();
            }
        });
    }

    private void updateQuantity(ViewHolder holder, cart_model item) {
        myDB.updateItemQuantity(item); // Update the database
        holder.productQuantity.setText(String.valueOf(item.productQuantity));
        holder.productPrice.setText("₹" + (item.productPrice * item.productQuantity));
        if (onCartItemChangeListener != null) {
            onCartItemChangeListener.onCartItemChange();
        }
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice;
        EditText productQuantity;
        Button buttonIncrease, buttonDecrease;
        ImageButton deleteButton;
        LinearLayout cartcardLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.textViewProductName);
            productPrice = itemView.findViewById(R.id.textViewProductPrice);
            productQuantity = itemView.findViewById(R.id.textViewQuantity);
            buttonIncrease = itemView.findViewById(R.id.buttonIncrease);
            buttonDecrease = itemView.findViewById(R.id.buttonDecrease);
            deleteButton = itemView.findViewById(R.id.buttonDelete);
            cartcardLayout = itemView.findViewById(R.id.cartcardLayout);
        }
    }

    public interface OnCartItemChangeListener {
        void onCartItemChange();
    }
}
