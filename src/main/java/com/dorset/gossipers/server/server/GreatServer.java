package com.dorset.gossipers.server.server;

import com.dorset.gossipers.server.server.socket.ServerSocket;

import java.io.IOException;
import java.net.*;

public class GreatServer {

    public static ServerSocket server;
    private static java.net.ServerSocket serverSocket;

    public static void createServer() {
        try {
            serverSocket = new java.net.ServerSocket(888, 10);
            System.out.println("Server Is Running");
            Socket client = serverSocket.accept();
            server = new ServerSocket(client);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void closeConnection() {
        server.stopThread();
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
