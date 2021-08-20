package com.kabirnayeem99.paymentpaid.domain.repositories

import android.content.Context
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.PieData

interface ChartRepository {
    fun getBarData(paymentList: List<Long>, context: Context): BarData
    fun getPieData(paymentList: List<Long>, context: Context): PieData
}