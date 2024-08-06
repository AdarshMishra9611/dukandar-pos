package com.example.dukandar20.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.dukandar20.MainActivity;
import com.example.dukandar20.add_Activity;
import com.example.dukandar20.DataBaseHelper;
import com.example.dukandar20.Printer.BluetoothPrinter;
import com.example.dukandar20.R;
import com.example.dukandar20.adapter.Cart_Adapter;
import com.example.dukandar20.models.cart_model;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCart#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCart extends Fragment {

    public interface OnNewOrderClickListener {
        void onNewOrderClick(int pagenumber);
    }

    private OnNewOrderClickListener mListener;


    ArrayList<cart_model> dataset;
    RecyclerView recyclerView;
    public TextView total;
    Button buttonCheckout,checkoutButton;
    FloatingActionButton instantAdd;
    Cart_Adapter adapter;
    RelativeLayout layoutRecycler,layoutNewOrder;

    DataBaseHelper myDB;




    public FragmentCart() {
        // Required empty public constructor
    }


    public static FragmentCart newInstance(String param1, String param2) {
        FragmentCart fragment = new FragmentCart();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }
    @Override
    public void onResume() {
        super.onResume();
        dataset.clear();
        cartData(); // Reload data
        adapter.notifyDataSetChanged(); // Notify adapter about data change
         setVisibility();

        // Update total
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (OnNewOrderClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnNewOrderClickListener");
        }
    }


    @SuppressLint({"MissingInflatedId", "CutPasteId"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_cart, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewCartItems);
        total = view.findViewById(R.id.textViewTotalPrice);
        buttonCheckout = view.findViewById(R.id.buttonCheckout);
        instantAdd = view.findViewById(R.id.instant_floatingActionButton);
        checkoutButton = view.findViewById(R.id.buttonCheckout);
        layoutRecycler=view.findViewById(R.id.layoutRecyclearView);
        layoutNewOrder =view.findViewById(R.id.layoutNewOrder);

        //dataset
        myDB = new DataBaseHelper(getContext());
        dataset = new ArrayList<>();
        cartData();
        setVisibility();


        BluetoothPrinter printer = new BluetoothPrinter(getContext(),getActivity());
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ((add_Activity) getActivity()).replace_cart_fragment(new Fragment_checkOut(totalAmount(dataset),dataset));
                Intent intent= new Intent(getContext(),add_Activity.class);
                intent.putExtra("FRAGMENT_KEY","PRINT");
                intent.putExtra("TOTAL",totalAmount(dataset));
                intent.putExtra("DATASET", (Serializable) dataset);
                startActivity(intent);
            }
        });

        layoutNewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    Log.d("FragmentCart", "New Order Clicked");
                    mListener.onNewOrderClick(3);
                } else {
                    Log.d("FragmentCart", "Listener is null");
                }
            }
        });

        instantAdd.setOnClickListener(view1 -> {
            Intent intent = new Intent(getContext(), add_Activity.class);
            intent.putExtra("FRAGMENT_KEY","instantAdd");
            startActivity(intent);
        });

        total.setText(String.valueOf( "Total ₹"+totalAmount(dataset) ));





        adapter = new Cart_Adapter(getContext(),dataset,this::onCartItemChange);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);




        return  view;
    }

    public void onCartItemChange() {
        total.setText(String.valueOf("Total ₹"+totalAmount(dataset)));

    }
    private void cartData() {
        dataset.clear();
        Cursor cursor = myDB.readCartData();

        if (cursor.getCount() == 0) {
            Toast.makeText(this.getContext(), "Cart is empty", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                String productName = cursor.getString(1);
                int productPrice = cursor.getInt(2);
                int productQuantity = cursor.getInt(3);

                dataset.add(new cart_model(productName, productPrice, productQuantity));
            }
        }
        cursor.close(); // Close cursor after use
    }

    private double totalAmount(ArrayList<cart_model> dataset){
        double totalAmount =0;
        for (int i = 0; i<dataset.size();i++){
            cart_model item = dataset.get(i);;
            double total =(item.productPrice*item.productQuantity);
            totalAmount+=total;
        }

        return totalAmount;
    }
    private void  setVisibility(){
        if(dataset.size() ==0){
            layoutRecycler.setVisibility(View.GONE);
            layoutNewOrder.setVisibility(View.VISIBLE);
        }else {
            layoutRecycler.setVisibility(View.VISIBLE);
            layoutNewOrder.setVisibility(View.GONE);
        }

    }







}