package com.kabirnayeem99.paymentpaid.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kabirnayeem99.paymentpaid.data.repositories.WorkRepository

class WorkViewModelProviderFactory(private val repository: WorkRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WorkViewModel(repository) as T
    }

}