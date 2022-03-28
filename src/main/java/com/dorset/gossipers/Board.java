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

    public String[][] getBoard() {
        return board;
    }

    //Print the board
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
    public boolean checkCoordinate(int x, int y, Boat boat){
        int length = boat.getLength();
        int height = boat.getHeight();
        int arround = 1;

        for(int i = x - arround; i <= x + height; i ++){
            for(int j = y - arround ; j <= y + length;j++){
                if(i < 0 || i == SIZE || j < 0 || j == SIZE)
                    continue;
                else if(i > SIZE || j >SIZE)
                    return false;
                else if(!board[i][j].equals("w"))
                    return false;
            }
        }
        return true;
    }

    //Place a boat at the given coordonate
    public void placeBoat(int x, int y, Boat boat){
        if(boat.getLength() == 1){
            for(int i = x; i < x + boat.getHeight(); i++)
                board[i][y] = boat.getName();
        }
        else{
            for(int j = y; j < y + boat.getLength(); j++)
                board[x][j] = boat.getName();
        }
    }

    //Fill the board with the boat in the array given in parameters
    public void fillBoardWithBoat(Boat[] array){
        for(Boat boat : array){
            boolean ok = false;
            Random rand = new Random();
            while(!ok){
                int bool = rand.nextInt(2);
                int r1 = rand.nextInt(10);
                int r2 = rand.nextInt(10);
                if(bool == 1){boat.changeDirection();}
                ok = checkCoordinate(r1,r2,boat);
                if(ok)
                    placeBoat(r1,r2,boat);
            }
        }
    }

    //Create the array of Boats
    public static Boat[] createArrayOfBoats() {
        return new Boat[]{
                new Boat("p", 1, 5),//porte-avions
                new Boat("c", 4, 1),//croiseurs
                new Boat("r", 1, 4),//croiseurs
                new Boat("s", 3, 1),//sous-marin
                new Boat("o", 2, 1),//torpilleurs
                new Boat("t", 1, 2)//torpilleurs
        };
    }
}
