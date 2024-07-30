package com.example.dukandar20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dukandar20.adapter.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar, navbar;
    ViewPager2 mainPage;
    BottomNavigationView bottom_Nav;
    FrameLayout add_category_frameLayout;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView toolbar_title;
    ImageView cart_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find view
        toolbar = findViewById(R.id.toolbar);
        navbar = findViewById(R.id.navbar);
        add_category_frameLayout = findViewById(R.id.add_category_frameLayout);
        mainPage = findViewById(R.id.container);
        bottom_Nav = findViewById(R.id.bottom_Nav);
        drawerLayout = findViewById(R.id.category_drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        cart_icon = findViewById(R.id.cart_icon);
        toolbar_title = findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // ActionBarDrawerToggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.OpenDrawer, R.string.ClodeDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //adapter
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        mainPage.setAdapter(adapter);

       mainPage.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
           @Override
           public void onPageSelected(int position) {
               super.onPageSelected(position);

           }
       });


        // Set OnItemSelectedListener to refresh fragments
        bottom_Nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.itSale) {
                    mainPage.setCurrentItem(0);
                    return true;
                } else if (itemId == R.id.itBlance) {
                    mainPage.setCurrentItem(1);
                    return true;
                } else if (itemId == R.id.itPOV) {
                    mainPage.setCurrentItem(2);
                    return true;
                } else if (itemId == R.id.itCategory) {
                    mainPage.setCurrentItem(3);
                    return true;
                } else if (itemId == R.id.itItem) {
                    mainPage.setCurrentItem(4);
                    return true;
                } else {
                    return false;
                }
            }
        });

        cart_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Cart_Activity.class);
                startActivity(intent);
            }
        });
    }


}
