package com.kabirnayeem99.paymentpaid.data.sources

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.kabirnayeem99.paymentpaid.domain.models.Work
import com.kabirnayeem99.paymentpaid.domain.models.Work.Companion.toWork
import com.kabirnayeem99.paymentpaid.domain.sources.RemoteDataSource
import com.kabirnayeem99.paymentpaid.other.Constants
import com.kabirnayeem99.paymentpaid.other.Resource
import com.kabirnayeem99.paymentpaid.other.Utils
import timber.log.Timber

class FirebaseDataSource : RemoteDataSource {


    private val workList = MutableLiveData<Resource<List<Work>>>()
    private val paymentList = MutableLiveData<Resource<List<Long>>>()
    private val paymentOfCurrentYear = MutableLiveData<Resource<Long>>()


    private val db = FirebaseFirestore.getInstance()
    private val user: FirebaseUser = FirebaseAuth.getInstance().currentUser!!


    override fun saveWork(work: Work): Resource<String> {
        try {

            work.documentId?.let {
                db.collection("users")
                    .document(user.uid)
                    .collection("work_list")
                    .document(it)
            }?.set(work)

            Timber.d("saveWork: saved work ${work.name}")

            return Resource.Success(work.name)
        } catch (e: Exception) {

            Timber.e("saveWork: could not save the work due to $e")

            return Resource.Error(e.message ?: Constants.UNKNOWN_ERROR)
        }
    }

    override fun getWorksList(): LiveData<Resource<List<Work>>> {
        Timber.d(
            "getWorksList: the collection reference is ${db.collection("users/${user.uid}/work_list")}"
        )

        workList.value = Resource.Loading()

        db.collection("users/${user.uid}/work_list").addSnapshotListener(
            EventListener<QuerySnapshot> { value, error ->

                if (error != null) {
                    workList.value = Resource.Error(error.message ?: Constants.UNKNOWN_ERROR)
                    return@EventListener
                }

                val temp = mutableListOf<Work>()

                for (doc in value!!) {
                    doc.toWork()?.let { temp.add(it) }
                    Timber.d("getWorkList:  the documents in json form: $doc")
                }

                workList.value = Resource.Success(temp)
            }

        )

        return workList
    }


    override fun deleteWork(work: Work): Resource<String> {
        try {
            val docRef = work.documentId?.let {
                db.collection("users")
                    .document(user.uid)
                    .collection("work_list")
                    .document(it)
            }

            docRef?.delete()

            return Resource.Success(work.name)
        } catch (e: Exception) {
            Timber.e("Could not delete the work due to $e")

            return Resource.Error(e.message ?: Constants.UNKNOWN_ERROR + work.name)
        }
    }

    override fun getPaymentListByMonth(): LiveData<Resource<List<Long>>> {
        paymentList.value = Resource.Loading()

        db.collection("users/${user.uid}/work_list").addSnapshotListener(
            EventListener<QuerySnapshot> { value, error ->

                if (error != null) {

                    paymentList.value = Resource.Error(error.message ?: Constants.UNKNOWN_ERROR)
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

                val tempPaymentList =
                    arrayListOf(jan, feb, mar, apr, may, jun, july, aug, sep, oct, nov, dec)
                paymentList.value = Resource.Success(tempPaymentList)

            })

        return paymentList

    }

    override fun getTotalPaymentsByYear(): LiveData<Resource<Long>> {

        paymentOfCurrentYear.value = Resource.Loading()

        db.collection("users/${user.uid}/work_list").addSnapshotListener(
            EventListener<QuerySnapshot> { value, error ->
                if (error != null) {

                    paymentOfCurrentYear.value =
                        Resource.Error(error.message ?: Constants.UNKNOWN_ERROR)
                    return@EventListener
                }
                var tempTotalPaymentOfCurrentYear: Long = 0
                for (doc in value!!) {
                    if (doc.get("year").toString().toInt() == Utils.currentYear) {
                        tempTotalPaymentOfCurrentYear += doc.get("payment").toString().toLong()
                    }
                }

                paymentOfCurrentYear.value = Resource.Success(tempTotalPaymentOfCurrentYear)
            })

        return paymentOfCurrentYear
    }
}