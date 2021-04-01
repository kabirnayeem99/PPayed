package com.kabirnayeem99.paymentpaid.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kabirnayeem99.paymentpaid.R
import com.kabirnayeem99.paymentpaid.adapters.PaymentAdapter
import com.kabirnayeem99.paymentpaid.ui.FirestoreViewModel
import com.kabirnayeem99.paymentpaid.ui.activities.HomeActivity
import kotlinx.android.synthetic.main.fragment_payments.*

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

    }

    private fun manipulateData() {

        Log.d(tag, "manipulateData: the manipulating data started")
        firestoreViewModel.paymentList.observe(viewLifecycleOwner,
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