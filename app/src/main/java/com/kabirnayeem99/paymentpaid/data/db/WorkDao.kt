package com.kabirnayeem99.paymentpaid.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kabirnayeem99.paymentpaid.data.db.entities.Work

@Dao
interface WorkDao {
    @Insert
    fun insert(work: Work)

    @Update
    fun update(work: Work)

    @Delete
    fun delete(work: Work)

    @get:Query(value = "SELECT * FROM works_db_table ORDER BY submission_date DESC")
    val getAllWorks: LiveData<List<Work>>

    @Query("SELECT IFNULL(SUM(w.payment), 0) FROM (SELECT 1 month UNION ALL SELECT 2 UNION ALL" +
            " SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 " +
            "UNION ALL SELECT 8 UNION ALL SELECT 9 UNION ALL SELECT 10 UNION ALL SELECT 11 UNION ALL" +
            " SELECT 12) m LEFT JOIN works_db_table w ON w.account_month =  m.month AND " +
            "w.account_year = :year GROUP BY m.month")
    fun getTotalPaymentByMonth(year: Int): LiveData<List<Int>>

    @Query("SELECT SUM(payment) FROM works_db_table WHERE account_year = :year ")
    fun getTotalPaymentByYear(year: Int): LiveData<Int>
}