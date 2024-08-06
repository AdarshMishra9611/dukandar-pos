package com.example.dukandar20.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dukandar20.DataBaseHelper;
import com.example.dukandar20.Printer.BluetoothPrinter;
import com.example.dukandar20.R;
import com.example.dukandar20.adapter.PaymentModeAdapter;
import com.example.dukandar20.models.BillItem;
import com.example.dukandar20.models.cart_model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Fragment_checkOut extends Fragment {


    private double totalAmount;
    private ArrayList<cart_model> dataset;
//    private ArrayList<BillItem> billItems;
    private DataBaseHelper myDB;
    private RecyclerView recyclerView;
    private EditText cuustomerName,phoneNumber;
    Button printButton;

    public Fragment_checkOut( double totalAmount,  ArrayList<cart_model> dataset) {

        this.totalAmount = totalAmount;
        this.dataset = dataset;
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_check_out, container, false);

         recyclerView = view.findViewById(R.id.checkout);
         cuustomerName = view.findViewById(R.id.editTextCustomerName);
         phoneNumber = view.findViewById(R.id.editTextPhoneNumber);
         printButton = view.findViewById(R.id.buttobPrint);


         printButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 saveBill();
             }
         });








        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        PaymentModeAdapter adapter = new PaymentModeAdapter();
        recyclerView.setAdapter(adapter);


        return view;
    }

    private void saveBill(){


        myDB = new DataBaseHelper(getContext());

        Date currentDate = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        String billDate =  dateFormat.format(currentDate);

        int customerId =0;


        double total= totalAmount ;
        String status = null;
        String paymentMethod = null;


        long billID = myDB.insertBill(customerId,billDate,total,status,paymentMethod);

        myDB.insertBillItems(billID,dataset);
        BluetoothPrinter printer= new BluetoothPrinter(getContext(),getActivity());
        printer.doPrint(dataset);
//        myDB.clearCart();





    }
}
