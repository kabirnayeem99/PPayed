package com.kabirnayeem99.paymentpaid.Database;

public class Work {
    private String name;
    private String date;
    private String payment;

    public Work(String name, String date, String payment) {
        this.name = name;
        this.date = date;
        this.payment = payment;
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

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    @Override
    public String toString() {
        return "Work{" +
                "name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", payment='" + payment + '\'' +
                '}';
    }
}
