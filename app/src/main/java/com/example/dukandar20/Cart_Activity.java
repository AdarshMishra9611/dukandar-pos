package com.example.dukandar20;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.contentcapture.DataRemovalRequest;
import android.widget.FrameLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;

public class Cart_Activity extends AppCompatActivity {

    Toolbar toolbar;
    FrameLayout cart_frameLayout;
    DrawerLayout drawerLayout;





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

        //*************-----   toggel --------*********//
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.OpenDrawer,R.string.ClodeDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();



        //fragment

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.cartFramlayout,new Fragment_cart());
        ft.commit();



    }
    public void replace_cart_fragment(Fragment fragment){
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.cartFramlayout,fragment)
                        .addToBackStack(null)
                        .commit();
    }
}