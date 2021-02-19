package com.kabirnayeem99.paymentpaid.utils

import android.util.Log
import java.text.DecimalFormat
import java.time.Month
import java.util.*

/***
 * A set of utilities
 */
object CustomUtils {

    private const val TAG = "Utils"


    fun padMonth(month: Int): String {
        val output = StringBuilder(month.toString())
        if (output.length < 2) {
            output.insert(0, "0")
        }
        Log.d(TAG, "padMonth: $output")
        return output.toString()
    }

    fun formatMoney(unformattedMoney: String): String {
        var formattedMoney: String = unformattedMoney
        formattedMoney = if (formattedMoney.length <= 3) {
            return String.format("৳%s", formattedMoney)
        } else {
            val amount = formattedMoney.toDouble()
            val formatter = DecimalFormat("#,###")
            formatter.format(amount)
        }
        return String.format("৳%s", formattedMoney)
    }

    val currentYear: Int
        get() = Calendar.getInstance()[Calendar.YEAR]

    /***
     * @param position
     * gets a month position
     * returns a month name
     */
    fun getCurrentMonthName(position: Int): String {
        val month = Month.values()
        return month[position].toString()
    }

    val currentMonth: Int
        get() = Calendar.getInstance()[Calendar.MONTH]
    val currentDay: Int
        get() = Calendar.getInstance()[Calendar.DATE]
}