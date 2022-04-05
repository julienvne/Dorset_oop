package com.dorset.gossipers.cores;

import com.dorset.gossipers.Board;
import com.dorset.gossipers.Boat;
import com.dorset.gossipers.Main;
import com.dorset.gossipers.Player;
import com.dorset.gossipers.server.PacketSender;
import com.dorset.gossipers.server.packets.PacketClientInitPlayerBoard;
import com.dorset.gossipers.server.packets.PacketClientPlay;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ServerCore implements ICore {

    private static ServerCore instance;
    private static volatile Player serverPlayer;
    private static volatile Player clientPlayer;

    @Override
    public void run() {
        System.out.println("Starting a game (server)");
        Board serverBoard = new Board();
        Board clientBoard = new Board();
        Board serverBlankBoard = new Board();
        Board clientBlankBoard = new Board();
        Boat[] serverBoat = Board.createArrayOfBoats();
        Boat[] clientBoat = Board.createArrayOfBoats();

        serverBoard.fillBoardWithBoat(serverBoat);
        clientBoard.fillBoardWithBoat(clientBoat);

        serverPlayer = new Player(serverBoard, serverBlankBoard, serverBoat);
        clientPlayer = new Player(clientBoard, clientBlankBoard, clientBoat);

        PacketSender.send(new PacketClientInitPlayerBoard(clientPlayer));

        while (!serverPlayer.gameOver() || !clientPlayer.gameOver()) {
            playRound();

            if (!clientPlayer.gameOver()) {
                System.out.println("Waiting for input from client");
                PacketSender.send(new PacketClientPlay());
                //playRound(clientPlayer, serverPlayer, "2");
                Main.lock();
            }
        }

        if (serverPlayer.gameOver()) {
            System.out.println("\n----------\nPlayer 2 won!");
        } else {
            System.out.println("\n----------\nPlayer 1 won!");
        }
    }

    public void playRound() {
        boolean continuePlaying = true;
        System.out.println("\n----------");
        System.out.println("\nIt's your turn !\n");

        while (continuePlaying) {
            System.out.println("Blank board of the opponent: ");
            serverPlayer.getBlankBoard().printBoard();
            System.out.println();

            // Reading data using readLine
            int x = getCoordinate("y");
            int y = getCoordinate("x");

            String status = serverPlayer.firePlayer(clientPlayer, x, y);
            System.out.println(status);

            continuePlaying = (status.equalsIgnoreCase("Touched boat") || status.equalsIgnoreCase("Sink!")) && !clientPlayer.gameOver();
        }
    }

    public int getCoordinate(String coord) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean check = true;
        int x = 0;
        while (check) {
            System.out.print("Choose the " + coord + " coordinate: ");
            try {
                x = Integer.parseInt(reader.readLine());
                if (x > 9 || x < 0) {
                    System.out.println("Please enter a valid number...");
                } else {
                    check = false;
                }
            } catch (Exception e) { //catches an exception when the user enters a string or anything but an int
                System.out.println("Please only use digits to make a selection.");
            }
        }
        return x;

    }

    public Player getServerPlayer() {
        return serverPlayer;
    }

    public Player getClientPlayer() {
        return clientPlayer;
    }

    public static synchronized ServerCore getInstance() {
        if (instance == null)
            instance = new ServerCore();
        return instance;
    }
}
