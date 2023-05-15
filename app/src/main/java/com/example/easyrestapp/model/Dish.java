package com.example.easyrestapp.model;

import java.util.ArrayList;

public class Dish {

    public String dishId;
    public String dishName;
    public String dishCategory;
    public String dishDescription;
    public String dishImage;
    public ArrayList<String> possibleChanges;
    public String prepBar;
    public double estimatedPrepTimeRegular;
    public double estimatedPrepTimeDuringRushHour;
    public boolean isGlutenFree;
    public boolean isVegan;
    public boolean isVegetarian;
    public boolean isLactoseFree;
    public double dishPrice;
    public String restaurantName;
    //public int orderAmount;

    public Dish() {}


     public Dish(String dishId, String dishName, String dishCategory, String dishDescription, String dishImage, ArrayList<String> possibleChanges, String prepBar, double estimatedPrepTimeRegular, double estimatedPrepTimeDuringRushHour, boolean isGlutenFree, boolean isVegan, boolean isVegetarian, boolean isLactoseFree, double dishPrice, String restaurantName) {
        this.dishId = dishId;
        this.dishName = dishName;
        this.dishCategory = dishCategory;
        this.dishDescription = dishDescription;
        this.dishImage = dishImage;
        this.possibleChanges = possibleChanges;
        this.prepBar = prepBar;
        this.estimatedPrepTimeRegular = estimatedPrepTimeRegular;
        this.estimatedPrepTimeDuringRushHour = estimatedPrepTimeDuringRushHour;
        this.isGlutenFree = isGlutenFree;
        this.isVegan = isVegan;
        this.isVegetarian = isVegetarian;
        this.isLactoseFree = isLactoseFree;
        this.dishPrice = dishPrice;
        this.restaurantName = restaurantName;

    }
    public String getDishId() {
        return dishId;
    }

    public void setDishId(String dishId) {
        this.dishId = dishId;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getDishCategory() {
        return dishCategory;
    }

    public void setDishCategory(String dishCategory) {
        this.dishCategory = dishCategory;
    }

    public String getDishDescription() {
        return dishDescription;
    }

    public void setDishDescription(String dishDescription) {
        this.dishDescription = dishDescription;
    }

    public String getDishImage() {
        return dishImage;
    }

    public void setDishImage(String dishImage) {
        this.dishImage = dishImage;
    }

    public ArrayList<String> getPossibleChanges() {
        return possibleChanges;
    }

    public void setPossibleChanges(ArrayList<String> possibleChanges) {
        this.possibleChanges = possibleChanges;
    }

    public String getPrepBar() {
        return prepBar;
    }

    public void setPrepBar(String prepBar) {
        this.prepBar = prepBar;
    }

    public double getEstimatedPrepTimeRegular() {
        return estimatedPrepTimeRegular;
    }

    public void setEstimatedPrepTimeRegular(double estimatedPrepTimeRegular) {
        this.estimatedPrepTimeRegular = estimatedPrepTimeRegular;
    }

    public double getEstimatedPrepTimeDuringRushHour() {
        return estimatedPrepTimeDuringRushHour;
    }

    public void setEstimatedPrepTimeDuringRushHour(double estimatedPrepTimeDuringRushHour) {
        this.estimatedPrepTimeDuringRushHour = estimatedPrepTimeDuringRushHour;
    }

    public boolean isGlutenFree() {
        return isGlutenFree;
    }

    public void setGlutenFree(boolean glutenFree) {
        isGlutenFree = glutenFree;
    }

    public boolean isVegan() {
        return isVegan;
    }

    public void setVegan(boolean vegan) {
        isVegan = vegan;
    }

    public boolean isVegetarian() {
        return isVegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        isVegetarian = vegetarian;
    }

    public boolean isLactoseFree() {
        return isLactoseFree;
    }

    public void setLactoseFree(boolean lactoseFree) {
        isLactoseFree = lactoseFree;
    }

    public double getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(double dishPrice) {
        this.dishPrice = dishPrice;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "dishId='" + dishId + '\'' +
                ", dishName='" + dishName + '\'' +
                ", dishCategory='" + dishCategory + '\'' +
                ", dishDescription='" + dishDescription + '\'' +
                ", dishImage='" + dishImage + '\'' +
                ", possibleChanges=" + possibleChanges +
                ", prepBar='" + prepBar + '\'' +
                ", estimatedPrepTimeRegular=" + estimatedPrepTimeRegular +
                ", estimatedPrepTimeDuringRushHour=" + estimatedPrepTimeDuringRushHour +
                ", isGlutenFree=" + isGlutenFree +
                ", isVegan=" + isVegan +
                ", isVegetarian=" + isVegetarian +
                ", isLactoseFree=" + isLactoseFree +
                ", dishPrice=" + dishPrice +
                ", restaurantName='" + restaurantName + '\'' +
                '}';
    }
}
