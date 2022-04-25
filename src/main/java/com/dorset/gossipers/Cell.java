package com.dorset.gossipers;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell extends Rectangle {
    public int x, y;
    public Boat boat;
    public boolean wasShot = false;
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
