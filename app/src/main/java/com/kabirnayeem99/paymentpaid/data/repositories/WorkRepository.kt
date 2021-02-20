package com.kabirnayeem99.paymentpaid.data.repositories

import androidx.lifecycle.LiveData
import com.kabirnayeem99.paymentpaid.data.db.WorkDatabase
import com.kabirnayeem99.paymentpaid.data.db.entities.Work
import com.kabirnayeem99.paymentpaid.utils.CustomUtils


/**
 * This repository class isolates the [WorkDatabase] from the rest of the app.
 * The repository class provides a clean API for data access to the rest of the app.
 * Using repositories is a recommended best practice for code separation and architecture.
 * @param db of [WorkDatabase] type
 */
class WorkRepository(val db: WorkDatabase) {

    /**
     * This is a repository method, which insert a new work to the database
     * @param work of [Work] type
     */
    suspend fun insert(work: Work) =
            db.getWorkDao().insert(work)


    /**
     * This is a repository method, which updates the work to the database
     * @param work of [Work] type
     */
    suspend fun update(work: Work) =
            db.getWorkDao().update(work)


    /**
     * This is a repository method, which deletes a work to the database
     * @param work of [Work] type
     */
    suspend fun delete(work: Work) =
            db.getWorkDao().delete(work)


    /**
     * This is a repository method, which gets the total payment of the existing year
     * @return Total Payment of the year of Integer Live data
     */
    fun getTotalPaymentByYear(): LiveData<Int> =
            db.getWorkDao().getTotalPaymentByYear(CustomUtils.currentYear)


    /**
     * This is a repository method, which gets all the works listed
     * @return List of Works - Which is the LiveData of List of [Work]
     */
    fun getAllWorks(): LiveData<List<Work>> =
            db.getWorkDao().getAllWorks()


    /**
     * This is a repository method, which gets the list of payment by month
     * @return payment list by month - which is LiveData of Integer List
     */
    fun getTotalPaymentByMonth(): LiveData<List<Int>> =
            db.getWorkDao().getTotalPaymentByMonth(CustomUtils.currentYear)
}