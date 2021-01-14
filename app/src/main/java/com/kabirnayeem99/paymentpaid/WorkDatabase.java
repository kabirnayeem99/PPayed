package com.kabirnayeem99.paymentpaid;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.kabirnayeem99.paymentpaid.models.PaymentMonth;
import com.kabirnayeem99.paymentpaid.models.Work;
import com.kabirnayeem99.paymentpaid.utils.WorkDao;

@Database(entities = {Work.class, PaymentMonth.class}, version = 1)
public abstract class WorkDatabase extends RoomDatabase {
    public static final String DB_NAME = "payment_paid_db";
    public static WorkDatabase instance;

    public static WorkDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    WorkDatabase.class, WorkDatabase.DB_NAME).build();
        }
        return instance;
    }

    public abstract WorkDao workDao();
}
