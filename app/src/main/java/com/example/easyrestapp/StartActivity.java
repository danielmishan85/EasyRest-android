package com.example.easyrestapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StartActivity extends AppCompatActivity {

    //------------------getByCategory-----------------------//
    //POST EXAMPLE
    /*
    public String postUrl= "http://10.0.2.2:3001/dish/getByCategory";
    public String postBody="{\n" +
            "    \"dishCategory\": \"starter\"\n" +
            "}";

     */

    //------------------addToOrder-----------------------//
    //POST EXAMPLE

    public String postUrl= "http://10.0.2.2:3001/openTable/addToOrder";
    public String postBody = "{\n" +
            "    \"tableId\": \"644ecbee5062641bd0352946\",\n" +
            "    \"dishArray\": [\n" +
            "        {\n" +
            "            \"dishid\": \"643ef91662cdc37f5379e502\",\n" +
            "            \"amount\": 1,\n" +
            "            \"firstOrMain\": \"F\",\n" +
            "            \"allTogether\": true\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    //------------------categoryList-----------------------//
    //GET EXAMPLE
    //public String postUrl= "http://10.0.2.2:3001/dish/categoryList";



    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

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
            try {
                postRequest(postUrl,postBody);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Intent i= new Intent(this,KitchenActivity.class);
            startActivity(i);
        });

    }

    void postRequest(String postUrl,String postBody) throws IOException {

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, postBody);

        Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("server", e.getMessage() );
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("server", "litalllll" + response.body().string());
                Log.d("server","litalllll" + response + "");
                Log.d("server","litalllll" + response.body() + "");
            }
        });
    }

    void getRequest(String postUrl) throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(postUrl)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("server", e.getMessage() );
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("server", "get" + response.body().string());
                Log.d("server","get" + response + "");
                Log.d("server","fet" + response.body() + "");
            }
        });
    }
}