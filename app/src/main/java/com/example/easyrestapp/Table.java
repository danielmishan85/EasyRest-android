package com.example.easyrestapp;

public class Table {
    public String tNum;
    public String tNote;
    public String tDinners;
    public double tTotal;
    public double tAvg;

    public Table(String tNum, String tNote, String tDinners, double tTotal, double tAvg) {
        this.tNum = tNum;
        this.tNote = tNote;
        this.tDinners = tDinners;
        this.tTotal = tTotal;
        this.tAvg = tAvg;
    }
}
