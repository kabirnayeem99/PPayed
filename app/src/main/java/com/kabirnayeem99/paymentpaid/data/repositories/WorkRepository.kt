package com.kabirnayeem99.paymentpaid.data.repositories

import androidx.lifecycle.LiveData
import com.kabirnayeem99.paymentpaid.data.db.WorkDatabase
import com.kabirnayeem99.paymentpaid.data.db.entities.Work
import com.kabirnayeem99.paymentpaid.utils.CustomUtils

class WorkRepository(val db: WorkDatabase) {


    suspend fun insert(work: Work) =
            db.getWorkDao().insert(work)


    suspend fun update(work: Work) =
            db.getWorkDao().update(work)


    suspend fun delete(work: Work) =
            db.getWorkDao().delete(work)


    suspend fun getTotalPaymentByYear(): LiveData<Int> =
            db.getWorkDao().getTotalPaymentByYear(CustomUtils.currentYear)


    suspend fun getAllWorks(): LiveData<List<Work>> =
            db.getWorkDao().getAllWorks()

    suspend fun getTotalPaymentByMonth(year: Int): LiveData<List<Int>> =
            db.getWorkDao().getTotalPaymentByMonth(year)
}