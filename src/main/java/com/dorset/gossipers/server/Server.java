package com.dorset.gossipers.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Server extends Thread{

    private BufferedReader input;
    private PrintWriter output;

    public Server(Socket client) throws Exception {
        input = new BufferedReader(new InputStreamReader(client.getInputStream()));
        output = new PrintWriter(client.getOutputStream(), true);

        start();
    }

    public void sendMessage(String text) {
        output.println(text);
    }

    @Override
    public void run() {
        String line;
        try {
            while (true) {
                line = input.readLine();
                if (line.equals("end"))
                    break;

                System.out.println("Received message: "+line);
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
