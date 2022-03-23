package com.dorset.gossipers;
import java.util.Random;

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

    public void placeBoat(int x, int y, Boat boat){
        if(boat.getHorizontal() == 1){
            for(int i = x; i < x + boat.getVertical(); i++)
                board[i][y] = boat.getName();
        }
        else{
            for(int j = y; j < y + boat.getHorizontal(); j++)
                board[x][j] = boat.getName();
        }
    }

    //Fill the board with the boat in the array given in parameters
    public void fillBoardWithBoat(Boat[] array){
        for(Boat boat : array){
            boolean ok = false;
            Random rand = new Random();
            while(!ok){
                int r1 = rand.nextInt(10);
                int r2 = rand.nextInt(10);
                System.out.println(r1 + " et " + r2);
                ok = checkCoordinate(r1,r2,boat);
                if(ok)
                    placeBoat(r1,r2,boat);
            }
        }
    }

    public static void main(String[] args) {
        Board b = new Board();
        Boat a = new Boat("a",2,1,3);
        Boat c = new Boat("c",1,1,3);
        Boat d = new Boat("d",1,3,3);
        Boat e = new Boat("e",4,1,3);
        Boat f = new Boat("f",2,1,3);
        Boat g = new Boat("g",1,1,3);
        Boat array[] = {a,c,d,e,f,g};

        b.fillBoardWithBoat(array);

        b.printBoard();
    }
}
