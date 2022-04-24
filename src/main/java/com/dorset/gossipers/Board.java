package com.dorset.gossipers;
import java.util.Random;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Board extends Parent {

    private static final int SIZE = 10;
    private final String[][] board;
    private VBox grid;
    private Boolean playerBoard;


    //initialize the board with 'w' on each square
    public Board(Boolean player) {

        grid = new VBox();
        playerBoard = player;

        for (int j = 0; j < 10; j++) {
            HBox row = new HBox();
            for (int i = 0; i < 10; i++) {
                Cell cellule = new Cell(i, j, this);
                row.getChildren().add(cellule);
            }

            grid.getChildren().add(row);
        }

        getChildren().add(grid);


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
    public void printBoard() {
        String line = "";
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                line += board[i][j];
            }
            System.out.println(line);
            line = "";
        }
    }

    public String getCoordinate(int x, int y) {
        return board[x][y];
    }

    //Take in parameter coordonate x and y and check if the boat can be placed at those coordonates
    public boolean checkCoordinate(int x, int y, Boat boat) {
        int length = boat.getLength();
        int height = boat.getHeight();
        int arround = 1;

        for (int i = x - arround; i <= x + height; i++) {
            for (int j = y - arround; j <= y + length; j++) {
                if (i < 0 || i == SIZE || j < 0 || j == SIZE)
                    continue;
                else if (i > SIZE || j > SIZE)
                    return false;
                else if (!board[i][j].equals("w"))
                    return false;
            }
        }
        return true;
    }

    //Place a boat at the given coordonate
    public void placeBoat(int x, int y, Boat boat) {
        if (boat.getLength() == 1) {
            for (int i = y; i < y + boat.getHeight(); i++)
                board[x][i] = boat.getName();
            for (int i = y; i < y + boat.getHeight(); i++) {
                Cell cell = getCell(x, i);
                cell.boat = boat;
                if (playerBoard) {
                    cell.setFill(Color.WHITE);
                    cell.setStroke(Color.GREEN);
                }
            }
        } else {
            for (int j = x; j < x + boat.getLength(); j++)
                board[j][x] = boat.getName();
            for (int i = x; i < x + boat.getLength(); i++) {
                Cell cell = getCell(i, y);
                cell.boat = boat;
                if (playerBoard) {
                    cell.setFill(Color.WHITE);
                    cell.setStroke(Color.GREEN);
                }
            }
        }
    }

    //Fill the board with the boat in the array given in parameters
    public void fillBoardWithBoat(Boat[] array) {
        for (Boat boat : array) {
            boolean BoatIsPlaced = false;
            Random rand = new Random();
            while (!BoatIsPlaced) {
                int bool = rand.nextInt(2);
                int x = rand.nextInt(10);
                int y = rand.nextInt(10);
                if (bool == 1) {boat.changeDirection();}
                BoatIsPlaced = checkCoordinate(x, y, boat);
                if (BoatIsPlaced)
                    placeBoat(x, y, boat);
                    boat.setX(x);
                    boat.setY(y);

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

    public class Cell extends Rectangle {
        public int x, y;
        public Boat boat;
        //public boolean wasShot = false;
        private Board board;

        public Cell(int x, int y, Board board) {
            super(35, 35);
            this.x = x;
            this.y = y;
            this.board = board;
            this.boat = null;
            setFill(Color.TRANSPARENT);
            setStroke(Color.BLACK);
        }

        /*public boolean shoot() {
            wasShot = true;
            setFill(Color.BLACK);

            if (boat != null) {
                boat.hit();
                setFill(Color.RED);
                if (!boat.isAlive()) {
                    board.boat--;
                }
                return true;
            }

            return false;
        }*/
    }

    public Cell getCell(int x, int y) {
        return (Cell)((HBox)grid.getChildren().get(y)).getChildren().get(x);
    }
}
