package com.kabirnayeem99.paymentpaid.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "payment_month_table")
public class PaymentMonth {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "month_name")
    private String monthName;
    @ColumnInfo(name = "payment_amount")
    private int paymentAmount;

    public void setId(int id) {
        this.id = id;
    }

    public String getMonthName() {
        return monthName;
    }

    public int getPaymentAmount() {
        return paymentAmount;
    }
}
