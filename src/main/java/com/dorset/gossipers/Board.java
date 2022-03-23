package com.dorset.gossipers;

public class Board {

    private static final int SIZE = 10;

    private final String[][] board;


    //initialize the board with 'w' on each square
    public Board(){
        board = new String[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[j][i] = "w";
            }
        }
    }

    public void printBoard(){
        String line = "";
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                line += board[i][j];
            }
            System.out.println(line);
            line = "";
        }
    }

    public String getCoordinate(int x, int y){
        return board[x][y];
    }

    //Take in parameter coordonate x and y and check if the boat can be placed at those coordonates
    public boolean checkCoordinate(int x,int y, Boat boat){
        int horizontal = boat.getHorizontal();
        int vertical = boat.getVertical();
        int arround = 1;

        for(int i = x-arround; i <= x+vertical; i ++){
            for(int j = y-arround ; j <= y+horizontal;j++){
                if(i < 0 || i == SIZE || j < 0 || j == SIZE)
                    continue;
                else if(i > SIZE || j >SIZE)
                    return false;
                else if(board[i][j] != "w")
                    return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {
        Board b = new Board();
        b.printBoard();
        System.out.println("hello");

        int x = 8,y=8;
        Boat boat = new Boat("nameBoat",3,1,3);
        boolean ok = b.checkCoordinate(x,y,boat);
        System.out.println(ok); // ok -> true if the boat can be placed at x and y
        b.board[x][y] = "c"; // c-> position of the coordonate of the boat
        b.printBoard();
    }
}
