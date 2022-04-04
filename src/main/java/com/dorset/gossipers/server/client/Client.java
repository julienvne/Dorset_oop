package com.dorset.gossipers.server.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Thread {

    private BufferedReader input;
    private PrintWriter output;

    public Client(Socket socket) {
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);

            start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message){
        output.println(message);
    }

    @Override
    public void run() {
        String line;
        try {
            while (true) {
                line = input.readLine();
                if (line.equals("end"))
                    break;

                System.out.println("Received message: " + line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
