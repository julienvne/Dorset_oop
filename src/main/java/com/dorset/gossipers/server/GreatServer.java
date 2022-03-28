package com.dorset.gossipers.server;

import java.net.*;
import java.util.Scanner;

class GreatServer {

    public static void main(String args[])
            throws Exception {
        ServerSocket server = new ServerSocket(888, 10);
        System.out.println("Now Server Is Running");
        Socket client = server.accept();
        Server c = new Server(client);

        Scanner scanner = new Scanner(System.in);
        String str;
        while (true){
            str = scanner.nextLine();
            c.sendMessage(str);
            System.out.println("Sending message from server to client: "+str);
        }
    }
}
