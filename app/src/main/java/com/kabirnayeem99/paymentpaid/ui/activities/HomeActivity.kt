package com.kabirnayeem99.paymentpaid.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kabirnayeem99.paymentpaid.R
import com.kabirnayeem99.paymentpaid.data.db.WorkDatabase
import com.kabirnayeem99.paymentpaid.data.repositories.WorkRepository
import com.kabirnayeem99.paymentpaid.enums.AccountStatus
import com.kabirnayeem99.paymentpaid.ui.LogInRegisterViewModel
import com.kabirnayeem99.paymentpaid.ui.LogInRegisterViewModelProviderFactory
import com.kabirnayeem99.paymentpaid.ui.WorkViewModel
import com.kabirnayeem99.paymentpaid.ui.WorkViewModelProviderFactory
import com.kabirnayeem99.paymentpaid.ui.fragments.AboutFragment
import com.kabirnayeem99.paymentpaid.ui.fragments.AnalyticsFragment
import com.kabirnayeem99.paymentpaid.ui.fragments.PaymentsFragment
import com.kabirnayeem99.paymentpaid.ui.fragments.WorkFragment
import kotlinx.android.synthetic.main.activity_home.*


/**
 * This Activity is the entry point of the application
 * and holds three fragment, such as works fragment, payment fragment and about fragment.
 */
class HomeActivity : AppCompatActivity() {

    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var workFragment: WorkFragment
    private lateinit var analyticsFragment: AnalyticsFragment
    private lateinit var paymentsFragment: PaymentsFragment
    private lateinit var aboutFragment: AboutFragment
    lateinit var workViewModel: WorkViewModel
    private lateinit var logInRegisterViewModel: LogInRegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_PaymentPaid)
        setContentView(R.layout.activity_home)
        initFragments()
        setUpFragmentNavigation()
        setUpNavigationDrawer()
//        createTabLayout()
        setUpViewModel()
        setUpLoggedOutListener()
    }


    private fun initFragments() {
        workFragment = WorkFragment()
        paymentsFragment = PaymentsFragment()
        aboutFragment = AboutFragment()
        analyticsFragment = AnalyticsFragment()
    }

    private fun closeDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        }
    }


    /**
     * This method sets up the DrawerLayout for the home activity
     */
    private fun setUpNavigationDrawer() {
        drawerToggle = ActionBarDrawerToggle(
                this, drawerLayout, R.string.open_drawer, R.string.close_drawer
        )
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.workNavMenu -> {
                    makeCurrentFragment(workFragment)
                }
                R.id.paymentNavMenu -> {
                    makeCurrentFragment(paymentsFragment)
                }
                R.id.analyticsNavMenu -> {
                    makeCurrentFragment(analyticsFragment)
                }
                R.id.aboutNavMenu -> {
                    makeCurrentFragment(aboutFragment)
                }
            }
            true
        }
    }

    /**
     * This method will show the current fragment on screen
     * @param fragment of [Fragment] type
     */
    private fun makeCurrentFragment(fragment: Fragment) {
        closeDrawer()
        supportFragmentManager
                .beginTransaction()
                // adds animation from https://github.com/yendangn/Fragment-Transaction-Animation
                .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                .replace(R.id.flFragmentPlaceholder, fragment)
                .commit()
    }


    override fun overridePendingTransition(enterAnim: Int, exitAnim: Int) {
        super.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun setUpFragmentNavigation() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.flFragmentPlaceholder, WorkFragment())
                .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    private fun setUpLoggedOutListener() {
        logInRegisterViewModel.getLoggedOutLiveData().observe(this, { isLoggedOut ->
            if (isLoggedOut) {
                Toast.makeText(this, "You are logged out", Toast.LENGTH_SHORT).show()
                moveToSignInActivity()
            }
        })
    }

    /**
     * This method starts the [SignInActivity]
     */
    private fun moveToSignInActivity() {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
        this.finish()
    }

    /**
     * This method sets up [LogInRegisterViewModel] & [WorkViewModel] for the [HomeActivity]
     */
    private fun setUpViewModel() {
        lateinit var accountStatus: AccountStatus


        val logInRegisterViewModelFactory = LogInRegisterViewModelProviderFactory(application)

        logInRegisterViewModel = ViewModelProvider(this,
                logInRegisterViewModelFactory).get(LogInRegisterViewModel::class.java)


        if (logInRegisterViewModel.getOfflineStatus()) {
            accountStatus = AccountStatus.OFFLINE
            val workRepository = WorkRepository(WorkDatabase(this), accountStatus)
            val workViewModelProviderFactory = WorkViewModelProviderFactory(workRepository)
            workViewModel = ViewModelProvider(this,
                    workViewModelProviderFactory).get(WorkViewModel::class.java)
        } else {

            accountStatus = AccountStatus.ONLINE
            val workRepository = WorkRepository(WorkDatabase(this), accountStatus)
            val workViewModelProviderFactory = WorkViewModelProviderFactory(workRepository)
            workViewModel = ViewModelProvider(this,
                    workViewModelProviderFactory).get(WorkViewModel::class.java)
        }

    }


    /**
     * This method creates a tab layout in the home screen
     */
//    private fun createTabLayout() {
//        tlMainTabHome.setupWithViewPager(vpHome)
//
//        pagerAdapter = HomePagerAdapter(supportFragmentManager, tlMainTabHome.tabCount)
//
//        vpHome.adapter = pagerAdapter
//
//        tlMainTabHome?.addOnTabSelectedListener(object : OnTabSelectedListener {
//            override fun onTabSelected(tab: TabLayout.Tab) {
//                vpHome.currentItem = tab.position
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab) {}
//            override fun onTabReselected(tab: TabLayout.Tab) {}
//        })
//    }


}