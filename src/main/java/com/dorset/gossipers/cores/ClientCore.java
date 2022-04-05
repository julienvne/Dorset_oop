package com.dorset.gossipers.cores;

import com.dorset.gossipers.Main;
import com.dorset.gossipers.Player;
import com.dorset.gossipers.server.PacketSender;
import com.dorset.gossipers.server.packets.PacketClientRequestAttack;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ClientCore implements ICore {

    private static ClientCore instance;

    private static volatile boolean play = true;
    private static volatile Player clientPlayer;
    private static volatile boolean continuePlaying;
    private static volatile String status;

    @Override
    public void run() {
        System.out.println("Starting a game (client)");

        Main.lock();

        System.out.println("client ??");
        clientPlayer.getBoard().printBoard();

        while (play) {
            playRound();

            Main.lock();
        }
    }

    public void playRound() {
        continuePlaying = true;
        System.out.println("\n----------");
        System.out.println("\nIt's your turn !\n");

        while (continuePlaying) {
            System.out.println("Blank board of the opponent: ");
            clientPlayer.getBlankBoard().printBoard();
            System.out.println();

            // Reading data using readLine
            int x = getCoordinate("y");
            int y = getCoordinate("x");

            PacketSender.send(new PacketClientRequestAttack(x, y));
            Main.lock();

            System.out.println(status);
            /*String status = attacker.firePlayer(defender, x, y);
            System.out.println(status);

            continuePlaying = (status.equalsIgnoreCase("Touched boat") || status.equalsIgnoreCase("Sink!")) && !defender.gameOver();

            defender.getLifeEachBoats();*/
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

    public void updateClientPlayer(Player clientPlayer) {
        this.clientPlayer = clientPlayer;
    }

    public void updateAttackRequest(String status, boolean continuePlaying) {
        this.status = status;
        this.continuePlaying = continuePlaying;
    }


    public static synchronized ClientCore getInstance() {
        if (instance == null)
            instance = new ClientCore();
        return instance;
    }
}
