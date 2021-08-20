package com.kabirnayeem99.paymentpaid.data.repositories

import android.content.Context
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.data.*
import com.kabirnayeem99.paymentpaid.R
import com.kabirnayeem99.paymentpaid.domain.repositories.ChartRepository
import com.kabirnayeem99.paymentpaid.other.Utils

class ChartRepositoryImpl : ChartRepository {

    private val colorTemplate = Utils.getColorsFromTemplate()

    /**
     * This method will create a BarData object based on the payment list
     * @param paymentList of [Int] [List] and context of [Context]
     * @return [BarData]
     */
    override fun getBarData(paymentList: List<Long>, context: Context): BarData {
        var position = 0f
        val barEntries = ArrayList<BarEntry>()

        for ((index, payment) in paymentList.withIndex()) {
            if (payment == 0L) {
                barEntries.add(BarEntry(index.toFloat(), 0f))
            } else {
                barEntries.add(BarEntry(index.toFloat(), payment.toFloat()))
            }
            barEntries.add(BarEntry(index.toFloat(), payment.toFloat()))
            position += 1f
        }

        val barDataSet = BarDataSet(barEntries, "Payments in Bar Template")

        barDataSet.valueTextColor = ContextCompat.getColor(context, R.color.material_black)
        barDataSet.color = ContextCompat.getColor(context, R.color.material_black)
        barDataSet.colors = colorTemplate

        return BarData(barDataSet)
    }


    /**
     * This method will create a PieData object based on the payment list
     * @param paymentList of [Int] [List] and context of [Context]
     * @return [PieData]
     */
    override fun getPieData(paymentList: List<Long>, context: Context): PieData {

        val pieEntries = ArrayList<PieEntry>()

        var position = 0f
        paymentList.forEach {
            if (it > 0) {
                pieEntries.add(PieEntry(it.toFloat(), Utils.getCurrentMonthName(position.toInt())))
            }
            position += 1f
        }

        val pieDataSet = PieDataSet(pieEntries, "Payments Comparison in Pie By month")
        pieDataSet.valueTextColor = ContextCompat.getColor(context, R.color.material_black)
        pieDataSet.color = ContextCompat.getColor(context, R.color.material_black)
        pieDataSet.colors = colorTemplate

        return PieData(pieDataSet)
    }
}