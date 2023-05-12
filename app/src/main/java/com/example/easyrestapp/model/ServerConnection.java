package com.example.easyrestapp.model;

import android.util.Log;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ServerConnection {

    //GET - get all category list
    public static CompletableFuture<String> categoryList() {
        String url = "http://10.0.2.2:3001/dish/categoryList";
        CompletableFuture<String> future = new CompletableFuture<>();

        getRequest(url, new RequestCallback() {
            @Override
            public void onSuccess(String response) {
                future.complete(response);
            }

            @Override
            public void onFailure(String error) {
                future.completeExceptionally(new Exception(error));
            }
        });

        return future;
    }


    //POST - get list of dishes by his category
    public static CompletableFuture<String> getDishByCategory(String dishCategory) {
        String postUrl = "http://10.0.2.2:3001/dish/getByCategory";
        CompletableFuture<String> future = new CompletableFuture<>();
        String postBody = "{\n" +
                "    \"dishCategory\": \"" + dishCategory + "\"\n" +
                "}";


        try {
            postRequest(postUrl, postBody, new RequestCallback() {
                @Override
                public void onSuccess(String response) {
                    future.complete(response);
                }

                @Override
                public void onFailure(String error) {
                    future.completeExceptionally(new Exception(error));
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        return future;

    }


    //POST - add a dish to specific table
    public static void addDishToOrder( String tableId,TableDish td)
    {
        String postUrl= "http://10.0.2.2:3001/openTable/addToOrder";
        String postBody = "{\n" +
                "    \"tableId\": \"" + tableId + "\",\n" +
                "    \"dishArray\": [\n" +
                "        {\n" +
                "            \"dishid\": \"" + td.dish.dishId + "\",\n" +
                "            \"amount\": " + td.amount + ",\n" +
                "            \"firstOrMain\": \"" + td.firstOrMain + "\",\n" +
                "            \"allTogether\": " + td.allTogether + "\n" +
                "        }\n" +
                "    ]\n" +
                "}";

//        try {
//          //  postRequest(postUrl, postBody);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public static CompletableFuture<String> getAllOpenTables(){

        String url = "http://10.0.2.2:3001/openTable/getTables";
        CompletableFuture<String> future = new CompletableFuture<>();
        getRequest(url, new RequestCallback() {
            @Override
            public void onSuccess(String response) {
                future.complete(response);
            }

            @Override
            public void onFailure(String error) {
                future.completeExceptionally(new Exception(error));
            }
        });

        return future;
    }


    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    static void postRequest(String postUrl,String postBody,RequestCallback callback) throws IOException {

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, postBody);

        Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(e.getMessage());
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody = response.body().string();
                callback.onSuccess(responseBody);
                //Log.d("server", "getRequest" + responseBody);
            }
        });
    }

    interface RequestCallback {
        void onSuccess(String response);
        void onFailure(String error);
    }

    static void getRequest(String postUrl, RequestCallback callback) {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(postUrl)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(e.getMessage());
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody = response.body().string();
                callback.onSuccess(responseBody);
                Log.d("server", "getRequest" + responseBody);
            }
        });
    }


}
