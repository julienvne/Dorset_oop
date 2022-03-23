package com.example.gossipers;

public class Board {
    String[][] board = new String[10][10];
    int size = 10;

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

    public static void main(String[] args) {
        Board b = new Board();
        b.printBoard();
    }
}
