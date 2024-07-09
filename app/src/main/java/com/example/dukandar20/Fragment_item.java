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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_item#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_item extends Fragment {



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    ArrayList<Item_model> dataset;
    DataBaseHelper myDB;
    RecyclerView recyclerView;
    String cat_id  ;











    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public Fragment_item() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_item.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_item newInstance(String param1, String param2) {
        Fragment_item fragment = new Fragment_item();
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
                dataset.add(new Item_model(convertByteArrayToBitmap(iImage),iName,iPrice));
            }
        }


    }
    private  Bitmap convertByteArrayToBitmap(byte[] iImage){
        return BitmapFactory.decodeByteArray(iImage,0,iImage.length);
    }
}