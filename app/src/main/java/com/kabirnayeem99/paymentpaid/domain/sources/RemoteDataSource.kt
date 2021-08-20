package com.kabirnayeem99.paymentpaid.domain.sources

import androidx.lifecycle.LiveData
import com.kabirnayeem99.paymentpaid.domain.models.Work

interface RemoteDataSource {
    fun saveWork(work: Work)
    fun getWorksList(): LiveData<List<Work>>
    fun deleteWork(work: Work)
    fun getPaymentListByMonth(): LiveData<List<Long>>
    fun getTotalPaymentsByYear(): LiveData<Long>
}