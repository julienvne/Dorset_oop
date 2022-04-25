package com.dorset.gossipers.server.packets;

import com.dorset.gossipers.Cell;
import com.dorset.gossipers.Main;
import com.dorset.gossipers.cores.ClientCore;
import com.dorset.gossipers.cores.ServerCore;
import com.dorset.gossipers.server.PacketListener;
import com.dorset.gossipers.server.PacketSender;
import javafx.scene.paint.Color;

public class PacketClientRequestAttack extends Packet {

    private int x;
    private int y;

    public PacketClientRequestAttack() {
    }

    public PacketClientRequestAttack(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void read(String[] data) {
        y = Integer.parseInt(data[0]);
        x = Integer.parseInt(data[1]);
    }

    @Override
    public String[] write() {
        return new String[]{
                y + "",
                x + ""
        };
    }

    public static class Listener implements PacketListener {

        @Override
        public void onReceive(Packet packet) {
            PacketClientRequestAttack clientRequestAttack = (PacketClientRequestAttack) packet;
            ServerCore instance = ServerCore.getInstance();
            String status = instance.getClientPlayer().firePlayer(
                    instance.getServerPlayer(),
                    clientRequestAttack.x,
                    clientRequestAttack.y,
                    instance.getClientPlayer().getBlankBoard().getCell(clientRequestAttack.x, clientRequestAttack.y));

            Cell cell = instance.getServerPlayer().getBoard().getCell(clientRequestAttack.x, clientRequestAttack.y);
            cell.wasShot = true;

            switch (status) {
                case "Plouf" -> cell.setFill(Color.BLACK);
                case "Touched" -> cell.setFill(Color.RED);
                case "Sink!" -> cell.boat.changeColor(instance.getServerPlayer().getBoard());
            }

            boolean continuePlaying = (status.equalsIgnoreCase("Touched") || status.equalsIgnoreCase("Sink!")) && !instance.getServerPlayer().gameOver();

            PacketSender.send(new PacketClientResponseAttack(clientRequestAttack.x, clientRequestAttack.y, status, continuePlaying));

            if (!continuePlaying) {
                ServerCore.yourTurn = true;
                Main.release();
            }
        }
    }
}
