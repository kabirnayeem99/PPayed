package com.kabirnayeem99.paymentpaid.presentation.fragments

import android.graphics.Typeface
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.github.mikephil.charting.components.Description
import com.kabirnayeem99.paymentpaid.R
import com.kabirnayeem99.paymentpaid.data.repositories.ChartRepositoryImpl
import com.kabirnayeem99.paymentpaid.domain.repositories.ChartRepository
import com.kabirnayeem99.paymentpaid.other.Utils
import com.kabirnayeem99.paymentpaid.presentation.FirestoreViewModel
import com.kabirnayeem99.paymentpaid.presentation.activities.HomeActivity
import kotlinx.android.synthetic.main.fragment_analytics.*
import kotlinx.coroutines.*
import java.util.*


@ExperimentalCoroutinesApi
class AnalyticsFragment : Fragment(R.layout.fragment_analytics) {

    companion object {
        const val TAG = "AnalyticsFragment"
    }

    private lateinit var firestoreViewModel: FirestoreViewModel
    private lateinit var typeFace: Typeface

    private lateinit var charRepository: ChartRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewModel()
        typeFace = context?.let { ResourcesCompat.getFont(it, R.font.lobster) }!!
        initGraph()

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

    /**
     * Sets up the [WorkViewModel] for this [WorkFragment]
     */
    private fun setUpViewModel() {
        firestoreViewModel = (activity as HomeActivity).firestoreViewModel
    }

    private fun initGraph() {

        firestoreViewModel.paymentListByMonth.observe(viewLifecycleOwner, { paymentList ->
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

            charRepository = ChartRepositoryImpl()

            val barData = charRepository.getBarData(paymentList, requireContext())
            val pieData = charRepository.getPieData(paymentList, requireContext())

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
                val centerText = "${currentMonth}\n${Utils.getCurrentMonthName(currentMonth)}"
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