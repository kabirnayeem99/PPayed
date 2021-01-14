package com.kabirnayeem99.paymentpaid.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.kabirnayeem99.paymentpaid.WorkDatabase;
import com.kabirnayeem99.paymentpaid.models.Work;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseUtils extends SQLiteOpenHelper {

    public static final String DB_WORK_TABLE = "works_db_table";
    public static final int DB_VERSION = 2;
    public static final char SINGLE_QUOTE = '"';
    private static final String TAG = "DatabaseHelper";
    private static final String KEY_ID = "id";
    private static final String KEY_STUDENT_NAME = "student_name";
    private static final String KEY_WORK_NAME = "work_name";
    private static final String KEY_PAYMENT = "payment";
    private static final String KEY_DATE = "submission_date";


    public DatabaseUtils(Context context) {
        super(context, WorkDatabase.DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Calls when the database is created for the first time.

        // CREATE TABLE DB_WORK_TABLE(id INT PRIMARY KEY, student TEXT, work_name
        // TEXT, INT payment, date TEXT);

        String dbCreateQuery = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT, " +
                        "%s TEXT, %s INT , %s TEXT)",
                DB_WORK_TABLE, KEY_ID, KEY_STUDENT_NAME, KEY_WORK_NAME, KEY_PAYMENT, KEY_DATE);

        db.execSQL(dbCreateQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Calls when the database needs to be upgraded.

        if (oldVersion >= newVersion) {
            // if no upgrade is needed, the method returns nothing
            return;
        }

        // if the table needs upgrade, drops the table, and creates new database
        String dbUpgradeQuery = String.format("DROP TABLE IF EXISTS %s", DB_WORK_TABLE);
        db.execSQL(dbUpgradeQuery);
        onCreate(db);
    }


    public long addToWork(Work work) {
        // adds new work to the database

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        if (work.getDate() != null && work.getPayment() > 100) {
            contentValues.put(KEY_WORK_NAME, work.getName());
            contentValues.put(KEY_DATE, work.getDate());
            contentValues.put(KEY_PAYMENT, work.getPayment());
            contentValues.put(KEY_STUDENT_NAME, work.getStudentName());

            /*
            should return a positive value, which will the new primary key
            of the database
             */
            return db.insert(DB_WORK_TABLE, null, contentValues);
        }

        db.close();

        /*
        if the adding new work to database fails
        return negative value
        */
        return -1;

    }

    public List<Work> getWorkList() {
        /*
         gets the work list from the database
         and returns as array list
         */

        List<Work> workList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        // SELECT * from works_db_table;
        String selectNoteQuery = String.format("SELECT * FROM %s", DB_WORK_TABLE);

        try (Cursor cursor = db.rawQuery(selectNoteQuery, null)) {
            // creates a work array list
            if (cursor.moveToFirst()) {
                do {
                    // creates a new instance of work
                    Work work = new Work();

                    // sets the value of the new work object
                    work.setStudentName(cursor.getString(1));
                    work.setName(cursor.getString(2));
                    work.setPayment(cursor.getInt(3));
                    work.setDate(cursor.getString(4));

                    // adds the new work object to the array list
                    workList.add(work);
                } while (cursor.moveToNext());
            }
        }
        db.close();

        return workList;
    }

// --Commented out by Inspection START (1/9/2021 1:12 PM):
//    public List<Work> getWorkListSortedByMonth(Integer month) {
//        List<Work> workList = this.getWorkList();
//        List<Work> workListByMonth = new ArrayList<>();
//
//
//        for (int i = 0; i < workList.size(); i++) {
//
//            if (Utils.checkDate(workList.get(i).getDate(), month, 2021)) {
//                workListByMonth.add(workList.get(i));
//            }
//        }
//        Log.d(TAG, "getWorkListSortedByMonth: " + workListByMonth);
//
//        return workListByMonth;
//    }
// --Commented out by Inspection STOP (1/9/2021 1:12 PM)

    public String getTotalPaymentByYear() {
        // gets the total payment using the database

        int totalPayment = 0;

        SQLiteDatabase db = getReadableDatabase();

        // SELECT SUM(payment) FROM works_db_table
        String selectNoteQuery = String.format("SELECT SUM(%s) FROM %s WHERE %s LIKE %s%s%%%s",
                KEY_PAYMENT, DB_WORK_TABLE, KEY_DATE, SINGLE_QUOTE, CustomUtils.getCurrentYear(), SINGLE_QUOTE);

        Log.d(TAG, "getTotalPaymentByYear: current year" + CustomUtils.getCurrentYear());

        try (Cursor cursor = db.rawQuery(selectNoteQuery, null)) {
            if (cursor.moveToFirst()) {
                // there is only one column and one row in the result table
                totalPayment = cursor.getInt(0);
            }
        }

        db.close();

        return String.format("%s", totalPayment);
    }

    public Map<Integer, Integer> getTotalPaymentByMonth() {
        // a method to return a hash map including both the month and the year

        Map<Integer, Integer> totalPayment = new HashMap<>();

        int monthlyPayment = 0;

        // quotes to help with the string malformation in SQLite query


        SQLiteDatabase db = getReadableDatabase();


        for (int month = 1; month <= 12; month++) {
            // SELECT SUM(payment) FROM works_db_table WHERE date LIKE "2021-01-%%"

            String selectNoteQueryByMonth = String.format("SELECT SUM(%s) FROM %s WHERE %s LIKE %s2021-%s-%%%s",
                    KEY_PAYMENT, DB_WORK_TABLE, KEY_DATE, SINGLE_QUOTE, CustomUtils.padMonth(month), SINGLE_QUOTE);
            Log.d(TAG, "getTotalPaymentByMonth: Query" + selectNoteQueryByMonth);

            try (Cursor cursor = db.rawQuery(selectNoteQueryByMonth, null)) {
                if (cursor.moveToFirst()) {
                    monthlyPayment = cursor.getInt(0);
                }
            }
            totalPayment.put(month, monthlyPayment);
            Log.d(TAG, String.format("getTotalPaymentByMonth: month= %d  payment= %d", month, monthlyPayment));
        }

        db.close();

        return totalPayment;
    }

}