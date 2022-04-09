package com.dorset.gossipers.server.server;

import java.io.IOException;
import java.net.*;

public class GreatServer {

    public static Server server;
    private static ServerSocket serverSocket;

    public static void createServer() {
        try {
            serverSocket = new ServerSocket(888, 10);
            System.out.println("Server Is Running");
            Socket client = serverSocket.accept();
            server = new Server(client);
            /*Scanner scanner = new Scanner(System.in);
            String str;
            while (true){
                str = scanner.nextLine();
                c.sendMessage(str);
                System.out.println("Sending message from server to client: "+str);
            }*/
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void closeConnection(){
        server.stopThread();
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
