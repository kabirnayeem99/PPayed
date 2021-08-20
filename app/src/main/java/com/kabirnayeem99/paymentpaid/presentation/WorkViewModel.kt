package com.kabirnayeem99.paymentpaid.presentation

import androidx.lifecycle.*
import com.kabirnayeem99.paymentpaid.domain.models.Work
import com.kabirnayeem99.paymentpaid.domain.repositories.WorkRepository
import com.kabirnayeem99.paymentpaid.other.Resource
import javax.inject.Inject


class WorkViewModel @Inject constructor(var workRepository: WorkRepository) : ViewModel() {

    private val _workList = workRepository.getWorksList()
    val workList: LiveData<Resource<List<Work>>> = _workList
    private val _paymentListByMonth = workRepository.getPaymentListByMonth()
    val paymentListByMonth: LiveData<Resource<List<Long>>> = _paymentListByMonth
    private val _paymentOfCurrentYear = workRepository.getTotalPaymentsByYear()
    val paymentOfCurrentYear: LiveData<Resource<Long>> = _paymentOfCurrentYear


    fun saveWork(work: Work) = workRepository.saveWork(work)


    fun deleteWork(work: Work) = workRepository.deleteWork(work)


}