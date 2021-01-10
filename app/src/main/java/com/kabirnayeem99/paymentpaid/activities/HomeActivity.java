package com.kabirnayeem99.paymentpaid.activities;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.kabirnayeem99.paymentpaid.R;
import com.kabirnayeem99.paymentpaid.adapters.HomePagerAdapter;

public class HomeActivity extends AppCompatActivity {

    TabLayout tlMainTab;
    TabItem tiWorks;
    TabItem tiPayments;
    TabItem tiExports;
    ViewPager vpHome;
    PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        initViews();

        createTabLayout();
    }

    private void createTabLayout() {
        tlMainTab.setupWithViewPager(vpHome);

        pagerAdapter = new HomePagerAdapter(getSupportFragmentManager(), tlMainTab.getTabCount());
        vpHome.setAdapter(pagerAdapter);

        tlMainTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vpHome.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initViews() {
        tlMainTab = findViewById(R.id.tl_tab_home);
        tiWorks = findViewById(R.id.ti_work_home);
        tiPayments = findViewById(R.id.ti_payments_work);
        tiExports = findViewById(R.id.ti_export_home);
        vpHome = findViewById(R.id.vp_view_pager_home);
    }
}