package com.example.easyrestapp.model;

import com.example.easyrestapp.model.Table;

public class Order {
    public Table table;
    public String time;
    public Order(Table table, String time)
    {
        this.table = table;
        this.time = time;
    }


}
