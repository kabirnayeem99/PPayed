package com.kabirnayeem99.paymentpaid.utils;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.kabirnayeem99.paymentpaid.models.Work;

import java.util.List;

@Dao
public interface WorkDao {

    @Insert
    void insert(Work work);

    @Update
    void update(Work work);

    @Delete
    void delete(Work work);

    @Query(value = "SELECT * FROM works_db_table")
    LiveData<List<Work>> getAllWorks();

    @Query("SELECT IFNULL(SUM(w.payment), 0) FROM (SELECT 1 month UNION ALL SELECT 2 UNION ALL" +
            " SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 " +
            "UNION ALL SELECT 8 UNION ALL SELECT 9 UNION ALL SELECT 10 UNION ALL SELECT 11 UNION ALL" +
            " SELECT 12) m LEFT JOIN works_db_table w ON w.account_month =  m.month AND " +
            "w.account_year = :year GROUP BY m.month")
    LiveData<List<Integer>> getTotalPaymentByMonth(int year);


    @Query("SELECT SUM(payment) FROM works_db_table WHERE account_year = :year ")
    LiveData<Integer> getTotalPaymentByYear(int year);
}
