package com.example.easyrestapp.model;

import java.util.List;

public class Table {


    public String id;
    public String tableNumber;
    public String openTime;
    public String update;
    public int numberOfPeople;
    public Double totalPrice;
    public double avgPerPerson;
    public List<TableDish> orderList;
    public List<TableDrink> drinkArray;
    public boolean fire;
    public boolean gluten;
    public boolean lactose;
    public boolean vegan;
    public boolean veggie;
    public String others;
    public String notes;
    public boolean askForWaiter;
    public String restaurantName;
    public Double leftToPay;

    public Table() {
    }

    public Table(String id, String tableNumber, String openTime, String update, int numberOfPeople, Double totalPrice, double avgPerPerson, List<TableDish> orderList, List<TableDrink> drinkArray, boolean fire, boolean gluten, boolean lactose, boolean vegan, boolean veggie, String others, String notes, boolean askForWaiter, String restaurantName, Double leftToPay) {
        this.id = id;
        this.tableNumber = tableNumber;
        this.openTime = openTime;
        this.update = update;
        this.numberOfPeople = numberOfPeople;
        this.totalPrice = totalPrice;
        this.avgPerPerson = avgPerPerson;
        this.orderList = orderList;
        this.drinkArray = drinkArray;
        this.fire = fire;
        this.gluten = gluten;
        this.lactose = lactose;
        this.vegan = vegan;
        this.veggie = veggie;
        this.others = others;
        this.notes = notes;
        this.askForWaiter = askForWaiter;
        this.restaurantName = restaurantName;
        this.leftToPay = leftToPay;
    }

//    public Table(String tableNumber, int numberOfPeople, String restaurantName, boolean gluten, boolean lactose, boolean vegan, boolean veggie, String others, String notes) {
//        this.tableNumber = tableNumber;
//        this.numberOfPeople = numberOfPeople;
//        this.restaurantName = restaurantName;
//        this.gluten = gluten;
//        this.lactose = lactose;
//        this.vegan = vegan;
//        this.veggie = veggie;
//        this.others = others;
//        this.notes = notes;
//    }

//    public Table(String id, String openTime, String update, String tableNumber, int numberOfPeople, double avgPerPerson, String restaurantName, boolean fire, boolean gluten, boolean lactose, boolean veggie, boolean vegan, String others, String notes, boolean askForWaiter, List<TableDish> orderList) {
//        this.id = id;
//        this.openTime = openTime;
//        this.update = update;
//        this.tableNumber = tableNumber;
//        this.numberOfPeople = numberOfPeople;
//        this.avgPerPerson = avgPerPerson;
//        this.restaurantName = restaurantName;
//        this.fire = fire;
//        this.gluten = gluten;
//        this.lactose = lactose;
//        this.vegan=vegan;
//        this.veggie = veggie;
//        this.others = others;
//        this.askForWaiter = askForWaiter;
//        this.orderList = orderList;
//        this.notes=notes;
//    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<TableDish> getOrderList() {
        return orderList;
    }

    public boolean isVegan() {
        return vegan;
    }

    public void setVegan(boolean vegan) {
        this.vegan = vegan;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public double getAvgPerPerson() {
        return avgPerPerson;
    }

    public void setAvgPerPerson(double avgPerPerson) {
        this.avgPerPerson = avgPerPerson;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public boolean isFire() {
        return fire;
    }

    public void setFire(boolean fire) {
        this.fire = fire;
    }

    public boolean isGluten() {
        return gluten;
    }

    public void setGluten(boolean gluten) {
        this.gluten = gluten;
    }

    public boolean isLactose() {
        return lactose;
    }

    public void setLactose(boolean lactose) {
        this.lactose = lactose;
    }

    public boolean isVeggie() {
        return veggie;
    }

    public void setVeggie(boolean veggie) {
        this.veggie = veggie;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public boolean isAskForWaiter() {
        return askForWaiter;
    }

    public void setAskForWaiter(boolean askForWaiter) {
        this.askForWaiter = askForWaiter;
    }

    public void setOrderList(List<TableDish> orderList) {
        this.orderList = orderList;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<TableDrink> getDrinkArray() {
        return drinkArray;
    }

    public void setDrinkArray(List<TableDrink> drinkArray) {
        this.drinkArray = drinkArray;
    }

    public Double getLeftToPay() {
        return leftToPay;
    }

    public void setLeftToPay(Double leftToPay) {
        this.leftToPay = leftToPay;
    }

    @Override
    public String toString() {
        return "Table{" +
                "id='" + id + '\'' +
                ", openTime='" + openTime + '\'' +
                ", update='" + update + '\'' +
                ", tableNumber='" + tableNumber + '\'' +
                ", numberOfPeople=" + numberOfPeople +
                ", avgPerPerson=" + avgPerPerson +
                ", restaurantName='" + restaurantName + '\'' +
                ", fire=" + fire +
                ", gluten=" + gluten +
                ", lactose=" + lactose +
                ", isVeggie=" + veggie +
                ", others=" + others +
                ", askForWaiter=" + askForWaiter +
                ", orderList=" + orderList +
                '}';
    }
}
