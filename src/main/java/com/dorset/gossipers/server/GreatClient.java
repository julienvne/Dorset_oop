package com.dorset.gossipers.server;

import java.io.*;
import java.net.*;
import java.util.Scanner;

class GreatClient {

    public static void main(String args[])
            throws Exception
    {
        Socket socket = new Socket("localhost", 888);
        Client client = new Client(socket);

        Scanner scanner = new Scanner(System.in);
        String str;
        while (true){
            str = scanner.nextLine();
            client.sendMessage(str);
            System.out.println("Sending message from client to server: "+str);
        }
    }
}
