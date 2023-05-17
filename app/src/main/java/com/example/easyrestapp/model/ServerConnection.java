package com.example.easyrestapp.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
                Log.d("server connection categoryList","categoryList success with response: "+response);
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
                    Log.d("server connection getDishByCategory","getDishByCategory success with response: "+response);

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


    public static CompletableFuture<String> addTable(Table table) {
        String postUrl = "http://10.0.2.2:3001/openTable/open";
        String postBody = "{\n" +
                "    \"numTable\": " + table.getTableNumber() + ",\n" +
                "    \"numberOfPeople\": " + table.getNumberOfPeople() + ",\n" +
                "    \"gluten\": " + table.isGluten() + ",\n" +
                "    \"lactuse\": " + table.isLactose() + ",\n" +
                "    \"isVagan\": " + table.isVegan() + ",\n" +
                "    \"isVegi\": " + table.isVeggie() + ",\n" +
                "    \"others\": \"" + table.getOthers() + "\",\n" +
                "    \"notes\": \"" + table.getNotes() + "\",\n" +
                "    \"ResturantName\": \"" + table.getRestaurantName() + "\"\n" +
                "}";

        CompletableFuture<String> future = new CompletableFuture<>();

        try {
            postRequest(postUrl, postBody, new RequestCallback() {
                @Override
                public void onSuccess(String response) {
                    future.complete(response);

                    Log.d("server connection addTable","addTable finish with response: "+response);
                }

                @Override
                public void onFailure(String error) {
                    future.complete(error);
                    //Toast.makeText(MyApplication.getMyContext(), "Table is unavailable: " + error, Toast.LENGTH_SHORT).show();
                    Log.d("server connection addTable","addTable failed with response: "+error);
                    new Exception(error);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return future;
    }


    public static CompletableFuture<Boolean> addDishToOrder(String tableId, TableDish td) {
        String postUrl = "http://10.0.2.2:3001/openTable/addToOrder";

        JSONObject dishObject = new JSONObject();
        try {
            dishObject.put("dishid", td.dish.dishId);
            dishObject.put("amount", td.amount);
            dishObject.put("changes", td.getComments().get(0));
            dishObject.put("firstOrMain", td.firstOrMain);
            dishObject.put("allTogether", td.allTogether);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray dishArray = new JSONArray();
        dishArray.put(dishObject);

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("tableId", tableId);
            requestBody.put("dishArray", dishArray);
            requestBody.put("drinkArray", new JSONArray());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        CompletableFuture<Boolean> future = new CompletableFuture<>();

        try {
            postRequest(postUrl, requestBody.toString(), new RequestCallback() {
                @Override
                public void onSuccess(String response) {
                    future.complete(true);
                    Log.d("server connection", "addDishToOrder finish with response: " + response);
                }

                @Override
                public void onFailure(String error) {
                    future.complete(false);
                    Log.d("server connection", "addDishToOrder failed with response: " + error);
                    new Exception(error);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return future;
    }




    public static CompletableFuture<Boolean> addDrinkToOrder(String tableId, TableDrink td) {
        String postUrl = "http://10.0.2.2:3001/openTable/addToOrder";

        JSONObject drinkObject = new JSONObject();
        try {
            drinkObject.put("drinkId", td.drink.getId());
            drinkObject.put("amount", td.amount);
            drinkObject.put("changes", td.comments.get(0));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray drinkArray = new JSONArray();
        drinkArray.put(drinkObject);

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("tableId", tableId);
            requestBody.put("dishArray", new JSONArray());
            requestBody.put("drinkArray", drinkArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        CompletableFuture<Boolean> future = new CompletableFuture<>();

        try {
            postRequest(postUrl, requestBody.toString(), new RequestCallback() {
                @Override
                public void onSuccess(String response) {
                    future.complete(true);
                    Log.d("server connection", "addDrinkToOrder finish with response: " + response);
                }

                @Override
                public void onFailure(String error) {
                    future.complete(false);
                    Log.d("server connection", "addDrinkToOrder failed with response: " + error);
                    new Exception(error);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return future;
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


    public static CompletableFuture<String> getAllClosedTables(){

        String url = "http://10.0.2.2:3001/closeTable/getCloseTables";
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


    public static CompletableFuture<String> getDrinkByCategory(String drinkCategory) {
        String postUrl = "http://10.0.2.2:3001/drink/getByCategory";
        CompletableFuture<String> future = new CompletableFuture<>();
        String postBody = "{\n" +
                "    \"drinkCategory\": \"" + drinkCategory + "\"\n" +
                "}";

        try {
            postRequest(postUrl, postBody, new RequestCallback() {
                @Override
                public void onSuccess(String response) {
                    Log.d("server connection getDrinkByCategory", "getDrinkByCategory success with response: " + response);

                    future.complete(response);
                }

                @Override
                public void onFailure(String error) {
                    future.completeExceptionally(new Exception(error));
                }
            });
        } catch (Exception e) {
            future.completeExceptionally(e);
        }

        return future;
    }

    public static CompletableFuture<String> getCategoryDrinksList() {
        String getUrl = "http://10.0.2.2:3001/drink/categoryList";
        CompletableFuture<String> future = new CompletableFuture<>();

        try {
            getRequest(getUrl, new RequestCallback() {
                @Override
                public void onSuccess(String response) {
                    Log.d("server connection getCategoryDrinksList", "getCategoryDrinksList success with response: " + response);
                    future.complete(response);
                }

                @Override
                public void onFailure(String error) {
                    future.completeExceptionally(new Exception(error));
                }
            });
        } catch (Exception e) {
            future.completeExceptionally(e);
        }

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
