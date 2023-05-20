package com.example.easyrestapp.model;

import java.util.ArrayList;

public class ClosedTable {

    String id;
    Table t;
    String openTime;
    Double tip;
    ArrayList<Payment> paymentArray;



    public ClosedTable(Table t, String openTime, Double tip, ArrayList<Payment> paymentArray) {
        this.t = t;
        this.openTime = openTime;
        this.tip = tip;
        this.paymentArray = paymentArray;
    }

    public ClosedTable() {

    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public Table getT() {
        return t;
    }

    public void setT(Table t) {
        this.t = t;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
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
