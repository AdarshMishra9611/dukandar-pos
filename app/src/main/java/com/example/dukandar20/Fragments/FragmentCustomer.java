package com.example.dukandar20.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dukandar20.R;


public class FragmentCustomer extends Fragment {




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








        return view;
    }
}