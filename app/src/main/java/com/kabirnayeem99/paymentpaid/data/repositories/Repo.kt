package com.kabirnayeem99.paymentpaid.data.repositories

import androidx.lifecycle.LiveData
import com.kabirnayeem99.paymentpaid.data.db.entities.Work

interface Repo {
    fun saveWork(work: Work)
    fun getWorksList(): LiveData<List<Work>>
    fun deleteWork(work: Work)
    fun getPaymentListByMonth(): LiveData<List<Long>>
    fun getTotalPaymentsByYear(): LiveData<Long>
}