package com.restready.common.tempchat;

import java.io.*;
import java.net.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class NetChannel {

    public static final int MESSAGE_BUFFER_CAPACITY = 64;

    private final Socket _socket;
    private final Thread _sendThread;
    private final Thread _readThread;
    private final BlockingQueue<String> _outgoingMessages;
    private final BlockingQueue<String> _incomingMessages;
    private boolean _isShutdown;

    public NetChannel(Socket socket) throws IOException {
        _socket = socket;
        _outgoingMessages = new ArrayBlockingQueue<>(MESSAGE_BUFFER_CAPACITY);
        _incomingMessages = new ArrayBlockingQueue<>(MESSAGE_BUFFER_CAPACITY);
        _sendThread = initSendThread();
        _readThread = initReadThread();
        _isShutdown = false;
        _sendThread.start();
        _readThread.start();
    }

    private Thread initSendThread() {
        Thread sendThread = new Thread(() -> {
            try (PrintWriter out = new PrintWriter(_socket.getOutputStream(), true)) {
                while (isConnected()) {
                    out.println(_outgoingMessages.take());
                }
            } catch (IOException | InterruptedException ignored) {
                // Socket closed, probably...
            } finally {
                shutdown();
            }
        });
        sendThread.setDaemon(true);
        return sendThread;
    }

    private Thread initReadThread() {
        Thread readThread = new Thread(() -> {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(_socket.getInputStream()))) {
                while (isConnected()) {
                    String message = in.readLine();
                    if (message == null) {
                        break;
                    }
                    _incomingMessages.put(message);
                }
            } catch (IOException | InterruptedException ignored) {
                // Socket closed, probably...
            } finally {
                shutdown();
            }
        });
        readThread.setDaemon(true);
        return readThread;
    }

    public void sendMessage(String message) {
        if (!_outgoingMessages.offer(message)) {
            throw new RuntimeException();
        }
    }

    public String readMessage() {
        try {
            return _incomingMessages.take();
        } catch (InterruptedException e) {
            return ""; // Socket closed, probably...
        }
    }

    public void shutdown() {

        if (_isShutdown) {
            return;
        }

        try {
            _socket.close();
            _incomingMessages.put("");
            _outgoingMessages.put("");
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }

        _readThread.interrupt();
        _sendThread.interrupt();
        _isShutdown = true;
    }

    public boolean isConnected() {
        return !_isShutdown && !_socket.isClosed() && _socket.isConnected();
    }

    public String getSocketInetAddressString() {
        return _socket.getInetAddress().toString();
    }
}
