package com.kabirnayeem99.paymentpaid.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kabirnayeem99.paymentpaid.R
import com.kabirnayeem99.paymentpaid.adapters.PaymentAdapter
import com.kabirnayeem99.paymentpaid.ui.WorkViewModel
import com.kabirnayeem99.paymentpaid.ui.activities.HomeActivity
import kotlinx.android.synthetic.main.fragment_payments.*
import kotlinx.android.synthetic.main.fragment_payments.view.*

class PaymentsFragment : Fragment(R.layout.fragment_payments) {


    private lateinit var paymentAdapter: PaymentAdapter
    private lateinit var workViewModel: WorkViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        workViewModel = (activity as HomeActivity).workViewModel

        initRecyclerView()

        manipulateData()

    }

    private fun manipulateData() {

        Log.d(tag, "manipulateData: the manipulationg data started")
        workViewModel.getTotalPaymentsByMonth().observe(viewLifecycleOwner,
                { paymentList ->
                    Log.d(tag, "manipulateData: $paymentList")
                    paymentList?.let {
                        paymentAdapter.differ.submitList(paymentList)
                    }
                })

        workViewModel.getTotalPaymentByYear().observe(viewLifecycleOwner,
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