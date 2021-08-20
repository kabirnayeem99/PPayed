package com.kabirnayeem99.paymentpaid.presentation.fragments

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kabirnayeem99.paymentpaid.R
import com.kabirnayeem99.paymentpaid.other.Resource
import com.kabirnayeem99.paymentpaid.presentation.adapters.PaymentAdapter
import com.kabirnayeem99.paymentpaid.presentation.viewmodels.WorkViewModel
import com.kabirnayeem99.paymentpaid.presentation.activities.HomeActivity
import kotlinx.android.synthetic.main.fragment_payments.*
import kotlinx.android.synthetic.main.fragment_works.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

@ExperimentalCoroutinesApi
class PaymentsFragment : Fragment(R.layout.fragment_payments) {

    private lateinit var paymentAdapter: PaymentAdapter
    private lateinit var workViewModel: WorkViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        workViewModel = (activity as HomeActivity).workViewModel

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
                    HomeActivity::class.java.simpleName,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
                return@OnKeyListener true
            }
            false
        })
    }

    private fun manipulateData() {

        Timber.d("manipulateData: the manipulating data started")
        workViewModel.paymentListByMonth.observe(viewLifecycleOwner,
            { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        showLoading()
                    }

                    is Resource.Success -> {
                        hideLoading()
                        paymentAdapter.differ.submitList(resource.data)
                    }

                    is Resource.Error -> {
                        hideLoading()
                        getSnackBar(resource.message).show()
                    }

                }

            })

        workViewModel.paymentOfCurrentYear.observe(viewLifecycleOwner,
            { totalPayment ->
                totalPayment?.let {
                    tvTotalPayment.text = totalPayment.toString()
                }
            })
    }

    private fun showLoading() {
        progressBarPayment.visibility = View.VISIBLE
        relativeLayoutPayment.visibility = View.GONE
    }


    private fun getSnackBar(message: String?): Snackbar {
        return Snackbar.make(
            this.requireView(),
            message ?: "Something went wrong",
            Snackbar.LENGTH_LONG
        )
    }

    private fun hideLoading() {
        progressBarPayment.visibility = View.GONE
        relativeLayoutPayment.visibility = View.VISIBLE
    }


    private fun initRecyclerView() {

        paymentAdapter = PaymentAdapter()

        rvPaymentListByMonth.apply {
            adapter = paymentAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}