package com.dorset.gossipers.server.client;

import com.dorset.gossipers.server.client.socket.ClientSocket;

import java.io.IOException;
import java.net.*;

public class GreatClient {

    private static Socket socket;
    public static ClientSocket client;

    public static void createClient(String host) {
        try {
            socket = new Socket(host, 888);
            client = new ClientSocket(socket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeConnection(){
        client.stopThread();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
