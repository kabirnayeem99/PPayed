package com.kabirnayeem99.paymentpaid.ui.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kabirnayeem99.paymentpaid.data.repositories.FirebaseRepo
import com.kabirnayeem99.paymentpaid.data.repositories.Repo
import com.kabirnayeem99.paymentpaid.ui.FirestoreViewModel
import java.lang.IllegalArgumentException

class FirestoreViewModelProviderFactory(private val repo: Repo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FirestoreViewModel::class.java)) {
            return FirestoreViewModel(repo) as T
        }
        throw IllegalArgumentException("Couldn't construct firestore view model")
    }

}