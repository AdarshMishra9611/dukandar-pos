package com.example.dukandar20;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dukandar20.Fragments.Fragment_item;
import com.example.dukandar20.models.cart_model;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class Item_Activity extends AppCompatActivity {

    Toolbar toolbar;
    TextView textView;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView cart_icon;
    ArrayList<cart_model> addTOcart;
//    RecyclerView recyclerView;
//    ArrayList<Item_model> dataset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
//        getCategoryID();
        //Find view
        toolbar = findViewById(R.id.toolbar_item);
        textView = findViewById(R.id.item_title);
        drawerLayout = findViewById(R.id.item_drawarerLayout);
        cart_icon = findViewById(R.id.cart_icon);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

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


        replace_Fragment(new Fragment_item(),0);









    }




    // to replace fragment when add item button clicked
    public void replace_Fragment(Fragment fragment,int flag){

        FragmentManager fm = getSupportFragmentManager();
        getCategoryID(fragment);
        FragmentTransaction ft = fm.beginTransaction();
        if (flag == 0){
            ft.add( R.id.item_container,fragment);


        }else {
            ft.replace(R.id.item_container,fragment);
            ft.addToBackStack(null);

        }
        ft.commit();

    }

    private  void getCategoryID( Fragment fragment){
         String cat_id = getIntent().getStringExtra("cat_id");

         Bundle bundle = new Bundle();
         bundle.putString("cat_id",cat_id);
         fragment.setArguments(bundle);

    }




}