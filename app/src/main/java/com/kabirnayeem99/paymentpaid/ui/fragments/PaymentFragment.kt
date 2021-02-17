package com.kabirnayeem99.paymentpaid.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.kabirnayeem99.paymentpaid.R
import com.kabirnayeem99.paymentpaid.adapters.PaymentAdapter
import com.kabirnayeem99.paymentpaid.ui.WorkViewModel
import kotlinx.android.synthetic.main.fragment_payments.*

class PaymentsFragment : Fragment(R.layout.fragment_payments) {
    private lateinit var paymentAdapter: PaymentAdapter
    private lateinit var workViewModel: WorkViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        workViewModel = ViewModelProviders.of(requireActivity()).get(WorkViewModel::class.java)

        initRecyclerView()

        manipulateData()

    }

    private fun manipulateData() {
        workViewModel.getTotalPaymentByMonth().observe(viewLifecycleOwner,
                { payments: List<Int?> ->
                    paymentAdapter.differ.submitList(payments)
                })

        workViewModel.getTotalPaymentByYear().observe(viewLifecycleOwner,
                { payment: Int ->
                    tvTotalPayment.text = payment.toString()
                })
    }


    private fun initRecyclerView() {
        rvPaymentListByMonth.apply {
            adapter = paymentAdapter
            layoutManager = LinearLayoutManager(activity)
            rvPaymentListByMonth!!.setHasFixedSize(true)
        }
    }

}