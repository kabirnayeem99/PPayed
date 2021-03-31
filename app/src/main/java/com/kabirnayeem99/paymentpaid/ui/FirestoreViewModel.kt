package com.kabirnayeem99.paymentpaid.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import com.google.gson.Gson
import com.kabirnayeem99.paymentpaid.data.db.entities.Work
import com.kabirnayeem99.paymentpaid.data.db.entities.Work.Companion.toWork
import com.kabirnayeem99.paymentpaid.data.repositories.FirebaseRepo

class FirestoreViewModel : ViewModel() {

    private val TAG = "FirestoreViewModel"
    private val repo = FirebaseRepo()
    private val workList = MutableLiveData<List<Work>>()

    fun saveWork(work: Work) {
        repo.saveWork(work)?.addOnFailureListener { e ->
            Log.e(TAG, "saveWork: Failed to save work. \n $e")
        }?.addOnSuccessListener {
            Log.d(TAG, "saveWork: successfully work saved \n")
        }
    }

    fun getWorkList(): LiveData<List<Work>> {
        repo.getWorksList().addSnapshotListener(EventListener<QuerySnapshot> { value, error ->
            if (error != null) {
                Log.w(TAG, "getWorkList: failed to listen", error)
                return@EventListener
            }

            val tempWorkList = mutableListOf<Work>()

            for (doc in value!!) {
                Log.d(TAG, "getWorkList: the document is $doc\n")
//                val work = Gson().fromJson(doc.data.toString(), Work::class.java)
                val work = doc.toWork()
                if (work != null) {
                    Log.d(TAG, "getWorkList: the generated work is $work")
                    tempWorkList.add(work)
                }
            }

            workList.value = tempWorkList

        })

        return workList
    }

    fun delete(work: Work) {
        repo.deleteWork(work)?.addOnFailureListener { e ->
            Log.e(TAG, "delete: failed to delete work", e)
        }
    }

    fun getAllPaymentsByMont(): LiveData<List<Int>> {
        return MutableLiveData<List<Int>>()
    }

    fun getTotalPaymentByYear(): LiveData<Int> {
        return MutableLiveData<Int>()
    }
}