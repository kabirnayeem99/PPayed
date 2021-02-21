package com.kabirnayeem99.paymentpaid.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.PagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.kabirnayeem99.paymentpaid.R
import com.kabirnayeem99.paymentpaid.adapters.HomePagerAdapter
import com.kabirnayeem99.paymentpaid.data.db.WorkDatabase
import com.kabirnayeem99.paymentpaid.data.repositories.WorkRepository
import com.kabirnayeem99.paymentpaid.ui.LogInRegisterViewModel
import com.kabirnayeem99.paymentpaid.ui.LogInRegisterViewModelProviderFactory
import com.kabirnayeem99.paymentpaid.ui.WorkViewModel
import com.kabirnayeem99.paymentpaid.ui.WorkViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_home.*

/**
 * This Activity is the entry point of the application
 * and holds three fragment, such as works fragment, payment fragment and about fragment.
 */
class HomeActivity : AppCompatActivity() {

    private lateinit var pagerAdapter: PagerAdapter

    lateinit var workViewModel: WorkViewModel
    lateinit var logInRegisterViewModel: LogInRegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_PaymentPaid)
        setContentView(R.layout.activity_home)
        createTabLayout()
        setUpViewModel()
        setUpLoggedOutListener()

    }

    private fun setUpLoggedOutListener() {
        logInRegisterViewModel.getLoggedOutLiveData().observe(this, Observer { isLoggedOut ->
            if (isLoggedOut) {
                Toast.makeText(this, "You are logged out", Toast.LENGTH_SHORT).show()
                moveToSignInActivity()
            }
        })
    }

    private fun moveToSignInActivity() {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
        this.finish()
    }

    /**
     * This methods sets up the view model for this activity
     */
    private fun setUpViewModel() {
        val workRepository = WorkRepository(WorkDatabase(this))

        val workViewModelProviderFactory = WorkViewModelProviderFactory(workRepository)

        workViewModel = ViewModelProvider(this,
                workViewModelProviderFactory).get(WorkViewModel::class.java)


        val logInRegisterViewModelFactory = LogInRegisterViewModelProviderFactory(application)

        logInRegisterViewModel = ViewModelProvider(this,
                logInRegisterViewModelFactory).get(LogInRegisterViewModel::class.java)

    }


    /**
     * This method creates a tab layout in the home screen
     */
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