package com.kabirnayeem99.paymentpaid.adapters;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.kabirnayeem99.paymentpaid.fragments.ExportsFragment;
import com.kabirnayeem99.paymentpaid.fragments.PaymentsFragment;
import com.kabirnayeem99.paymentpaid.fragments.WorksFragment;

public class HomePagerAdapter extends FragmentStatePagerAdapter {
    private static final String TAG = "HomePagerAdapter";

    private final int numberOfTabs;

    public HomePagerAdapter(@NonNull FragmentManager fm, int numberOfTabs) {
        super(fm, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.numberOfTabs = numberOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Log.d(TAG, "getItem: " + position);
        switch (position) {
            case 0:
                return new WorksFragment();
            case 1:
                return new PaymentsFragment();
            case 2:
                return new ExportsFragment();
        }
        return new WorksFragment();
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        Log.d(TAG, "getPageTitle: " + position);
        String[] tabTitles = {"Works", "Payments", "Export"};
        return tabTitles[position];
    }
}
