package com.kabirnayeem99.paymentpaid.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "payment_paid_db";
    public static final String DB_WORK_TABLE = "works_db_table";
    private static final String KEY_ID = "id";
    private static final String KEY_STUDENT_NAME = "student_name";
    private static final String KEY_WORK_NAME = "work_description";
    private static final String KEY_PAYMENT = "payment";
    private static final String KEY_DATE = "date";
    public static int DB_VERSION = 2;

    public DatabaseHelper(Context context) {
        super(context, DatabaseHelper.DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // CREATE TABLE DB_WORK_TABLE(id INT PRIMARY KEY, student TEXT, work_name TEXT, INT payment, date TEXT);

        String dbCreateQuery = String.format("CREATE TABLE %s(%s INT PRIMARY KEY, %s TEXT, " +
                        "%s TEXT, %s INT , %s TEXT)",
                DB_WORK_TABLE, KEY_ID, KEY_STUDENT_NAME, KEY_WORK_NAME, KEY_PAYMENT, KEY_DATE);

        db.execSQL(dbCreateQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion >= newVersion) {
            return;
        }

        String dbUpgradeQuery = String.format("DROP TABLE IF EXISTS %s", DB_WORK_TABLE);
        db.execSQL(dbUpgradeQuery);
        onCreate(db);
    }
}