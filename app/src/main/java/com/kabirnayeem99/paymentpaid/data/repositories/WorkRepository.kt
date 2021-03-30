package com.kabirnayeem99.paymentpaid.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import com.kabirnayeem99.paymentpaid.data.db.WorkDatabase
import com.kabirnayeem99.paymentpaid.data.db.entities.Work
import com.kabirnayeem99.paymentpaid.enums.AccountStatus
import com.kabirnayeem99.paymentpaid.utils.CustomUtils
import kotlinx.coroutines.flow.Flow


/**
 * This repository class isolates the [WorkDatabase] from the rest of the app.
 * The repository class provides a clean API for data access to the rest of the app.
 * Using repositories is a recommended best practice for code separation and architecture.
 * @param db of [WorkDatabase] type
 */
class WorkRepository(private val db: WorkDatabase, private val accountStatus: AccountStatus) {

    /**
     * This is a repository method, which insert a new work to the database
     * @param work of [Work] type
     */
    suspend fun insert(work: Work) {

        if (accountStatus == AccountStatus.OFFLINE) {
            db.getWorkDao().insert(work)
        } else if (accountStatus == AccountStatus.ONLINE) {
            //todo: implements
            db.getWorkDao().insert(work)
            Log.d("WorkRepository", "insert: this is an online insertation")
        }
    }

    /**
     * This is a repository method, which updates the work to the database
     * @param work of [Work] type
     */
    suspend fun update(work: Work) {
        db.getWorkDao().update(work)

//        if (auth.currentUser == null) {
//            db.getWorkDao().update(work)
//        } else if (auth.currentUser != null) {
//            remoteDb?.update(work)
//        }
    }


    /**
     * This is a repository method, which deletes a work to the database
     * @param work of [Work] type
     */
    suspend fun delete(work: Work) {
        db.getWorkDao().delete(work)

//        if (auth.currentUser == null) {
//            db.getWorkDao().delete(work)
//        } else if (auth.currentUser != null) {
//            remoteDb?.delete(work)
//        }
    }


    /**
     * This is a repository method, which gets the total payment of the existing year
     * @return Total Payment of the year of Integer Live data
     */
    fun getTotalPaymentByYear(): LiveData<Int> {
//        if (auth.currentUser == null) {
//            return db.getWorkDao().getTotalPaymentByYear(CustomUtils.currentYear)
//        } else if (auth.currentUser != null) {
////            return remoteDb?.getTotalPaymentByYear(CustomUtils.currentYear)
//        }
        return db.getWorkDao().getTotalPaymentByYear(CustomUtils.currentYear)
    }

    /**
     * This is a repository method, which gets all the works listed
     * @return List of Works - Which is the LiveData of List of [Work]
     */
    fun getAllWorks(): LiveData<List<Work>> {
//        if (auth.currentUser == null) {
//            return db.getWorkDao().getAllWorks()
//        } else if (auth.currentUser != null) {
////            return remoteDb.getAllWorks()
//        }
        return db.getWorkDao().getAllWorks()

    }

    fun getAllWorksSync(): Flow<List<Work>> {
        return db.getWorkDao().getAllWorksSync()
    }


    /**
     * This is a repository method, which gets the list of payment by month
     * @return payment list by month - which is LiveData of Integer List
     */
    fun getTotalPaymentByMonth(): LiveData<List<Int>> {
//        if (auth.currentUser == null) {
//            return db.getWorkDao().getTotalPaymentByMonth(CustomUtils.currentYear)
//        } else if (auth.currentUser != null) {
////            return remoteDb.getTotalPaymentByMonth(CustomUtils.currentYear)
//        }
        return db.getWorkDao().getTotalPaymentByMonth(CustomUtils.currentYear)
    }
}