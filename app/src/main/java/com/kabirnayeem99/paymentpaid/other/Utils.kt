package com.kabirnayeem99.paymentpaid.other

import com.github.mikephil.charting.utils.ColorTemplate
import java.text.DecimalFormat
import java.time.Month
import java.util.*

/***
 * A set of utilities, that different classes use
 */
object Utils {

    /**
     * This method adds a 0 before the month, if the month
     * is a single digit number, such as 08, 04
     * @param month which is an [Int]
     * @return month which is a [String]
     */
    fun padMonth(month: Long): String {
        val output = StringBuilder(month.toString())
        if (output.length < 2) {
            output.insert(0, "0")
        }
        return output.toString()
    }

    /**
     * This method adds a comma, after each three digit
     * to make this more readable
     * @param unformattedMoney which is a string of money, such as 20000
     * @return formattedMoney [String], such as 20,000
     */
    fun formatMoney(unformattedMoney: String): String {
        val formattedMoney: String = unformattedMoney
        return if (formattedMoney.length <= 3) {
            String.format("৳%s", formattedMoney)
        } else {
            val amount = formattedMoney.toDouble()
            val formatter = DecimalFormat("#,###")
            val amountString = formatter.format(amount)
            String.format("৳%s", amountString)
        }
    }


    val currentYear: Int
        get() = Calendar.getInstance()[Calendar.YEAR]

    /***
     * @param position
     * gets a month position
     * the index starts from 0
     * so, something like OCTOBER is 9
     * returns a month name
     */
    fun getCurrentMonthName(position: Int): String {
        // the months are as index
        val month = Month.values()
        return month[position].toString()
    }

    val currentMonth: Int
        get() = Calendar.getInstance()[Calendar.MONTH]
    val currentDay: Int
        get() = Calendar.getInstance()[Calendar.DATE]

    fun getColorsFromTemplate(): MutableList<Int> {
        val colorInts = ColorTemplate.MATERIAL_COLORS
        val colorMutableList: MutableList<Int> = ArrayList(colorInts.size)
        for (i in colorInts) {
            colorMutableList.add(i)
        }
        return colorMutableList
    }

}