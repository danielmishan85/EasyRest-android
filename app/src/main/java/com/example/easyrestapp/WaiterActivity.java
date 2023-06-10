package com.example.easyrestapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.easyrestapp.model.Model;
import com.example.easyrestapp.model.ServerConnection;
import com.example.easyrestapp.model.Table;
import com.example.easyrestapp.model.TableDish;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public class WaiterActivity extends AppCompatActivity {

    NavController navController;
    AtomicReference<ArrayList<Table>> atomicArrayList;
    private static final Object modelLock = new Object();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiter);

        //        dishOnline: dishOnline,
        //        estimatedPrepTime: estimatedTime,

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.main_navhost);
        navController = navHostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController);


        ExecutorService es = Executors.newSingleThreadExecutor();
        es.execute(() -> {
            while (true) {
                List<Table> openTables = Model.instance().getAllOpenTables(); // Move the getAllOpenTables() call outside the while loop
                for (Table t : openTables) {
                    String value = Model.instance().isWaiterCalledOrAskedForBill(t.getId());

                    switch (value){
                        case "Waiter":
                            runOnUiThread(() -> {
                                AlertDialog.Builder builder = new AlertDialog.Builder(WaiterActivity.this);
                                builder.setTitle("Waiter Called to table number: " + t.getTableNumber());
                                builder.show();
                            });

                            try {
                                Thread.sleep(10000); // Sleep for 10 seconds
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

//                        case "Bill":
//                            runOnUiThread(() -> {
//                                AlertDialog.Builder builder = new AlertDialog.Builder(WaiterActivity.this);
//                                builder.setTitle("Table number " + t.getTableNumber() + " is asking for Bill");
//                                builder.show();
//                            });
//
//                            try {
//                                Thread.sleep(10000); // Sleep for 10 seconds
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
                        case "None":
                            try {
                                Thread.sleep(1000); // Sleep for 1 second
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                    }

                }
            }
        });


    }

}