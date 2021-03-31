package com.kabirnayeem99.paymentpaid.ui

import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import com.kabirnayeem99.paymentpaid.data.db.entities.Work
import com.kabirnayeem99.paymentpaid.data.db.entities.Work.Companion.toWork
import com.kabirnayeem99.paymentpaid.data.repositories.FirebaseRepo
import com.kabirnayeem99.paymentpaid.utils.CustomUtils
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


class FirestoreViewModel : ViewModel() {

    private val TAG = "FirestoreViewModel"
    private val repo = FirebaseRepo()
    private val _workList = MutableLiveData<List<Work>>()
    val workList: LiveData<List<Work>> = _workList
    private val _paymentList = MutableLiveData<List<Long>>()
    val paymentList: LiveData<List<Long>> = _paymentList
    private val _paymentOfCurrentYear = MutableLiveData<Long>()
    val paymentOfCurrentYear: LiveData<Long> = _paymentOfCurrentYear

    init {
        getWorksList()
        getPaymentListByMonth().also {
            getTotalPaymentByYear()
        }
    }


    fun saveWork(work: Work) {
        repo.saveWork(work)?.addOnFailureListener { e ->
            Log.e(TAG, "saveWork: Failed to save work. \n $e")
        }?.addOnSuccessListener {
            Log.d(TAG, "saveWork: successfully work saved \n")
        }
    }

    private fun getWorksList() = viewModelScope.launch {

        repo.getWorksList().addSnapshotListener(
                EventListener<QuerySnapshot> { value, error ->

                    if (error != null) {
                        cancel(cause = error, message = "getWorksList: failed to fetch works")
                        return@EventListener
                    }

                    val temp = mutableListOf<Work>()

                    for (doc in value!!) {
                        doc.toWork()?.let { temp.add(it) }
                        Log.d(TAG, "getWorkList:  the documents in json form: $doc")
                    }

                    _workList.value = temp
                }

        )

    }


    private fun getPaymentListByMonth() = viewModelScope.launch {


        repo.getWorksList().addSnapshotListener(EventListener<QuerySnapshot> { value, error ->

            if (error != null) {
                cancel(message = "getPaymentListByMonth: could't get your dates", cause = error)
                return@EventListener
            }


            var jan: Long = 0
            var feb: Long = 0
            var mar: Long = 0
            var apr: Long = 0
            var may: Long = 0
            var jun: Long = 0
            var july: Long = 0
            var aug: Long = 0
            var sep: Long = 0
            var oct: Long = 0
            var nov: Long = 0
            var dec: Long = 0

            for (doc in value!!) {
                when (doc.get("month").toString().toInt()) {
                    1 -> {
                        jan += doc.get("payment").toString().toInt()
                    }
                    2 -> {
                        feb += doc.get("payment").toString().toInt()
                    }
                    3 -> {
                        mar += doc.get("payment").toString().toInt()
                    }
                    4 -> {
                        apr = doc.get("payment").toString().toLong()
                    }
                    5 -> {
                        may = doc.get("payment").toString().toLong()
                    }
                    6 -> {
                        jun = doc.get("payment").toString().toLong()
                    }
                    7 -> {
                        july = doc.get("payment").toString().toLong()
                    }
                    8 -> {
                        aug = doc.get("payment").toString().toLong()
                    }
                    9 -> {
                        sep = doc.get("payment").toString().toLong()
                    }
                    10 -> {
                        oct = doc.get("payment").toString().toLong()
                    }
                    11 -> {
                        nov = doc.get("payment").toString().toLong()
                    }
                    12 -> {
                        dec = doc.get("payment").toString().toLong()
                    }
                }
            }

            val tempPaymentList = arrayListOf(jan, feb, mar, apr, may, jun, july, aug, sep, oct, nov, dec)
            _paymentList.value = tempPaymentList

        })
    }

    fun delete(work: Work) {
        repo.deleteWork(work)?.addOnFailureListener { e ->
            Log.e(TAG, "delete: failed to delete work", e)
        }?.addOnSuccessListener {
            Log.d(TAG, "delete: successfully deleted ${work.name} named work")
        }
    }


    private fun getTotalPaymentByYear() = viewModelScope.launch {
        repo.getWorksList().addSnapshotListener(EventListener<QuerySnapshot> { value, error ->

            if (error != null) {
                cancel(message = "getTotalPaymentByYear: could't get your total payments", cause = error)
                return@EventListener
            }


            var tempTotalPaymentOfCurrentYear: Long = 0

            for (doc in value!!) {
                if (doc.get("year").toString().toInt() == CustomUtils.currentYear) {
                    tempTotalPaymentOfCurrentYear += doc.get("payment").toString().toLong()
                }
            }

            _paymentOfCurrentYear.value = tempTotalPaymentOfCurrentYear

        })
    }
}