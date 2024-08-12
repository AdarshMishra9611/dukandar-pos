package com.example.dukandar20.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.dukandar20.Fragments.FragmentBalance;
import com.example.dukandar20.Fragments.FragmentDueCustomer;
import com.example.dukandar20.Fragments.FragmentMore;
import com.example.dukandar20.Fragments.FragmentSales;
import com.example.dukandar20.Fragments.FragmentCart;
import com.example.dukandar20.Fragments.FragmentCategory;

public class MainViewPagerAdapter extends FragmentStateAdapter {




    public MainViewPagerAdapter(@NonNull FragmentActivity fragmentActivity ) {
        super(fragmentActivity);

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Return a new instance of the fragment based on the position
        switch (position) {
            case 0:
                return new FragmentSales();
            case 1:
                return new FragmentDueCustomer();
            case 2:
                return new FragmentCart();
            case 3:
                return new FragmentCategory();
            case 4:
                return new FragmentMore();
            default:
                return new FragmentSales();
        }
    }

    @Override
    public int getItemCount() {
        return 5; // Number of fragments
    }

    @Override
    public long getItemId(int position) {
        // Return a unique ID for each fragment (new method)
        return position;
    }

    @Override
    public boolean containsItem(long itemId) {
        // Check if the item ID exists (new method)
        return itemId >= 0 && itemId < getItemCount();
    }

}
