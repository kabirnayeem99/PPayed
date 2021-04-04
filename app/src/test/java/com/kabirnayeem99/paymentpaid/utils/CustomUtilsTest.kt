package com.kabirnayeem99.paymentpaid.utils

import junit.framework.TestCase
import org.junit.Test

class CustomUtilsTest : TestCase() {


    /**
     * test padMonth() class with a single digit month like 1 or 4
     * and it should return something like 01 or 04.
     */
    @Test
    fun `test padMonth() with a single digit month`() {
        val param = 1L
        val expectedResult = "01"
        val result = CustomUtils.padMonth(param)
        assert(result == expectedResult)
    }


    /**
     * test padMonth() function with a double digit number, such as,
     * 12, 11
     * should return 12 or 11, as they are.
     */
    @Test
    fun `test padMonth() with a double digit month`() {
        val param = 12L
        val expectedResult = "12"
        val result = CustomUtils.padMonth(param)
        assert(result == expectedResult)
    }

    /**
     * tests formatMoney() with a billion digit number as function
     * such as, 1000000
     * should return something with one comma after 3 digit and a currency sign
     * before, like this -> ৳1,000,000
     */
    @Test
    fun `test formatMoney() with a billion digit number`() {
        val param = "1000000" //1,000,000
        val expectedResult = "৳1,000,000"
        val result = CustomUtils.formatMoney(param)
        assert(result == expectedResult)
    }

    /**
     * tests formatMoney() function with a hundred digit number,
     * such as, 100
     * should return something without any comma, like -> ৳100
     */
    @Test
    fun `test formatMoney with a hundred digit number`() {
        val param = "100" //1000
        val expectedResult = "৳100"
        val result = CustomUtils.formatMoney(param)
        assert(result == expectedResult)
    }


    /**
     * Test getCurrentMonthName class
     * which returns a month name, based on month number,
     * like -> 2 for FEBRUARY
     */
    @Test
    fun `test getCurrentMonthName() function`() {
        val param: Int = 9
        val expectedResult: String = "OCTOBER"
        val result: String = CustomUtils.getCurrentMonthName(param)
        assert(result == expectedResult)
    }
}