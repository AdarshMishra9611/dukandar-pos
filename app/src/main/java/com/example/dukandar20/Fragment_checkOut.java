package com.example.dukandar20;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dukandar20.adapter.PaymentModeAdapter;

public class Fragment_checkOut extends Fragment {

    public Fragment_checkOut() {
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

        RecyclerView recyclerView = view.findViewById(R.id.checkout);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        PaymentModeAdapter adapter = new PaymentModeAdapter();
        recyclerView.setAdapter(adapter);


        return view;
    }
}
