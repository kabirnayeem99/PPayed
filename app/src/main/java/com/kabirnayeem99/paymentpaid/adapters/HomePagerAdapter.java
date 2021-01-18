package com.kabirnayeem99.paymentpaid.adapters;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.kabirnayeem99.paymentpaid.ui.fragments.AboutFragment;
import com.kabirnayeem99.paymentpaid.ui.fragments.PaymentsFragment;
import com.kabirnayeem99.paymentpaid.ui.fragments.WorksFragment;

public class HomePagerAdapter extends FragmentStatePagerAdapter {

    private final int numberOfTabs;

    public HomePagerAdapter(@NonNull FragmentManager fm, int numberOfTabs) {
        super(fm, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.numberOfTabs = numberOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return WorksFragment.getInstance();
            case 1:
                return PaymentsFragment.getInstance();
            case 2:
                return  AboutFragment.getInstance();
        }
        return WorksFragment.getInstance();
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        final String[] tabTitles = {"Works", "Payments", "About"};
        return tabTitles[position];
    }
}
