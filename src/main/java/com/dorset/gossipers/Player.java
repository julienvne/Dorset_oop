package com.dorset.gossipers;

import javafx.scene.paint.Color;

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
    public Boat getBoat(String name) {
        for (Boat boat : boats) {
            if (boat.getName().equals(name))
                return boat;
        }
        throw new IllegalStateException("Unable to find the boat with name " + name);
    }

    //print out the life of each boat of the player
    public void getLifeEachBoats() {
        for (Boat boat : boats)
            System.out.println(boat.getStatus());
    }

    public boolean gameOver() {
        for (Boat boat : boats) {
            if (boat.getLife() != 0) {
                return false;
            }
        }
        return true;
    }

    //Take in parameter the enemy player, and fire him at the coordonate
    //given in parameters, return a string which contains the result
    public String firePlayer(Player ennemyPlayer, int x, int y, Cell cell) {
        String box = ennemyPlayer.board.getCoordinate(x, y);
        switch (box) {
            case "T":
                return ("Already touched");
            case "w":
                blankBoard.getBoard()[x][y] = ".";
                ennemyPlayer.board.getBoard()[x][y] = "T";
                cell.wasShot = true;
                cell.setFill(Color.BLACK);
                return ("Plouf");
            default:
                blankBoard.getBoard()[x][y] = "X";
                Boat boat = ennemyPlayer.getBoat(box);
                boat.removeLife();
                ennemyPlayer.board.getBoard()[x][y] = "T";
                cell.wasShot = true;
                if (boat.getLife() != 0) {
                    cell.setFill(Color.RED);
                    return ("Touched");
                }
                boat.changeColor(blankBoard);
                return ("Sink!");

        }
    }

}
