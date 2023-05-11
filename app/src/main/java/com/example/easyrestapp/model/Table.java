package com.example.easyrestapp.model;

import java.util.ArrayList;
import java.util.List;

public class Table {


    public String id;
    public String openTime;
    public String update;
    public String tableNumber;
    public int numberOfPeople;
    public double avgPerPerson;
    public String restaurantName;
    public boolean fire;
    public boolean gluten;
    public boolean lactose;
    public boolean isVeggie;
    public ArrayList<String> comments;
    public boolean askForWaiter;
    public List<TableDish> orderList;

    public Table() {
    }

    public Table(String id, String openTime, String update, String tableNumber, int numberOfPeople, double avgPerPerson, String restaurantName, boolean fire, boolean gluten, boolean lactose, boolean isVeggie, ArrayList<String> comments, boolean askForWaiter, List<TableDish> orderList) {
        this.id = id;
        this.openTime = openTime;
        this.update = update;
        this.tableNumber = tableNumber;
        this.numberOfPeople = numberOfPeople;
        this.avgPerPerson = avgPerPerson;
        this.restaurantName = restaurantName;
        this.fire = fire;
        this.gluten = gluten;
        this.lactose = lactose;
        this.isVeggie = isVeggie;
        this.comments = comments;
        this.askForWaiter = askForWaiter;
        this.orderList = orderList;
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
        return isVeggie;
    }

    public void setVeggie(boolean veggie) {
        isVeggie = veggie;
    }

    public ArrayList<String> getComments() {
        return comments;
    }

    public void setComments(ArrayList<String> comments) {
        this.comments = comments;
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
                ", isVeggie=" + isVeggie +
                ", comments=" + comments +
                ", askForWaiter=" + askForWaiter +
                ", orderList=" + orderList +
                '}';
    }
}
