package com.kabirnayeem99.paymentpaid.presentation.fragments

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.kabirnayeem99.paymentpaid.R
import com.kabirnayeem99.paymentpaid.presentation.adapters.ProfileSettingsAdapter
import com.kabirnayeem99.paymentpaid.presentation.adapters.ProfileSettingsItem
import com.kabirnayeem99.paymentpaid.presentation.activities.HomeActivity
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*

@ExperimentalCoroutinesApi
class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val auth = FirebaseAuth.getInstance()
    private val user: FirebaseUser? = auth.currentUser

    private val settingsItemListLiveData: MutableLiveData<List<ProfileSettingsItem>> =
        MutableLiveData()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
        setUpRecyclerView()
        ivUserProfileImage.setOnClickListener {
            activity?.supportFragmentManager.let { fragment ->
                if (fragment != null) {
                    SettingsModifyingFragment().newInstance("Change DisplayName")
                        .show(fragment, ProfileFragment::class.java.simpleName)
                }
            }
        }
        setupPopBack(view)
    }


    private fun setupPopBack(view: View) {
        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
                fragmentManager?.popBackStack(
                    HomeActivity.TAG,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
                return@OnKeyListener true
            }
            false
        })
    }

    private fun setUpRecyclerView() {
        val profileAdapter = ProfileSettingsAdapter()
        rvSettings.apply {
            adapter = profileAdapter
            layoutManager = LinearLayoutManager(context)
        }.also {
            initialiseSettingsItems()
        }
        settingsItemListLiveData.observe(viewLifecycleOwner, {
            profileAdapter.differ.submitList(it)

        })

    }

    private fun initialiseSettingsItems() {

        if (user != null) {
            with(user) {
                lateinit var settingsName: ProfileSettingsItem

                if (displayName != null && displayName?.isNotEmpty() == true) {
                    settingsName = ProfileSettingsItem(1, "Name", displayName!!)
                } else {
                    with(StringTokenizer(email, "@")) {
                        val username: String = this.nextToken()
                        settingsName = ProfileSettingsItem(1, "Name", username)
                    }
                }

                val settingsImage: ProfileSettingsItem = if (this.photoUrl != null) {
                    ProfileSettingsItem(2, "Image", this.photoUrl?.toString()!!)
                } else {
                    ProfileSettingsItem(2, "Image", "N/A")
                }

                val settingsPhoneNumber: ProfileSettingsItem =
                    if (this.phoneNumber != null && this.phoneNumber?.isNotEmpty() == true) {
                        ProfileSettingsItem(3, "Phone Number", this.phoneNumber?.toString()!!)
                    } else {
                        ProfileSettingsItem(3, "Phone Number", "N/A")
                    }

                val settingsEmail: ProfileSettingsItem =
                    if (this.email != null && this.email?.isNotEmpty() == true) {
                        ProfileSettingsItem(4, "Email", email!!)
                    } else {
                        ProfileSettingsItem(4, "Email", "N/A")
                    }
                settingsItemListLiveData.value =
                    listOf(settingsName, settingsImage, settingsPhoneNumber, settingsEmail)
            }
        }
    }

    private fun setData() {
        if (user != null) {
            with(user) {
                if (this.displayName?.isNotEmpty() == true) {
                    tvUserDisplayName.text = this.displayName
                } else {
                    with(StringTokenizer(email, "@")) {
                        val username: String = this.nextToken()
                        tvUserDisplayName.text = username
                    }
                }
                tvUserEmail.text = this.email
            }
        }
    }

}