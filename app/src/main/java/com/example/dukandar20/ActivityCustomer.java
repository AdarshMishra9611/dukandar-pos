package com.example.dukandar20;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.dukandar20.Fragments.FragmentAddCustomer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ActivityCustomer extends AppCompatActivity {
    private Toolbar toolbar;
    FloatingActionButton addCustomerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        toolbar = findViewById(R.id.toolbar_customer);
        addCustomerButton =findViewById(R.id.floatingActionButtonAddCustomer);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        addCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new FragmentAddCustomer(),0);
            }
        });




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

    private void replaceFragment(Fragment fragment ,int  flag){

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
}