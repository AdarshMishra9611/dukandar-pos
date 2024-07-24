package com.example.dukandar20;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dukandar20.adapter.DataBaseHelper;
import com.example.dukandar20.adapter.Item_RecyclerViewAdapter;
import com.example.dukandar20.adapter.Item_model;
import com.example.dukandar20.adapter.cart_model;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;



public class Fragment_item extends Fragment {







    ArrayList<Item_model> dataset;
    DataBaseHelper myDB;
    RecyclerView recyclerView;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        cat_id  = getArguments().getString("cat_id");
        // Inflate the layout for this fragment
        View view =    inflater.inflate(R.layout.fragment_item, container, false);

        //find view
        TextView title = view.findViewById(R.id.item_title);
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

//        dataset = new ArrayList<>();
//        dataset.add(new Item_model(R.drawable.a,"item1",10));
//        dataset.add(new Item_model(R.drawable.b,"item2",45));
//        dataset.add(new Item_model(R.drawable.c,"item3",69));
//        dataset.add(new Item_model(R.drawable.d,"item4", 45));
//
//        dataset.add(new Item_model(R.drawable.b,"item5",45));
//        dataset.add(new Item_model(R.drawable.c,"item6",69));
//        dataset.add(new Item_model(R.drawable.d,"item7", 45));
//        dataset.add(new Item_model(R.drawable.e,"Item8",78));


        Item_RecyclerViewAdapter adapter = new Item_RecyclerViewAdapter(getContext(),dataset);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);









        return view;
    }

    private  void   getItemData(){
        Cursor cursor = myDB.readItemData(Integer.parseInt(cat_id));
        if (cursor.getCount() == 0){
            Toast.makeText(this.getContext(),"No Item in Category Avilable",Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){
                byte[] iImage = cursor.getBlob(4);
                String iName = cursor.getString(1);
                int iPrice = cursor.getInt(2);
                dataset.add(new Item_model(convertByteArrayToBitmap(iImage),iName,iPrice,0));
            }
        }


    }
    private  Bitmap convertByteArrayToBitmap(byte[] iImage){
        return BitmapFactory.decodeByteArray(iImage,0,iImage.length);
    }

    // Method to handle back press






}