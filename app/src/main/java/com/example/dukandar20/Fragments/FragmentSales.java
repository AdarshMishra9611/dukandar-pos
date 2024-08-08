package com.example.dukandar20.Fragments;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dukandar20.DataBaseHelper;
import com.example.dukandar20.R;
import com.example.dukandar20.adapter.SalseRecyclerViewAdapter;
import com.example.dukandar20.models.salse_item_model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSales#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSales extends Fragment {

    RecyclerView recyclerView;
    ArrayList<salse_item_model> dataset;

    SalseRecyclerViewAdapter adapter;
    DataBaseHelper myDB;
    public FragmentSales() {
        // Required empty public constructor
    }


    public static FragmentSales newInstance(String param1, String param2) {
        FragmentSales fragment = new FragmentSales();
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
        populateDataSet();
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view   = inflater.inflate(R.layout.fragment__sales, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewSalse);

        dataset = new ArrayList<>();
        myDB = new DataBaseHelper(getContext());
        populateDataSet();





        Log.v("Dataset",String.valueOf(dataset.size()));

        adapter = new SalseRecyclerViewAdapter(getActivity() ,dataset);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);




        return view;
    }

    @SuppressLint("SimpleDateFormat")
    private void populateDataSet() {
        dataset.clear();
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        String billDate = dateFormat.format(currentDate);

        Cursor cursor = null;
        try {
            cursor = myDB.getBillsByDate(billDate);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int billId = cursor.getInt(0);
                    int customerId = cursor.getInt(1);
                    String billDateFromCursor = cursor.getString(2);
                    double totalBillAmount = cursor.getDouble(3);
                    String status = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.BILL_STATUS));
                    String paymentMethod = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.BILL_PAYMENT_METHOD));

                    salse_item_model item = new salse_item_model(String.valueOf(billId), totalBillAmount, billDateFromCursor);
                    dataset.add(item);

                } while (cursor.moveToNext());
            } else {
                Log.d("FragmentSales", "No bills found for the current date.");
//                Toast.makeText(getContext(),"No bills found for the current date",Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("FragmentSales", "Error retrieving bills for the current date", e);
//            Toast.makeText(getContext(),"Error retrieving bills for the current date "+e,Toast.LENGTH_SHORT).show();

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

}