package com.kabirnayeem99.paymentpaid.data.repositories

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.kabirnayeem99.paymentpaid.data.db.entities.Work

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
    fun saveWork(work: Work): Task<Void> {

        val docRef = db.collection("users")
                .document(user.uid)
                .collection("work_list")
                .document(work.documentId)

        return docRef.set(work)
    }

    /**
     * A {@code CollectionReference} can be used
     * for adding documents, getting document references, and
     * querying for documents (using the methods
     * inherited from {@code Query}).
     */
    fun getWorksList(): CollectionReference {
        return db.collection("users/${user.uid}/work_list")
    }

    /**
     * Delete work from firebase firestore
     *
     * @param work [Work]
     *
     * @return [Task], which can be successful, complete
     * and so on.
     */
    fun deleteWork(work: Work): Task<Void> {
        val docRef = db.collection("users")
                .document(user.uid)
                .collection("work_list")
                .document(work.documentId)

        return docRef.delete()
    }
}