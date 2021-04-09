package com.kabirnayeem99.paymentpaid.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import com.kabirnayeem99.paymentpaid.data.db.entities.Work
import com.kabirnayeem99.paymentpaid.data.db.entities.Work.Companion.toWork
import com.kabirnayeem99.paymentpaid.other.Utils
import kotlinx.coroutines.NonCancellable.cancel
import kotlinx.coroutines.cancel

class FirebaseRepo : Repo {
    private val TAG = "FirebaseRepo"
    val db = FirebaseFirestore.getInstance()
    private val user: FirebaseUser = FirebaseAuth.getInstance().currentUser!!

    private val workList = MutableLiveData<List<Work>>()
    private val paymentList = MutableLiveData<List<Long>>()
    private val paymentOfCurrentYear = MutableLiveData<Long>()

    /**
     * Save work to firebase firestore
     *
     * @param work [Work]
     *
     * @return [Task], which can be successful, complete
     * and so on.
     */
    override fun saveWork(work: Work) {

        work.documentId?.let {
            db.collection("users")
                    .document(user.uid)
                    .collection("work_list")
                    .document(it)
        }?.set(work)

    }

    /**
     * A {@code CollectionReference} can be used
     * for adding documents, getting document references, and
     * querying for documents (using the methods
     * inherited from {@code Query}).
     */
    override fun getWorksList(): LiveData<List<Work>> {
        Log.d(TAG, "getWorksList: the collection reference is ${db.collection("users/${user.uid}/work_list")}")
        db.collection("users/${user.uid}/work_list").addSnapshotListener(
                EventListener<QuerySnapshot> { value, error ->

                    if (error != null) {
                        return@EventListener
                    }

                    val temp = mutableListOf<Work>()

                    for (doc in value!!) {
                        doc.toWork()?.let { temp.add(it) }
                        Log.d(TAG, "getWorkList:  the documents in json form: $doc")
                    }

                    workList.value = temp
                }

        )

        return workList
    }


    override fun getPaymentListByMonth(): LiveData<List<Long>> {
        db.collection("users/${user.uid}/work_list").addSnapshotListener(
                EventListener<QuerySnapshot> { value, error ->

                    if (error != null) {
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
                    paymentList.value = tempPaymentList

                })

        return paymentList

    }

    override fun getTotalPaymentsByYear(): LiveData<Long> {
        db.collection("users/${user.uid}/work_list").addSnapshotListener(
                EventListener<QuerySnapshot> { value, error ->
                    if (error != null) {
                        return@EventListener
                    }
                    var tempTotalPaymentOfCurrentYear: Long = 0
                    for (doc in value!!) {
                        if (doc.get("year").toString().toInt() == Utils.currentYear) {
                            tempTotalPaymentOfCurrentYear += doc.get("payment").toString().toLong()
                        }
                    }

                    paymentOfCurrentYear.value = tempTotalPaymentOfCurrentYear
                })

        return paymentOfCurrentYear
    }


    /**
     * Delete work from firebase firestore
     *
     * @param work [Work]
     *
     * @return [Task], which can be successful, complete
     * and so on.
     */
    override fun deleteWork(work: Work) {
        val docRef = work.documentId?.let {
            db.collection("users")
                    .document(user.uid)
                    .collection("work_list")
                    .document(it)
        }

        docRef?.delete()
    }


}