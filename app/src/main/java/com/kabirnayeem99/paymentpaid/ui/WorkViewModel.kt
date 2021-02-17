package com.kabirnayeem99.paymentpaid.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kabirnayeem99.paymentpaid.data.db.entities.Work
import com.kabirnayeem99.paymentpaid.data.repositories.WorkRepository
import com.kabirnayeem99.paymentpaid.utils.CustomUtils

class WorkViewModel(private val repository: WorkRepository) : ViewModel() {


    suspend fun insert(work: Work) {
        repository.insert(work)
    }

    suspend fun update(work: Work) {
        repository.update(work)
    }

    suspend fun delete(work: Work) {
        repository.delete(work)
    }

    suspend fun getTotalPaymentByYear(): LiveData<Int> =
            repository.getTotalPaymentByYear()

    suspend fun getTotalPaymentByMonth(): LiveData<List<Int>> =
            repository.getTotalPaymentByMonth(CustomUtils.currentYear)

    suspend fun getAllWorks(): LiveData<List<Work>> =
            repository.getAllWorks()
}