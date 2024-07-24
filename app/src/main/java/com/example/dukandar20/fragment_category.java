package com.example.dukandar20;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dukandar20.adapter.DataBaseHelper;
import com.example.dukandar20.adapter.RecylerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class fragment_category extends Fragment {




    // TODO: Rename and change types of parameters

    ArrayList<String> cat_name;
    ArrayList<String> cat_id;
    ArrayList<Bitmap> cat_image;
    RecylerViewAdapter adapter;
    DataBaseHelper myDB;

    public fragment_category() {
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
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_category, container, false);



        FloatingActionButton add_cat_floting_button  = view.findViewById(R.id.add_cat_floatingActionButton);
        TextView title = view.findViewById(R.id.category_title);
        RecyclerView recyclerView = view.findViewById(R.id.category_recyclerView);

        myDB = new DataBaseHelper(getContext());

        cat_name= new ArrayList<>();
        cat_image = new ArrayList<>();
        cat_id = new ArrayList<>();

        categoryData();


        adapter = new RecylerViewAdapter( cat_id,  cat_name,cat_image,getActivity(),getContext());
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

    void  categoryData(){
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
}