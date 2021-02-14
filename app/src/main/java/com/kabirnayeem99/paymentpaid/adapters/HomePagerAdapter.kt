package com.kabirnayeem99.paymentpaid.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.kabirnayeem99.paymentpaid.ui.fragments.AboutFragment
import com.kabirnayeem99.paymentpaid.ui.fragments.PaymentsFragment
import com.kabirnayeem99.paymentpaid.ui.fragments.WorksFragment

class HomePagerAdapter(fm: FragmentManager, private val numberOfTabs: Int) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return WorksFragment()
            1 -> return PaymentsFragment.getInstance()
            2 -> return AboutFragment.getInstance()
        }
        return WorksFragment()
    }

    override fun getCount(): Int {
        return numberOfTabs
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val tabTitles = arrayOf("Works", "Payments", "About")
        return tabTitles[position]
    }
}