package com.example.easyrestapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.easyrestapp.model.Model;
import com.example.easyrestapp.model.ServerConnection;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Button kitchen_btn = this.findViewById(R.id.kitchen_btn);
        Button waiter_btn = this.findViewById(R.id.waiter_btn);




        waiter_btn.setOnClickListener((v)->{
            Model.instance().getAllDrinks();
            Intent i= new Intent(this,WaiterActivity.class);
            startActivity(i);

        });

        kitchen_btn.setOnClickListener((v)->{
            Intent i= new Intent(this,KitchenActivity.class);
            startActivity(i);
        });

    }




}