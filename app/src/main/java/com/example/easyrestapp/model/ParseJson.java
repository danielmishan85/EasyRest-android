package com.example.easyrestapp.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ParseJson {


    public static ArrayList<Dish> parseDishesFromJson(String json) {
        ArrayList<Dish> dishes = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Dish dish = new Dish();
                dish.dishId = jsonObject.getString("_id");
                dish.dishName = jsonObject.getString("dishName");
                dish.dishCategory = jsonObject.getString("dishCategory");
                dish.dishDescription = jsonObject.getString("dishDescription");
                dish.dishImage = jsonObject.getString("dishImage");
                JSONArray possibleChangesArray = jsonObject.getJSONArray("possibleChanges");
                ArrayList<String> possibleChanges = new ArrayList<>();
                for (int j = 0; j < possibleChangesArray.length(); j++) {
                    possibleChanges.add(possibleChangesArray.getString(j));
                }
                dish.possibleChanges = possibleChanges;
                dish.prepBar = jsonObject.getString("prepBar");
                dish.estimatedPrepTimeRegular = jsonObject.getDouble("estimatedPrepTimeRegular");
                dish.estimatedPrepTimeDuringRushHour = jsonObject.getDouble("estimatedPrepTimeDuringRushHour");
                dish.isGlutenFree = jsonObject.getBoolean("isGlutenFree");
                dish.isVegan = jsonObject.getBoolean("isVegan");
                dish.isVegetarian = jsonObject.getBoolean("isVegetarian");
                dish.isLactoseFree = jsonObject.getBoolean("isLactoseFree");
                dish.dishPrice = jsonObject.getDouble("dishPrice");
                dish.restaurantName = jsonObject.getString("ResturantName");
                dishes.add(dish);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dishes;
    }


    public static ArrayList<Table> parseTablesFromJson(String json) {
        ArrayList<Table> tables = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++)
                tables.add(parseTable(jsonArray.getJSONObject(i),true));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tables;
    }


    public static ArrayList<ClosedTable> parseClosedTablesFromJson(String json) {
        ArrayList<ClosedTable> closedTables = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ClosedTable closedTable = new ClosedTable();
                closedTable.id = jsonObject.getString("_id");
                closedTable.t = parseTable(jsonObject,false);
                closedTable.closeTime = jsonObject.getString("closeTime");
                closedTable.tip = jsonObject.getDouble("pTip");
                closedTable.paymentArray = parsePaymentArray(jsonObject.getJSONArray("payment"));
                closedTables.add(closedTable);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return closedTables;
    }

    // parseTable get 2 values : openTable -> a boolean that shows us if we work on a regular open table or on a close table because open table has more values
    private static Table parseTable(JSONObject jsonTable,boolean openTable) throws JSONException {
        JSONObject jsonObject = jsonTable;
        Table table = new Table();
        table.id = jsonObject.getString("_id");
        table.openTime = jsonObject.getString("openTime");
        table.tableNumber = jsonObject.getString("numTable");
        table.numberOfPeople = jsonObject.getInt("numberOfPeople");
        table.avgPerPerson = jsonObject.getDouble("avgPerPerson");
        table.restaurantName = jsonObject.getString("ResturantName");
        table.gluten = jsonObject.getBoolean("gluten");
        table.lactose = jsonObject.getBoolean("lactuse");
        table.veggie = jsonObject.getBoolean("isVegi");
        table.vegan = jsonObject.getBoolean("isVagan");
        table.notes = jsonObject.getString("notes");
        table.others = jsonObject.getString("others");

        JSONArray orderListArray = jsonObject.getJSONArray("dishArray");
        List<TableDish> orderList = new ArrayList<>();
        for (int j = 0; j < orderListArray.length(); j++) {
            orderList.add(parseTableDish(orderListArray.getJSONObject(j),openTable));
        }
        table.orderList=orderList;

        if(openTable) {
            table.update = jsonObject.getString("udate");
            table.fire = jsonObject.getBoolean("fire");
            table.askForWaiter = jsonObject.getBoolean("askedForwaiter");
            table.askForWaiter = jsonObject.getBoolean("askedForBill");
            table.totalPrice = jsonObject.getDouble("TotalPrice");
            table.leftToPay = jsonObject.getDouble("leftToPay");

        }

        return table;
    }

    // parseTableDish get 2 values : openTable -> a boolean that shows us if we work on a regular open table or on a close table because open table has more values
    private static TableDish parseTableDish(JSONObject orderListObject,boolean openTable) throws JSONException {

        TableDish tableDish = new TableDish();
        Dish dish = new Dish();

        dish.setDishId(orderListObject.getString("dishId"));
        tableDish.setDish(dish);
        tableDish.amount = orderListObject.getInt("amount");
        tableDish.setId(orderListObject.getString("_id"));
        tableDish.firstOrMain = orderListObject.getString("firstOrMain");
        if(orderListObject.getString("readyTime")!=null)
            tableDish.readyTime = orderListObject.getString("readyTime"); // Change if necessary
        tableDish.allTogether = orderListObject.getBoolean("allTogether");
        tableDish.orderTime = orderListObject.getString("orderTime"); // Change if necessary
        tableDish.ready = orderListObject.getBoolean("ready");

        if(openTable) {
            ArrayList<String> commentsList = new ArrayList<>();
            JSONArray commentsListJson = orderListObject.getJSONArray("changes");
            for (int k = 0; k < commentsListJson.length(); k++) {
                String commentsListObject = commentsListJson.getString(k);
                commentsList.add(commentsListObject);
            }
            tableDish.comments = commentsList;

        }
        return tableDish;
    }

    public static ArrayList<Drink> parseDrinksFromJson(String json) {
        ArrayList<Drink> drinks = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Drink drink = new Drink();
                drink.setId(jsonObject.getString("_id"));
                drink.setDrinkName(jsonObject.getString("drinkName"));
                drink.setDrinkCategory(jsonObject.getString("drinkCategory"));
                drink.setDrinkImage(jsonObject.getString("drinkImage"));
                JSONArray possibleChangesArray = jsonObject.getJSONArray("possibleChanges");
                ArrayList<String> possibleChanges = new ArrayList<>();
                for (int j = 0; j < possibleChangesArray.length(); j++) {
                    possibleChanges.add(possibleChangesArray.getString(j));
                }
                drink.setDrinkPrice(jsonObject.getDouble("drinkPrice"));
                drink.setRestaurantName(jsonObject.getString("ResturantName"));
                drinks.add(drink);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return drinks;
    }

    private static TableDrink parseTableDrink(JSONObject orderListObject,boolean openTable) throws JSONException {

        TableDrink tableDrink = new TableDrink();
        Drink drink = new Drink();
        if(openTable) {
            ArrayList<String> commentsList = new ArrayList<>();
            JSONArray commentsListJson = orderListObject.getJSONArray("changes");
            for (int k = 0; k < commentsListJson.length(); k++) {
                String commentsListObject = commentsListJson.getString(k);
                commentsList.add(commentsListObject);
            }
            tableDrink.comments = commentsList;
        }
        drink.setId(orderListObject.getString("drinkId"));
        tableDrink.setDrink(drink);
        tableDrink.setAmount(orderListObject.getInt("amount"));
        tableDrink.setId(orderListObject.getString("_id"));
        tableDrink.setReady(orderListObject.getBoolean("ready"));
        tableDrink.setPrice(orderListObject.getDouble("price"));

        return tableDrink;
    }


    private static ArrayList<Payment> parsePaymentArray(JSONArray jsonPayments) throws JSONException {
        ArrayList<Payment> paymentArray = new ArrayList<>();
        for (int i = 0; i < jsonPayments.length(); i++) {
            JSONObject jsonPayment = jsonPayments.getJSONObject(i);
            Payment payment = new Payment();
            payment.paymentMethod = jsonPayment.getString("paymentMethod");
            payment.price = jsonPayment.getDouble("amountPaid");
            paymentArray.add(payment);
        }
        return paymentArray;
    }
}
