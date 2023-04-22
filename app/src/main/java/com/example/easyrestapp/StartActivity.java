package com.example.easyrestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

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
            Intent i= new Intent(this,KitchenActivity.class);
            startActivity(i);
        });

    }
}