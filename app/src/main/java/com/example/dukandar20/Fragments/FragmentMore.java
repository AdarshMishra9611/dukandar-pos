package com.example.dukandar20.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import com.example.dukandar20.ActivityCustomer;
import com.example.dukandar20.R;
import com.example.dukandar20.adapter.ListAdapterMore;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentMore#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMore extends Fragment {

   GridView listView;

    public FragmentMore() {
        // Required empty public constructor


    }

    public static FragmentMore newInstance(String param1, String param2) {
        FragmentMore fragment = new FragmentMore();
        Bundle args = new Bundle();

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
        View view =   inflater.inflate(R.layout.fragment_more, container, false);
        listView = view.findViewById(R.id.ListviewMore);


        ArrayList<String> itemList = new ArrayList<>();
        itemList.add("Customer");
        itemList.add("Stock");
        itemList.add("Expance");
        itemList.add("Receipts");
        itemList.add("Coming Soon");
        itemList.add("Coming Soon");
        itemList.add("Coming Soon");
        itemList.add("Coming Soon");

        ListAdapterMore adapter = new ListAdapterMore(getContext(),R.layout.card_more_listview,itemList);


        listView.setAdapter(adapter);

//        cardViewAddcustomer = view.findViewById(R.id.cardViewAddCustomer);
//
//        cardViewAddcustomer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getContext(), ActivityCustomer.class);
//                intent.putExtra("FRAGMENT_KEY","addCustomer");
//                getContext().startActivity(intent);
//            }
//        });


        return view;
    }
}