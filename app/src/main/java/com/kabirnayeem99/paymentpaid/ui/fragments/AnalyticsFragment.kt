package com.kabirnayeem99.paymentpaid.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.*
import com.kabirnayeem99.paymentpaid.R
import com.kabirnayeem99.paymentpaid.ui.WorkViewModel
import com.kabirnayeem99.paymentpaid.ui.activities.HomeActivity
import com.kabirnayeem99.paymentpaid.utils.CustomUtils
import kotlinx.android.synthetic.main.fragment_analytics.*


class AnalyticsFragment : Fragment(R.layout.fragment_analytics) {

    private lateinit var workViewModel: WorkViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewModel()
        initGraph()

    }

    /**
     * Sets up the [WorkViewModel] for this [WorkFragment]
     */
    private fun setUpViewModel() {
        workViewModel = (activity as HomeActivity).workViewModel
    }

    private fun initGraph() {

        workViewModel.getTotalPaymentsByMonth().observe(viewLifecycleOwner, { paymentList ->
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


        val barEntries = ArrayList<BarEntry>()
        val pieEntries = ArrayList<PieEntry>()

        var position = 0f
        paymentList.forEach {
            barEntries.add(BarEntry(position, it.toFloat()))
            if (it > 0) {
                pieEntries.add(PieEntry(it.toFloat(), CustomUtils.getCurrentMonthName(position.toInt())))
            }
            position += 1f
        }

        val barDataSet = BarDataSet(barEntries, "Payments")
        val pieDataSet = PieDataSet(pieEntries, "payments")

        val barData = BarData(barDataSet)
        val pieData = PieData(pieDataSet)
        barChart.data = barData
        pieChart.data = pieData



        barDataSet.valueTextColor = getColor(requireContext(), R.color.material_grey)
        barDataSet.color = getColor(requireContext(), R.color.material_dark_green_dark)
        barDataSet.colors = CustomUtils.getColorsFromTemplate()
        pieDataSet.valueTextColor = getColor(requireContext(), R.color.material_grey)
        pieDataSet.color = getColor(requireContext(), R.color.material_dark_green_dark)
        pieDataSet.colors = CustomUtils.getColorsFromTemplate()
        pieChart.isEnabled = false
        val description = Description()
        description.text = "Payment Charts"
        description.let {
            pieChart.description = it
            barChart.description = it
        }




        barChart.animateY(1000)
        pieChart.animateY(1000)

    }


    private fun showLoading() {
        progressBarAnalytics.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        progressBarAnalytics.visibility = View.INVISIBLE
    }


    companion object
}