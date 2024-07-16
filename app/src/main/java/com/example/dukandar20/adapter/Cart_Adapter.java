package com.example.dukandar20.adapter;

import android.content.Context;
import android.telephony.PhoneNumberFormattingTextWatcher;
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

import com.example.dukandar20.R;

import java.util.ArrayList;

public class Cart_Adapter extends RecyclerView.Adapter<Cart_Adapter.ViewHolder> {

    Context context;
    ArrayList<cart_model> dataset;
    OnCartItemChangeListener onCartItemChangeListener;
    DataBaseHelper myDB;

    public Cart_Adapter(Context context, ArrayList<cart_model> dataset,OnCartItemChangeListener onCartItemChangeListener){
        this.context = context;
        this.dataset = dataset;
        this.onCartItemChangeListener = onCartItemChangeListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view   =    LayoutInflater.from(context).inflate(R.layout.cart_cardlayout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        cart_model item = dataset.get(position);

        holder.productName.setText(item.productName);
        holder.productPrice.setText(new String(String.valueOf( "₹"+item.productPrice * item.productQuantity)));
        holder.productQuantity.setText(new String(String.valueOf(item.productQuantity)));

        //Increase button

        holder.buttonIncrease.setOnClickListener(view -> {
            int curreentQuantity = item.productQuantity;
            curreentQuantity++;
            item.productQuantity = curreentQuantity;
            holder.productQuantity.setText(new String(String.valueOf(item.productQuantity)));

            insert( new cart_model(item.productName,item.productPrice,item.productQuantity));


            if (onCartItemChangeListener != null) {
                onCartItemChangeListener.onCartItemChange();
            }


        });
        // decrease button
        holder.buttonDecrease.setOnClickListener(view -> {
            int currentQuantity = item.productQuantity;
            if (currentQuantity >0)
                currentQuantity--;



            item.productQuantity = currentQuantity;
            holder.productQuantity.setText(new String(String.valueOf(item.productQuantity)));
            insert( new cart_model(item.productName,item.productPrice,item.productQuantity));
            if(currentQuantity == 0){
                myDB.removeItemFromCart(item.productName);
                dataset.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, dataset.size());
            }

            if (onCartItemChangeListener != null) {
                onCartItemChangeListener.onCartItemChange();
            }
        });


        holder.deleteButton.setOnClickListener(view ->{
            myDB = new DataBaseHelper(context);
            myDB.removeItemFromCart(item.productName);
            dataset.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, dataset.size());


            if (onCartItemChangeListener != null) {
                onCartItemChangeListener.onCartItemChange();
            }

        });

        // if edit text is changed manully


        holder.productQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int after) {
                if (!charSequence.toString().isEmpty()) {
                    int quantity = Integer.parseInt(charSequence.toString());
                    item.productQuantity = quantity;
                    if (onCartItemChangeListener != null) {
                        onCartItemChangeListener.onCartItemChange();
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });







    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView productName,productPrice;
        EditText productQuantity;
        Button buttonIncrease,buttonDecrease;
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
    private void  insert(cart_model item){
        myDB = new DataBaseHelper(context);


        myDB.addItemsToCart(item);
    }




}
