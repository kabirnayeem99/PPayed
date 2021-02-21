package com.kabirnayeem99.paymentpaid.data.repositories

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.kabirnayeem99.paymentpaid.data.db.WorkDatabase
import com.kabirnayeem99.paymentpaid.data.db.entities.Work
import com.kabirnayeem99.paymentpaid.data.remote_repo.FirebaseService
import com.kabirnayeem99.paymentpaid.utils.CustomUtils


/**
 * This repository class isolates the [WorkDatabase] from the rest of the app.
 * The repository class provides a clean API for data access to the rest of the app.
 * Using repositories is a recommended best practice for code separation and architecture.
 * @param db of [WorkDatabase] type
 */
class WorkRepository(val db: WorkDatabase, private val remoteDb: FirebaseService? = null) {

    val auth = FirebaseAuth.getInstance()

    /**
     * This is a repository method, which insert a new work to the database
     * @param work of [Work] type
     */
    suspend fun insert(work: Work) {
        if (auth.currentUser == null) {
            db.getWorkDao().insert(work)
        } else if (auth.currentUser != null) {
            remoteDb.insert(work)
        }
    }

    /**
     * This is a repository method, which updates the work to the database
     * @param work of [Work] type
     */
    suspend fun update(work: Work) {
        if (auth.currentUser == null) {
            db.getWorkDao().update(work)
        } else if (auth.currentUser != null) {
            remoteDb.update(work)
        }
    }


    /**
     * This is a repository method, which deletes a work to the database
     * @param work of [Work] type
     */
    suspend fun delete(work: Work) {
        if (auth.currentUser == null) {
            db.getWorkDao().delete(work)
        } else if (auth.currentUser != null) {
            remoteDb.delete(work)
        }
    }


    /**
     * This is a repository method, which gets the total payment of the existing year
     * @return Total Payment of the year of Integer Live data
     */
    fun getTotalPaymentByYear(): LiveData<Int> {
        if (auth.currentUser == null) {
            return db.getWorkDao().getTotalPaymentByYear(CustomUtils.currentYear)
        } else if (auth.currentUser != null) {
            return remoteDb.getTotalPaymentByYear(CustomUtils.currentYear)
        }
        return db.getWorkDao().getTotalPaymentByYear(CustomUtils.currentYear)
    }

    /**
     * This is a repository method, which gets all the works listed
     * @return List of Works - Which is the LiveData of List of [Work]
     */
    fun getAllWorks(): LiveData<List<Work>> {
        if (auth.currentUser == null) {
            return db.getWorkDao().getAllWorks()
        } else if (auth.currentUser != null) {
            return remoteDb.getAllWorks()
        }
        return db.getWorkDao().getAllWorks()

    }


    /**
     * This is a repository method, which gets the list of payment by month
     * @return payment list by month - which is LiveData of Integer List
     */
    fun getTotalPaymentByMonth(): LiveData<List<Int>> {
        if (auth.currentUser == null) {
            return db.getWorkDao().getTotalPaymentByMonth(CustomUtils.currentYear)
        } else if (auth.currentUser != null) {
            return remoteDb.getTotalPaymentByMonth(CustomUtils.currentYear)
        }
        return db.getWorkDao().getTotalPaymentByMonth(CustomUtils.currentYear)
    }
}