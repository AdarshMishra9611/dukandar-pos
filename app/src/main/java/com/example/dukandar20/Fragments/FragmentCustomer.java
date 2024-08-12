package com.example.dukandar20.Fragments;

import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dukandar20.ActivityCustomer;
import com.example.dukandar20.DataBaseHelper;
import com.example.dukandar20.R;
import com.example.dukandar20.adapter.CustomerRecyclerViewAdapter;

import com.example.dukandar20.models.customer_model;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class FragmentCustomer extends Fragment {


    private RecyclerView recyclerView;
    private FloatingActionButton addCustomer;
    private ArrayList<customer_model> dataSet;
    private DataBaseHelper myDB;
    private Button toolbarSerch,toolbarSave;
    private TextView toolbar_customer_title;
    private SearchView serchView;

    private CustomerRecyclerViewAdapter adapter;


    public FragmentCustomer() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentCustomer newInstance(String param1, String param2) {
        FragmentCustomer fragment = new FragmentCustomer();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_customer, container, false);

        recyclerView = view.findViewById(R.id.reyclerViewCustomer);
        addCustomer = view.findViewById(R.id.floatingActionButtonAddCustomer);
        toolbarSerch = getActivity().findViewById(R.id.customerSearchButton);
        toolbarSave = getActivity().findViewById(R.id.saveButton);
        toolbar_customer_title = getActivity().findViewById(R.id.toolbar_customer_title);
        serchView = getActivity().findViewById(R.id.customersearchView);



        addCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityCustomer) getActivity()).replaceFragment(new FragmentAddCustomer(),1);
                toolbarSerch.setVisibility(View.GONE);
                toolbarSave.setVisibility(View.VISIBLE);
                toolbar_customer_title.setText("ADD CUSTOMER");
            }
        });
        toolbarSerch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serchView.setVisibility(View.VISIBLE);
                toolbarSerch.setVisibility(View.GONE);
                toolbarSave.setVisibility(View.GONE);
                toolbar_customer_title.setVisibility(View.GONE);
            }
        });

        serchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterCustomer(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterCustomer(newText);
                return true;
            }
        });

        myDB = new DataBaseHelper(getContext());
        dataSet = new ArrayList<>();
        populateCustomer();


        adapter = new CustomerRecyclerViewAdapter(getActivity() ,dataSet);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);





        return view;
    }

    private void populateCustomer(){

             Cursor cursor = myDB.getAllCustomers();

             if(cursor != null && cursor.moveToFirst()){
                 do {
                     int id = cursor.getInt(0);
                     String name = cursor.getString(01);
                     String phone =cursor.getString(2);

                     customer_model items = new customer_model(id,name,phone);
                     Toast.makeText(getContext(), "name", Toast.LENGTH_SHORT).show();
                     dataSet.add(items);



                 }while (cursor.moveToNext());
              }
       }

       private void filterCustomer(String query){
        ArrayList<customer_model>filteredList = new ArrayList<>();
        for(customer_model item :dataSet){
            if(item.customerName.toLowerCase().contains(query.toLowerCase())){
                filteredList.add(item);
            }
        }
        adapter.updateList(filteredList);


       }



}