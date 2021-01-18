package com.kabirnayeem99.paymentpaid.data.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.kabirnayeem99.paymentpaid.data.db.WorkDatabase;
import com.kabirnayeem99.paymentpaid.data.db.entities.Work;
import com.kabirnayeem99.paymentpaid.utils.CustomUtils;
import com.kabirnayeem99.paymentpaid.data.db.WorkDao;

import java.util.List;

public class WorkRepository {
    private final WorkDao workDao;
    private final LiveData<List<Work>> allWorks;
    private final LiveData<List<Integer>> monthlyPayment;


    public WorkRepository(Application application) {
        WorkDatabase workDatabase = WorkDatabase.getInstance(application);
        this.workDao = workDatabase.workDao();
        this.allWorks = workDao.getAllWorks();
        this.monthlyPayment = workDao.getTotalPaymentByMonth(CustomUtils.getCurrentYear());
    }

    public void insert(Work work) {
        new InsertWorkAsyncTask(workDao).execute(work);
    }

    public void update(Work work) {
        new UpdateWorkAsyncTask(workDao).execute(work);
    }

    public void delete(Work work) {
        new DeleteWorkAsyncTask(workDao).execute(work);
    }

    public LiveData<List<Work>> getAllWorks() {
        return allWorks;
    }

    public LiveData<List<Integer>> getTotalPaymentByMonth() {
        return monthlyPayment;
    }

    public LiveData<Integer> getTotalPaymentByYear() {
        return workDao.getTotalPaymentByYear(CustomUtils.getCurrentYear());
    }

    public static class InsertWorkAsyncTask extends AsyncTask<Work, Void, Void> {
        private final WorkDao workDao;

        public InsertWorkAsyncTask(WorkDao workDao) {
            this.workDao = workDao;
        }

        @Override
        protected Void doInBackground(Work... works) {
            workDao.insert(works[0]);
            return null;
        }
    }

    public static class UpdateWorkAsyncTask extends AsyncTask<Work, Void, Void> {
        private final WorkDao workDao;

        public UpdateWorkAsyncTask(WorkDao workDao) {
            this.workDao = workDao;
        }

        @Override
        protected Void doInBackground(Work... works) {
            workDao.update(works[0]);
            return null;
        }
    }

    public static class DeleteWorkAsyncTask extends AsyncTask<Work, Void, Void> {
        private final WorkDao workDao;

        public DeleteWorkAsyncTask(WorkDao workDao) {
            this.workDao = workDao;
        }

        @Override
        protected Void doInBackground(Work... works) {
            workDao.delete(works[0]);
            return null;
        }
    }

}
