package com.kabirnayeem99.paymentpaid.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


/**
 * [LogInRegisterViewModelProviderFactory] is an interface which have [create] method.
 * The create method is responsible for creating our [LogInRegisterViewModel] instance.
 * @param application - [Application]
 * This passes this repository parameter to the LogInRegisterViewModel
 */
class LogInRegisterViewModelProviderFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LogInRegisterViewModel(application) as T
    }

}