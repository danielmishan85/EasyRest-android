package com.example.easyrestapp.model;

import com.example.easyrestapp.model.Dish;

import java.util.ArrayList;

public class TableDish {

    public Dish dish;
    public int amount;
    public int firstOrMain;
    public String readyTime; //consider to change
    public boolean allTogether;
    public double price;
    public String orderTime; //consider to change
    public boolean ready;

    public TableDish() {
    }

    public TableDish(Dish dish, int amount, int firstOrMain, String readyTime, boolean allTogether, double price, String orderTime, boolean ready) {
        this.dish = dish;
        this.amount = amount;
        this.firstOrMain = firstOrMain;
        this.readyTime = readyTime;
        this.allTogether = allTogether;
        this.price = price;
        this.orderTime = orderTime;
        this.ready = ready;
    }

    public TableDish(Dish dish, int amount, int firstOrMain, boolean allTogether) {
        this.dish = dish;
        this.amount = amount;
        this.firstOrMain = firstOrMain;
        this.allTogether = allTogether;
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
