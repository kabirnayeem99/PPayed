package com.kabirnayeem99.paymentpaid.data.remote_repo

import androidx.lifecycle.LiveData
import com.kabirnayeem99.paymentpaid.data.db.WorkDatabase
import com.kabirnayeem99.paymentpaid.data.db.entities.Work

class FirebaseService {
    suspend fun insert(work: Work) {}

    suspend fun update(work: Work) {}

    suspend fun delete(work: Work) {}

//    fun getAllWorks(): LiveData<List<Work>> {
//
//    }
//
//
//    fun getTotalPaymentByMonth(year: Int): LiveData<List<Int>> {
//    }
//
//    fun getTotalPaymentByYear(year: Int): LiveData<Int> {}

}