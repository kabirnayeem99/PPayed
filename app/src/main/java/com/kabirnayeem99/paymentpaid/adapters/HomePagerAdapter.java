package com.kabirnayeem99.paymentpaid.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.kabirnayeem99.paymentpaid.fragments.ExportsFragment;
import com.kabirnayeem99.paymentpaid.fragments.PaymentsFragment;
import com.kabirnayeem99.paymentpaid.fragments.WorksFragment;

public class HomePagerAdapter extends FragmentStatePagerAdapter {

    private final int numberOfTabs;

    public HomePagerAdapter(@NonNull FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new WorksFragment();
            case 1:
                return new PaymentsFragment();
            case 2:
                return new ExportsFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }

    
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return ("Tab" + super.getPageTitle(position));
    }
}
