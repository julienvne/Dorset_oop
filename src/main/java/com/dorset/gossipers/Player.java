package com.dorset.gossipers;

public class Player {
    private final Board board;
    private final Board enemyBoard;

    public Player(Board board, Board enemyBoard) {
        this.board = board;
        this.enemyBoard = enemyBoard;
    }
}
