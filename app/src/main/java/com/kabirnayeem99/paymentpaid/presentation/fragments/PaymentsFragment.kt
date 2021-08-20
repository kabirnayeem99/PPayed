package com.kabirnayeem99.paymentpaid.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.kabirnayeem99.paymentpaid.R
import com.kabirnayeem99.paymentpaid.presentation.adapters.PaymentAdapter
import com.kabirnayeem99.paymentpaid.presentation.FirestoreViewModel
import com.kabirnayeem99.paymentpaid.presentation.activities.HomeActivity
import kotlinx.android.synthetic.main.fragment_payments.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class PaymentsFragment : Fragment(R.layout.fragment_payments) {

    companion object {
        const val TAG = "PaymentsFragment"
    }

    private lateinit var paymentAdapter: PaymentAdapter
    private lateinit var firestoreViewModel: FirestoreViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        firestoreViewModel = (activity as HomeActivity).firestoreViewModel

        initRecyclerView()

        manipulateData()

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

    private fun manipulateData() {

        Log.d(tag, "manipulateData: the manipulating data started")
        firestoreViewModel.paymentListByMonth.observe(viewLifecycleOwner,
            { paymentList ->
                Log.d(tag, "manipulateData: $paymentList")
                paymentList?.let {
                    paymentAdapter.differ.submitList(paymentList)
                }
            })

        firestoreViewModel.paymentOfCurrentYear.observe(viewLifecycleOwner,
            { totalPayment ->
                totalPayment?.let {
                    tvTotalPayment.text = totalPayment.toString()
                }
            })
    }


    private fun initRecyclerView() {

        paymentAdapter = PaymentAdapter()

        rvPaymentListByMonth.apply {
            adapter = paymentAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}