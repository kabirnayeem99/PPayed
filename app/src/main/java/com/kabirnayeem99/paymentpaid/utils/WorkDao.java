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

    @Query("SELECT IFNULL(SUM(payment), 0) FROM works_db_table WHERE account_year = :year " +
            " GROUP BY account_month ORDER BY account_month ")
    LiveData<List<Integer>> getTotalPaymentByMonth(int year);


    @Query("SELECT SUM(payment) FROM works_db_table WHERE account_year = :year ")
    LiveData<Integer> getTotalPaymentByYear(int year);
}
