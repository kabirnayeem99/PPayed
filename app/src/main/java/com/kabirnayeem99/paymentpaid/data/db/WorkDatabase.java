package com.kabirnayeem99.paymentpaid.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.kabirnayeem99.paymentpaid.data.db.entities.Work;

@Database(entities = {Work.class}, version = 1, exportSchema = false)
public abstract class WorkDatabase extends RoomDatabase {
    public static final String DB_NAME = "payment_paid_db";
    public static WorkDatabase instance;


    public static WorkDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    WorkDatabase.class, WorkDatabase.DB_NAME).fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract WorkDao workDao();
}
