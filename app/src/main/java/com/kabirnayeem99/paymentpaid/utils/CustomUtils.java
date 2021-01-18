package com.kabirnayeem99.paymentpaid.utils;

import android.util.Log;

import java.text.DecimalFormat;
import java.time.Month;
import java.util.Calendar;

public class CustomUtils {
    private static final String TAG = "Utils";



    public static String padMonth(int month) {
        StringBuilder output = new StringBuilder(Integer.toString(month));
        if (output.length() < 2) {
            output.insert(0, "0");
        }
        Log.d(TAG, "padMonth: " + output);
        return output.toString();
    }

    public static String formatMoney(String number) {
        if (number.length() <= 3) {
            return String.format("৳%s", number);
        } else {
            double amount = Double.parseDouble(number);
            DecimalFormat formatter = new DecimalFormat("#,###");
            number = formatter.format(amount);
        }
        return String.format("৳%s", number);
    }

    public static Integer getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static String getCurrentMonthName(int position) {
        Month[] month = Month.values();
        return month[position].toString();
    }

    public static int getCurrentMonth() {
        return Calendar.getInstance().get(Calendar.MONTH);
    }


    public static int getCurrentDay() {
        return Calendar.getInstance().get(Calendar.DATE);
    }
}
