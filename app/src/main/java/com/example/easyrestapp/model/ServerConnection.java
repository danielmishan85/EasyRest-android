package com.example.easyrestapp.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class ServerConnection {
    private ExecutorService executorService;
    private String serverAddress;
    private int serverPort;
    private Socket socket;

    public ServerConnection(final String serverAddress, final int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                // Establish the connection with the server
                socket = new Socket(serverAddress, serverPort);
                Log.d("server", "Connection successful!");
            } catch (UnknownHostException e) {
                // Handle unknown host exception
                Log.d("server", e + "");
            } catch (IOException e) {
                // Handle IO exception
                Log.d("server", e + "");
            }
        });
    }

    public void getDishesByCategory(final String category, final String restaurantName) {
        executorService.execute(() -> {
            try {
                // Send the request to the server
                JSONObject request = new JSONObject();
                request.put("dishCategory", category);
                request.put("ResturantName", restaurantName);

                OutputStream outputStream = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(outputStream);
                writer.println(request.toString());
                writer.flush();

                // Receive the response from the server
                InputStream inputStream = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String response = reader.readLine();

                // Print the JSON string in the log
                Log.d("server", "Response from server: " + response);

                // TODO: Parse the JSON string and handle the result

                // Close the streams
                writer.close();
                reader.close();
                inputStream.close();
                outputStream.close();

            } catch (UnknownHostException e) {
                // Handle unknown host exception
                Log.d("server", e + "");
            } catch (IOException e) {
                // Handle IO exception
                Log.d("server", e + "");
            } catch (JSONException e) {
                // Handle JSON exception
                Log.d("server", e + "");
            }
        });
    }

    // Other methods for sending and receiving data from the server go here
}
