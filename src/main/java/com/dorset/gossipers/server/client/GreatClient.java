package com.dorset.gossipers.server.client;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class GreatClient {

    private static Socket socket;
    public static Client client;

    public static void createClient(String host) {
        try {
            socket = new Socket(host, 888);
            client = new Client(socket);

            /*Scanner scanner = new Scanner(System.in);
            String str;
            while (true) {
                str = scanner.nextLine();
                client.sendMessage(str);
                System.out.println("Sending message from client to server: " + str);
            }*/
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
