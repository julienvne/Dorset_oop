package com.dorset.gossipers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {
        Board board1 = new Board();
        Board board2 = new Board();
        Board Blankboard1 = new Board();
        Board Blankboard2 = new Board();

        Boat[] array1 = Board.createArrayOfBoats();
        Boat[] array2 = Board.createArrayOfBoats();

        board1.fillBoardWithBoat(array1);
        board2.fillBoardWithBoat(array2);

        Player player1 = new Player(board1,Blankboard1,array1);
        Player player2 = new Player(board2,Blankboard2,array2);

        while(!player1.gameOver() || !player2.gameOver()){
            // Enter data using BufferReader
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(System.in));

            player2.getBoard().printBoard();

            // Reading data using readLine
            int x = Integer.parseInt(reader.readLine());
            while(x > 9)
                x = Integer.parseInt(reader.readLine());

            int y = Integer.parseInt(reader.readLine());
            while(y > 9)
                y = Integer.parseInt(reader.readLine());

            System.out.println(player1.firePlayer(player2,x,y));
            player2.getLifeEachBoats();

        }
    }
}
