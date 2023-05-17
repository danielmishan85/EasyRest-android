package com.example.easyrestapp.model;

import java.util.ArrayList;

public class TableDrink {
    String Id;
    Drink drink;
    int amount;
    public ArrayList<String> comments;
    Boolean ready;
    Double price;

    public TableDrink(String drinkId, Drink drink, int amount, ArrayList<String> comments, Boolean ready, Double price) {
        this.Id = drinkId;
        this.drink = drink;
        this.amount = amount;
        this.comments = comments;
        this.ready = ready;
        this.price = price;
    }

    public TableDrink( Drink drink, int amount, ArrayList<String> comments) {
        this.drink = drink;
        this.amount = amount;
        this.comments = comments;
    }

    public TableDrink(){}

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public Drink getDrink() {
        return drink;
    }

    public void setDrink(Drink drink) {
        this.drink = drink;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public ArrayList<String> getComments() {
        return comments;
    }

    public void setComments(ArrayList<String> comments) {
        this.comments = comments;
    }

    public Boolean getReady() {
        return ready;
    }

    public void setReady(Boolean ready) {
        this.ready = ready;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
