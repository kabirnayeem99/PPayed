package com.kabirnayeem99.paymentpaid.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.kabirnayeem99.paymentpaid.R
import com.kabirnayeem99.paymentpaid.adapters.HomePagerAdapter
import com.kabirnayeem99.paymentpaid.adapters.PaymentAdapter
import com.kabirnayeem99.paymentpaid.ui.WorkViewModel
import com.kabirnayeem99.paymentpaid.ui.activities.HomeActivity
import com.kabirnayeem99.paymentpaid.utils.Resource
import kotlinx.android.synthetic.main.fragment_payments.*

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
        workViewModel.getTotalPaymentsByMonth().observe(viewLifecycleOwner,
                { paymentList ->
                    when (paymentList.isEmpty()) {
                        true -> showLoading()
                        false -> {
                            hideLoading()
                            paymentAdapter.differ.submitList(paymentList)
                        }
                    }
                })

        workViewModel.getTotalPaymentByYear().observe(viewLifecycleOwner,
                { totalPayment ->
                    totalPayment?.let {
                        tvTotalPayment.text = totalPayment.toString()
                    }
                })
    }

    private fun showLoading() {
        progressBarPayment.visibility = View.VISIBLE
        paymentDataShown.visibility = View.INVISIBLE
    }

    private fun hideLoading() {
        progressBarPayment.visibility = View.INVISIBLE
        paymentDataShown.visibility = View.VISIBLE
    }

    private fun showError() {
        errorImage.visibility = View.VISIBLE
    }


    private fun initRecyclerView() {

        paymentAdapter = PaymentAdapter()

        rvPaymentListByMonth.apply {
            adapter = paymentAdapter
            layoutManager = LinearLayoutManager(activity)
            rvPaymentListByMonth!!.setHasFixedSize(true)
        }
    }

}