package com.kabirnayeem99.paymentpaid.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kabirnayeem99.paymentpaid.data.db.WorkDatabase
import com.kabirnayeem99.paymentpaid.data.db.entities.Work
import com.kabirnayeem99.paymentpaid.utils.CustomUtils
import com.kabirnayeem99.paymentpaid.utils.Resource

class WorkRepository(val db: WorkDatabase) {


    suspend fun insert(work: Work) =
            db.getWorkDao().insert(work)


    suspend fun update(work: Work) =
            db.getWorkDao().update(work)


    suspend fun delete(work: Work) =
            db.getWorkDao().delete(work)


    suspend fun getTotalPaymentByYear(): Int =
            db.getWorkDao().getTotalPaymentByYear(CustomUtils.currentYear)


    suspend fun getAllWorks(): List<Work> =
            db.getWorkDao().getAllWorks()

    suspend fun getTotalPaymentByMonth(): List<Int> =
            db.getWorkDao().getTotalPaymentByMonth()
}