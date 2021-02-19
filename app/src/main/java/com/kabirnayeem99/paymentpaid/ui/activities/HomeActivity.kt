package com.kabirnayeem99.paymentpaid.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.PagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.kabirnayeem99.paymentpaid.R
import com.kabirnayeem99.paymentpaid.adapters.HomePagerAdapter
import com.kabirnayeem99.paymentpaid.data.db.WorkDatabase
import com.kabirnayeem99.paymentpaid.data.repositories.WorkRepository
import com.kabirnayeem99.paymentpaid.ui.WorkViewModel
import com.kabirnayeem99.paymentpaid.ui.WorkViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var pagerAdapter: PagerAdapter

    lateinit var workViewModel: WorkViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        createTabLayout()
        setUpViewModel()

    }

    private fun setUpViewModel() {
        val workRepository = WorkRepository(WorkDatabase(this))

        val workViewModelProviderFactory = WorkViewModelProviderFactory(workRepository)

        workViewModel = ViewModelProvider(this, workViewModelProviderFactory).get(WorkViewModel::class.java)

    }

    private fun createTabLayout() {
        tlMainTabHome.setupWithViewPager(vpHome)

        pagerAdapter = HomePagerAdapter(supportFragmentManager, tlMainTabHome.tabCount)

        vpHome.adapter = pagerAdapter

        tlMainTabHome?.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                vpHome.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }


}