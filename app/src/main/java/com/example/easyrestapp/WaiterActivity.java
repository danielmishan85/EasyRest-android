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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WaiterActivity extends AppCompatActivity {

    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiter);

        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.main_navhost);
        navController = navHostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(this,navController);

        ExecutorService es = Executors.newSingleThreadExecutor();
        es.execute(() -> {
            boolean notCalled = true;
            while (notCalled) {
                if (Model.instance().isWaiterCalled("64627605422e4c1341d22a30")) {
                    //notCalled = false;
                    runOnUiThread(() -> {
                        // Create and show the popup
                        AlertDialog.Builder builder = new AlertDialog.Builder(WaiterActivity.this);
                        builder.setTitle("Waiter Called");
                        // Set other properties of the dialog as needed
                        builder.show();
                    });

                    try {
                        Thread.sleep(10000); // Sleep for 10 seconds
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        Thread.sleep(1000); // Sleep for 1 second
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

}