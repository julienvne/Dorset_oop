package com.dorset.gossipers.server.packets;

import com.dorset.gossipers.Cell;
import com.dorset.gossipers.GsonUtils;
import com.dorset.gossipers.Main;
import com.dorset.gossipers.Player;
import com.dorset.gossipers.cores.ClientCore;
import com.dorset.gossipers.server.PacketListener;
import javafx.scene.paint.Color;

public class PacketClientResponseAttack extends Packet{

    private int x,y;
    private String status;
    private boolean continuePlaying;

    public PacketClientResponseAttack() {
    }

    public PacketClientResponseAttack(int x, int y, String status, boolean continuePlaying) {
        this.x = x;
        this.y = y;
        this.status = status;
        this.continuePlaying = continuePlaying;
    }

    @Override
    public void read(String[] data) {
        this.status = data[0];
        this.continuePlaying = Boolean.parseBoolean(data[1]);
    }

    @Override
    public String[] write() {
        return new String[]{
                status,
                continuePlaying+"",
        };
    }

    public static class Listener implements PacketListener{

        @Override
        public void onReceive(Packet packet) {
            PacketClientResponseAttack responseAttack = (PacketClientResponseAttack) packet;
            ClientCore instance = ClientCore.getInstance();
            instance.updateAttackRequest(responseAttack.status, responseAttack.continuePlaying);
            Cell cell = ClientCore.clientPlayer.getBlankBoard().getCell(responseAttack.x, responseAttack.y);
            cell.wasShot = true;

            switch (responseAttack.status) {
                case "Plouf" -> cell.setFill(Color.BLACK);
                case "Touched" -> cell.setFill(Color.RED);
                case "Sink!" -> cell.boat.changeColor(ClientCore.clientPlayer.getBoard());
            }

            if(!responseAttack.continuePlaying){
                System.out.println("your turn = false");
                PacketClientInitPlayerBoard.Listener.yourTurn = false;
            }

            Main.release();
        }
    }
}
