package com.example.easyrestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.easyrestapp.model.ServerConnection;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Button kitchen_btn = this.findViewById(R.id.kitchen_btn);
        Button waiter_btn = this.findViewById(R.id.waiter_btn);

        waiter_btn.setOnClickListener((v)->{
            Intent i= new Intent(this,WaiterActivity.class);
            startActivity(i);

        });

        kitchen_btn.setOnClickListener((v)->{
            ServerConnection serverConnection = new ServerConnection("10.0.2.2", 3001);
            serverConnection.getDishesByCategory("starter","MyRest");
            Intent i= new Intent(this,KitchenActivity.class);
            startActivity(i);
        });

    }
}