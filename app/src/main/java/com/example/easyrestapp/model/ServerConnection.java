package com.example.easyrestapp.model;

import android.util.Log;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerConnection {
    private ExecutorService executorService;
    private String serverAddress;
    private int serverPort;
    private HttpURLConnection connection;
    private Gson gson;

    public ServerConnection(final String serverAddress, final int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        executorService = Executors.newSingleThreadExecutor();
        gson = new Gson();
        executorService.execute(() -> {
            try {
                // Establish the connection with the server
                connection = (HttpURLConnection) new URL("http://" + serverAddress + ":" + serverPort).openConnection();
                Log.d("server", "Connection successful!");
            } catch (IOException e) {
                // Handle IO exception
                Log.d("server", e + "");
            }
        });
    }

    public void getDishesByCategory(String category, String restaurantName) {
        if (connection == null) {
            Log.d("server", "Connection not established!");
            return;
        }
        executorService.execute(() -> {
            try {
                // Construct request URL
                URL url = new URL("http://127.0.0.1:3001/getByCategory");

                // Open HTTP connection
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                // Set request method and headers
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                // Create request body
                RequestBody requestBody = new RequestBody(category, restaurantName);
                Gson gson = new Gson();
                String requestBodyJson = gson.toJson(requestBody);

                // Write request body to output stream
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(requestBodyJson.getBytes());
                outputStream.flush();
                outputStream.close();

                // Read response from input stream
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String response = bufferedReader.readLine();
                bufferedReader.close();
                inputStream.close();

                // Log response
                Log.d("server", response);
            } catch (IOException e) {
                // Handle IO exception
                Log.d("server", e + "");
            }
        });
    }

    private static class RequestBody {
        private String dishCategory;
        private String ResturantName;

        public RequestBody(String dishCategory, String ResturantName) {
            this.dishCategory = dishCategory;
            this.ResturantName = ResturantName;
        }
    }

}
