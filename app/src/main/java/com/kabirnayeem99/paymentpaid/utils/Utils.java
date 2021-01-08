package com.kabirnayeem99.paymentpaid.utils;

import android.util.Log;

import java.text.DecimalFormat;

public class Utils {
    private static final String TAG = "Utils";

// --Commented out by Inspection START (1/8/2021 4:36 PM):
//    public static Date formatStringToDate(String date) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
//        try {
//            return sdf.parse(date);
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return new Date();
//        }
//    }
// --Commented out by Inspection STOP (1/8/2021 4:36 PM)

    public static boolean checkDate(String date, int month, int year) {
        //generated from [https://www.regular-expressions.info/dates.html]
        // tested in [https://regex101.com/]
        String regex = String.format("^(20)\\2\\1([- \\/.])(%s)\\2(0[1-9]|[12][0-9]|3[01])$", month);
        Log.d(TAG, "checkDate: " + regex);
        return date.matches(regex);
    }

    public static String padMonth(int month) {
        StringBuilder output = new StringBuilder(Integer.toString(month));
        if (output.length() < 2) {
            output.insert(0, "0");
        }
        Log.d(TAG, "padMonth: " + output);
        return output.toString();
    }

    public static String formatNumber(String number) {
        if (number.length() <= 3) {
            return number.toString();
        } else {
            double amount = Double.parseDouble(number);
            DecimalFormat formatter = new DecimalFormat("#,###");
            number = formatter.format(amount);
        }
        return number.toString();
    }

}
