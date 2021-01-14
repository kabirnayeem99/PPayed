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

    @Query("SELECT * FROM works_db_table")
    LiveData<List<Work>> getAllWorks();

    @Query("SELECT SUM(payment) FROM works_db_table WHERE submission_date LIKE '2021-%%-%%'")
        //todo: implement monthly basis total payment
    int getTotalPaymentByMonth();

    @Query("SELECT SUM(payment) FROM works_db_table WHERE submission_date LIKE '2021%%'")
        //todo: implement soft coded current year basis total payment
    LiveData<Integer> getTotalPaymentByYear();
}
