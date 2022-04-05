package com.dorset.gossipers.server.packets;

import com.dorset.gossipers.Main;
import com.dorset.gossipers.Player;
import com.dorset.gossipers.cores.ClientCore;
import com.dorset.gossipers.server.PacketListener;

public class PacketClientInitPlayerBoard extends Packet {

    private Player player;

    public PacketClientInitPlayerBoard() {
    }

    public PacketClientInitPlayerBoard(Player player) {
        this.player = player;
    }

    @Override
    public void read(String[] data) {
        player = gson.fromJson(data[0], Player.class);
    }

    @Override
    public String[] write() {
        return new String[]{
                gson.toJson(player)
        };
    }

    public static class Listener implements PacketListener {

        @Override
        public void onReceive(Packet packet) {
            PacketClientInitPlayerBoard playerBoardPacket = (PacketClientInitPlayerBoard) packet;
            ClientCore instance = ClientCore.getInstance();
            playerBoardPacket.player.getBoard().printBoard();
            playerBoardPacket.player.getBlankBoard().printBoard();
            instance.updateClientPlayer(playerBoardPacket.player);
        }
    }
}