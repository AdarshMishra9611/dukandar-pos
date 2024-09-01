package com.example.dukandar20.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.dukandar20.ActivityBillPreview;
import com.example.dukandar20.ActivityFinal;
import com.example.dukandar20.DataBaseHelper;
import com.example.dukandar20.Printer.BluetoothPrinter;
import com.example.dukandar20.R;
import com.example.dukandar20.adapter.PaymentModeAdapter;
import com.example.dukandar20.models.BillItem;
import com.example.dukandar20.models.cart_model;
import com.google.android.material.snackbar.Snackbar;

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
    private long billID;
    Button printButton,buttonPreview;
    RadioGroup radioGroup;
    String paymentMethod = null;




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

//         recyclerView = view.findViewById(R.id.checkout);
         cuustomerName = view.findViewById(R.id.editTextCustomerName);
         phoneNumber = view.findViewById(R.id.editTextPhoneNumber);
         printButton = view.findViewById(R.id.buttobPrint);
         buttonPreview = view.findViewById(R.id.buttonPreview);
         radioGroup = view.findViewById(R.id.radioGroupPayment);



         radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                 // Handle selection change

                 if (checkedId == R.id.isCash) {
                     paymentMethod = "Cash";
                 } else if (checkedId == R.id.isUPI) {
                     paymentMethod = "UPI";
                 } else if (checkedId == R.id.isCredit) {
                     paymentMethod = "Credit";
                 }


             }
         });



         printButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if ("Credit".equals(paymentMethod)) {
                     String name = cuustomerName.getText().toString().trim();
                     String phone = phoneNumber.getText().toString().trim();

                     if (name.isEmpty() || phone.isEmpty()) {
                         Snackbar.make(view, "Customer name and phone number are required for Credit payments.", Snackbar.LENGTH_LONG).show();
                         highlightErrorFields();
                         return;
                     }
                 }

                 Intent intent = new Intent(getContext(), ActivityFinal.class);
                 getActivity().startActivity(intent);
                 if (getActivity() != null) {
                     getActivity().finish();
                 }

                 saveBill(1);
                 myDB.clearCart();
             }


         });
         buttonPreview.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 saveBill(0);
                 Intent intent = new Intent(getContext(), ActivityBillPreview.class);
                 intent.putExtra("BILL_ID",String.valueOf(billID));
                 getContext().startActivity(intent);
             }
         });














        return view;
    }

    private void saveBill(int flag){


        myDB = new DataBaseHelper(getContext());

        Date currentDate = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        String billDate =  dateFormat.format(currentDate);
        String name = cuustomerName.getText().toString().trim();
        String phone = phoneNumber.getText().toString().trim();





        double total= totalAmount ;
        String status=null;
        if ("Credit".equals(paymentMethod)){
             status = "Unpaid";
        }else {
             status = "Paid";
        }


        long customerId =0;
        if(name.isEmpty()||phone.isEmpty()){
            billID = myDB.insertBill(null,billDate,total,status,paymentMethod);
        }else {
            customerId =myDB.getOrAddCustomerByPhone(name,phone);
            billID = myDB.insertBill((int) customerId,billDate,total,status,paymentMethod);
        }


        myDB.insertBillItems(billID,dataset);

        BluetoothPrinter printer= new BluetoothPrinter(getContext(),getActivity());
        printer.doPrint(dataset);







    }
    private void highlightErrorFields() {
        String name = cuustomerName.getText().toString().trim();
        String phone = phoneNumber.getText().toString().trim();

        if (name.isEmpty()) {
            cuustomerName.setError("Customer name is required");
        } else {
            cuustomerName.setError(null);
        }

        if (phone.isEmpty()) {
            phoneNumber.setError("Phone number is required");
        } else {
            phoneNumber.setError(null);
        }
    }
}
