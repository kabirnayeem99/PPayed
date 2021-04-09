package com.kabirnayeem99.paymentpaid.ui

import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import com.kabirnayeem99.paymentpaid.data.db.entities.Work
import com.kabirnayeem99.paymentpaid.data.db.entities.Work.Companion.toWork
import com.kabirnayeem99.paymentpaid.data.repositories.FirebaseRepo
import com.kabirnayeem99.paymentpaid.data.repositories.Repo
import com.kabirnayeem99.paymentpaid.other.Utils
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


class FirestoreViewModel(private val repo: Repo) : ViewModel() {

    private val TAG = "FirestoreViewModel"

    private val _workList = repo.getWorksList()
    val workList: LiveData<List<Work>> = _workList
    private val _paymentList = repo.getPaymentListByMonth()
    val paymentList: LiveData<List<Long>> = _paymentList
    private val _paymentOfCurrentYear = repo.getTotalPaymentsByYear()
    val paymentOfCurrentYear: LiveData<Long> = _paymentOfCurrentYear


    fun saveWork(work: Work) {
        repo.saveWork(work)
    }

    fun delete(work: Work) {
        repo.deleteWork(work)
    }


}