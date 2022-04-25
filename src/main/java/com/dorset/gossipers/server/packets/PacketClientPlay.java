package com.dorset.gossipers.server.packets;

import com.dorset.gossipers.Main;
import com.dorset.gossipers.cores.ClientCore;
import com.dorset.gossipers.server.PacketListener;
import javafx.scene.paint.Color;

public class PacketClientPlay extends Packet{

    private int x,y;
    private String status;

    public PacketClientPlay() {
    }

    public PacketClientPlay(int x, int y, String status) {
        this.x = x;
        this.y = y;
        this.status = status;
    }

    @Override
    public void read(String[] data) {}

    @Override
    public String[] write() {
        return new String[]{};
    }

    public static class Listener implements PacketListener {

        @Override
        public void onReceive(Packet packet) {
            PacketClientPlay clientPlay = (PacketClientPlay) packet;
            switch (clientPlay.status) {
                case "Plouf" -> ClientCore.clientPlayer.getBoard().getCell(clientPlay.x, clientPlay.y).setFill(Color.BLACK);
                case "Touched" -> ClientCore.clientPlayer.getBoard().getCell(clientPlay.x, clientPlay.y).setFill(Color.RED);
                case "Sink!" -> ClientCore.clientPlayer.getBoard().getCell(clientPlay.x, clientPlay.y).boat.changeColor(ClientCore.clientPlayer.getBoard());
            }

            PacketClientInitPlayerBoard.Listener.yourTurn = true;


            Main.release();
        }
    }
}
