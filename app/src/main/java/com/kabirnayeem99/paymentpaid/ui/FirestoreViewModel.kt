package com.kabirnayeem99.paymentpaid.ui

import androidx.lifecycle.*
import com.kabirnayeem99.paymentpaid.data.db.entities.Work
import com.kabirnayeem99.paymentpaid.data.repositories.Repo


class FirestoreViewModel(private val repo: Repo) : ViewModel() {

    private val _workList = repo.getWorksList()
    val workList: LiveData<List<Work>> = _workList
    private val _paymentListByMonth = repo.getPaymentListByMonth()
    val paymentListByMonth: LiveData<List<Long>> = _paymentListByMonth
    private val _paymentOfCurrentYear = repo.getTotalPaymentsByYear()
    val paymentOfCurrentYear: LiveData<Long> = _paymentOfCurrentYear


    fun saveWork(work: Work) {
        repo.saveWork(work)
    }

    fun delete(work: Work) {
        repo.deleteWork(work)
    }


}