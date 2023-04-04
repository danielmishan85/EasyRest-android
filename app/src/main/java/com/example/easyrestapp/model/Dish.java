package com.example.easyrestapp.model;

import java.util.ArrayList;

public class Dish {

    public String name;
    public String imgURL;
    public String type;
    public double price;
    public ArrayList<String> ingredients;

    public Dish(String name, String type, ArrayList<String> ingredients) {
        this.name = name;
        this.type = type;
        this.price = 39.99;
        this.ingredients = ingredients;
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
