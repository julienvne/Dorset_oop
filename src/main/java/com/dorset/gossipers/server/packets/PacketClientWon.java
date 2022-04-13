package com.dorset.gossipers.server.packets;

import com.dorset.gossipers.server.ClientType;
import com.dorset.gossipers.server.PacketListener;
import com.dorset.gossipers.server.client.GreatClient;

public class PacketClientWon extends Packet{

    private ClientType winner;

    public PacketClientWon() {
    }

    public PacketClientWon(ClientType winner) {
        this.winner = winner;
    }

    @Override
    public void read(String[] data) {
        winner = ClientType.valueOf(data[0]);
    }

    @Override
    public String[] write() {
        return new String[]{
                winner.name()
        };
    }

    public static class Listener implements PacketListener{

        @Override
        public void onReceive(Packet packet) {
            PacketClientWon packetClientWon = (PacketClientWon) packet;

            GreatClient.closeConnection();

            switch (packetClientWon.winner){
                case CLIENT -> {
                    System.out.println("You won the game !");
                    System.exit(0);
                }
                case SERVER -> {
                    System.out.println("You lost the game !");
                    System.exit(0);
                }
            }
        }
    }
}
