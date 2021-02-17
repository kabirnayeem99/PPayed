package com.kabirnayeem99.paymentpaid.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kabirnayeem99.paymentpaid.data.db.entities.Work
import com.kabirnayeem99.paymentpaid.data.repositories.WorkRepository
import com.kabirnayeem99.paymentpaid.utils.Resource
import kotlinx.coroutines.launch

class WorkViewModel(private val repository: WorkRepository) : ViewModel() {

    val totalPaymentByYear: MutableLiveData<Resource<Int>> = MutableLiveData()
    val totalPaymentByMonth: MutableLiveData<Resource<List<Int>>> = MutableLiveData()
    val allWorks: MutableLiveData<Resource<List<Work>>> = MutableLiveData()

    fun insert(work: Work) = viewModelScope.launch {
        repository.insert(work)
    }

    fun update(work: Work) = viewModelScope.launch {
        repository.update(work)
    }

    fun delete(work: Work) = viewModelScope.launch {
        repository.delete(work)
    }

    fun getTotalPaymentByYear() = viewModelScope.launch {
        totalPaymentByYear.postValue(Resource.Success(repository.getTotalPaymentByYear()))
    }


    suspend fun getTotalPaymentByMonth() = viewModelScope.launch {

        totalPaymentByMonth.postValue(Resource.Loading())

        val response = repository.getTotalPaymentByMonth()

        totalPaymentByMonth.postValue(handleTotalPaymentByMonth(response))

    }


    private fun handleTotalPaymentByMonth(response: List<Int>): Resource<List<Int>> {
        if (response.isEmpty()) {
            return Resource.Error("The list was empty")
        }

        return Resource.Success(response)
    }

    suspend fun getAllWorks() = viewModelScope.launch {

        allWorks.postValue(Resource.Loading())


        allWorks.postValue(Resource.Success(repository.getAllWorks()))


    }

}