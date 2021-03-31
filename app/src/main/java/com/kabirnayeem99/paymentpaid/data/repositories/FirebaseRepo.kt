package com.kabirnayeem99.paymentpaid.data.repositories

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import com.kabirnayeem99.paymentpaid.data.db.entities.Work
import com.kabirnayeem99.paymentpaid.data.db.entities.Work.Companion.toWork
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirebaseRepo {
    private val TAG = "FirebaseRepo"
    val db = FirebaseFirestore.getInstance()
    private val user: FirebaseUser = FirebaseAuth.getInstance().currentUser!!

    /**
     * Save work to firebase firestore
     *
     * @param work [Work]
     *
     * @return [Task], which can be successful, complete
     * and so on.
     */
    fun saveWork(work: Work): Task<Void>? {

        val docRef = work.documentId?.let {
            db.collection("users")
                    .document(user.uid)
                    .collection("work_list")
                    .document(it)
        }

        if (docRef != null) {
            return docRef.set(work)
        }

        return null
    }

    /**
     * A {@code CollectionReference} can be used
     * for adding documents, getting document references, and
     * querying for documents (using the methods
     * inherited from {@code Query}).
     */
    fun getWorksList(): CollectionReference {
        Log.d(TAG, "getWorksList: the collection reference is ${db.collection("users/${user.uid}/work_list")}")
        return db.collection("users/${user.uid}/work_list")

//        return callbackFlow {
//            val listenerRegistration = db.collection("users/${user.uid}/work_list")
//                    .addSnapshotListener { querySnapshot: QuerySnapshot?, exception: FirebaseFirestoreException? ->
//                        if (exception == null) {
//                            cancel(message = "Error fetching works", cause = exception)
//                            return@addSnapshotListener
//                        }
//
//                        val map = querySnapshot?.documents?.mapNotNull {
//                            it.toWork()
//                        }
//                        offer(map)
//
//                    }
//            awaitClose {
//                Log.d(TAG, "Cancelling posts listener")
//                listenerRegistration.remove()
//            }
//        }
    }


    fun getPaymentListByMonth(): Query {

        return db.collection("public_messages").whereEqualTo("month", 3);
    }


    /**
     * Delete work from firebase firestore
     *
     * @param work [Work]
     *
     * @return [Task], which can be successful, complete
     * and so on.
     */
    fun deleteWork(work: Work): Task<Void>? {
        val docRef = work.documentId?.let {
            db.collection("users")
                    .document(user.uid)
                    .collection("work_list")
                    .document(it)
        }

        return docRef?.delete()
    }


}