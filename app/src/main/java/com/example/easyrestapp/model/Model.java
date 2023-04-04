package com.example.easyrestapp.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Model {

    private static final Model _instance = new Model();
    public List<Dish> menu;
    public List<Table> tables;

    public static Model instance(){
        return _instance;
    }

    private Model() {
        menu = new ArrayList<>();
        //menu
        for (int i=0;i<10;i++){
            menu.add(new Dish("Dish number " + Integer.toString(i), "Start", new ArrayList<>()));
        }
        for (int i=10;i<20;i++){
            menu.add(new Dish("Dish number " + Integer.toString(i), "Main", new ArrayList<>()));
        }
        for (int i=20;i<30;i++){
            menu.add(new Dish("Dish number " + Integer.toString(i), "Dessert", new ArrayList<>()));
        }
        for (int i=30;i<34;i++){
            menu.add(new Dish("Dish number " + Integer.toString(i), "Drink", new ArrayList<>()));
        }
        //tables
        tables = new ArrayList<>();
        for (int i=0;i<20;i++){
            tables.add(new Table(String.valueOf(i), "Note " + i, String.valueOf(i+1), i*10.0, (int)(i*10.0)/(i+1)));
        }
    }

    public List<Dish> getMenu() {
        return menu;
    }

    public List<Table> getTables() {
        return tables;
    }
}
