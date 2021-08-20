package com.kabirnayeem99.paymentpaid.domain.repositories

import androidx.lifecycle.LiveData
import com.kabirnayeem99.paymentpaid.domain.models.Work
import com.kabirnayeem99.paymentpaid.other.Resource

/**
 * Repository interface that defines all the functions
 * of the repository class
 */
interface WorkRepository {
    fun saveWork(work: Work): Resource<String>
    fun getWorksList(): LiveData<Resource<List<Work>>>
    fun deleteWork(work: Work): Resource<String>
    fun getPaymentListByMonth(): LiveData<Resource<List<Long>>>
    fun getTotalPaymentsByYear(): LiveData<Resource<Long>>
}