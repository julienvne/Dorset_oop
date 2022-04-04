package com.dorset.gossipers;

import com.dorset.gossipers.server.ClientType;
import com.dorset.gossipers.server.client.GreatClient;
import com.dorset.gossipers.server.server.GreatServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static ClientType clientType;

    public static int getCoordinate(String number, String coord){
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        Boolean check = true;
        int x = 0;
        while(check){
            System.out.print("Player "+number+", choose the "+coord+" coordinate: ");
            try{
                x = Integer.parseInt(reader.readLine());
                if(x > 9 || x < 0){
                    System.out.println("Please enter a valid number...");
                }else{
                    check = false;
                }
            }catch(Exception e){ //catches an exception when the user enters a string or anything but an int
                System.out.println("Please only use digits to make a selection.");
            }
        }
        return x;

    };

    public static void playRound(Player attacker, Player defender, String number) throws IOException {

        boolean continuePlaying = true;
        // Enter data using BufferReader
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        System.out.println("\n----------");
        System.out.println("\nPlayer "+number+", it's your turn !\n");

        while(continuePlaying){
            System.out.println("Blank board of the opponent: ");
            defender.getBlankBoard().printBoard();
            System.out.println();

            // Reading data using readLine

            int x = getCoordinate(number, "y");

            int y = getCoordinate(number, "x");


            String status = attacker.firePlayer(defender, x, y);
            System.out.println(status);

            continuePlaying = (status == "Touched boat" || status == "Sink!") && !defender.gameOver();

            defender.getLifeEachBoats();
        }
    };

    public static void main(String[] args) throws IOException {
        if (args.length == 1 && args[0].equalsIgnoreCase("server")) {
            clientType = ClientType.SERVER;
            GreatServer.createServer();
        } else if (args.length == 3 && args[0].equalsIgnoreCase("client")) {
            String ip = args[1];
            int port = Integer.parseInt(args[2]);

            clientType = ClientType.CLIENT;
            GreatClient.createClient(ip, port);
            return;
        }

        Board board1 = new Board();
        Board board2 = new Board();
        Board Blankboard1 = new Board();
        Board Blankboard2 = new Board();

        Boat[] array1 = Board.createArrayOfBoats();
        Boat[] array2 = Board.createArrayOfBoats();

        board1.fillBoardWithBoat(array1);
        board2.fillBoardWithBoat(array2);

        Player player1 = new Player(board1, Blankboard1, array1);
        Player player2 = new Player(board2, Blankboard2, array2);


        while (!player1.gameOver() || !player2.gameOver()) {

            playRound(player1,player2,"1");

            if(!player2.gameOver()){
                playRound(player2,player1,"2");
            }
        }

        if(player1.gameOver()){
            System.out.println("\n----------\nPlayer 2 won!");
        }
        else{
            System.out.println("\n----------\nPlayer 1 won!");
        }

    }
}
