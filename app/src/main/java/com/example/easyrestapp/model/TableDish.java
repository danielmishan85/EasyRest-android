package com.example.easyrestapp.model;

import com.example.easyrestapp.model.Dish;

import java.util.ArrayList;

public class TableDish {
    public String id;
    public Dish dish;
    public int amount;
    public int firstOrMain;
    public ArrayList<String> comments;
    public boolean ready;
    public String readyTime; //consider to change
    public boolean allTogether;
    public double price;
    public String orderTime; //consider to change

    public TableDish() {
    }

//    public TableDish(String id,Dish dish, int amount, int firstOrMain, String readyTime, boolean allTogether, double price, String orderTime, boolean ready,ArrayList<String> comments) {
//
//        this.id=id;
//        this.dish = dish;
//        this.amount = amount;
//        this.firstOrMain = firstOrMain;
//        this.readyTime = readyTime;
//        this.allTogether = allTogether;
//        this.price = price;
//        this.orderTime = orderTime;
//        this.ready = ready;
//        this.comments=comments;
//    }

    public TableDish(Dish dish, int amount, int firstOrMain, boolean allTogether,ArrayList<String> comments) {
        this.dish = dish;
        this.amount = amount;
        this.firstOrMain = firstOrMain;
        this.allTogether = allTogether;
        this.comments=comments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getComments() {
        return comments;
    }

    public void setComments(ArrayList<String> comments) {
        this.comments = comments;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getFirstOrMain() {
        return firstOrMain;
    }

    public void setFirstOrMain(int firstOrMain) {
        this.firstOrMain = firstOrMain;
    }

    public String getReadyTime() {
        return readyTime;
    }

    public void setReadyTime(String readyTime) {
        this.readyTime = readyTime;
    }

    public boolean isAllTogether() {
        return allTogether;
    }

    public void setAllTogether(boolean allTogether) {
        this.allTogether = allTogether;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }
}
