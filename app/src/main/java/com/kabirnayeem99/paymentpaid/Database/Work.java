package com.kabirnayeem99.paymentpaid.Database;

public class Work {
    private String name;
    private String date;
    private int payment;
    private String studentName;

    public Work(String name, String date, int payment, String studentName) {
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
}
