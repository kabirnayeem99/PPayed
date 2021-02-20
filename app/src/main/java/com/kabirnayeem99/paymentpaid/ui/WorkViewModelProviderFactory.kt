package com.kabirnayeem99.paymentpaid.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kabirnayeem99.paymentpaid.data.repositories.WorkRepository

/**
 * [WorkViewModelProviderFactory] is an interface which have [create] method.
 * The create method is responsible for creating our [WorkViewModel] instance.
 * @param repository - [WorkRepository]
 * This passes this repository parameter to the WorkViewModel
 */
class WorkViewModelProviderFactory(private val repository: WorkRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WorkViewModel(repository) as T
    }

}