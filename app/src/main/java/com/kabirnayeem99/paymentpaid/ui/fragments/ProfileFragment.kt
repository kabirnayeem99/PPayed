package com.kabirnayeem99.paymentpaid.ui.fragments

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.kabirnayeem99.paymentpaid.R
import com.kabirnayeem99.paymentpaid.adapters.ProfileSettingsAdapter
import com.kabirnayeem99.paymentpaid.adapters.ProfileSettingsItem
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.flow.Flow
import java.util.*

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val auth = FirebaseAuth.getInstance()
    private val user: FirebaseUser? = auth.currentUser

    companion object {
        const val TAG = "ProfileFragment"
    }

    private val settingsItemListLiveData: MutableLiveData<List<ProfileSettingsItem>> = MutableLiveData()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
        setUpRecyclerView()
        ivUserProfileImage.setOnClickListener(View.OnClickListener {
            fragmentManager?.let { it1 -> SettingsModifyingFragment().newInstance("T").show(it1, "") }
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

                if (this.displayName?.isNotEmpty() == true) {
                    settingsName = ProfileSettingsItem(1, "Name", displayName)
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

                val settingsPhoneNumber: ProfileSettingsItem = if (this.phoneNumber != null && this.phoneNumber?.isNotEmpty() == true) {
                    ProfileSettingsItem(3, "Phone Number", this.phoneNumber?.toString()!!)
                } else {
                    ProfileSettingsItem(3, "Phone Number", "N/A")
                }

                val settingsEmail: ProfileSettingsItem = if (this.phoneNumber != null && this.phoneNumber?.isNotEmpty() == true) {
                    ProfileSettingsItem(4, "Email", email!!)
                } else {
                    ProfileSettingsItem(4, "Email", "N/A")
                }
                settingsItemListLiveData.value = listOf(settingsName, settingsImage, settingsPhoneNumber, settingsEmail)
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