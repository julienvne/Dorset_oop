package com.dorset.gossipers.server.packets;

import com.dorset.gossipers.Board;
import com.dorset.gossipers.Boat;
import com.dorset.gossipers.Cell;
import com.dorset.gossipers.Player;
import com.dorset.gossipers.cores.ClientCore;
import com.dorset.gossipers.server.PacketListener;
import com.dorset.gossipers.server.PacketSender;
import javafx.application.Application;

public class PacketClientInitPlayerBoard extends Packet {

    private String[][] board;
    private String[][] blankBoard;
    private Boat[] boats;

    public PacketClientInitPlayerBoard() {
    }

    public PacketClientInitPlayerBoard(String[][] board, String[][] blankBoard, Boat[] boats) {
        this.board = board;
        this.blankBoard = blankBoard;
        this.boats = boats;
    }

    @Override
    public void read(String[] data) {
    }

    @Override
    public String[] write() {
        return new String[]{
        };
    }

    public static class Listener implements PacketListener {

        public static volatile boolean yourTurn = false;

        @Override
        public void onReceive(Packet packet) {
            PacketClientInitPlayerBoard playerBoardPacket = (PacketClientInitPlayerBoard) packet;
            Board board = new Board(true);
            board.setBoard(playerBoardPacket.board);

            for (Boat boat : playerBoardPacket.boats){
                board.placeBoat(boat.getX(), boat.getY(), boat);
            }

            Board blankBoard = new Board(false, event -> {
                if(!yourTurn)
                    return;

                Cell cell = (Cell) event.getSource();
                PacketSender.send(new PacketClientRequestAttack(cell.x, cell.y));
            });
            blankBoard.setBoard(playerBoardPacket.blankBoard);

            Player player = new Player(board, blankBoard, playerBoardPacket.boats);
            ClientCore instance = ClientCore.getInstance();
            player.getBoard().printBoard();
            player.getBlankBoard().printBoard();
            instance.updateClientPlayer(player);


            ClientCore.launchInterface();
        }
    }
}