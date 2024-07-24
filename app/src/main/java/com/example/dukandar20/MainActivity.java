package com.example.dukandar20;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    FrameLayout add_category_frameLayout;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    ImageView cart_icon;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find view
        toolbar = findViewById(R.id.toolbar);
        add_category_frameLayout = findViewById(R.id.add_category_frameLayout);
        drawerLayout = findViewById(R.id.category_drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        cart_icon = findViewById(R.id.cart_icon);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //*************-----   toggel --------*********//

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.OpenDrawer,R.string.ClodeDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        FragmentManager fm =  getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container,new fragment_category());
        ft.commit();


        cart_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Cart_Activity.class);
                startActivity(intent);
            }
        });



    }




    public void replace_fragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container,new add_category())
                .addToBackStack(null)
                .commit();

    }








}