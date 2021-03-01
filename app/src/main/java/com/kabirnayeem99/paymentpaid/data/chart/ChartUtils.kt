package com.kabirnayeem99.paymentpaid.data.chart

import android.content.Context
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.data.*
import com.kabirnayeem99.paymentpaid.R
import com.kabirnayeem99.paymentpaid.utils.CustomUtils

object ChartUtils {

    /**
     * This method will create a BarData object based on the payment list
     * @param paymentList of [Int] [List] and context of [Context]
     * @return [BarData]
     */
    fun getBarData(paymentList: List<Int>, context: Context): BarData {
        var position = 0f
        val barEntries = ArrayList<BarEntry>()
        paymentList.forEach {
            barEntries.add(BarEntry(position, it.toFloat()))
            position += 1f
        }
        val barDataSet = BarDataSet(barEntries, "Payments")

        barDataSet.valueTextColor = ContextCompat.getColor(context, R.color.material_grey)
        barDataSet.color = ContextCompat.getColor(context, R.color.material_dark_green_dark)
        barDataSet.colors = CustomUtils.getColorsFromTemplate()

        return BarData(barDataSet)
    }


    /**
     * This method will create a PieData object based on the payment list
     * @param paymentList of [Int] [List] and context of [Context]
     * @return [PieData]
     */
    fun getPieData(paymentList: List<Int>, context: Context): PieData {

        val pieEntries = ArrayList<PieEntry>()

        var position = 0f
        paymentList.forEach {
            if (it > 0) {
                pieEntries.add(PieEntry(it.toFloat(),
                        CustomUtils.getCurrentMonthName(position.toInt())))
            }
            position += 1f
        }
        val pieDataSet = PieDataSet(pieEntries, "payments")
        pieDataSet.valueTextColor = ContextCompat.getColor(context, R.color.material_grey)
        pieDataSet.color = ContextCompat.getColor(context, R.color.material_dark_green_dark)
        pieDataSet.colors = CustomUtils.getColorsFromTemplate()

        return PieData(pieDataSet)
    }
}