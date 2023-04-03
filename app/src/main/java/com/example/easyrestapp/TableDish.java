package com.example.easyrestapp;

import java.util.ArrayList;

public class TableDish {

    public String name;
    public String type;
    public double price;
    public ArrayList<String> ingredients;
    public String comments;

    public TableDish(Dish d, String comments) {
        this.name = d.getName();
        if(d.getType().equals("Start"))
            this.type = "F";
        else if (d.getType().equals("Main"))
            this.type = "M";
        else
            this.type = "";
        this.price = d.getPrice();
        this.comments = comments;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }
}
