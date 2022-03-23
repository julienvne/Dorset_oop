package com.dorset.gossipers;

public class Board {
    String[][] board = new String[10][10];
    int size = 10;

    //initialize the board with 'w' on each square
    public Board(){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[j][i] = "w";
            }
        }
    }

    public void printBoard(){
        String line = "";
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                line += board[j][i];
            }
            System.out.println(line);
            line = "";
        }
    }

    public String getCoordinate(int x, int y){
        return board[x][y];
    }

    public static void main(String[] args) {
        Board b = new Board();
        b.printBoard();
        int x = 1,y = 2;
        System.out.print("Aux coordoonées (" + x +";" + y + ") nous avons : " + b.getCoordinate(x,y));
    }
}
