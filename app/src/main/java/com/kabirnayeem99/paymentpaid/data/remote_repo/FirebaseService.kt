package com.kabirnayeem99.paymentpaid.data.remote_repo

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kabirnayeem99.paymentpaid.data.db.WorkDatabase
import com.kabirnayeem99.paymentpaid.data.db.entities.Work
import com.kabirnayeem99.paymentpaid.utils.Constants
import kotlinx.coroutines.tasks.await

class FirebaseService {

    var workCollectionReference = Firebase.firestore
            .collection(
                    Constants.DB_NAME
            )
            .document(
                    FirebaseAuth.getInstance().currentUser.toString()
            ).collection(
                    Constants.DB_TABLE
            )

    suspend fun insert(work: Work) {
        workCollectionReference.add(work).await()
    }

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