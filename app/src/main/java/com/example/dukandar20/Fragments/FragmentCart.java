package com.example.dukandar20.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.dukandar20.Cart_Activity;
import com.example.dukandar20.DataBaseHelper;
import com.example.dukandar20.Printer.BluetoothPrinter;
import com.example.dukandar20.R;
import com.example.dukandar20.adapter.Cart_Adapter;
import com.example.dukandar20.models.cart_model;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCart#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCart extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ArrayList<cart_model> dataset;
    RecyclerView recyclerView;
    public TextView total;
    Button buttonCheckout,checkoutButton;
    FloatingActionButton instantAdd;
    Cart_Adapter adapter;

    DataBaseHelper myDB;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentCart() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_cart.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentCart newInstance(String param1, String param2) {
        FragmentCart fragment = new FragmentCart();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public void onResume() {
        super.onResume();

        cartData(); // Reload data
        adapter.notifyDataSetChanged(); // Notify adapter about data change
        total.setText(String.valueOf("Total ₹" + totalAmount(dataset))); // Update total
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

        //dataset
        myDB = new DataBaseHelper(getContext());
        dataset = new ArrayList<>();
        cartData();

        BluetoothPrinter printer = new BluetoothPrinter(getContext(),getActivity());
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Cart_Activity) getActivity()).replace_cart_fragment(new Fragment_checkOut(totalAmount(dataset),dataset));
            }
        });

        instantAdd.setOnClickListener(view1 -> {
            Intent intent = new Intent(getContext(),Cart_Activity.class);
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




}