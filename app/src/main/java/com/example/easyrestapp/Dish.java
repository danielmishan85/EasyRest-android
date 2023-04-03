package com.example.easyrestapp;

import java.util.ArrayList;

public class Dish {

    public String name;
    public String imgURL;
    public String type;
    public ArrayList<String> ingredients;

    public Dish(String name, String type, ArrayList<String> ingredients) {
        this.name = name;
        this.type = type;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }
}
