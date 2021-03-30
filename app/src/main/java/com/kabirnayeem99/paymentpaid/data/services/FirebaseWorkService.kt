package com.kabirnayeem99.paymentpaid.data.services

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.kabirnayeem99.paymentpaid.data.db.entities.Work
import com.kabirnayeem99.paymentpaid.data.db.entities.Work.Companion.toWork
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

object FirebaseWorkService {

    private const val TAG = "FirebaseWorkService"
    suspend fun getWorkData(documentId: String): Work? {
        val db = FirebaseFirestore.getInstance()
        try {
            return db.collection("works").document(documentId).get().await().toWork()
        } catch (e: Exception) {
            Log.e(TAG, "getWorksData: error at converting firebase document to work $e")
//            FirebaseCrashlytics.getInstance().log("Error getting user details")
//            FirebaseCrashlytics.getInstance().setCustomKey("user id", xpertSlug)
//            FirebaseCrashlytics.getInstance().recordException(e)
            return null
        }
    }

    @ExperimentalCoroutinesApi
    fun getWorksList(userId: String): Flow<List<Work>?> {
        val db = FirebaseFirestore.getInstance()
        return callbackFlow {
            // Creating a listener registration inside a callback flow
            val listenerRegistration = db.collection("users")
                    .document(userId)
                    .collection("works")
                    .addSnapshotListener { querySnapshot: QuerySnapshot?, firebaseFireStoreException: FirebaseFirestoreException? ->
                        if (firebaseFireStoreException != null) {
                            // Cancelling the registration in case of any error
                            cancel(message = "Error fetching the works", cause = firebaseFireStoreException)
                            return@addSnapshotListener
                        }

                        val map = querySnapshot?.documents?.mapNotNull { it.toWork() }
                        // Emitting the results via the offer() method
                        offer(map)

                    }

            /*
             This single statement keeps this flow active and
             ensures it waits till it’s closed or cancelled.
             When it’s closed, we can safely detach the Firebase
             listener we attached earlier.
             */
            awaitClose {
                Log.d(TAG, "getWorks: cancelling works listener")
                listenerRegistration.remove()
            }
        }
    }
}