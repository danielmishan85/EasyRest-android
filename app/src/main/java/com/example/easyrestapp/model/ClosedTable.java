package com.example.easyrestapp.model;

import java.util.ArrayList;

public class ClosedTable {

    Table t;
    String closeTime;
    Double tip;
    ArrayList<Payment> paymentArray;


    public ClosedTable(Table t, String closeTime, Double tip, ArrayList<Payment> paymentArray) {
        this.t = t;
        this.closeTime = closeTime;
        this.tip = tip;
        this.paymentArray = paymentArray;
    }

    public Table getT() {
        return t;
    }

    public void setT(Table t) {
        this.t = t;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public Double getTip() {
        return tip;
    }

    public void setTip(Double tip) {
        this.tip = tip;
    }

    public ArrayList<Payment> getPaymentArray() {
        return paymentArray;
    }

    public void setPaymentArray(ArrayList<Payment> paymentArray) {
        this.paymentArray = paymentArray;
    }
}
