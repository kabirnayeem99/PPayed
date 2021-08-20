package com.kabirnayeem99.paymentpaid.domain.repositories

import androidx.lifecycle.LiveData
import com.kabirnayeem99.paymentpaid.domain.models.Work

/**
 * Repository interface that defines all the functions
 * of the repository class
 */
interface WorkRepository {
    fun saveWork(work: Work)
    fun getWorksList(): LiveData<List<Work>>
    fun deleteWork(work: Work)
    fun getPaymentListByMonth(): LiveData<List<Long>>
    fun getTotalPaymentsByYear(): LiveData<Long>
}