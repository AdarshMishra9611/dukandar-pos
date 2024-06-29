package com.example.dukandar20;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dukandar20.adapter.RecylerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class fragment_category extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<String> cat_name;
    ArrayList<Integer> cat_image;
    RecylerViewAdapter adapter;

    public fragment_category() {
        // Required empty public constructor
    }


    public static fragment_category newInstance(String param1, String param2) {
        fragment_category fragment = new fragment_category();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_category, container, false);



        FloatingActionButton add_cat_floting_button  = view.findViewById(R.id.add_cat_floatingActionButton);
        TextView title = view.findViewById(R.id.category_title);
        RecyclerView recyclerView = view.findViewById(R.id.category_recyclerView);


        cat_name= new ArrayList<>();
        cat_name.add("category");
        cat_name.add("category1");
        cat_name.add("category2");
        cat_name.add("category3");
        cat_name.add("category4");
        cat_name.add("category5");
        cat_name.add("category");
        cat_name.add("category1");
        cat_name.add("category2");
        cat_name.add("category3");
        cat_name.add("category4");
        cat_name.add("category5");
        cat_name.add("category");
        cat_name.add("category1");
        cat_name.add("category2");
        cat_name.add("category3");
        cat_name.add("category4");
        cat_name.add("category5");
        cat_image = new ArrayList<>();
        cat_image.add(R.drawable.a);
        cat_image.add(R.drawable.b);
        cat_image.add(R.drawable.c);
        cat_image.add(R.drawable.d);
        cat_image.add(R.drawable.e);
        cat_image.add(R.drawable.f);
        cat_image.add(R.drawable.a);
        cat_image.add(R.drawable.b);
        cat_image.add(R.drawable.c);
        cat_image.add(R.drawable.d);
        cat_image.add(R.drawable.e);
        cat_image.add(R.drawable.f);
        cat_image.add(R.drawable.a);
        cat_image.add(R.drawable.b);
        cat_image.add(R.drawable.c);
        cat_image.add(R.drawable.d);
        cat_image.add(R.drawable.e);
        cat_image.add(R.drawable.f);


        adapter = new RecylerViewAdapter(cat_name,cat_image,getActivity(),getContext());
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        add_cat_floting_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).replace_fragment(new add_category());
            }
        });









        return  view;
    }
}