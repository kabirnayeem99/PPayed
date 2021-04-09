package com.kabirnayeem99.paymentpaid.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kabirnayeem99.paymentpaid.data.db.entities.Work

class FakeRepo : Repo {

    private val works = mutableListOf<Work>()
    private val paymentsByMonth = mutableListOf<Long>()
    private val paymentByYear = 0L
    private val observableWorkList = MutableLiveData<List<Work>>(works)
    private val observablePaymentListByMonth = MutableLiveData<List<Long>>()
    private val observablePaymentByYear = MutableLiveData<Long>()


    private fun refreshLiveData() {
        observableWorkList.postValue(works)
        observablePaymentListByMonth.postValue(paymentsByMonth)
        observablePaymentByYear.postValue(paymentByYear)
    }

    override fun saveWork(work: Work) {
        works.add(work)
        refreshLiveData()
    }

    override fun getWorksList(): LiveData<List<Work>> {
        return observableWorkList
    }

    override fun deleteWork(work: Work) {
        works.remove(work)
        refreshLiveData()
    }

    override fun getPaymentListByMonth(): LiveData<List<Long>> {
        return observablePaymentListByMonth
    }

    override fun getTotalPaymentsByYear(): LiveData<Long> {
        return observablePaymentByYear
    }
}