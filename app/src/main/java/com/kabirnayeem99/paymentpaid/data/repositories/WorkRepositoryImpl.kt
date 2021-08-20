package com.kabirnayeem99.paymentpaid.data.repositories

import androidx.lifecycle.LiveData
import com.google.android.gms.tasks.Task
import com.kabirnayeem99.paymentpaid.domain.models.Work
import com.kabirnayeem99.paymentpaid.domain.repositories.WorkRepository
import com.kabirnayeem99.paymentpaid.domain.sources.RemoteDataSource
import com.kabirnayeem99.paymentpaid.other.Resource
import javax.inject.Inject

class WorkRepositoryImpl @Inject constructor(
    var dataSource: RemoteDataSource
) : WorkRepository {


    /**
     * Save work to firebase firestore
     *
     * @param work [Work]
     *
     * @return [Task], which can be successful, complete
     * and so on.
     */
    override fun saveWork(work: Work) = dataSource.saveWork(work)


    /**
     * A {@code CollectionReference} can be used
     * for adding documents, getting document references, and
     * querying for documents (using the methods
     * inherited from {@code Query}).
     */
    override fun getWorksList(): LiveData<Resource<List<Work>>> = dataSource.getWorksList()


    override fun getPaymentListByMonth(): LiveData<Resource<List<Long>>> =
        dataSource.getPaymentListByMonth()

    override fun getTotalPaymentsByYear(): LiveData<Resource<Long>> =
        dataSource.getTotalPaymentsByYear()


    /**
     * Delete work from firebase firestore
     *
     * @param work [Work]
     *
     * @return [Task], which can be successful, complete
     * and so on.
     */
    override fun deleteWork(work: Work) = dataSource.deleteWork(work)


}