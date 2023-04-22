package com.example.easyrestapp.model;

import java.util.ArrayList;
import java.util.List;

public class Table {
    public String gettNum() {
        return tNum;
    }

    public String tNum;
    public String tNote;
    public String tDinners;
    public String tTime;
    public double tTotal;
    public double tAvg;
    public List<TableDish> orderList;


    public Table(String tNum, String tNote, String tDinners, double tTotal, double tAvg) {
        this.tNum = tNum;
        this.tNote = tNote;
        this.tDinners = tDinners;
        this.tTotal = tTotal;
        this.tAvg = tAvg;
        this.orderList= new ArrayList<>();
        //demo order list for each table
        for (int i=0;i<2;i++){
            orderList.add(new TableDish(new Dish("No " + Integer.toString(i), "Start", new ArrayList<>()), "no comments yet"));
        }
        for (int i=10;i<12;i++){
            orderList.add(new TableDish(new Dish("No " + Integer.toString(i), "Main", new ArrayList<>()), "no comments yet"));
        }
        for (int i=20;i<22;i++){
            orderList.add(new TableDish(new Dish("No " + Integer.toString(i), "Dessert", new ArrayList<>()), "no comments yet"));
        }
    }
    public Table(String tNum, String tNote, String tTime, String tDinners, double tTotal, double tAvg) {
        this.tNum = tNum;
        this.tNote = tNote;
        this.tTime = tTime;
        this.tDinners = tDinners;
        this.tTotal = tTotal;
        this.tAvg = tAvg;
        this.orderList= new ArrayList<>();
        //demo order list for each table
        for (int i=0;i<2;i++){
            orderList.add(new TableDish(new Dish("No " + Integer.toString(i), "Start", new ArrayList<>()), "no comments yet"));
        }
        for (int i=10;i<12;i++){
            orderList.add(new TableDish(new Dish("No " + Integer.toString(i), "Main", new ArrayList<>()), "no comments yet"));
        }
        for (int i=20;i<22;i++){
            orderList.add(new TableDish(new Dish("No " + Integer.toString(i), "Dessert", new ArrayList<>()), "no comments yet"));
        }
    }

    public List<TableDish> getOrderList() {
        return orderList;
    }
}
