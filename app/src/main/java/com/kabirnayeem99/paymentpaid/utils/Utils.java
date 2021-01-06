package com.kabirnayeem99.paymentpaid.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {
    private static final String TAG = "Utils";

    public static Date formatStringToDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public static boolean checkDate(String date, int month, int year) {
        //generated from [https://www.regular-expressions.info/dates.html]
        // tested in [https://regex101.com/]
        String regex = String.format("^(20)\\2\\1([- \\/.])(%s)\\2(0[1-9]|[12][0-9]|3[01])$", month);
        return date.matches(regex);
    }

}
