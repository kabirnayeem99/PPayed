package com.kabirnayeem99.paymentpaid.ui.fragments

import android.graphics.Typeface
import android.os.Bundle
import android.renderscript.Type
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.components.Description
import com.kabirnayeem99.paymentpaid.R
import com.kabirnayeem99.paymentpaid.data.chart.ChartUtils
import com.kabirnayeem99.paymentpaid.ui.FirestoreViewModel
import com.kabirnayeem99.paymentpaid.ui.activities.HomeActivity
import com.kabirnayeem99.paymentpaid.utils.CustomUtils
import kotlinx.android.synthetic.main.fragment_analytics.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


class AnalyticsFragment : Fragment(R.layout.fragment_analytics) {

    companion object {
        const val TAG = "AnalyticsFragment"
    }

    private lateinit var firestoreViewModel: FirestoreViewModel
    private lateinit var typeFace: Typeface

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewModel()
        typeFace = context?.let { ResourcesCompat.getFont(it, R.font.lobster) }!!
        initGraph()
    }

    /**
     * Sets up the [WorkViewModel] for this [WorkFragment]
     */
    private fun setUpViewModel() {
        firestoreViewModel = (activity as HomeActivity).firestoreViewModel
    }

    private fun initGraph() {

        firestoreViewModel.paymentList.observe(viewLifecycleOwner, { paymentList ->
            when (paymentList == null || paymentList.isEmpty()) {
                true -> showLoading()
                false -> {
                    hideLoading()
                    implementDataSeries(paymentList)
                }
            }
        })
    }

    private fun implementDataSeries(paymentList: List<Long>) {

        CoroutineScope(Dispatchers.IO).launch {

            val description = Description()
            description.text = "Payments"

            val barData = ChartUtils.getBarData(paymentList, requireContext())
            val pieData = ChartUtils.getPieData(paymentList, requireContext())

            withContext(Dispatchers.Main) {
                barChart.let { bc ->
                    bc.data = barData
                    bc.description = description
                    bc.setGridBackgroundColor(R.color.material_greenish_white)
                    bc.setBackgroundColor(resources.getColor(R.color.material_greenish_white))
                    bc.setFitBars(true)
                    bc.animateY(900)
                }

                val currentMonth = Calendar.getInstance().get(Calendar.MONTH)
                val centerText = "${currentMonth}\n${CustomUtils.getCurrentMonthName(currentMonth)}"
                pieChart.let { pc ->
                    pc.data = pieData
                    pc.description = description
                    pc.animateY(900)
                    pc.centerText = centerText
                    pc.setCenterTextTypeface(typeFace)
                    pc.setCenterTextSize(20f)

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