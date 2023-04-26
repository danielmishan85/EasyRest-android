package com.example.easyrestapp.model;

import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerConnection {
    private ExecutorService executorService;


    public ServerConnection() {
        executorService = Executors.newSingleThreadExecutor();

    }

    public void connectToServer(final String serverAddress, final int serverPort) {

            executorService.execute(()->{
                try {
                Socket socket = new Socket(serverAddress, serverPort);
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

    // Other methods for sending and receiving data from the server go here
}
