package com.example.easyrestapp.model;

public class Drink {
    String id;
    String drinkName;
    String drinkCategory;
    String drinkDescription;
    String drinkImage;
    String possibleChanges;
    Double drinkPrice;
    String RestaurantName;

    public Drink(String id, String drinkName, String drinkCategory, String drinkDescription, String drinkImage, String possibleChanges, Double drinkPrice, String restaurantName) {
        this.id = id;
        this.drinkName = drinkName;
        this.drinkCategory = drinkCategory;
        this.drinkDescription = drinkDescription;
        this.drinkImage = drinkImage;
        this.possibleChanges = possibleChanges;
        this.drinkPrice = drinkPrice;
        this.RestaurantName = restaurantName;
    }
    public Drink() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public void setDrinkName(String drinkName) {
        this.drinkName = drinkName;
    }

    public String getDrinkCategory() {
        return drinkCategory;
    }

    public void setDrinkCategory(String drinkCategory) {
        this.drinkCategory = drinkCategory;
    }

    public String getDrinkDescription() {
        return drinkDescription;
    }

    public void setDrinkDescription(String drinkDescription) {
        this.drinkDescription = drinkDescription;
    }

    public String getDrinkImage() {
        return drinkImage;
    }

    public void setDrinkImage(String drinkImage) {
        this.drinkImage = drinkImage;
    }

    public String getPossibleChanges() {
        return possibleChanges;
    }

    public void setPossibleChanges(String possibleChanges) {
        this.possibleChanges = possibleChanges;
    }

    public Double getDrinkPrice() {
        return drinkPrice;
    }

    public void setDrinkPrice(Double drinkPrice) {
        this.drinkPrice = drinkPrice;
    }

    public String getRestaurantName() {
        return RestaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        RestaurantName = restaurantName;
    }
}
