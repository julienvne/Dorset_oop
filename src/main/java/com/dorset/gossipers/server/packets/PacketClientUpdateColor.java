package com.dorset.gossipers.server.packets;

import com.dorset.gossipers.cores.ClientCore;
import com.dorset.gossipers.server.PacketListener;
import javafx.scene.paint.Color;

public class PacketClientUpdateColor extends Packet{

    private int x,y;
    private String status;

    public PacketClientUpdateColor() {
    }

    public PacketClientUpdateColor(int x, int y, String status) {
        this.x = x;
        this.y = y;
        this.status = status;
    }

    @Override
    public void read(String[] data) {

    }

    @Override
    public String[] write() {
        return new String[0];
    }

    public static class Listener implements PacketListener{

        @Override
        public void onReceive(Packet packet) {
            PacketClientUpdateColor clientUpdateColor = (PacketClientUpdateColor) packet;

            switch (clientUpdateColor.status) {
                case "Plouf" -> ClientCore.clientPlayer.getBoard().getCell(clientUpdateColor.x, clientUpdateColor.y).setFill(Color.BLACK);
                case "Touched" -> ClientCore.clientPlayer.getBoard().getCell(clientUpdateColor.x, clientUpdateColor.y).setFill(Color.RED);
                case "Sink!" -> ClientCore.clientPlayer.getBoard().getCell(clientUpdateColor.x, clientUpdateColor.y).boat.changeColor(ClientCore.clientPlayer.getBoard());
            }
        }
    }
}
