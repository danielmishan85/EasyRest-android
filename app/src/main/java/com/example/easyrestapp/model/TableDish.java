package com.example.easyrestapp.model;

import com.example.easyrestapp.model.Dish;

import java.util.ArrayList;

public class TableDish {

    public Dish dish;
    public ArrayList<String> ingredients;
    public String comments;
    public boolean checked;

    public TableDish(Dish d, String comments) {
        this.dish=d;
        if(d.getType().equals("Start"))
            this.dish.type = "F";
        else if (d.getType().equals("Main"))
            this.dish.type = "M";
        else
            this.dish.type = "X";
        this.dish.price = d.getPrice();
        this.comments = comments;
    }

    public boolean isChacked() {
        return checked;
    }

    public void setChacked(boolean chacked) {
        this.checked = chacked;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
