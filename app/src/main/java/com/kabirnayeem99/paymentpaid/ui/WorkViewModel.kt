package com.kabirnayeem99.paymentpaid.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kabirnayeem99.paymentpaid.data.db.entities.Work
import com.kabirnayeem99.paymentpaid.data.repositories.WorkRepository
import com.kabirnayeem99.paymentpaid.utils.CustomUtils

class WorkViewModel(private val repository: WorkRepository) : ViewModel() {


    fun insert(work: Work) {
        repository.insert(work)
    }

    fun update(work: Work) {
        repository.update(work)
    }

    fun delete(work: Work) {
        repository.delete(work)
    }

    fun getTotalPaymentByYear(): LiveData<Int> =
            repository.getTotalPaymentByYear()

    fun getTotalPaymentByMonth(): LiveData<List<Int>> =
            repository.getTotalPaymentByMonth(CustomUtils.currentYear)

    fun getAllWorks(): LiveData<List<Work>> =
            repository.getAllWorks()
}