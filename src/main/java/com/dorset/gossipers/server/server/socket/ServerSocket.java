package com.dorset.gossipers.server.server.socket;

import com.dorset.gossipers.server.PacketReceiver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerSocket extends Thread{

    private final BufferedReader input;
    private final PrintWriter output;
    private boolean exit;

    public ServerSocket(Socket client) throws Exception {
        input = new BufferedReader(new InputStreamReader(client.getInputStream()));
        output = new PrintWriter(client.getOutputStream(), true);
        exit = false;

        start();
    }

    public void sendMessage(String text) {
        output.println(text);
    }

    @Override
    public void run() {
        String line;
        try {
            while (!exit) {
                line = input.readLine();
                if (line.equals("end"))
                    break;

                System.out.println("Received message: "+line);
                PacketReceiver.receive(line);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void stopThread(){
        exit = true;
    }
}
