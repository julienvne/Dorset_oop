package com.dorset.gossipers.server.packets;

import com.dorset.gossipers.Main;
import com.dorset.gossipers.cores.ServerCore;
import com.dorset.gossipers.server.PacketListener;
import com.dorset.gossipers.server.PacketSender;

public class PacketClientRequestAttack extends Packet{

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
                y+"",
                x+""
        };
    }

    public static class Listener implements PacketListener{

        @Override
        public void onReceive(Packet packet) {
            PacketClientRequestAttack clientRequestAttack = (PacketClientRequestAttack) packet;
            ServerCore instance = ServerCore.getInstance();
            String status = instance.getClientPlayer().firePlayer(instance.getServerPlayer(), clientRequestAttack.x, clientRequestAttack.y);

            boolean continuePlaying = (status.equalsIgnoreCase("Touched boat") || status.equalsIgnoreCase("Sink!")) && !instance.getServerPlayer().gameOver();

            PacketSender.send(new PacketClientResponseAttack(status, continuePlaying, instance.getClientPlayer()));

            if(!continuePlaying){
                Main.release();
            }
        }
    }
}
