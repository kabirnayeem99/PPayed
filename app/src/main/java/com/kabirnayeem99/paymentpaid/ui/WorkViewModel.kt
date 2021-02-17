package com.kabirnayeem99.paymentpaid.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kabirnayeem99.paymentpaid.data.db.entities.Work
import com.kabirnayeem99.paymentpaid.data.repositories.WorkRepository
import com.kabirnayeem99.paymentpaid.utils.Resource
import kotlinx.coroutines.launch

class WorkViewModel(private val repository: WorkRepository) : ViewModel() {


    fun insert(work: Work) = viewModelScope.launch {
        repository.insert(work)
    }

    fun update(work: Work) = viewModelScope.launch {
        repository.update(work)
    }

    fun delete(work: Work) = viewModelScope.launch {
        repository.delete(work)
    }


    fun getAllWorks() = repository.getAllWorks()
    fun getTotalPaymentsByMonth() = repository.getTotalPaymentByMonth()
    fun getTotalPaymentByYear() = repository.getTotalPaymentByYear()

}