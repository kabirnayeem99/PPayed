package com.kabirnayeem99.paymentpaid.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "works_db_table")
public class Work {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "work_name")
    private String name;
    @ColumnInfo(name = "submission_date")
    private String date;
    @ColumnInfo(name = "payment")
    private int payment;
    @ColumnInfo(name = "student_name")
    private String studentName;

    public Work(String name, String date, int payment, String studentName) {
        this.name = name;
        this.date = date;
        this.payment = payment;
        this.studentName = studentName;
    }

    public Work() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    @Override
    public String toString() {
        return "Work{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", payment=" + payment +
                ", studentName='" + studentName + '\'' +
                '}';
    }
}
