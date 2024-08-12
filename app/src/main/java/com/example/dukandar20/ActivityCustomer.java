package com.example.dukandar20;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.dukandar20.Fragments.FragmentAddCustomer;
import com.example.dukandar20.adapter.CustomerViewPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class ActivityCustomer extends AppCompatActivity {
    private Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager mainPage;
    Button toolbarSerch,toolbarSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        toolbar = findViewById(R.id.toolbar_customer);
        mainPage = findViewById(R.id.viewPagerCustomer);
        tabLayout =findViewById(R.id.customerTab);
        toolbarSerch = findViewById(R.id.customerSearchButton);
        toolbarSave = findViewById(R.id.saveButton);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        CustomerViewPagerAdapter adapter = new CustomerViewPagerAdapter(getSupportFragmentManager());
        mainPage.setAdapter(adapter);
        tabLayout.setupWithViewPager(mainPage);





    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Handle the back button click here
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void replaceFragment(Fragment fragment ,int  flag){

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (flag == 0){
            ft.add( R.id.customerActivityFramelayout,fragment);


        }else {
            ft.replace(R.id.customerActivityFramelayout,fragment);
            ft.addToBackStack(null);

        }
        ft.commit();
    }
    public void changeToolbar(){



    }
}