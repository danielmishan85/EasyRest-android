package com.example.easyrestapp.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Model {

    private static final Model _instance = new Model();
    public List<Dish> menu;
    public List<Table> tables;

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

    public List<Table> getAllTables(){
        return null;
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

        return null;
    }

    public void addDishToOrder(Dish dish, String tableId){

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
