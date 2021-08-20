package com.kabirnayeem99.paymentpaid.data.repositories

import androidx.lifecycle.LiveData
import com.google.android.gms.tasks.Task
import com.kabirnayeem99.paymentpaid.domain.models.Work
import com.kabirnayeem99.paymentpaid.data.sources.FirebaseDataSource
import com.kabirnayeem99.paymentpaid.domain.repositories.WorkRepository

class WorkRepositoryImpl : WorkRepository {

    private val firebaseDataSource: FirebaseDataSource = FirebaseDataSource()

    /**
     * Save work to firebase firestore
     *
     * @param work [Work]
     *
     * @return [Task], which can be successful, complete
     * and so on.
     */
    override fun saveWork(work: Work) = firebaseDataSource.saveWork(work)


    /**
     * A {@code CollectionReference} can be used
     * for adding documents, getting document references, and
     * querying for documents (using the methods
     * inherited from {@code Query}).
     */
    override fun getWorksList(): LiveData<List<Work>> = firebaseDataSource.getWorksList()


    override fun getPaymentListByMonth(): LiveData<List<Long>> =
        firebaseDataSource.getPaymentListByMonth()

    override fun getTotalPaymentsByYear(): LiveData<Long> =
        firebaseDataSource.getTotalPaymentsByYear()


    /**
     * Delete work from firebase firestore
     *
     * @param work [Work]
     *
     * @return [Task], which can be successful, complete
     * and so on.
     */
    override fun deleteWork(work: Work) = firebaseDataSource.deleteWork(work)


}