package com.example.dukandar20.Fragments;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dukandar20.DataBaseHelper;
import com.example.dukandar20.Fragments.Fragment_Add_Item;
import com.example.dukandar20.Item_Activity;
import com.example.dukandar20.R;
import com.example.dukandar20.adapter.Item_RecyclerViewAdapter;
import com.example.dukandar20.models.Item_model;
import com.example.dukandar20.models.cart_model;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;



public class Fragment_item extends Fragment {







    ArrayList<Item_model> dataset;
    DataBaseHelper myDB;
    RecyclerView recyclerView;
    Item_RecyclerViewAdapter adapter;
    String cat_id  ;
    private  ArrayList<cart_model> addTOCart;
















    public Fragment_item() {
        // Required empty public constructor
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
        getItemData();
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        cat_id  = getArguments().getString("cat_id");
        // Inflate the layout for this fragment
        View view =    inflater.inflate(R.layout.fragment_item, container, false);

        //find view
//        TextView title = view.findViewById(R.id.item_title);
        recyclerView = view.findViewById(R.id.item_recyclerView);
        FloatingActionButton ft_button = view.findViewById(R.id.add_item_floatingActionButton);

        ft_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ((Item_Activity) getActivity()).replace_Fragment(new Fragment_Add_Item(),1);


            }
        });


        //dataset
        myDB = new DataBaseHelper(getContext());
        dataset = new ArrayList<>();

        getItemData();



        int spanCount = calculateNumberOfColumns();
        adapter = new Item_RecyclerViewAdapter(getContext(),dataset,getActivity());
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),spanCount);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);









        return view;
    }

    private  void   getItemData(){
        dataset.clear();
        Cursor cursor = myDB.readItemData(Integer.parseInt(cat_id));
        if (cursor.getCount() == 0){
            Toast.makeText(this.getContext(),"No Item in Category Avilable",Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){
                int itemId = cursor.getInt(0);

                int cat_id = cursor.getInt(3);
                byte[] iImage = cursor.getBlob(4);
                String iName = cursor.getString(1);
                int iPrice = cursor.getInt(2);
                dataset.add(new Item_model(itemId,cat_id,  convertByteArrayToBitmap(iImage),iName,iPrice,0));
            }
        }


    }
    private  Bitmap convertByteArrayToBitmap(byte[] iImage){
        return BitmapFactory.decodeByteArray(iImage,0,iImage.length);
    }

    private int calculateNumberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int itemWidth = getResources().getDimensionPixelSize(R.dimen.grid_item_width); // Define your item width in dimens.xml
        return screenWidth / itemWidth;
    }






}