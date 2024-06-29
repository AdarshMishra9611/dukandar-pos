package com.example.dukandar20;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dukandar20.adapter.Item_RecyclerViewAdapter;
import com.example.dukandar20.adapter.Item_model;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class Item_Activity extends AppCompatActivity {

    Toolbar toolbar;
    TextView textView;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView cart_icon;
//    RecyclerView recyclerView;
//    ArrayList<Item_model> dataset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        //Find view
        toolbar = findViewById(R.id.toolbar_item);
        textView = findViewById(R.id.item_title);
        drawerLayout = findViewById(R.id.item_drawarerLayout);
        cart_icon = findViewById(R.id.cart_icon);

        setSupportActionBar(toolbar);

        //*************-----   toggel --------*********//

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.OpenDrawer,R.string.ClodeDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //cart button pressed
        cart_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Item_Activity.this, Cart_Activity.class);
                startActivity(intent);
            }
        });



        //Fragment
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.item_container,new Fragment_item());
        ft.commit();




        // Add DataSet

//        dataset = new ArrayList<>();
//        dataset.add(new Item_model(R.drawable.a,"item1",10));
//        dataset.add(new Item_model(R.drawable.b,"item2",45));
//        dataset.add(new Item_model(R.drawable.c,"item3",69));
//        dataset.add(new Item_model(R.drawable.d,"item4", 45));
//        dataset.add(new Item_model(R.drawable.e,"Item5",78));


      // Adding adapter to recyclerview

//        Item_RecyclerViewAdapter adapter = new Item_RecyclerViewAdapter(this,dataset);
//
//        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(adapter);



    }
    // to replace fragment when add item button clicked
    public void replace_Fragment(Fragment fragment){

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.item_container,fragment)
                .addToBackStack(null)
                .commit();
    }

}