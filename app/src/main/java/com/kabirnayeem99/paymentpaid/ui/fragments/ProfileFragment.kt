package com.kabirnayeem99.paymentpaid.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.kabirnayeem99.paymentpaid.R
import kotlinx.android.synthetic.main.fragment_profile.*
import java.util.*

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val auth = FirebaseAuth.getInstance()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setData()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setData() {
        if (auth.currentUser != null) {
            with(auth.currentUser!!) {
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