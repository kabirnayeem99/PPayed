package com.kabirnayeem99.paymentpaid.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "works_db_table")
public class PaymentMonth {
    @ColumnInfo(name = "account_month")
    private final int month;
    @ColumnInfo(name = "payment")
    private final int payment;
    @PrimaryKey(autoGenerate = true)
    @Ignore
    private int id;

    public PaymentMonth(int month, int payment) {
        this.month = month;
        this.payment = payment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMonth() {
        return month;
    }

    public int getPayment() {
        return payment;
    }

    @Override
    public String toString() {
        return "PaymentMonth{" +
                "id=" + id +
                ", month=" + month +
                ", payment=" + payment +
                '}';
    }
}
