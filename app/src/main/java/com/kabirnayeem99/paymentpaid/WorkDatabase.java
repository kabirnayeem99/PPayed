package com.kabirnayeem99.paymentpaid;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.kabirnayeem99.paymentpaid.models.PaymentMonth;
import com.kabirnayeem99.paymentpaid.models.Work;
import com.kabirnayeem99.paymentpaid.utils.WorkDao;

@Database(entities = {Work.class, PaymentMonth.class}, version = 1, exportSchema = false)
public abstract class WorkDatabase extends RoomDatabase {
    public static final String DB_NAME = "payment_paid_db";
    public static WorkDatabase instance;
    private static final RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDatabaseAsyncTask(instance).execute();
        }
    };

    public static WorkDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    WorkDatabase.class, WorkDatabase.DB_NAME).fallbackToDestructiveMigration()
                    .addCallback(roomCallback).build();
        }
        return instance;
    }

    public abstract WorkDao workDao();

    private static class PopulateDatabaseAsyncTask extends AsyncTask<Void, Void, Void> {
        private final WorkDao workDao;

        public PopulateDatabaseAsyncTask(WorkDatabase db) {
            this.workDao = db.workDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            workDao.insert(new Work("Maria MSBP", "2021-08-19", 4600, "Maria Suleka"));
            workDao.insert(new Work("Anne SRMD", "2021-01-19", 3577, "Anne Greene"));
            workDao.insert(new Work("Kristy CVYM", "2021-08-19", 2256, "Kristy May "));
            workDao.insert(new Work("Justin MSBP", "2021-04-19", 45445, "Justin Griffin"));
            workDao.insert(new Work("Maria MSBP", "2021-02-19", 785412, "Maria Suleka"));
            return null;
        }
    }
}
