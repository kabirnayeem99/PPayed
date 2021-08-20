package com.kabirnayeem99.paymentpaid.presentation.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kabirnayeem99.paymentpaid.domain.repositories.WorkRepository
import com.kabirnayeem99.paymentpaid.presentation.FirestoreViewModel
import java.lang.IllegalArgumentException

class FirestoreViewModelProviderFactory(private val workRepository: WorkRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FirestoreViewModel::class.java)) {
            return FirestoreViewModel(workRepository) as T
        }
        throw IllegalArgumentException("Couldn't construct firestore view model")
    }

}