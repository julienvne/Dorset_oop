package com.dorset.gossipers.server.packets;

import com.dorset.gossipers.Board;
import com.dorset.gossipers.server.PacketListener;

public class PacketInitPlayerBoard extends Packet{

    private Board clientBoard;
    private Board clientBlankBoard;

    @Override
    public void read(String[] data) {
        clientBoard = gson.fromJson(data[0], Board.class);
        clientBlankBoard = gson.fromJson(data[1], Board.class);
    }

    @Override
    public String[] write() {
        return new String[]{
                gson.toJson(clientBoard),
                gson.toJson(clientBlankBoard)
        };
    }

    public static class Listener implements PacketListener {

        @Override
        public void onReceive(Packet packet) {
            PacketInitPlayerBoard playerBoardPacket = (PacketInitPlayerBoard) packet;
            playerBoardPacket.clientBoard.printBoard();
        }
    }
}