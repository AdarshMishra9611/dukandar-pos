package com.example.dukandar20.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.dukandar20.Fragments.FragmentBalance;
import com.example.dukandar20.Fragments.FragmentCart;
import com.example.dukandar20.Fragments.FragmentCategory;
import com.example.dukandar20.Fragments.FragmentCustomer;
import com.example.dukandar20.Fragments.FragmentDueCustomer;
import com.example.dukandar20.Fragments.FragmentMore;
import com.example.dukandar20.Fragments.FragmentSales;

public class CustomerViewPagerAdapter extends FragmentPagerAdapter {

    public CustomerViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentCustomer();
            case 1:
                return new FragmentDueCustomer();

            default:
                return new FragmentCustomer();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "ALL CUSTOMER";
        } else {
            return "DUE CUSTOMER";
        }


    }

}
