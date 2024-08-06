package com.example.dukandar20.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dukandar20.DataBaseHelper;
import com.example.dukandar20.R;
import com.example.dukandar20.adapter.CategoryRecylerViewAdapter;
import com.example.dukandar20.add_Activity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class FragmentCategory extends Fragment {




    // TODO: Rename and change types of parameters

    ArrayList<String> cat_name;
    ArrayList<String> cat_id;
    ArrayList<Bitmap> cat_image;
    CategoryRecylerViewAdapter adapter;

    DataBaseHelper myDB;

    public FragmentCategory() {
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
        categoryData();
        adapter.notifyDataSetChanged();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_category, container, false);



        FloatingActionButton add_cat_floting_button  = view.findViewById(R.id.add_cat_floatingActionButton);

        RecyclerView recyclerView = view.findViewById(R.id.category_recyclerView);




        myDB = new DataBaseHelper(getContext());

        cat_name= new ArrayList<>();
        cat_image = new ArrayList<>();
        cat_id = new ArrayList<>();

        categoryData();

        int spanCount = calculateNumberOfColumns();
        adapter = new CategoryRecylerViewAdapter( cat_id,  cat_name,cat_image,getActivity(),getContext());
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),spanCount);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        add_cat_floting_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), add_Activity.class);
                intent.putExtra("FRAGMENT_KEY", "add_category");
                startActivity(intent);

            }
        });

        return  view;
    }

    void  categoryData(){
        cat_id.clear();
        cat_name.clear();
        cat_image.clear();
        Cursor cursor = myDB.readCategoryData();
        if (cursor.getCount() == 0){
            Toast.makeText(this.getContext(),"No Category Avilable",Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){
                cat_id.add(cursor.getString(0));
                cat_name.add(cursor.getString(1));

                //geting image byte value from blob and converting it into bitmap
                byte[] imgByte = cursor.getBlob(2);
                cat_image.add(convertByteArrayToBitmap(imgByte));

            }


        }
    }


    private  Bitmap convertByteArrayToBitmap(byte[] bytes){
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }
    private int calculateNumberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int itemWidth = getResources().getDimensionPixelSize(R.dimen.grid_item_width); // Define your item width in dimens.xml
        return screenWidth / itemWidth;
    }

}