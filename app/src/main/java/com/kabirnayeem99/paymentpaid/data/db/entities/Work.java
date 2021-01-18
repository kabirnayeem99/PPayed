package com.kabirnayeem99.paymentpaid.data.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "works_db_table")
public class Work {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "work_name")
    private final String name;
    @ColumnInfo(name = "submission_date")
    private final String date;
    @ColumnInfo(name = "account_month")
    private final int month;
    @ColumnInfo(name = "account_year")
    private final int year;
    @ColumnInfo(name = "payment")
    private final int payment;
    @ColumnInfo(name = "student_name")
    private final String studentName;

    public Work(String name, String date, int month, int year, int payment, String studentName) {
        this.name = name;
        this.date = date;
        this.month = month;
        this.year = year;
        this.payment = payment;
        this.studentName = studentName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getPayment() {
        return payment;
    }

    public String getStudentName() {
        return studentName;
    }

    @Override
    public String toString() {
        return "Work{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", month=" + month +
                ", year=" + year +
                ", payment=" + payment +
                ", studentName='" + studentName + '\'' +
                '}';
    }
}
