package com.dorset.gossipers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Player {
    private final Board board;
    private final Board blankBoard;
    private final Boat[] boats;

    public Player(Board board, Board blankBoard, Boat[] boats) {
        this.board = board;
        this.blankBoard = blankBoard;
        this.boats = boats;
    }

    public Boat[] getBoats() {
        return boats;
    }

    public Board getBoard() {
        return board;
    }

    public Board getBlankBoard() {
        return blankBoard;
    }

    //Search the boat in the given array with the given name
    public Boat getBoat(String name){
        for(Boat boat : boats){
            if(boat.getName().equals(name))
                return boat;
        }
        throw new IllegalStateException("Unable to find the boat with name " + name);
    }

    //print out the life of each boat of the player
    public void getLifeEachBoats(){
        for(Boat boat : boats)
            System.out.println(boat.getStatus());
    }

    public boolean gameOver(){
        for(Boat boat : boats){
            if(boat.getLife() != 0)
                return false;
        }
        return true;
    }

    //Take in parameter the enemy player, and fire him at the coordonate
    //given in parameters, return a string which contains the result
    public String firePlayer(Player ennemyPlayer, int x, int y){
        String box = ennemyPlayer.board.getCoordinate(x,y);
        switch (box){
            case "T":
                return("Already touched");
            case "w":
                return("Plouf");
            default:
                Boat boat = ennemyPlayer.getBoat(box);
                boat.removeLife();
                ennemyPlayer.board.getBoard()[x][y] ="T";
                if(boat.getLife() != 0 )
                    return("Touched boat");
                return("Couler!");
        }
    }

    public static void main(String[] args)
            throws IOException{
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

            player2.board.printBoard();

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
