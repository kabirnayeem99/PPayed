package com.kabirnayeem99.paymentpaid.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kabirnayeem99.paymentpaid.domain.models.Work
import com.kabirnayeem99.paymentpaid.domain.repositories.WorkRepository
import com.kabirnayeem99.paymentpaid.other.Utils

class FakeWorkRepository : WorkRepository {

    private val works = mutableListOf<Work>()
    private var paymentsByMonth = mutableListOf<Long>()
    private var paymentByYear = 0L
    private val observableWorkList = MutableLiveData<List<Work>>(works)
    private val observablePaymentListByMonth = MutableLiveData<List<Long>>(paymentsByMonth)
    private val observablePaymentByYear = MutableLiveData<Long>(paymentByYear)


    /**
     * Refreshes the live data manually with change to the work list
     */
    private fun refreshLiveData() {
        observableWorkList.postValue(works)
        observablePaymentListByMonth.postValue(paymentsByMonth)
        observablePaymentByYear.postValue(paymentByYear)
    }


    /**
     * Saves work or inserts work to the list
     * and if it is current year adds to the current payment by year
     * and if it is month add to the month list as well
     */
    override fun saveWork(work: Work) {
        works.add(work)
        if (work.year == Utils.currentYear.toLong()) {
            paymentByYear += work.payment
        }
        refreshLiveData()
    }

    override fun getWorksList(): LiveData<List<Work>> {
        return observableWorkList
    }

    /**
     * Deletes work or removes work to the list
     * and if it is current year deletes from the current payment by year
     * and if it is month delete from the month list as well
     */
    override fun deleteWork(work: Work) {
        works.remove(work)
        if (work.year == Utils.currentYear.toLong()) {
            paymentByYear -= work.payment
        }
        refreshLiveData()
    }

    override fun getPaymentListByMonth(): LiveData<List<Long>> {
        return observablePaymentListByMonth
    }

    override fun getTotalPaymentsByYear(): LiveData<Long> {
        return observablePaymentByYear
    }
}