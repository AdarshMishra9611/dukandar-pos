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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dukandar20.ActivityCustomer;
import com.example.dukandar20.DataBaseHelper;
import com.example.dukandar20.R;
import com.example.dukandar20.adapter.CustomerRecyclerViewAdapter;
import com.example.dukandar20.adapter.DueCustomerRecyclerViewAdapter;
import com.example.dukandar20.models.customer_model;
import com.example.dukandar20.models.due_customer_model;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class FragmentDueCustomer extends Fragment {



    private RecyclerView recyclerView;
    private FloatingActionButton addCustomer;
    private ArrayList<due_customer_model> dataSet;
    private DataBaseHelper myDB;
    private ImageView search_icon;
    private SearchView searchView;
    private TextView toolbar_title;
    private DueCustomerRecyclerViewAdapter adapter;
    
    public FragmentDueCustomer() {
        // Required empty public constructor
    }

    
    public static FragmentDueCustomer newInstance(String param1, String param2) {
        FragmentDueCustomer fragment = new FragmentDueCustomer();
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
        View view = inflater.inflate(R.layout.fragment_due_customer, container, false);

        recyclerView = view.findViewById(R.id.reyclerViewDCustomer);
        addCustomer = view.findViewById(R.id.floatingActionButtonAddDCustomer);
        search_icon =  getActivity().findViewById(R.id.search_icon);
        searchView = getActivity().findViewById(R.id.duesearchView);
        toolbar_title =getActivity().findViewById(R.id.toolbar_title);


        addCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityCustomer) getActivity()).replaceFragment(new FragmentAddCustomer(),1);
            }
        });

        search_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setVisibility(View.VISIBLE);
                toolbar_title.setVisibility(View.GONE);
                search_icon.setVisibility(View.GONE);

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterCustomer(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterCustomer(newText);
                return true ;
            }
        });

        myDB = new DataBaseHelper(getContext());
        dataSet = new ArrayList<>();
        populateCustomer();


        adapter = new DueCustomerRecyclerViewAdapter(getActivity() ,dataSet);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        
        
        
        
        
        return view;
    }

    private void populateCustomer() {
        dataSet.clear();


        List<due_customer_model> customers = myDB.getCustomersWithBalance();


        dataSet.addAll(customers);


    }
    private void filterCustomer(String query){
        ArrayList<due_customer_model>filteredList = new ArrayList<>();
        for(due_customer_model item :dataSet){
            if(item.customerName.toLowerCase().contains(query.toLowerCase())){
                filteredList.add(item);
            }
        }
        adapter.updateList(filteredList);


    }



}