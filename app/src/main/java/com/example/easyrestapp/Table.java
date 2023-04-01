package com.example.easyrestapp;

public class Table {
    public String tNum;
    public String tNote;
    public String tDinners;
    public String tTime;
    public double tTotal;
    public double tAvg;


    public Table(String tNum, String tNote, String tDinners, double tTotal, double tAvg) {
        this.tNum = tNum;
        this.tNote = tNote;
        this.tDinners = tDinners;
        this.tTotal = tTotal;
        this.tAvg = tAvg;
    }
    public Table(String tNum, String tNote, String tTime, String tDinners, double tTotal, double tAvg) {
        this.tNum = tNum;
        this.tNote = tNote;
        this.tTime = tTime;
        this.tDinners = tDinners;
        this.tTotal = tTotal;
        this.tAvg = tAvg;
    }
}
