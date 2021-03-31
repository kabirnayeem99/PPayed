package com.kabirnayeem99.paymentpaid.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.kabirnayeem99.paymentpaid.R
import com.kabirnayeem99.paymentpaid.data.chart.ChartUtils
import com.kabirnayeem99.paymentpaid.ui.FirestoreViewModel
import com.kabirnayeem99.paymentpaid.ui.activities.HomeActivity
import kotlinx.android.synthetic.main.fragment_analytics.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AnalyticsFragment : Fragment(R.layout.fragment_analytics) {

    private lateinit var firestoreViewModel: FirestoreViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewModel()
        initGraph()
    }

    /**
     * Sets up the [WorkViewModel] for this [WorkFragment]
     */
    private fun setUpViewModel() {
        firestoreViewModel = (activity as HomeActivity).firestoreViewModel
    }

    private fun initGraph() {

        firestoreViewModel.getAllPaymentsByMont().observe(viewLifecycleOwner, { paymentList ->
            when (paymentList == null) {
                true -> showLoading()
                false -> {
                    hideLoading()
                    implementDataSeries(paymentList)
                }
            }
        })
    }

    private fun implementDataSeries(paymentList: List<Int>) {

        CoroutineScope(Dispatchers.IO).launch {

            val barData = ChartUtils.getBarData(paymentList, requireContext())
            val pieData = ChartUtils.getPieData(paymentList, requireContext())

            withContext(Dispatchers.Main) {
                barChart.let { bc ->
                    bc.data = barData
                    bc.animateY(1000)
                }

                pieChart.let { pc ->
                    pc.data = pieData
                    pc.animateY(1000)
                }
            }
        }

    }


    private fun showLoading() {
        progressBarAnalytics.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        progressBarAnalytics.visibility = View.INVISIBLE
    }

}