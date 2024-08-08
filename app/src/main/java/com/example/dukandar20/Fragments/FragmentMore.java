package com.example.dukandar20.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dukandar20.ActivityCustomer;
import com.example.dukandar20.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentMore#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMore extends Fragment {

   CardView cardViewAddcustomer;

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

        cardViewAddcustomer = view.findViewById(R.id.cardViewAddCustomer);

        cardViewAddcustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ActivityCustomer.class);
                intent.putExtra("FRAGMENT_KEY","addCustomer");
                getContext().startActivity(intent);
            }
        });


        return view;
    }
}