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
    private final LiveData<List<Integer>> monthlyPayment;


    public WorkViewModel(@NonNull Application application) {
        super(application);
        repository = new WorkRepository(application);
        allWorks = repository.getAllWorks();
        totalPayment = repository.getTotalPaymentByYear();
        monthlyPayment = repository.getTotalPaymentByMonth();
    }

    public void insert(Work work) {
        repository.insert(work);
    }

    public void update(Work work) {
        repository.update(work);
    }

    public void delete(Work work) {
        repository.delete(work);
    }

    public LiveData<List<Work>> getAllWorks() {
        return allWorks;
    }

    public LiveData<List<Integer>> getTotalPaymentByMonth(int currentYear) {
        return monthlyPayment;
    }

    public LiveData<Integer> getTotalPaymentByYear() {
        return totalPayment;
    }
}
