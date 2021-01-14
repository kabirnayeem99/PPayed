package com.kabirnayeem99.paymentpaid;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kabirnayeem99.paymentpaid.models.Work;

import java.util.List;

public class WorkViewModel extends AndroidViewModel {
    private final WorkRepository repository;
    private final LiveData<List<Work>> allWorks;
    private final LiveData<Integer> totalPayment;

    public WorkViewModel(@NonNull Application application) {
        super(application);
        repository = new WorkRepository(application);
        allWorks = repository.getAllWorks();
        totalPayment = repository.getTotalPaymentByYear();
    }

    void insert(Work work) {
        repository.insert(work);
    }

    void update(Work work) {
        repository.update(work);
    }

    void delete(Work work) {
        repository.delete(work);
    }

    LiveData<List<Work>> getAllWorks() {
        return allWorks;
    }

    int getTotalPaymentByMonth() {
        return 0;
    }

    LiveData<Integer> getTotalPaymentByYear() {
        return totalPayment;
    }
}
