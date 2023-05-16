package com.example.easyrestapp.model;

import android.util.Log;
import android.widget.Toast;

import com.example.easyrestapp.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Model {

    private static final Model _instance = new Model();

    public static Model instance(){
        return _instance;
    }

    public boolean isWaiterCalled(String id){
        return getTableById(id).isAskForWaiter();
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


    public ArrayList<Drink> getAllDrinks(){
        ArrayList<Drink> arrayAllDrinks = new ArrayList<>();
        try {

            String categories= ServerConnection.getCategoryDrinksList().get();
            String[] categoryArray = categories.split(",");
            for (int i = 0; i < categoryArray.length; i++) {
                categoryArray[i] = categoryArray[i].replace("[", "").replace("]", "").replace("\"", "");
                arrayAllDrinks.addAll(getDrinksByCategory(categoryArray[i]));
            }
        } catch (Exception e) {
            e.getMessage();
        }

        return arrayAllDrinks;
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

        return ParseJson.parseTablesFromJson(tables);
    }

    public ArrayList<ClosedTable> getAllClosedTables(){

        String closedTables="";
        try {
            closedTables = ServerConnection.getAllClosedTables().get();
            Log.d("server","closed tables: "+ closedTables);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return ParseJson.parseClosedTablesFromJson(closedTables);
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


    public Table getTableByNumber(String tableNum){

        ArrayList<Table> tables = this.getAllOpenTables();
        for(Table table : tables)
        {
            if(table.getTableNumber().equals(tableNum)) {
                return table;

            }
        }
        return new Table();    }

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

    public void addNewTable(Table table){
        try {
            String response = ServerConnection.addTable(table).get();
            if(response.contains("message"))
            {
                Toast.makeText(MyApplication.getMyContext(), response, Toast.LENGTH_SHORT).show();
            }


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
        return ParseJson.parseDishesFromJson(dishes);
    }

    public ArrayList<Drink> getDrinksByCategory(String category) {

        String drinks="";
        try {
            drinks = ServerConnection.getDrinkByCategory(category).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ParseJson.parseDrinksFromJson(drinks);
    }






}
