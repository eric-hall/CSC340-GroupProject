package com.restready.common.tempchat;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class Client {

    private static final int RETRY_CONNECTION_INTERVAL_SECONDS = 2;

    private static NetChannel connectToServer(ConsoleGui console) {

        try (ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1)) {

            CompletableFuture<Socket> future = new CompletableFuture<>();
            future.thenRun(scheduler::shutdown);

            scheduler.scheduleAtFixedRate(() -> {
                console.print("Attempting to connect to server...\n");
                try {
                    Socket socket = new Socket(Server.DEFAULT_ADDRESS, Server.DEFAULT_PORT);
                    future.complete(socket);
                } catch (IOException ignored) {
                    // Server not running or not reachable...
                }
            }, 0, RETRY_CONNECTION_INTERVAL_SECONDS, TimeUnit.SECONDS);

            return new NetChannel(future.get());

        } catch (IOException | ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

        ConsoleGui console = new ConsoleGui("Client Console", "Client");

        NetChannel server = connectToServer(console);
        console.print("Connected to server " + server.getSocketInetAddressString() + "\n");

        // Forward lines entered into the console to the network channel
        console.setUserTextInputListener(server::sendMessage);

        while (server.isConnected()) {
            String message = server.readMessage();
            if (!message.isBlank()) {
                console.print("[Server] :: " + message + "\n");
            }
        }

        server.shutdown();
        console.shutdown();
    }
}
