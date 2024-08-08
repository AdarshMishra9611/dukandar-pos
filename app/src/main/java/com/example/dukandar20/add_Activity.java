package com.example.dukandar20;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.widget.FrameLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.dukandar20.Fragments.Fragment_Add_Item;
import com.example.dukandar20.Fragments.Fragment_checkOut;
import com.example.dukandar20.Fragments.Fragment_instantAdd;
import com.example.dukandar20.Fragments.add_category;
import com.example.dukandar20.models.cart_model;

import java.util.ArrayList;

public class add_Activity extends AppCompatActivity {

    Toolbar toolbar;
    FrameLayout cart_frameLayout;
    DrawerLayout drawerLayout;
    String cat_id;
    private GestureDetector gestureDetector;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //Find view
        toolbar = findViewById(R.id.cart_tolbar);
        cart_frameLayout = findViewById(R.id.cartFramlayout);
        drawerLayout = findViewById(R.id.cart_drawerlayout);

        //toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //*************-----   toggel --------*********//
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.OpenDrawer,R.string.ClodeDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(ContextCompat.getColor(this, R.color.emeraldGreen));



        //fragment

//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.replace(R.id.cartFramlayout,new Fragment_instantAdd());
//        ft.commit();


        // Retrieve data from Intent
        Intent intent = getIntent();
        String value = intent.getStringExtra("FRAGMENT_KEY");


        double total = intent.getDoubleExtra("TOTAL", 0.0);
        ArrayList<cart_model> dataset = (ArrayList<cart_model>) intent.getSerializableExtra("DATASET");

        if ("add_category".equals(value)){
            boolean isupdate = intent.getBooleanExtra("isUpdate",false);
            String categoryId = intent.getStringExtra("categoryId");
            if(isupdate){
                replace_cart_fragment(add_category.newInstance(true,Integer.parseInt(categoryId)));
            }else {
                replace_cart_fragment(new add_category());
            }




        }else if ("PRINT".equals(value)){
            replace_cart_fragment(new Fragment_checkOut(total,dataset));

        }else if("updateItem".equals(value)){

            boolean isUpdate = intent.getBooleanExtra("isUpdate",false);
            String itemId = intent.getStringExtra("itemId");
            String cat_id = intent.getStringExtra("cat_id");

            Log.v("cat_id",cat_id);
            Log.v("isUpdate", String.valueOf(isUpdate));
            Log.v("itemId", String.valueOf(itemId));

            if (isUpdate && itemId != null && cat_id != null) {
                replace_cart_fragment(Fragment_Add_Item.newInstance(true, Integer.parseInt(itemId), cat_id));
            } else {
                // Handle error or default case
            }

        } else if ("instantAdd".equals(value)) {
            replace_cart_fragment(new Fragment_instantAdd());

        } else if ("addCustomer".equals(value)) {
//            replace_cart_fragment(new FragmentAddCustomer());

        }


        // Replace the fragment
//        replace_cart_fragment();





    }
    public void replace_cart_fragment(Fragment fragment){

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.cartFramlayout,fragment)
                        .commit();
    }




}