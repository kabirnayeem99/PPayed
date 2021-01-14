package com.kabirnayeem99.paymentpaid;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.kabirnayeem99.paymentpaid.models.Work;
import com.kabirnayeem99.paymentpaid.utils.WorkDao;

import java.util.List;

public class WorkRepository {
    private final WorkDao workDao;
    private final LiveData<List<Work>> allWorks;

    public WorkRepository(Application application) {
        WorkDatabase workDatabase = WorkDatabase.getInstance(application);
        this.workDao = workDatabase.workDao();
        this.allWorks = workDao.getAllWorks();
    }

    void insert(Work work) {
        new InsertWorkAsyncTask(workDao).execute(work);
    }

    void update(Work work) {
        new UpdateWorkAsyncTask(workDao).execute(work);
    }

    void delete(Work work) {
        new DeleteWorkAsyncTask(workDao).execute(work);
    }

    LiveData<List<Work>> getAllWorks() {
        return allWorks;
    }

    int getTotalPaymentByMonth() {
        return 0;
    }

    LiveData<Integer> getTotalPaymentByYear() {
        return workDao.getTotalPaymentByYear();
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
