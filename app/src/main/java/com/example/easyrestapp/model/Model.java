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

        return parseClosedTablesFromJson(closedTables);
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
                Log.d("server","closed id: "+ closedTable.id);
                closedTable.t = parseTable(jsonObject,false);
                closedTable.closeTime = jsonObject.getString("closeTime");
                closedTable.tip = jsonObject.getDouble("pTip");
                Log.d("server","closed time: "+ closedTable.closeTime +","+closedTable.tip);

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

        if(openTable) {
            table.update = jsonObject.getString("udate");
            table.fire = jsonObject.getBoolean("fire");
            table.askForWaiter = jsonObject.getBoolean("askedForwaiter");

        }
        table.tableNumber = jsonObject.getString("numTable");
        table.numberOfPeople = jsonObject.getInt("numberOfPeople");
        table.avgPerPerson = jsonObject.getDouble("avgPerPerson");
        table.restaurantName = jsonObject.getString("ResturantName");
        table.gluten = jsonObject.getBoolean("gluten");
        table.lactose = jsonObject.getBoolean("lactuse");
        table.veggie = jsonObject.getBoolean("isVegi");
//       table.notes = jsonObject.getString("notes");
        table.others = jsonObject.getString("others");

        JSONArray orderListArray = jsonObject.getJSONArray("dishArray");
        List<TableDish> orderList = new ArrayList<>();
        for (int j = 0; j < orderListArray.length(); j++) {
            orderList.add(parseTableDish(orderListArray.getJSONObject(j),openTable));
        }
        table.orderList=orderList;
        return table;
    }

    // parseTableDish get 2 values : openTable -> a boolean that shows us if we work on a regular open table or on a close table because open table has more values
    private static TableDish parseTableDish(JSONObject orderListObject,boolean openTable) throws JSONException {

            TableDish tableDish = new TableDish();
            Dish dish = new Dish();
            if(openTable) {
                ArrayList<String> commentsList = new ArrayList<>();
                JSONArray commentsListJson = orderListObject.getJSONArray("changes");
                for (int k = 0; k < commentsListJson.length(); k++) {
                    String commentsListObject = commentsListJson.getString(k);
                    commentsList.add(commentsListObject);
                }
                tableDish.comments = commentsList;

            }
            dish.setDishId(orderListObject.getString("dishId"));
            tableDish.setDish(dish);
            tableDish.amount = orderListObject.getInt("amount");
            tableDish.setId(orderListObject.getString("_id"));
            tableDish.firstOrMain = orderListObject.getInt("firstOrMain");
            if(orderListObject.getString("readyTime")!=null)
                tableDish.readyTime = orderListObject.getString("readyTime"); // Change if necessary
            tableDish.allTogether = orderListObject.getBoolean("allTogether");
            //tableDish.comments=orderListObject.get

            tableDish.orderTime = orderListObject.getString("orderTime"); // Change if necessary
            tableDish.ready = orderListObject.getBoolean("ready");

        return tableDish;
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
