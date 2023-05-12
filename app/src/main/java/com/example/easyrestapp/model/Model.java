package com.example.easyrestapp.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Model {

    private static final Model _instance = new Model();

    public static Model instance(){
        return _instance;
    }

    public ArrayList<Dish> getAllDishes(){
        ArrayList<Dish> arrayAllDishes = new ArrayList<>();
        try {

            String categories= ServerConnection.categoryList().get();
            String[] categoryArray = categories.split(",");
            for (int i = 0; i < categoryArray.length; i++) {
                categoryArray[i] = categoryArray[i].replace("[", "").replace("]", "").replace("\"", "");
                arrayAllDishes.addAll(getDishesByCategory(categoryArray[i]));
            }
        } catch (Exception e) {
            e.getMessage();
        }

        Log.d("server", arrayAllDishes.size() + "");
        return arrayAllDishes;
    }

    public ArrayList<Table> getAllOpenTables(){

        String tables="";
        try {
            tables = ServerConnection.getAllOpenTables().get();
            Log.d("server","tables: "+ tables);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return parseTablesFromJson(tables);
    }

    public Dish getDishById(String id){
        ArrayList<Dish> dishes = this.getAllDishes();
        for(Dish dish : dishes)
        {
            if(dish.getDishId().equals(id)) {
                Log.d("server",dish.toString());
                return dish;

            }
        }
        return new Dish();
    }

    public Table getTableById(String id){

        ArrayList<Table> tables = this.getAllOpenTables();
        for(Table table : tables)
        {
            if(table.getId().equals(id)) {
                return table;

            }
        }
        return new Table();    }

    public void addDishToOrder(TableDish td, String tableId){
        try {
            ServerConnection.addDishToOrder(tableId,td).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Dish> getDishesByCategory(String category) {

        String dishes="";
        try {
            dishes = ServerConnection.getDishByCategory(category).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return parseDishesFromJson(dishes);
    }

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
            Log.d("server", "" + jsonArray.length());

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Table table = new Table();
                table.id = jsonObject.getString("_id");
                table.openTime = jsonObject.getString("openTime");
                table.update = jsonObject.getString("udate");
                table.tableNumber = jsonObject.getString("numTable");
                table.numberOfPeople = jsonObject.getInt("numberOfPeople");
                table.avgPerPerson = jsonObject.getDouble("avgPerPerson");
                table.restaurantName = jsonObject.getString("ResturantName");
                table.fire = jsonObject.getBoolean("fire");
                table.gluten = jsonObject.getBoolean("gluten");
                table.lactose = jsonObject.getBoolean("lactuse");
                table.isVeggie = jsonObject.getBoolean("isVegi");
                table.others = jsonObject.getString("others");
                table.askForWaiter = jsonObject.getBoolean("askedForwaiter");

                JSONArray orderListArray = jsonObject.getJSONArray("dishArray");

                List<TableDish> orderList = new ArrayList<>();
                for (int j = 0; j < orderListArray.length(); j++) {
                    JSONObject orderListObject = orderListArray.getJSONObject(j);
                    TableDish tableDish = new TableDish();
                    Dish dish = new Dish();
                    dish.setDishId(orderListObject.getString("dishId"));
                    tableDish.setDish(dish);
                    tableDish.amount = orderListObject.getInt("amount");
                    tableDish.setId(orderListObject.getString("_id"));
                    tableDish.firstOrMain = orderListObject.getInt("firstOrMain");
                    if(orderListObject.getString("readyTime")!=null)
                        tableDish.readyTime = orderListObject.getString("readyTime"); // Change if necessary
                    tableDish.allTogether = orderListObject.getBoolean("allTogether");
                    tableDish.price = orderListObject.getInt("price");
                    tableDish.orderTime = orderListObject.getString("orderTime"); // Change if necessary
                    tableDish.ready = orderListObject.getBoolean("ready");
                    orderList.add(tableDish);
                }
                table.orderList = orderList;
                tables.add(table);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("server", "" + tables.size());

        return tables;
    }





//    private Model() {
//        menu = new ArrayList<>();
//        //menu
//        for (int i=0;i<10;i++){
//            menu.add(new Dish("Dish id " + (i+" "), i,"F",true,true,50,"time"));
//        }
//        for (int i=10;i<20;i++){
//            menu.add(new Dish("Dish id " + (i+" "), i,"F",true,true,50,"time"));
//        }
//        for (int i=20;i<30;i++){
//            menu.add(new Dish("Dish id " + (i+" "), i,"F",true,true,50,"time"));
//        }
//        for (int i=30;i<34;i++){
//            menu.add(new Dish("Dish id " + (i+" "), i,"F",true,true,50,"time"));
//        }
//        //tables
//        tables = new ArrayList<>();
//        for (int i=0;i<20;i++){
//            tables.add(new Table(String.valueOf(i), "Note " + i, String.valueOf(i+1), i*10.0, (int)(i*10.0)/(i+1)));
//        }
//    }


}
