package com.restready.common.tempchat;

import java.io.*;
import java.net.*;

public class Server {

    public static final String DEFAULT_ADDRESS = "localhost";
    public static final int DEFAULT_PORT = 8080;

    private static NetChannel receiveClient() {
        try (ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT)) {
            return new NetChannel(serverSocket.accept());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

        ConsoleGui console = new ConsoleGui("Server Console", "Server");

        console.print("Waiting for connections...\n");
        NetChannel client = receiveClient();
        console.print("Client connected: " + client.getSocketInetAddressString() + "\n");

        // Forward messages entered into the console to the network channel
        console.setUserTextInputListener(client::sendMessage);

        while (client.isConnected()) {
            String message = client.readMessage();
            if (!message.isBlank()) {
                console.print("[Client] :: " + message + "\n");
            }
        }

        client.shutdown();
        console.shutdown();
    }
}
