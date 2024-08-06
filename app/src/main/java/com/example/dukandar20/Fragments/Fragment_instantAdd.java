package com.example.dukandar20.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.dukandar20.DataBaseHelper;
import com.example.dukandar20.R;
import com.example.dukandar20.models.cart_model;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_instantAdd#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_instantAdd extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText productName,productPrice,productQty;
    Button buttonAddProduct;
    DataBaseHelper myDB;



    public Fragment_instantAdd() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_instantAdd.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_instantAdd newInstance(String param1, String param2) {
        Fragment_instantAdd fragment = new Fragment_instantAdd();
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
       View view = inflater.inflate(R.layout.fragment_instant_add, container, false);

       productName = view.findViewById(R.id.instant_productName);
       productPrice= view.findViewById(R.id.instant_productPrice);
       productQty = view.findViewById(R.id.instant_quentity);
       buttonAddProduct = view.findViewById(R.id.instant_buttonAdd);


       buttonAddProduct.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               myDB = new DataBaseHelper(getContext());
               cart_model cart_model = new cart_model(
                       productName.getText().toString().trim(),
                       Integer.parseInt(productPrice.getText().toString().trim()),
                       Integer.parseInt((productQty.getText().toString().trim()))

               );
               myDB.addItemsToCart(cart_model);
               if (getActivity() != null) {
                   getActivity().finish();
               }
           }
       });




       return  view;
    }
}