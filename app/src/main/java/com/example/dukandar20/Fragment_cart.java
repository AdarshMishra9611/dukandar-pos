package com.example.dukandar20;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dukandar20.adapter.Cart_Adapter;
import com.example.dukandar20.adapter.cart_model;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_cart#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_cart extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ArrayList<cart_model> dataset;
    RecyclerView recyclerView;
    TextView total;
    Button buttonCheckout;
    FloatingActionButton instantAdd;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_cart() {
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
    public static Fragment_cart newInstance(String param1, String param2) {
        Fragment_cart fragment = new Fragment_cart();
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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_cart, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewCartItems);
        total = view.findViewById(R.id.textViewTotalPrice);
        buttonCheckout = view.findViewById(R.id.buttonCheckout);
        instantAdd = view.findViewById(R.id.instant_floatingActionButton);

        instantAdd.setOnClickListener(view1 -> {
            ((Cart_Activity) getActivity()).replace_cart_fragment(new Fragment_instantAdd());
        });


        //dataset
        dataset = new ArrayList<>();
        dataset.add(new cart_model("product1",1,45));
        dataset.add(new cart_model("product2",1,45));
        dataset.add(new cart_model("product3",1,45));
        dataset.add(new cart_model("product4",1,45));
        dataset.add(new cart_model("product5",1,45));
        dataset.add(new cart_model("product6",1,45));
        dataset.add(new cart_model("product7",1,45));
        dataset.add(new cart_model("product8",1,45));

        Cart_Adapter adapter = new Cart_Adapter(getContext(),dataset);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);




        return  view;
    }
}