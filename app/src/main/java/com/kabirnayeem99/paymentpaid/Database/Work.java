package com.kabirnayeem99.paymentpaid.Database;

import com.kabirnayeem99.paymentpaid.Utils;

import java.util.Date;

public class Work {
    private String name;
    private Date date;
    private int payment;
    private String studentName;

    public Work(String name, Date date, int payment, String studentName) {
        this.name = name;
        this.date = date;
        this.payment = payment;
        this.studentName = studentName;
    }

    public Work() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return Utils.formatDateToString(date);
    }

    public void setDate(Date date) {
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
}
