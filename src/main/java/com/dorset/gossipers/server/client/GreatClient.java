package com.dorset.gossipers.server.client;

import java.net.*;
import java.util.Scanner;

public class GreatClient {

    public static Client client;

    public static void createClient(String host) {
        try {
            Socket socket = new Socket(host, 888);
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
}
