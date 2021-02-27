package com.kabirnayeem99.paymentpaid.utils

import android.graphics.Color
import android.util.Log
import com.github.mikephil.charting.utils.ColorTemplate
import java.text.DecimalFormat
import java.time.Month
import java.util.*

/***
 * A set of utilities, that different classes use
 */
object CustomUtils {

    private const val TAG = "Utils"

    /**
     * This method adds a 0 before the month, if the month
     * is a single digit number, such as 08, 04
     * @param month which is an [Int]
     * @return month which is a [String]
     */
    fun padMonth(month: Int): String {
        val output = StringBuilder(month.toString())
        if (output.length < 2) {
            output.insert(0, "0")
        }
        Log.d(TAG, "padMonth: $output")
        return output.toString()
    }

    /**
     * This method adds a comma, after each three digit
     * to make this more readable
     * @param unformattedMoney which is a string of money, such as 20000
     * @return formattedMoney [String], such as 20,000
     */
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

    fun getRandomColor(): Int {
        val rnd = Random()
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }

    fun getColorsFromTemplate(): MutableList<Int> {
        val colorInts = ColorTemplate.MATERIAL_COLORS
        val colorMutableList: MutableList<Int> = ArrayList(colorInts.size)
        for (i in colorInts) {
            colorMutableList.add(i)
        }
        return colorMutableList
    }
}