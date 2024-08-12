package com.example.dukandar20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dukandar20.Fragments.FragmentCart;
import com.example.dukandar20.adapter.MainViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements FragmentCart.OnNewOrderClickListener {

    Toolbar toolbar, navbar;
    ViewPager2 mainPage;
    BottomNavigationView bottom_Nav;
    FrameLayout add_category_frameLayout;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView toolbar_title;
    ImageView search_icon;
    SearchView searchView;

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
        search_icon = findViewById(R.id.search_icon);
        toolbar_title = findViewById(R.id.toolbar_title);
        searchView = findViewById(R.id.duesearchView);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // ActionBarDrawerToggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.OpenDrawer, R.string.ClodeDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(ContextCompat.getColor(this, R.color.emeraldGreen));

        //adapter
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(this);
        mainPage.setAdapter(adapter);

       mainPage.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
           @Override
           public void onPageSelected(int position) {
               super.onPageSelected(position);
               switch (position){
                   case 0: toolbar_title.setVisibility(View.VISIBLE);
                           toolbar_title.setText("SALES");
                           bottom_Nav.setSelectedItemId(R.id.itSale);
                           searchView.setVisibility(View.GONE);
                          break;
                   case 1: toolbar_title.setVisibility(View.VISIBLE);
                           toolbar_title.setText("BALANCE");
                           search_icon.setVisibility(View.VISIBLE);
                           bottom_Nav.setSelectedItemId(R.id.itBlance);
                           searchView.setVisibility(View.GONE);
                       break;

                   case 2: toolbar_title.setVisibility(View.VISIBLE);
                           toolbar_title.setText("DUKANDAR");
                           bottom_Nav.setSelectedItemId(R.id.itPOV);
                           searchView.setVisibility(View.GONE);

                       break;
                   case 3: toolbar_title.setVisibility(View.VISIBLE);
                           toolbar_title.setText("CATEGORY");
                           bottom_Nav.setSelectedItemId(R.id.itCategory);
                           searchView.setVisibility(View.GONE);
                       break;

                   case 4: toolbar_title.setVisibility(View.VISIBLE);
                           toolbar_title.setText("DUKANDAR");
                           bottom_Nav.setSelectedItemId(R.id.itMore);
                           searchView.setVisibility(View.GONE);
                       break;


               }

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
//
                    return true;
                } else if (itemId == R.id.itMore) {
                    mainPage.setCurrentItem(4);
                    return true;
                } else {
                    return false;
                }
            }
        });
        bottom_Nav.setSelectedItemId(R.id.itPOV);

//        cart_icon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, add_Activity.class);
//                startActivity(intent);
//            }
//        });
    }
    @Override
    public void onBackPressed() {
        if (mainPage.getCurrentItem() != 2) {
            mainPage.setCurrentItem(2);
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Exit App")
                    .setIcon(R.drawable.exit_vector_asset)
                    .setMessage("Are you sure you want to exit?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity.super.onBackPressed();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    }


    //change vipagercurrent element

    public void changeMainPage(int pageNumber){
        mainPage.setCurrentItem(pageNumber);

    }


    @Override
    public void onNewOrderClick(int pagenumber) {
        mainPage.setCurrentItem(3);
    }
}
